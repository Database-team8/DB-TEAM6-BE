package com.ajoufinder.be.location.repository.init;

import com.ajoufinder.be.global.util.DummyDataInit;
import com.ajoufinder.be.location.domain.Location;
import com.ajoufinder.be.location.domain.constant.LocationName;
import com.ajoufinder.be.location.repository.LocationRepository;
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
public class LocationInitializer implements ApplicationRunner {

    private final LocationRepository locationRepository;

    @Override
    public void run(ApplicationArguments args) {
        log.info("[LocationInitializer] 초기화 시작");

        if (locationRepository.count() > 0) {
            log.info("[Location] 더미 데이터 존재");
        } else {
            List<Location> locations = Arrays.stream(LocationName.values())
                    .map(typeName -> Location.builder().locationName(typeName).build())
                    .toList();

            locationRepository.saveAll(locations);
            log.info("[LocationInitializer] 모든 LocationName에 대해 더미 데이터 삽입 완료");
        }
    }
}
