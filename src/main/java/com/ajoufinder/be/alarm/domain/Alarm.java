package com.ajoufinder.be.alarm.domain;

import com.ajoufinder.be.global.domain.BaseTimeEntity;
import com.ajoufinder.be.user.domain.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "alarm")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Alarm extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "alarm_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false, length = 500)
    private String content;

    @Column(name = "related_board_id", nullable = false, length = 500)
    private Long relatedBoardId;

    @Column(name = "is_read", nullable = false)
    private Boolean isRead;


    @Builder
    public Alarm(User user, String content, Long relatedBoardId, Boolean isRead) {
        this.user = user;
        this.content = content;
        this.relatedBoardId = relatedBoardId;
        this.isRead = isRead;
    }

    public void markAsRead() {
        this.isRead = true;
    }
}
