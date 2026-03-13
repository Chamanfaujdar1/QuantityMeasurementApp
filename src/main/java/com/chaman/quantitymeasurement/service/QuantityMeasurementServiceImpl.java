package com.chaman.quantitymeasurement.service;

import com.chaman.quantitymeasurement.IMeasurable;
import com.chaman.quantitymeasurement.LengthUnit;
import com.chaman.quantitymeasurement.Quantity;
import com.chaman.quantitymeasurement.TemperatureUnit;
import com.chaman.quantitymeasurement.VolumeUnit;
import com.chaman.quantitymeasurement.WeightUnit;
import com.chaman.quantitymeasurement.dto.QuantityDTO;
import com.chaman.quantitymeasurement.entity.QuantityMeasurementEntity;
import com.chaman.quantitymeasurement.exception.QuantityMeasurementException;
import com.chaman.quantitymeasurement.model.QuantityModel;
import com.chaman.quantitymeasurement.repository.IQuantityMeasurementRepository;

public abstract class QuantityMeasurementServiceImpl implements IQuantityMeasurementService {

    private final IQuantityMeasurementRepository repository;

    public QuantityMeasurementServiceImpl(IQuantityMeasurementRepository repository) {
        if (repository == null) {
            throw new IllegalArgumentException("Repository cannot be null");
        }
        this.repository = repository;
    }

    @Override
    public boolean compare(QuantityDTO first, QuantityDTO second) {
        QuantityModel<IMeasurable> left = toModel(first);
        QuantityModel<IMeasurable> right = toModel(second);
        boolean result = new Quantity<>(left.getValue(), left.getUnit())
                .equals(new Quantity<>(right.getValue(), right.getUnit()));

        QuantityMeasurementEntity entity = new QuantityMeasurementEntity(
                left,
                right,
                QuantityMeasurementEntity.OperationType.COMPARISON,
                (QuantityModel<?>) null);
        repository.save(entity);
        return result;
    }

    @Override
    public QuantityDTO convert(QuantityDTO source, QuantityDTO.IMeasurableUnit targetUnitDTO) {
        QuantityModel<IMeasurable> model = toModel(source);
        IMeasurable targetUnit = mapDtoUnitToDomain(source.getMeasurementType(), targetUnitDTO);
        try {
            Quantity<IMeasurable> quantity = new Quantity<>(model.getValue(), model.getUnit());
            Quantity<IMeasurable> converted = quantity.convertTo(targetUnit);
            QuantityModel<IMeasurable> resultModel = new QuantityModel<>(converted.getValue(), converted.getUnit());
            repository.save(new QuantityMeasurementEntity(model,
                    QuantityMeasurementEntity.OperationType.CONVERSION, resultModel));
            return toDto(resultModel, source.getMeasurementType());
        } catch (RuntimeException e) {
            repository.save(new QuantityMeasurementEntity(model, null,
                    QuantityMeasurementEntity.OperationType.CONVERSION, e.getMessage()));
            throw new QuantityMeasurementException("Conversion failed", e);
        }
    }

    public QuantityDTO add(QuantityDTO first, QuantityDTO second, QuantityDTO.IMeasurableUnit targetUnitDTO) {
        QuantityModel<IMeasurable> left = toModel(first);
        QuantityModel<IMeasurable> right = toModel(second);
        QuantityDTO.IMeasurableUnit effectiveTarget = targetUnitDTO != null ? targetUnitDTO : first.getUnit();
        IMeasurable targetUnit = mapDtoUnitToDomain(first.getMeasurementType(), effectiveTarget);
        try {
            Quantity<IMeasurable> q1 = new Quantity<>(left.getValue(), left.getUnit());
            Quantity<IMeasurable> q2 = new Quantity<>(right.getValue(), right.getUnit());
            Quantity<IMeasurable> sum = q1.add(q2, targetUnit);
            QuantityModel<IMeasurable> resultModel = new QuantityModel<>(sum.getValue(), sum.getUnit());
            repository.save(new QuantityMeasurementEntity(left, right,
                    QuantityMeasurementEntity.OperationType.ADDITION, resultModel));
            return toDto(resultModel, first.getMeasurementType());
        } catch (RuntimeException e) {
            repository.save(new QuantityMeasurementEntity(left, right,
                    QuantityMeasurementEntity.OperationType.ADDITION, e.getMessage()));
            throw new QuantityMeasurementException("Addition failed", e);
        }
    }

