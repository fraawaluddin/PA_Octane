package dao;

import java.sql.SQLException;
import java.util.List;
import model.StokRingkasModel;

public interface StokDao {
    List<StokRingkasModel> getStokPenyimpanan(String kataKunci) throws SQLException;
    List<StokRingkasModel> getStokPenyimpananByLokasi(int idLokasi) throws SQLException;
    List<StokRingkasModel> getStokSpbu(String kataKunci) throws SQLException;
    List<StokRingkasModel> getStokSpbuBySpbu(int idSpbu) throws SQLException;
    List<StokRingkasModel> getRingkasanStokSpbu(int idSpbu) throws SQLException;
}
