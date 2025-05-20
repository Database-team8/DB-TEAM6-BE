package com.ajoufinder.be.board.repository;

import com.ajoufinder.be.board.domain.Board;
import com.ajoufinder.be.board.domain.constant.BoardStatus;
import com.ajoufinder.be.board.domain.constant.Category;

import java.time.LocalDate;
import java.util.List;


public interface BoardRepositoryCustom {
    List<Board> findAllByDynamicFilter(
            Category category,
            BoardStatus status,
            Long itemTypeId,
            Long locationId,
            LocalDate startDate,
            LocalDate endDate
    );
}