package dao;

import java.sql.SQLException;
import model.DashboardAdminData;

public interface DashboardAdminDao {
    DashboardAdminData getRingkasan() throws SQLException;
}
