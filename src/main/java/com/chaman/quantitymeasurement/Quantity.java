package com.chaman.quantitymeasurement;

import java.util.Objects;
import java.util.function.DoubleBinaryOperator;

public class Quantity<U extends IMeasurable> {

    private final double value;
    private final U unit;

    public Quantity(double value, U unit) {

        if (unit == null)
            throw new IllegalArgumentException("Unit cannot be null");

        if (!Double.isFinite(value))
            throw new IllegalArgumentException("Invalid value");

        this.value = value;
        this.unit = unit;
    }

    private double convertToBaseUnit() {
        return value * unit.getConversionFactor();
    }

    public U getUnit() {
        return unit;
    }

    public double getValue() {
        return value;
    }

    public Quantity<U> convertTo(U targetUnit) {

        if (targetUnit == null)
            throw new IllegalArgumentException("Target unit cannot be null");

        double baseValue = convertToBaseUnit();

        double converted = baseValue / targetUnit.getConversionFactor();

        return new Quantity<>(round(converted), targetUnit);
    }

    public Quantity<U> add(Quantity<U> other) {

        unit.validateOperationSupport("ADD");

        validateArithmeticOperands(other, null, false);

        double baseResult = performBaseArithmetic(other, ArithmeticOperation.ADD);

        double result = baseResult / unit.getConversionFactor();

        return new Quantity<>(round(result), unit);
    }

    public Quantity<U> add(Quantity<U> other, U targetUnit) {

        unit.validateOperationSupport("ADD");

        validateArithmeticOperands(other, targetUnit, true);

        double baseResult = performBaseArithmetic(other, ArithmeticOperation.ADD);

        double result = baseResult / targetUnit.getConversionFactor();

        return new Quantity<>(round(result), targetUnit);
    }

    public Quantity<U> subtract(Quantity<U> other) {

        unit.validateOperationSupport("SUBTRACT");

        validateArithmeticOperands(other, null, false);

        double baseResult = performBaseArithmetic(other, ArithmeticOperation.SUBTRACT);

        double result = baseResult / unit.getConversionFactor();

        return new Quantity<>(round(result), unit);
    }

    public Quantity<U> subtract(Quantity<U> other, U targetUnit) {

        unit.validateOperationSupport("SUBTRACT");

        validateArithmeticOperands(other, targetUnit, true);

        double baseResult = performBaseArithmetic(other, ArithmeticOperation.SUBTRACT);

        double result = baseResult / targetUnit.getConversionFactor();

        return new Quantity<>(round(result), targetUnit);
    }

    public double divide(Quantity<U> other) {

        unit.validateOperationSupport("DIVIDE");

        validateArithmeticOperands(other, null, false);

        return performBaseArithmetic(other, ArithmeticOperation.DIVIDE);
    }

    private double round(double value) {
        return Math.round(value * 100.0) / 100.0;
    }

    private void validateArithmeticOperands(Quantity<U> other, U targetUnit, boolean targetUnitRequired) {

        if (other == null)
            throw new IllegalArgumentException("Other quantity cannot be null");

        if (!unit.getClass().equals(other.unit.getClass()))
            throw new IllegalArgumentException("Cannot operate on different measurement categories");

        if (!Double.isFinite(value) || !Double.isFinite(other.value))
            throw new IllegalArgumentException("Values must be finite numbers");

        if (targetUnitRequired && targetUnit == null)
            throw new IllegalArgumentException("Target unit cannot be null");
    }

    public enum ArithmeticOperation {

        ADD((a, b) -> a + b),

        SUBTRACT((a, b) -> a - b),

        DIVIDE((a, b) -> {
            if (b == 0)
                throw new ArithmeticException("Division by zero");
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

    private double performBaseArithmetic(Quantity<U> other, ArithmeticOperation operation) {

        double baseValue1 = value * unit.getConversionFactor();
        double baseValue2 = other.value * other.unit.getConversionFactor();

        return operation.compute(baseValue1, baseValue2);
    }

    @Override
    public boolean equals(Object o) {

        if (this == o)
            return true;

        if (!(o instanceof Quantity))
            return false;

        Quantity<?> that = (Quantity<?>) o;

        if (!this.unit.getClass().equals(that.unit.getClass()))
            return false;

        return Double.compare(
                this.convertToBaseUnit(),
                that.convertToBaseUnit()
        ) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(convertToBaseUnit());
    }

    @Override
    public String toString() {
        return value + " " + unit.getUnitName();
    }
}