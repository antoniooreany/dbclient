package com.gorshkov.dbclient;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.Scanner;

public class Client {

    private final static String DEFAULT_URL = "jdbc:mysql://localhost:3306";
    private final static String DEFAULT_USER = "user";
    private final static String DEFAULT_PASSWORD = "7777777";
    private final static String SQL_SELECT_FROM_TEST_TABLE = "SELECT * FROM test_db.test_table;";
    private final static String SQL_SELECT_FROM_BOOKS = "SELECT * FROM test_db.books;";
    private final static String SQL_SELECT_FROM_WHERE = "SELECT * FROM test_db.test_table WHERE id > 2;";
    private final static String SQL_UPDATE_WHERE = "UPDATE test_db.test_table SET name='Juan' WHERE id=3;";
    private final static String FILE_PATH = "C:/Users/GAS_Dell_XPS9310/IdeaProjects/krtkabna/dbclient/src/main/resources/report1.html";

    public void start() throws SQLException, IOException {
//        String userInput = getUserInput();

        try (Connection connection = DriverManager.getConnection(
                DEFAULT_URL, DEFAULT_USER, DEFAULT_PASSWORD);) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SQL_SELECT_FROM_BOOKS);
//            writeResultToConsole(resultSet);
            writeResultToFile(resultSet);
        }
    }

    private String getUserInput() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    private void writeResultToConsole(ResultSet resultSet) throws SQLException {
        int columnCount = resultSet.getMetaData().getColumnCount();
        while (resultSet.next()) {
            for (int i = 0; i < columnCount; i++) {
                System.out.print(resultSet.getString(i + 1) + "   ");
            }
            System.out.println();
        }
    }

    private void writeResultToFile(ResultSet resultSet) throws SQLException, IOException {
        ResultSetMetaData metaData = resultSet.getMetaData(); //TODO
        int columnCount = metaData.getColumnCount();

        StringBuilder builder = new StringBuilder("<table>\n");

        String[] columnNames = getColumnNames(resultSet);
        String headers = getHeadersToHtml(columnCount, columnNames);

        builder.append(headers);

        while (resultSet.next()) {
            builder.append("  <tr>\n");
            for (int i = 0; i < columnCount; i++) {
                builder.append("    <td>" + resultSet.getString(i + 1) + "</td>\n");
            }
        }

        builder.append("</table>");

//        System.out.println(builder);

        writeToFile(builder);
    }

    private void writeToFile(StringBuilder builder) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH));) {
            writer.write(builder.toString());
        }
    }

    private String getHeadersToHtml(int columnCount, String[] columnNames) {
        StringBuilder result = new StringBuilder("  <tr>\n");
        for (int i = 0; i < columnCount; i++) {
            result
                    .append("    <th>")
                    .append(columnNames[i])
                    .append("</th>\n");
        }
        return result.toString();
    }

    private String[] getColumnNames(ResultSet resultSet) throws SQLException {
        ResultSetMetaData metaData = resultSet.getMetaData();
        int columnCount = metaData.getColumnCount();

        String[] columnNames = new String[columnCount];
        for (int i = 0; i < columnCount; i++) {
            columnNames[i] = metaData.getColumnName(i + 1);
        }
        return columnNames;
    }
}
