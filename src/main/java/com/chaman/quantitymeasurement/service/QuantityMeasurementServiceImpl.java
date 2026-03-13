package com.chaman.quantitymeasurement.service;

import com.chaman.quantitymeasurement.dto.QuantityDTO;
import com.chaman.quantitymeasurement.entity.QuantityMeasurementEntity;
import com.chaman.quantitymeasurement.repository.IQuantityMeasurementRepository;

public class QuantityMeasurementServiceImpl
        implements IQuantityMeasurementService {

    private IQuantityMeasurementRepository repository;

    public QuantityMeasurementServiceImpl(IQuantityMeasurementRepository repository) {
        this.repository = repository;
    }

    @Override
    public QuantityDTO convert(QuantityDTO input, String targetUnit) {

        if (input == null)
            throw new IllegalArgumentException("Input cannot be null");

        return new QuantityDTO(input.getValue(), targetUnit, input.getMeasurementType());
    }

    @Override
    public boolean compare(QuantityDTO q1, QuantityDTO q2) {

        if (q1 == null || q2 == null)
            throw new IllegalArgumentException("Quantities cannot be null");

        double value1 = q1.getValue();
        double value2 = q2.getValue();

        String unit1 = q1.getUnit();
        String unit2 = q2.getUnit();

        if (unit1.equals("FEET") && unit2.equals("INCHES")) {
            value2 = value2 / 12;
        }

        if (unit1.equals("INCHES") && unit2.equals("FEET")) {
            value1 = value1 / 12;
        }

        return value1 == value2;
    }

    @Override
    public QuantityDTO add(QuantityDTO q1, QuantityDTO q2) {

        double result = q1.getValue() + q2.getValue();

        QuantityMeasurementEntity entity =
                new QuantityMeasurementEntity(
                        "ADD",
                        q1.getValue(),
                        q2.getValue(),
                        result
                );

        repository.save(entity);

        return new QuantityDTO(result, q1.getUnit(), q1.getMeasurementType());
    }

    @Override
    public QuantityDTO subtract(QuantityDTO q1, QuantityDTO q2) {

        double result = q1.getValue() - q2.getValue();

        QuantityMeasurementEntity entity =
                new QuantityMeasurementEntity(
                        "SUBTRACT",
                        q1.getValue(),
                        q2.getValue(),
                        result
                );

        repository.save(entity);

        return new QuantityDTO(result, q1.getUnit(), q1.getMeasurementType());
    }

    @Override
    public double divide(QuantityDTO q1, QuantityDTO q2) {

        if (q2.getValue() == 0)
            throw new ArithmeticException("Division by zero");

        double result = q1.getValue() / q2.getValue();

        QuantityMeasurementEntity entity =
                new QuantityMeasurementEntity(
                        "DIVIDE",
                        q1.getValue(),
                        q2.getValue(),
                        result
                );

        repository.save(entity);

        return result;
    }

    @Override
    public void deleteAllMeasurements() {
        repository.deleteAllMeasurements();
    }
}
