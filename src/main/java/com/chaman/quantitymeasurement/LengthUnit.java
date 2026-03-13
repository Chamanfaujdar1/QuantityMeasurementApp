package com.chaman.quantitymeasurement;

public enum LengthUnit implements IMeasurable {
    FEET(12.0), INCH(1.0), YARD(36.0), CENTIMETER(0.393701);

    private final double conversionFactor;

    LengthUnit(double conversionFactor) {
        this.conversionFactor = conversionFactor;
    }

    public double getConversionFactor() {
        return conversionFactor;
    }

    @Override
    public double convertToBaseUnit(double value) {
        return value * this.conversionFactor;
    }

    @Override
    public double convertFromBaseUnit(double baseValue) {
        return baseValue / this.conversionFactor;
    }

    @Override
    public String getUnitName() {
        return this.name();
    }
}
