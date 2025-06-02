package com.ajoufinder.be.condition.domain;
import com.ajoufinder.be.item_type.domain.ItemType;
import com.ajoufinder.be.location.domain.Location;
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
@Table(name = "conditions")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Condition{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "condition_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_type_id", nullable = false)
    private ItemType itemType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id", nullable = false)
    private Location location;


    @Builder
    public Condition(ItemType itemType, Location location, User user) {
        this.itemType = itemType;
        this.location=location;
        this.user = user;
    }
}
