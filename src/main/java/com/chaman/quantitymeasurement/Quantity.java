package com.chaman.quantitymeasurement;

public class Quantity {
    private final double value;
    private final LengthUnit unit;

    public Quantity(double value, LengthUnit unit) {
        this.value = value;
        this.unit = unit;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Quantity quantity = (Quantity) obj;
        return Double.compare(compareValue(quantity), compareValue(this)) == 0;
    }

    private double compareValue(Quantity quantity) {
        return quantity.value * quantity.unit.getConversionFactor();
    }
}
