package com.ajoufinder.be.board.service;

import com.ajoufinder.be.alarm.domain.constant.AlarmTarget;
import com.ajoufinder.be.alarm.domain.constant.AlarmType;
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
import com.ajoufinder.be.board.dto.Request.BoardCreateRequest;
import com.ajoufinder.be.board.dto.Request.BoardFilterRequest;
import com.ajoufinder.be.board.dto.Request.BoardUpdateRequest;
import com.ajoufinder.be.board.dto.Response.BoardDetailResponse;
import com.ajoufinder.be.board.dto.Response.BoardSimpleResponse;
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

    @Transactional
    @AlarmTarget(AlarmType.FOUND_CREATED)
    public Board createFoundBoard(User loginUser, BoardCreateRequest request) {
        return createBoardWithCategory(loginUser, request, Category.FOUND);
    }

    @Transactional
    public Board createLostBoard(User loginUser, BoardCreateRequest request) {
        return createBoardWithCategory(loginUser, request, Category.LOST);
    }

    private Board createBoardWithCategory(User loginUser, BoardCreateRequest request, Category category) {
        User user = userRepository.findById(loginUser.getId())
                .orElseThrow(() -> new EntityNotFoundException("유저 정보를 찾을 수 없습니다."));

        ItemType itemType = itemTypeRepository.findById(request.itemTypeId())
                .orElseThrow(() -> new EntityNotFoundException("물품 종류를 찾을 수 없습니다."));

        Location location = null;
        if (request.locationId() != null) {
            location = locationRepository.findById(request.locationId())
                    .orElseThrow(() -> new EntityNotFoundException("위치 정보를 찾을 수 없습니다."));
        }

        Board board = request.toEntity(user, itemType, location, category);
        boardRepository.save(board);

        /*
        * alarmService.notify(user, "조건에 맞는 게시글이 생성됨", board.getTitle(), "board/"+board.getId());
        * 
        * 알림 생성 예시 코드입니다. 위 코드를 수행하기 전, 작성된 게시글에 따른 알림 대상을 뽑고,
        * 그 유저에 대해 알림을 추가하면 됩니다. 댓글 생성에서도 동일함.
        */
        return board;
    }

    /* 게시글 수정 메서드 */
    /* User 정보는 따로 DTO에서 받지 않는 것으로 구현했습니다.
     * BoardRepository 속 User 정보 이외의 부분만 업데이트 합니다.
     * 프론트단에서 게시글을 수정하려는 사용자는 게시글 작성자라는 것을 보장해야 함.
     */
    @Transactional
    public Long updateBoard(User loginUser, Long boardId, BoardUpdateRequest request) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));

        if(!board.getUser().equals(loginUser)){
            throw new RuntimeException("게시글 수정은 게시글의 작성자만 할 수 있습니다.");
        }

        Location location = locationRepository.findById(request.locationId())
                .orElseThrow(() -> new IllegalArgumentException("위치 정보를 찾을 수 없습니다."));

        ItemType itemType = itemTypeRepository.findById(request.itemTypeId())
                .orElseThrow(() -> new IllegalArgumentException("물건 종류를 찾을 수 없습니다."));

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

    /* 사용자가 작성한 게시글 조회하기 */
    @Transactional(readOnly = true)
    public Page<BoardSimpleResponse> getBoardsByUser(Long userId, Pageable pageable) {
        return boardRepository.findByUserIdAndStatus(userId, BoardStatus.ACTIVE, pageable)
            .map(BoardSimpleResponse::from);
    }


    /* 특정 게시글의 상세 정보 조회하기 */
    public BoardDetailResponse getBoardDetail(Long boardId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("Board not found."));

        if (board.getStatus().equals(BoardStatus.DELETED)) {
            throw new RuntimeException("삭제된 게시글은 조회할 수 없습니다.");
        }

        return BoardDetailResponse.from(board);
    }

    /* 게시글 상태 (ACTIVE, COMPLETED) 변경하기 */
    @Transactional
    public Long updateBoardStatus(User loginUser, Long boardId, BoardStatus newStatus) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new EntityNotFoundException("게시글을 찾을 수 없습니다: id=" + boardId));

        if (!loginUser.getId().equals(board.getUser().getId())) {
            throw new RuntimeException("게시글 작성자만 게시글 상태를 변경할 수 있습니다.");
        }

        board.updateStatus(newStatus);
        return board.getId();
    }

    /* 게시글 삭제하기 (status를 DELETED로 변경) */
    @Transactional
    public Long deleteBoard(User loginUser, Long boardId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new EntityNotFoundException("게시글을 찾을 수 없습니다: id=" + boardId));

        if (!loginUser.getId().equals(board.getUser().getId())) {
            throw new RuntimeException("게시글 작성자만 게시글을 삭제할 수 있습니다.");
        }

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
        
}
