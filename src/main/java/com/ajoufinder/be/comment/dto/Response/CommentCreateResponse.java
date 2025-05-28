package com.ajoufinder.be.comment.dto.Response;

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
    @Schema(description = "댓글 id", example = "1")
    Long commentId,

    @Schema(description = "댓글 내용", example = "이 노트북 성호관 편의점 앞 테이블에 있어요")
    String content,

    @Schema(description = "비밀댓글 여부", example = "true")
    Boolean isSecret,

    @Schema(description = "댓글 상태. 삭제된 경우 DELETED, 삭제되지 않은 경우 VISIBLE값을 가집니다.", example = "VISIBLE")
    CommentStatus status,

    @Schema(description = "댓글 작성 일시", example = "2024-05-01T15:30:00")
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
