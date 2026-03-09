package com.chaman.quantitymeasurement;

public class Weight {
	private double value;
	private WeightUnit unit;
	private static final double EPSILON=1e-6;
	
	public Weight(double value,WeightUnit unit) {
		if(!Double.isFinite(value)) throw new IllegalArgumentException("Value should be finite!");
		if(unit==null) throw new IllegalArgumentException("Unit can't be null!");
		this.value=value;
		this.unit=unit;
	}
	
	private double convertToBaseUnit() {
		return unit.toBase(value); 
	}
	
	public double getValue() {
		return value;
	}
	
	public WeightUnit getUnit() {
		return unit;
	}
	
	@Override 
	public boolean equals(Object obj) {
		if(this==obj) return true;
		if(obj==null || getClass()!=obj.getClass()) return false;
		Weight other= (Weight) obj;
		return Math.abs(this.convertToBaseUnit()-other.convertToBaseUnit()) < EPSILON;
	}
	
	@Override
	public int hashCode() {
		return Long.hashCode(Math.round(convertToBaseUnit()/EPSILON));
	}
	
	@Override
	public String toString() {
		return String.format("%.2f %s", value, unit);
	}
	
	public double convertTo(WeightUnit targetUnit) {
		if(targetUnit==null) throw new IllegalArgumentException("Unit can't be null!");
		double baseValue=convertToBaseUnit();
		return targetUnit.fromBase(baseValue);
	}
	
	public Weight add(Weight other) {
		if (other == null) throw new IllegalArgumentException("Weight cannot be null");
		return add(other,this.unit);
	}
	
	public Weight add(Weight other, WeightUnit targetUnit) {
		if(other==null) throw new IllegalArgumentException("Weight cant be null");
		if(targetUnit==null) throw new IllegalArgumentException("Unit can't be null");
		double result=this.convertToBaseUnit()+other.convertToBaseUnit();
		result=targetUnit.fromBase(result);
		return new Weight(result,targetUnit);
	}
}
