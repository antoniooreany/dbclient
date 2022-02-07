package com.gorshkov.dbclient;

import java.sql.*;

public class Client {

    private final static String DEFAULT_URL = "jdbc:mysql://localhost:3306";
    private final static String DEFAULT_USER = "user";
    private final static String DEFAULT_PASSWORD = "7777777";
    private final static String SQL_SELECT_FROM = "SELECT * FROM test_db.test_table";

    public void start() throws SQLException {
        try (Connection connection = DriverManager.getConnection(
                DEFAULT_URL, DEFAULT_USER, DEFAULT_PASSWORD);) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SQL_SELECT_FROM);
            while (resultSet.next())
                System.out.println(resultSet.getInt(1) + "  "
                        + resultSet.getString(2));
        }
    }
}
