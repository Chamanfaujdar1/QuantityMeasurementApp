package com.chaman.quantitymeasurement;

public class Quantity<U extends IMeasurable> {
	
	private double value;
	private U unit;
	private static final double EPSILON = 1e-6;
	
	public Quantity(double value, U unit) {
		if(!Double.isFinite(value)) throw new IllegalArgumentException("Value must be finite!");
		if(unit==null) throw new IllegalArgumentException("Unit cannot be null!");
		this.value=value;
		this.unit=unit;
	}
	
	public double getValue() {
		return value;
	}
	
	public U getUnit() {
		return unit;
	}
	
	@Override 
	public boolean equals(Object obj) {
		if(this==obj) return true;
		if(obj==null || getClass()!=obj.getClass()) return false;
		Quantity<?> other= (Quantity<?>) obj;
		return Math.abs(unit.convertToBaseUnit(value)-other.getUnit().convertToBaseUnit(other.getValue())) < EPSILON;
	}
	
	@Override
	public int hashCode() {
		return Long.hashCode(Math.round(unit.convertToBaseUnit(value)/EPSILON));
	}
	
	@Override
	public String toString() {
		return String.format("%.2f %s", value, unit);
	}
	
	public double convertTo(U targetUnit) {
		if(targetUnit==null) throw new IllegalArgumentException("Unit cannot be null!");
		double baseValue=unit.convertToBaseUnit(value);
		return targetUnit.convertFromBaseUnit(baseValue);
	}
	
	public Quantity<U> add(Quantity<U> other, U targetUnit){
		if(other==null) throw new IllegalArgumentException("Quantity cannot be null!");
		if(targetUnit==null) throw new IllegalArgumentException("Unit cannot be null!");
		double result=unit.convertToBaseUnit(value)+other.getUnit().convertToBaseUnit(other.getValue());
		return new Quantity<>(targetUnit.convertFromBaseUnit(result),targetUnit);
	}
	
	public Quantity<U> add(Quantity<U> other){
		if (other == null) throw new IllegalArgumentException("other cannot be null");
		return add(other,unit);
	}
	
}
