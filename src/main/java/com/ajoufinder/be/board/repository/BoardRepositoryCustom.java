package com.ajoufinder.be.board.repository;

import com.ajoufinder.be.board.domain.Board;
import com.ajoufinder.be.board.domain.constant.BoardStatus;
import com.ajoufinder.be.board.domain.constant.Category;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;



public interface BoardRepositoryCustom {
    Page<Board> findAllByDynamicFilter(
            Category category,
            BoardStatus status,
            Long itemTypeId,
            Long locationId,
            LocalDate startDate,
            LocalDate endDate,
            Pageable pageable
    );
}