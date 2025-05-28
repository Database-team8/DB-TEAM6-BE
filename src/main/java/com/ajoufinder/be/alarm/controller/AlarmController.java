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
    //TODO: 알림 보내기(본인 게시글에 댓글이 달렸을 때, 본인 댓글에 대댓글이 달렸을 때, 설정한 조건에 맞는 게시글이 등록되었을 때)
    //TODO: 알림 정보 생성하기 (댓글 달렸을 때, 게시글 등록됐을 때 동적으로 생성)
    //TODO: 

    private final AlarmService alarmService;

    @Operation(
            summary = "알림 목록 조회",
            description = "사용자에게 보내진 알림을 조회합니다. RequestParam 방식으로 userId를 전송합니다."
    )
    @GetMapping()
    public List<AlarmResponse> getAlarms(@AuthenticationPrincipal UserPrincipal principal) {
        return alarmService.getAlarmsByUserId(principal.getUser().getId());
    }
}
