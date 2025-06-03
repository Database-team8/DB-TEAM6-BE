package com.ajoufinder.be.comment.dto.Request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Schema(description = "댓글 수정 요청 DTO")
public record CommentUpdateRequest(
    //@Schema(description = "사용자 id", example = "1")
    //Long userId,

    @Schema(description = "댓글 내용", example = "이 지갑 성호관 편의점이 아니라 다산관 편의점에 있어요")
    @NotBlank String content,

    @Schema(description = "비밀댓글 여부", example = "true")
    @NotNull Boolean isSecret
) {}