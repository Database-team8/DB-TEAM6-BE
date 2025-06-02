package com.ajoufinder.be.comment.dto.Response;

import com.ajoufinder.be.comment.domain.Comment;
import com.ajoufinder.be.comment.domain.constant.CommentStatus;
import com.ajoufinder.be.user.dto.response.UserResponse;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
// import java.util.List;
// import java.util.stream.Collectors;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Schema(description = "사용자별 댓글 응답 DTO")
public record CommentUserResponse(
    @Schema(description = "댓글 id", example = "1")
    Long commentId,

    @Schema(description = "부모 댓글 id", example = "1")
    Long parentId,

    @Schema(description = "댓글을 단 게시글 id", example = "1")
    Long boardId,

    @Schema(description = "댓글을 단 게시글의 제목 또는 댓글의 내용", example = "다산관에서 지갑을 잃어버렸어요")
    String relatedContent,

    @Schema(description = "댓글 내용", example = "이 휴대폰 성호관 편의점에서 맡고 있습니다.")
    String content,

    @Schema(description = "비밀댓글 여부", example = "true")
    Boolean isSecret,

    @Schema(description = "댓글 작성시간", example = "2024-05-01T15:30:00")
    LocalDateTime createdAt,

    @Schema(description = "댓글 수정시간", example = "2024-05-01T15:30:00")
    LocalDateTime updatedAt

    // @Schema(description = "댓글 작성자 요약정보")
    // UserResponse user,

    // @Schema(description = "대댓글 목록")
    // List<CommentResponse> childComments
) {
    public static CommentUserResponse from(Comment comment, String relatedContent) {
        return new CommentUserResponse(
            comment.getId(),
            comment.getParentComment() != null ? comment.getParentComment().getId() : null,
            comment.getBoard().getId(),
            relatedContent,
            comment.getContent(),
            comment.getIsSecret(),
            comment.getCreatedAt(),
            comment.getUpdatedAt()
        );
    }
}
