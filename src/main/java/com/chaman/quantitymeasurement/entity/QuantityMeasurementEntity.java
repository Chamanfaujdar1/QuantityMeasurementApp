package com.chaman.quantitymeasurement.entity;

import java.io.Serializable;

public class QuantityMeasurementEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    private String operation;
    private double operand1;
    private double operand2;
    private double result;
    private boolean error;
    private String message;

    public QuantityMeasurementEntity(String operation,
                                     double operand1,
                                     double operand2,
                                     double result) {
        this.operation = operation;
        this.operand1 = operand1;
        this.operand2 = operand2;
        this.result = result;
        this.error = false;
    }

    public QuantityMeasurementEntity(String message) {
        this.error = true;
        this.message = message;
    }

    public boolean hasError() {
        return error;
    }

    public double getResult() {
        return result;
    }

    public String getMessage() {
        return message;
    }
    
    public String getOperation() {
        return operation;
    }

    public double getOperand1() {
        return operand1;
    }

    public double getOperand2() {
        return operand2;
    }
}
