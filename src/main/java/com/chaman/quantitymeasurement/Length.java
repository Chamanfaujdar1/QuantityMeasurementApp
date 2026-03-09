package com.chaman.quantitymeasurement;

public class Length {
	
	private double value;
	private LengthUnit unit;
	private static final double EPSILON = 0.0001;
	
	public enum LengthUnit{
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
	}
	
	public Length(double value, LengthUnit unit) {
		//if(value<0) throw new IllegalArgumentException("Length can't be negative");
		if(!Double.isFinite(value)) throw new IllegalArgumentException("value should be finite");
		if(unit==null) throw new IllegalArgumentException("Unit cannot be null");
		this.value=value;
		this.unit=unit;
	}
	
	private double convertToBaseUnit() {
		return value * unit.getConversionFactor(); 
	}
	
	public double getValue() {
		return value;
	}
	
	public LengthUnit getUnit() {
		return unit;
	}
	
	
	@Override
	public boolean equals (Object obj) {
		if(this==obj) return true;
		if(obj==null || getClass()!=obj.getClass()) return false;
		Length other= (Length) obj;
		return Math.abs(this.convertToBaseUnit()-other.convertToBaseUnit()) < EPSILON;
	}
	
	@Override
	public int hashCode() {
		return Long.hashCode(Math.round(convertToBaseUnit() / EPSILON));
	}
	
	@Override
	public String toString() {
		return Math.round(value*100.0)/100.0+" "+unit;
	}
	
	public double convertTo(LengthUnit targetUnit) {
		if(targetUnit==null) throw new IllegalArgumentException("unit cannot be null");
		double baseValue=convertToBaseUnit();
		return baseValue/targetUnit.getConversionFactor();
	}
	
	public Length add(Length other) {
		if(other==null) throw new IllegalArgumentException("Length cant be null");
		double result=(this.convertToBaseUnit()+other.convertToBaseUnit())/this.unit.getConversionFactor();
		return new Length(result,this.unit);
	}
	
	public Length add(Length other, LengthUnit unit) {
		if(other==null) throw new IllegalArgumentException("Length cant be null");
		if(unit==null) throw new IllegalArgumentException("Unit can't be null");
		double result=(this.convertToBaseUnit()+other.convertToBaseUnit())/unit.getConversionFactor();
		return new Length(result,unit);
	}
}