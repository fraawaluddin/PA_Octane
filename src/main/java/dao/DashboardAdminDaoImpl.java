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
import model.DashboardAdminData;

public class DashboardAdminDaoImpl implements DashboardAdminDao {
    @Override
    public DashboardAdminData getRingkasan() throws SQLException {
        Session.requireRole(Session.ADMINISTRATOR);

        try (Connection c = Koneksi.getConnection()) {
            int lokasi = hitung(c, "SELECT COUNT(*) FROM lokasi_penyimpanan WHERE status_aktif='AKTIF'");
            int spbu = hitung(c, "SELECT COUNT(*) FROM spbu WHERE status_aktif='AKTIF'");
            int jenis = hitung(c, "SELECT COUNT(*) FROM jenis_bbm WHERE status_aktif='AKTIF'");
            BigDecimal stok = nilaiDecimal(c,
                    "SELECT COALESCE(SUM(jumlah_stok),0) FROM stok_penyimpanan");

            List<Object[]> stokLokasi = ambil(c,
                    "SELECT l.nama_lokasi, COALESCE(SUM(s.jumlah_stok),0) AS total "
                  + "FROM lokasi_penyimpanan l LEFT JOIN stok_penyimpanan s "
                  + "ON s.id_lokasi_penyimpanan=l.id_lokasi_penyimpanan "
                  + "LEFT JOIN jenis_bbm j ON j.id_jenis_bbm=s.id_jenis_bbm "
                  + "GROUP BY l.id_lokasi_penyimpanan,l.nama_lokasi "
                  + "ORDER BY l.nama_lokasi",
                    new String[]{"Lokasi Penyimpanan","Total Stok"});

            List<Object[]> status = ambil(c,
                    "SELECT status_distribusi, COUNT(*) AS jumlah "
                  + "FROM distribusi_bbm GROUP BY status_distribusi "
                  + "ORDER BY jumlah DESC",
                    new String[]{"Status Distribusi","Jumlah"});

            List<Object[]> terbaru = ambil(c,
                    "SELECT d.id_distribusi, DATE_FORMAT(d.tanggal_distribusi,'%d-%m-%Y %H:%i') AS tanggal, "
                  + "s.nama_spbu, j.nama_bbm, d.jumlah_dikirim, d.status_distribusi "
                  + "FROM distribusi_bbm d JOIN spbu s ON s.id_spbu=d.id_spbu "
                  + "JOIN jenis_bbm j ON j.id_jenis_bbm=d.id_jenis_bbm "
                  + "ORDER BY d.tanggal_distribusi DESC LIMIT 8",
                    new String[]{"ID","Tanggal","SPBU Tujuan","Jenis BBM","Jumlah (L)","Status"});

            return new DashboardAdminData(lokasi, spbu, jenis, stok,
                    stokLokasi, status, terbaru);
        }
    }

    private int hitung(Connection c, String sql) throws SQLException {
        try (PreparedStatement p=c.prepareStatement(sql); ResultSet r=p.executeQuery()) {
            return r.next() ? r.getInt(1) : 0;
        }
    }
    private BigDecimal nilaiDecimal(Connection c, String sql) throws SQLException {
        try (PreparedStatement p=c.prepareStatement(sql); ResultSet r=p.executeQuery()) {
            return r.next() ? r.getBigDecimal(1) : BigDecimal.ZERO;
        }
    }
    private List<Object[]> ambil(Connection c, String sql, String[] kolom) throws SQLException {
        List<Object[]> hasil = new ArrayList<>();
        try (PreparedStatement p=c.prepareStatement(sql); ResultSet r=p.executeQuery()) {
            while (r.next()) {
                Object[] baris = new Object[kolom.length];
                for (int i=0;i<kolom.length;i++) baris[i]=r.getObject(i+1);
                hasil.add(baris);
            }
        }
        return hasil;
    }
}
