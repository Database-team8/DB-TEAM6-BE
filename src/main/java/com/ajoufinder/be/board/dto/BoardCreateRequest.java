package com.ajoufinder.be.board.dto;

import com.ajoufinder.be.board.domain.Board;
import com.ajoufinder.be.board.domain.constant.Category;
import com.ajoufinder.be.board.domain.constant.BoardStatus;
import com.ajoufinder.be.item_type.domain.ItemType;
import com.ajoufinder.be.location.domain.Location;
import com.ajoufinder.be.user.domain.User;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Schema(description = "게시글 생성 요청 DTO")
public record BoardCreateRequest(
    @Schema(description = "작성자 id", example = "1")
    @NotNull Long userId,

    @Schema(description = "제목", example = "지갑을 잃어버렸어요") 
    @NotBlank String title,

    @Schema(description = "상세 위치", example = "중앙도서관 3층 열람실")
    String detailedLocation,

    @Schema(description = "설명", example = "갈색 지갑이고, 안에 학생증이 들어있어요.")
    @NotBlank String description,

    @Schema(description = "분실/발견 날짜", example = "2024-05-01T15:30:00") 
    LocalDateTime relatedDate,

    @Schema(description = "이미지 URL", example = "https://cdn.ajoufinder.com/images/wallet.jpg") 
    String image,

    @Schema(description = "상태 (ACTIVE, COMPLETED, DELETED)", example = "ACTIVE") 
    @NotNull BoardStatus status,

    @Schema(description = "게시글의 종류(LOST, FOUND)", example = "LOST") 
    @NotNull Category category,

    @Schema(description = "아이템 타입 ID", example = "1")
    @NotNull Long itemTypeId,

    @Schema(description = "위치 ID", example = "3")
    @NotNull Long locationId
) {
    public Board toEntity(User userId, ItemType itemType, Location location) {
        return Board.builder()
                .title(this.title())
                .user(userId)
                .location(location)
                .detailedLocation(this.detailedLocation())
                .description(this.description())
                .relatedDate(this.relatedDate())
                .image(this.image())
                .status(this.status())
                .category(this.category())
                .itemType(itemType)
                .build();
    }
}
