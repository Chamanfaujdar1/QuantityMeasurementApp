package com.chaman.quantitymeasurement.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionPool {

	private static final String URL =
		    "jdbc:mysql://localhost:3306/quantitydb?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";

    private static final String USER = "root";
    private static final String PASSWORD = "Ch@m@n123";

    public Connection getConnection() throws SQLException {

        try {
            // ✅ LOAD DRIVER (IMPORTANT)
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("MySQL Driver not found", e);
        }

        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}