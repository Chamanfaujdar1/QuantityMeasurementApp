package com.chaman.quantitymeasurement.entity;

import java.io.Serializable;

import com.chaman.quantitymeasurement.model.QuantityModel;

/**
 * Persistence model capturing a single quantity measurement operation,
 * including operands, operation type, result and error state.
 */
public class QuantityMeasurementEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    public enum OperationType {
        COMPARISON,
        CONVERSION,
        ADDITION,
        SUBTRACTION,
        DIVISION,
        ERROR
    }

    private QuantityModel<?> leftOperand;
    private QuantityModel<?> rightOperand;
    private OperationType operationType;
    private QuantityModel<?> resultQuantity;
    private Double scalarResult;
    private boolean error;
    private String errorMessage;

    // Constructor for unary operations (e.g. conversion)
    public QuantityMeasurementEntity(QuantityModel<?> operand, OperationType operationType,
                                     QuantityModel<?> resultQuantity) {
        this.leftOperand = operand;
        this.operationType = operationType;
        this.resultQuantity = resultQuantity;
        this.error = false;
    }

    // Constructor for binary operations producing a quantity result
    public QuantityMeasurementEntity(QuantityModel<?> leftOperand, QuantityModel<?> rightOperand,
                                     OperationType operationType, QuantityModel<?> resultQuantity) {
        this.leftOperand = leftOperand;
        this.rightOperand = rightOperand;
        this.operationType = operationType;
        this.resultQuantity = resultQuantity;
        this.error = false;
    }

    // Constructor for binary operations producing a scalar result (e.g. division)
    public QuantityMeasurementEntity(QuantityModel<?> leftOperand, QuantityModel<?> rightOperand,
                                     OperationType operationType, Double scalarResult) {
        this.leftOperand = leftOperand;
        this.rightOperand = rightOperand;
        this.operationType = operationType;
        this.scalarResult = scalarResult;
        this.error = false;
    }

    // Constructor for error representation
    public QuantityMeasurementEntity(QuantityModel<?> leftOperand, QuantityModel<?> rightOperand,
                                     OperationType operationType, String errorMessage) {
        this.leftOperand = leftOperand;
        this.rightOperand = rightOperand;
        this.operationType = operationType;
        this.error = true;
        this.errorMessage = errorMessage;
    }

    public QuantityModel<?> getLeftOperand() {
        return leftOperand;
    }

    public QuantityModel<?> getRightOperand() {
        return rightOperand;
    }

    public OperationType getOperationType() {
        return operationType;
    }

    public QuantityModel<?> getResultQuantity() {
        return resultQuantity;
    }

    public Double getScalarResult() {
        return scalarResult;
    }

    public boolean hasError() {
        return error;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    @Override
    public String toString() {
        if (error) {
            return "QuantityMeasurementEntity{" +
                    "operationType=" + operationType +
                    ", errorMessage='" + errorMessage + '\'' +
                    '}';
        }
        StringBuilder builder = new StringBuilder("QuantityMeasurementEntity{");
        builder.append("operationType=").append(operationType);
        builder.append(", leftOperand=").append(leftOperand);
        if (rightOperand != null) {
            builder.append(", rightOperand=").append(rightOperand);
        }
        if (resultQuantity != null) {
            builder.append(", resultQuantity=").append(resultQuantity);
        }
        if (scalarResult != null) {
            builder.append(", scalarResult=").append(scalarResult);
        }
        builder.append('}');
        return builder.toString();
    }
}