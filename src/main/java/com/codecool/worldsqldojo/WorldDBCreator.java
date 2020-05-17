package com.codecool.worldsqldojo;

import org.postgresql.copy.CopyManager;
import org.postgresql.core.BaseConnection;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;

public class WorldDBCreator {
    private static final String DATABASE = "jdbc:postgresql://localhost:5432/world";
    private static final String DB_USER = System.getenv("POSTGRES_DB_USER");
    private static final String DB_PASSWORD = System.getenv("POSTGRES_DB_PASSWORD");

    public WorldDBCreator() {
        executeUpdateFromFile("src/main/resources/init_db.sql");
    }

    public Connection getConnection() {
        try {
            return DriverManager.getConnection(
                    DATABASE,
                    DB_USER,
                    DB_PASSWORD);
        } catch (SQLException e) {
            System.err.println("ERROR: Connection error.");
            e.printStackTrace();
        }
        return null;
    }

    public void executeUpdate(String query) throws SQLException {
        try (Connection connection = getConnection()) {

            PreparedStatement statement = connection.prepareStatement(query);
            statement.executeUpdate();

        } catch (SQLTimeoutException e) {
            System.err.println("ERROR: SQL Timeout");
        }
    }

    public ResultSet executeQuery(String query) throws SQLException {
        try (Connection connection = getConnection()) {

            PreparedStatement statement = connection.prepareStatement(query);
            return statement.executeQuery();

        } catch (SQLTimeoutException e) {
            System.err.println("ERROR: SQL Timeout");
        }
        return null;
    }

    public void copyDataFromFile(String tableName, String filePath) throws SQLException {
        FileReader fileReader = null;

        try (Connection connection = getConnection()) {

            try {
                fileReader = new FileReader(filePath);
            } catch (FileNotFoundException e) {
                System.err.println("ERROR: File not found!");
            }

            CopyManager copyManager = new CopyManager((BaseConnection) connection);
            try {
                copyManager.copyIn("COPY " + tableName + " FROM STDIN", fileReader);
            } catch (IOException e) {
                System.err.println("ERROR: IO Error occured!");
                e.printStackTrace();
            }

        } catch (SQLTimeoutException e) {
            System.err.println("ERROR: SQL Timeout");
        }
    }

    public void executeUpdateFromFile(String filePath) {
        String query = "";
        try {
            query = new String(Files.readAllBytes(Paths.get(filePath)));
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
