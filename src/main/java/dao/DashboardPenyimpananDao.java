package dao;

import java.sql.SQLException;
import model.DashboardPenyimpananData;

public interface DashboardPenyimpananDao {
    DashboardPenyimpananData getRingkasan(int idLokasi) throws SQLException;
}
