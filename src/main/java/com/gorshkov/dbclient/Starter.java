package com.gorshkov.dbclient;

import java.io.IOException;
import java.sql.SQLException;

public class Starter {
    public static void main(String[] args) throws SQLException, ClassNotFoundException, IOException {
        Client client = new Client();
        client.start();
    }
}
