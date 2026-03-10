package com.chaman.quantitymeasurement;

public class QuantityMeasurementApp {

    public static <U extends IMeasurable> boolean demonstrateEquality(
            Quantity<U> q1,
            Quantity<U> q2) {

        boolean result = q1.equals(q2);

        System.out.println(q1 + " == " + q2 + " ? " + result);

        return result;
    }

    public static <U extends IMeasurable> Quantity<U> demonstrateConversion(
            Quantity<U> quantity,
            U targetUnit) {

        Quantity<U> result = quantity.convertTo(targetUnit);

        System.out.println(quantity + " -> " + result);

        return result;
    }

    public static <U extends IMeasurable> Quantity<U> demonstrateAddition(
            Quantity<U> q1,
            Quantity<U> q2) {

        Quantity<U> result = q1.add(q2);

        System.out.println(q1 + " + " + q2 + " = " + result);

        return result;
    }

    public static <U extends IMeasurable> Quantity<U> demonstrateAddition(
            Quantity<U> q1,
            Quantity<U> q2,
            U targetUnit) {

        Quantity<U> result = q1.add(q2, targetUnit);

        System.out.println(q1 + " + " + q2 + " = " + result);

        return result;
    }
    
    public static <U extends IMeasurable> Quantity<U>
    demonstrateSubtraction(Quantity<U> q1, Quantity<U> q2) {

        Quantity<U> result = q1.subtract(q2);

        System.out.println(q1 + " - " + q2 + " = " + result);

        return result;
    }
    
    public static <U extends IMeasurable> double
    demonstrateDivision(Quantity<U> q1, Quantity<U> q2) {

        double result = q1.divide(q2);

        System.out.println(q1 + " / " + q2 + " = " + result);

        return result;
    }

    public static void main(String[] args) {

        Quantity<LengthUnit> l1 =
                new Quantity<>(1, LengthUnit.FEET);

        Quantity<LengthUnit> l2 =
                new Quantity<>(12, LengthUnit.INCHES);

        demonstrateEquality(l1, l2);

        demonstrateConversion(l1, LengthUnit.YARDS);

        demonstrateAddition(l1, l2);

        demonstrateAddition(l1, l2, LengthUnit.YARDS);


        Quantity<WeightUnit> w1 =
                new Quantity<>(1, WeightUnit.KILOGRAM);

        Quantity<WeightUnit> w2 =
                new Quantity<>(1000, WeightUnit.GRAM);

        demonstrateEquality(w1, w2);

        demonstrateConversion(w1, WeightUnit.POUND);

        demonstrateAddition(w1, w2);

        demonstrateAddition(w1, w2, WeightUnit.POUND);
        
        
        Quantity<VolumeUnit> v1 =
                new Quantity<>(1, VolumeUnit.LITRE);

        Quantity<VolumeUnit> v2 =
                new Quantity<>(1000, VolumeUnit.MILLILITRE);

        Quantity<VolumeUnit> v3 =
                new Quantity<>(1, VolumeUnit.GALLON);

        demonstrateEquality(v1, v2);

        demonstrateConversion(v1, VolumeUnit.MILLILITRE);

        demonstrateAddition(v1, v2);

        demonstrateAddition(v1, v3, VolumeUnit.MILLILITRE);
        
        Quantity<VolumeUnit> v5 =
                new Quantity<>(5, VolumeUnit.LITRE);

        Quantity<VolumeUnit> v6 =
                new Quantity<>(2, VolumeUnit.LITRE);

        demonstrateSubtraction(v5, v6);

        demonstrateDivision(v5, v6);
        
        Quantity<LengthUnit> ll1 = new Quantity<>(5, LengthUnit.FEET);
        Quantity<LengthUnit> ll2 = new Quantity<>(24, LengthUnit.INCHES);

        System.out.println("Addition: " + ll1.add(ll2));

        Quantity<TemperatureUnit> t1 =
                new Quantity<>(100, TemperatureUnit.CELSIUS);

        Quantity<TemperatureUnit> t2 =
                new Quantity<>(212, TemperatureUnit.FAHRENHEIT);

        System.out.println("Temperature Equal: " + t1.equals(t2));

        System.out.println("Converted: " +
                t2.convertTo(TemperatureUnit.CELSIUS));

        try {
            t1.add(t2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}