package controller;


import view.Session;
import dao.DashboardPenyimpananDao;
import dao.DashboardPenyimpananDaoImpl;
import java.sql.SQLException;
import model.DashboardPenyimpananData;

public class DashboardPenyimpananController {
    private final DashboardPenyimpananDao dao;

    public DashboardPenyimpananController() {
        this(new DashboardPenyimpananDaoImpl());
    }
    public DashboardPenyimpananController(DashboardPenyimpananDao dao) {
        this.dao = dao;
    }
    public DashboardPenyimpananData muatRingkasan(int idLokasi) throws SQLException {
        Session.requireRole(Session.PETUGAS_PENYIMPANAN);
        if (idLokasi != Session.idLokasiPenyimpanan) throw new SecurityException("Lokasi tidak sesuai sesi.");

        return dao.getRingkasan(idLokasi);
    }
}
