package com.ajoufinder.be.board.repository;

import com.ajoufinder.be.board.domain.Board;
import com.ajoufinder.be.board.domain.constant.BoardStatus;
import com.ajoufinder.be.board.domain.constant.Category;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long>, BoardRepositoryCustom{
    //List<Board> findByCategory(Category category);
    Page<Board> findByCategoryAndStatus(Category category, BoardStatus status, Pageable pageable);

}
