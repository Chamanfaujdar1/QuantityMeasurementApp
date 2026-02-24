package com.chaman.quantitymeasurement;

public class QuantityMeasurementApp {

    public static void main(String[] args) {
        Quantity feet1 = new Quantity(1.0, LengthUnit.FEET);
        Quantity feet2 = new Quantity(1.0, LengthUnit.FEET);

        System.out.println("Input: Quantity(1.0, \"feet\") and Quantity(1.0, \"feet\")");
        if (feet1.equals(feet2)) {
            System.out.println("Output: Equal (true)");
        } else {
            System.out.println("Output: Not Equal (false)");
        }

        Quantity inch1 = new Quantity(1.0, LengthUnit.INCH);
        Quantity inch2 = new Quantity(1.0, LengthUnit.INCH);

        System.out.println("Input: Quantity(1.0, \"inch\") and Quantity(1.0, \"inch\")");
        if (inch1.equals(inch2)) {
            System.out.println("Output: Equal (true)");
        } else {
            System.out.println("Output: Not Equal (false)");
        }

        Quantity feet3 = new Quantity(1.0, LengthUnit.FEET);
        Quantity inch3 = new Quantity(12.0, LengthUnit.INCH);
        System.out.println("Input: Quantity(1.0, \"feet\") and Quantity(12.0, \"inch\")");
        if (feet3.equals(inch3)) {
            System.out.println("Output: Equal (true)");
        } else {
            System.out.println("Output: Not Equal (false)");
        }

        Quantity yard1 = new Quantity(1.0, LengthUnit.YARD);
        Quantity feet4 = new Quantity(3.0, LengthUnit.FEET);
        System.out.println("Input: Quantity(1.0, YARDS) and Quantity(3.0, FEET)");
        if (yard1.equals(feet4)) {
            System.out.println("Output: Equal (true)");
        } else {
            System.out.println("Output: Not Equal (false)");
        }

        Quantity cm1 = new Quantity(1.0, LengthUnit.CENTIMETER);
        Quantity inch4 = new Quantity(0.393701, LengthUnit.INCH);
        System.out.println("Input: Quantity(1.0, CENTIMETERS) and Quantity(0.393701, INCHES)");
        if (cm1.equals(inch4)) {
            System.out.println("Output: Equal (true)");
        } else {
            System.out.println("Output: Not Equal (false)");
        }
    }
}