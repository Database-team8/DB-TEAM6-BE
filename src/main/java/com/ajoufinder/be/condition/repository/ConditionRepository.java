package com.ajoufinder.be.condition.repository;

import com.ajoufinder.be.condition.domain.Condition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConditionRepository extends JpaRepository <Condition, Long> {
}
