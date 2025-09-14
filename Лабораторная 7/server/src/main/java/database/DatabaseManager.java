package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseManager {
    private final String URL;
    private final String USER;
    private final String PASSWORD;

    public DatabaseManager(String URL, String USER, String PASSWORD) {
        this.URL = URL;
        this.USER = USER;
        this.PASSWORD = PASSWORD;
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public void initializeDatabase() {
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {

            // Create users table
            statement.execute("""
                CREATE TABLE IF NOT EXISTS users (
                    id SERIAL PRIMARY KEY,
                    username VARCHAR(255) UNIQUE NOT NULL,
                    password_hash VARCHAR(255) NOT NULL
                );
            """);

            // Create labworks table
            statement.execute("""
                CREATE TABLE IF NOT EXISTS labworks (
                    id BIGSERIAL PRIMARY KEY,
                    name VARCHAR(255) NOT NULL,
                    coordinates_x INTEGER NOT NULL,
                    coordinates_y DOUBLE PRECISION NOT NULL,
                    creation_date TIMESTAMP DEFAULT NOW(),
                    minimal_point DOUBLE PRECISION,
                    tuned_in_works BIGINT,
                    difficulty VARCHAR(255),
                    discipline_name VARCHAR(255),
                    discipline_labs_count INTEGER,
                    owner_id INTEGER REFERENCES users(id) ON DELETE CASCADE
                );
            """);
            System.out.println("Database schema initialized.");
        } catch (SQLException e) {
            System.err.println("Error initializing database: " + e.getMessage());
        }
    }
} 