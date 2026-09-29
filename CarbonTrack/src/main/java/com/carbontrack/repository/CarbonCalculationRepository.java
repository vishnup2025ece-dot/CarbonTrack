package com.carbontrack.repository;

import com.carbontrack.model.CarbonCalculation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarbonCalculationRepository extends JpaRepository<CarbonCalculation, Long> {
}
