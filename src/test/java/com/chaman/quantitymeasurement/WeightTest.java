package com.chaman.quantitymeasurement;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EnumSource;

import org.junit.jupiter.api.Test;

class WeightTest {

    private static final double EPSILON = 1e-4;

    // weight equality tests

    @ParameterizedTest
    @EnumSource(WeightUnit.class)
    void testEquality_SameValue_ForAllUnits(WeightUnit unit) {
        Quantity<WeightUnit> w1 = new Quantity<>(10.0, unit);
        Quantity<WeightUnit> w2 = new Quantity<>(10.0, unit);

        assertTrue(w1.equals(w2));
    }

    @ParameterizedTest
    @EnumSource(WeightUnit.class)
    void testEquality_DifferentValue_ForAllUnits(WeightUnit unit) {
        Quantity<WeightUnit> w1 = new Quantity<>(10.0, unit);
        Quantity<WeightUnit> w2 = new Quantity<>(20.0, unit);

        assertFalse(w1.equals(w2));
    }

    @ParameterizedTest
    @EnumSource(WeightUnit.class)
    void testWeightEquality_NullComparison(WeightUnit unit) {
        Quantity<WeightUnit> w1 = new Quantity<>(68.0, unit);

        assertFalse(w1.equals(null));
    }

    @ParameterizedTest
    @EnumSource(WeightUnit.class)
    void testWeightEquality_NonNumericInput(WeightUnit unit) {
        Quantity<WeightUnit> w1 = new Quantity<>(68.0, unit);

        assertFalse(w1.equals("68"));
    }

    @ParameterizedTest
    @EnumSource(WeightUnit.class)
    void testWeightEquality_SameReference(WeightUnit unit) {
        Quantity<WeightUnit> w1 = new Quantity<>(68.0, unit);

        assertTrue(w1.equals(w1));
    }

    @ParameterizedTest
    @EnumSource(WeightUnit.class)
    void testWeightEquality_Consistent(WeightUnit unit) {
        Quantity<WeightUnit> w1 = new Quantity<>(1.0, unit);
        Quantity<WeightUnit> w2 = new Quantity<>(1.0, unit);

        assertTrue(w1.equals(w2));
        assertTrue(w1.equals(w2));
        assertTrue(w1.equals(w2));
    }

    // cross unit test

    @ParameterizedTest
    @CsvSource({
        "1.0,       KILOGRAM,  1000.0,     GRAM",
        "1.0,       KILOGRAM,  1000000.0,  MILLIGRAM",
        "1.0,       KILOGRAM,  2.20462262, POUND",
        "1.0,       KILOGRAM,  35.274,     OUNCE",
        "1.0,       TONNE,     1000.0,     KILOGRAM",
        "1.0,       GRAM,      1000.0,     MILLIGRAM",
        "1.0,       POUND,     16.0,       OUNCE"
    })
    void testCrossUnitEquality_SameWeight(double v1, WeightUnit u1,
                                          double v2, WeightUnit u2) {
        Quantity<WeightUnit> w1 = new Quantity<>(v1, u1);
        Quantity<WeightUnit> w2 = new Quantity<>(v2, u2);

        assertTrue(w1.equals(w2));
    }

    @ParameterizedTest
    @CsvSource({
        "2.0, KILOGRAM, 1000.0,    GRAM",
        "2.0, KILOGRAM, 1000000.0, MILLIGRAM",
        "2.0, KILOGRAM, 2.20462,   POUND",
        "2.0, TONNE,    1000.0,    KILOGRAM",
        "2.0, GRAM,     1000.0,    MILLIGRAM"
    })
    void testCrossUnitEquality_DifferentWeight(double v1, WeightUnit u1,
                                               double v2, WeightUnit u2) {
        Quantity<WeightUnit> w1 = new Quantity<>(v1, u1);
        Quantity<WeightUnit> w2 = new Quantity<>(v2, u2);

        assertFalse(w1.equals(w2));
    }

    // unit conversion

    @ParameterizedTest
    @CsvSource({
        "1.0,        KILOGRAM,  GRAM,      1000.0",
        "1000.0,     GRAM,      KILOGRAM,  1.0",
        "1.0,        KILOGRAM,  MILLIGRAM, 1000000.0",
        "1000000.0,  MILLIGRAM, KILOGRAM,  1.0",
        "1.0,        KILOGRAM,  POUND,     2.20462",
        "1.0,        POUND,     KILOGRAM,  0.453592",
        "1.0,        POUND,     OUNCE,     16.0",
        "16.0,       OUNCE,     POUND,     1.0",
        "1.0,        TONNE,     KILOGRAM,  1000.0",
        "1000.0,     KILOGRAM,  TONNE,     1.0",
        "5.0,        KILOGRAM,  KILOGRAM,  5.0",
        "0.0,        KILOGRAM,  GRAM,      0.0",
        "-1.0,       KILOGRAM,  GRAM,      -1000.0",
        "-1.0,       KILOGRAM,  POUND,     -2.20462",
        "-1.0,       TONNE,     KILOGRAM,  -1000.0"
    })
    void testConversion(double value, WeightUnit source,
                        WeightUnit target, double expected) {
        Quantity<WeightUnit> w = new Quantity<>(value, source);
        assertEquals(expected, w.convertTo(target), EPSILON);
    }

    @ParameterizedTest
    @EnumSource(WeightUnit.class)
    void testRoundTrip_AllUnitsToKilogramAndBack(WeightUnit unit) {
        double original  = 1.0;
        double converted = new Quantity<>(original, unit).convertTo(WeightUnit.KILOGRAM);
        double back      = new Quantity<>(converted, WeightUnit.KILOGRAM).convertTo(unit);
        assertEquals(original, back, EPSILON);
    }

