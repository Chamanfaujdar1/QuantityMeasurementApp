package com.chaman.quantitymeasurement;

public enum LengthUnit {
    FEET(12.0), INCH(1.0);

    private final double conversionFactor;

    LengthUnit(double conversionFactor) {
        this.conversionFactor = conversionFactor;
    }

    public double getConversionFactor() {
        return conversionFactor;
    }
}
