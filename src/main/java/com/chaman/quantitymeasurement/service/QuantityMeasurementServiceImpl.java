package com.chaman.quantitymeasurement.service;

import org.springframework.stereotype.Service;

import com.chaman.quantitymeasurement.dto.QuantityInputDTO;
import com.chaman.quantitymeasurement.entity.QuantityMeasurementEntity;
import com.chaman.quantitymeasurement.repository.QuantityMeasurementRepository;
import com.chaman.quantitymeasurement.units.*;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class QuantityMeasurementServiceImpl implements IQuantityMeasurementService {

    private final QuantityMeasurementRepository repository;

    public QuantityMeasurementServiceImpl(QuantityMeasurementRepository repository) {
        this.repository = repository;
    }

    // ---------------- COMMON METHOD ----------------

    private IMeasurable getUnit(String measurementType, String unit) {
        unit = unit.toUpperCase();

        return switch (measurementType.toUpperCase()) {
            case "LENGTH" -> LengthUnit.valueOf(unit);
            case "WEIGHT" -> WeightUnit.valueOf(unit);
            case "VOLUME" -> VolumeUnit.valueOf(unit);
            case "TEMPERATURE" -> TemperatureUnit.valueOf(unit);
            default -> throw new RuntimeException("Invalid measurement type");
        };
    }

    // ---------------- COMPARE ----------------

    @Override
    public QuantityMeasurementEntity compare(QuantityInputDTO input) {

        QuantityMeasurementEntity entity = new QuantityMeasurementEntity();

        var q1 = input.getThisQuantityDTO();
        var q2 = input.getThatQuantityDTO();

        IMeasurable unit1 = getUnit(q1.getMeasurementType(), q1.getUnit());
        IMeasurable unit2 = getUnit(q2.getMeasurementType(), q2.getUnit());

        double base1 = unit1.convertToBaseUnit(q1.getValue());
        double base2 = unit2.convertToBaseUnit(q2.getValue());

        boolean result = base1 == base2;

        setCommonFields(entity, input);
        entity.setOperation("COMPARE");
        entity.setResultString(String.valueOf(result));
        entity.setCreatedAt(LocalDateTime.now());

        return repository.save(entity);
    }

    // ---------------- CONVERT ----------------

    @Override
    public QuantityMeasurementEntity convert(QuantityInputDTO input) {

        QuantityMeasurementEntity entity = new QuantityMeasurementEntity();

        var q = input.getThisQuantityDTO();

        IMeasurable unit = getUnit(q.getMeasurementType(), q.getUnit());

        double base = unit.convertToBaseUnit(q.getValue());
        double result = unit.convertFromBaseUnit(base);

        entity.setThisValue(q.getValue());
        entity.setThisUnit(q.getUnit());
        entity.setThisMeasurementType(q.getMeasurementType());

        entity.setResultValue(result);
        entity.setResultUnit(q.getUnit());
        entity.setOperation("CONVERT");
        entity.setCreatedAt(LocalDateTime.now());

        return repository.save(entity);
    }

    // ---------------- ADD ----------------

    @Override
    public QuantityMeasurementEntity add(QuantityInputDTO input) {

        QuantityMeasurementEntity entity = new QuantityMeasurementEntity();

        var q1 = input.getThisQuantityDTO();
        var q2 = input.getThatQuantityDTO();

        IMeasurable unit1 = getUnit(q1.getMeasurementType(), q1.getUnit());
        IMeasurable unit2 = getUnit(q2.getMeasurementType(), q2.getUnit());

        if (!unit1.supportsArithmetic() || !unit2.supportsArithmetic()) {
            throw new RuntimeException("Arithmetic not supported for this unit");
        }

        double base1 = unit1.convertToBaseUnit(q1.getValue());
        double base2 = unit2.convertToBaseUnit(q2.getValue());

        double resultBase = base1 + base2;

        double finalResult = unit1.convertFromBaseUnit(resultBase);

        setCommonFields(entity, input);
        entity.setResultValue(finalResult);
        entity.setResultUnit(q1.getUnit());
        entity.setOperation("ADD");
        entity.setCreatedAt(LocalDateTime.now());

        return repository.save(entity);
    }
    
    @Override
    public QuantityMeasurementEntity subtract(QuantityInputDTO input) {

        QuantityMeasurementEntity entity = new QuantityMeasurementEntity();

        var q1 = input.getThisQuantityDTO();
        var q2 = input.getThatQuantityDTO();

        IMeasurable unit1 = getUnit(q1.getMeasurementType(), q1.getUnit());
        IMeasurable unit2 = getUnit(q2.getMeasurementType(), q2.getUnit());

        if (!unit1.supportsArithmetic() || !unit2.supportsArithmetic()) {
            throw new RuntimeException("Arithmetic not supported for this unit");
        }

        double base1 = unit1.convertToBaseUnit(q1.getValue());
        double base2 = unit2.convertToBaseUnit(q2.getValue());

        double resultBase = base1 - base2;

        double finalResult = unit1.convertFromBaseUnit(resultBase);

        setCommonFields(entity, input);
        entity.setResultValue(finalResult);
        entity.setResultUnit(q1.getUnit());
        entity.setOperation("SUBTRACT");
        entity.setCreatedAt(LocalDateTime.now());

        return repository.save(entity);
    }
    
    @Override
    public QuantityMeasurementEntity divide(QuantityInputDTO input) {

        QuantityMeasurementEntity entity = new QuantityMeasurementEntity();

        var q1 = input.getThisQuantityDTO();
        var q2 = input.getThatQuantityDTO();

        IMeasurable unit1 = getUnit(q1.getMeasurementType(), q1.getUnit());
        IMeasurable unit2 = getUnit(q2.getMeasurementType(), q2.getUnit());

        if (!unit1.supportsArithmetic() || !unit2.supportsArithmetic()) {
            throw new RuntimeException("Arithmetic not supported for this unit");
        }

        double base1 = unit1.convertToBaseUnit(q1.getValue());
        double base2 = unit2.convertToBaseUnit(q2.getValue());

        if (base2 == 0) {
            throw new RuntimeException("Cannot divide by zero");
        }

        double result = base1 / base2;

        setCommonFields(entity, input);
        entity.setResultValue(result);
        entity.setResultUnit("RATIO"); // division ka unit
        entity.setOperation("DIVIDE");
        entity.setCreatedAt(LocalDateTime.now());

        return repository.save(entity);
    }

    // ---------------- HISTORY ----------------

    @Override
    public List<QuantityMeasurementEntity> getHistoryByOperation(String operation) {
        return repository.findByOperation(operation.toUpperCase());
    }

    // ---------------- COUNT ----------------

    @Override
    public long getOperationCount(String operation) {
        return repository.countByOperationAndErrorFalse(operation.toUpperCase());
    }

    // ---------------- COMMON SETTER ----------------

    private void setCommonFields(QuantityMeasurementEntity entity, QuantityInputDTO input) {

        var q1 = input.getThisQuantityDTO();
        var q2 = input.getThatQuantityDTO();

        entity.setThisValue(q1.getValue());
        entity.setThisUnit(q1.getUnit());
        entity.setThisMeasurementType(q1.getMeasurementType());

        if (q2 != null) {
            entity.setThatValue(q2.getValue());
            entity.setThatUnit(q2.getUnit());
            entity.setThatMeasurementType(q2.getMeasurementType());
        }
    }
}