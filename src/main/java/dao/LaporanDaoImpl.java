package dao;


import view.Session;
import config.Koneksi;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.*;

public class LaporanDaoImpl implements LaporanDao {
    @Override public List<RiwayatPenerimaanModel> getRiwayatPenerimaan(int idLokasi) throws SQLException {
        Session.requireRole(Session.PETUGAS_PENYIMPANAN);
        if (idLokasi != Session.idLokasiPenyimpanan) throw new SecurityException("Lokasi tidak sesuai sesi.");

        String sql = "SELECT p.id_penerimaan,p.tanggal_penerimaan,j.nama_bbm,p.jumlah_diterima,j.satuan,u.nama_lengkap FROM penerimaan_bbm p JOIN jenis_bbm j ON p.id_jenis_bbm=j.id_jenis_bbm JOIN pengguna u ON p.id_petugas_penyimpanan=u.id_pengguna WHERE p.id_lokasi_penyimpanan=? ORDER BY p.tanggal_penerimaan DESC,p.id_penerimaan DESC";
        List<RiwayatPenerimaanModel> hasil=new ArrayList<>();
        try(Connection c=Koneksi.getConnection(); PreparedStatement ps=c.prepareStatement(sql)){ps.setInt(1,idLokasi);try(ResultSet rs=ps.executeQuery()){while(rs.next()) hasil.add(new RiwayatPenerimaanModel(rs.getInt("id_penerimaan"),rs.getTimestamp("tanggal_penerimaan"),rs.getString("nama_bbm"),rs.getBigDecimal("jumlah_diterima"),rs.getString("satuan"),rs.getString("nama_lengkap")));}}
        return hasil;
    }
    @Override public List<RiwayatDistribusiModel> getRiwayatDistribusi(int idLokasi) throws SQLException {
        Session.requireRole(Session.PETUGAS_PENYIMPANAN);
        if (idLokasi != Session.idLokasiPenyimpanan) throw new SecurityException("Lokasi tidak sesuai sesi.");

        String sql=sqlRiwayat()+" WHERE d.id_lokasi_penyimpanan=? ORDER BY d.tanggal_distribusi DESC,d.id_distribusi DESC";
        return queryRiwayat(sql,idLokasi);
    }
    @Override public List<RiwayatDistribusiModel> getRiwayatDistribusiSpbu(int idSpbu) throws SQLException {
        Session.requireRole(Session.PETUGAS_SPBU);
        if (idSpbu != Session.idSpbu) throw new SecurityException("SPBU tidak sesuai sesi.");

        String sql=sqlRiwayat()+" WHERE d.id_spbu=? ORDER BY d.tanggal_distribusi DESC,d.id_distribusi DESC";
        return queryRiwayat(sql,idSpbu);
    }
    private String sqlRiwayat(){return "SELECT d.id_distribusi,d.tanggal_distribusi,l.nama_lokasi,s.nama_spbu,j.nama_bbm,j.satuan,d.jumlah_dikirim,d.status_distribusi,k.jumlah_aktual,k.keterangan FROM distribusi_bbm d JOIN jenis_bbm j ON d.id_jenis_bbm=j.id_jenis_bbm JOIN lokasi_penyimpanan l ON d.id_lokasi_penyimpanan=l.id_lokasi_penyimpanan JOIN spbu s ON d.id_spbu=s.id_spbu LEFT JOIN konfirmasi_penerimaan k ON d.id_distribusi=k.id_distribusi";}
    private List<RiwayatDistribusiModel> queryRiwayat(String sql,int id) throws SQLException {
        List<RiwayatDistribusiModel> hasil=new ArrayList<>();
        try(Connection c=Koneksi.getConnection();PreparedStatement ps=c.prepareStatement(sql)){ps.setInt(1,id);try(ResultSet rs=ps.executeQuery()){while(rs.next())hasil.add(new RiwayatDistribusiModel(rs.getInt("id_distribusi"),rs.getTimestamp("tanggal_distribusi"),rs.getString("nama_lokasi"),rs.getString("nama_spbu"),rs.getString("nama_bbm"),rs.getString("satuan"),rs.getBigDecimal("jumlah_dikirim"),rs.getString("status_distribusi"),rs.getBigDecimal("jumlah_aktual"),rs.getString("keterangan")));}}
        return hasil;
    }
    @Override public List<DaftarPenerimaanModel> getDaftarPenerimaan() throws SQLException {
        Session.requireRole(Session.ADMINISTRATOR);

        String sql="SELECT pb.id_penerimaan,j.nama_bbm,l.nama_lokasi,u.nama_lengkap,pb.tanggal_penerimaan,pb.jumlah_diterima FROM penerimaan_bbm pb JOIN jenis_bbm j ON pb.id_jenis_bbm=j.id_jenis_bbm JOIN lokasi_penyimpanan l ON pb.id_lokasi_penyimpanan=l.id_lokasi_penyimpanan JOIN pengguna u ON pb.id_petugas_penyimpanan=u.id_pengguna ORDER BY pb.id_penerimaan ASC";
        List<DaftarPenerimaanModel> hasil=new ArrayList<>();
        try(Connection c=Koneksi.getConnection();PreparedStatement ps=c.prepareStatement(sql);ResultSet rs=ps.executeQuery()){while(rs.next())hasil.add(new DaftarPenerimaanModel(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getTimestamp(5),rs.getBigDecimal(6)));}
        return hasil;
    }
    @Override public List<DaftarDistribusiModel> getDaftarDistribusi() throws SQLException {
        Session.requireRole(Session.ADMINISTRATOR);

        String sql="SELECT d.id_distribusi,j.nama_bbm,l.nama_lokasi,s.nama_spbu,u.nama_lengkap,d.tanggal_distribusi,d.jumlah_dikirim,d.status_distribusi FROM distribusi_bbm d JOIN jenis_bbm j ON d.id_jenis_bbm=j.id_jenis_bbm JOIN lokasi_penyimpanan l ON d.id_lokasi_penyimpanan=l.id_lokasi_penyimpanan JOIN spbu s ON d.id_spbu=s.id_spbu JOIN pengguna u ON d.id_petugas_penyimpanan=u.id_pengguna ORDER BY d.id_distribusi ASC";
        List<DaftarDistribusiModel> hasil=new ArrayList<>();
        try(Connection c=Koneksi.getConnection();PreparedStatement ps=c.prepareStatement(sql);ResultSet rs=ps.executeQuery()){while(rs.next())hasil.add(new DaftarDistribusiModel(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getTimestamp(6),rs.getBigDecimal(7),rs.getString(8)));}
        return hasil;
    }

