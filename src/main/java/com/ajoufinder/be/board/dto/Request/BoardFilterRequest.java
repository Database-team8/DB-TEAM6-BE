package com.ajoufinder.be.board.dto.Request;

import com.ajoufinder.be.board.domain.constant.BoardStatus;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Schema(description = "게시글 필터링 요청 DTO")
public record BoardFilterRequest(
        @Schema(description = "게시글 상태 (ACTIVE, COMPLETED)") BoardStatus status,
        @Schema(description = "물건 종류 ID") Long itemTypeId,
        @Schema(description = "장소 ID") Long locationId,
        @Schema(description = "시작 날짜") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
        @Schema(description = "종료 날짜") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
) {}