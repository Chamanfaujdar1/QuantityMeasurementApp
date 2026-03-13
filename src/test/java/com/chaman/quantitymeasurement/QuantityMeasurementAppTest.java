package com.chaman.quantitymeasurement;

import org.junit.Test;
import static org.junit.Assert.*;

public class QuantityMeasurementAppTest {

    // --- UC14: Temperature Measurement Tests ---

    @Test
    public void testTemperatureEquality_CelsiusToCelsius_SameValue() {
        assertTrue(new Quantity<>(0.0, TemperatureUnit.CELSIUS).equals(new Quantity<>(0.0, TemperatureUnit.CELSIUS)));
    }

    @Test
    public void testTemperatureEquality_FahrenheitToFahrenheit_SameValue() {
        assertTrue(new Quantity<>(32.0, TemperatureUnit.FAHRENHEIT)
                .equals(new Quantity<>(32.0, TemperatureUnit.FAHRENHEIT)));
    }

    @Test
    public void testTemperatureEquality_CelsiusToFahrenheit_0Celsius32Fahrenheit() {
        assertTrue(
                new Quantity<>(0.0, TemperatureUnit.CELSIUS).equals(new Quantity<>(32.0, TemperatureUnit.FAHRENHEIT)));
    }

    @Test
    public void testTemperatureEquality_CelsiusToFahrenheit_100Celsius212Fahrenheit() {
        assertTrue(new Quantity<>(100.0, TemperatureUnit.CELSIUS)
                .equals(new Quantity<>(212.0, TemperatureUnit.FAHRENHEIT)));
    }

    @Test
    public void testTemperatureEquality_CelsiusToFahrenheit_Negative40Equal() {
        assertTrue(new Quantity<>(-40.0, TemperatureUnit.CELSIUS)
                .equals(new Quantity<>(-40.0, TemperatureUnit.FAHRENHEIT)));
    }

    @Test
    public void testTemperatureEquality_SymmetricProperty() {
        Quantity<TemperatureUnit> c = new Quantity<>(100.0, TemperatureUnit.CELSIUS);
        Quantity<TemperatureUnit> f = new Quantity<>(212.0, TemperatureUnit.FAHRENHEIT);
        assertTrue(c.equals(f));
        assertTrue(f.equals(c));
    }

    @Test
    public void testTemperatureEquality_ReflexiveProperty() {
        Quantity<TemperatureUnit> f = new Quantity<>(212.0, TemperatureUnit.FAHRENHEIT);
        assertTrue(f.equals(f));
    }

    @Test
    public void testTemperatureConversion_CelsiusToFahrenheit_VariousValues() {
        assertEquals(122.0,
                new Quantity<>(50.0, TemperatureUnit.CELSIUS).convertTo(TemperatureUnit.FAHRENHEIT).getValue(), 1e-6);
        assertEquals(-4.0,
                new Quantity<>(-20.0, TemperatureUnit.CELSIUS).convertTo(TemperatureUnit.FAHRENHEIT).getValue(), 1e-6);
    }

    @Test
    public void testTemperatureConversion_FahrenheitToCelsius_VariousValues() {
        assertEquals(50.0,
                new Quantity<>(122.0, TemperatureUnit.FAHRENHEIT).convertTo(TemperatureUnit.CELSIUS).getValue(), 1e-6);
        assertEquals(-20.0,
                new Quantity<>(-4.0, TemperatureUnit.FAHRENHEIT).convertTo(TemperatureUnit.CELSIUS).getValue(), 1e-6);
    }

    @Test
    public void testTemperatureConversion_RoundTrip_PreservesValue() {
        Quantity<TemperatureUnit> f1 = new Quantity<>(100.0, TemperatureUnit.FAHRENHEIT);
        Quantity<TemperatureUnit> c = f1.convertTo(TemperatureUnit.CELSIUS);
        Quantity<TemperatureUnit> f2 = c.convertTo(TemperatureUnit.FAHRENHEIT);
        assertEquals(f1.getValue(), f2.getValue(), 1e-6);
    }

    @Test
    public void testTemperatureConversion_SameUnit() {
        assertEquals(100.0,
                new Quantity<>(100.0, TemperatureUnit.CELSIUS).convertTo(TemperatureUnit.CELSIUS).getValue(), 1e-6);
    }

    @Test
    public void testTemperatureConversion_ZeroValue() {
        assertEquals(32.0,
                new Quantity<>(0.0, TemperatureUnit.CELSIUS).convertTo(TemperatureUnit.FAHRENHEIT).getValue(), 1e-6);
    }

