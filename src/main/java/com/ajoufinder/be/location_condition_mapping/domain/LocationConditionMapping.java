package com.ajoufinder.be.location_condition_mapping.domain;


//import com.ajoufinder.be.global.domain.BaseTimeEntity;
import com.ajoufinder.be.condition.domain.Condition;
import com.ajoufinder.be.location.domain.Location;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
//import jakarta.persistence.EnumType;
//import jakarta.persistence.Enumerated;
//import jakarta.persistence.EnumType;
//import jakarta.persistence.Enumerated;
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
@Table(name = "locationconditionmapping")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LocationConditionMapping{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mapping_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "condition_id", nullable = false)
    private Condition condition;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id", nullable = false)
    private Location location;


    @Builder
    public LocationConditionMapping(Condition condition, Location location) {
        this.condition = condition;
        this.location = location;
    }
}
