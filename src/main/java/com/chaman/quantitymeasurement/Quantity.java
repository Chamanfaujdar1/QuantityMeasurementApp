package com.chaman.quantitymeasurement;

import java.util.function.DoubleBinaryOperator;

public class Quantity<U extends IMeasurable> {

    private enum ArithmeticOperation {
        ADD((a, b) -> a + b),
        SUBTRACT((a, b) -> a - b),
        DIVIDE((a, b) -> {
            if (b == 0.0) {
                throw new ArithmeticException("Division by zero");
            }
            return a / b;
        });

        private final DoubleBinaryOperator operator;

        ArithmeticOperation(DoubleBinaryOperator operator) {
            this.operator = operator;
        }

        public double compute(double a, double b) {
            return operator.applyAsDouble(a, b);
        }
    }

    private final double value;
    private final U unit;

    public Quantity(double value, U unit) {
        if (unit == null) {
            throw new IllegalArgumentException("Unit cannot be null");
        }
        if (!Double.isFinite(value)) {
            throw new IllegalArgumentException("Value must be a finite number");
        }
        this.value = value;
        this.unit = unit;
    }

    public double getValue() {
        return value;
    }

    public U getUnit() {
        return unit;
    }

    public static <U extends IMeasurable> double convert(double value, U sourceUnit, U targetUnit) {
        if (sourceUnit == null || targetUnit == null) {
            throw new IllegalArgumentException("Units cannot be null");
        }
        if (!Double.isFinite(value)) {
            throw new IllegalArgumentException("Value must be a finite number");
        }
        if (sourceUnit.getClass() != targetUnit.getClass()) {
            throw new IllegalArgumentException("Cannot convert between different measurement categories.");
        }
        double baseValue = sourceUnit.convertToBaseUnit(value);
        return targetUnit.convertFromBaseUnit(baseValue);
    }

    public Quantity<U> convertTo(U targetUnit) {
        double convertedValue = Quantity.convert(this.value, this.unit, targetUnit);
        return new Quantity<>(convertedValue, targetUnit);
    }

    private void validateArithmeticOperands(Quantity<U> other, U targetUnit, boolean targetUnitRequired) {
        if (other == null) {
            throw new IllegalArgumentException("Quantity cannot be null");
        }
        if (targetUnitRequired && targetUnit == null) {
            throw new IllegalArgumentException("Target unit cannot be null");
        }
        if (this.unit.getClass() != other.unit.getClass()
                || (targetUnitRequired && this.unit.getClass() != targetUnit.getClass())) {
            throw new IllegalArgumentException(
                    "Cannot perform arithmetic operations on different measurement categories.");
        }
    }

    private double performBaseArithmetic(Quantity<U> other, ArithmeticOperation operation) {
        this.unit.validateOperationSupport(operation.name());
        double thisInBase = this.unit.convertToBaseUnit(this.value);
        double otherInBase = other.unit.convertToBaseUnit(other.value);
        return operation.compute(thisInBase, otherInBase);
    }

    public Quantity<U> add(Quantity<U> other, U targetUnit) {
        validateArithmeticOperands(other, targetUnit, true);
        double sumInBase = performBaseArithmetic(other, ArithmeticOperation.ADD);
        double sumInTarget = targetUnit.convertFromBaseUnit(sumInBase);
        return new Quantity<>(sumInTarget, targetUnit);
    }

    public Quantity<U> add(Quantity<U> other) {
        return this.add(other, this.unit);
    }

    public Quantity<U> subtract(Quantity<U> other, U targetUnit) {
        validateArithmeticOperands(other, targetUnit, true);
        double diffInBase = performBaseArithmetic(other, ArithmeticOperation.SUBTRACT);
        double diffInTarget = targetUnit.convertFromBaseUnit(diffInBase);
        return new Quantity<>(diffInTarget, targetUnit);
    }

    public Quantity<U> subtract(Quantity<U> other) {
        return this.subtract(other, this.unit);
    }

    public double divide(Quantity<U> other) {
        validateArithmeticOperands(other, null, false);
        return performBaseArithmetic(other, ArithmeticOperation.DIVIDE);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;

        Quantity<?> quantity = (Quantity<?>) obj;
        if (this.unit.getClass() != quantity.unit.getClass()) {
            return false;
        }

        // Use epsilon for robust double comparisons
        double epsilon = 1e-6;
        return Math.abs(compareValue(quantity) - compareValue(this)) < epsilon;
    }

    private double compareValue(Quantity<?> quantity) {
        return quantity.unit.convertToBaseUnit(quantity.value);
    }

    @Override
    public String toString() {
        return "Quantity{value=" + value + ", unit=" + unit + "}";
    }
}
