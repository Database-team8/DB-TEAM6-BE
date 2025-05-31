package com.ajoufinder.be.comment.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ajoufinder.be.comment.dto.Request.CommentCreateRequest;
import com.ajoufinder.be.comment.dto.Request.CommentUpdateRequest;
import com.ajoufinder.be.comment.dto.Response.CommentCreateResponse;
import com.ajoufinder.be.comment.dto.Response.CommentResponse;
import com.ajoufinder.be.comment.dto.Response.CommentUserResponse;
import com.ajoufinder.be.comment.service.CommentService;
import com.ajoufinder.be.global.domain.UserPrincipal;

@Tag(name = "Comment", description = "댓글 관련 API")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/comments")
public class CommentController {
    private final CommentService commentService;

    @Operation(
            summary = "댓글 작성",
            description = """
        게시글 또는 다른 댓글에 새 댓글을 생성합니다. 아래 필드는 필수입니다:
        - user_id: 댓글 작성자 id
        - content: 댓글 내용
        - is_secret: 비밀댓글 여부 (T/F)
        - parent_comment_id: (대댓글 대상) 부모 댓글 id / 대댓글이 아니라면 null
        """
    )
    @PostMapping("/{boardId}")
    public ResponseEntity<CommentCreateResponse> createComment(
        @AuthenticationPrincipal UserPrincipal principal,
        @PathVariable("boardId") Long boardId,
        @RequestBody CommentCreateRequest request
    ) {
        CommentCreateResponse response = commentService.createComment(principal.getUser(), boardId, request);
        return ResponseEntity.ok(response);
    }

    
    @Operation(
            summary = "댓글 수정",
            description = """
        댓글을 수정합니다. 생성합니다. 아래 필드는 필수입니다:
        - user_id: 댓글 작성자 id
        - content: 댓글 내용
        - is_secret: 비밀댓글 여부 (T/F)
        """
    )
    @PatchMapping("/{boardId}/{commentId}")
    public ResponseEntity<CommentCreateResponse> updateComment(
        @AuthenticationPrincipal UserPrincipal principal,
        @PathVariable("boardId") Long boardId,
        @PathVariable("commentId") Long commentId,
        @RequestBody CommentUpdateRequest request
    ) {
        return ResponseEntity.ok(commentService.updateComment(principal.getUser(), boardId, commentId, request));
    }


    @Operation(
            summary = "댓글 삭제",
            description = "댓글을 삭제합니다. 상태를 DELETED로 변경합니다."
    )
    @DeleteMapping("/{boardId}/{commentId}")
    public ResponseEntity<Void> deleteComment(
        @AuthenticationPrincipal UserPrincipal principal,
        @PathVariable("boardId") Long boardId,
        @PathVariable("commentId") Long commentId
    ) {
        commentService.deleteComment(principal.getUser(), boardId, commentId);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "댓글 조회",
            description = """
            특정 게시글의 댓글을 조회합니다.
            현재 로그인한 사용자와 댓글의 작성자가 다르고, isSecret 값이 True라면 댓글의 내용은 "비밀 댓글입니다."로 바뀌어 반환됩니다.
            """
    )
    @GetMapping("/{boardId}")
    public ResponseEntity<Page<CommentResponse>> getComments(
        @AuthenticationPrincipal UserPrincipal principal,
        @PathVariable("boardId") Long boardId,
        @RequestParam(value = "page", defaultValue = "0") int page,
        @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<CommentResponse> result = commentService.getParentComments(principal.getUser(), boardId, pageable);
        return ResponseEntity.ok(result);
    }
    // public ResponseEntity<List<CommentResponse>> getComments(@PathVariable Long boardId) {
    //     List<CommentResponse> responses = commentService.getCommentsByBoard(boardId);
    //     return ResponseEntity.ok(responses);
    // }

    @Operation(
            summary = "사용자별 댓글 조회",
            description = """
            현재 로그인한 사용자가 등록한 댓글을 조회합니다.
            relatedContent 속성은 댓글이 등록된 요소의 내용입니다.
            게시글의 댓글: 게시글의 제목
            댓글의 대댓글: 부모 댓글의 내용
            """
    )
    @GetMapping("/user")
    public ResponseEntity<Page<CommentUserResponse>> getCommentsByUser(
            @AuthenticationPrincipal UserPrincipal principal,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<CommentUserResponse> result = commentService.getUserComments(principal.getUser().getId(), pageable);
        return ResponseEntity.ok(result);
    }
}
