package com.chaman.quantitymeasurement.controller;

import com.chaman.quantitymeasurement.dto.QuantityDTO;
import com.chaman.quantitymeasurement.service.IQuantityMeasurementService;

public class QuantityMeasurementController {

    private final IQuantityMeasurementService service;

    public QuantityMeasurementController(IQuantityMeasurementService service) {
        if (service == null) {
            throw new IllegalArgumentException("Service cannot be null");
        }
        this.service = service;
    }

    public boolean performComparison(QuantityDTO first, QuantityDTO second) {
        return service.compare(first, second);
    }

    public QuantityDTO performConversion(QuantityDTO source, QuantityDTO.IMeasurableUnit targetUnit) {
        return service.convert(source, targetUnit);
    }

    public QuantityDTO performAddition(QuantityDTO first, QuantityDTO second, QuantityDTO.IMeasurableUnit targetUnit) {
        return service.add(first, second);
    }

    public QuantityDTO performSubtraction(QuantityDTO first, QuantityDTO second,
                                          QuantityDTO.IMeasurableUnit targetUnit) {
        return service.subtract(first, second);
    }

    public double performDivision(QuantityDTO first, QuantityDTO second) {
        return service.divide(first, second);
    }
}