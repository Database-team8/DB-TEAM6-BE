package com.ajoufinder.be.location.repository;

import com.ajoufinder.be.location.domain.Location;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {
    @Override
    List<Location> findAll();
}
