package dao;

import java.sql.SQLException;
import java.util.List;
import model.LokasiPenyimpananModel;

public interface LokasiPenyimpananDao {
    List<LokasiPenyimpananModel> getAll() throws SQLException;
    boolean insert(LokasiPenyimpananModel data) throws SQLException;
    boolean update(LokasiPenyimpananModel data) throws SQLException;
    boolean updateStatus(int id, String status) throws SQLException;
    int deleteOrDeactivate(int id) throws SQLException;
}
