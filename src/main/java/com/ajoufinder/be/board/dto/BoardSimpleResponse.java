package com.ajoufinder.be.board.dto;

import com.ajoufinder.be.board.domain.Board;
import com.ajoufinder.be.board.domain.constant.BoardStatus;
//import com.fasterxml.jackson.databind.PropertyNamingStrategies;
//import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
//import jakarta.validation.constraints.NotBlank;
//import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
@Schema(description = "게시글 목록 응답 DTO")
public record BoardSimpleResponse(
    @Schema(description = "게시글 ID") Long boardId,
    @Schema(description = "제목") String title,
    @Schema(description = "작성자 닉네임") String nickname,
    @Schema(description = "등록일") LocalDateTime createdAt,
    @Schema(description = "게시글 상태") BoardStatus status
) {
    public static BoardSimpleResponse from(Board board) {
        return new BoardSimpleResponse(
            board.getId(),
            board.getTitle(),
            board.getUser().getNickname(),
            board.getCreatedAt(),
            board.getStatus()
        );
    }
}
