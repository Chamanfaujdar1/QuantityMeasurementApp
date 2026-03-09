package com.chaman.quantitymeasurement;

public class QuantityMeasurementApp {
	
	public static void demonstrateLengthComparison(Length l1, Length l2) {
		if(l1.equals(l2)) {
			System.out.println("Both lengths are equal (true)");
		}else {
			System.out.println("Both lengths are not equal (false)");
		}
	}
	
	public static void demonstrateWeightComparison(Weight w1, Weight w2) {
		if(w1.equals(w2)) {
			System.out.println("Both weights are equal (true)");
		}else {
			System.out.println("Both weights are not equal (false)");
		}
	}
	
	public static boolean demonstrateLengthEquality(Length l1, Length l2) {
		return l1.equals(l2);
	}
	
	public static boolean demonstrateWeightEquality(Weight w1, Weight w2) {
		return w1.equals(w2);
	}
	
	public static double convert(double value,LengthUnit sourceUnit, LengthUnit targetUnit) {
		if(!Double.isFinite(value)) throw new IllegalArgumentException("value should be finite");
		Length l=new Length(value,sourceUnit);
		return l.convertTo(targetUnit);
	}
	
	public static double convert(double value,WeightUnit sourceUnit, WeightUnit targetUnit) {
		if(!Double.isFinite(value)) throw new IllegalArgumentException("value should be finite");
		Weight l=new Weight(value,sourceUnit);
		return l.convertTo(targetUnit);
	}
	
	public static void demonstrateLengthAddition(Length l1, Length l2) {
		Length additionResult=l1.add(l2);
		System.out.println(l1+" + "+l2+" = "+additionResult);
	}
	
	public static void demonstrateWeightAddition(Weight w1, Weight w2) {
		Weight additionResult=w1.add(w2);
		System.out.println(w1+" + "+w2+" = "+additionResult);
	}
	
	public static void demonstrateTargetLengthAddition(Length l1, Length l2, LengthUnit unit) {
		Length result=l1.add(l2, unit);
		System.out.println("("+l1+" + "+l2+") "+unit+" = "+result);
	}
	
	public static void demonstrateTargetWeightAddition(Weight w1, Weight w2, WeightUnit unit) {
		Weight result=w1.add(w2, unit);
		System.out.println("("+w1+" + "+w2+") "+unit+" = "+result);
	}

	public static void main(String[] args) {
		
		//length
		
		demonstrateLengthAddition(new Length(1.0,LengthUnit.FEET),
								  new Length(36.0,LengthUnit.INCHES));
		
		demonstrateTargetLengthAddition(new Length(1.0,LengthUnit.FEET),
				  						new Length(36.0,LengthUnit.INCHES),
				  						LengthUnit.YARDS);
		
		System.out.print("Length coversion: ");
		System.out.println(convert(1,LengthUnit.FEET,LengthUnit.INCHES)+
									" "+LengthUnit.INCHES);
		
		System.out.print("Comparison btw Feet & Inch: ");
		demonstrateLengthComparison(new Length(1.0,LengthUnit.FEET),
									new Length(12.0,LengthUnit.INCHES));
		
		System.out.print("Comparison btw Yard & Inch: ");
		demonstrateLengthComparison(new Length(1.0,LengthUnit.YARDS),
									new Length(36.0,LengthUnit.INCHES));
		
		System.out.print("Comparison btw Centimeter & Inch: ");
		demonstrateLengthComparison(new Length(100.0,LengthUnit.CENTIMETERS),
									new Length(39.3701,LengthUnit.INCHES));
		
		System.out.print("Comparison btw Feet & Yard: ");
		demonstrateLengthComparison(new Length(3.0,LengthUnit.FEET),
									new Length(1.0,LengthUnit.YARDS));
		
		System.out.print("Comparison btw Centimeter & Feet: ");
		demonstrateLengthComparison(new Length(30.48,LengthUnit.CENTIMETERS),
									new Length(1.0,LengthUnit.FEET));
		
		//weight
		
		demonstrateWeightAddition(new Weight(1.0, WeightUnit.KILOGRAM),
				new Weight(1000.0, WeightUnit.GRAM));

		demonstrateTargetWeightAddition(new Weight(1.0, WeightUnit.KILOGRAM),
				new Weight(1000.0, WeightUnit.GRAM),
				WeightUnit.TONNE);

		System.out.print("Weight conversion: ");
		System.out.println(convert(1, WeightUnit.KILOGRAM, WeightUnit.GRAM) +
				" " + WeightUnit.GRAM);

		System.out.print("Comparison btw Kilogram & Gram: ");
		demonstrateWeightComparison(new Weight(1.0, WeightUnit.KILOGRAM),
				new Weight(1000.0, WeightUnit.GRAM));

		System.out.print("Comparison btw Kilogram & Pound: ");
		demonstrateWeightComparison(new Weight(1.0, WeightUnit.KILOGRAM),
				new Weight(2.20462, WeightUnit.POUND));

		System.out.print("Comparison btw Gram & Ounce: ");
		demonstrateWeightComparison(new Weight(28.3495, WeightUnit.GRAM),
				new Weight(1.0, WeightUnit.OUNCE));

		System.out.print("Comparison btw Kilogram & Ounce: ");
		demonstrateWeightComparison(new Weight(1.0, WeightUnit.KILOGRAM),
				new Weight(35.274, WeightUnit.OUNCE));

		System.out.print("Comparison btw Tonne & Kilogram: ");
		demonstrateWeightComparison(new Weight(1.0, WeightUnit.TONNE),
				new Weight(1000.0, WeightUnit.KILOGRAM));

		System.out.print("Comparison btw Gram & Milligram: ");
		demonstrateWeightComparison(new Weight(1.0, WeightUnit.GRAM),
				new Weight(1000.0, WeightUnit.MILLIGRAM));
	}

}