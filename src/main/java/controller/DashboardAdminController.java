package controller;


import view.Session;
import dao.DashboardAdminDao;
import dao.DashboardAdminDaoImpl;
import java.sql.SQLException;
import model.DashboardAdminData;

public class DashboardAdminController {
    private final DashboardAdminDao dao;

    public DashboardAdminController() {
        this(new DashboardAdminDaoImpl());
    }
    public DashboardAdminController(DashboardAdminDao dao) {
        this.dao = dao;
    }
    public DashboardAdminData muatRingkasan() throws SQLException {
        Session.requireRole(Session.ADMINISTRATOR);

        return dao.getRingkasan();
    }
}
