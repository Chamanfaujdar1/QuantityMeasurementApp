package com.chaman.quantitymeasurement;

import java.util.function.Function;

public enum TemperatureUnit implements IMeasurable {
    CELSIUS((cel) -> cel, (cel) -> cel),
    FAHRENHEIT((fah) -> (fah - 32) * 5.0 / 9.0, (cel) -> (cel * 9.0 / 5.0) + 32),
    KELVIN((kel) -> kel - 273.15, (cel) -> cel + 273.15);

    private final Function<Double, Double> toBaseConverter;
    private final Function<Double, Double> fromBaseConverter;
    SupportsArithmetic supportsArithmetic = () -> false;

    TemperatureUnit(Function<Double, Double> toBaseConverter, Function<Double, Double> fromBaseConverter) {
        this.toBaseConverter = toBaseConverter;
        this.fromBaseConverter = fromBaseConverter;
    }

    @Override
    public boolean supportsArithmetic() {
        return supportsArithmetic.isSupported();
    }

    @Override
    public void validateOperationSupport(String operation) {
        throw new UnsupportedOperationException(
                "Temperature does not support " + operation.toLowerCase() + " operations.");
    }

    @Override
    public double getConversionFactor() {
        return 1.0; // Dummy value, non-linear conversion handles mapping natively
    }

    @Override
    public double convertToBaseUnit(double value) {
        return toBaseConverter.apply(value);
    }

    @Override
    public double convertFromBaseUnit(double baseValue) {
        return fromBaseConverter.apply(baseValue);
    }

    @Override
    public String getUnitName() {
        return this.name();
    }
}
