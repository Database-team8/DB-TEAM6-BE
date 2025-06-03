package com.ajoufinder.be.condition.dto.response;

import com.ajoufinder.be.condition.domain.Condition;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ConditionResponse {
    private Long condition_id;
    private String item_type;
    private String location;

    public static ConditionResponse from(Condition condition) {
        return new ConditionResponse(
                condition.getId(),
                condition.getItemType().getItemType().name(),
                condition.getLocation().getLocationName().name()
        );
    }
}