package config;

import java.sql.Connection;
import java.sql.SQLException;

public class TestKoneksi {

    public static void main(String[] args) {
        try (Connection conn = Koneksi.getConnection()) {

            if (conn != null && !conn.isClosed()) {
                System.out.println("Koneksi database berhasil!");
            }

        } catch (SQLException e) {
            System.out.println("Koneksi database gagal!");
            System.out.println("Pesan error: " + e.getMessage());
        }
    }
}
