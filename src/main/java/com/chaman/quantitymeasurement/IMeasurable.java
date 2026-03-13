package com.chaman.quantitymeasurement;

public interface IMeasurable {

    @FunctionalInterface
    public interface SupportsArithmetic {
        boolean isSupported();
    }

    SupportsArithmetic supportsArithmetic = () -> true;

    default boolean supportsArithmetic() {
        return supportsArithmetic.isSupported();
    }

    default void validateOperationSupport(String operation) {
        // Subclasses can override to validate specific operations.
        // Default allows all operations.
    }

    double getConversionFactor();

    double convertToBaseUnit(double value);

    double convertFromBaseUnit(double baseValue);

    String getUnitName();
}
