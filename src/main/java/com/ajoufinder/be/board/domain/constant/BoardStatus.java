package com.ajoufinder.be.board.domain.constant;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BoardStatus {
    ACTIVE,
    COMPLETED,
    DELETED,
    ;
}
