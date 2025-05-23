package com.ajoufinder.be.board.domain;

import com.ajoufinder.be.item_type.domain.ItemType;
import com.ajoufinder.be.location.domain.Location;
import com.ajoufinder.be.user.domain.User;
import java.time.LocalDateTime;

import com.ajoufinder.be.global.domain.BaseTimeEntity;
import com.ajoufinder.be.board.domain.constant.Category;
import com.ajoufinder.be.board.dto.BoardUpdateRequest;
import com.ajoufinder.be.board.domain.constant.BoardStatus;

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
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "board")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long id;

    @Column(nullable = false, length = 255)
    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id")
    private Location location;

    @Column(name = "detailed_location", length = 100)
    private String detailedLocation;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "related_date")
    private LocalDateTime relatedDate;

    @Column(length = 255)
    private String image;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private BoardStatus status;

    @Enumerated(EnumType.STRING)
    @Column(name = "category", nullable = false)
    private Category category;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_type", nullable = false)
    private ItemType itemType;



   @Builder
    public Board(String title, User user, Location location, String detailedLocation, String description,
                 LocalDateTime relatedDate, String image, BoardStatus status, Category category, ItemType itemType) {
        this.title = title;
        this.user = user;
        this.location = location;
        this.detailedLocation = detailedLocation;
        this.description = description;
        this.relatedDate = relatedDate;
        this.image = image;
        this.status = status;
        this.category = category;
        this.itemType = itemType;
    }

    public void update(BoardUpdateRequest request, Location location, ItemType itemType) {
        this.title = request.title();
        this.description = request.description();
        this.detailedLocation = request.detailedLocation();
        this.image = request.image();
        this.relatedDate = request.relatedDate();
        //this.status = request.status();
        //this.category = request.category();
        this.location = location;
        this.itemType = itemType;
    }

    public void updateStatus(BoardStatus newStatus) {
        this.status = newStatus;
    }
}
