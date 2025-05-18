package com.ajoufinder.be.location.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Location", description = "위치 관련 API")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/location")
public class LocationController {
}
