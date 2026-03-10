package com.chaman.quantitymeasurement;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EnumSource;

import org.junit.jupiter.api.Test;

class QuantityMeasurementAppTest {

    private static final double EPSILON = 1e-6;

    // length equality tests

    @ParameterizedTest
    @EnumSource(LengthUnit.class)
    void testEquality_SameValue_ForAllUnits(LengthUnit unit) {
        Quantity<LengthUnit> l1 = new Quantity<>(10.0, unit);
        Quantity<LengthUnit> l2 = new Quantity<>(10.0, unit);

        assertTrue(l1.equals(l2));
    }

    @ParameterizedTest
    @EnumSource(LengthUnit.class)
    void testEquality_DifferentValue_ForAllUnits(LengthUnit unit) {
        Quantity<LengthUnit> l1 = new Quantity<>(10.0, unit);
        Quantity<LengthUnit> l2 = new Quantity<>(20.0, unit);

        assertFalse(l1.equals(l2));
    }

    @ParameterizedTest
    @EnumSource(LengthUnit.class)
    void testFeetEquality_NullComparison(LengthUnit unit) {
        Quantity<LengthUnit> l1 = new Quantity<>(68.0, unit);

        assertFalse(l1.equals(null));
    }

    @ParameterizedTest
    @EnumSource(LengthUnit.class)
    void testFeetEquality_NonNumericInput(LengthUnit unit) {
        Quantity<LengthUnit> l1 = new Quantity<>(68.0, unit);

        assertFalse(l1.equals("68"));
    }

    @ParameterizedTest
    @EnumSource(LengthUnit.class)
    void testFeetEquality_SameReference(LengthUnit unit) {
        Quantity<LengthUnit> l1 = new Quantity<>(68.0, unit);

        assertTrue(l1.equals(l1));
    }

    @ParameterizedTest
    @EnumSource(LengthUnit.class)
    void testFeetEquality_Consistent(LengthUnit unit) {
        Quantity<LengthUnit> l1 = new Quantity<>(1.0, unit);
        Quantity<LengthUnit> l2 = new Quantity<>(1.0, unit);

        assertTrue(l1.equals(l2));
        assertTrue(l1.equals(l2));
        assertTrue(l1.equals(l2));
    }

    // cross unit test

    @ParameterizedTest
    @CsvSource({
        "1.0,   FEET,        12.0,    INCHES",
        "1.0,   YARDS,       36.0,    INCHES",
        "100.0, CENTIMETERS, 39.370078, INCHES",
        "3.0,   FEET,        1.0,     YARDS",
        "30.48, CENTIMETERS, 1.0,     FEET"
    })
    void testCrossUnitEquality_SameLength(double v1, LengthUnit u1,
                                          double v2, LengthUnit u2) {
        Quantity<LengthUnit> l1 = new Quantity<>(v1, u1);
        Quantity<LengthUnit> l2 = new Quantity<>(v2, u2);

        assertTrue(l1.equals(l2));
    }

    @ParameterizedTest
    @CsvSource({
        "2.0,    FEET,        12.0,    INCHES",
        "2.0,    YARDS,       36.0,    INCHES",
        "1000.0, CENTIMETERS, 39.3701, INCHES",
        "3.0,    FEET,        3.0,     YARDS",
        "30.48,  CENTIMETERS, 2.0,     FEET"
    })
    void testCrossUnitEquality_DifferentLength(double v1, LengthUnit u1,
                                               double v2, LengthUnit u2) {
        Quantity<LengthUnit> l1 = new Quantity<>(v1, u1);
        Quantity<LengthUnit> l2 = new Quantity<>(v2, u2);

        assertFalse(l1.equals(l2));
    }

    // unit conversion

    @ParameterizedTest
    @CsvSource({
        "1.0,  FEET,        INCHES,      12.0",
        "24.0, INCHES,      FEET,        2.0",
        "3.0,  YARDS,       FEET,        9.0",
        "1.0,  YARDS,       INCHES,      36.0",
        "2.54, CENTIMETERS, INCHES,      1.0",
        "6.0,  FEET,        YARDS,       2.0",
        "5.0,  FEET,        FEET,        5.0",
        "0.0,  FEET,        INCHES,      0.0",
        "-1.0, FEET,        INCHES,      -12.0"
    })
    void testConversion(double value, LengthUnit source,
                        LengthUnit target, double expected) {
        Quantity<LengthUnit> l = new Quantity<>(value, source);
        assertEquals(expected, l.convertTo(target), EPSILON);
    }

    @ParameterizedTest
    @EnumSource(LengthUnit.class)
    void testRoundTrip_AllUnitsToMetreAndBack(LengthUnit unit) {
        double original  = 1.0;
        double converted = new Quantity<>(original, unit).convertTo(LengthUnit.CENTIMETERS);
        double back      = new Quantity<>(converted, LengthUnit.CENTIMETERS).convertTo(unit);
        assertEquals(original, back, EPSILON);
    }

