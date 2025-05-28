package com.ajoufinder.be.location.controller;

import com.ajoufinder.be.global.api_response.ApiResponse;
import com.ajoufinder.be.location.dto.response.LocationResponse;
import com.ajoufinder.be.location.service.LocationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Location", description = "위치 관련 API")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/locations")
public class LocationController {
    private final LocationService locationService;

    @Operation(
            summary = "모든 위치 조회",
            description = "등록된 모든 위치 정보를 조회합니다."
    )
    @GetMapping
    public ApiResponse<List<LocationResponse>> getAllLocations() {
        List<LocationResponse> locations = locationService.getAllLocations();
        return ApiResponse.onSuccess(locations);
    }
}
