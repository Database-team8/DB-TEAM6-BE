package com.ajoufinder.be.alarm.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ajoufinder.be.alarm.dto.AlarmResponse;
import com.ajoufinder.be.alarm.service.AlarmService;
import com.ajoufinder.be.global.domain.UserPrincipal;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;


@Tag(name = "Alarm", description = "알람 관련 API")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/alarms")
public class AlarmController {
    private final AlarmService alarmService;

    @Operation(
            summary = "알림 목록 조회",
            description = "로그인한 사용자에게 보내진 알림을 조회합니다."
    )
    @GetMapping()
    public List<AlarmResponse> getAlarms(@AuthenticationPrincipal UserPrincipal principal) {
        return alarmService.getAlarmsByUserId(principal.getUser().getId());
    }
}
