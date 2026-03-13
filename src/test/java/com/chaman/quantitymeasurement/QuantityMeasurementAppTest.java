package com.chaman.quantitymeasurement;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.chaman.quantitymeasurement.dto.QuantityDTO;
import com.chaman.quantitymeasurement.repository.QuantityMeasurementCacheRepository;
import com.chaman.quantitymeasurement.service.QuantityMeasurementServiceImpl;

public class QuantityMeasurementAppTest {

    private QuantityMeasurementServiceImpl service;

    @BeforeEach
    void setup() {
        service = new QuantityMeasurementServiceImpl(
                new QuantityMeasurementCacheRepository());
    }

    @Test
    void testAddition() {
        QuantityDTO q1 = new QuantityDTO(10, "FEET", "LENGTH");
        QuantityDTO q2 = new QuantityDTO(5, "FEET", "LENGTH");

        QuantityDTO result = service.add(q1, q2);

        assertEquals(15, result.getValue());
    }

    @Test
    void testSubtraction() {
        QuantityDTO q1 = new QuantityDTO(10, "FEET", "LENGTH");
        QuantityDTO q2 = new QuantityDTO(4, "FEET", "LENGTH");

        QuantityDTO result = service.subtract(q1, q2);

        assertEquals(6, result.getValue());
    }

    @Test
    void testDivision() {
        QuantityDTO q1 = new QuantityDTO(10, "FEET", "LENGTH");
        QuantityDTO q2 = new QuantityDTO(2, "FEET", "LENGTH");

        double result = service.divide(q1, q2);

        assertEquals(5, result);
    }

    @Test
    void testCompareEqualValues() {
        QuantityDTO q1 = new QuantityDTO(5, "FEET", "LENGTH");
        QuantityDTO q2 = new QuantityDTO(5, "FEET", "LENGTH");

        assertTrue(service.compare(q1, q2));
    }

    @Test
    void testCompareDifferentValues() {
        QuantityDTO q1 = new QuantityDTO(5, "FEET", "LENGTH");
        QuantityDTO q2 = new QuantityDTO(6, "FEET", "LENGTH");

        assertFalse(service.compare(q1, q2));
    }

    @Test
    void testFeetInchesComparison() {
        QuantityDTO q1 = new QuantityDTO(1, "FEET", "LENGTH");
        QuantityDTO q2 = new QuantityDTO(12, "INCHES", "LENGTH");

        assertTrue(service.compare(q1, q2));
    }

    @Test
    void testConvertUnit() {
        QuantityDTO q1 = new QuantityDTO(10, "FEET", "LENGTH");

        QuantityDTO result = service.convert(q1, "INCHES");

        assertEquals("INCHES", result.getUnit());
    }

    @Test
    void testConvertValueSame() {
        QuantityDTO q1 = new QuantityDTO(10, "FEET", "LENGTH");

        QuantityDTO result = service.convert(q1, "FEET");

        assertEquals(10, result.getValue());
    }

    @Test
    void testAddNegativeValues() {
        QuantityDTO q1 = new QuantityDTO(-5, "FEET", "LENGTH");
        QuantityDTO q2 = new QuantityDTO(10, "FEET", "LENGTH");

        QuantityDTO result = service.add(q1, q2);

        assertEquals(5, result.getValue());
    }

    @Test
    void testSubtractNegativeValues() {
        QuantityDTO q1 = new QuantityDTO(-5, "FEET", "LENGTH");
        QuantityDTO q2 = new QuantityDTO(-5, "FEET", "LENGTH");

        QuantityDTO result = service.subtract(q1, q2);

        assertEquals(0, result.getValue());
    }

    @Test
    void testLargeAddition() {
        QuantityDTO q1 = new QuantityDTO(1000, "FEET", "LENGTH");
        QuantityDTO q2 = new QuantityDTO(2000, "FEET", "LENGTH");

        QuantityDTO result = service.add(q1, q2);

        assertEquals(3000, result.getValue());
    }

