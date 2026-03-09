package com.chaman.quantitymeasurement;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EnumSource;

import org.junit.jupiter.api.Test;

class QuantityMeasurementAppTest {
	
	private static final double EPSILON = 1e-6;
	
	//inches and feet test
 
    @ParameterizedTest
    @EnumSource(Length.LengthUnit.class)
    void testEquality_SameValue_ForAllUnits(Length.LengthUnit unit) {
        Length l1 = new Length(10.0, unit);
        Length l2 = new Length(10.0, unit);

        assertTrue(l1.equals(l2));
    }

    @ParameterizedTest
    @EnumSource(Length.LengthUnit.class)
    void testEquality_DifferentValue_ForAllUnits(Length.LengthUnit unit) {
        Length l1 = new Length(10.0, unit);
        Length l2 = new Length(20.0, unit);

        assertFalse(l1.equals(l2));
    }

    @ParameterizedTest
    @EnumSource(Length.LengthUnit.class)
    void testFeetEquality_NullComparison(Length.LengthUnit unit) {
        Length l1 = new Length(68.0, unit);

        assertFalse(l1.equals(null));
    }

    @ParameterizedTest
    @EnumSource(Length.LengthUnit.class)
    void testFeetEquality_NonNumericInput(Length.LengthUnit unit) {
        Length l1 = new Length(68.0, unit);

        assertFalse(l1.equals("68"));
    }

    @ParameterizedTest
    @EnumSource(Length.LengthUnit.class)
    void testFeetEquality_SameReference(Length.LengthUnit unit) {
        Length l1 = new Length(68.0, unit);

        assertTrue(l1.equals(l1));
    }

    @ParameterizedTest
    @EnumSource(Length.LengthUnit.class)
    void testFeetEquality_Consistent(Length.LengthUnit unit) {
        Length l1 = new Length(1.0, unit);
        Length l2 = new Length(1.0, unit);

        assertTrue(l1.equals(l2));
        assertTrue(l1.equals(l2));
        assertTrue(l1.equals(l2));
    }

    //cross unit test

    @Test
    void testFeetAndInchesEquality_SameLength() {
        Length feet = new Length(1.0, Length.LengthUnit.FEET);
        Length inches = new Length(12.0, Length.LengthUnit.INCHES);

        assertTrue(feet.equals(inches));
    }
    
    @Test
    void testYardAndInchesEquality_SameLength() {
        Length yard = new Length(1.0, Length.LengthUnit.YARDS);
        Length inches = new Length(36.0, Length.LengthUnit.INCHES);

        assertTrue(yard.equals(inches));
    }
    
    @Test
    void testCentimeterAndInchesEquality_SameLength() {
        Length cm = new Length(100.0, Length.LengthUnit.CENTIMETERS);
        Length inches = new Length(39.3701, Length.LengthUnit.INCHES);

        assertTrue(cm.equals(inches));
    }
    
    @Test
    void testFeetAndYardEquality_SameLength() {
        Length feet = new Length(3.0, Length.LengthUnit.FEET);
        Length yard = new Length(1.0, Length.LengthUnit.YARDS);

        assertTrue(feet.equals(yard));
    }
    
    @Test
    void testCentimeterAndFeetEquality_SameLength() {
        Length cm = new Length(30.48, Length.LengthUnit.CENTIMETERS);
        Length feet = new Length(1.0, Length.LengthUnit.FEET);

        assertTrue(cm.equals(feet));
    }

    @Test
    void testFeetAndInchesEquality_DifferentLength() {
        Length feet = new Length(2.0, Length.LengthUnit.FEET);
        Length inches = new Length(12.0, Length.LengthUnit.INCHES);

        assertFalse(feet.equals(inches));
    }
    
    @Test
    void testYardAndInchesEquality_DifferentLength() {
        Length yard = new Length(2.0, Length.LengthUnit.YARDS);
        Length inches = new Length(36.0, Length.LengthUnit.INCHES);

        assertFalse(yard.equals(inches));
    }
    
    @Test
    void testCentimeterAndInchesEquality_DifferentLength() {
        Length cm = new Length(1000.0, Length.LengthUnit.CENTIMETERS);
        Length inches = new Length(39.3701, Length.LengthUnit.INCHES);

        assertFalse(cm.equals(inches));
    }
    
    @Test
    void testFeetAndYardEquality_DifferentLength() {
        Length feet = new Length(3.0, Length.LengthUnit.FEET);
        Length yard = new Length(3.0, Length.LengthUnit.YARDS);

        assertFalse(feet.equals(yard));
    }
    
    @Test
    void testCentimeterAndFeetEquality_DifferentLength() {
        Length cm = new Length(30.48, Length.LengthUnit.CENTIMETERS);
        Length feet = new Length(2.0, Length.LengthUnit.FEET);

        assertFalse(cm.equals(feet));
    }
    
    //unit conversion
    
