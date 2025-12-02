package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class Util {

    private static final String PASSWORD = "root";
    private static final String USERNAME = "root";
    private static final String HOST = "jdbc:mysql://localhost:3306/sqltestdb";

    public Util() {

    }

    public Connection getConnection() {
        try {
            return DriverManager.getConnection(HOST, USERNAME, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
