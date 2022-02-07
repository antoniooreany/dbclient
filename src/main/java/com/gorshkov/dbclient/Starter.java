package com.gorshkov.dbclient;

import java.sql.SQLException;

public class Starter {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        Client client = new Client();
        client.start();
    }
}
