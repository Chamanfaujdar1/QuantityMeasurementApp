package com.chaman.quantitymeasurement.dto;

public class QuantityDTO {

    /**
     * Marker interface for DTO units. DTO units are decoupled from the domain
     * units so that API contracts can evolve independently from internal
     * representations.
     */
    public interface IMeasurableUnit {
    }

    /**
     * High level measurement categories supported by the application.
     */
    public enum MeasurementType {
        LENGTH,
        WEIGHT,
        VOLUME,
        TEMPERATURE
    }

    /**
     * DTO representation of length units.
     */
    public enum LengthUnitDTO implements IMeasurableUnit {
        FEET,
        INCH,
        YARD,
        CENTIMETER
    }

    /**
     * DTO representation of weight units.
     */
    public enum WeightUnitDTO implements IMeasurableUnit {
        KILOGRAM,
        GRAM,
        POUND
    }

    /**
     * DTO representation of volume units.
     */
    public enum VolumeUnitDTO implements IMeasurableUnit {
        LITRE,
        MILLILITRE,
        GALLON
    }

    /**
     * DTO representation of temperature units.
     */
    public enum TemperatureUnitDTO implements IMeasurableUnit {
        CELSIUS,
        FAHRENHEIT,
        KELVIN
    }

    private final double value;
    private final MeasurementType measurementType;
    private final IMeasurableUnit unit;

    public QuantityDTO(double value, MeasurementType measurementType, IMeasurableUnit unit) {
        if (!Double.isFinite(value)) {
            throw new IllegalArgumentException("Value must be finite");
        }
        if (measurementType == null) {
            throw new IllegalArgumentException("Measurement type cannot be null");
        }
        if (unit == null) {
            throw new IllegalArgumentException("Unit cannot be null");
        }
        validateUnitMatchesMeasurement(measurementType, unit);
        this.value = value;
        this.measurementType = measurementType;
        this.unit = unit;
    }

    private static void validateUnitMatchesMeasurement(MeasurementType measurementType, IMeasurableUnit unit) {
        switch (measurementType) {
            case LENGTH:
                if (!(unit instanceof LengthUnitDTO)) {
                    throw new IllegalArgumentException("Expected a length unit DTO for LENGTH measurement");
                }
                break;
            case WEIGHT:
                if (!(unit instanceof WeightUnitDTO)) {
                    throw new IllegalArgumentException("Expected a weight unit DTO for WEIGHT measurement");
                }
                break;
            case VOLUME:
                if (!(unit instanceof VolumeUnitDTO)) {
                    throw new IllegalArgumentException("Expected a volume unit DTO for VOLUME measurement");
                }
                break;
            case TEMPERATURE:
                if (!(unit instanceof TemperatureUnitDTO)) {
                    throw new IllegalArgumentException("Expected a temperature unit DTO for TEMPERATURE measurement");
                }
                break;
            default:
                throw new IllegalArgumentException("Unsupported measurement type: " + measurementType);
        }
    }

    public double getValue() {
        return value;
    }

    public MeasurementType getMeasurementType() {
        return measurementType;
    }

    public IMeasurableUnit getUnit() {
        return unit;
    }

    @Override
    public String toString() {
        return "QuantityDTO{" +
                "value=" + value +
                ", measurementType=" + measurementType +
                ", unit=" + unit +
                '}';
    }
}