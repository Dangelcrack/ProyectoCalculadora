package com.github.dangelcrack.model.connection;

import com.github.dangelcrack.utils.XMLManager;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
public class ConnectionMariaDB {
    // XML file containing the connection properties
    private final static String FILE = "connection.xml";
    // Single instance of the class (Singleton pattern)
    private static ConnectionMariaDB _instance;
    // Connection object for database connection
    private static Connection conn;

    /**
     * Private constructor that initializes the connection using the connection properties read from the XML file.
     * It also validates the database by executing the necessary SQL scripts.
     */
    private ConnectionMariaDB() {
        // Read the connection properties from the XML file
        ConnectionProperties properties = (ConnectionProperties) XMLManager.readXML(new ConnectionProperties(), FILE);
        try {
            // Validate the database by executing the necessary SQL scripts
            validateDB(properties);
            // Establish the connection to the database with the read properties
            conn = DriverManager.getConnection(properties.getURL() + "/" + properties.getDatabase(), properties.getUser(), properties.getPassword());
        } catch (SQLException e) {
            e.printStackTrace();
            conn = null;
        }
    }

    /**
     * Validates the database by executing the necessary SQL scripts for initial configuration.
     * @param properties The connection properties read from the XML file.
     */
    private void validateDB(ConnectionProperties properties) {
        try {
            // Establish an initial connection to the database
            conn = DriverManager.getConnection(properties.getURL(), properties.getUser(), properties.getPassword());
            Statement stmt = conn.createStatement();
            // Read the SQL file with the configuration scripts
            String sql = readFile("DBPokedex.sql");
            // Split the SQL scripts into individual statements
            String[] sqlStatements = sql.split(";");
            // Execute each SQL statement if it is not empty
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

    /**
     * Reads the content of a file and returns it as a string.
     * @param filePath The path of the file to read.
     * @return The content of the file as a string.
     */
    private static String readFile(String filePath) {
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            // Read the file line by line and construct the content string
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return content.toString();
    }

    /**
     * Gets the single instance of the class and the database connection.
     * @return The database connection.
     */
    public static Connection getConnection() {
        if (_instance == null) {
            _instance = new ConnectionMariaDB();
        }
        return conn;
    }

    /**
     * Closes the database connection if it is open.
     */
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