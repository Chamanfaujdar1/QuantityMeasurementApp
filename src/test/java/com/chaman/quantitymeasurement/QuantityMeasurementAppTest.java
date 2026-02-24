package com.chaman.quantitymeasurement;

import org.junit.Test;
import static org.junit.Assert.*;

public class QuantityMeasurementAppTest {

    @Test
    public void testEquality_FeetToFeet_SameValue() {
        Quantity feet1 = new Quantity(1.0, LengthUnit.FEET);
        Quantity feet2 = new Quantity(1.0, LengthUnit.FEET);
        assertTrue(feet1.equals(feet2));
    }

    @Test
    public void testEquality_InchToInch_SameValue() {
        Quantity inch1 = new Quantity(1.0, LengthUnit.INCH);
        Quantity inch2 = new Quantity(1.0, LengthUnit.INCH);
        assertTrue(inch1.equals(inch2));
    }

    @Test
    public void testEquality_InchToFeet_EquivalentValue() {
        Quantity inch = new Quantity(12.0, LengthUnit.INCH);
        Quantity feet = new Quantity(1.0, LengthUnit.FEET);
        assertTrue(inch.equals(feet));
    }

    @Test
    public void testEquality_FeetToFeet_DifferentValue() {
        Quantity feet1 = new Quantity(1.0, LengthUnit.FEET);
        Quantity feet2 = new Quantity(2.0, LengthUnit.FEET);
        assertFalse(feet1.equals(feet2));
    }

    @Test
    public void testEquality_InchToInch_DifferentValue() {
        Quantity inch1 = new Quantity(1.0, LengthUnit.INCH);
        Quantity inch2 = new Quantity(2.0, LengthUnit.INCH);
        assertFalse(inch1.equals(inch2));
    }

    @Test
    public void testEquality_NullComparison() {
        Quantity feet = new Quantity(1.0, LengthUnit.FEET);
        assertFalse(feet.equals(null));
    }

    @Test
    public void testEquality_SameReference() {
        Quantity feet = new Quantity(1.0, LengthUnit.FEET);
        assertTrue(feet.equals(feet));
    }

    @Test
    public void testEquality_NonNumericInput() {
        // Technically strict type check in Java prevents passing non-Quantity to
        // `equals` if we used a specific type,
        // but `equals` takes Object.
        Quantity feet = new Quantity(1.0, LengthUnit.FEET);
        Object nonQuantity = new Object();
        assertFalse(feet.equals(nonQuantity));
    }
}