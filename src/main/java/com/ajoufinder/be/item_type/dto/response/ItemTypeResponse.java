package com.ajoufinder.be.item_type.dto.response;

import com.ajoufinder.be.item_type.domain.ItemType;
import com.ajoufinder.be.item_type.domain.constant.ItemTypeName;

public record ItemTypeResponse(
    Long itemTypeId,
    ItemTypeName itemType
) {
    public static ItemTypeResponse from(ItemType itemType) {
        return new ItemTypeResponse(
            itemType.getId(),
            itemType.getItemType()
        );
    }
}