    @Test
    void testConversion_NaN_throws() {
        assertThrows(IllegalArgumentException.class,
                () -> new Quantity<>(Double.NaN, LengthUnit.FEET));
    }

    @Test
    void testConversion_Infinite_throws() {
        assertThrows(IllegalArgumentException.class,
                () -> new Quantity<>(Double.POSITIVE_INFINITY, LengthUnit.FEET));
    }

    @Test
    void testConversion_NullUnit_throws() {
        assertThrows(IllegalArgumentException.class,
                () -> new Quantity<>(1.0, null));
    }

    // add test

    @ParameterizedTest
    @CsvSource({
        "1.0,       FEET,        2.0,  FEET,        3.0",
        "6.0,       INCHES,      6.0,  INCHES,      12.0",
        "1.0,       FEET,        12.0, INCHES,      2.0",
        "12.0,      INCHES,      1.0,  FEET,        24.0",
        "1.0,       YARDS,       3.0,  FEET,        2.0",
        "2.54,      CENTIMETERS, 1.0,  INCHES,      5.08",
        "5.0,       FEET,        0.0,  INCHES,      5.0",
        "5.0,       FEET,        -2.0, FEET,        3.0",
        "1000000.0, FEET,        1000000.0, FEET,   2000000.0",
        "0.001,     FEET,        0.002, FEET,       0.003"
    })
    void testAdd(double v1, LengthUnit u1,
                 double v2, LengthUnit u2,
                 double expectedValue) {
        Quantity<LengthUnit> l1 = new Quantity<>(v1, u1);
        Quantity<LengthUnit> l2 = new Quantity<>(v2, u2);

        Quantity<LengthUnit> result = l1.add(l2);

        assertEquals(expectedValue, result.getValue(), EPSILON);
        assertEquals(u1, result.getUnit());
    }

    @ParameterizedTest
    @CsvSource({
        // same unit operations
        "1.0,  FEET,        1.0,  FEET,        FEET,        2.0",
        "12.0, INCHES,      12.0, INCHES,      INCHES,      24.0",
        "1.0,  YARDS,       1.0,  YARDS,       YARDS,       2.0",
        "2.54, CENTIMETERS, 2.54, CENTIMETERS, CENTIMETERS, 5.08",

        // FEET + INCHES
        "1.0, FEET, 12.0, INCHES, FEET,   2.0",
        "1.0, FEET, 12.0, INCHES, INCHES, 24.0",
        "1.0, FEET, 12.0, INCHES, YARDS,  0.666667",

        // YARDS + FEET
        "1.0, YARDS, 3.0, FEET, YARDS,  2.0",
        "1.0, YARDS, 3.0, FEET, FEET,   6.0",
        "1.0, YARDS, 3.0, FEET, INCHES, 72.0",

        // INCHES + YARDS
        "36.0, INCHES, 1.0, YARDS, FEET,  6.0",
        "36.0, INCHES, 1.0, YARDS, YARDS, 2.0",

        // CENTIMETERS + INCHES
        "2.54, CENTIMETERS, 1.0, INCHES, CENTIMETERS, 5.08",
        "2.54, CENTIMETERS, 1.0, INCHES, INCHES,      2.0",

        // zero value
        "5.0, FEET, 0.0, INCHES, YARDS, 1.666667",

        // negative values
        "5.0, FEET, -2.0, FEET, INCHES, 36.0",

        // large scale
        "1000.0, FEET, 500.0, FEET, INCHES, 18000.0",

        // small scale
        "12.0, INCHES, 12.0, INCHES, YARDS, 0.666667"
    })
    void testTargetAdd(double v1, LengthUnit u1,
                       double v2, LengthUnit u2,
                       LengthUnit target, double expectedValue) {
        Quantity<LengthUnit> l1 = new Quantity<>(v1, u1);
        Quantity<LengthUnit> l2 = new Quantity<>(v2, u2);

        Quantity<LengthUnit> result = l1.add(l2, target);

        assertEquals(expectedValue, result.getValue(), EPSILON);
        assertEquals(target, result.getUnit());
    }

    @Test
    void testAdd_NullLength() {
        Quantity<LengthUnit> l1 = new Quantity<>(1.0, LengthUnit.FEET);

        assertThrows(IllegalArgumentException.class,
                () -> l1.add(null));
    }

    @Test
    void testTargetAdd_NullLength() {
        Quantity<LengthUnit> l1 = new Quantity<>(1.0, LengthUnit.FEET);

        assertThrows(IllegalArgumentException.class,
                () -> l1.add(null, null));
    }
}