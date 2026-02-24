package com.chaman.quantitymeasurement;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class QuantityMeasurementAppTest {

	@Test
	void testFeetEquality_SameValue() {
		QuantityMeasurementApp.Feet f1=new QuantityMeasurementApp.Feet(1.0);
		QuantityMeasurementApp.Feet f2=new QuantityMeasurementApp.Feet(1.0);
		
		assertTrue(f1.equals(f2));
	}
	
	@Test
	void testFeetEquality_DifferentValue() {
		QuantityMeasurementApp.Feet f1=new QuantityMeasurementApp.Feet(68.0);
		QuantityMeasurementApp.Feet f2=new QuantityMeasurementApp.Feet(88.0);
		
		assertFalse(f1.equals(f2));
	}
	
	@Test
	void testFeetEquality_NullComparison() {
		QuantityMeasurementApp.Feet f1=new QuantityMeasurementApp.Feet(68.0);
		
		assertFalse(f1.equals(null));
	}
	
	@Test
	void testFeetEquality_NonNumericInput() {
		QuantityMeasurementApp.Feet f1=new QuantityMeasurementApp.Feet(68.0);
		
		assertFalse(f1.equals("68"));
	}
	
	@Test
	void testFeetEquality_SameReference() {
		QuantityMeasurementApp.Feet f1=new QuantityMeasurementApp.Feet(68.0);
		
		assertTrue(f1.equals(f1));
	}
	
	@Test
    void testFeetEquality_Consistent() {
		QuantityMeasurementApp.Feet f1 = new QuantityMeasurementApp.Feet(1.0);
		QuantityMeasurementApp.Feet f2 = new QuantityMeasurementApp.Feet(1.0);

        assertTrue(f1.equals(f2));
        assertTrue(f1.equals(f2));
        assertTrue(f1.equals(f2));
    }
	
	@Test
	void testInchesEquality_SameValue() {
		QuantityMeasurementApp.Inches i1=new QuantityMeasurementApp.Inches(1.0);
		QuantityMeasurementApp.Inches i2=new QuantityMeasurementApp.Inches(1.0);
		
		assertTrue(i1.equals(i2));
	}
	
	@Test
	void testInchesEquality_DifferentValue() {
		QuantityMeasurementApp.Inches i1=new QuantityMeasurementApp.Inches(68.0);
		QuantityMeasurementApp.Inches i2=new QuantityMeasurementApp.Inches(88.0);
		
		assertFalse(i1.equals(i2));
	}
	
	@Test
	void testInchesEquality_NullComparison() {
		QuantityMeasurementApp.Inches i1=new QuantityMeasurementApp.Inches(68.0);
		
		assertFalse(i1.equals(null));
	}
	
	@Test
	void testInchesEquality_NonNumericInput() {
		QuantityMeasurementApp.Inches i1=new QuantityMeasurementApp.Inches(68.0);
		
		assertFalse(i1.equals("68"));
	}
	
	@Test
	void testInchesEquality_SameReference() {
		QuantityMeasurementApp.Inches i1=new QuantityMeasurementApp.Inches(68.0);
		
		assertTrue(i1.equals(i1));
	}
	
	@Test
    void testInchesEquality_Consistent() {
		QuantityMeasurementApp.Inches i1 = new QuantityMeasurementApp.Inches(1.0);
		QuantityMeasurementApp.Inches i2 = new QuantityMeasurementApp.Inches(1.0);

        assertTrue(i1.equals(i2));
        assertTrue(i1.equals(i2));
        assertTrue(i1.equals(i2));
    }

}