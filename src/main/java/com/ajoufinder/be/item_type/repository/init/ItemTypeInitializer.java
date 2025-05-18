package com.ajoufinder.be.item_type.repository.init;

import com.ajoufinder.be.global.util.DummyDataInit;
import com.ajoufinder.be.item_type.domain.ItemType;
import com.ajoufinder.be.item_type.domain.constant.ItemTypeName;
import com.ajoufinder.be.item_type.repository.ItemTypeRepository;
import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;

@Slf4j
@RequiredArgsConstructor
@Order(1)
@DummyDataInit
public class ItemTypeInitializer implements ApplicationRunner {

    private final ItemTypeRepository itemTypeRepository;

    @Override
    public void run(ApplicationArguments args) {
        log.info("[ItemTypeInitializer] 초기화 시작");

        List<ItemType> itemTypes = Arrays.stream(ItemTypeName.values())
                .map(typeName -> ItemType.builder().itemType(typeName).build())
                .toList();

        itemTypeRepository.saveAll(itemTypes);
        log.info("[ItemTypeInitializer] 모든 ItemTypeName에 대해 더미 데이터 삽입 완료");
    }
}
