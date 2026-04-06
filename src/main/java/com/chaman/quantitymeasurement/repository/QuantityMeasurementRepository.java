package com.chaman.quantitymeasurement.repository;

import com.chaman.quantitymeasurement.entity.QuantityMeasurementEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuantityMeasurementRepository extends JpaRepository<QuantityMeasurementEntity, Long> {

    // Paginated - used by getHistoryByOperation
    Page<QuantityMeasurementEntity> findByOperation(String operation, Pageable pageable);

    // Count - used by getOperationCount
    long countByOperationAndErrorFalse(String operation);
}