package com.ajoufinder.be.board.dto;

import com.ajoufinder.be.board.domain.Board;
import com.ajoufinder.be.board.domain.constant.Category;
import com.ajoufinder.be.board.domain.constant.BoardStatus;
import com.ajoufinder.be.item_type.dto.ItemTypeSummary;
import com.ajoufinder.be.location.dto.LocationSummary;
import com.ajoufinder.be.user.dto.UserSummary;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Schema(description = "게시글 상세 응답 DTO")
public record BoardDetailResponse(
    Long boardId,
    String title,
    String description,
    String image,
    String detailedLocation,
    UserSummary user,
    BoardStatus status,
    Category category,
    ItemTypeSummary itemType,
    LocationSummary location,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {
    public static BoardDetailResponse from(Board board) {
        return new BoardDetailResponse(
            board.getId(),
            board.getTitle(),
            board.getDescription(),
            board.getImage(),
            board.getDetailedLocation(),
            UserSummary.from(board.getUser()),
            board.getStatus(),
            board.getCategory(),
            ItemTypeSummary.from(board.getItemType()),
            LocationSummary.from(board.getLocation()),
            board.getCreatedAt(),
            board.getUpdatedAt()
        );
    }
}
