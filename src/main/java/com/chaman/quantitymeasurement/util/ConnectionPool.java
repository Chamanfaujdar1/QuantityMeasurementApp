package com.chaman.quantitymeasurement.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionPool {

    private static final String URL =
            "jdbc:mysql://localhost:3306/quantitydb";

    private static final String USER = "root";
    private static final String PASSWORD = "Ch@m@n123";

    public Connection getConnection() throws SQLException {

        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}