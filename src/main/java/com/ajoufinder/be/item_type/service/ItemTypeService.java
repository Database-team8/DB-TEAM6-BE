package com.ajoufinder.be.item_type.service;

import com.ajoufinder.be.item_type.dto.response.ItemTypeResponse;
import com.ajoufinder.be.item_type.repository.ItemTypeRepository;
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

    public List<ItemTypeResponse> getAllItemTypes() {
        return itemTypeRepository.findAll()
                .stream()
                .map(ItemTypeResponse::from)
                .toList();
    }
}
