package edu.fast.stockflow;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@EnabledIfSystemProperty(named = "stockflow.oracle.smoke", matches = "true")
class OracleConnectionTest {
    @Test
    void connectsToOracleAndExecutesQuery() throws Exception {
        String url = requiredEnvironment("STOCKFLOW_DB_URL");
        String user = requiredEnvironment("STOCKFLOW_DB_USER");
        String password = requiredEnvironment("STOCKFLOW_DB_PASSWORD");

        DriverManager.setLoginTimeout(15);
        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement query = connection.prepareStatement("SELECT 1 FROM DUAL")) {
            assertEquals("Oracle", connection.getMetaData().getDatabaseProductName());
            query.setQueryTimeout(15);
            try (ResultSet rows = query.executeQuery()) {
                assertTrue(rows.next());
                assertEquals(1, rows.getInt(1));
                assertFalse(rows.next());
            }
        }
    }

    private static String requiredEnvironment(String name) {
        String value = System.getenv(name);
        assertNotNull(value, name + " is required for the Oracle smoke test");
        assertFalse(value.isBlank(), name + " is required for the Oracle smoke test");
        return value;
    }
}
