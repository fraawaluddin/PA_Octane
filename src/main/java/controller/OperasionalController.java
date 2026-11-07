package controller;


import view.Session;
import dao.OperasionalDao;
import dao.OperasionalDaoImpl;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import util.ValidasiInput;

public class OperasionalController {
    private final OperasionalDao operasionalDao;

    public OperasionalController() { this(new OperasionalDaoImpl()); }
    public OperasionalController(OperasionalDao operasionalDao) {
        this.operasionalDao = operasionalDao;
    }

    public List<String> getJenisBbmAktif() throws SQLException {
        Session.requireRole(Session.PETUGAS_PENYIMPANAN);
        return operasionalDao.getJenisBbmAktif();
    }

    public void catatPenerimaan(int idPengguna, int idLokasi, String namaBbm,
            BigDecimal jumlah) throws SQLException {
        Session.requireRole(Session.PETUGAS_PENYIMPANAN);
        if (idPengguna != Session.idPengguna || idLokasi != Session.idLokasiPenyimpanan) throw new SecurityException("Operasi hanya diizinkan untuk petugas dan lokasi sesuai sesi.");

        validar(idPengguna, idLokasi, namaBbm, jumlah);
        operasionalDao.catatPenerimaan(idPengguna, idLokasi,
                ValidasiInput.wajibTeks(namaBbm, "Jenis BBM", 50), ValidasiInput.desimalPositif(jumlah, "Jumlah penerimaan"));
    }

    public void catatDistribusi(int idPengguna, int idLokasi, String namaSpbu,
            String namaBbm, BigDecimal jumlah) throws SQLException {
        Session.requireRole(Session.PETUGAS_PENYIMPANAN);
        if (idPengguna != Session.idPengguna || idLokasi != Session.idLokasiPenyimpanan) throw new SecurityException("Operasi hanya diizinkan untuk petugas dan lokasi sesuai sesi.");

        validar(idPengguna, idLokasi, namaBbm, jumlah);
        String tujuan = ValidasiInput.wajibTeks(namaSpbu, "SPBU tujuan", 100);
        operasionalDao.catatDistribusi(idPengguna, idLokasi, tujuan,
                ValidasiInput.wajibTeks(namaBbm, "Jenis BBM", 50), ValidasiInput.desimalPositif(jumlah, "Jumlah distribusi"));
    }

    public void mulaiPemeriksaanDistribusi(int idDistribusi, int idPetugasPenyimpanan) throws SQLException {
        Session.requireRole(Session.PETUGAS_PENYIMPANAN);
        if (idPetugasPenyimpanan != Session.idPengguna) throw new SecurityException("Petugas tidak sesuai sesi.");

        ValidasiInput.idPositif(idDistribusi, "ID distribusi");
        ValidasiInput.idPositif(idPetugasPenyimpanan, "ID petugas penyimpanan");
        operasionalDao.mulaiPemeriksaanDistribusi(idDistribusi, idPetugasPenyimpanan);
    }

    public void selesaikanPemeriksaanDistribusi(int idDistribusi, int idPetugasPenyimpanan,
            String catatanPemeriksaan) throws SQLException {
        Session.requireRole(Session.PETUGAS_PENYIMPANAN);
        if (idPetugasPenyimpanan != Session.idPengguna) throw new SecurityException("Petugas tidak sesuai sesi.");

        ValidasiInput.idPositif(idDistribusi, "ID distribusi");
        ValidasiInput.idPositif(idPetugasPenyimpanan, "ID petugas penyimpanan");
        String catatan = ValidasiInput.wajibTeks(catatanPemeriksaan, "Catatan hasil pemeriksaan", 500);
        if (catatan.length() < 5) throw new IllegalArgumentException("Catatan hasil pemeriksaan minimal 5 karakter.");
        operasionalDao.selesaikanPemeriksaanDistribusi(idDistribusi, idPetugasPenyimpanan, catatan);
    }

    private void validar(int idPengguna, int idLokasi, String namaBbm, BigDecimal jumlah) {
        ValidasiInput.idPositif(idPengguna, "ID petugas");
        ValidasiInput.idPositif(idLokasi, "ID lokasi");
        ValidasiInput.wajibTeks(namaBbm, "Jenis BBM", 50);
        ValidasiInput.desimalPositif(jumlah, "Jumlah");
    }
}
