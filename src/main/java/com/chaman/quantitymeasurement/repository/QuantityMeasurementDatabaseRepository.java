package com.chaman.quantitymeasurement.repository;

import com.chaman.quantitymeasurement.entity.QuantityMeasurementEntity;
import com.chaman.quantitymeasurement.exception.DatabaseException;
import com.chaman.quantitymeasurement.util.ConnectionPool;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QuantityMeasurementDatabaseRepository
        implements IQuantityMeasurementRepository {

    private final ConnectionPool pool = new ConnectionPool();

    @Override
    public void save(QuantityMeasurementEntity entity) {

        String sql = "INSERT INTO quantity_measurements " +
                "(operation, operand1, operand2, result) VALUES (?, ?, ?, ?)";

        try (Connection conn = pool.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, entity.getOperation());
            stmt.setDouble(2, entity.getOperand1());
            stmt.setDouble(3, entity.getOperand2());
            stmt.setDouble(4, entity.getResult());

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new DatabaseException("Error saving measurement", e);
        }
    }

    @Override
    public List<QuantityMeasurementEntity> findAll() {

        List<QuantityMeasurementEntity> list = new ArrayList<>();

        String sql = "SELECT * FROM quantity_measurements";

        try (Connection conn = pool.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {

                QuantityMeasurementEntity entity =
                        new QuantityMeasurementEntity(
                                rs.getString("operation"),
                                rs.getDouble("operand1"),
                                rs.getDouble("operand2"),
                                rs.getDouble("result")
                        );

                list.add(entity);
            }

        } catch (SQLException e) {
            throw new DatabaseException("Error fetching data", e);
        }

        return list;
    }

    @Override
    public void deleteAllMeasurements() {

        String sql = "TRUNCATE TABLE quantity_measurements";

        try (Connection conn = pool.getConnection();
             Statement stmt = conn.createStatement()) {

            stmt.executeUpdate(sql);

        } catch (SQLException e) {
            throw new DatabaseException("Error deleting records", e);
        }
    }
}
