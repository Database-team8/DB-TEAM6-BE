package com.ajoufinder.be.comment.domain;

import com.ajoufinder.be.board.domain.Board;
import com.ajoufinder.be.global.domain.BaseTimeEntity;
import com.ajoufinder.be.user.domain.User;
import com.ajoufinder.be.comment.domain.constant.CommentStatus;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "comment")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Comment parentComment;

    @OneToMany(mappedBy = "parentComment")
    private List<Comment> childComments = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id", nullable = false)
    private Board board;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private CommentStatus status;

    @Column(name = "is_secret", nullable = false)
    private Boolean isSecret;

    public void addChildComment(Comment child) {
        childComments.add(child);
        child.assignParentComment(this);
    }

    public void assignParentComment(Comment parent){
        this.parentComment = parent;
    }

   @Builder
    public Comment(Comment parentComment, User user, Board board, String content, CommentStatus status, Boolean isSecret) {
        this.parentComment = parentComment;
        this.user = user;
        this.board = board;
        this.content = content;
        this.status = status;
        this.isSecret = isSecret;
    }

    public void updateContent(String Content, Boolean isSecret) {
        this.content = Content;
        this.isSecret = isSecret;
    }

    public void markAsDeleted() {
        this.status = CommentStatus.DELETED;
    }
}
