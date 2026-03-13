package com.chaman.quantitymeasurement.service;

import com.chaman.quantitymeasurement.dto.QuantityDTO;
import com.chaman.quantitymeasurement.dto.QuantityDTO.IMeasurableUnit;

public interface IQuantityMeasurementService {

    boolean compare(QuantityDTO q1, QuantityDTO q2);

    QuantityDTO convert(QuantityDTO input, IMeasurableUnit targetUnit);

    QuantityDTO add(QuantityDTO q1, QuantityDTO q2);

    QuantityDTO subtract(QuantityDTO q1, QuantityDTO q2);

    double divide(QuantityDTO q1, QuantityDTO q2);
}