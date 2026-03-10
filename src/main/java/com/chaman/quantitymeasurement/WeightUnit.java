package com.chaman.quantitymeasurement;

public enum WeightUnit implements IMeasurable {
	MILLIGRAM(0.000001),
	GRAM(0.001),
	KILOGRAM(1.0),
	POUND(0.453592),
	OUNCE(0.0283495),
	TONNE(1000.0);
	
	private final double conversionFactor;
	
	WeightUnit(double conversionFactor){
		this.conversionFactor=conversionFactor;
	}
	
	public double getConversionFactor() {
		return conversionFactor;
	}
	
	public double convertToBaseUnit(double value) {
		return value*getConversionFactor();
	}
	
	public double convertFromBaseUnit(double value) {
		return value/getConversionFactor();
	}
}