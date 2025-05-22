package com.ajoufinder.be.comment.repository;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ajoufinder.be.comment.domain.Comment;
import com.ajoufinder.be.comment.domain.constant.CommentStatus;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long>{
    List<Comment> findByBoardIdAndStatus(Long boardId, CommentStatus status);
    Page<Comment> findByBoardIdAndParentCommentIsNullAndStatus(Long boardId, CommentStatus status, Pageable pageable);

}
