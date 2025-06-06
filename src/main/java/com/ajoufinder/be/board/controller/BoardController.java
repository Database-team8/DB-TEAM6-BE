package com.ajoufinder.be.board.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import com.ajoufinder.be.board.domain.constant.BoardStatus;
import com.ajoufinder.be.board.domain.constant.Category;
import com.ajoufinder.be.board.dto.Request.BoardCreateRequest;
import com.ajoufinder.be.board.dto.Request.BoardFilterRequest;
import com.ajoufinder.be.board.dto.Request.BoardUpdateRequest;
import com.ajoufinder.be.board.dto.Response.BoardDetailResponse;
import com.ajoufinder.be.board.dto.Response.BoardSimpleResponse;
import com.ajoufinder.be.board.service.BoardService;
import com.ajoufinder.be.global.api_response.ApiResponse;
import com.ajoufinder.be.global.domain.UserPrincipal;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;



@Tag(name = "Board", description = "게시글 관련 API")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/boards")
public class BoardController {
    private final BoardService boardService;

    @Operation(
            summary = "분실물 게시글 생성",
            description = """
        새 분실물 게시글을 등록합니다. 다음 필드는 필수입니다:
        - title: 게시글 제목
        - description: 게시글 내용
        - item_type_id: 분실물 종류 id
        - location_id: 발견 위치 id

        다음 필드는 선택적으로 입력해 주세요.
        - detailed_location: 상세 위치
        - related_date: 발견/습득 일시
        - image: 이미지 url
        """
    )
    @PostMapping( "/lost")
    public ApiResponse<Long> createLostBoard(@AuthenticationPrincipal UserPrincipal principal, @Valid @RequestBody BoardCreateRequest request) {
        Long boardId = boardService.createLostBoard(principal.getUser(), request).getId();
        return ApiResponse.onSuccess(boardId);
    }

    @Operation(
            summary = "습득물 게시글 생성",
            description = """
        새 습득물 게시글을 등록합니다. 다음 필드는 필수입니다:
        - user_id: 게시글 작성자 id
        - title: 게시글 제목
        - description: 게시글 내용
        - item_type_id: 습득물 종류 id
        - location_id: 발견 위치 id
        """
    )
    @PostMapping( "/found")
    public ApiResponse<Long> createFoundBoard(@AuthenticationPrincipal UserPrincipal principal, @Valid @RequestBody BoardCreateRequest request) {
        Long boardId = boardService.createFoundBoard(principal.getUser(), request).getId();
        return ApiResponse.onSuccess(boardId);
    }


    @Operation(
            summary = "게시글 수정",
            description = """
                기존 게시글을 수정합니다. 수정 가능한 필드는 아래와 같습니다:
                
                [필수]
                - title: 게시글 제목
                - location_id: 게시글 관련 위치 ID
                - item_type_id: 게시글 관련 물건 종류 ID
                - description: 게시글 본문 (상세 설명)

                [선택]
                - detailed_location: 상세 위치 정보
                - image: 이미지 URL
                - related_date: 분실/발견 일시
            """
    )
    @PatchMapping("/{boardId}")
    public ApiResponse<Void> updateBoard(@AuthenticationPrincipal UserPrincipal principal, @PathVariable("boardId") Long boardId, @Valid @RequestBody BoardUpdateRequest request) {
        boardService.updateBoard(principal.getUser(), boardId, request);
        return ApiResponse.onSuccess();
    }

    @Operation(summary = "분실 게시글 목록 조회")
    @GetMapping("/lost")
    public ResponseEntity<Page<BoardSimpleResponse>> getLostBoards(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<BoardSimpleResponse> result = boardService.getBoardsByCategory(Category.LOST, pageable);
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "습득 게시글 목록 조회")
    @GetMapping("/found")
    public ResponseEntity<Page<BoardSimpleResponse>> getFoundBoards(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<BoardSimpleResponse> result = boardService.getBoardsByCategory(Category.FOUND, pageable);
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "특정 사용자가 작성한 게시글 조회")
    @GetMapping("/user")
    public ResponseEntity<Page<BoardSimpleResponse>> getBoardsByUser(
        @AuthenticationPrincipal UserPrincipal principal,
        @RequestParam(value = "page", defaultValue = "0") int page,
        @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<BoardSimpleResponse> result = boardService.getBoardsByUser(principal.getUser().getId(), pageable);
        return ResponseEntity.ok(result);
    }


