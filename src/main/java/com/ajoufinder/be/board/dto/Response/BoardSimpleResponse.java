package com.ajoufinder.be.board.dto.Response;

import com.ajoufinder.be.board.domain.Board;
import com.ajoufinder.be.board.domain.constant.BoardStatus;
import com.ajoufinder.be.board.domain.constant.Category;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

//import com.fasterxml.jackson.databind.PropertyNamingStrategies;
//import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
//import jakarta.validation.constraints.NotBlank;
//import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Schema(description = "게시글 목록 응답 DTO")
public record BoardSimpleResponse(
    @Schema(description = "게시글 ID") Long boardId,
    @Schema(description = "제목") String title,
    @Schema(description = "작성자 닉네임") String nickname,
    @Schema(description = "게시글 종류") Category category,
    @Schema(description = "등록일") LocalDateTime createdAt,
    @Schema(description = "게시글 상태") BoardStatus status,
    @Schema(description = "게시글 이미지") String image
) {
    public static BoardSimpleResponse from(Board board) {
        return new BoardSimpleResponse(
            board.getId(),
            board.getTitle(),
            board.getUser().getNickname(),
            board.getCategory(),
            board.getCreatedAt(),
            board.getStatus(),
            board.getImage()
        );
    }
}
