package com.ajoufinder.be.item_type.controller;

import com.ajoufinder.be.global.api_response.ApiResponse;
import com.ajoufinder.be.item_type.dto.response.ItemTypeResponse;
import com.ajoufinder.be.item_type.service.ItemTypeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Itemtype", description = "아이템 종류 관련 API")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/itemtypes")

public class ItemTypeController {
    private final ItemTypeService itemTypeService;

    @Operation(
            summary = "모든 아이템 종류 조회",
            description = "등록된 모든 아이템 종류를 조회합니다."
    )
    @GetMapping
    public ApiResponse<List<ItemTypeResponse>> getAllLocations() {
        List<ItemTypeResponse> locations = itemTypeService.getAllItemTypes();
        return ApiResponse.onSuccess(locations);
    }
}
