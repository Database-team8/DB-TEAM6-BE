package com.ajoufinder.be.comment.dto;

import com.ajoufinder.be.comment.domain.Comment;
import com.ajoufinder.be.comment.domain.constant.CommentStatus;
import com.ajoufinder.be.user.dto.response.UserResponse;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Schema(description = "댓글 응답 DTO")
public record CommentResponse(
    Long commentId,
    Long parentId,
    String content,
    Boolean isSecret,
    LocalDateTime createdAt,
    LocalDateTime updatedAt,
    UserResponse user,
    List<CommentResponse> childComments
) {
    public static CommentResponse from(Comment comment) {
        return new CommentResponse(
            comment.getId(),
            comment.getParentComment() != null ? comment.getParentComment().getId() : null,
            comment.getContent(),
            comment.getIsSecret(),
            comment.getCreatedAt(),
            comment.getUpdatedAt(),
            UserResponse.from(comment.getUser()),
            comment.getChildComments().stream()
                .filter(child -> child.getStatus() == CommentStatus.VISIBLE)
                .map(CommentResponse::from)
                .collect(Collectors.toList())
        );
    }
}
