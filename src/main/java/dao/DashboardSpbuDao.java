package dao;

import java.math.BigDecimal;
import java.sql.SQLException;
import model.PengirimanMenungguModel;

public interface DashboardSpbuDao {
    int[] getStatistik(int idSpbu) throws SQLException;
    String getStatusPelatihan(int idPengguna, int idSpbu) throws SQLException;
    PengirimanMenungguModel getPengirimanTerbaru(int idSpbu) throws SQLException;
    BigDecimal getTotalStok(int idSpbu) throws SQLException;
}
