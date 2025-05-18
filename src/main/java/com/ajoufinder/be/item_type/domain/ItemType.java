package com.ajoufinder.be.item_type.domain;

import com.ajoufinder.be.item_type.domain.constant.ItemTypeName;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "itemtype")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ItemType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "item_type", nullable = false)
    private ItemTypeName itemType;


    @Builder
    public ItemType(ItemTypeName itemType) {
        this.itemType = itemType;
    }
}