    @Test
    public void testTemperatureConversion_NegativeValues() {
        assertEquals(-40.0,
                new Quantity<>(-40.0, TemperatureUnit.CELSIUS).convertTo(TemperatureUnit.FAHRENHEIT).getValue(), 1e-6);
    }

    @Test
    public void testTemperatureConversion_LargeValues() {
        assertEquals(1832.0,
                new Quantity<>(1000.0, TemperatureUnit.CELSIUS).convertTo(TemperatureUnit.FAHRENHEIT).getValue(), 1e-6);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testTemperatureUnsupportedOperation_Add() {
        new Quantity<>(100.0, TemperatureUnit.CELSIUS).add(new Quantity<>(50.0, TemperatureUnit.CELSIUS));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testTemperatureUnsupportedOperation_Subtract() {
        new Quantity<>(100.0, TemperatureUnit.CELSIUS).subtract(new Quantity<>(50.0, TemperatureUnit.CELSIUS));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testTemperatureUnsupportedOperation_Divide() {
        new Quantity<>(100.0, TemperatureUnit.CELSIUS).divide(new Quantity<>(50.0, TemperatureUnit.CELSIUS));
    }

    @Test
    public void testTemperatureUnsupportedOperation_ErrorMessage() {
        try {
            new Quantity<>(100.0, TemperatureUnit.CELSIUS).add(new Quantity<>(50.0, TemperatureUnit.CELSIUS));
            fail("Expected UnsupportedOperationException");
        } catch (UnsupportedOperationException e) {
            assertEquals("Temperature does not support add operations.", e.getMessage());
        }
    }

    @Test
    public void testTemperatureVsLengthIncompatibility() {
        assertFalse(new Quantity<>(100.0, TemperatureUnit.CELSIUS).equals(new Quantity<>(100.0, LengthUnit.FEET)));
    }

    @Test
    public void testTemperatureVsWeightIncompatibility() {
        assertFalse(new Quantity<>(50.0, TemperatureUnit.CELSIUS).equals(new Quantity<>(50.0, WeightUnit.KILOGRAM)));
    }

    @Test
    public void testTemperatureVsVolumeIncompatibility() {
        assertFalse(new Quantity<>(25.0, TemperatureUnit.CELSIUS).equals(new Quantity<>(25.0, VolumeUnit.LITRE)));
    }

    @Test
    public void testOperationSupportMethods_TemperatureUnitAddition() {
        assertFalse(TemperatureUnit.CELSIUS.supportsArithmetic());
    }

    @Test
    public void testOperationSupportMethods_TemperatureUnitDivision() {
        assertFalse(TemperatureUnit.FAHRENHEIT.supportsArithmetic());
    }

    @Test
    public void testOperationSupportMethods_LengthUnitAddition() {
        assertTrue(LengthUnit.FEET.supportsArithmetic());
    }

    @Test
    public void testOperationSupportMethods_WeightUnitDivision() {
        assertTrue(WeightUnit.KILOGRAM.supportsArithmetic());
    }

    @Test
    public void testIMeasurableInterface_Evolution_BackwardCompatible() {
        assertTrue(LengthUnit.FEET.supportsArithmetic());
        assertTrue(WeightUnit.GRAM.supportsArithmetic());
        assertTrue(VolumeUnit.GALLON.supportsArithmetic());
    }

    @Test
    public void testTemperatureUnit_NonLinearConversion() {
        assertEquals(37.77777777777778,
                new Quantity<>(100.0, TemperatureUnit.FAHRENHEIT).convertTo(TemperatureUnit.CELSIUS).getValue(), 1e-6);
    }

    @Test
    public void testTemperatureUnit_AllConstants() {
        assertNotNull(TemperatureUnit.CELSIUS);
        assertNotNull(TemperatureUnit.FAHRENHEIT);
        assertNotNull(TemperatureUnit.KELVIN);
    }

    @Test
    public void testTemperatureUnit_NameMethod() {
        assertEquals("CELSIUS", TemperatureUnit.CELSIUS.getUnitName());
    }

    @Test
    public void testTemperatureUnit_ConversionFactor() {
        assertEquals(1.0, TemperatureUnit.CELSIUS.getConversionFactor(), 1e-6);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testTemperatureNullUnitValidation() {
        new Quantity<>(100.0, null);
    }

    @Test
    public void testTemperatureNullOperandValidation_InComparison() {
        assertFalse(new Quantity<>(100.0, TemperatureUnit.CELSIUS).equals(null));
    }

    @Test
    public void testTemperatureDifferentValuesInequality() {
        assertFalse(
                new Quantity<>(50.0, TemperatureUnit.CELSIUS).equals(new Quantity<>(100.0, TemperatureUnit.CELSIUS)));
    }

    @Test
    public void testTemperatureConversionPrecision_Epsilon() {
        double result = new Quantity<>(98.6, TemperatureUnit.FAHRENHEIT).convertTo(TemperatureUnit.CELSIUS).getValue();
        assertEquals(37.0, result, 1e-6);
    }

    @Test
    public void testTemperatureConversionEdgeCase_VerySmallDifference() {
        Quantity<TemperatureUnit> q1 = new Quantity<>(0.0000001, TemperatureUnit.CELSIUS);
        Quantity<TemperatureUnit> q2 = new Quantity<>(0.0000001, TemperatureUnit.CELSIUS);
        assertTrue(q1.equals(q2));
    }

    @Test
    public void testTemperatureEnumImplementsIMeasurable() {
        assertTrue(TemperatureUnit.CELSIUS instanceof IMeasurable);
    }

    @Test
    public void testTemperatureDefaultMethodInheritance() {
        assertTrue(LengthUnit.FEET.supportsArithmetic());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testTemperatureCrossCategoryAdditionAttempt() {
        Quantity<?> temp = new Quantity<>(10.0, TemperatureUnit.CELSIUS);
        Quantity<?> length = new Quantity<>(10.0, LengthUnit.FEET);
        ((Quantity<IMeasurable>) temp).add((Quantity<IMeasurable>) length);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testTemperatureValidateOperationSupport_MethodBehavior() {
        TemperatureUnit.CELSIUS.validateOperationSupport("addition");
    }

    @Test
    public void testTemperatureIntegrationWithGenericQuantity() {
        Quantity<TemperatureUnit> q = new Quantity<>(100.0, TemperatureUnit.CELSIUS);
        assertEquals(100.0, q.getValue(), 1e-6);
        assertEquals(TemperatureUnit.CELSIUS, q.getUnit());
    }

    // --- UC1-UC10: Length and Weight Tests ---

    @Test
    public void testEquality_FeetToFeet_SameValue() {
        Quantity<LengthUnit> feet1 = new Quantity<>(1.0, LengthUnit.FEET);
        Quantity<LengthUnit> feet2 = new Quantity<>(1.0, LengthUnit.FEET);
        assertTrue(feet1.equals(feet2));
    }

    @Test
    public void testEquality_InchToInch_SameValue() {
        Quantity<LengthUnit> inch1 = new Quantity<>(1.0, LengthUnit.INCH);
        Quantity<LengthUnit> inch2 = new Quantity<>(1.0, LengthUnit.INCH);
        assertTrue(inch1.equals(inch2));
    }

    @Test
    public void testEquality_InchToFeet_EquivalentValue() {
        Quantity<LengthUnit> inch = new Quantity<>(12.0, LengthUnit.INCH);
        Quantity<LengthUnit> feet = new Quantity<>(1.0, LengthUnit.FEET);
        assertTrue(inch.equals(feet));
    }

    @Test
    public void testEquality_FeetToFeet_DifferentValue() {
        Quantity<LengthUnit> feet1 = new Quantity<>(1.0, LengthUnit.FEET);
        Quantity<LengthUnit> feet2 = new Quantity<>(2.0, LengthUnit.FEET);
        assertFalse(feet1.equals(feet2));
    }

    @Test
    public void testEquality_InchToInch_DifferentValue() {
        Quantity<LengthUnit> inch1 = new Quantity<>(1.0, LengthUnit.INCH);
        Quantity<LengthUnit> inch2 = new Quantity<>(2.0, LengthUnit.INCH);
        assertFalse(inch1.equals(inch2));
    }

    @Test
    public void testEquality_NullComparison() {
        Quantity<LengthUnit> feet = new Quantity<>(1.0, LengthUnit.FEET);
        assertFalse(feet.equals(null));
    }

    @Test
    public void testEquality_SameReference() {
        Quantity<LengthUnit> feet = new Quantity<>(1.0, LengthUnit.FEET);
        assertTrue(feet.equals(feet));
    }

    @Test
    public void testEquality_NonNumericInput() {
        Quantity<LengthUnit> feet = new Quantity<>(1.0, LengthUnit.FEET);
        Object nonQuantity = new Object();
        assertFalse(feet.equals(nonQuantity));
    }

    @Test
    public void testEquality_YardToYard_SameValue() {
        Quantity<LengthUnit> yard1 = new Quantity<>(1.0, LengthUnit.YARD);
        Quantity<LengthUnit> yard2 = new Quantity<>(1.0, LengthUnit.YARD);
        assertTrue(yard1.equals(yard2));
    }

    @Test
    public void testEquality_YardToYard_DifferentValue() {
        Quantity<LengthUnit> yard1 = new Quantity<>(1.0, LengthUnit.YARD);
        Quantity<LengthUnit> yard2 = new Quantity<>(2.0, LengthUnit.YARD);
        assertFalse(yard1.equals(yard2));
    }

    @Test
    public void testEquality_YardToFeet_EquivalentValue() {
        Quantity<LengthUnit> yard = new Quantity<>(1.0, LengthUnit.YARD);
        Quantity<LengthUnit> feet = new Quantity<>(3.0, LengthUnit.FEET);
        assertTrue(yard.equals(feet));
    }

    @Test
    public void testEquality_FeetToYard_EquivalentValue() {
        Quantity<LengthUnit> feet = new Quantity<>(3.0, LengthUnit.FEET);
        Quantity<LengthUnit> yard = new Quantity<>(1.0, LengthUnit.YARD);
        assertTrue(feet.equals(yard));
    }

    @Test
    public void testEquality_YardToInches_EquivalentValue() {
        Quantity<LengthUnit> yard = new Quantity<>(1.0, LengthUnit.YARD);
        Quantity<LengthUnit> inch = new Quantity<>(36.0, LengthUnit.INCH);
        assertTrue(yard.equals(inch));
    }

    @Test
    public void testEquality_InchesToYard_EquivalentValue() {
        Quantity<LengthUnit> inch = new Quantity<>(36.0, LengthUnit.INCH);
        Quantity<LengthUnit> yard = new Quantity<>(1.0, LengthUnit.YARD);
        assertTrue(inch.equals(yard));
    }

    @Test
    public void testEquality_YardToFeet_NonEquivalentValue() {
        Quantity<LengthUnit> yard = new Quantity<>(1.0, LengthUnit.YARD);
        Quantity<LengthUnit> feet = new Quantity<>(2.0, LengthUnit.FEET);
        assertFalse(yard.equals(feet));
    }

    @Test
    public void testEquality_centimetersToInches_EquivalentValue() {
        Quantity<LengthUnit> cm = new Quantity<>(1.0, LengthUnit.CENTIMETER);
        Quantity<LengthUnit> inch = new Quantity<>(0.393701, LengthUnit.INCH);
        assertTrue(cm.equals(inch));
    }

    @Test
    public void testEquality_centimetersToFeet_NonEquivalentValue() {
        Quantity<LengthUnit> cm = new Quantity<>(1.0, LengthUnit.CENTIMETER);
        Quantity<LengthUnit> feet = new Quantity<>(1.0, LengthUnit.FEET);
        assertFalse(cm.equals(feet));
    }

    @Test
    public void testEquality_MultiUnit_TransitiveProperty() {
        Quantity<LengthUnit> yard = new Quantity<>(1.0, LengthUnit.YARD);
        Quantity<LengthUnit> feet = new Quantity<>(3.0, LengthUnit.FEET);
        Quantity<LengthUnit> inch = new Quantity<>(36.0, LengthUnit.INCH);
        assertTrue(yard.equals(feet));
        assertTrue(feet.equals(inch));
        assertTrue(yard.equals(inch));
    }

    // --- Conversion Tests (UC5) ---

    @Test
    public void testConversion_FeetToInches() {
        double result = Quantity.convert(1.0, LengthUnit.FEET, LengthUnit.INCH);
        assertEquals(12.0, result, 1e-6);
    }

    @Test
    public void testConversion_InchesToFeet() {
        double result = Quantity.convert(24.0, LengthUnit.INCH, LengthUnit.FEET);
        assertEquals(2.0, result, 1e-6);
    }

    @Test
    public void testConversion_YardsToInches() {
        double result = Quantity.convert(1.0, LengthUnit.YARD, LengthUnit.INCH);
        assertEquals(36.0, result, 1e-6);
    }

    @Test
    public void testConversion_InchesToYards() {
        double result = Quantity.convert(72.0, LengthUnit.INCH, LengthUnit.YARD);
        assertEquals(2.0, result, 1e-6);
    }

    @Test
    public void testConversion_CentimetersToInches() {
        double result = Quantity.convert(2.54, LengthUnit.CENTIMETER, LengthUnit.INCH);
        assertEquals(1.0, result, 1e-6);
    }

    @Test
    public void testConversion_FeetToYard() {
        double result = Quantity.convert(6.0, LengthUnit.FEET, LengthUnit.YARD);
        assertEquals(2.0, result, 1e-6);
    }

    @Test
    public void testConversion_RoundTrip_PreservesValue() {
        double v = 5.5;
        double feetToInch = Quantity.convert(v, LengthUnit.FEET, LengthUnit.INCH);
        double inchToFeet = Quantity.convert(feetToInch, LengthUnit.INCH, LengthUnit.FEET);
        assertEquals(v, inchToFeet, 1e-6);
        double yardToCm = Quantity.convert(v, LengthUnit.YARD, LengthUnit.CENTIMETER);
        double cmToYard = Quantity.convert(yardToCm, LengthUnit.CENTIMETER, LengthUnit.YARD);
        assertEquals(v, cmToYard, 1e-6);
    }

    @Test
    public void testConversion_ZeroValue() {
        double result = Quantity.convert(0.0, LengthUnit.FEET, LengthUnit.INCH);
        assertEquals(0.0, result, 1e-6);
    }

    @Test
    public void testConversion_NegativeValue() {
        double result = Quantity.convert(-1.0, LengthUnit.FEET, LengthUnit.INCH);
        assertEquals(-12.0, result, 1e-6);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConversion_InvalidUnit_NullSourceThrows() {
        Quantity.convert(1.0, null, LengthUnit.INCH);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConversion_InvalidUnit_NullTargetThrows() {
        Quantity.convert(1.0, LengthUnit.FEET, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConversion_NaN_Throws() {
        Quantity.convert(Double.NaN, LengthUnit.FEET, LengthUnit.INCH);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConversion_Infinite_Throws() {
        Quantity.convert(Double.POSITIVE_INFINITY, LengthUnit.FEET, LengthUnit.INCH);
    }

    @Test
    public void testInstanceConversion_FeetToInches() {
        Quantity<LengthUnit> feet = new Quantity<>(1.0, LengthUnit.FEET);
        Quantity<LengthUnit> inInches = feet.convertTo(LengthUnit.INCH);
        assertEquals(12.0, inInches.getValue(), 1e-6);
        assertEquals(LengthUnit.INCH, inInches.getUnit());
        assertEquals(1.0, feet.getValue(), 1e-6);
        assertEquals(LengthUnit.FEET, feet.getUnit());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testQuantityCreation_NullUnit_Throws() {
        new Quantity<>(1.0, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testQuantityCreation_NaN_Throws() {
        new Quantity<>(Double.NaN, LengthUnit.FEET);
    }

    // --- UC11: Volume Measurement Tests ---
    @Test
    public void testEquality_LitreToLitre_SameValue() {
        assertTrue(new Quantity<>(1.0, VolumeUnit.LITRE).equals(new Quantity<>(1.0, VolumeUnit.LITRE)));
    }

    @Test
    public void testEquality_LitreToLitre_DifferentValue() {
        assertFalse(new Quantity<>(1.0, VolumeUnit.LITRE).equals(new Quantity<>(2.0, VolumeUnit.LITRE)));
    }

    @Test
    public void testEquality_LitreToMillilitre_EquivalentValue() {
        assertTrue(new Quantity<>(1.0, VolumeUnit.LITRE).equals(new Quantity<>(1000.0, VolumeUnit.MILLILITRE)));
    }

    @Test
    public void testEquality_LitreToGallon_EquivalentValue() {
        assertTrue(new Quantity<>(1.0, VolumeUnit.LITRE).equals(new Quantity<>(0.264172, VolumeUnit.GALLON)));
    }

    @Test
    public void testEquality_GallonToLitre_EquivalentValue() {
        assertTrue(new Quantity<>(1.0, VolumeUnit.GALLON).equals(new Quantity<>(3.78541, VolumeUnit.LITRE)));
    }

    @Test
    public void testEquality_VolumeVsLength_Incompatible() {
        assertFalse(new Quantity<>(1.0, VolumeUnit.LITRE).equals(new Quantity<>(1.0, LengthUnit.FEET)));
    }

    @Test
    public void testConversion_LitreToMillilitre() {
        Quantity<VolumeUnit> result = new Quantity<>(1.0, VolumeUnit.LITRE).convertTo(VolumeUnit.MILLILITRE);
        assertEquals(1000.0, result.getValue(), 1e-6);
        assertEquals(VolumeUnit.MILLILITRE, result.getUnit());
    }

    @Test
    public void testConversion_GallonToLitre() {
        Quantity<VolumeUnit> result = new Quantity<>(1.0, VolumeUnit.GALLON).convertTo(VolumeUnit.LITRE);
        assertEquals(3.78541, result.getValue(), 1e-6);
        assertEquals(VolumeUnit.LITRE, result.getUnit());
    }

    @Test
    public void testAddition_SameUnit_LitrePlusLitre() {
        Quantity<VolumeUnit> result = new Quantity<>(1.0, VolumeUnit.LITRE).add(new Quantity<>(2.0, VolumeUnit.LITRE));
        assertEquals(3.0, result.getValue(), 1e-6);
        assertEquals(VolumeUnit.LITRE, result.getUnit());
    }

    @Test
    public void testAddition_CrossUnit_LitrePlusMillilitre() {
        Quantity<VolumeUnit> result = new Quantity<>(1.0, VolumeUnit.LITRE)
                .add(new Quantity<>(1000.0, VolumeUnit.MILLILITRE));
        assertEquals(2.0, result.getValue(), 1e-6);
        assertEquals(VolumeUnit.LITRE, result.getUnit());
    }

    @Test
    public void testAddition_ExplicitTargetUnit_Millilitre() {
        Quantity<VolumeUnit> result = new Quantity<>(1.0, VolumeUnit.LITRE)
                .add(new Quantity<>(1000.0, VolumeUnit.MILLILITRE), VolumeUnit.MILLILITRE);
        assertEquals(2000.0, result.getValue(), 1e-6);
        assertEquals(VolumeUnit.MILLILITRE, result.getUnit());
    }

    @Test
    public void testVolumeUnitEnum_LitreConstant() {
        assertEquals(1.0, VolumeUnit.LITRE.getConversionFactor(), 1e-6);
    }

    // --- UC12: Subtraction Tests ---

    @Test
    public void testSubtraction_SameUnit_FeetMinusFeet() {
        Quantity<LengthUnit> result = new Quantity<>(10.0, LengthUnit.FEET)
                .subtract(new Quantity<>(5.0, LengthUnit.FEET));
        assertEquals(5.0, result.getValue(), 1e-6);
        assertEquals(LengthUnit.FEET, result.getUnit());
    }

    @Test
    public void testSubtraction_SameUnit_LitreMinusLitre() {
        Quantity<VolumeUnit> result = new Quantity<>(10.0, VolumeUnit.LITRE)
                .subtract(new Quantity<>(3.0, VolumeUnit.LITRE));
        assertEquals(7.0, result.getValue(), 1e-6);
        assertEquals(VolumeUnit.LITRE, result.getUnit());
    }

    @Test
    public void testSubtraction_CrossUnit_FeetMinusInches() {
        Quantity<LengthUnit> result = new Quantity<>(10.0, LengthUnit.FEET)
                .subtract(new Quantity<>(6.0, LengthUnit.INCH));
        assertEquals(9.5, result.getValue(), 1e-6);
        assertEquals(LengthUnit.FEET, result.getUnit());
    }

    @Test
    public void testSubtraction_CrossUnit_InchesMinusFeet() {
        Quantity<LengthUnit> result = new Quantity<>(120.0, LengthUnit.INCH)
                .subtract(new Quantity<>(5.0, LengthUnit.FEET));
        assertEquals(60.0, result.getValue(), 1e-6);
        assertEquals(LengthUnit.INCH, result.getUnit());
    }

    @Test
    public void testSubtraction_ExplicitTargetUnit_Feet() {
        Quantity<LengthUnit> result = new Quantity<>(10.0, LengthUnit.FEET)
                .subtract(new Quantity<>(6.0, LengthUnit.INCH), LengthUnit.FEET);
        assertEquals(9.5, result.getValue(), 1e-6);
        assertEquals(LengthUnit.FEET, result.getUnit());
    }

    @Test
    public void testSubtraction_ExplicitTargetUnit_Inches() {
        Quantity<LengthUnit> result = new Quantity<>(10.0, LengthUnit.FEET)
                .subtract(new Quantity<>(6.0, LengthUnit.INCH), LengthUnit.INCH);
        assertEquals(114.0, result.getValue(), 1e-6);
        assertEquals(LengthUnit.INCH, result.getUnit());
    }

    @Test
    public void testSubtraction_ExplicitTargetUnit_Millilitre() {
        Quantity<VolumeUnit> result = new Quantity<>(5.0, VolumeUnit.LITRE)
                .subtract(new Quantity<>(2.0, VolumeUnit.LITRE), VolumeUnit.MILLILITRE);
        assertEquals(3000.0, result.getValue(), 1e-6);
        assertEquals(VolumeUnit.MILLILITRE, result.getUnit());
    }

    @Test
    public void testSubtraction_ResultingInNegative() {
        Quantity<LengthUnit> result = new Quantity<>(5.0, LengthUnit.FEET)
                .subtract(new Quantity<>(10.0, LengthUnit.FEET));
        assertEquals(-5.0, result.getValue(), 1e-6);
    }

    @Test
    public void testSubtraction_ResultingInZero() {
        Quantity<LengthUnit> result = new Quantity<>(10.0, LengthUnit.FEET)
                .subtract(new Quantity<>(120.0, LengthUnit.INCH));
        assertEquals(0.0, result.getValue(), 1e-6);
    }

    @Test
    public void testSubtraction_WithZeroOperand() {
        Quantity<LengthUnit> result = new Quantity<>(5.0, LengthUnit.FEET)
                .subtract(new Quantity<>(0.0, LengthUnit.INCH));
        assertEquals(5.0, result.getValue(), 1e-6);
    }

    @Test
    public void testSubtraction_WithNegativeValues() {
        Quantity<LengthUnit> result = new Quantity<>(5.0, LengthUnit.FEET)
                .subtract(new Quantity<>(-2.0, LengthUnit.FEET));
        assertEquals(7.0, result.getValue(), 1e-6);
    }

    @Test
    public void testSubtraction_NonCommutative() {
        Quantity<LengthUnit> a = new Quantity<>(10.0, LengthUnit.FEET);
        Quantity<LengthUnit> b = new Quantity<>(5.0, LengthUnit.FEET);
        assertNotEquals(a.subtract(b).getValue(), b.subtract(a).getValue(), 1e-6);
        assertEquals(5.0, a.subtract(b).getValue(), 1e-6);
        assertEquals(-5.0, b.subtract(a).getValue(), 1e-6);
    }

    @Test
    public void testSubtraction_WithLargeValues() {
        Quantity<WeightUnit> result = new Quantity<>(1e6, WeightUnit.KILOGRAM)
                .subtract(new Quantity<>(5e5, WeightUnit.KILOGRAM));
        assertEquals(5e5, result.getValue(), 1e-6);
    }

    @Test
    public void testSubtraction_WithSmallValues() {
        Quantity<LengthUnit> result = new Quantity<>(0.001, LengthUnit.FEET)
                .subtract(new Quantity<>(0.0005, LengthUnit.FEET));
        assertEquals(0.0005, result.getValue(), 1e-6);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSubtraction_NullOperand() {
        new Quantity<>(10.0, LengthUnit.FEET).subtract(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSubtraction_NullTargetUnit() {
        new Quantity<>(10.0, LengthUnit.FEET).subtract(new Quantity<>(5.0, LengthUnit.FEET), null);
    }

    @Test
    public void testSubtraction_AllMeasurementCategories() {
        assertNotNull(new Quantity<>(10.0, LengthUnit.FEET).subtract(new Quantity<>(5.0, LengthUnit.FEET)));
        assertNotNull(new Quantity<>(10.0, WeightUnit.KILOGRAM).subtract(new Quantity<>(5.0, WeightUnit.GRAM)));
        assertNotNull(new Quantity<>(10.0, VolumeUnit.LITRE).subtract(new Quantity<>(5.0, VolumeUnit.MILLILITRE)));
    }

    @Test
    public void testSubtraction_ChainedOperations() {
        Quantity<LengthUnit> result = new Quantity<>(10.0, LengthUnit.FEET)
                .subtract(new Quantity<>(2.0, LengthUnit.FEET))
                .subtract(new Quantity<>(1.0, LengthUnit.FEET));
        assertEquals(7.0, result.getValue(), 1e-6);
    }

    // --- UC12: Division Tests ---

    @Test
    public void testDivision_SameUnit_FeetDividedByFeet() {
        double result = new Quantity<>(10.0, LengthUnit.FEET).divide(new Quantity<>(2.0, LengthUnit.FEET));
        assertEquals(5.0, result, 1e-6);
    }

    @Test
    public void testDivision_SameUnit_LitreDividedByLitre() {
        double result = new Quantity<>(10.0, VolumeUnit.LITRE).divide(new Quantity<>(5.0, VolumeUnit.LITRE));
        assertEquals(2.0, result, 1e-6);
    }

    @Test
    public void testDivision_CrossUnit_FeetDividedByInches() {
        double result = new Quantity<>(24.0, LengthUnit.INCH).divide(new Quantity<>(2.0, LengthUnit.FEET));
        assertEquals(1.0, result, 1e-6);
    }

    @Test
    public void testDivision_CrossUnit_KilogramDividedByGram() {
        double result = new Quantity<>(2.0, WeightUnit.KILOGRAM).divide(new Quantity<>(2000.0, WeightUnit.GRAM));
        assertEquals(1.0, result, 1e-6);
    }

    @Test
    public void testDivision_RatioGreaterThanOne() {
        double result = new Quantity<>(10.0, LengthUnit.FEET).divide(new Quantity<>(2.0, LengthUnit.FEET));
        assertTrue(result > 1.0);
    }

    @Test
    public void testDivision_RatioLessThanOne() {
        double result = new Quantity<>(5.0, LengthUnit.FEET).divide(new Quantity<>(10.0, LengthUnit.FEET));
        assertTrue(result < 1.0);
        assertEquals(0.5, result, 1e-6);
    }

    @Test
    public void testDivision_RatioEqualToOne() {
        double result = new Quantity<>(10.0, LengthUnit.FEET).divide(new Quantity<>(10.0, LengthUnit.FEET));
        assertEquals(1.0, result, 1e-6);
    }

    @Test
    public void testDivision_NonCommutative() {
        Quantity<LengthUnit> a = new Quantity<>(10.0, LengthUnit.FEET);
        Quantity<LengthUnit> b = new Quantity<>(5.0, LengthUnit.FEET);
        assertNotEquals(a.divide(b), b.divide(a), 1e-6);
    }

    @Test(expected = ArithmeticException.class)
    public void testDivision_ByZero() {
        new Quantity<>(10.0, LengthUnit.FEET).divide(new Quantity<>(0.0, LengthUnit.FEET));
    }

    @Test
    public void testDivision_WithLargeRatio() {
        double result = new Quantity<>(1e6, WeightUnit.KILOGRAM).divide(new Quantity<>(1.0, WeightUnit.KILOGRAM));
        assertEquals(1e6, result, 1e-6);
    }

    @Test
    public void testDivision_WithSmallRatio() {
        double result = new Quantity<>(1.0, WeightUnit.KILOGRAM).divide(new Quantity<>(1e6, WeightUnit.KILOGRAM));
        assertEquals(1e-6, result, 1e-12);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDivision_NullOperand() {
        new Quantity<>(10.0, LengthUnit.FEET).divide(null);
    }

    @Test
    public void testDivision_AllMeasurementCategories() {
        assertEquals(2.0, new Quantity<>(10.0, LengthUnit.FEET).divide(new Quantity<>(5.0, LengthUnit.FEET)), 1e-6);
        assertEquals(2.0, new Quantity<>(10.0, WeightUnit.KILOGRAM).divide(new Quantity<>(5.0, WeightUnit.KILOGRAM)),
                1e-6);
        assertEquals(2.0, new Quantity<>(10.0, VolumeUnit.LITRE).divide(new Quantity<>(5.0, VolumeUnit.LITRE)), 1e-6);
    }

    @Test
    public void testSubtractionAndDivision_Integration() {
        Quantity<LengthUnit> a = new Quantity<>(10.0, LengthUnit.FEET);
        Quantity<LengthUnit> b = new Quantity<>(2.0, LengthUnit.FEET);
        Quantity<LengthUnit> c = new Quantity<>(4.0, LengthUnit.FEET);
        double result = a.subtract(b).divide(c);
        assertEquals(2.0, result, 1e-6);
    }

    @Test
    public void testSubtractionAddition_Inverse() {
        Quantity<LengthUnit> a = new Quantity<>(10.0, LengthUnit.FEET);
        Quantity<LengthUnit> b = new Quantity<>(5.0, LengthUnit.FEET);
        Quantity<LengthUnit> result = a.add(b).subtract(b);
        assertEquals(a.getValue(), result.getValue(), 1e-6);
    }

    @Test
    public void testSubtraction_Immutability() {
        Quantity<LengthUnit> a = new Quantity<>(10.0, LengthUnit.FEET);
        Quantity<LengthUnit> b = new Quantity<>(5.0, LengthUnit.FEET);
        a.subtract(b);
        assertEquals(10.0, a.getValue(), 1e-6);
    }
}
