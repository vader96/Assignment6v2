package org.example;

import org.example.Amazon.Database;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DatabaseTest {
    private Database database;

    @BeforeEach
    void setUp() {
        database = new Database();
        database.resetDatabase();
    }

    // Test cases are used to ensure 100% code coverage by analyzing the structure of the Database file.
    @Test
    @DisplayName("structural-based")
    void testThrowException() {
        // Throws an exception to see whether the withSql method catches it.
        assertThrows(RuntimeException.class, () -> {
            database.withSql(() -> {
                throw new SQLException();
            });
        });
    }
    @Test
    @DisplayName("structural-based")
    void testCloseDatabase() {
        database.close();
        // Since the database is closed, trying to execute a query should throw the exception in withSql()
        assertThrows(RuntimeException.class, () -> {
            database.withSql(() -> {
                database.getConnection().prepareStatement("SELECT * from shoppingcart").executeQuery();
                return null;
            });
        });
    }

    @Test
    @DisplayName("structural-based")
    void testCloseWhenConnectionIsNull() {
        database.close(); // This closes the connection and sets to null
        assertDoesNotThrow(() -> database.close()); // Calling close again should not throw an exception, and should return null.
    }

    @Test
    @DisplayName("structural-based")
    void testResetDatabaseWithNullConnection() {
        database.close(); // Ensures connection is null
        assertDoesNotThrow(() -> database.resetDatabase()); // resetDatabase should not throw an exception, and should skip the conditional check
    }
}
