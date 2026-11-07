package controller;


import view.Session;
import dao.StokDao;
import dao.StokDaoImpl;
import java.sql.SQLException;
import java.util.List;
import model.StokRingkasModel;

public class StokController {
    private final StokDao stokDao;
    public StokController() { this(new StokDaoImpl()); }
    public StokController(StokDao stokDao) { this.stokDao = stokDao; }

    public List<StokRingkasModel> getStokPenyimpanan(String kataKunci) throws SQLException {
        Session.requireRole(Session.ADMINISTRATOR, Session.PETUGAS_PENYIMPANAN);

        return stokDao.getStokPenyimpanan(kataKunci == null ? "" : kataKunci);
    }
    public List<StokRingkasModel> getStokPenyimpananByLokasi(int idLokasi) throws SQLException {
        Session.requireRole(Session.ADMINISTRATOR, Session.PETUGAS_PENYIMPANAN);
        if (idLokasi != Session.idLokasiPenyimpanan) throw new SecurityException("Lokasi tidak sesuai sesi.");

        return stokDao.getStokPenyimpananByLokasi(idLokasi);
    }
    public List<StokRingkasModel> getStokSpbu(String kataKunci) throws SQLException {
        Session.requireRole(Session.ADMINISTRATOR, Session.PETUGAS_SPBU);

        return stokDao.getStokSpbu(kataKunci == null ? "" : kataKunci);
    }
    public List<StokRingkasModel> getStokSpbuBySpbu(int idSpbu) throws SQLException {
        Session.requireRole(Session.ADMINISTRATOR, Session.PETUGAS_SPBU);
        if (idSpbu != Session.idSpbu) throw new SecurityException("SPBU tidak sesuai sesi.");

        return stokDao.getStokSpbuBySpbu(idSpbu);
    }
    public List<StokRingkasModel> getRingkasanStokSpbu(int idSpbu) throws SQLException {
        Session.requireRole(Session.ADMINISTRATOR, Session.PETUGAS_SPBU);
        if (idSpbu != Session.idSpbu) throw new SecurityException("SPBU tidak sesuai sesi.");

        if (idSpbu <= 0) throw new IllegalArgumentException("SPBU tidak valid.");
        return stokDao.getRingkasanStokSpbu(idSpbu);
    }
}
