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

    @Test
    public void testEquality_YardToYard_SameValue() {
        Quantity yard1 = new Quantity(1.0, LengthUnit.YARD);
        Quantity yard2 = new Quantity(1.0, LengthUnit.YARD);
        assertTrue(yard1.equals(yard2));
    }

    @Test
    public void testEquality_YardToYard_DifferentValue() {
        Quantity yard1 = new Quantity(1.0, LengthUnit.YARD);
        Quantity yard2 = new Quantity(2.0, LengthUnit.YARD);
        assertFalse(yard1.equals(yard2));
    }

    @Test
    public void testEquality_YardToFeet_EquivalentValue() {
        Quantity yard = new Quantity(1.0, LengthUnit.YARD);
        Quantity feet = new Quantity(3.0, LengthUnit.FEET);
        assertTrue(yard.equals(feet));
    }

    @Test
    public void testEquality_FeetToYard_EquivalentValue() {
        Quantity feet = new Quantity(3.0, LengthUnit.FEET);
        Quantity yard = new Quantity(1.0, LengthUnit.YARD);
        assertTrue(feet.equals(yard));
    }

    @Test
    public void testEquality_YardToInches_EquivalentValue() {
        Quantity yard = new Quantity(1.0, LengthUnit.YARD);
        Quantity inch = new Quantity(36.0, LengthUnit.INCH);
        assertTrue(yard.equals(inch));
    }

    @Test
    public void testEquality_InchesToYard_EquivalentValue() {
        Quantity inch = new Quantity(36.0, LengthUnit.INCH);
        Quantity yard = new Quantity(1.0, LengthUnit.YARD);
        assertTrue(inch.equals(yard));
    }

    @Test
    public void testEquality_YardToFeet_NonEquivalentValue() {
        Quantity yard = new Quantity(1.0, LengthUnit.YARD);
        Quantity feet = new Quantity(2.0, LengthUnit.FEET);
        assertFalse(yard.equals(feet));
    }

    @Test
    public void testEquality_centimetersToInches_EquivalentValue() {
        Quantity cm = new Quantity(1.0, LengthUnit.CENTIMETER);
        Quantity inch = new Quantity(0.393701, LengthUnit.INCH);
        assertTrue(cm.equals(inch));
    }

    @Test
    public void testEquality_centimetersToFeet_NonEquivalentValue() {
        Quantity cm = new Quantity(1.0, LengthUnit.CENTIMETER);
        Quantity feet = new Quantity(1.0, LengthUnit.FEET);
        assertFalse(cm.equals(feet));
    }

    @Test
    public void testEquality_MultiUnit_TransitiveProperty() {
        Quantity yard = new Quantity(1.0, LengthUnit.YARD);
        Quantity feet = new Quantity(3.0, LengthUnit.FEET);
        Quantity inch = new Quantity(36.0, LengthUnit.INCH);

        assertTrue(yard.equals(feet));
        assertTrue(feet.equals(inch));
        assertTrue(yard.equals(inch));
    }
}