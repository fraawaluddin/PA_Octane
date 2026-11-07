package dao;

import config.Koneksi;
import model.PenggunaLogin;
import model.AdministratorLogin;
import model.PetugasPenyimpananLogin;
import model.PetugasSpbuLogin;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AutentikasiDaoImpl implements AutentikasiDao {
    private static final String SQL =
        "SELECT p.id_pengguna, p.nama_lengkap, p.username, p.password, p.status_aktif, " +
        "pp.id_lokasi_penyimpanan, ps.id_spbu, " +
        "CASE WHEN a.id_pengguna IS NOT NULL THEN 'ADMINISTRATOR' " +
        "WHEN pp.id_pengguna IS NOT NULL THEN 'PETUGAS PENYIMPANAN' " +
        "WHEN ps.id_pengguna IS NOT NULL THEN 'PETUGAS SPBU' ELSE NULL END AS peran, " +
        "COALESCE(lp.nama_lokasi, s.nama_spbu, '-') AS lokasi " +
        "FROM pengguna p " +
        "LEFT JOIN administrator a ON a.id_pengguna = p.id_pengguna " +
        "LEFT JOIN petugas_penyimpanan pp ON pp.id_pengguna = p.id_pengguna " +
        "LEFT JOIN lokasi_penyimpanan lp ON lp.id_lokasi_penyimpanan = pp.id_lokasi_penyimpanan " +
        "LEFT JOIN petugas_spbu ps ON ps.id_pengguna = p.id_pengguna " +
        "LEFT JOIN spbu s ON s.id_spbu = ps.id_spbu " +
        "WHERE p.username = ? LIMIT 1";

    @Override
    public PenggunaLogin autentikasi(String username, String password) throws Exception {
        try (Connection conn = Koneksi.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return null;
                if (!"AKTIF".equalsIgnoreCase(rs.getString("status_aktif"))) return null;
                if (!password.equals(rs.getString("password"))) return null;
                String peran = rs.getString("peran");
                if (peran == null) return null;
                int id = rs.getInt("id_pengguna");
                String nama = rs.getString("nama_lengkap");
                String user = rs.getString("username");
                String lokasi = rs.getString("lokasi");
                return switch (peran) {
                    case "ADMINISTRATOR" -> new AdministratorLogin(id, nama, user, lokasi);
                    case "PETUGAS PENYIMPANAN" -> new PetugasPenyimpananLogin(
                            id, nama, user, lokasi, rs.getInt("id_lokasi_penyimpanan"));
                    case "PETUGAS SPBU" -> new PetugasSpbuLogin(
                            id, nama, user, lokasi, rs.getInt("id_spbu"));
                    default -> null;
                };
            }
        } catch (SQLException ex) {
            throw new Exception("Gagal mengakses database: " + ex.getMessage(), ex);
        }
    }
}
