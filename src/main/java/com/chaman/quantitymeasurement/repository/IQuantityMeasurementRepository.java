package com.chaman.quantitymeasurement.repository;

import java.util.List;

import com.chaman.quantitymeasurement.entity.QuantityMeasurementEntity;

public interface IQuantityMeasurementRepository {

    void save(QuantityMeasurementEntity entity);

    List<QuantityMeasurementEntity> findAll();
    
    void deleteAllMeasurements();
}