    public QuantityDTO subtract(QuantityDTO first, QuantityDTO second, QuantityDTO.IMeasurableUnit targetUnitDTO) {
        QuantityModel<IMeasurable> left = toModel(first);
        QuantityModel<IMeasurable> right = toModel(second);
        QuantityDTO.IMeasurableUnit effectiveTarget = targetUnitDTO != null ? targetUnitDTO : first.getUnit();
        IMeasurable targetUnit = mapDtoUnitToDomain(first.getMeasurementType(), effectiveTarget);
        try {
            Quantity<IMeasurable> q1 = new Quantity<>(left.getValue(), left.getUnit());
            Quantity<IMeasurable> q2 = new Quantity<>(right.getValue(), right.getUnit());
            Quantity<IMeasurable> diff = q1.subtract(q2, targetUnit);
            QuantityModel<IMeasurable> resultModel = new QuantityModel<>(diff.getValue(), diff.getUnit());
            repository.save(new QuantityMeasurementEntity(left, right,
                    QuantityMeasurementEntity.OperationType.SUBTRACTION, resultModel));
            return toDto(resultModel, first.getMeasurementType());
        } catch (RuntimeException e) {
            repository.save(new QuantityMeasurementEntity(left, right,
                    QuantityMeasurementEntity.OperationType.SUBTRACTION, e.getMessage()));
            throw new QuantityMeasurementException("Subtraction failed", e);
        }
    }

    @Override
    public double divide(QuantityDTO first, QuantityDTO second) {
        QuantityModel<IMeasurable> left = toModel(first);
        QuantityModel<IMeasurable> right = toModel(second);
        try {
            Quantity<IMeasurable> q1 = new Quantity<>(left.getValue(), left.getUnit());
            Quantity<IMeasurable> q2 = new Quantity<>(right.getValue(), right.getUnit());
            double result = q1.divide(q2);
            repository.save(new QuantityMeasurementEntity(left, right,
                    QuantityMeasurementEntity.OperationType.DIVISION, result));
            return result;
        } catch (RuntimeException e) {
            repository.save(new QuantityMeasurementEntity(left, right,
                    QuantityMeasurementEntity.OperationType.DIVISION, e.getMessage()));
            throw new QuantityMeasurementException("Division failed", e);
        }
    }

    @SuppressWarnings("unchecked")
    private QuantityModel<IMeasurable> toModel(QuantityDTO dto) {
        if (dto == null) {
            throw new QuantityMeasurementException("QuantityDTO cannot be null");
        }
        IMeasurable unit = mapDtoUnitToDomain(dto.getMeasurementType(), dto.getUnit());
        return new QuantityModel<>(dto.getValue(), unit);
    }

    private QuantityDTO toDto(QuantityModel<IMeasurable> model, QuantityDTO.MeasurementType type) {
        QuantityDTO.IMeasurableUnit dtoUnit = mapDomainUnitToDto(type, model.getUnit());
        return new QuantityDTO(model.getValue(), type, dtoUnit);
    }

    private IMeasurable mapDtoUnitToDomain(QuantityDTO.MeasurementType type, QuantityDTO.IMeasurableUnit dtoUnit) {
        switch (type) {
            case LENGTH:
                QuantityDTO.LengthUnitDTO lengthUnitDTO = (QuantityDTO.LengthUnitDTO) dtoUnit;
                return LengthUnit.valueOf(lengthUnitDTO.name());
            case WEIGHT:
                QuantityDTO.WeightUnitDTO weightUnitDTO = (QuantityDTO.WeightUnitDTO) dtoUnit;
                return WeightUnit.valueOf(weightUnitDTO.name());
            case VOLUME:
                QuantityDTO.VolumeUnitDTO volumeUnitDTO = (QuantityDTO.VolumeUnitDTO) dtoUnit;
                return VolumeUnit.valueOf(volumeUnitDTO.name());
            case TEMPERATURE:
                QuantityDTO.TemperatureUnitDTO temperatureUnitDTO = (QuantityDTO.TemperatureUnitDTO) dtoUnit;
                return TemperatureUnit.valueOf(temperatureUnitDTO.name());
            default:
                throw new QuantityMeasurementException("Unsupported measurement type: " + type);
        }
    }

    private QuantityDTO.IMeasurableUnit mapDomainUnitToDto(QuantityDTO.MeasurementType type, IMeasurable unit) {
        switch (type) {
            case LENGTH:
                return QuantityDTO.LengthUnitDTO.valueOf(((LengthUnit) unit).name());
            case WEIGHT:
                return QuantityDTO.WeightUnitDTO.valueOf(((WeightUnit) unit).name());
            case VOLUME:
                return QuantityDTO.VolumeUnitDTO.valueOf(((VolumeUnit) unit).name());
            case TEMPERATURE:
                return QuantityDTO.TemperatureUnitDTO.valueOf(((TemperatureUnit) unit).name());
            default:
                throw new QuantityMeasurementException("Unsupported measurement type: " + type);
        }
    }
}