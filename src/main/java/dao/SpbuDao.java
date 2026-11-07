package dao;

import java.sql.SQLException;
import java.util.List;
import model.SpbuModel;

public interface SpbuDao {

    List<SpbuModel> getAll() throws SQLException;

    boolean insert(SpbuModel spbu) throws SQLException;

    boolean update(SpbuModel spbu) throws SQLException;

    boolean updateStatus(int idSpbu, String statusAktif)
            throws SQLException;

    int deleteOrDeactivate(int idSpbu) throws SQLException;
}