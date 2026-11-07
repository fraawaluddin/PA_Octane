
package controller;

import dao.DashboardSpbuDao;
import dao.DashboardSpbuDaoImpl;
import java.math.BigDecimal;
import java.sql.SQLException;
import model.PengirimanMenungguModel;
import view.Session;

public class DashboardSpbuController {

    private final DashboardSpbuDao dao;

    public DashboardSpbuController() {
        this(new DashboardSpbuDaoImpl());
    }

    public DashboardSpbuController(DashboardSpbuDao dao) {
        this.dao = dao;
    }

    public int[] getStatistik(int idSpbu) throws SQLException {
        Session.requireRole(Session.PETUGAS_SPBU);

        if (idSpbu != Session.idSpbu) {
            throw new SecurityException(
                "Akses hanya untuk SPBU penempatan akun."
            );
        }

        if (idSpbu <= 0) {
            throw new IllegalArgumentException("SPBU tidak valid.");
        }

        return dao.getStatistik(idSpbu);
    }

    public String getStatusPelatihan(int idPengguna, int idSpbu)
            throws SQLException {

        Session.requireRole(Session.PETUGAS_SPBU);

        if (idSpbu != Session.idSpbu
                || idPengguna != Session.idPengguna) {
            throw new SecurityException("Data SPBU tidak sesuai sesi.");
        }

        return dao.getStatusPelatihan(idPengguna, idSpbu);
    }

    public PengirimanMenungguModel getPengirimanTerbaru(int idSpbu)
            throws SQLException {

        Session.requireRole(Session.PETUGAS_SPBU);

        if (idSpbu != Session.idSpbu) {
            throw new SecurityException(
                "Akses hanya untuk SPBU penempatan akun."
            );
        }

        if (idSpbu <= 0) {
            throw new IllegalArgumentException("SPBU tidak valid.");
        }

        return dao.getPengirimanTerbaru(idSpbu);
    }

    public BigDecimal getTotalStok(int idSpbu) throws SQLException {
        Session.requireRole(Session.PETUGAS_SPBU);

        if (idSpbu != Session.idSpbu) {
            throw new SecurityException(
                "Akses hanya untuk SPBU penempatan akun."
            );
        }

        if (idSpbu <= 0) {
            throw new IllegalArgumentException("SPBU tidak valid.");
        }

        return dao.getTotalStok(idSpbu);
    }
}