    @ParameterizedTest
    @CsvSource({
    	"1.0, FEET, INCHES, 12.0",
        "24.0, INCHES, FEET, 2.0",
        "3.0, YARDS, FEET, 9.0",
        "1.0, YARDS, INCHES, 36.0",
        "2.54, CENTIMETERS, INCHES, 1.0",
        "6.0, FEET, YARDS, 2.0",
        "5.0, FEET, FEET, 5.0",
        "0.0, FEET, INCHES, 0.0",
        "-1.0, FEET, INCHES, -12.0"
    })
    void testConversion(double value, Length.LengthUnit source,
    					Length.LengthUnit target, double expected) {
    	double result=QuantityMeasurementApp.convert(value, source, target);
    	System.out.println(result);
    	assertEquals(expected,result,EPSILON);
    }
    
    @ParameterizedTest
    @CsvSource({
    	"5.0, FEET, INCHES",
        "3.0, YARDS, FEET",
        "2.54, CENTIMETERS, INCHES"
    })
    void testRoundTrip(double value,Length.LengthUnit source, Length.LengthUnit target) {
    	double converted=QuantityMeasurementApp.convert(value, source, target);
    	double back=QuantityMeasurementApp.convert(converted, target, source);
    	assertEquals(value,back,EPSILON);
    }
    
    @Test
    void testConversion_NaN_throws() {
    	assertThrows(IllegalArgumentException.class,()-> QuantityMeasurementApp.convert(Double.NaN,
    											Length.LengthUnit.FEET,Length.LengthUnit.INCHES));
    }
    
    // add test 
    
    @ParameterizedTest
    @CsvSource({
        "1.0, FEET, 2.0, FEET, 3.0",
        "6.0, INCHES, 6.0, INCHES, 12.0",
        "1.0, FEET, 12.0, INCHES, 2.0",
        "12.0, INCHES, 1.0, FEET, 24.0",
        "1.0, YARDS, 3.0, FEET, 2.0",
        "2.54, CENTIMETERS, 1.0, INCHES, 5.08",
        "5.0, FEET, 0.0, INCHES, 5.0",
        "5.0, FEET, -2.0, FEET, 3.0",
        "1000000.0, FEET, 1000000.0, FEET, 2000000.0",
        "0.001, FEET, 0.002, FEET, 0.003"
    })
    void testAdd(double v1, Length.LengthUnit u1,
                 double v2, Length.LengthUnit u2,
                 double expectedValue) {

        Length l1 = new Length(v1, u1);
        Length l2 = new Length(v2, u2);

        Length result = l1.add(l2);

        assertEquals(expectedValue, result.getValue(), EPSILON);
        assertEquals(u1, result.getUnit());
    }
    
    @ParameterizedTest
    @CsvSource({
        // same unit operations
        "1.0, FEET, 1.0, FEET, FEET, 2.0",
        "12.0, INCHES, 12.0, INCHES, INCHES, 24.0",
        "1.0, YARDS, 1.0, YARDS, YARDS, 2.0",
        "2.54, CENTIMETERS, 2.54, CENTIMETERS, CENTIMETERS, 5.08",

        // FEET + INCHES
        "1.0, FEET, 12.0, INCHES, FEET, 2.0",
        "1.0, FEET, 12.0, INCHES, INCHES, 24.0",
        "1.0, FEET, 12.0, INCHES, YARDS, 0.666667",

        // YARDS + FEET
        "1.0, YARDS, 3.0, FEET, YARDS, 2.0",
        "1.0, YARDS, 3.0, FEET, FEET, 6.0",
        "1.0, YARDS, 3.0, FEET, INCHES, 72.0",

        // INCHES + YARDS
        "36.0, INCHES, 1.0, YARDS, FEET, 6.0",
        "36.0, INCHES, 1.0, YARDS, YARDS, 2.0",

        // CENTIMETERS + INCHES
        "2.54, CENTIMETERS, 1.0, INCHES, CENTIMETERS, 5.08",
        "2.54, CENTIMETERS, 1.0, INCHES, INCHES, 2.0",

        // zero value
        "5.0, FEET, 0.0, INCHES, YARDS, 1.666667",

        // negative values
        "5.0, FEET, -2.0, FEET, INCHES, 36.0",

        // large scale conversion
        "1000.0, FEET, 500.0, FEET, INCHES, 18000.0",

        // small scale conversion
        "12.0, INCHES, 12.0, INCHES, YARDS, 0.666667"
    })
    void testTargetAdd(double v1, Length.LengthUnit u1,
    				   double v2, Length.LengthUnit u2,
    				   Length.LengthUnit target, double expectedValue) {
    	
    	Length l1=new Length(v1,u1);
    	Length l2=new Length(v2,u2);
    	
    	Length result=l1.add(l2,target);
    	
    	assertEquals(expectedValue,result.getValue(),EPSILON);
    	assertEquals(target,result.getUnit());
    	
    }
    
    @Test
    void testAdd_NullLength() {
        Length l1 = new Length(1.0, Length.LengthUnit.FEET);

        assertThrows(IllegalArgumentException.class,
                () -> l1.add(null));
    }
    
    @Test
    void testTargetAdd_NullLength() {
        Length l1 = new Length(1.0, Length.LengthUnit.FEET);

        assertThrows(IllegalArgumentException.class,
                () -> l1.add(null,null));
    }
}