package com.ajoufinder.be.comment.service;

import com.ajoufinder.be.board.domain.Board;
import com.ajoufinder.be.board.repository.BoardRepository;
import com.ajoufinder.be.comment.domain.Comment;
import com.ajoufinder.be.comment.domain.constant.CommentStatus;
import com.ajoufinder.be.comment.dto.Request.CommentCreateRequest;
import com.ajoufinder.be.comment.dto.Request.CommentUpdateRequest;
import com.ajoufinder.be.comment.dto.Response.CommentCreateResponse;
import com.ajoufinder.be.comment.dto.Response.CommentResponse;
import com.ajoufinder.be.comment.dto.Response.CommentUserResponse;
import com.ajoufinder.be.comment.repository.CommentRepository;
import com.ajoufinder.be.user.domain.User;
import com.ajoufinder.be.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    /* 댓글 작성하기 */
    @Transactional
    public CommentCreateResponse createComment(User loginUser, Long boardId, CommentCreateRequest request) {
        Board board = boardRepository.findById(boardId)
            .orElseThrow(() -> new EntityNotFoundException("게시글이 존재하지 않습니다."));

        User user = userRepository.findById(loginUser.getId())
            .orElseThrow(() -> new EntityNotFoundException("사용자가 존재하지 않습니다."));

        Comment parentComment = null;
        if (request.parentCommentId() != null) {
            parentComment = commentRepository.findById(request.parentCommentId())
                .orElseThrow(() -> new EntityNotFoundException("부모 댓글이 존재하지 않습니다."));
        }

        Comment comment = Comment.builder()
            .parentComment(parentComment)
            .user(user)
            .board(board)
            .content(request.content())
            .status(CommentStatus.VISIBLE)
            .isSecret(request.isSecret())
            .build();

        commentRepository.save(comment);
        return CommentCreateResponse.from(comment);
    }

    /* 댓글 수정하기 */
    @Transactional
    public CommentCreateResponse updateComment(User loginUser, Long boardId, Long commentId, CommentUpdateRequest request) {
        Comment comment = commentRepository.findById(commentId)
            .orElseThrow(() -> new RuntimeException("댓글을 찾을 수 없습니다."));
        
        if (comment.getStatus() == CommentStatus.DELETED) {
            throw new IllegalStateException("삭제된 댓글은 수정할 수 없습니다.");
        }
        
        if (!comment.getBoard().getId().equals(boardId)) {
            throw new RuntimeException("해당 게시글에 속한 댓글이 아닙니다.");
        }

        if (!comment.getUser().getId().equals(loginUser.getId())){
            throw new RuntimeException("댓글 작성자만 댓글을 수정할 수 있습니다.");
        }


        comment.updateContent(request.content(), request.isSecret());

        return CommentCreateResponse.from(comment);
    }

    /* 댓글 삭제하기 */
    @Transactional
    public void deleteComment(User loginUser, Long boardId, Long commentId) {
        Comment comment = commentRepository.findById(commentId)
            .orElseThrow(() -> new RuntimeException("댓글을 찾을 수 없습니다."));

        if (!comment.getBoard().getId().equals(boardId)) {
            throw new RuntimeException("해당 게시글에 속한 댓글이 아닙니다.");
        }

        if (!comment.getUser().getId().equals(loginUser.getId())){
            throw new RuntimeException("댓글 작성자만 댓글을 수정할 수 있습니다.");
        }

        comment.markAsDeleted();
    }

    /* 한 게시글의 모든 댓글 리스트 조회하기
     * 현재 컨트롤러에는 이 메서드를 사용하는 부분이 없습니다. 하지만 남겨는 놓았습니다.
    */
    @Transactional(readOnly = true)
    public List<CommentResponse> getCommentsByBoard(Long boardId) {
        List<Comment> comments = commentRepository.findByBoardIdAndStatus(boardId, CommentStatus.VISIBLE);

        return comments.stream()
            .filter(comment -> comment.getParentComment() == null)
            .map(CommentResponse::from)
            .collect(Collectors.toList());
    }

    /* [페이지 기반] 한 게시글의 부모 댓글 페이지 수만큼 받아오기
     * "대댓글이 아닌 댓글"을 페이지 번호, 페이지 사이즈만큼 가져옵니다.
     * 예로, 한 페이지 사이즈가 10이라면 대댓글이 아닌 댓글 10개를 가져옵니다.
     * 주의: 해당 댓글들에 달린 대댓글, 대대댓글 등 또한 함께 포함해 가져옵니다.
     * 
     * 로그인한 사용자와 댓글 작성자가 다르고, isSecret이 True인 댓글은 "비밀 댓글입니다." 라고 변경되어 반환됨.
    */
    @Transactional(readOnly = true)
    public Page<CommentResponse> getParentComments(User loginUser, Long boardId, Pageable pageable) {
        return commentRepository.findByBoardIdAndParentCommentIsNullAndStatus(boardId, CommentStatus.VISIBLE, pageable)
                .map(comment -> CommentResponse.from(comment, loginUser));
    }

    /* [페이지 기반] 사용자별 작성한 댓글 조회하기 */
    @Transactional(readOnly = true)
    public Page<CommentUserResponse> getUserComments(Long userId, Pageable pageable) {
        Page<Comment> commentPage = commentRepository.findByUserId(userId, pageable);
        return commentPage.map(comment -> {
            String relatedContent;
            if (comment.getParentComment() != null) {
                // 대댓글: 부모 댓글 내용
                relatedContent = comment.getParentComment().getContent();
            } else {
                // 일반 댓글: 게시글 제목
                relatedContent = comment.getBoard().getTitle();
            }

            return CommentUserResponse.from(comment, relatedContent);
        });
    }

}
