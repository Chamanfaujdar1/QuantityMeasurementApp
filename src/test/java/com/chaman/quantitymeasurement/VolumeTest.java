package com.chaman.quantitymeasurement;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EnumSource;

import org.junit.jupiter.api.Test;

class VolumeTest {

    private static final double EPSILON = 1e-4;

    // volume equality tests

    @ParameterizedTest
    @EnumSource(VolumeUnit.class)
    void testEquality_SameValue_ForAllUnits(VolumeUnit unit) {
        Quantity<VolumeUnit> v1 = new Quantity<>(10.0, unit);
        Quantity<VolumeUnit> v2 = new Quantity<>(10.0, unit);

        assertTrue(v1.equals(v2));
    }

    @ParameterizedTest
    @EnumSource(VolumeUnit.class)
    void testEquality_DifferentValue_ForAllUnits(VolumeUnit unit) {
        Quantity<VolumeUnit> v1 = new Quantity<>(10.0, unit);
        Quantity<VolumeUnit> v2 = new Quantity<>(20.0, unit);

        assertFalse(v1.equals(v2));
    }

    @ParameterizedTest
    @EnumSource(VolumeUnit.class)
    void testVolumeEquality_NullComparison(VolumeUnit unit) {
        Quantity<VolumeUnit> v1 = new Quantity<>(68.0, unit);

        assertFalse(v1.equals(null));
    }

    @ParameterizedTest
    @EnumSource(VolumeUnit.class)
    void testVolumeEquality_NonNumericInput(VolumeUnit unit) {
        Quantity<VolumeUnit> v1 = new Quantity<>(68.0, unit);

        assertFalse(v1.equals("68"));
    }

    @ParameterizedTest
    @EnumSource(VolumeUnit.class)
    void testVolumeEquality_SameReference(VolumeUnit unit) {
        Quantity<VolumeUnit> v1 = new Quantity<>(68.0, unit);

        assertTrue(v1.equals(v1));
    }

    @ParameterizedTest
    @EnumSource(VolumeUnit.class)
    void testVolumeEquality_Consistent(VolumeUnit unit) {
        Quantity<VolumeUnit> v1 = new Quantity<>(1.0, unit);
        Quantity<VolumeUnit> v2 = new Quantity<>(1.0, unit);

        assertTrue(v1.equals(v2));
        assertTrue(v1.equals(v2));
        assertTrue(v1.equals(v2));
    }

    // cross unit test

    @ParameterizedTest
    @CsvSource({
        "1.0,    LITRE,       1000.0,   MILLILITRE",
        "1.0,    GALLON,      3.78541,  LITRE",
        "1.0,    GALLON,      3785.41,  MILLILITRE"
    })
    void testCrossUnitEquality_SameVolume(double v1, VolumeUnit u1,
                                          double v2, VolumeUnit u2) {
        Quantity<VolumeUnit> vol1 = new Quantity<>(v1, u1);
        Quantity<VolumeUnit> vol2 = new Quantity<>(v2, u2);

        assertTrue(vol1.equals(vol2));
    }

    @ParameterizedTest
    @CsvSource({
        "2.0, LITRE,  1000.0,  MILLILITRE",
        "2.0, GALLON, 3.78541, LITRE",
        "2.0, GALLON, 3785.41, MILLILITRE"
    })
    void testCrossUnitEquality_DifferentVolume(double v1, VolumeUnit u1,
                                               double v2, VolumeUnit u2) {
        Quantity<VolumeUnit> vol1 = new Quantity<>(v1, u1);
        Quantity<VolumeUnit> vol2 = new Quantity<>(v2, u2);

        assertFalse(vol1.equals(vol2));
    }

    // unit conversion

    @ParameterizedTest
    @CsvSource({
        "1.0,     LITRE,      MILLILITRE, 1000.0",
        "1000.0,  MILLILITRE, LITRE,      1.0",
        "1.0,     GALLON,     LITRE,      3.78541",
        "3.78541, LITRE,      GALLON,     1.0",
        "1.0,     GALLON,     MILLILITRE, 3785.41",
        "5.0,     LITRE,      LITRE,      5.0",
        "0.0,     LITRE,      MILLILITRE, 0.0",
        "-1.0,    LITRE,      MILLILITRE, -1000.0",
        "-1.0,    GALLON,     LITRE,      -3.78541"
    })
    void testConversion(double value, VolumeUnit source,
                        VolumeUnit target, double expected) {
        Quantity<VolumeUnit> v = new Quantity<>(value, source);
        assertEquals(expected, v.convertTo(target), EPSILON);
    }

    @ParameterizedTest
    @EnumSource(VolumeUnit.class)
    void testRoundTrip_AllUnitsToLitreAndBack(VolumeUnit unit) {
        double original  = 1.0;
        double converted = new Quantity<>(original, unit).convertTo(VolumeUnit.LITRE);
        double back      = new Quantity<>(converted, VolumeUnit.LITRE).convertTo(unit);
        assertEquals(original, back, EPSILON);
    }

    @Test
    void testConversion_NaN_throws() {
        assertThrows(IllegalArgumentException.class,
                () -> new Quantity<>(Double.NaN, VolumeUnit.LITRE));
    }

