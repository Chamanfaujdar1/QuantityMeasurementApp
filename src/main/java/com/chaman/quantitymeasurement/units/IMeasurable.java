package com.chaman.quantitymeasurement.units;

public interface IMeasurable {

    double convertToBaseUnit(double value);

    double convertFromBaseUnit(double value);

    String getUnitName();

    double getConversionFactor();

    boolean supportsArithmetic();
}
