package controller;


import view.Session;
import dao.KonfirmasiPenerimaanDao;
import dao.KonfirmasiPenerimaanDaoImpl;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import model.PengirimanMenungguModel;
import model.KonfirmasiRiwayatModel;
import util.ValidasiInput;

public class KonfirmasiPenerimaanController {
    private final KonfirmasiPenerimaanDao dao;

    public KonfirmasiPenerimaanController() {
        this(new KonfirmasiPenerimaanDaoImpl());
    }

    public KonfirmasiPenerimaanController(KonfirmasiPenerimaanDao dao) {
        this.dao = dao;
    }

    public List<KonfirmasiRiwayatModel> getDaftarKonfirmasi() throws SQLException {
        Session.requireRole(Session.PETUGAS_SPBU);

        return dao.getDaftarKonfirmasi();
    }

    public List<PengirimanMenungguModel> getPengirimanMenunggu(int idSpbu) throws SQLException {
        Session.requireRole(Session.PETUGAS_SPBU);
        if (idSpbu != Session.idSpbu) throw new SecurityException("Akses hanya untuk SPBU penempatan akun.");

        if (idSpbu <= 0) throw new IllegalArgumentException("SPBU tidak valid.");
        return dao.getPengirimanMenunggu(idSpbu);
    }

    public PengirimanMenungguModel getDetailPengiriman(int idDistribusi, int idSpbu) throws SQLException {
        Session.requireRole(Session.PETUGAS_SPBU);
        if (idSpbu != Session.idSpbu) throw new SecurityException("Akses hanya untuk SPBU penempatan akun.");

        if (idDistribusi <= 0 || idSpbu <= 0) throw new IllegalArgumentException("Pengiriman tidak valid.");
        return dao.getDetailPengiriman(idDistribusi, idSpbu);
    }

    public String konfirmasi(int idDistribusi, int idSpbu, int idPetugasSpbu,
            BigDecimal jumlahAktual, String keterangan) throws SQLException {
        Session.requireRole(Session.PETUGAS_SPBU);
        if (idSpbu != Session.idSpbu) throw new SecurityException("Akses hanya untuk SPBU penempatan akun.");
        if (idPetugasSpbu != Session.idPengguna) throw new SecurityException("Petugas tidak sesuai sesi.");

        ValidasiInput.idPositif(idDistribusi, "ID distribusi");
        ValidasiInput.idPositif(idSpbu, "ID SPBU");
        ValidasiInput.idPositif(idPetugasSpbu, "ID petugas SPBU");
        String catatan = ValidasiInput.wajibTeks(keterangan, "Keterangan konfirmasi", 255);
        BigDecimal aktual = ValidasiInput.desimalPositif(jumlahAktual, "Jumlah aktual");
        return dao.simpanKonfirmasi(idDistribusi, idSpbu, idPetugasSpbu, aktual, catatan);
    }
}
