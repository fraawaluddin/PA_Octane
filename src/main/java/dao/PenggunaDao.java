package dao;

import java.sql.SQLException;
import java.util.List;
import model.Pengguna;
import model.PetugasOperasionalModel;

public interface PenggunaDao {
    Pengguna login(String username, String password) throws SQLException;
    List<Pengguna> getAll() throws SQLException;
    boolean insert(Pengguna pengguna) throws SQLException;
    boolean update(Pengguna pengguna) throws SQLException;
    boolean updateStatus(int idPengguna, String statusAktif) throws SQLException;
    /** 1=terhapus, 2=dinonaktifkan karena memiliki riwayat, 0=tidak ditemukan. */
    int delete(int id) throws SQLException;
    List<PetugasOperasionalModel> getAllPetugas() throws SQLException;
    List<String> getPenempatanAktif(String jenisPetugas) throws SQLException;
    boolean updateIdentitas(int idPengguna, String nama, String username) throws SQLException;
    boolean updateIdentitasDenganPassword(int idPengguna, String nama, String username, String password) throws SQLException;
    boolean updateDataAkunDanAtributPeran(int idPengguna, String nama, String username, String passwordBaru, String atributPeran) throws SQLException;
    int tambahPetugas(Pengguna pengguna, String jenisPetugas, int idPenempatan, String atribut) throws SQLException;
    int tambahPetugasDenganNamaPenempatan(Pengguna pengguna, String jenisPetugas, String namaPenempatan, String atribut) throws SQLException;
}
