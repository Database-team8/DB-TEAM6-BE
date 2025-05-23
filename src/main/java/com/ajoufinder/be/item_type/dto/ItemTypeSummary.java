package com.ajoufinder.be.item_type.dto;

import com.ajoufinder.be.item_type.domain.ItemType;
import com.ajoufinder.be.item_type.domain.constant.ItemTypeName;

public record ItemTypeSummary(
    Long itemTypeId,
    ItemTypeName itemType
) {
    public static ItemTypeSummary from(ItemType itemType) {
        return new ItemTypeSummary(
            itemType.getId(),
            itemType.getItemType()
        );
    }
}