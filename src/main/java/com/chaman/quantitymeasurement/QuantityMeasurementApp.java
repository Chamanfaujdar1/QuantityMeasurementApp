package com.chaman.quantitymeasurement;

import com.chaman.quantitymeasurement.controller.QuantityMeasurementController;
import com.chaman.quantitymeasurement.dto.QuantityDTO;
import com.chaman.quantitymeasurement.repository.QuantityMeasurementCacheRepository;
import com.chaman.quantitymeasurement.repository.IQuantityMeasurementRepository;
import com.chaman.quantitymeasurement.service.IQuantityMeasurementService;
import com.chaman.quantitymeasurement.service.QuantityMeasurementServiceImpl;
import com.chaman.quantitymeasurement.exception.QuantityMeasurementException;
import com.chaman.quantitymeasurement.service.QuantityMeasurementServiceImpl;
import com.chaman.quantitymeasurement.entity.QuantityMeasurementEntity;
import com.chaman.quantitymeasurement.model.QuantityModel;

public class QuantityMeasurementApp {

    private static QuantityMeasurementController controller;

    public static void main(String[] args) {
        initialize();

        System.out.println("--- Quantity Measurement App ---");

        // Equality checks
        System.out.println("\nEquality Tests:");
        demonstrateComparison(1.0, QuantityDTO.MeasurementType.LENGTH,
                QuantityDTO.LengthUnitDTO.FEET, 1.0, QuantityDTO.LengthUnitDTO.FEET);
        demonstrateComparison(1.0, QuantityDTO.MeasurementType.LENGTH,
                QuantityDTO.LengthUnitDTO.INCH, 1.0, QuantityDTO.LengthUnitDTO.INCH);
        demonstrateComparison(1.0, QuantityDTO.MeasurementType.LENGTH,
                QuantityDTO.LengthUnitDTO.FEET, 12.0, QuantityDTO.LengthUnitDTO.INCH);
        demonstrateComparison(1.0, QuantityDTO.MeasurementType.LENGTH,
                QuantityDTO.LengthUnitDTO.YARD, 3.0, QuantityDTO.LengthUnitDTO.FEET);
        demonstrateComparison(1.0, QuantityDTO.MeasurementType.LENGTH,
                QuantityDTO.LengthUnitDTO.CENTIMETER, 0.393701, QuantityDTO.LengthUnitDTO.INCH);

        // Conversion Examples (UC5)
        System.out.println("\n--- UC5: Unit-to-Unit Conversion Examples ---");
        demonstrateLengthConversion(1.0, QuantityDTO.LengthUnitDTO.FEET, QuantityDTO.LengthUnitDTO.INCH);
        demonstrateLengthConversion(3.0, QuantityDTO.LengthUnitDTO.YARD, QuantityDTO.LengthUnitDTO.FEET);
        demonstrateLengthConversion(36.0, QuantityDTO.LengthUnitDTO.INCH, QuantityDTO.LengthUnitDTO.YARD);
        demonstrateLengthConversion(1.0, QuantityDTO.LengthUnitDTO.CENTIMETER, QuantityDTO.LengthUnitDTO.INCH);
        demonstrateLengthConversion(0.0, QuantityDTO.LengthUnitDTO.FEET, QuantityDTO.LengthUnitDTO.INCH);

        System.out.println("\n--- Overloaded convert method ---");
        QuantityDTO lengthInYards = new QuantityDTO(3.0, QuantityDTO.MeasurementType.LENGTH,
                QuantityDTO.LengthUnitDTO.YARD);
        demonstrateConversion(lengthInYards, QuantityDTO.LengthUnitDTO.INCH);

        System.out.println("\n--- UC11: Volume Measurement Examples ---");
        System.out.println("\nEquality Tests:");
        demonstrateComparison(1.0, QuantityDTO.MeasurementType.VOLUME,
                QuantityDTO.VolumeUnitDTO.LITRE, 1.0, QuantityDTO.VolumeUnitDTO.LITRE);
        demonstrateComparison(1.0, QuantityDTO.MeasurementType.VOLUME,
                QuantityDTO.VolumeUnitDTO.LITRE, 1000.0, QuantityDTO.VolumeUnitDTO.MILLILITRE);
        demonstrateComparison(1.0, QuantityDTO.MeasurementType.VOLUME,
                QuantityDTO.VolumeUnitDTO.GALLON, 1.0, QuantityDTO.VolumeUnitDTO.GALLON);
        demonstrateComparison(1.0, QuantityDTO.MeasurementType.VOLUME,
                QuantityDTO.VolumeUnitDTO.LITRE, 0.264172, QuantityDTO.VolumeUnitDTO.GALLON);

        System.out.println("\n--- UC14: Temperature Measurement Examples ---");
        System.out.println("\nTemperature Equality Comparisons:");
        demonstrateComparison(0.0, QuantityDTO.MeasurementType.TEMPERATURE,
                QuantityDTO.TemperatureUnitDTO.CELSIUS, 32.0, QuantityDTO.TemperatureUnitDTO.FAHRENHEIT);
        demonstrateComparison(273.15, QuantityDTO.MeasurementType.TEMPERATURE,
                QuantityDTO.TemperatureUnitDTO.KELVIN, 0.0, QuantityDTO.TemperatureUnitDTO.CELSIUS);
        demonstrateComparison(212.0, QuantityDTO.MeasurementType.TEMPERATURE,
                QuantityDTO.TemperatureUnitDTO.FAHRENHEIT, 100.0, QuantityDTO.TemperatureUnitDTO.CELSIUS);
        demonstrateComparison(100.0, QuantityDTO.MeasurementType.TEMPERATURE,
                QuantityDTO.TemperatureUnitDTO.CELSIUS, 373.15, QuantityDTO.TemperatureUnitDTO.KELVIN);
        demonstrateComparison(50.0, QuantityDTO.MeasurementType.TEMPERATURE,
                QuantityDTO.TemperatureUnitDTO.CELSIUS, 122.0, QuantityDTO.TemperatureUnitDTO.FAHRENHEIT);

        System.out.println("\nTemperature Conversions:");
        demonstrateConversion(new QuantityDTO(100.0, QuantityDTO.MeasurementType.TEMPERATURE,
                        QuantityDTO.TemperatureUnitDTO.CELSIUS),
                QuantityDTO.TemperatureUnitDTO.FAHRENHEIT);
        demonstrateConversion(new QuantityDTO(32.0, QuantityDTO.MeasurementType.TEMPERATURE,
                        QuantityDTO.TemperatureUnitDTO.FAHRENHEIT),
                QuantityDTO.TemperatureUnitDTO.CELSIUS);
        demonstrateConversion(new QuantityDTO(273.15, QuantityDTO.MeasurementType.TEMPERATURE,
                        QuantityDTO.TemperatureUnitDTO.KELVIN),
                QuantityDTO.TemperatureUnitDTO.CELSIUS);
        demonstrateConversion(new QuantityDTO(0.0, QuantityDTO.MeasurementType.TEMPERATURE,
                        QuantityDTO.TemperatureUnitDTO.CELSIUS),
                QuantityDTO.TemperatureUnitDTO.KELVIN);
        demonstrateConversion(new QuantityDTO(-40.0, QuantityDTO.MeasurementType.TEMPERATURE,
                        QuantityDTO.TemperatureUnitDTO.CELSIUS),
                QuantityDTO.TemperatureUnitDTO.FAHRENHEIT);

        System.out.println("\nUnsupported Operations (Error Handling):");
        try {
            demonstrateAddition(
                    new QuantityDTO(100.0, QuantityDTO.MeasurementType.TEMPERATURE,
                            QuantityDTO.TemperatureUnitDTO.CELSIUS),
                    new QuantityDTO(50.0, QuantityDTO.MeasurementType.TEMPERATURE,
                            QuantityDTO.TemperatureUnitDTO.CELSIUS),
                    null);
        } catch (UnsupportedOperationException | QuantityMeasurementException e) {
            System.out.println("Output: Throws UnsupportedOperationException");
        }
        try {
            demonstrateSubtraction(
                    new QuantityDTO(100.0, QuantityDTO.MeasurementType.TEMPERATURE,
                            QuantityDTO.TemperatureUnitDTO.CELSIUS),
                    new QuantityDTO(50.0, QuantityDTO.MeasurementType.TEMPERATURE,
                            QuantityDTO.TemperatureUnitDTO.CELSIUS),
                    null);
        } catch (UnsupportedOperationException | QuantityMeasurementException e) {
            System.out.println("Output: Throws UnsupportedOperationException");
        }
        try {
            demonstrateDivision(
                    new QuantityDTO(100.0, QuantityDTO.MeasurementType.TEMPERATURE,
                            QuantityDTO.TemperatureUnitDTO.CELSIUS),
                    new QuantityDTO(50.0, QuantityDTO.MeasurementType.TEMPERATURE,
                            QuantityDTO.TemperatureUnitDTO.CELSIUS));
        } catch (UnsupportedOperationException | QuantityMeasurementException e) {
            System.out.println("Output: Throws UnsupportedOperationException");
        }

        System.out.println("\nCross-Category Prevention:");
        System.out.println("Input: new Quantity<>(100.0, CELSIUS).equals(new Quantity<>(100.0, FEET))");
        System.out.println("Output: "
                + new Quantity<>(100.0, TemperatureUnit.CELSIUS).equals(new Quantity<>(100.0, LengthUnit.FEET)));

        System.out.println("\nTemperature Comparisons with Other Categories:");
        System.out.println("Input: new Quantity<>(50.0, CELSIUS).equals(new Quantity<>(50.0, KILOGRAM))");
        System.out.println("Output: "
                + new Quantity<>(50.0, TemperatureUnit.CELSIUS).equals(new Quantity<>(50.0, WeightUnit.KILOGRAM)));

        System.out.println("\nConversion Tests:");
        demonstrateConversion(
                new QuantityDTO(1.0, QuantityDTO.MeasurementType.VOLUME, QuantityDTO.VolumeUnitDTO.LITRE),
                QuantityDTO.VolumeUnitDTO.MILLILITRE);
        demonstrateConversion(
                new QuantityDTO(2.0, QuantityDTO.MeasurementType.VOLUME, QuantityDTO.VolumeUnitDTO.GALLON),
                QuantityDTO.VolumeUnitDTO.LITRE);
        demonstrateConversion(
                new QuantityDTO(500.0, QuantityDTO.MeasurementType.VOLUME, QuantityDTO.VolumeUnitDTO.MILLILITRE),
                QuantityDTO.VolumeUnitDTO.GALLON);

        System.out.println("\nAddition Tests:");
        demonstrateAddition(
                new QuantityDTO(1.0, QuantityDTO.MeasurementType.VOLUME, QuantityDTO.VolumeUnitDTO.LITRE),
                new QuantityDTO(2.0, QuantityDTO.MeasurementType.VOLUME, QuantityDTO.VolumeUnitDTO.LITRE),
                null);
        demonstrateAddition(
                new QuantityDTO(1.0, QuantityDTO.MeasurementType.VOLUME, QuantityDTO.VolumeUnitDTO.LITRE),
                new QuantityDTO(1000.0, QuantityDTO.MeasurementType.VOLUME,
                        QuantityDTO.VolumeUnitDTO.MILLILITRE),
                null);
        demonstrateAddition(
                new QuantityDTO(1.0, QuantityDTO.MeasurementType.VOLUME, QuantityDTO.VolumeUnitDTO.LITRE),
                new QuantityDTO(1000.0, QuantityDTO.MeasurementType.VOLUME,
                        QuantityDTO.VolumeUnitDTO.MILLILITRE),
                QuantityDTO.VolumeUnitDTO.MILLILITRE);
        demonstrateAddition(
                new QuantityDTO(1.0, QuantityDTO.MeasurementType.VOLUME, QuantityDTO.VolumeUnitDTO.GALLON),
                new QuantityDTO(3.78541, QuantityDTO.MeasurementType.VOLUME,
                        QuantityDTO.VolumeUnitDTO.LITRE),
                QuantityDTO.VolumeUnitDTO.GALLON);

        System.out.println("\n--- UC12: Subtraction and Division Examples ---");
        System.out.println("\nSubtraction Operations:");
        demonstrateSubtraction(
                new QuantityDTO(10.0, QuantityDTO.MeasurementType.LENGTH, QuantityDTO.LengthUnitDTO.FEET),
                new QuantityDTO(6.0, QuantityDTO.MeasurementType.LENGTH, QuantityDTO.LengthUnitDTO.INCH),
                null);
        demonstrateSubtraction(
                new QuantityDTO(10.0, QuantityDTO.MeasurementType.WEIGHT, QuantityDTO.WeightUnitDTO.KILOGRAM),
                new QuantityDTO(5000.0, QuantityDTO.MeasurementType.WEIGHT, QuantityDTO.WeightUnitDTO.GRAM),
                null);
        demonstrateSubtraction(
                new QuantityDTO(5.0, QuantityDTO.MeasurementType.VOLUME, QuantityDTO.VolumeUnitDTO.LITRE),
                new QuantityDTO(500.0, QuantityDTO.MeasurementType.VOLUME,
                        QuantityDTO.VolumeUnitDTO.MILLILITRE),
                null);

        System.out.println("\nSubtraction with Explicit Target Unit:");
        demonstrateSubtraction(
                new QuantityDTO(10.0, QuantityDTO.MeasurementType.LENGTH, QuantityDTO.LengthUnitDTO.FEET),
                new QuantityDTO(6.0, QuantityDTO.MeasurementType.LENGTH, QuantityDTO.LengthUnitDTO.INCH),
                QuantityDTO.LengthUnitDTO.INCH);
        demonstrateSubtraction(
                new QuantityDTO(10.0, QuantityDTO.MeasurementType.WEIGHT, QuantityDTO.WeightUnitDTO.KILOGRAM),
                new QuantityDTO(5000.0, QuantityDTO.MeasurementType.WEIGHT, QuantityDTO.WeightUnitDTO.GRAM),
                QuantityDTO.WeightUnitDTO.GRAM);
        demonstrateSubtraction(
                new QuantityDTO(5.0, QuantityDTO.MeasurementType.VOLUME, QuantityDTO.VolumeUnitDTO.LITRE),
                new QuantityDTO(2.0, QuantityDTO.MeasurementType.VOLUME, QuantityDTO.VolumeUnitDTO.LITRE),
                QuantityDTO.VolumeUnitDTO.MILLILITRE);

        System.out.println("\nSubtraction Resulting in Negative Values:");
        demonstrateSubtraction(
                new QuantityDTO(5.0, QuantityDTO.MeasurementType.LENGTH, QuantityDTO.LengthUnitDTO.FEET),
                new QuantityDTO(10.0, QuantityDTO.MeasurementType.LENGTH, QuantityDTO.LengthUnitDTO.FEET),
                null);
        demonstrateSubtraction(
                new QuantityDTO(2.0, QuantityDTO.MeasurementType.WEIGHT, QuantityDTO.WeightUnitDTO.KILOGRAM),
                new QuantityDTO(5.0, QuantityDTO.MeasurementType.WEIGHT, QuantityDTO.WeightUnitDTO.KILOGRAM),
                null);

        System.out.println("\nSubtraction Resulting in Zero:");
        demonstrateSubtraction(
                new QuantityDTO(10.0, QuantityDTO.MeasurementType.LENGTH, QuantityDTO.LengthUnitDTO.FEET),
                new QuantityDTO(120.0, QuantityDTO.MeasurementType.LENGTH, QuantityDTO.LengthUnitDTO.INCH),
                null);
        demonstrateSubtraction(
                new QuantityDTO(1.0, QuantityDTO.MeasurementType.VOLUME, QuantityDTO.VolumeUnitDTO.LITRE),
                new QuantityDTO(1000.0, QuantityDTO.MeasurementType.VOLUME,
                        QuantityDTO.VolumeUnitDTO.MILLILITRE),
                null);

        System.out.println("\nDivision Operations:");
        demonstrateDivision(
                new QuantityDTO(10.0, QuantityDTO.MeasurementType.LENGTH, QuantityDTO.LengthUnitDTO.FEET),
                new QuantityDTO(2.0, QuantityDTO.MeasurementType.LENGTH, QuantityDTO.LengthUnitDTO.FEET));
        demonstrateDivision(
                new QuantityDTO(10.0, QuantityDTO.MeasurementType.LENGTH, QuantityDTO.LengthUnitDTO.FEET),
                new QuantityDTO(5.0, QuantityDTO.MeasurementType.LENGTH, QuantityDTO.LengthUnitDTO.FEET));
        demonstrateDivision(
                new QuantityDTO(24.0, QuantityDTO.MeasurementType.LENGTH, QuantityDTO.LengthUnitDTO.INCH),
                new QuantityDTO(2.0, QuantityDTO.MeasurementType.LENGTH, QuantityDTO.LengthUnitDTO.FEET));
        demonstrateDivision(
                new QuantityDTO(10.0, QuantityDTO.MeasurementType.WEIGHT, QuantityDTO.WeightUnitDTO.KILOGRAM),
                new QuantityDTO(5.0, QuantityDTO.MeasurementType.WEIGHT, QuantityDTO.WeightUnitDTO.KILOGRAM));
        demonstrateDivision(
                new QuantityDTO(5.0, QuantityDTO.MeasurementType.VOLUME, QuantityDTO.VolumeUnitDTO.LITRE),
                new QuantityDTO(10.0, QuantityDTO.MeasurementType.VOLUME, QuantityDTO.VolumeUnitDTO.LITRE));

        System.out.println("\nDivision with Different Units (Same Category):");
        demonstrateDivision(
                new QuantityDTO(12.0, QuantityDTO.MeasurementType.LENGTH, QuantityDTO.LengthUnitDTO.INCH),
                new QuantityDTO(1.0, QuantityDTO.MeasurementType.LENGTH, QuantityDTO.LengthUnitDTO.FEET));
        demonstrateDivision(
                new QuantityDTO(2000.0, QuantityDTO.MeasurementType.WEIGHT, QuantityDTO.WeightUnitDTO.GRAM),
                new QuantityDTO(1.0, QuantityDTO.MeasurementType.WEIGHT, QuantityDTO.WeightUnitDTO.KILOGRAM));
        demonstrateDivision(
                new QuantityDTO(1000.0, QuantityDTO.MeasurementType.VOLUME,
                        QuantityDTO.VolumeUnitDTO.MILLILITRE),
                new QuantityDTO(1.0, QuantityDTO.MeasurementType.VOLUME, QuantityDTO.VolumeUnitDTO.LITRE));

        System.out.println("\nError Cases (Testing Exceptions):");
        try {
            demonstrateSubtraction(
                    new QuantityDTO(10.0, QuantityDTO.MeasurementType.LENGTH, QuantityDTO.LengthUnitDTO.FEET),
                    null,
                    null);
        } catch (Exception e) {
            System.out.println("Output: Throws " + e.getClass().getSimpleName());
        }
        try {
            demonstrateDivision(
                    new QuantityDTO(10.0, QuantityDTO.MeasurementType.LENGTH, QuantityDTO.LengthUnitDTO.FEET),
                    new QuantityDTO(0.0, QuantityDTO.MeasurementType.LENGTH, QuantityDTO.LengthUnitDTO.FEET));
        } catch (Exception e) {
            System.out.println("Output: Throws " + e.getClass().getSimpleName());
        }
        try {
            demonstrateSubtraction(
                    new QuantityDTO(10.0, QuantityDTO.MeasurementType.LENGTH, QuantityDTO.LengthUnitDTO.FEET),
                    new QuantityDTO(5.0, QuantityDTO.MeasurementType.WEIGHT,
                            QuantityDTO.WeightUnitDTO.KILOGRAM),
                    null);
        } catch (Exception e) {
            System.out.println("Output: Throws " + e.getClass().getSimpleName());
        }
    }

