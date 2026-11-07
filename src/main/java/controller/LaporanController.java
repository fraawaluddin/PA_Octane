package controller;


import view.Session;
import dao.LaporanDao;
import dao.LaporanDaoImpl;
import java.sql.SQLException;
import java.util.List;
import model.*;

public class LaporanController {
    private final LaporanDao dao;
    public LaporanController() { this(new LaporanDaoImpl()); }
    public LaporanController(LaporanDao dao) { this.dao = dao; }
    public List<RiwayatPenerimaanModel> getRiwayatPenerimaan(int idLokasi) throws SQLException {
        Session.requireRole(Session.PETUGAS_PENYIMPANAN);
        if (idLokasi != Session.idLokasiPenyimpanan) throw new SecurityException("Lokasi tidak sesuai sesi.");

        validasiLokasi(idLokasi); return dao.getRiwayatPenerimaan(idLokasi);
    }
    public List<RiwayatDistribusiModel> getRiwayatDistribusi(int idLokasi) throws SQLException {
        Session.requireRole(Session.PETUGAS_PENYIMPANAN);
        if (idLokasi != Session.idLokasiPenyimpanan) throw new SecurityException("Lokasi tidak sesuai sesi.");

        validasiLokasi(idLokasi); return dao.getRiwayatDistribusi(idLokasi);
    }
    public List<DaftarPenerimaanModel> getDaftarPenerimaan() throws SQLException {
        Session.requireRole(Session.ADMINISTRATOR);
 return dao.getDaftarPenerimaan(); }
    public List<DaftarDistribusiModel> getDaftarDistribusi() throws SQLException {
        Session.requireRole(Session.ADMINISTRATOR);
 return dao.getDaftarDistribusi(); }
    public List<RiwayatDistribusiModel> getRiwayatDistribusiSpbu(int idSpbu) throws SQLException {
        Session.requireRole(Session.PETUGAS_SPBU);
        if (idSpbu != Session.idSpbu) throw new SecurityException("SPBU tidak sesuai sesi.");

        if (idSpbu <= 0) throw new IllegalArgumentException("SPBU tidak valid.");
        return dao.getRiwayatDistribusiSpbu(idSpbu);
    }
    public List<String> getNamaBbmAktif() throws SQLException {
        Session.requireRole(Session.ADMINISTRATOR);
 return dao.getNamaBbmAktif(); }
    public List<String> getNamaLokasiAktif() throws SQLException {
        Session.requireRole(Session.ADMINISTRATOR);
 return dao.getNamaLokasiAktif(); }
    public List<LaporanPersediaanModel> getLaporanPersediaanDistribusi() throws SQLException {
        Session.requireRole(Session.ADMINISTRATOR);

        return dao.getLaporanPersediaanDistribusi();
    }
    public List<LaporanPersediaanModel> getLaporanPersediaanDistribusi(
            String bbm, String lokasi, java.sql.Date awal, java.sql.Date akhir) throws SQLException {
        Session.requireRole(Session.ADMINISTRATOR);

        if (awal != null && akhir != null && awal.after(akhir)) {
            throw new IllegalArgumentException("Tanggal awal tidak boleh melewati tanggal akhir.");
        }
        return dao.getLaporanPersediaanDistribusi(bbm, lokasi, awal, akhir);
    }
    private void validasiLokasi(int idLokasi) {
        if (idLokasi <= 0) throw new IllegalArgumentException("Lokasi penyimpanan tidak valid.");
    }
}
