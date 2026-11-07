package dao;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import model.PengirimanMenungguModel;
import model.KonfirmasiRiwayatModel;

public interface KonfirmasiPenerimaanDao {
    List<PengirimanMenungguModel> getPengirimanMenunggu(int idSpbu) throws SQLException;
    List<KonfirmasiRiwayatModel> getDaftarKonfirmasi() throws SQLException;
    PengirimanMenungguModel getDetailPengiriman(int idDistribusi, int idSpbu) throws SQLException;
    String simpanKonfirmasi(int idDistribusi, int idSpbu, int idPetugasSpbu,
            BigDecimal jumlahAktual, String keterangan) throws SQLException;
}
