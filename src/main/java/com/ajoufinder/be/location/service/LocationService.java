package com.ajoufinder.be.location.service;

import com.ajoufinder.be.global.api_response.exception.GeneralException;
import com.ajoufinder.be.global.api_response.status.ErrorStatus;
import com.ajoufinder.be.location.domain.Location;
import com.ajoufinder.be.location.dto.response.LocationResponse;
import com.ajoufinder.be.location.repository.LocationRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LocationService {
    private final LocationRepository locationRepository;

    public List<LocationResponse> getAllLocations() {
        return locationRepository.findAll()
                .stream()
                .map(LocationResponse::from)
                .toList();
    }
    public Location getLocationByIdOrThrow(Long locationId) {
        return locationRepository.findById(locationId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.NOT_FOUND,"존재하지 않는 Location입니다."));
    }


}
