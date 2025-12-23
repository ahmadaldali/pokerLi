package com.api.planning.repository;

import com.api.planning.entity.Estimation;
import com.api.planning.entity.UserStory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstimationRepository extends JpaRepository<Estimation, Long> {
}
