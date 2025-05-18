package com.ajoufinder.be.location_condition_mapping.repository;

import com.ajoufinder.be.location_condition_mapping.domain.LocationConditionMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationConditionMappingRepository  extends JpaRepository<LocationConditionMapping, Long> {
}
