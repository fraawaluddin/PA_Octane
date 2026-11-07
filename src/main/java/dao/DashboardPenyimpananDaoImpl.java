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
import model.DashboardPenyimpananData;

public class DashboardPenyimpananDaoImpl implements DashboardPenyimpananDao {
    @Override
    public DashboardPenyimpananData getRingkasan(int idLokasi) throws SQLException {
        Session.requireRole(Session.PETUGAS_PENYIMPANAN);
        if (idLokasi != Session.idLokasiPenyimpanan) throw new SecurityException("Lokasi tidak sesuai sesi.");

        try (Connection c = Koneksi.getConnection()) {
            BigDecimal totalStok = nilai(c,
                    "SELECT COALESCE(SUM(jumlah_stok),0) FROM stok_penyimpanan WHERE id_lokasi_penyimpanan=?",
                    idLokasi);
            BigDecimal totalPenerimaan = nilai(c,
                    "SELECT COALESCE(SUM(jumlah_diterima),0) FROM penerimaan_bbm WHERE id_lokasi_penyimpanan=?",
                    idLokasi);
            BigDecimal totalDistribusi = nilai(c,
                    "SELECT COALESCE(SUM(jumlah_dikirim),0) FROM distribusi_bbm WHERE id_lokasi_penyimpanan=?",
                    idLokasi);

            List<Object[]> stok = ambil(c,
                    "SELECT j.nama_bbm, COALESCE(s.jumlah_stok,0) "
                  + "FROM jenis_bbm j LEFT JOIN stok_penyimpanan s "
                  + "ON s.id_jenis_bbm=j.id_jenis_bbm AND s.id_lokasi_penyimpanan=? "
                  + "WHERE j.status_aktif='AKTIF' ORDER BY j.nama_bbm",
                    idLokasi, 2);

            List<Object[]> status = ambil(c,
                    "SELECT status_distribusi, COUNT(*) FROM distribusi_bbm "
                  + "WHERE id_lokasi_penyimpanan=? GROUP BY status_distribusi "
                  + "ORDER BY COUNT(*) DESC",
                    idLokasi, 2);

            List<Object[]> terbaru = ambil(c,
                    "SELECT d.id_distribusi, DATE_FORMAT(d.tanggal_distribusi,'%d-%m-%Y %H:%i'), "
                  + "s.nama_spbu, j.nama_bbm, d.jumlah_dikirim, d.status_distribusi "
                  + "FROM distribusi_bbm d JOIN spbu s ON s.id_spbu=d.id_spbu "
                  + "JOIN jenis_bbm j ON j.id_jenis_bbm=d.id_jenis_bbm "
                  + "WHERE d.id_lokasi_penyimpanan=? "
                  + "ORDER BY d.tanggal_distribusi DESC LIMIT 8",
                    idLokasi, 6);

            return new DashboardPenyimpananData(totalStok, totalPenerimaan,
                    totalDistribusi, stok, status, terbaru);
        }
    }

    private BigDecimal nilai(Connection c, String sql, int idLokasi) throws SQLException {
        try (PreparedStatement p=c.prepareStatement(sql)) {
            p.setInt(1,idLokasi);
            try (ResultSet r=p.executeQuery()) {
                return r.next() ? r.getBigDecimal(1) : BigDecimal.ZERO;
            }
        }
    }

    private List<Object[]> ambil(Connection c, String sql, int idLokasi, int jumlahKolom)
            throws SQLException {
        List<Object[]> hasil = new ArrayList<>();
        try (PreparedStatement p=c.prepareStatement(sql)) {
            p.setInt(1,idLokasi);
            try (ResultSet r=p.executeQuery()) {
                while (r.next()) {
                    Object[] baris = new Object[jumlahKolom];
                    for (int i=0;i<jumlahKolom;i++) baris[i]=r.getObject(i+1);
                    hasil.add(baris);
                }
            }
        }
        return hasil;
    }
}