    private static void initialize() {
        IQuantityMeasurementRepository repository = QuantityMeasurementCacheRepository.getInstance();
        IQuantityMeasurementService service = new QuantityMeasurementServiceImpl(repository);
        controller = new QuantityMeasurementController(service);
    }

    private static void demonstrateEqualityDTO(QuantityDTO q1, QuantityDTO q2) {
        System.out.println("Input: " + q1 + " and " + q2);
        boolean equal = controller.performComparison(q1, q2);
        if (equal) {
            System.out.println("Output: Equal (true)");
        } else {
            System.out.println("Output: Not Equal (false)");
        }
    }

    private static void demonstrateComparison(double value1, QuantityDTO.MeasurementType type1,
                                              QuantityDTO.IMeasurableUnit unit1,
                                              double value2, QuantityDTO.IMeasurableUnit unit2) {
        QuantityDTO q1 = new QuantityDTO(value1, type1, unit1);
        QuantityDTO q2 = new QuantityDTO(value2, type1, unit2);
        demonstrateEqualityDTO(q1, q2);
    }

    private static void demonstrateLengthConversion(double value, QuantityDTO.LengthUnitDTO fromUnit,
                                                    QuantityDTO.LengthUnitDTO toUnit) {
        QuantityDTO source = new QuantityDTO(value, QuantityDTO.MeasurementType.LENGTH, fromUnit);
        QuantityDTO result = controller.performConversion(source, toUnit);
        System.out.println("Input: convert(" + value + ", " + fromUnit + ", " + toUnit + ")");
        System.out.println("Output: " + result.getValue());
    }

