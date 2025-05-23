package com.ajoufinder.be.comment.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ajoufinder.be.comment.dto.CommentCreateRequest;
import com.ajoufinder.be.comment.dto.CommentCreateResponse;
import com.ajoufinder.be.comment.dto.CommentResponse;
import com.ajoufinder.be.comment.dto.CommentUpdateRequest;
import com.ajoufinder.be.comment.service.CommentService;

@Tag(name = "Comment", description = "댓글 관련 API")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/boards/{boardId}/comments")
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
    @PostMapping
    public ResponseEntity<CommentCreateResponse> createComment(
        @PathVariable("boardId") Long boardId,
        @RequestBody CommentCreateRequest request
    ) {
        CommentCreateResponse response = commentService.createComment(boardId, request);
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
    @PatchMapping("/{commentId}")
    public ResponseEntity<CommentCreateResponse> updateComment(
        @PathVariable("boardId") Long boardId,
        @PathVariable("commentId") Long commentId,
        @RequestBody CommentUpdateRequest request
    ) {
        return ResponseEntity.ok(commentService.updateComment(boardId, commentId, request));
    }

    @Operation(
            summary = "댓글 삭제",
            description = "댓글을 삭제합니다. 상태를 DELETED로 변경합니다."
    )
    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(
        @PathVariable("boardId") Long boardId,
        @PathVariable("commentId") Long commentId
    ) {
        commentService.deleteComment(boardId, commentId);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "댓글 조회",
            description = "특정 게시글의 댓글을 조회합니다."
    )
    @GetMapping()
    public ResponseEntity<Page<CommentResponse>> getComments(
        @PathVariable("boardId") Long boardId,
        @RequestParam(value = "page", defaultValue = "0") int page,
        @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<CommentResponse> result = commentService.getParentComments(boardId, pageable);
        return ResponseEntity.ok(result);
    }
    // public ResponseEntity<List<CommentResponse>> getComments(@PathVariable Long boardId) {
    //     List<CommentResponse> responses = commentService.getCommentsByBoard(boardId);
    //     return ResponseEntity.ok(responses);
    // }

}
