package com.github.dangelcrack.model.connection;

import com.github.dangelcrack.utils.XMLManager;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectionMariaDB {
    private final static String FILE = "connection.xml";
    private static ConnectionMariaDB _instance;
    private static Connection conn;

    private ConnectionMariaDB() {
        ConnectionProperties properties = (ConnectionProperties) XMLManager.readXML(new ConnectionProperties(), FILE);
        try {
            validateDB(properties);
            conn = DriverManager.getConnection(properties.getURL()+"/"+properties.getDatabase(), properties.getUser(), properties.getPassword());
        } catch (SQLException e) {
            e.printStackTrace();
            conn = null;
        }
    }
    private void validateDB(ConnectionProperties properties){
        try {
            conn = DriverManager.getConnection(properties.getURL(), properties.getUser(), properties.getPassword());
            Statement stmt = conn.createStatement();
            String sql = readFile("DBPokedex.sql");
            String[] sqlStatements = sql.split(";");
            for (String sqlStatement : sqlStatements) {
                if (!sqlStatement.trim().isEmpty()) {
                    stmt.execute(sqlStatement);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            conn = null;
        }

    }

    private static String readFile(String filePath) {
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return content.toString();
    }
    public static Connection getConnection() {
        if (_instance == null) {
            _instance = new ConnectionMariaDB();
        }
        return conn;
    }

    public static void closeConnection() {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


}