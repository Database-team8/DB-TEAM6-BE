package com.ajoufinder.be.comment.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import io.swagger.v3.oas.annotations.media.Schema;


@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Schema(description = "댓글 작성 요청 DTO")
public record CommentCreateRequest(
    @Schema(description = "댓글 작성자 ID", example = "1")
    Long userId,

    @Schema(description = "댓글 내용", example = "이 지갑 다산관 프런트에서 맡고 있어요")
    String content,

    @Schema(description = "비밀댓글 여부", example = "false")
    Boolean isSecret,

    @Schema(description = "부모 댓글 id. 대댓글의 경우에만 존재합니다. 대댓글이 아닌 경우, null값을 가집니다.", example = "1")
    Long parentCommentId // null이면 일반 댓글, 존재하면 대댓글
) {}
