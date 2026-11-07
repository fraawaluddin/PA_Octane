package dao;


import view.Session;
import config.Koneksi;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.SpbuModel;

public class SpbuDaoImpl implements SpbuDao {

    @Override
    public List<SpbuModel> getAll() throws SQLException {
        Session.requireRole(Session.ADMINISTRATOR);


        List<SpbuModel> daftarSPBU = new ArrayList<>();

        String sql = "SELECT id_spbu, nama_spbu, alamat, status_aktif "
                   + "FROM spbu "
                   + "ORDER BY id_spbu ASC";

        try (Connection conn = Koneksi.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                SpbuModel spbu = new SpbuModel(
                    rs.getInt("id_spbu"),
                    rs.getString("nama_spbu"),
                    rs.getString("alamat"),
                    rs.getString("status_aktif")
                );

                daftarSPBU.add(spbu);
            }
        }

        return daftarSPBU;
    }

    @Override
    public boolean insert(SpbuModel spbu) throws SQLException {
        Session.requireRole(Session.ADMINISTRATOR);


        String sql = "INSERT INTO spbu "
                   + "(nama_spbu, alamat, status_aktif) "
                   + "VALUES (?, ?, 'AKTIF')";

        try (Connection conn = Koneksi.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, spbu.getNamaSpbu());
            ps.setString(2, spbu.getAlamat());

            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public boolean update(SpbuModel spbu) throws SQLException {
        Session.requireRole(Session.ADMINISTRATOR);


        String sql = "UPDATE spbu "
                   + "SET nama_spbu = ?, alamat = ? "
                   + "WHERE id_spbu = ?";

        try (Connection conn = Koneksi.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, spbu.getNamaSpbu());
            ps.setString(2, spbu.getAlamat());
            ps.setInt(3, spbu.getIdSpbu());

            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public boolean updateStatus(int idSpbu, String statusAktif)
            throws SQLException {
        Session.requireRole(Session.ADMINISTRATOR);


        if (!"AKTIF".equals(statusAktif)
                && !"NONAKTIF".equals(statusAktif)) {
            throw new IllegalArgumentException("Status SPBU tidak valid.");
        }

        String sql = "UPDATE spbu "
                   + "SET status_aktif = ? "
                   + "WHERE id_spbu = ?";

        try (Connection conn = Koneksi.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, statusAktif);
            ps.setInt(2, idSpbu);

            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public int deleteOrDeactivate(int idSpbu) throws SQLException {
        Session.requireRole(Session.ADMINISTRATOR);

        String cek = "SELECT id_spbu FROM spbu WHERE id_spbu = ? FOR UPDATE";
        String referensi = "SELECT 1 FROM petugas_spbu WHERE id_spbu = ? "
                + "UNION ALL SELECT 1 FROM distribusi_bbm WHERE id_spbu = ? "
                + "UNION ALL SELECT 1 FROM stok_spbu WHERE id_spbu = ? LIMIT 1";
        try (Connection conn = Koneksi.getConnection()) {
            conn.setAutoCommit(false);
            try {
                try (PreparedStatement ps = conn.prepareStatement(cek)) {
                    ps.setInt(1, idSpbu);
                    try (ResultSet rs = ps.executeQuery()) {
                        if (!rs.next()) { conn.rollback(); return 0; }
                    }
                }
                boolean dipakai;
                try (PreparedStatement ps = conn.prepareStatement(referensi)) {
                    ps.setInt(1, idSpbu); ps.setInt(2, idSpbu); ps.setInt(3, idSpbu);
                    try (ResultSet rs = ps.executeQuery()) { dipakai = rs.next(); }
                }
                if (dipakai) {
                    try (PreparedStatement ps = conn.prepareStatement(
                            "UPDATE spbu SET status_aktif='NONAKTIF' WHERE id_spbu=?")) {
                        ps.setInt(1, idSpbu); ps.executeUpdate();
                    }
                    conn.commit(); return 2;
                }
                try (PreparedStatement ps = conn.prepareStatement("DELETE FROM spbu WHERE id_spbu=?")) {
                    ps.setInt(1, idSpbu); ps.executeUpdate();
                }
                conn.commit(); return 1;
            } catch (SQLException ex) {
                conn.rollback();
                if ("23000".equals(ex.getSQLState())) {
                    try (PreparedStatement ps = conn.prepareStatement(
                            "UPDATE spbu SET status_aktif='NONAKTIF' WHERE id_spbu=?")) {
                        ps.setInt(1, idSpbu); int n=ps.executeUpdate(); conn.commit(); return n > 0 ? 2 : 0;
                    }
                }
                throw ex;
            }
        }
    }
}