    @Test
    void testDivideLargeNumbers() {
        QuantityDTO q1 = new QuantityDTO(1000, "FEET", "LENGTH");
        QuantityDTO q2 = new QuantityDTO(10, "FEET", "LENGTH");

        double result = service.divide(q1, q2);

        assertEquals(100, result);
    }

    @Test
    void testCompareInchesFeetReverse() {
        QuantityDTO q1 = new QuantityDTO(12, "INCHES", "LENGTH");
        QuantityDTO q2 = new QuantityDTO(1, "FEET", "LENGTH");

        assertTrue(service.compare(q1, q2));
    }

    @Test
    void testAdditionResultUnit() {
        QuantityDTO q1 = new QuantityDTO(5, "FEET", "LENGTH");
        QuantityDTO q2 = new QuantityDTO(5, "FEET", "LENGTH");

        QuantityDTO result = service.add(q1, q2);

        assertEquals("FEET", result.getUnit());
    }

    @Test
    void testMeasurementTypeMaintained() {
        QuantityDTO q1 = new QuantityDTO(5, "FEET", "LENGTH");
        QuantityDTO q2 = new QuantityDTO(5, "FEET", "LENGTH");

        QuantityDTO result = service.add(q1, q2);

        assertEquals("LENGTH", result.getMeasurementType());
    }

    @Test
    void testMultipleOperations() {
        QuantityDTO q1 = new QuantityDTO(20, "FEET", "LENGTH");
        QuantityDTO q2 = new QuantityDTO(10, "FEET", "LENGTH");

        QuantityDTO add = service.add(q1, q2);
        QuantityDTO sub = service.subtract(q1, q2);

        assertEquals(30, add.getValue());
        assertEquals(10, sub.getValue());
    }

    @Test
    void testAdditionWithZero() {
        QuantityDTO q1 = new QuantityDTO(0, "FEET", "LENGTH");
        QuantityDTO q2 = new QuantityDTO(5, "FEET", "LENGTH");

        QuantityDTO result = service.add(q1, q2);

        assertEquals(5, result.getValue());
    }

    @Test
    void testSubtractionWithZero() {
        QuantityDTO q1 = new QuantityDTO(10, "FEET", "LENGTH");
        QuantityDTO q2 = new QuantityDTO(0, "FEET", "LENGTH");

        QuantityDTO result = service.subtract(q1, q2);

        assertEquals(10, result.getValue());
    }

    @Test
    void testDivisionByOne() {
        QuantityDTO q1 = new QuantityDTO(20, "FEET", "LENGTH");
        QuantityDTO q2 = new QuantityDTO(1, "FEET", "LENGTH");

        double result = service.divide(q1, q2);

        assertEquals(20, result);
    }

    @Test
    void testCompareSameObject() {
        QuantityDTO q1 = new QuantityDTO(5, "FEET", "LENGTH");

        assertTrue(service.compare(q1, q1));
    }

    @Test
    void testConvertMaintainsMeasurementType() {
        QuantityDTO q1 = new QuantityDTO(10, "FEET", "LENGTH");

        QuantityDTO result = service.convert(q1, "INCHES");

        assertEquals("LENGTH", result.getMeasurementType());
    }

    @Test
    void testLargeSubtraction() {
        QuantityDTO q1 = new QuantityDTO(5000, "FEET", "LENGTH");
        QuantityDTO q2 = new QuantityDTO(2000, "FEET", "LENGTH");

        QuantityDTO result = service.subtract(q1, q2);

        assertEquals(3000, result.getValue());
    }

    @Test
    void testCompareZeroValues() {
        QuantityDTO q1 = new QuantityDTO(0, "FEET", "LENGTH");
        QuantityDTO q2 = new QuantityDTO(0, "FEET", "LENGTH");

        assertTrue(service.compare(q1, q2));
    }

    @Test
    void testMultipleAdditions() {
        QuantityDTO q1 = new QuantityDTO(2, "FEET", "LENGTH");
        QuantityDTO q2 = new QuantityDTO(3, "FEET", "LENGTH");

        QuantityDTO r1 = service.add(q1, q2);
        QuantityDTO r2 = service.add(r1, q2);

        assertEquals(8, r2.getValue());
    }
}
