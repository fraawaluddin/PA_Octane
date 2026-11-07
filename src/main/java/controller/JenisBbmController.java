package controller;


import view.Session;
import dao.JenisBbmDao;
import dao.JenisBbmDaoImpl;
import java.sql.SQLException;
import java.util.List;
import model.JenisBbmModel;
import util.ValidasiInput;

public class JenisBbmController {
    private final JenisBbmDao dao;
    public JenisBbmController() { this(new JenisBbmDaoImpl()); }
    public JenisBbmController(JenisBbmDao dao) { this.dao=dao; }
    public List<JenisBbmModel> getAll() throws SQLException {
        Session.requireRole(Session.ADMINISTRATOR);
 return dao.getAll(); }
    private void pastikanNamaUnik(String nama, int idDiabaikan) throws SQLException {
        String kandidat = nama.trim();
        boolean duplikat = dao.getAll().stream().anyMatch(data ->
                data.getIdJenisBbm() != idDiabaikan
                && data.getNamaBbm() != null
                && data.getNamaBbm().trim().equalsIgnoreCase(kandidat));
        if (duplikat) throw new IllegalArgumentException("Nama BBM sudah digunakan.");
    }

    public boolean tambah(String nama, String satuan) throws SQLException {
        Session.requireRole(Session.ADMINISTRATOR);

        JenisBbmModel d=new JenisBbmModel();
        d.setNamaBbm(ValidasiInput.wajibTeks(nama,"Nama BBM",50));
        d.setSatuan("Liter");
        pastikanNamaUnik(d.getNamaBbm(), -1);
        return dao.insert(d);
    }
    public boolean ubah(int id, String nama, String satuan) throws SQLException {
        Session.requireRole(Session.ADMINISTRATOR);

        ValidasiInput.idPositif(id,"ID jenis BBM");
        JenisBbmModel d=new JenisBbmModel(); d.setIdJenisBbm(id);
        d.setNamaBbm(ValidasiInput.wajibTeks(nama,"Nama BBM",50));
        d.setSatuan("Liter");
        pastikanNamaUnik(d.getNamaBbm(), id);
        return dao.update(d);
    }
    public boolean ubahStatus(int id,String status) throws SQLException {
        Session.requireRole(Session.ADMINISTRATOR);

        ValidasiInput.idPositif(id,"ID jenis BBM");
        String nilai=ValidasiInput.wajibTeks(status,"Status",20);
        if (!nilai.equalsIgnoreCase("AKTIF") && !nilai.equalsIgnoreCase("NONAKTIF"))
            throw new IllegalArgumentException("Status hanya boleh AKTIF atau NONAKTIF.");
        return dao.updateStatus(id,nilai.toUpperCase());
    }
    public int hapus(int id) throws SQLException {
        Session.requireRole(Session.ADMINISTRATOR);
 ValidasiInput.idPositif(id,"ID jenis BBM"); return dao.deleteOrDeactivate(id); }
}
