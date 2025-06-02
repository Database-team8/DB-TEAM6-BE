package com.ajoufinder.be.item_type.service;

import com.ajoufinder.be.global.api_response.exception.GeneralException;
import com.ajoufinder.be.global.api_response.status.ErrorStatus;
import com.ajoufinder.be.item_type.domain.ItemType;
import com.ajoufinder.be.item_type.dto.response.ItemTypeResponse;
import com.ajoufinder.be.item_type.repository.ItemTypeRepository;
import com.ajoufinder.be.location.domain.Location;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemTypeService {
    private final ItemTypeRepository itemTypeRepository;

    public ItemType getItemTypeByIdOrThrow(Long itemTypeId) {
        return itemTypeRepository.findById(itemTypeId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.NOT_FOUND,"존재하지 않는 Item입니다."));
    }

    public List<ItemTypeResponse> getAllItemTypes() {
        return itemTypeRepository.findAll()
                .stream()
                .map(ItemTypeResponse::from)
                .toList();
    }
}
