package dao;

import java.sql.SQLException;
import java.util.List;
import model.JenisBbmModel;

public interface JenisBbmDao {
    List<JenisBbmModel> getAll() throws SQLException;
    boolean insert(JenisBbmModel data) throws SQLException;
    boolean update(JenisBbmModel data) throws SQLException;
    boolean updateStatus(int id, String status) throws SQLException;
    int deleteOrDeactivate(int id) throws SQLException;
}