    @Override public List<LaporanPersediaanModel> getLaporanPersediaanDistribusi() throws SQLException {
        Session.requireRole(Session.ADMINISTRATOR);

        String sql = "SELECT j.id_jenis_bbm,j.nama_bbm,j.satuan,"
                + "COALESCE(p.total_diterima,0) AS total_diterima,"
                + "COALESCE(d.total_didistribusikan,0) AS total_didistribusikan,"
                + "COALESCE(g.stok_gudang,0) AS stok_gudang,"
                + "COALESCE(s.stok_spbu,0) AS stok_spbu "
                + "FROM jenis_bbm j "
                + "LEFT JOIN (SELECT id_jenis_bbm,SUM(jumlah_diterima) total_diterima "
                + "FROM penerimaan_bbm GROUP BY id_jenis_bbm) p ON p.id_jenis_bbm=j.id_jenis_bbm "
                + "LEFT JOIN (SELECT id_jenis_bbm,SUM(jumlah_dikirim) total_didistribusikan "
                + "FROM distribusi_bbm GROUP BY id_jenis_bbm) d ON d.id_jenis_bbm=j.id_jenis_bbm "
                + "LEFT JOIN (SELECT id_jenis_bbm,SUM(jumlah_stok) stok_gudang "
                + "FROM stok_penyimpanan GROUP BY id_jenis_bbm) g ON g.id_jenis_bbm=j.id_jenis_bbm "
                + "LEFT JOIN (SELECT id_jenis_bbm,SUM(jumlah_stok) stok_spbu "
                + "FROM stok_spbu GROUP BY id_jenis_bbm) s ON s.id_jenis_bbm=j.id_jenis_bbm "
                + "WHERE j.status_aktif='AKTIF' ORDER BY j.id_jenis_bbm";
        List<LaporanPersediaanModel> hasil = new ArrayList<>();
        try (Connection c = Koneksi.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                hasil.add(new LaporanPersediaanModel(
                        rs.getInt("id_jenis_bbm"), rs.getString("nama_bbm"),
                        rs.getBigDecimal("total_diterima"), rs.getBigDecimal("total_didistribusikan"),
                        rs.getBigDecimal("stok_gudang"), rs.getBigDecimal("stok_spbu"),
                        rs.getString("satuan")));
            }
        }
        return hasil;
    }

    @Override public List<LaporanPersediaanModel> getLaporanPersediaanDistribusi(
            String namaBbm, String namaLokasi, java.sql.Date tanggalAwal,
            java.sql.Date tanggalAkhir) throws SQLException {
        Session.requireRole(Session.ADMINISTRATOR);

        String sql = "SELECT j.id_jenis_bbm,j.nama_bbm,j.satuan,"
                + "COALESCE(p.total_diterima,0) total_diterima,"
                + "COALESCE(d.total_didistribusikan,0) total_didistribusikan,"
                + "COALESCE(g.stok_gudang,0) stok_gudang,"
                + "COALESCE(s.stok_spbu,0) stok_spbu "
                + "FROM jenis_bbm j "
                + "LEFT JOIN (SELECT pb.id_jenis_bbm,SUM(pb.jumlah_diterima) total_diterima "
                + "FROM penerimaan_bbm pb JOIN lokasi_penyimpanan lp "
                + "ON lp.id_lokasi_penyimpanan=pb.id_lokasi_penyimpanan "
                + "WHERE (? IS NULL OR lp.nama_lokasi LIKE ?) "
                + "AND (? IS NULL OR DATE(pb.tanggal_penerimaan)>=?) "
                + "AND (? IS NULL OR DATE(pb.tanggal_penerimaan)<=?) "
                + "GROUP BY pb.id_jenis_bbm) p ON p.id_jenis_bbm=j.id_jenis_bbm "
                + "LEFT JOIN (SELECT db.id_jenis_bbm,SUM(db.jumlah_dikirim) total_didistribusikan "
                + "FROM distribusi_bbm db JOIN lokasi_penyimpanan ld "
                + "ON ld.id_lokasi_penyimpanan=db.id_lokasi_penyimpanan "
                + "WHERE (? IS NULL OR ld.nama_lokasi LIKE ?) "
                + "AND (? IS NULL OR DATE(db.tanggal_distribusi)>=?) "
                + "AND (? IS NULL OR DATE(db.tanggal_distribusi)<=?) "
                + "GROUP BY db.id_jenis_bbm) d ON d.id_jenis_bbm=j.id_jenis_bbm "
                + "LEFT JOIN (SELECT id_jenis_bbm,SUM(jumlah_stok) stok_gudang "
                + "FROM stok_penyimpanan GROUP BY id_jenis_bbm) g ON g.id_jenis_bbm=j.id_jenis_bbm "
                + "LEFT JOIN (SELECT id_jenis_bbm,SUM(jumlah_stok) stok_spbu "
                + "FROM stok_spbu GROUP BY id_jenis_bbm) s ON s.id_jenis_bbm=j.id_jenis_bbm "
                + "WHERE j.status_aktif='AKTIF' AND (? IS NULL OR j.nama_bbm LIKE ?) "
                + "ORDER BY j.id_jenis_bbm";
        List<LaporanPersediaanModel> hasil = new ArrayList<>();
        try (Connection c=Koneksi.getConnection(); PreparedStatement ps=c.prepareStatement(sql)) {
            int i=1;
            setLike(ps,i++,namaLokasi); setLike(ps,i++,namaLokasi);
            ps.setDate(i++,tanggalAwal); ps.setDate(i++,tanggalAwal);
            ps.setDate(i++,tanggalAkhir); ps.setDate(i++,tanggalAkhir);
            setLike(ps,i++,namaLokasi); setLike(ps,i++,namaLokasi);
            ps.setDate(i++,tanggalAwal); ps.setDate(i++,tanggalAwal);
            ps.setDate(i++,tanggalAkhir); ps.setDate(i++,tanggalAkhir);
            setLike(ps,i++,namaBbm); setLike(ps,i++,namaBbm);
            try(ResultSet rs=ps.executeQuery()) {
                while(rs.next()) hasil.add(new LaporanPersediaanModel(
                    rs.getInt("id_jenis_bbm"),rs.getString("nama_bbm"),
                    rs.getBigDecimal("total_diterima"),rs.getBigDecimal("total_didistribusikan"),
                    rs.getBigDecimal("stok_gudang"),rs.getBigDecimal("stok_spbu"),rs.getString("satuan")));
            }
        }
        return hasil;
    }

    private void setLike(PreparedStatement ps, int index, String value) throws SQLException {
        if (value == null || value.isBlank() || value.equalsIgnoreCase("Semua")) {
            ps.setNull(index, Types.VARCHAR);
        } else {
            ps.setString(index, "%" + value.trim() + "%");
        }
    }

    @Override public List<String> getNamaBbmAktif() throws SQLException {
        Session.requireRole(Session.ADMINISTRATOR);

        List<String> hasil = new ArrayList<>();
        String sql = "SELECT nama_bbm FROM jenis_bbm WHERE status_aktif='AKTIF' ORDER BY nama_bbm";
        try(Connection c=Koneksi.getConnection(); PreparedStatement ps=c.prepareStatement(sql);
            ResultSet rs=ps.executeQuery()) { while(rs.next()) hasil.add(rs.getString(1)); }
        return hasil;
    }

    @Override public List<String> getNamaLokasiAktif() throws SQLException {
        Session.requireRole(Session.ADMINISTRATOR);

        List<String> hasil = new ArrayList<>();
        String sql = "SELECT nama_lokasi FROM lokasi_penyimpanan WHERE status_aktif='AKTIF' ORDER BY nama_lokasi";
        try(Connection c=Koneksi.getConnection(); PreparedStatement ps=c.prepareStatement(sql);
            ResultSet rs=ps.executeQuery()) { while(rs.next()) hasil.add(rs.getString(1)); }
        return hasil;
    }
}
