package com.chaman.quantitymeasurement.service;

import com.chaman.quantitymeasurement.dto.QuantityInputDTO;
import com.chaman.quantitymeasurement.entity.QuantityMeasurementEntity;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IQuantityMeasurementService {

    QuantityMeasurementEntity convert(QuantityInputDTO input);

    QuantityMeasurementEntity add(QuantityInputDTO input);

    QuantityMeasurementEntity subtract(QuantityInputDTO input);

    QuantityMeasurementEntity divide(QuantityInputDTO input);

    QuantityMeasurementEntity compare(QuantityInputDTO input);

    // Paginated version
    Page<QuantityMeasurementEntity> getHistoryByOperation(String operation, int page, int size);

    long getOperationCount(String operation);
}
