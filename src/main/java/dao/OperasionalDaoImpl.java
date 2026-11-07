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

public class OperasionalDaoImpl implements OperasionalDao {

    @Override
    public List<String> getJenisBbmAktif() throws SQLException {
        Session.requireRole(Session.PETUGAS_PENYIMPANAN);
        List<String> hasil = new ArrayList<>();
        String sql = "SELECT nama_bbm FROM jenis_bbm WHERE status_aktif='AKTIF' ORDER BY nama_bbm";
        try (Connection c = Koneksi.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                hasil.add(rs.getString("nama_bbm"));
            }
        }
        return hasil;
    }


    @Override
    public void catatPenerimaan(int idPengguna, int idLokasi, String namaBbm,
            BigDecimal jumlah) throws SQLException {
        Session.requireRole(Session.PETUGAS_PENYIMPANAN);
        if (idLokasi != Session.idLokasiPenyimpanan || idPengguna != Session.idPengguna) throw new SecurityException("Identitas atau lokasi tidak sesuai sesi.");

        String sqlJenis = "SELECT id_jenis_bbm FROM jenis_bbm "
                + "WHERE LOWER(nama_bbm)=LOWER(?) AND status_aktif='AKTIF'";
        String sqlLokasi = "SELECT status_aktif FROM lokasi_penyimpanan "
                + "WHERE id_lokasi_penyimpanan=?";
        String sqlPetugas = "SELECT 1 FROM petugas_penyimpanan pp "
                + "JOIN pengguna p ON p.id_pengguna=pp.id_pengguna "
                + "WHERE pp.id_pengguna=? AND pp.id_lokasi_penyimpanan=? "
                + "AND p.status_aktif='AKTIF'";
        String sqlInsert = "INSERT INTO penerimaan_bbm "
                + "(id_jenis_bbm,id_lokasi_penyimpanan,id_petugas_penyimpanan,"
                + "tanggal_penerimaan,jumlah_diterima) VALUES (?,?,?,NOW(),?)";
        String sqlUpsert = "INSERT INTO stok_penyimpanan "
                + "(id_lokasi_penyimpanan,id_jenis_bbm,jumlah_stok) VALUES (?,?,?) "
                + "ON DUPLICATE KEY UPDATE jumlah_stok=jumlah_stok+VALUES(jumlah_stok)";

        try (Connection c = Koneksi.getConnection()) {
            c.setAutoCommit(false);
            try {
                validasiLokasi(c, sqlLokasi, idLokasi);
                validasiPetugas(c, sqlPetugas, idPengguna, idLokasi);
                int idJenis = cariId(c, sqlJenis, namaBbm, "Jenis BBM aktif tidak ditemukan.");
                try (PreparedStatement ps = c.prepareStatement(sqlInsert)) {
                    ps.setInt(1, idJenis); ps.setInt(2, idLokasi);
                    ps.setInt(3, idPengguna); ps.setBigDecimal(4, jumlah);
                    ps.executeUpdate();
                }
                try (PreparedStatement ps = c.prepareStatement(sqlUpsert)) {
                    ps.setInt(1, idLokasi); ps.setInt(2, idJenis); ps.setBigDecimal(3, jumlah);
                    ps.executeUpdate();
                }
                c.commit();
            } catch (Exception ex) {
                rollback(c, ex);
                if (ex instanceof SQLException se) throw se;
                throw new SQLException(ex.getMessage(), ex);
            } finally {
                try { c.setAutoCommit(true); } catch (SQLException ignored) { }
            }
        }
    }

    @Override
    public void catatDistribusi(int idPengguna, int idLokasi, String namaSpbu,
            String namaBbm, BigDecimal jumlah) throws SQLException {
        Session.requireRole(Session.PETUGAS_PENYIMPANAN);
        if (idLokasi != Session.idLokasiPenyimpanan || idPengguna != Session.idPengguna) throw new SecurityException("Identitas atau lokasi tidak sesuai sesi.");

        String sqlJenis = "SELECT id_jenis_bbm FROM jenis_bbm "
                + "WHERE LOWER(nama_bbm)=LOWER(?) AND status_aktif='AKTIF'";
        String sqlSpbu = "SELECT id_spbu FROM spbu "
                + "WHERE LOWER(nama_spbu)=LOWER(?) AND status_aktif='AKTIF'";
        String sqlLokasi = "SELECT status_aktif FROM lokasi_penyimpanan "
                + "WHERE id_lokasi_penyimpanan=?";
        String sqlPetugas = "SELECT 1 FROM petugas_penyimpanan pp "
                + "JOIN pengguna p ON p.id_pengguna=pp.id_pengguna "
                + "WHERE pp.id_pengguna=? AND pp.id_lokasi_penyimpanan=? "
                + "AND p.status_aktif='AKTIF'";
        String sqlKurangi = "UPDATE stok_penyimpanan SET jumlah_stok=jumlah_stok-? "
                + "WHERE id_lokasi_penyimpanan=? AND id_jenis_bbm=? AND jumlah_stok>=?";
        String sqlDistribusi = "INSERT INTO distribusi_bbm "
                + "(id_jenis_bbm,id_lokasi_penyimpanan,id_spbu,id_petugas_penyimpanan,"
                + "tanggal_distribusi,jumlah_dikirim,status_distribusi) "
                + "VALUES (?,?,?,?,NOW(),?,'MENUNGGU_KONFIRMASI')";

        try (Connection c = Koneksi.getConnection()) {
            c.setAutoCommit(false);
            try {
                validasiLokasi(c, sqlLokasi, idLokasi);
                validasiPetugas(c, sqlPetugas, idPengguna, idLokasi);
                int idJenis = cariId(c, sqlJenis, namaBbm, "Jenis BBM aktif tidak ditemukan.");
                int idSpbu = cariId(c, sqlSpbu, namaSpbu, "SPBU aktif tidak ditemukan.");
                try (PreparedStatement ps = c.prepareStatement(sqlKurangi)) {
                    ps.setBigDecimal(1, jumlah); ps.setInt(2, idLokasi);
                    ps.setInt(3, idJenis); ps.setBigDecimal(4, jumlah);
                    if (ps.executeUpdate() != 1) {
                        throw new SQLException("Stok penyimpanan tidak mencukupi atau data stok belum tersedia.");
                    }
                }
                try (PreparedStatement ps = c.prepareStatement(sqlDistribusi)) {
                    ps.setInt(1, idJenis); ps.setInt(2, idLokasi);
                    ps.setInt(3, idSpbu); ps.setInt(4, idPengguna);
                    ps.setBigDecimal(5, jumlah); ps.executeUpdate();
                }
                c.commit();
            } catch (Exception ex) {
                rollback(c, ex);
                if (ex instanceof SQLException se) throw se;
                throw new SQLException(ex.getMessage(), ex);
            } finally {
                try { c.setAutoCommit(true); } catch (SQLException ignored) { }
            }
        }
    }


    @Override
    public void mulaiPemeriksaanDistribusi(int idDistribusi, int idPetugasPenyimpanan) throws SQLException {
        Session.requireRole(Session.PETUGAS_PENYIMPANAN);
        if (idPetugasPenyimpanan != Session.idPengguna) throw new SecurityException("Petugas tidak sesuai sesi.");

        ubahStatusDistribusi(idDistribusi, idPetugasPenyimpanan,
                "PERLU_PEMERIKSAAN", "DIPROSES", null);
    }

    @Override
    public void selesaikanPemeriksaanDistribusi(int idDistribusi, int idPetugasPenyimpanan,
            String catatanPemeriksaan) throws SQLException {
        Session.requireRole(Session.PETUGAS_PENYIMPANAN);
        if (idPetugasPenyimpanan != Session.idPengguna) throw new SecurityException("Petugas tidak sesuai sesi.");

        if (catatanPemeriksaan == null || catatanPemeriksaan.trim().length() < 5
                || catatanPemeriksaan.trim().length() > 500) {
            throw new SQLException("Catatan hasil pemeriksaan wajib diisi (5-500 karakter).");
        }
        ubahStatusDistribusi(idDistribusi, idPetugasPenyimpanan,
                "DIPROSES", "SELESAI_DITANGANI", catatanPemeriksaan.trim());
    }

    private void ubahStatusDistribusi(int idDistribusi, int idPetugas,
            String statusAsal, String statusTujuan, String catatanPemeriksaan) throws SQLException {
        boolean tambahkanStokSpbu = "SELESAI_DITANGANI".equals(statusTujuan);
        if (idDistribusi <= 0 || idPetugas <= 0)
            throw new SQLException("ID distribusi dan ID petugas harus valid.");
        try (Connection c = Koneksi.getConnection()) {
            c.setAutoCommit(false);
            try {
                int idLokasi; int idSpbu; int idJenis; BigDecimal jumlahAktual = null;
                String sqlDistribusi = "SELECT d.id_lokasi_penyimpanan,d.id_spbu,d.id_jenis_bbm "
                        + (tambahkanStokSpbu ? ",k.jumlah_aktual " : ",NULL AS jumlah_aktual ")
                        + "FROM distribusi_bbm d "
                        + (tambahkanStokSpbu ? "JOIN konfirmasi_penerimaan k ON k.id_distribusi=d.id_distribusi " : "")
                        + "WHERE d.id_distribusi=? AND d.status_distribusi=? FOR UPDATE";
                try (PreparedStatement ps = c.prepareStatement(sqlDistribusi)) {
                    ps.setInt(1, idDistribusi); ps.setString(2, statusAsal);
                    try (ResultSet rs = ps.executeQuery()) {
                        if (!rs.next()) throw new SQLException("Distribusi tidak ditemukan atau statusnya bukan " + statusAsal + ".");
                        idLokasi=rs.getInt("id_lokasi_penyimpanan"); idSpbu=rs.getInt("id_spbu");
                        idJenis=rs.getInt("id_jenis_bbm"); jumlahAktual=rs.getBigDecimal("jumlah_aktual");
                    }
                }
                if (tambahkanStokSpbu && (jumlahAktual == null
                        || jumlahAktual.compareTo(BigDecimal.ZERO) <= 0)) {
                    throw new SQLException("Jumlah aktual belum tersedia atau tidak valid. Pemeriksaan tidak dapat diselesaikan.");
                }
                String petugasSql = "SELECT 1 FROM petugas_penyimpanan pp JOIN pengguna p "
                        + "ON p.id_pengguna=pp.id_pengguna WHERE pp.id_pengguna=? "
                        + "AND pp.id_lokasi_penyimpanan=? AND p.status_aktif='AKTIF'";
                try (PreparedStatement ps=c.prepareStatement(petugasSql)) {
                    ps.setInt(1,idPetugas); ps.setInt(2,idLokasi);
                    try(ResultSet rs=ps.executeQuery()) { if(!rs.next()) throw new SQLException("Petugas aktif tidak terdaftar pada lokasi distribusi."); }
                }
                try (PreparedStatement ps=c.prepareStatement("UPDATE distribusi_bbm SET status_distribusi=? WHERE id_distribusi=? AND status_distribusi=?")) {
                    ps.setString(1,statusTujuan); ps.setInt(2,idDistribusi); ps.setString(3,statusAsal);
                    if(ps.executeUpdate()!=1) throw new SQLException("Perubahan status distribusi gagal.");
                }
                if (tambahkanStokSpbu) {
                    
                    String stok = "INSERT INTO stok_spbu (id_spbu,id_jenis_bbm,jumlah_stok) VALUES (?,?,?) "
                            + "ON DUPLICATE KEY UPDATE jumlah_stok=jumlah_stok+VALUES(jumlah_stok)";
                    try (PreparedStatement ps = c.prepareStatement(stok)) {
                        ps.setInt(1, idSpbu);
                        ps.setInt(2, idJenis);
                        ps.setBigDecimal(3, jumlahAktual);
                        ps.executeUpdate();
                    }
                }
                c.commit();
            } catch(Exception ex) {
                try { c.rollback(); } catch(SQLException re) { ex.addSuppressed(re); }
                if(ex instanceof SQLException se) throw se;
                throw new SQLException("Tindak lanjut distribusi gagal.",ex);
            } finally { try { c.setAutoCommit(true); } catch(SQLException ignored) {} }
        }
    }

    private int cariId(Connection c, String sql, String nilai, String pesan) throws SQLException {
        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, nilai);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) throw new SQLException(pesan);
                return rs.getInt(1);
            }
        }
    }

    private void validasiLokasi(Connection c, String sql, int idLokasi) throws SQLException {
        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, idLokasi);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next() || !"AKTIF".equalsIgnoreCase(rs.getString(1)))
                    throw new SQLException("Lokasi penyimpanan tidak aktif atau tidak ditemukan.");
            }
        }
    }

    private void validasiPetugas(Connection c, String sql, int idPengguna, int idLokasi) throws SQLException {
        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, idPengguna); ps.setInt(2, idLokasi);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) throw new SQLException("Petugas tidak terdaftar pada lokasi penyimpanan ini.");
            }
        }
    }

    private void rollback(Connection c, Exception original) {
        try { c.rollback(); } catch (SQLException rollbackError) { original.addSuppressed(rollbackError); }
    }
}
