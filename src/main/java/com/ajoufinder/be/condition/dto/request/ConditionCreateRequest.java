package com.ajoufinder.be.condition.dto.request;

import com.ajoufinder.be.condition.domain.Condition;
import com.ajoufinder.be.item_type.domain.ItemType;
import com.ajoufinder.be.location.domain.Location;
import com.ajoufinder.be.user.domain.User;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Schema(description = "조건 생성 요청 DTO")
public record ConditionCreateRequest(
        @NotBlank(message = "아이템 타입을 선택해주세요.")
        @Schema(description = "조건을 적용할 아이템 타입", example = "1")
        Long itemTypeId,

        @NotBlank(message = "위치를 선택해주세요.")
        @Schema(description = "조건을 적용할 위치", example = "4")
        Long locationId
){
 public Condition toEntity(ItemType itemType, Location location, User user){
     return Condition.builder()
             .user(user)
             .itemType(itemType)
             .location(location)
             .build();
 }
}
