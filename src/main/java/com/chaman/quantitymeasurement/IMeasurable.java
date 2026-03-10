package com.chaman.quantitymeasurement;

@FunctionalInterface
interface SupportsArithmetic {
    boolean isSupported();
}

public interface IMeasurable {

    double convertToBaseUnit(double value);

    double convertFromBaseUnit(double value);

    String getUnitName();

    double getConversionFactor();   // method declaration

    default boolean supportsArithmetic() {
        return true;
    }

    default void validateOperationSupport(String operation) {
        if (!supportsArithmetic()) {
            throw new UnsupportedOperationException(
                    operation + " operation not supported for this unit"
            );
        }
    }
}
