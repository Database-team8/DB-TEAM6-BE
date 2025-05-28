package com.ajoufinder.be.board.dto.Response;

import com.ajoufinder.be.board.domain.Board;
import com.ajoufinder.be.board.domain.constant.Category;
import com.ajoufinder.be.board.domain.constant.BoardStatus;
import com.ajoufinder.be.item_type.dto.response.ItemTypeResponse;
import com.ajoufinder.be.location.dto.response.LocationResponse;
import com.ajoufinder.be.user.dto.response.UserResponse;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Schema(description = "게시글 상세 응답 DTO")
public record BoardDetailResponse(
    @Schema(description = "게시글 id", example = "1")
    Long boardId,
    
    @Schema(description = "게시글 제목", example = "제 지갑을 잃어버렸어요")
    String title,
    
    @Schema(description = "게시글 내용", example = "갈색 반지갑입니다. 안에 신분증이 들어 있어요")
    String description,

    @Schema(description = "분실물/습득물 이미지 url", example = "https://cdn.ajoufinder.com/images/wallet.png")
    String image,

    @Schema(description = "분실/습득 상세 위치 정보", example = "산학협력원 1층 로비")
    String detailedLocation,

    @Schema(description = "게시글 작성자 요약정보. 사용자 ID, 이름, 닉네임, 프로필 이미지 url이 포함됨")
    UserResponse user,

    @Schema(description = "게시글의 상태", example = "ACTIVE/COMPLETED")
    BoardStatus status,

    @Schema(description = "게시글의 종류", example = "FOUND/LOST")
    Category category,

    @Schema(description = "분실물의 종류 정보. 종류의 id와 이름이 포함됨.")
    ItemTypeResponse itemType,

    @Schema(description = "분실/습득 위치 정보. 위치의 id와 이름이 포함됨.")
    LocationResponse location,

    @Schema(description = "게시글 생성 시간", example = "2024-05-01T15:30:00")
    LocalDateTime createdAt,

    @Schema(description = "게시글 수정 시간", example = "2024-05-01T15:30:00")
    LocalDateTime updatedAt
) {
    public static BoardDetailResponse from(Board board) {
        return new BoardDetailResponse(
            board.getId(),
            board.getTitle(),
            board.getDescription(),
            board.getImage(),
            board.getDetailedLocation(),
            UserResponse.from(board.getUser()),
            board.getStatus(),
            board.getCategory(),
            ItemTypeResponse.from(board.getItemType()),
            LocationResponse.from(board.getLocation()),
            board.getCreatedAt(),
            board.getUpdatedAt()
        );
    }
}
