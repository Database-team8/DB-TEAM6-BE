package com.ajoufinder.be.board.dto;

//import com.ajoufinder.be.board.domain.constant.Category;
//import com.ajoufinder.be.board.domain.constant.BoardStatus;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Schema(description = "게시글 수정 요청 DTO")
public record BoardUpdateRequest(
        @Schema(description = "게시글 제목", example = "지갑을 분실했습니다.")
        @NotBlank String title,

        @Schema(description = "발견/분실된 위치 id", example = "2")
        @NotNull Long locationId,

        @Schema(description = "분실물 종류 id", example = "3")
        @NotNull Long itemTypeId,

        @Schema(description = "상세 설명", example = "학생회관에서 검정색 지갑을 잃어버렸어요.")
        @NotBlank String description,

        @Schema(description = "상세 위치", example = "학생회관 1층")
        String detailedLocation,

        @Schema(description = "이미지 URL", example = "https://cdn.ajoufinder.com/images/wallet.png")
        String image,

        @Schema(description = "관련된 날짜", example = "2025-05-14T10:15:00")
        LocalDateTime relatedDate

        //@Schema(description = "게시글 상태 (ACTIVE, COMPLETED, DELETED)", example = "ACTIVE")
        //@NotNull BoardStatus status

        //@Schema(description = "게시글 유형 (LOST, FOUND)", example = "LOST")
        //@NotNull Category category
        //게시글 유형은 변경할 수 없도록 설정
) { }
