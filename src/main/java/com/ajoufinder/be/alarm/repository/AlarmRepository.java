package com.ajoufinder.be.alarm.repository;

import com.ajoufinder.be.alarm.domain.Alarm;
import com.ajoufinder.be.user.domain.User;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlarmRepository extends JpaRepository<Alarm, Long> {
    List<Alarm> findByUserOrderByCreatedAtDesc(User user);
}   
