package com.ajoufinder.be.location_condition_mapping.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "LocationConditionMapping", description = "위치조건매핑 관련 API")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/locationConditionMapping")
public class LocationConditionMappingController {
}
