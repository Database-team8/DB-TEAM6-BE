package com.ajoufinder.be.board.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ajoufinder.be.alarm.repository.AlarmRepository;
import com.ajoufinder.be.alarm.service.AlarmService;
import com.ajoufinder.be.board.domain.Board;
import com.ajoufinder.be.board.domain.constant.BoardStatus;
import com.ajoufinder.be.board.domain.constant.Category;
import com.ajoufinder.be.board.dto.BoardCreateRequest;
import com.ajoufinder.be.board.dto.BoardDetailResponse;
import com.ajoufinder.be.board.dto.BoardFilterRequest;
import com.ajoufinder.be.board.dto.BoardSimpleResponse;
import com.ajoufinder.be.board.dto.BoardUpdateRequest;
import com.ajoufinder.be.board.repository.BoardRepository;
import com.ajoufinder.be.item_type.domain.ItemType;
import com.ajoufinder.be.item_type.repository.ItemTypeRepository;
import com.ajoufinder.be.location.domain.Location;
import com.ajoufinder.be.location.repository.LocationRepository;
import com.ajoufinder.be.user.domain.User;
import com.ajoufinder.be.user.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardService {
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    private final ItemTypeRepository itemTypeRepository;
    private final LocationRepository locationRepository;
    //private final AlarmService alarmService; 알림 기능 구현 시 쓸 것.

    /* 게시글 생성 메서드 */
    @Transactional
    public Long createBoard(BoardCreateRequest request) {
        User user = userRepository.findById(request.userId())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        ItemType itemType = itemTypeRepository.findById(request.itemTypeId())
                .orElseThrow(() -> new EntityNotFoundException("ItemType not found"));

        Location location = null;
        if (request.locationId() != null) {
            location = locationRepository.findById(request.locationId())
                    .orElseThrow(() -> new EntityNotFoundException("Location not found"));
        }

        Board board = request.toEntity(user, itemType, location);
        boardRepository.save(board);

        /* alarmService.notify(user, "조건에 맞는 게시글이 생성됨", board.getTitle(), "board/"+board.getId());
        * 알림 생성 예시 코드입니다. 위 코드를 수행하기 전, 작성된 게시글에 따른 알림 대상을 뽑고,
        * 그 유저에 대해 알림을 추가하면 됩니다. 댓글 생성에서도 동일함.
        */
        return board.getId();
    }

    /* 게시글 수정 메서드 */
    /* User 정보는 따로 DTO에서 받지 않는 것으로 구현했습니다.
     * BoardRepository 속 User 정보 이외의 부분만 업데이트 합니다.
     * 프론트단에서 게시글을 수정하려는 사용자는 게시글 작성자라는 것을 보장해야 함.
     */
    @Transactional
    public Long updateBoard(Long boardId, BoardUpdateRequest request) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("Board not found"));

        Location location = locationRepository.findById(request.locationId())
                .orElseThrow(() -> new IllegalArgumentException("Location not found."));

        ItemType itemType = itemTypeRepository.findById(request.itemTypeId())
                .orElseThrow(() -> new IllegalArgumentException("ItemType not found."));

        board.update(request, location, itemType);
        return board.getId();
    }

    /* 게시글 종류 (분실/습득) 따라 게시글 조회하기
     * 페이지 단위로 가져오도록 했습니다. 경로 뒤 인자로 페이지 번호, 사이즈 넣어서 요청합니다.
    */

    @Transactional(readOnly = true)
    public Page<BoardSimpleResponse> getBoardsByCategory(Category category, Pageable pageable) {
        return boardRepository.findByCategoryAndStatus(category, BoardStatus.ACTIVE, pageable)
                .map(BoardSimpleResponse::from);
    }
//     public List<BoardSimpleResponse> getBoardsByCategory(Category category) {
//         return boardRepository.findByCategory(category).stream()
//                 .map(BoardSimpleResponse::from)
//                 .collect(Collectors.toList());
//     }   

    /* 특정 게시글의 상세 정보 조회하기 */
    public BoardDetailResponse getBoardDetail(Long boardId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("Board not found."));

        if (!board.getStatus().equals(BoardStatus.DELETED)) {
            throw new RuntimeException("삭제된 게시글은 조회할 수 없습니다.");
        }

        return BoardDetailResponse.from(board);
    }

    /* 게시글 상태 (ACTIVE, COMPLETED) 변경하기 */
    @Transactional
    public Long updateBoardStatus(Long boardId, BoardStatus newStatus) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new EntityNotFoundException("게시글을 찾을 수 없습니다: id=" + boardId));

        board.updateStatus(newStatus); // 엔티티 내 메서드로 깔끔하게 처리
        return board.getId();
    }

    /* 게시글 삭제하기 (status를 DELETED로 변경) */
    @Transactional
    public Long deleteBoard(Long boardId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new EntityNotFoundException("게시글을 찾을 수 없습니다: id=" + boardId));

        board.updateStatus(BoardStatus.DELETED);
        return board.getId();
    }

    /* 게시글 필터링 조회하기
     * 필터링 조회 시에도 페이지 단위로 가져오도록 했습니다.
    */
    @Transactional(readOnly = true)
    public Page<BoardSimpleResponse> filterBoards(Category category, BoardFilterRequest request, Pageable pageable) {
        Page<Board> boardPage = boardRepository.findAllByDynamicFilter(
                category,
                request.status(),
                request.itemTypeId(),
                request.locationId(),
                request.startDate(),
                request.endDate(),
                pageable
        );

        return boardPage.map(BoardSimpleResponse::from);
    }
        

//     @Transactional(readOnly = true)
//     public List<BoardSimpleResponse> filterBoards(Category category, BoardFilterRequest request) {
//         List<Board> boards = boardRepository.findAllByDynamicFilter(
//                 category,
//                 request.status(),
//                 request.itemTypeId(),
//                 request.locationId(),
//                 request.startDate(),
//                 request.endDate()
//         );

//         return boards.stream()
//                 .map(BoardSimpleResponse::from)
//                 .toList();
//     }
        
}
