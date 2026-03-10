package com.chaman.quantitymeasurement;

public class QuantityMeasurementApp {
	
	public static <U extends IMeasurable> boolean demonstrateEquality(Quantity<U> q1, Quantity<U> q2) {
		if(q1.equals(q2)) return true;
		return false;
	}
	
	public static <U extends IMeasurable> Quantity<U> demonstrateConversion(Quantity<U> q, U targetUnit){
		return new Quantity<>(q.convertTo(targetUnit), targetUnit);
	}
	
	public static <U extends IMeasurable> Quantity<U> demonstrateAddition(Quantity<U> q1, Quantity<U> q2){
			return q1.add(q2);
	}
	
	public static <U extends IMeasurable> Quantity<U> demonstrateAddition(Quantity<U> q1, Quantity<U> q2, U targetUnit){
		return q1.add(q2, targetUnit);
	}

	public static void main(String[] args) {
		
		//Demonstrate equality between to two quantities
		Quantity<WeightUnit> weightInGrams=new Quantity<>(1000.0,WeightUnit.GRAM);
		Quantity<WeightUnit> weightInKilograms=new Quantity<>(1.0,WeightUnit.KILOGRAM);
		boolean areEqual=demonstrateEquality(weightInGrams,weightInKilograms);
		System.out.println("Are weights equal: "+areEqual);
		
		Quantity<VolumeUnit> volumeInML=new Quantity<>(1000.0,VolumeUnit.MILLILITRE);
		Quantity<VolumeUnit> volumeInL=new Quantity<>(1.0,VolumeUnit.LITRE);
		boolean areVolumeEqual=demonstrateEquality(volumeInML, volumeInL);
		System.out.println("Are volumes equal: "+areVolumeEqual);
		
		//Demonstrate conversion between two quantities
		Quantity<WeightUnit> convertedWeight=demonstrateConversion(weightInGrams, WeightUnit.KILOGRAM);
		System.out.println(convertedWeight);
		
		Quantity<VolumeUnit> convertedVolume=demonstrateConversion(volumeInML,VolumeUnit.LITRE);
		System.out.println(convertedVolume);
		
		//Demonstration addition of two quantities and return the result in the unit of first quantity
		System.out.println(demonstrateAddition(weightInGrams, weightInKilograms));
		System.out.println(demonstrateAddition(volumeInML, volumeInL));
		
		//Demonstration addition of two quantities and return the result in the target unit
		System.out.println(demonstrateAddition(weightInGrams,weightInKilograms,WeightUnit.MILLIGRAM));
		System.out.println(demonstrateAddition(volumeInML,volumeInL,VolumeUnit.GALLON));
	}

}