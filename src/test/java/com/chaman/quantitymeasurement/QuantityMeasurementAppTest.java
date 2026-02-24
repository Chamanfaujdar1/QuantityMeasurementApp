package com.chaman.quantitymeasurement;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class QuantityMeasurementAppTest {

	@Test
	void testEquality_SameValue() {
		QuantityMeasurementApp.Feet f1=new QuantityMeasurementApp.Feet(1.0);
		QuantityMeasurementApp.Feet f2=new QuantityMeasurementApp.Feet(1.0);
		
		assertTrue(f1.equals(f2));
	}
	
	@Test
	void testEquality_DifferentValue() {
		QuantityMeasurementApp.Feet f1=new QuantityMeasurementApp.Feet(68.0);
		QuantityMeasurementApp.Feet f2=new QuantityMeasurementApp.Feet(88.0);
		
		assertFalse(f1.equals(f2));
	}
	
	@Test
	void testEquality_NullComparison() {
		QuantityMeasurementApp.Feet f1=new QuantityMeasurementApp.Feet(68.0);
		
		assertFalse(f1.equals(null));
	}
	
	@Test
	void testEquality_NonNumericInput() {
		QuantityMeasurementApp.Feet f1=new QuantityMeasurementApp.Feet(68.0);
		
		assertFalse(f1.equals("68"));
	}
	
	@Test
	void testEquality_SameReference() {
		QuantityMeasurementApp.Feet f1=new QuantityMeasurementApp.Feet(68.0);
		
		assertTrue(f1.equals(f1));
	}
	
	@Test
    void testEquality_Consistent() {
		QuantityMeasurementApp.Feet f1 = new QuantityMeasurementApp.Feet(1.0);
		QuantityMeasurementApp.Feet f2 = new QuantityMeasurementApp.Feet(1.0);

        assertTrue(f1.equals(f2));
        assertTrue(f1.equals(f2));
        assertTrue(f1.equals(f2));
    }

}
