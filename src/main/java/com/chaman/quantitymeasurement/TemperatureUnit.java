package com.chaman.quantitymeasurement;

import java.util.function.Function;

public enum TemperatureUnit implements IMeasurable {

    CELSIUS(
            c -> c,
            c -> c,
            "Celsius"
    ),

    FAHRENHEIT(
            f -> (f - 32) * 5 / 9,
            c -> (c * 9 / 5) + 32,
            "Fahrenheit"
    );

    private final Function<Double, Double> toCelsius;
    private final Function<Double, Double> fromCelsius;
    private final String name;

    SupportsArithmetic supportsArithmetic = () -> false;

    TemperatureUnit(Function<Double, Double> toCelsius,
                    Function<Double, Double> fromCelsius,
                    String name) {

        this.toCelsius = toCelsius;
        this.fromCelsius = fromCelsius;
        this.name = name;
    }

    @Override
    public double convertToBaseUnit(double value) {
        return toCelsius.apply(value);
    }

    @Override
    public double convertFromBaseUnit(double value) {
        return fromCelsius.apply(value);
    }

    @Override
    public String getUnitName() {
        return name;
    }

    @Override
    public boolean supportsArithmetic() {
        return supportsArithmetic.isSupported();
    }

    @Override
    public void validateOperationSupport(String operation) {
        throw new UnsupportedOperationException(
                "Temperature does not support " + operation + " operation"
        );
    }

    @Override
    public double getConversionFactor() {
        return 1;   // safe neutral value
    }
}
