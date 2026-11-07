package dao;


import view.Session;
import config.Koneksi;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.PengirimanMenungguModel;
import model.KonfirmasiRiwayatModel;

public class KonfirmasiPenerimaanDaoImpl implements KonfirmasiPenerimaanDao {


    @Override
    public List<KonfirmasiRiwayatModel> getDaftarKonfirmasi() throws SQLException {
        Session.requireRole(Session.PETUGAS_SPBU);

        String sql = "SELECT k.id_konfirmasi, d.id_distribusi, s.nama_spbu, "
                + "p.nama_lengkap, k.tanggal_konfirmasi, k.jumlah_aktual, k.keterangan "
                + "FROM konfirmasi_penerimaan k "
                + "JOIN distribusi_bbm d ON k.id_distribusi=d.id_distribusi "
                + "JOIN petugas_spbu ps ON k.id_petugas_spbu=ps.id_pengguna "
                + "JOIN pengguna p ON ps.id_pengguna=p.id_pengguna "
                + "JOIN spbu s ON ps.id_spbu=s.id_spbu "
                + "WHERE d.id_spbu=? ORDER BY k.id_konfirmasi ASC";
        List<KonfirmasiRiwayatModel> hasil = new ArrayList<>();
        try (Connection conn = Koneksi.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, Session.idSpbu);
            try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                hasil.add(new KonfirmasiRiwayatModel(rs.getInt("id_konfirmasi"),
                    rs.getInt("id_distribusi"), rs.getString("nama_spbu"),
                    rs.getString("nama_lengkap"), rs.getTimestamp("tanggal_konfirmasi"),
                    rs.getBigDecimal("jumlah_aktual"), rs.getString("keterangan")));
            }
            }
        }
        return hasil;
    }

    @Override
    public List<PengirimanMenungguModel> getPengirimanMenunggu(int idSpbu) throws SQLException {
        Session.requireRole(Session.PETUGAS_SPBU);
        if (idSpbu != Session.idSpbu) throw new SecurityException("SPBU tidak sesuai sesi.");

        String sql = "SELECT d.id_distribusi,d.tanggal_distribusi,lp.nama_lokasi AS asal_penyimpanan,"
                + "j.nama_bbm,d.jumlah_dikirim,d.status_distribusi "
                + "FROM distribusi_bbm d JOIN lokasi_penyimpanan lp "
                + "ON lp.id_lokasi_penyimpanan=d.id_lokasi_penyimpanan "
                + "JOIN jenis_bbm j ON j.id_jenis_bbm=d.id_jenis_bbm "
                + "WHERE d.id_spbu=? AND d.status_distribusi='MENUNGGU_KONFIRMASI' "
                + "AND NOT EXISTS (SELECT 1 FROM konfirmasi_penerimaan kp "
                + "WHERE kp.id_distribusi=d.id_distribusi) ORDER BY d.tanggal_distribusi ASC";
        List<PengirimanMenungguModel> hasil = new ArrayList<>();
        try (Connection conn = Koneksi.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idSpbu);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    hasil.add(new PengirimanMenungguModel(rs.getInt("id_distribusi"),
                            rs.getTimestamp("tanggal_distribusi"), rs.getString("asal_penyimpanan"),
                            rs.getString("nama_bbm"), rs.getBigDecimal("jumlah_dikirim"),
                            rs.getString("status_distribusi")));
                }
            }
        }
        return hasil;
    }

    @Override
    public PengirimanMenungguModel getDetailPengiriman(int idDistribusi, int idSpbu) throws SQLException {
        Session.requireRole(Session.PETUGAS_SPBU);
        if (idSpbu != Session.idSpbu) throw new SecurityException("SPBU tidak sesuai sesi.");

        String sql = "SELECT d.id_distribusi,d.tanggal_distribusi,lp.nama_lokasi AS asal_penyimpanan,"
                + "j.nama_bbm,d.jumlah_dikirim,d.status_distribusi "
                + "FROM distribusi_bbm d JOIN lokasi_penyimpanan lp "
                + "ON lp.id_lokasi_penyimpanan=d.id_lokasi_penyimpanan "
                + "JOIN jenis_bbm j ON j.id_jenis_bbm=d.id_jenis_bbm "
                + "WHERE d.id_distribusi=? AND d.id_spbu=? "
                + "AND d.status_distribusi='MENUNGGU_KONFIRMASI' "
                + "AND NOT EXISTS (SELECT 1 FROM konfirmasi_penerimaan kp "
                + "WHERE kp.id_distribusi=d.id_distribusi)";
        try (Connection conn = Koneksi.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idDistribusi);
            ps.setInt(2, idSpbu);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new PengirimanMenungguModel(rs.getInt("id_distribusi"),
                            rs.getTimestamp("tanggal_distribusi"), rs.getString("asal_penyimpanan"),
                            rs.getString("nama_bbm"), rs.getBigDecimal("jumlah_dikirim"),
                            rs.getString("status_distribusi"));
                }
            }
        }
        return null;
    }

    @Override
    public String simpanKonfirmasi(int idDistribusi, int idSpbu, int idPetugasSpbu,
            BigDecimal jumlahAktual, String keterangan) throws SQLException {
        Session.requireRole(Session.PETUGAS_SPBU);
        if (idSpbu != Session.idSpbu) throw new SecurityException("SPBU tidak sesuai sesi.");
        if (idPetugasSpbu != Session.idPengguna) throw new SecurityException("Petugas tidak sesuai sesi.");

        if (idDistribusi <= 0 || idSpbu <= 0 || idPetugasSpbu <= 0) {
            throw new SQLException("Identitas distribusi, SPBU, dan petugas tidak valid.");
        }
        if (jumlahAktual == null || jumlahAktual.compareTo(BigDecimal.ZERO) <= 0) {
            throw new SQLException("Jumlah aktual harus lebih besar dari 0.");
        }
        if (keterangan == null || keterangan.trim().isEmpty()) {
            throw new SQLException("Keterangan konfirmasi wajib diisi.");
        }

        final String sqlDistribusi = "SELECT jumlah_dikirim, status_distribusi, id_jenis_bbm "
                + "FROM distribusi_bbm WHERE id_distribusi=? AND id_spbu=? FOR UPDATE";
        final String sqlPetugas = "SELECT 1 FROM petugas_spbu ps "
                + "JOIN pengguna p ON p.id_pengguna=ps.id_pengguna "
                + "WHERE ps.id_pengguna=? AND ps.id_spbu=? AND p.status_aktif='AKTIF' "
                + "AND ps.status_pelatihan_penerimaan='LULUS'";
        final String sqlInsert = "INSERT INTO konfirmasi_penerimaan "
                + "(id_distribusi,id_petugas_spbu,tanggal_konfirmasi,jumlah_aktual,keterangan) "
                + "VALUES (?,?,NOW(),?,?)";
        final String sqlUpdate = "UPDATE distribusi_bbm SET status_distribusi=? "
                + "WHERE id_distribusi=? AND id_spbu=? "
                + "AND status_distribusi='MENUNGGU_KONFIRMASI'";
        final String sqlStok = "INSERT INTO stok_spbu "
                + "(id_spbu,id_jenis_bbm,jumlah_stok) VALUES (?,?,?) "
                + "ON DUPLICATE KEY UPDATE jumlah_stok=jumlah_stok+VALUES(jumlah_stok)";

        try (Connection conn = Koneksi.getConnection()) {
            conn.setAutoCommit(false);
            try {
                BigDecimal jumlahDikirim;
                int idJenis;
                try (PreparedStatement ps = conn.prepareStatement(sqlDistribusi)) {
                    ps.setInt(1, idDistribusi);
                    ps.setInt(2, idSpbu);
                    try (ResultSet rs = ps.executeQuery()) {
                        if (!rs.next()) {
                            throw new SQLException("Pengiriman tidak ditemukan untuk SPBU ini.");
                        }
                        jumlahDikirim = rs.getBigDecimal("jumlah_dikirim");
                        idJenis = rs.getInt("id_jenis_bbm");
                        if (!"MENUNGGU_KONFIRMASI".equals(rs.getString("status_distribusi"))) {
                            throw new SQLException("Pengiriman tidak lagi menunggu konfirmasi.");
                        }
                    }
                }

                try (PreparedStatement ps = conn.prepareStatement(
                        "SELECT 1 FROM konfirmasi_penerimaan WHERE id_distribusi=? FOR UPDATE")) {
                    ps.setInt(1, idDistribusi);
                    try (ResultSet rs = ps.executeQuery()) {
                        if (rs.next()) {
                            throw new SQLException("Distribusi ini sudah pernah dikonfirmasi.");
                        }
                    }
                }

                try (PreparedStatement ps = conn.prepareStatement(sqlPetugas)) {
                    ps.setInt(1, idPetugasSpbu);
                    ps.setInt(2, idSpbu);
                    try (ResultSet rs = ps.executeQuery()) {
                        if (!rs.next()) {
                            throw new SQLException("Petugas tidak aktif, tidak terdaftar pada SPBU ini, "
                                    + "atau belum lulus pelatihan penerimaan.");
                        }
                    }
                }

                String statusBaru = jumlahAktual.compareTo(jumlahDikirim) == 0
                        ? "SELESAI" : "PERLU_PEMERIKSAAN";

                try (PreparedStatement ps = conn.prepareStatement(sqlInsert)) {
                    ps.setInt(1, idDistribusi);
                    ps.setInt(2, idPetugasSpbu);
                    ps.setBigDecimal(3, jumlahAktual);
                    ps.setString(4, keterangan.trim());
                    ps.executeUpdate();
                }

                try (PreparedStatement ps = conn.prepareStatement(sqlUpdate)) {
                    ps.setString(1, statusBaru);
                    ps.setInt(2, idDistribusi);
                    ps.setInt(3, idSpbu);
                    if (ps.executeUpdate() != 1) {
                        throw new SQLException("Status distribusi gagal diperbarui.");
                    }
                }
              
                if ("SELESAI".equals(statusBaru)) {
                    try (PreparedStatement ps = conn.prepareStatement(sqlStok)) {
                        ps.setInt(1, idSpbu);
                        ps.setInt(2, idJenis);
                        ps.setBigDecimal(3, jumlahAktual);
                        ps.executeUpdate();
                    }
                }

                conn.commit();
                return statusBaru;
            } catch (SQLException | RuntimeException ex) {
                try {
                    conn.rollback();
                } catch (SQLException rollbackEx) {
                    ex.addSuppressed(rollbackEx);
                }
                if (ex instanceof SQLException sqlEx) {
                    throw sqlEx;
                }
                throw new SQLException("Konfirmasi gagal diproses.", ex);
            } finally {
                try {
                    conn.setAutoCommit(true);
                } catch (SQLException ignored) {
                    
                }
            }
        }
    }
}
