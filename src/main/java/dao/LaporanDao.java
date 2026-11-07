package dao;

import java.sql.SQLException;
import java.util.List;
import model.*;


public interface LaporanDao {
    List<RiwayatPenerimaanModel> getRiwayatPenerimaan(int idLokasi) throws SQLException;
    List<RiwayatDistribusiModel> getRiwayatDistribusi(int idLokasi) throws SQLException;
    List<DaftarPenerimaanModel> getDaftarPenerimaan() throws SQLException;
    List<DaftarDistribusiModel> getDaftarDistribusi() throws SQLException;
    List<RiwayatDistribusiModel> getRiwayatDistribusiSpbu(int idSpbu) throws SQLException;
    List<String> getNamaBbmAktif() throws SQLException;
    List<String> getNamaLokasiAktif() throws SQLException;
    List<LaporanPersediaanModel> getLaporanPersediaanDistribusi() throws SQLException;
    List<LaporanPersediaanModel> getLaporanPersediaanDistribusi(
            String namaBbm, String namaLokasi, java.sql.Date tanggalAwal,
            java.sql.Date tanggalAkhir) throws SQLException;
}
