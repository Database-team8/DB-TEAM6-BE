package com.ajoufinder.be.alarm.domain;

import com.ajoufinder.be.alarm.domain.constant.AlarmTarget;
import com.ajoufinder.be.alarm.domain.constant.AlarmType;
import com.ajoufinder.be.alarm.service.AlarmService;
import com.ajoufinder.be.board.domain.Board;
import com.ajoufinder.be.board.dto.Request.BoardCreateRequest;
import com.ajoufinder.be.board.repository.BoardRepository;
import com.ajoufinder.be.condition.domain.Condition;
import com.ajoufinder.be.condition.repository.ConditionRepository;
import com.ajoufinder.be.user.domain.User;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class AlarmAspect {

    private final AlarmService alarmService;
    private final ConditionRepository conditionRepository;
    private final BoardRepository boardRepository;

    @AfterReturning(value = "@annotation(alarmTarget)", returning = "result")
    public void handleAlarm(JoinPoint joinPoint, AlarmTarget alarmTarget, Object result) {
        AlarmType type = alarmTarget.value();

        switch (type) {
            case COMMENT_CREATED -> handleCommentAlarm(joinPoint);
            case FOUND_CREATED -> handleFoundBoardAlarm(joinPoint, result);
        }
    }

    private void handleCommentAlarm(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        User loginUser = (User) args[0];
        Long boardId = (Long) args[1];

        Board board = boardRepository.findById(boardId).orElse(null);
        if (board != null && !board.getUser().getId().equals(loginUser.getId())) {
            alarmService.notify(
                    board.getUser(),
                    board.getId(),
                    "작성하신 게시글에 댓글이 달렸습니다."
            );
        }
    }

    private void handleFoundBoardAlarm(JoinPoint joinPoint, Object boardObj) {
        Object[] args = joinPoint.getArgs();
        BoardCreateRequest request = (BoardCreateRequest) args[1];

        Long itemTypeId = request.itemTypeId();
        Long locationId = request.locationId();
        Board board = (Board) boardObj;

        List<Condition> targets = conditionRepository.findByItemTypeIdAndLocationId(itemTypeId, locationId);
        for (Condition c : targets) {
            alarmService.notify(
                    c.getUser(),
                    board.getId(),
                    "설정한 조건에 부합하는 습득물이 등록되었습니다."
            );
        }
    }
}
