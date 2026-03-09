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
        Weight w1 = new Weight(10.0, unit);
        Weight w2 = new Weight(10.0, unit);

        assertTrue(w1.equals(w2));
    }

    @ParameterizedTest
    @EnumSource(WeightUnit.class)
    void testEquality_DifferentValue_ForAllUnits(WeightUnit unit) {
        Weight w1 = new Weight(10.0, unit);
        Weight w2 = new Weight(20.0, unit);

        assertFalse(w1.equals(w2));
    }

    @ParameterizedTest
    @EnumSource(WeightUnit.class)
    void testWeightEquality_NullComparison(WeightUnit unit) {
        Weight w1 = new Weight(68.0, unit);

        assertFalse(w1.equals(null));
    }

    @ParameterizedTest
    @EnumSource(WeightUnit.class)
    void testWeightEquality_NonNumericInput(WeightUnit unit) {
        Weight w1 = new Weight(68.0, unit);

        assertFalse(w1.equals("68"));
    }

    @ParameterizedTest
    @EnumSource(WeightUnit.class)
    void testWeightEquality_SameReference(WeightUnit unit) {
        Weight w1 = new Weight(68.0, unit);

        assertTrue(w1.equals(w1));
    }

    @ParameterizedTest
    @EnumSource(WeightUnit.class)
    void testWeightEquality_Consistent(WeightUnit unit) {
        Weight w1 = new Weight(1.0, unit);
        Weight w2 = new Weight(1.0, unit);

        assertTrue(w1.equals(w2));
        assertTrue(w1.equals(w2));
        assertTrue(w1.equals(w2));
    }

    // cross unit test

    @Test
    void testKilogramAndGramEquality_SameWeight() {
        Weight kg = new Weight(1.0, WeightUnit.KILOGRAM);
        Weight g  = new Weight(1000.0, WeightUnit.GRAM);

        assertTrue(kg.equals(g));
    }

    @Test
    void testKilogramAndMilligramEquality_SameWeight() {
        Weight kg = new Weight(1.0, WeightUnit.KILOGRAM);
        Weight mg = new Weight(1000000.0, WeightUnit.MILLIGRAM);

        assertTrue(kg.equals(mg));
    }

    @Test
    void testKilogramAndPoundEquality_SameWeight() {
        Weight kg  = new Weight(1.0, WeightUnit.KILOGRAM);
        Weight lbs = new Weight(2.20462262, WeightUnit.POUND);

        assertTrue(kg.equals(lbs));
    }

    @Test
    void testKilogramAndOunceEquality_SameWeight() {
        Weight kg = new Weight(1.0, WeightUnit.KILOGRAM);
        Weight oz = new Weight(35.274, WeightUnit.OUNCE);

        assertTrue(kg.equals(oz));
    }

    @Test
    void testTonneAndKilogramEquality_SameWeight() {
        Weight tonne = new Weight(1.0, WeightUnit.TONNE);
        Weight kg    = new Weight(1000.0, WeightUnit.KILOGRAM);

        assertTrue(tonne.equals(kg));
    }

    @Test
    void testGramAndMilligramEquality_SameWeight() {
        Weight g  = new Weight(1.0, WeightUnit.GRAM);
        Weight mg = new Weight(1000.0, WeightUnit.MILLIGRAM);

        assertTrue(g.equals(mg));
    }

    @Test
    void testKilogramAndGramEquality_DifferentWeight() {
        Weight kg = new Weight(2.0, WeightUnit.KILOGRAM);
        Weight g  = new Weight(1000.0, WeightUnit.GRAM);

        assertFalse(kg.equals(g));
    }

    @Test
    void testKilogramAndMilligramEquality_DifferentWeight() {
        Weight kg = new Weight(2.0, WeightUnit.KILOGRAM);
        Weight mg = new Weight(1000000.0, WeightUnit.MILLIGRAM);

        assertFalse(kg.equals(mg));
    }

    @Test
    void testKilogramAndPoundEquality_DifferentWeight() {
        Weight kg  = new Weight(2.0, WeightUnit.KILOGRAM);
        Weight lbs = new Weight(2.20462, WeightUnit.POUND);

        assertFalse(kg.equals(lbs));
    }

    @Test
    void testTonneAndKilogramEquality_DifferentWeight() {
        Weight tonne = new Weight(2.0, WeightUnit.TONNE);
        Weight kg    = new Weight(1000.0, WeightUnit.KILOGRAM);

        assertFalse(tonne.equals(kg));
    }

    @Test
    void testGramAndMilligramEquality_DifferentWeight() {
        Weight g  = new Weight(2.0, WeightUnit.GRAM);
        Weight mg = new Weight(1000.0, WeightUnit.MILLIGRAM);

        assertFalse(g.equals(mg));
    }

    // unit conversion

    @ParameterizedTest
    @CsvSource({
        "1.0,        KILOGRAM,   GRAM,        1000.0",
        "1000.0,     GRAM,       KILOGRAM,    1.0",
        "1.0,        KILOGRAM,   MILLIGRAM,   1000000.0",
        "1000000.0,  MILLIGRAM,  KILOGRAM,    1.0",
        "1.0,        KILOGRAM,   POUND,       2.20462",
        "1.0,        POUND,      KILOGRAM,    0.453592",
        "1.0,        POUND,      OUNCE,       16.0",
        "16.0,       OUNCE,      POUND,       1.0",
        "1.0,        TONNE,      KILOGRAM,    1000.0",
        "1000.0,     KILOGRAM,   TONNE,       1.0",
        "5.0,        KILOGRAM,   KILOGRAM,    5.0",
        "0.0,        KILOGRAM,   GRAM,        0.0"
    })
    void testConversion(double value, WeightUnit source,
                        WeightUnit target, double expected) {
        Weight w = new Weight(value, source);
        assertEquals(expected, w.convertTo(target), EPSILON);
    }

    @ParameterizedTest
    @CsvSource({
        "1.0,  KILOGRAM,  GRAM",
        "1.0,  KILOGRAM,  MILLIGRAM",
        "1.0,  POUND,     OUNCE",
        "1.0,  TONNE,     KILOGRAM",
        "1.0,  GRAM,      MILLIGRAM"
    })
    void testRoundTrip(double value, WeightUnit source, WeightUnit target) {
        double converted = new Weight(value, source).convertTo(target);
        double back      = new Weight(converted, target).convertTo(source);
        assertEquals(value, back, EPSILON);
    }

    @Test
    void testConversion_NaN_throws() {
        assertThrows(IllegalArgumentException.class,
                () -> new Weight(Double.NaN, WeightUnit.KILOGRAM));
    }

    // add test

    @ParameterizedTest
    @CsvSource({
        "1.0,       KILOGRAM,  2.0,       KILOGRAM,  3.0",
        "500.0,     GRAM,      500.0,     GRAM,      1000.0",
        "500.0,     MILLIGRAM, 500.0,     MILLIGRAM, 1000.0",
        "1.0,       KILOGRAM,  1000.0,    GRAM,      2.0",
        "1000.0,    GRAM,      1.0,       KILOGRAM,  2000.0",
        "1.0,       POUND,     16.0,      OUNCE,     2.0",
        "1.0,       TONNE,     1000.0,    KILOGRAM,  2.0",
        "5.0,       KILOGRAM,  0.0,       GRAM,      5.0",
        "1000000.0, KILOGRAM,  1000000.0, KILOGRAM,  2000000.0",
        "0.001,     KILOGRAM,  0.002,     KILOGRAM,  0.003"
    })
    void testAdd(double v1, WeightUnit u1,
                 double v2, WeightUnit u2,
                 double expectedValue) {
        Weight w1 = new Weight(v1, u1);
        Weight w2 = new Weight(v2, u2);

        Weight result = w1.add(w2);

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
        "1.0,    KILOGRAM, 1000.0, GRAM,      KILOGRAM,  2.0",
        "1.0,    KILOGRAM, 1000.0, GRAM,      GRAM,      2000.0",
        "1.0,    KILOGRAM, 1000.0, GRAM,      TONNE,     0.002",

        // KILOGRAM + MILLIGRAM
        "1.0,    KILOGRAM, 1000000.0, MILLIGRAM, KILOGRAM,  2.0",
        "1.0,    KILOGRAM, 1000000.0, MILLIGRAM, GRAM,      2000.0",

        // POUND + OUNCE
        "1.0,  POUND, 16.0, OUNCE, POUND,    2.0",
        "1.0,  POUND, 16.0, OUNCE, OUNCE,    32.0",
        "1.0,  POUND, 16.0, OUNCE, KILOGRAM, 0.907184",

        // TONNE + KILOGRAM
        "1.0, TONNE, 1000.0, KILOGRAM, TONNE,    2.0",
        "1.0, TONNE, 1000.0, KILOGRAM, KILOGRAM, 2000.0",
        "1.0, TONNE, 1000.0, KILOGRAM, GRAM,     2000000.0",

        // GRAM + MILLIGRAM
        "1.0, GRAM, 1000.0, MILLIGRAM, GRAM,      2.0",
        "1.0, GRAM, 1000.0, MILLIGRAM, MILLIGRAM, 2000.0",

        // zero value
        "5.0, KILOGRAM, 0.0, GRAM, KILOGRAM, 5.0",

        // large scale
        "1000.0, KILOGRAM, 500.0, KILOGRAM, GRAM,      1500000.0",
        "1000.0, KILOGRAM, 500.0, KILOGRAM, MILLIGRAM, 1500000000.0",

        // small scale
        "0.001, KILOGRAM, 0.002, KILOGRAM, GRAM,      3.0",
        "0.001, KILOGRAM, 0.002, KILOGRAM, MILLIGRAM, 3000.0"
    })
    void testTargetAdd(double v1, WeightUnit u1,
                       double v2, WeightUnit u2,
                       WeightUnit target, double expectedValue) {
        Weight w1 = new Weight(v1, u1);
        Weight w2 = new Weight(v2, u2);

        Weight result = w1.add(w2, target);

        assertEquals(expectedValue, result.getValue(), EPSILON);
        assertEquals(target, result.getUnit());
    }

    @Test
    void testAdd_NullWeight() {
        Weight w1 = new Weight(1.0, WeightUnit.KILOGRAM);

        assertThrows(IllegalArgumentException.class,
                () -> w1.add(null));
    }

    @Test
    void testTargetAdd_NullWeight() {
        Weight w1 = new Weight(1.0, WeightUnit.KILOGRAM);

        assertThrows(IllegalArgumentException.class,
                () -> w1.add(null, null));
    }

}
