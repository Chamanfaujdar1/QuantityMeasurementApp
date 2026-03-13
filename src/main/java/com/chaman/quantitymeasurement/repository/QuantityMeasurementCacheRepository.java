package com.chaman.quantitymeasurement.repository;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.chaman.quantitymeasurement.entity.QuantityMeasurementEntity;

/**
 * Simple in-memory cache based repository with optional serialization to disk.
 * <p>
 * Implemented as a Singleton to provide a single shared cache of
 * {@link QuantityMeasurementEntity} across the application.
 */
public class QuantityMeasurementCacheRepository implements IQuantityMeasurementRepository {

    private static final String DEFAULT_FILE_NAME = "quantity_measurements.dat";

    private static final QuantityMeasurementCacheRepository INSTANCE =
            new QuantityMeasurementCacheRepository(DEFAULT_FILE_NAME);

    private final File storageFile;
    private final List<QuantityMeasurementEntity> cache = new ArrayList<>();

    public static QuantityMeasurementCacheRepository getInstance() {
        return INSTANCE;
    }

    // Constructor visible for extensibility / testing
    QuantityMeasurementCacheRepository(String fileName) {
        this.storageFile = new File(fileName);
        loadFromDisk();
    }

    @Override
    public synchronized void save(QuantityMeasurementEntity entity) {
        if (entity == null) {
            return;
        }
        cache.add(entity);
        saveToDisk();
    }

    @Override
    public synchronized List<QuantityMeasurementEntity> getAllMeasurements() {
        return Collections.unmodifiableList(new ArrayList<>(cache));
    }

    @SuppressWarnings("unchecked")
    private void loadFromDisk() {
        if (!storageFile.exists()) {
            return;
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(storageFile))) {
            Object obj = ois.readObject();
            if (obj instanceof List) {
                cache.clear();
                cache.addAll((List<QuantityMeasurementEntity>) obj);
            }
        } catch (IOException | ClassNotFoundException e) {
            // If loading fails we fall back to an empty in-memory cache.
        }
    }

    private void saveToDisk() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(storageFile))) {
            oos.writeObject(new ArrayList<>(cache));
            oos.flush();
        } catch (IOException e) {
            // Persistence errors are non-fatal for the core measurement logic.
        }
    }
}