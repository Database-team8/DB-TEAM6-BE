package com.ajoufinder.be.comment.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import io.swagger.v3.oas.annotations.media.Schema;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Schema(description = "댓글 수정 요청 DTO")
public record CommentUpdateRequest(
    Long userId,
    String content,
    Boolean isSecret
) {}