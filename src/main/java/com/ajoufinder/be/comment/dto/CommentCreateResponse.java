package com.ajoufinder.be.comment.dto;

import com.ajoufinder.be.comment.domain.Comment;
import com.ajoufinder.be.comment.domain.constant.CommentStatus;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

//댓글 수정의 응답에서도 그대로 사용함.
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Schema(description = "댓글 작성 반환 DTO")
public record CommentCreateResponse(
    Long commentId,
    String content,
    Boolean isSecret,
    CommentStatus status,
    LocalDateTime createdAt
) {
    public static CommentCreateResponse from(Comment comment) {
        return new CommentCreateResponse(
            comment.getId(),
            comment.getContent(),
            comment.getIsSecret(),
            comment.getStatus(),
            comment.getCreatedAt()
        );
    }
}
