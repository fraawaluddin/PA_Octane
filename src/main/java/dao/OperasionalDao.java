package dao;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

public interface OperasionalDao {
    List<String> getJenisBbmAktif() throws SQLException;

    void catatPenerimaan(int idPengguna, int idLokasi, String namaBbm,
            BigDecimal jumlah) throws SQLException;

    void catatDistribusi(int idPengguna, int idLokasi, String namaSpbu,
            String namaBbm, BigDecimal jumlah) throws SQLException;

    void mulaiPemeriksaanDistribusi(int idDistribusi, int idPetugasPenyimpanan) throws SQLException;

    void selesaikanPemeriksaanDistribusi(int idDistribusi, int idPetugasPenyimpanan, String catatanPemeriksaan) throws SQLException;
}
