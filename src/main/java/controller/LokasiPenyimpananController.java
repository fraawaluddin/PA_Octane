package controller;


import view.Session;
import dao.LokasiPenyimpananDao;
import dao.LokasiPenyimpananDaoImpl;
import java.sql.SQLException;
import java.util.List;
import model.LokasiPenyimpananModel;
import util.ValidasiInput;

public class LokasiPenyimpananController {
    private final LokasiPenyimpananDao dao;
    public LokasiPenyimpananController() { this(new LokasiPenyimpananDaoImpl()); }
    public LokasiPenyimpananController(LokasiPenyimpananDao dao) { this.dao=dao; }
    public List<LokasiPenyimpananModel> getAll() throws SQLException {
        Session.requireRole(Session.ADMINISTRATOR);
 return dao.getAll(); }
    private void pastikanNamaUnik(String nama, int idDiabaikan) throws SQLException {
        String kandidat = nama.trim();
        boolean duplikat = dao.getAll().stream().anyMatch(data ->
                data.getIdLokasiPenyimpanan() != idDiabaikan
                && data.getNamaLokasi() != null
                && data.getNamaLokasi().trim().equalsIgnoreCase(kandidat));
        if (duplikat) throw new IllegalArgumentException("Nama lokasi sudah digunakan.");
    }

    public boolean tambah(String nama, String alamat) throws SQLException {
        Session.requireRole(Session.ADMINISTRATOR);

        LokasiPenyimpananModel d=new LokasiPenyimpananModel();
        d.setNamaLokasi(ValidasiInput.wajibTeks(nama,"Nama lokasi",100));
        d.setAlamat(ValidasiInput.wajibTeks(alamat,"Alamat",255));
        pastikanNamaUnik(d.getNamaLokasi(), -1);
        return dao.insert(d);
    }
    public boolean ubah(int id, String nama, String alamat) throws SQLException {
        Session.requireRole(Session.ADMINISTRATOR);

        ValidasiInput.idPositif(id,"ID lokasi");
        LokasiPenyimpananModel d=new LokasiPenyimpananModel(); d.setIdLokasiPenyimpanan(id);
        d.setNamaLokasi(ValidasiInput.wajibTeks(nama,"Nama lokasi",100));
        d.setAlamat(ValidasiInput.wajibTeks(alamat,"Alamat",255));
        pastikanNamaUnik(d.getNamaLokasi(), id);
        return dao.update(d);
    }
    public boolean ubahStatus(int id,String status) throws SQLException {
        Session.requireRole(Session.ADMINISTRATOR);

        ValidasiInput.idPositif(id,"ID lokasi");
        String nilai=ValidasiInput.wajibTeks(status,"Status",20);
        if (!nilai.equalsIgnoreCase("AKTIF") && !nilai.equalsIgnoreCase("NONAKTIF"))
            throw new IllegalArgumentException("Status hanya boleh AKTIF atau NONAKTIF.");
        return dao.updateStatus(id,nilai.toUpperCase());
    }
    public int hapus(int id) throws SQLException {
        Session.requireRole(Session.ADMINISTRATOR);
 ValidasiInput.idPositif(id,"ID lokasi"); return dao.deleteOrDeactivate(id); }
}