    @Test
    void testConversion_NaN_throws() {
        assertThrows(IllegalArgumentException.class,
                () -> new Quantity<>(Double.NaN, WeightUnit.KILOGRAM));
    }

    @Test
    void testConversion_Infinite_throws() {
        assertThrows(IllegalArgumentException.class,
                () -> new Quantity<>(Double.POSITIVE_INFINITY, WeightUnit.KILOGRAM));
    }

    @Test
    void testConversion_NullUnit_throws() {
        assertThrows(IllegalArgumentException.class,
                () -> new Quantity<>(1.0, null));
    }

    // add test

    @ParameterizedTest
    @CsvSource({
        "1.0,    KILOGRAM,  2.0,     KILOGRAM,  3.0",
        "500.0,  GRAM,      500.0,   GRAM,      1000.0",
        "500.0,  MILLIGRAM, 500.0,   MILLIGRAM, 1000.0",
        "1.0,    KILOGRAM,  1000.0,  GRAM,      2.0",
        "1000.0, GRAM,      1.0,     KILOGRAM,  2000.0",
        "1.0,    POUND,     16.0,    OUNCE,     2.0",
        "1.0,    TONNE,     1000.0,  KILOGRAM,  2.0",
        "5.0,    KILOGRAM,  0.0,     GRAM,      5.0",
        "0.001,  KILOGRAM,  0.002,   KILOGRAM,  0.003",
        "5.0,    KILOGRAM,  -2.0,    KILOGRAM,  3.0",
        "-1.0,   KILOGRAM,  -1.0,    KILOGRAM,  -2.0",
        "1000.0, GRAM,      -500.0,  GRAM,      500.0"
    })
    void testAdd(double v1, WeightUnit u1,
                 double v2, WeightUnit u2,
                 double expectedValue) {
        Quantity<WeightUnit> w1 = new Quantity<>(v1, u1);
        Quantity<WeightUnit> w2 = new Quantity<>(v2, u2);

        Quantity<WeightUnit> result = w1.add(w2);

        assertEquals(expectedValue, result.getValue(), EPSILON);
        assertEquals(u1, result.getUnit());
    }

    @ParameterizedTest
    @CsvSource({
        // same unit operations
        "1.0,   KILOGRAM,  1.0,    KILOGRAM,  KILOGRAM,  2.0",
        "500.0, GRAM,      500.0,  GRAM,      GRAM,      1000.0",
        "1.0,   POUND,     1.0,    POUND,     POUND,     2.0",
        "1.0,   OUNCE,     1.0,    OUNCE,     OUNCE,     2.0",
        "1.0,   TONNE,     1.0,    TONNE,     TONNE,     2.0",
        "500.0, MILLIGRAM, 500.0,  MILLIGRAM, MILLIGRAM, 1000.0",

        // KILOGRAM + GRAM
        "1.0, KILOGRAM, 1000.0, GRAM, KILOGRAM, 2.0",
        "1.0, KILOGRAM, 1000.0, GRAM, GRAM,     2000.0",
        "1.0, KILOGRAM, 1000.0, GRAM, TONNE,    0.002",

        // POUND + OUNCE
        "1.0, POUND, 16.0, OUNCE, POUND,    2.0",
        "1.0, POUND, 16.0, OUNCE, OUNCE,    32.0",
        "1.0, POUND, 16.0, OUNCE, KILOGRAM, 0.907184",

        // TONNE + KILOGRAM
        "1.0, TONNE, 1000.0, KILOGRAM, TONNE,    2.0",
        "1.0, TONNE, 1000.0, KILOGRAM, KILOGRAM, 2000.0",
        "1.0, TONNE, 1000.0, KILOGRAM, GRAM,     2000000.0",

        // GRAM + MILLIGRAM
        "1.0, GRAM, 1000.0, MILLIGRAM, GRAM,      2.0",
        "1.0, GRAM, 1000.0, MILLIGRAM, MILLIGRAM, 2000.0",

        // zero value
        "5.0, KILOGRAM, 0.0, GRAM, KILOGRAM, 5.0",

        // negative values
        "5.0,  KILOGRAM, -2.0,   KILOGRAM, KILOGRAM, 3.0",
        "-1.0, KILOGRAM, -1.0,   KILOGRAM, GRAM,     -2000.0",

        // large scale
        "1000.0, KILOGRAM, 500.0, KILOGRAM, GRAM, 1500000.0",

        // small scale
        "0.001, KILOGRAM, 0.002, KILOGRAM, GRAM, 3.0"
    })
    void testTargetAdd(double v1, WeightUnit u1,
                       double v2, WeightUnit u2,
                       WeightUnit target, double expectedValue) {
        Quantity<WeightUnit> w1 = new Quantity<>(v1, u1);
        Quantity<WeightUnit> w2 = new Quantity<>(v2, u2);

        Quantity<WeightUnit> result = w1.add(w2, target);

        assertEquals(expectedValue, result.getValue(), EPSILON);
        assertEquals(target, result.getUnit());
    }

    @Test
    void testAdd_NullWeight() {
        Quantity<WeightUnit> w1 = new Quantity<>(1.0, WeightUnit.KILOGRAM);

        assertThrows(IllegalArgumentException.class,
                () -> w1.add(null));
    }

    @Test
    void testTargetAdd_NullWeight() {
        Quantity<WeightUnit> w1 = new Quantity<>(1.0, WeightUnit.KILOGRAM);

        assertThrows(IllegalArgumentException.class,
                () -> w1.add(null, null));
    }
}
