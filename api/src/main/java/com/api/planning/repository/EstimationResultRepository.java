package com.api.planning.repository;


import com.api.planning.entity.EstimationResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface EstimationResultRepository extends JpaRepository<EstimationResult, Long> {
}
