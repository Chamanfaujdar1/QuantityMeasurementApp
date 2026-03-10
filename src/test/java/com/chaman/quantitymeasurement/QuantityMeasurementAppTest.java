package com.chaman.quantitymeasurement;

import org.junit.jupiter.api.Test;

import com.chaman.quantitymeasurement.Quantity.ArithmeticOperation;

import static org.junit.jupiter.api.Assertions.*;

public class QuantityMeasurementAppTest {
    
	private static final double EPSILON = 0.01;

	@Test
    void testTemperatureEquality_CelsiusToCelsius_SameValue() {
        assertEquals(
                new Quantity<>(0.0, TemperatureUnit.CELSIUS),
                new Quantity<>(0.0, TemperatureUnit.CELSIUS)
        );
    }

    @Test
    void testTemperatureEquality_FahrenheitToFahrenheit_SameValue() {
        assertEquals(
                new Quantity<>(32.0, TemperatureUnit.FAHRENHEIT),
                new Quantity<>(32.0, TemperatureUnit.FAHRENHEIT)
        );
    }

    @Test
    void testTemperatureEquality_CelsiusToFahrenheit_Negative40Equal() {
        assertEquals(
                new Quantity<>(-40.0, TemperatureUnit.CELSIUS),
                new Quantity<>(-40.0, TemperatureUnit.FAHRENHEIT)
        );
    }

    @Test
    void testTemperatureEquality_ReflexiveProperty() {
        Quantity<TemperatureUnit> a =
                new Quantity<>(50.0, TemperatureUnit.CELSIUS);

        assertTrue(a.equals(a));
    }

    // -------- Conversion Tests --------


    @Test
    void testTemperatureConversion_RoundTrip_PreservesValue() {

        Quantity<TemperatureUnit> original =
                new Quantity<>(20.0, TemperatureUnit.CELSIUS);

        Quantity<TemperatureUnit> converted =
                original.convertTo(TemperatureUnit.FAHRENHEIT)
                        .convertTo(TemperatureUnit.CELSIUS);

        assertEquals(original.getValue(), converted.getValue(), EPSILON);
    }

    @Test
    void testTemperatureConversion_SameUnit() {

        Quantity<TemperatureUnit> q =
                new Quantity<>(25.0, TemperatureUnit.CELSIUS);

        Quantity<TemperatureUnit> result =
                q.convertTo(TemperatureUnit.CELSIUS);

        assertEquals(25.0, result.getValue(), EPSILON);
    }

    

    // -------- Unsupported Operations --------

    @Test
    void testTemperatureUnsupportedOperation_Add() {

        Quantity<TemperatureUnit> t1 =
                new Quantity<>(100.0, TemperatureUnit.CELSIUS);

        Quantity<TemperatureUnit> t2 =
                new Quantity<>(50.0, TemperatureUnit.CELSIUS);

        assertThrows(
                UnsupportedOperationException.class,
                () -> t1.add(t2)
        );
    }

    @Test
    void testTemperatureUnsupportedOperation_Subtract() {

        Quantity<TemperatureUnit> t1 =
                new Quantity<>(100.0, TemperatureUnit.CELSIUS);

        Quantity<TemperatureUnit> t2 =
                new Quantity<>(50.0, TemperatureUnit.CELSIUS);

        assertThrows(
                UnsupportedOperationException.class,
                () -> t1.subtract(t2)
        );
    }

    @Test
    void testTemperatureUnsupportedOperation_Divide() {

        Quantity<TemperatureUnit> t1 =
                new Quantity<>(100.0, TemperatureUnit.CELSIUS);

        Quantity<TemperatureUnit> t2 =
                new Quantity<>(50.0, TemperatureUnit.CELSIUS);

        assertThrows(
                UnsupportedOperationException.class,
                () -> t1.divide(t2)
        );
    }

    // -------- Incompatibility Tests --------

    @Test
    void testTemperatureVsLengthIncompatibility() {

        Quantity<TemperatureUnit> temp =
                new Quantity<>(100.0, TemperatureUnit.CELSIUS);

        Quantity<LengthUnit> length =
                new Quantity<>(100.0, LengthUnit.FEET);

        assertFalse(temp.equals(length));
    }

    // -------- Enum Behaviour --------

    @Test
    void testTemperatureUnit_AllConstants() {

        assertNotNull(TemperatureUnit.CELSIUS);
        assertNotNull(TemperatureUnit.FAHRENHEIT);
    }

    @Test
    void testTemperatureUnit_NameMethod() {

        assertEquals(
                "Celsius",
                TemperatureUnit.CELSIUS.getUnitName()
        );

        assertEquals(
                "Fahrenheit",
                TemperatureUnit.FAHRENHEIT.getUnitName()
        );
    }

    @Test
    void testTemperatureUnit_ConversionFactor() {

        assertEquals(
                1.0,
                TemperatureUnit.CELSIUS.getConversionFactor()
        );
    }

    // -------- Validation --------

    @Test
    void testTemperatureNullUnitValidation() {

        assertThrows(
                IllegalArgumentException.class,
                () -> new Quantity<>(100.0, null)
        );
    }

    @Test
    void testTemperatureNullOperandValidation_InComparison() {

        Quantity<TemperatureUnit> q =
                new Quantity<>(10.0, TemperatureUnit.CELSIUS);

        assertFalse(q.equals(null));
    }
    
}