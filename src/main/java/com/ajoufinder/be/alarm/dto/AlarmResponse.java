package com.ajoufinder.be.alarm.dto;

import java.time.LocalDateTime;

import com.ajoufinder.be.alarm.domain.Alarm;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import io.swagger.v3.oas.annotations.media.Schema;


@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Schema(description = "알림 응답 DTO")
public record AlarmResponse(
    @Schema(description = "알림 id", example = "1")
    Long id,

    @Schema(description = "알림 내용", example = "작성하신 게시글에 새로운 댓글이 달렸습니다.")
    String content,

    @Schema(description = "알림이 발생한 게시글의 id", example = "1")
    Long relatedBoardId,

    @Schema(description = "알림 읽음 여부", example = "false")
    boolean isRead,

    @Schema(description = "알림 발생 일시", example = "2024-05-01T15:30:00")
    LocalDateTime createdAt,

    @Schema(description = "알림 수정 일시", example = "2024-05-01T15:30:00")
    LocalDateTime updatedAt
) {
    public static AlarmResponse from(Alarm alarm) {
        return new AlarmResponse(
            alarm.getId(),
            alarm.getContent(),
            alarm.getRelatedBoardId(),
            alarm.getIsRead(),
            alarm.getCreatedAt(),
            alarm.getUpdatedAt()
        );
    }
}