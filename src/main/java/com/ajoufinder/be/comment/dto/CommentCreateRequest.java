package com.ajoufinder.be.comment.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import io.swagger.v3.oas.annotations.media.Schema;


@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Schema(description = "댓글 작성 요청 DTO")
public record CommentCreateRequest(
    Long userId,
    String content,
    Boolean isSecret,
    Long parentCommentId // null이면 일반 댓글, 존재하면 대댓글
) {}
