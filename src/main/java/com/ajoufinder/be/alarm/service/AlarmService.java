package com.ajoufinder.be.alarm.service;

import static com.ajoufinder.be.global.api_response.status.ErrorStatus.UNAUTHORIZED;

import com.ajoufinder.be.board.domain.Board;
import com.ajoufinder.be.global.api_response.exception.GeneralException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ajoufinder.be.alarm.domain.Alarm;
import com.ajoufinder.be.alarm.dto.AlarmResponse;
import com.ajoufinder.be.alarm.repository.AlarmRepository;
import com.ajoufinder.be.user.domain.User;
import com.ajoufinder.be.user.repository.UserRepository;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AlarmService{
    private final AlarmRepository alarmRepository;
    private final UserRepository userRepository;

    /* 알림 정보를 생성하고 저장하는 메서드 */
    public void notify(User user,Long boardId, String content) {
        Alarm alarm = Alarm.builder()
                .user(user)
                .content(content)
                .relatedBoardId(boardId)
                .isRead(false)
                .build();
        alarmRepository.save(alarm);
    }

    /* userId 별로 알림 정보를 리스트 형식으로 조회하는 메서드 */
    @Transactional(readOnly = true)
    public List<AlarmResponse> getAlarmsByUserId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 존재하지 않습니다."));

        return alarmRepository.findByUserOrderByCreatedAtDesc(user).stream()
                .map(AlarmResponse::from)
                .toList();
    }


    @Transactional
    public void markAsRead(Long alarmId, Long userId) {
        Alarm alarm = alarmRepository.findById(alarmId)
                .orElseThrow(() -> new IllegalArgumentException("알림이 존재하지 않습니다."));
        if (!alarm.getUser().getId().equals(userId)) {
            throw new GeneralException(UNAUTHORIZED,"본인의 알림만 읽음 처리할 수 있습니다.");
        }
        alarm.markAsRead();
    }

    @Transactional
    public void markAllAsRead(User user) {
        List<Alarm> alarms = alarmRepository.findByUserAndIsReadFalse(user);
        for (Alarm alarm : alarms) {
            alarm.markAsRead();
        }
    }
}
