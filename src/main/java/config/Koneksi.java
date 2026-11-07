package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class Koneksi {
    private static final String DEFAULT_URL =
        "jdbc:mysql://localhost:3306/octane_db?useSSL=false&serverTimezone=Asia/Jakarta&allowPublicKeyRetrieval=true";
    private static final String URL = env("OCTANE_DB_URL", DEFAULT_URL);
    private static final String USER = env("OCTANE_DB_USER", "root");
    private static final String PASS = env("OCTANE_DB_PASSWORD", "");

    private Koneksi() {}
    private static String env(String key, String fallback) {
        String value = System.getenv(key);
        return value == null || value.isBlank() ? fallback : value;
    }
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }
    public static Connection getKoneksi() throws SQLException {
        return getConnection();
    }
}
