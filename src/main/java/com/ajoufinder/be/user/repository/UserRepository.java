package com.ajoufinder.be.user.repository;

import com.ajoufinder.be.user.domain.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);
}
