package com.ajoufinder.be.condition.repository;

import com.ajoufinder.be.condition.domain.Condition;
import com.ajoufinder.be.user.domain.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConditionRepository extends JpaRepository <Condition, Long> {
    List<Condition> findAllByUser(User user);
}
