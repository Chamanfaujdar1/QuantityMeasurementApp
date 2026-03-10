package com.chaman.quantitymeasurement;

public enum LengthUnit implements IMeasurable{
	FEET(12.0),
	INCHES(1.0),
	YARDS(36),
	CENTIMETERS(1.0/2.54);
	
	private final double conversionFactor;
	
	LengthUnit(double conversionFactor) {
		this.conversionFactor=conversionFactor;
	}
	
	public double getConversionFactor() {
		return conversionFactor;
	}
	
	public double convertToBaseUnit(double value) {
		return value * getConversionFactor(); 
	}
	
	public double convertFromBaseUnit(double value) {
		return value/getConversionFactor();
	}
}