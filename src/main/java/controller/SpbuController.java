package controller;


import view.Session;
import dao.SpbuDao;
import dao.SpbuDaoImpl;
import java.sql.SQLException;
import java.util.List;
import model.SpbuModel;
import util.ValidasiInput;

public class SpbuController {
    private final SpbuDao spbuDao;
    public SpbuController() { this(new SpbuDaoImpl()); }
    public SpbuController(SpbuDao dao) { this.spbuDao = dao; }
    public List<SpbuModel> getAllSpbu() throws SQLException {
        Session.requireRole(Session.ADMINISTRATOR);
 return spbuDao.getAll(); }
    public List<SpbuModel> getSpbuAktif() throws SQLException {
        Session.requireRole(Session.ADMINISTRATOR);

        return spbuDao.getAll().stream().filter(s -> "AKTIF".equalsIgnoreCase(s.getStatusAktif())).toList();
    }
    private void pastikanNamaUnik(String nama, int idDiabaikan) throws SQLException {
        String kandidat = nama.trim();
        boolean duplikat = spbuDao.getAll().stream().anyMatch(data ->
                data.getIdSpbu() != idDiabaikan
                && data.getNamaSpbu() != null
                && data.getNamaSpbu().trim().equalsIgnoreCase(kandidat));
        if (duplikat) throw new IllegalArgumentException("Nama SPBU sudah digunakan.");
    }

    public boolean tambahSpbu(String nama, String alamat) throws SQLException {
        Session.requireRole(Session.ADMINISTRATOR);

        SpbuModel spbu=new SpbuModel();
        spbu.setNamaSpbu(ValidasiInput.wajibTeks(nama,"Nama SPBU",100));
        spbu.setAlamat(ValidasiInput.wajibTeks(alamat,"Alamat SPBU",255));
        pastikanNamaUnik(spbu.getNamaSpbu(), -1);
        return spbuDao.insert(spbu);
    }
    public boolean ubahSpbu(int id, String nama, String alamat) throws SQLException {
        Session.requireRole(Session.ADMINISTRATOR);

        ValidasiInput.idPositif(id,"ID SPBU"); SpbuModel spbu=new SpbuModel(); spbu.setIdSpbu(id);
        spbu.setNamaSpbu(ValidasiInput.wajibTeks(nama,"Nama SPBU",100));
        spbu.setAlamat(ValidasiInput.wajibTeks(alamat,"Alamat SPBU",255));
        pastikanNamaUnik(spbu.getNamaSpbu(), id);
        return spbuDao.update(spbu);
    }
    public boolean ubahStatusSpbu(int id, String status) throws SQLException {
        Session.requireRole(Session.ADMINISTRATOR);

        ValidasiInput.idPositif(id,"ID SPBU"); String nilai=ValidasiInput.wajibTeks(status,"Status",20);
        if (!nilai.equalsIgnoreCase("AKTIF") && !nilai.equalsIgnoreCase("NONAKTIF"))
            throw new IllegalArgumentException("Status hanya boleh AKTIF atau NONAKTIF.");
        return spbuDao.updateStatus(id,nilai.toUpperCase());
    }
    public int hapusSpbu(int id) throws SQLException {
        Session.requireRole(Session.ADMINISTRATOR);
 ValidasiInput.idPositif(id,"ID SPBU"); return spbuDao.deleteOrDeactivate(id); }
}
