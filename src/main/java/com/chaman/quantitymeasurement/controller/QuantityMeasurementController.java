package com.chaman.quantitymeasurement.controller;

import com.chaman.quantitymeasurement.dto.QuantityDTO;
import com.chaman.quantitymeasurement.service.IQuantityMeasurementService;

public class QuantityMeasurementController {

    private IQuantityMeasurementService service;

    public QuantityMeasurementController(IQuantityMeasurementService service) {
        this.service = service;
    }

    // ADDITION
    public void performAddition(QuantityDTO q1, QuantityDTO q2) {

        QuantityDTO result = service.add(q1, q2);

        System.out.println("Addition Result: " 
                + result.getValue() + " " + result.getUnit());
    }

    // SUBTRACTION
    public void performSubtraction(QuantityDTO q1, QuantityDTO q2) {

        QuantityDTO result = service.subtract(q1, q2);

        System.out.println("Subtraction Result: "
                + result.getValue() + " " + result.getUnit());
    }

    // DIVISION
    public void performDivision(QuantityDTO q1, QuantityDTO q2) {

        double result = service.divide(q1, q2);

        System.out.println("Division Result: " + result);
    }

    // COMPARISON
    public void performComparison(QuantityDTO q1, QuantityDTO q2) {

        boolean result = service.compare(q1, q2);

        System.out.println("Are equal: " + result);
    }

    // CONVERSION
    public void performConversion(QuantityDTO input, String targetUnit) {

        QuantityDTO result = service.convert(input, targetUnit);

        System.out.println("Converted Result: "
                + result.getValue() + " " + result.getUnit());
    }

    // DELETE ALL MEASUREMENTS
    public void deleteAllMeasurements() {

        service.deleteAllMeasurements();

        System.out.println("All measurements deleted from database.");
    }
}

public class QuantityMeasurementController {

}
