package com.chaman.quantitymeasurement.controller;

import com.chaman.quantitymeasurement.service.*;
import com.chaman.quantitymeasurement.dto.*;
import com.chaman.quantitymeasurement.entity.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/quantities")
public class QuantityMeasurementController {

    @Autowired
    private IQuantityMeasurementService service;

    @PostMapping("/compare")
    public ResponseEntity<QuantityMeasurementEntity> compare(@RequestBody QuantityInputDTO input) {
        return ResponseEntity.ok(service.compare(input));
    }

    @PostMapping("/convert")
    public ResponseEntity<QuantityMeasurementEntity> convert(@RequestBody QuantityInputDTO input) {
        return ResponseEntity.ok(service.convert(input));
    }

    @PostMapping("/add")
    public ResponseEntity<QuantityMeasurementEntity> add(@RequestBody QuantityInputDTO input) {
        return ResponseEntity.ok(service.add(input));
    }

    @PostMapping("/subtract")
    public ResponseEntity<QuantityMeasurementEntity> subtract(@RequestBody QuantityInputDTO input) {
        return ResponseEntity.ok(service.subtract(input));
    }

    @PostMapping("/divide")
    public ResponseEntity<QuantityMeasurementEntity> divide(@RequestBody QuantityInputDTO input) {
        return ResponseEntity.ok(service.divide(input));
    }

    // Paginated history - GET /api/v1/quantities/history/operation/ADD?page=0&size=10
    @GetMapping("/history/operation/{operation}")
    public ResponseEntity<Page<QuantityMeasurementEntity>> getHistory(
            @PathVariable String operation,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        return ResponseEntity.ok(service.getHistoryByOperation(operation, page, size));
    }

    @GetMapping("/count/{operation}")
    public ResponseEntity<Long> getCount(@PathVariable String operation) {
        return ResponseEntity.ok(service.getOperationCount(operation));
    }
}
