package dao;


import view.Session;
import config.Koneksi;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.StokRingkasModel;

public class StokDaoImpl implements StokDao {
    private static final String STOK_LOKASI = "SELECT s.id_stok_penyimpanan AS id_stok, "
            + "l.id_lokasi_penyimpanan AS id_pemilik, l.nama_lokasi AS nama_pemilik, "
            + "j.id_jenis_bbm, j.nama_bbm, s.jumlah_stok, j.satuan, s.terakhir_diperbarui "
            + "FROM stok_penyimpanan s JOIN lokasi_penyimpanan l "
            + "ON l.id_lokasi_penyimpanan=s.id_lokasi_penyimpanan "
            + "JOIN jenis_bbm j ON j.id_jenis_bbm=s.id_jenis_bbm ";
    private static final String STOK_SPBU = "SELECT s.id_stok_spbu AS id_stok, "
            + "p.id_spbu AS id_pemilik, p.nama_spbu AS nama_pemilik, "
            + "j.id_jenis_bbm, j.nama_bbm, s.jumlah_stok, j.satuan, s.terakhir_diperbarui "
            + "FROM stok_spbu s JOIN spbu p ON p.id_spbu=s.id_spbu "
            + "JOIN jenis_bbm j ON j.id_jenis_bbm=s.id_jenis_bbm ";

    @Override public List<StokRingkasModel> getStokPenyimpanan(String kataKunci) throws SQLException {
        Session.requireRole(Session.ADMINISTRATOR, Session.PETUGAS_PENYIMPANAN);

        if (Session.hasRole(Session.ADMINISTRATOR)) {
            return cari(STOK_LOKASI + "WHERE l.nama_lokasi LIKE ? OR j.nama_bbm LIKE ? ORDER BY l.nama_lokasi,j.nama_bbm",
                    kataKunci, kataKunci);
        }
        return cari(STOK_LOKASI + "WHERE l.id_lokasi_penyimpanan=? AND (l.nama_lokasi LIKE ? OR j.nama_bbm LIKE ?) ORDER BY l.nama_lokasi,j.nama_bbm",
                Session.idLokasiPenyimpanan, kataKunci, kataKunci);
    }
    @Override public List<StokRingkasModel> getStokPenyimpananByLokasi(int idLokasi) throws SQLException {
        Session.requireRole(Session.ADMINISTRATOR, Session.PETUGAS_PENYIMPANAN);
        if (Session.hasRole(Session.PETUGAS_PENYIMPANAN) && idLokasi != Session.idLokasiPenyimpanan)
            throw new SecurityException("Lokasi tidak sesuai sesi.");

        if (idLokasi <= 0) throw new IllegalArgumentException("ID lokasi tidak valid.");
        return cari(STOK_LOKASI + "WHERE l.id_lokasi_penyimpanan=? ORDER BY j.nama_bbm", idLokasi);
    }
    @Override public List<StokRingkasModel> getStokSpbu(String kataKunci) throws SQLException {
        Session.requireRole(Session.ADMINISTRATOR, Session.PETUGAS_SPBU);

        if (Session.hasRole(Session.ADMINISTRATOR)) {
            return cari(STOK_SPBU + "WHERE p.nama_spbu LIKE ? OR j.nama_bbm LIKE ? ORDER BY p.nama_spbu,j.nama_bbm",
                    kataKunci, kataKunci);
        }
        return cari(STOK_SPBU + "WHERE p.id_spbu=? AND (p.nama_spbu LIKE ? OR j.nama_bbm LIKE ?) ORDER BY p.nama_spbu,j.nama_bbm",
                Session.idSpbu, kataKunci, kataKunci);
    }
    @Override public List<StokRingkasModel> getStokSpbuBySpbu(int idSpbu) throws SQLException {
        Session.requireRole(Session.ADMINISTRATOR, Session.PETUGAS_SPBU);
        if (Session.hasRole(Session.PETUGAS_SPBU) && idSpbu != Session.idSpbu)
            throw new SecurityException("SPBU tidak sesuai sesi.");

        if (idSpbu <= 0) throw new IllegalArgumentException("ID SPBU tidak valid.");
        return cari(STOK_SPBU + "WHERE p.id_spbu=? ORDER BY j.nama_bbm", idSpbu);
    }

    @Override public List<StokRingkasModel> getRingkasanStokSpbu(int idSpbu) throws SQLException {
        Session.requireRole(Session.ADMINISTRATOR, Session.PETUGAS_SPBU);
        if (Session.hasRole(Session.PETUGAS_SPBU) && idSpbu != Session.idSpbu)
            throw new SecurityException("SPBU tidak sesuai sesi.");

        if (idSpbu <= 0) throw new IllegalArgumentException("ID SPBU tidak valid.");
        String sql = "SELECT 0 AS id_stok, p.id_spbu AS id_pemilik, p.nama_spbu AS nama_pemilik, "
                + "j.id_jenis_bbm,j.nama_bbm,COALESCE(SUM(s.jumlah_stok),0) AS jumlah_stok, "
                + "j.satuan,MAX(s.terakhir_diperbarui) AS terakhir_diperbarui "
                + "FROM jenis_bbm j CROSS JOIN spbu p LEFT JOIN stok_spbu s "
                + "ON s.id_spbu=p.id_spbu AND s.id_jenis_bbm=j.id_jenis_bbm "
                + "WHERE p.id_spbu=? AND j.status_aktif='AKTIF' "
                + "GROUP BY p.id_spbu,p.nama_spbu,j.id_jenis_bbm,j.nama_bbm,j.satuan "
                + "ORDER BY j.id_jenis_bbm";
        return cari(sql, idSpbu);
    }

    private List<StokRingkasModel> cari(String sql, Object... parameter) throws SQLException {
        List<StokRingkasModel> hasil = new ArrayList<>();
        try (Connection conn = Koneksi.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            for (int i = 0; i < parameter.length; i++) {
                Object nilai = parameter[i];
                if (nilai instanceof Integer id) ps.setInt(i + 1, id);
                else ps.setString(i + 1, "%" + (nilai == null ? "" : nilai.toString().trim()) + "%");
            }
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    hasil.add(new StokRingkasModel(rs.getInt("id_stok"), rs.getInt("id_pemilik"),
                            rs.getString("nama_pemilik"), rs.getInt("id_jenis_bbm"),
                            rs.getString("nama_bbm"), rs.getBigDecimal("jumlah_stok"),
                            rs.getString("satuan"), rs.getTimestamp("terakhir_diperbarui")));
                }
            }
        }
        return hasil;
    }
}
