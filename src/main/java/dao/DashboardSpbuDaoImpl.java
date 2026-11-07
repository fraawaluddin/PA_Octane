package dao;

import view.Session;
import config.Koneksi;
import java.math.BigDecimal;
import java.sql.*;
import model.PengirimanMenungguModel;

public class DashboardSpbuDaoImpl implements DashboardSpbuDao {
    @Override public int[] getStatistik(int idSpbu) throws SQLException {
        Session.requireRole(Session.PETUGAS_SPBU);
        if (idSpbu != Session.idSpbu) throw new SecurityException("SPBU tidak sesuai sesi.");

        String sql="SELECT COUNT(CASE WHEN status_distribusi='MENUNGGU_KONFIRMASI' THEN 1 END),COUNT(CASE WHEN status_distribusi='PERLU_PEMERIKSAAN' THEN 1 END),COUNT(CASE WHEN status_distribusi IN ('SELESAI','SELESAI_DITANGANI') THEN 1 END) FROM distribusi_bbm WHERE id_spbu=?";
        try(Connection c=Koneksi.getConnection();PreparedStatement p=c.prepareStatement(sql)){p.setInt(1,idSpbu);try(ResultSet r=p.executeQuery()){r.next();return new int[]{r.getInt(1),r.getInt(2),r.getInt(3)};}}
    }
    @Override public String getStatusPelatihan(int idPengguna,int idSpbu)throws SQLException{
        Session.requireRole(Session.PETUGAS_SPBU);
        if (idSpbu != Session.idSpbu || idPengguna != Session.idPengguna) throw new SecurityException("Identitas SPBU tidak sesuai sesi.");

        String sql="SELECT status_pelatihan_penerimaan FROM petugas_spbu WHERE id_pengguna=? AND id_spbu=?";
        try(Connection c=Koneksi.getConnection();PreparedStatement p=c.prepareStatement(sql)){p.setInt(1,idPengguna);p.setInt(2,idSpbu);try(ResultSet r=p.executeQuery()){return r.next()?r.getString(1):null;}}
    }
    @Override public PengirimanMenungguModel getPengirimanTerbaru(int idSpbu)throws SQLException{
        Session.requireRole(Session.PETUGAS_SPBU);
        if (idSpbu != Session.idSpbu) throw new SecurityException("SPBU tidak sesuai sesi.");

        String sql="SELECT d.id_distribusi,d.tanggal_distribusi,lp.nama_lokasi AS asal,j.nama_bbm,d.jumlah_dikirim,d.status_distribusi FROM distribusi_bbm d JOIN lokasi_penyimpanan lp ON lp.id_lokasi_penyimpanan=d.id_lokasi_penyimpanan JOIN jenis_bbm j ON j.id_jenis_bbm=d.id_jenis_bbm WHERE d.id_spbu=? AND d.status_distribusi='MENUNGGU_KONFIRMASI' AND NOT EXISTS(SELECT 1 FROM konfirmasi_penerimaan k WHERE k.id_distribusi=d.id_distribusi) ORDER BY d.tanggal_distribusi DESC LIMIT 1";
        try(Connection c=Koneksi.getConnection();PreparedStatement p=c.prepareStatement(sql)){p.setInt(1,idSpbu);try(ResultSet r=p.executeQuery()){return r.next()?new PengirimanMenungguModel(r.getInt("id_distribusi"),r.getTimestamp("tanggal_distribusi"),r.getString("asal"),r.getString("nama_bbm"),r.getBigDecimal("jumlah_dikirim"),r.getString("status_distribusi")):null;}}
    }
    @Override public BigDecimal getTotalStok(int idSpbu)throws SQLException{
        Session.requireRole(Session.PETUGAS_SPBU);
        if (idSpbu != Session.idSpbu) throw new SecurityException("SPBU tidak sesuai sesi.");

        String sql="SELECT COALESCE(SUM(jumlah_stok),0) FROM stok_spbu WHERE id_spbu=?";
        try(Connection c=Koneksi.getConnection();PreparedStatement p=c.prepareStatement(sql)){p.setInt(1,idSpbu);try(ResultSet r=p.executeQuery()){r.next();return r.getBigDecimal(1);}}
    }
}