    private static void demonstrateConversion(QuantityDTO quantity, QuantityDTO.IMeasurableUnit targetUnit) {
        QuantityDTO result = controller.performConversion(quantity, targetUnit);
        System.out.println(
                "Input: convert(" + quantity.getValue() + " " + quantity.getUnit() + " to " + targetUnit + ")");
        System.out.println("Output: " + result.getValue());
    }

    private static void demonstrateAddition(QuantityDTO q1, QuantityDTO q2, QuantityDTO.IMeasurableUnit targetUnit) {
        QuantityDTO.IMeasurableUnit effectiveTarget = targetUnit != null ? targetUnit : q1.getUnit();
        System.out.println("Input: " + q1 + ".add(" + q2 + ", " + effectiveTarget + ")");
        if (q2 == null) {
            System.out.println("Output: Throws IllegalArgumentException");
            throw new IllegalArgumentException("Second operand cannot be null");
        }
        QuantityDTO result = controller.performAddition(q1, q2, targetUnit);
        System.out.println("Output: " + result);
    }

    private static void demonstrateSubtraction(QuantityDTO q1, QuantityDTO q2,
                                               QuantityDTO.IMeasurableUnit targetUnit) {
        QuantityDTO.IMeasurableUnit effectiveTarget = targetUnit != null ? targetUnit : q1.getUnit();
        System.out.println("Input: " + q1 + ".subtract(" + q2 + (targetUnit != null ? ", " + effectiveTarget : "") +
                ")");
        if (q2 == null) {
            System.out.println("Output: Throws IllegalArgumentException");
            throw new IllegalArgumentException("Second operand cannot be null");
        }
        QuantityDTO result = controller.performSubtraction(q1, q2, targetUnit);
        System.out.println("Output: " + result);
    }

    private static void demonstrateDivision(QuantityDTO q1, QuantityDTO q2) {
        System.out.println("Input: " + q1 + ".divide(" + q2 + ")");
        if (q2 != null && q2.getValue() == 0.0) {
            System.out.println("Output: Throws ArithmeticException");
            throw new ArithmeticException("Division by zero");
        }
        double result = controller.performDivision(q1, q2);
        System.out.println("Output: " + result);
    }
}