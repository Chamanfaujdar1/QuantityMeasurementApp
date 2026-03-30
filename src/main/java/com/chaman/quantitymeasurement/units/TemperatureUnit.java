package com.chaman.quantitymeasurement.units;

public enum TemperatureUnit implements IMeasurable {

    CELSIUS,
    FAHRENHEIT;

    @Override
    public double convertToBaseUnit(double value) {

        if(this == FAHRENHEIT)
            return (value - 32) * 5 / 9;

        return value;
    }

    @Override
    public double convertFromBaseUnit(double value) {

        if(this == FAHRENHEIT)
            return (value * 9 / 5) + 32;

        return value;
    }

    @Override
    public String getUnitName() {
        return name();
    }

    @Override
    public double getConversionFactor() {
        return 1;
    }

    @Override
    public boolean supportsArithmetic() {
        return false;
    }
}