    @Test
    void testConversion_Infinite_throws() {
        assertThrows(IllegalArgumentException.class,
                () -> new Quantity<>(Double.POSITIVE_INFINITY, VolumeUnit.LITRE));
    }

    @Test
    void testConversion_NullUnit_throws() {
        assertThrows(IllegalArgumentException.class,
                () -> new Quantity<>(1.0, null));
    }

    // add test

    @ParameterizedTest
    @CsvSource({
        "1.0,    LITRE,      2.0,     LITRE,      3.0",
        "500.0,  MILLILITRE, 500.0,   MILLILITRE, 1000.0",
        "1.0,    LITRE,      1000.0,  MILLILITRE, 2.0",
        "1000.0, MILLILITRE, 1.0,     LITRE,      2000.0",
        "1.0,    GALLON,     1.0,     GALLON,     2.0",
        "1.0,    LITRE,      0.0,     MILLILITRE, 1.0",
        "5.0,    LITRE,      -2.0,    LITRE,      3.0",
        "-1.0,   LITRE,      -1.0,    LITRE,      -2.0",
        "0.001,  LITRE,      0.002,   LITRE,      0.003"
    })
    void testAdd(double v1, VolumeUnit u1,
                 double v2, VolumeUnit u2,
                 double expectedValue) {
        Quantity<VolumeUnit> vol1 = new Quantity<>(v1, u1);
        Quantity<VolumeUnit> vol2 = new Quantity<>(v2, u2);

        Quantity<VolumeUnit> result = vol1.add(vol2);

        assertEquals(expectedValue, result.getValue(), EPSILON);
        assertEquals(u1, result.getUnit());
    }

    @ParameterizedTest
    @CsvSource({
        // same unit operations
        "1.0,   LITRE,      1.0,    LITRE,      LITRE,      2.0",
        "500.0, MILLILITRE, 500.0,  MILLILITRE, MILLILITRE, 1000.0",
        "1.0,   GALLON,     1.0,    GALLON,     GALLON,     2.0",

        // LITRE + MILLILITRE
        "1.0, LITRE, 1000.0, MILLILITRE, LITRE,      2.0",
        "1.0, LITRE, 1000.0, MILLILITRE, MILLILITRE, 2000.0",
        "1.0, LITRE, 1000.0, MILLILITRE, GALLON,     0.528344",

        // GALLON + LITRE
        "1.0, GALLON, 3.78541, LITRE, GALLON,     2.0",
        "1.0, GALLON, 3.78541, LITRE, LITRE,      7.57082",
        "1.0, GALLON, 3.78541, LITRE, MILLILITRE, 7570.82",

        // GALLON + MILLILITRE
        "1.0, GALLON, 3785.41, MILLILITRE, GALLON,     2.0",
        "1.0, GALLON, 3785.41, MILLILITRE, LITRE,      7.57082",

        // zero value
        "5.0, LITRE, 0.0, MILLILITRE, LITRE, 5.0",

        // negative values
        "5.0,  LITRE, -2.0, LITRE, LITRE,      3.0",
        "-1.0, LITRE, -1.0, LITRE, MILLILITRE, -2000.0",

        // large scale
        "1000.0, LITRE, 500.0, LITRE, MILLILITRE, 1500000.0",

        // small scale
        "0.001, LITRE, 0.002, LITRE, MILLILITRE, 3.0"
    })
    void testTargetAdd(double v1, VolumeUnit u1,
                       double v2, VolumeUnit u2,
                       VolumeUnit target, double expectedValue) {
        Quantity<VolumeUnit> vol1 = new Quantity<>(v1, u1);
        Quantity<VolumeUnit> vol2 = new Quantity<>(v2, u2);

        Quantity<VolumeUnit> result = vol1.add(vol2, target);

        assertEquals(expectedValue, result.getValue(), EPSILON);
        assertEquals(target, result.getUnit());
    }

    @Test
    void testAdd_NullVolume() {
        Quantity<VolumeUnit> v1 = new Quantity<>(1.0, VolumeUnit.LITRE);

        assertThrows(IllegalArgumentException.class,
                () -> v1.add(null));
    }

    @Test
    void testTargetAdd_NullVolume() {
        Quantity<VolumeUnit> v1 = new Quantity<>(1.0, VolumeUnit.LITRE);

        assertThrows(IllegalArgumentException.class,
                () -> v1.add(null, null));
    }
    
 // type safety tests

    @Test
    void testVolumeAndLengthIncompatibility() {
        Quantity<VolumeUnit> volume = new Quantity<>(1.0, VolumeUnit.LITRE);
        Quantity<LengthUnit> length = new Quantity<>(1.0, LengthUnit.INCHES);

        assertFalse(volume.equals(length));
    }

    @Test
    void testVolumeAndWeightIncompatibility() {
        Quantity<VolumeUnit> volume = new Quantity<>(1.0, VolumeUnit.LITRE);
        Quantity<WeightUnit> weight = new Quantity<>(1.0, WeightUnit.KILOGRAM);

        assertFalse(volume.equals(weight));
    }
}
