package com.chaman.quantitymeasurement;

public enum VolumeUnit implements IMeasurable {
	MILLILITRE(0.001),
	LITRE(1.0),
	GALLON(3.78541);
	
	private final double conversionFactor;
	
	VolumeUnit(double conversionFactor){
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