    @Operation(summary = "게시글 상세 조회")
    @GetMapping("/{boardId}")
    public ApiResponse<BoardDetailResponse> getBoardDetail(@PathVariable("boardId") Long boardId) {
        return ApiResponse.onSuccess(boardService.getBoardDetail(boardId));
    }

    @Operation(summary = "게시글 상태를 ACTIVE로 변경", description = "게시글의 상태를 ACTIVE로 설정합니다.")
    @PatchMapping("/{boardId}/active")
    public ApiResponse<Long> updateBoardStatusToActive(@AuthenticationPrincipal UserPrincipal principal, @PathVariable("boardId") Long boardId) {
        Long updatedBoardId = boardService.updateBoardStatus(principal.getUser(), boardId, BoardStatus.ACTIVE);
        return ApiResponse.onSuccess(updatedBoardId);
    }

    @Operation(summary = "게시글 상태를 COMPLETED로 변경", description = "게시글의 상태를 COMPLETED로 설정합니다.")
    @PatchMapping("/{boardId}/completed")
    public ApiResponse<Long> updateBoardStatusToCompleted(@AuthenticationPrincipal UserPrincipal principal, @PathVariable("boardId") Long boardId) {
        Long updatedBoardId = boardService.updateBoardStatus(principal.getUser(), boardId, BoardStatus.COMPLETED);
        return ApiResponse.onSuccess(updatedBoardId);
    }

    @Operation(summary = "게시글 삭제", description = "게시글 상태를 DELETED로 변경합니다.")
    @DeleteMapping("/{boardId}")
    public ApiResponse<Long> deleteBoard(@AuthenticationPrincipal UserPrincipal principal, @PathVariable("boardId") Long boardId) {
        Long deletedBoardId = boardService.deleteBoard(principal.getUser(), boardId);
        return ApiResponse.onSuccess(deletedBoardId);
    }

    @Operation(
        summary = "분실 게시글 필터링 조회",
        description = """
            필터링 할 수 있는 속성은 다음과 같습니다:
            - status: 게시글 상태 (ACTIVE, COMPLETED)
            - itemTypeId: 분실물 종류 id
            - locationId: 위치 정보 id
            - startDate: 필터링 기간 시작 일시
            - endDate: 필터링 기간 끝 일시

            URL의 RequestParam 방식으로 필터링 정보를 넘겨 줍니다.
            - ex1) /boards/lost/filter?status=ACTIVE&locationId=3&startDate=2025-05-15&endDate=2025-05-19
            - ex2) /boards/lost/filter?itemTypeId=2
            - ex3) /boards/lost/filter?status=COMPLETED&itemTypeId=2
        """
    )
    @GetMapping("/lost/filter")
    public ResponseEntity<Page<BoardSimpleResponse>> filterLostBoards(
        @ModelAttribute BoardFilterRequest request,
        @RequestParam(value = "page", defaultValue = "0") int page,
        @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<BoardSimpleResponse> result = boardService.filterBoards(Category.LOST, request, pageable);
        return ResponseEntity.ok(result);
    }

    @Operation(
        summary = "습득 게시글 필터링 조회",
        description = """
            필터링 할 수 있는 속성은 다음과 같습니다:
            - status: 게시글 상태 (ACTIVE, COMPLETED)
            - itemTypeId: 분실물 종류 id
            - locationId: 위치 정보 id
            - startDate: 필터링 기간 시작 일시
            - endDate: 필터링 기간 끝 일시

            URL의 RequestParam 방식으로 필터링 정보를 넘겨 줍니다.
            - ex1) /boards/found/filter?status=ACTIVE&locationId=3&startDate=2025-05-15&endDate=2025-05-19
            - ex2) /boards/found/filter?itemTypeId=2
            - ex3) /boards/found/filter?status=COMPLETED&itemTypeId=2
        """)
    @GetMapping("/found/filter")
    public ResponseEntity<Page<BoardSimpleResponse>> filterFoundBoards(
        @ModelAttribute BoardFilterRequest request,
        @RequestParam(value = "page", defaultValue = "0") int page,
        @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<BoardSimpleResponse> result = boardService.filterBoards(Category.FOUND, request, pageable);
        return ResponseEntity.ok(result);
    }
    // public ApiResponse<List<BoardSimpleResponse>> filterFoundBoards(BoardFilterRequest request) {
    //     return ApiResponse.onSuccess(boardService.filterBoards(Category.FOUND, request));
    // }
}
