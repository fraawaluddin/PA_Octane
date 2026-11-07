package controller;


import view.Session;
import dao.PenggunaDao;
import dao.PenggunaDaoImpl;
import java.sql.SQLException;
import java.util.List;
import model.Pengguna;
import model.PetugasOperasionalModel;
import util.ValidasiInput;

public class PenggunaController {
    private final PenggunaDao penggunaDao;

    public PenggunaController() {
        this(new PenggunaDaoImpl());
    }

    public PenggunaController(PenggunaDao penggunaDao) {
        this.penggunaDao = penggunaDao;
    }

    public List<Pengguna> getAllPengguna() throws SQLException {
        Session.requireRole(Session.ADMINISTRATOR);

        return penggunaDao.getAll();
    }

    public boolean tambahPengguna(String nama, String username, String password, String role) throws SQLException {
        Session.requireRole(Session.ADMINISTRATOR);

        validasi(nama, username, password, role);
        return penggunaDao.insert(new Pengguna(0, nama.trim(), username.trim(), password, role.trim()));
    }
   
    public boolean tambahAdministrator(String nama, String username, String password, String tanggalPengangkatan) throws SQLException {
        Session.requireRole(Session.ADMINISTRATOR);

        validasi(nama, username, password, "ADMINISTRATOR");
        ValidasiInput.wajibTeks(tanggalPengangkatan, "Tanggal pengangkatan", 10);
        try {
            java.time.LocalDate.parse(tanggalPengangkatan.trim());
        } catch (java.time.format.DateTimeParseException ex) {
            throw new IllegalArgumentException("Tanggal pengangkatan harus berformat YYYY-MM-DD.");
        }
        Pengguna data = new Pengguna(0, nama.trim(), username.trim(), password, "ADMINISTRATOR");
        data.setTanggalPengangkatan(tanggalPengangkatan.trim());
        return penggunaDao.insert(data);
    }

    public boolean updatePengguna(int id, String nama, String username, String password, String role) throws SQLException {
        Session.requireRole(Session.ADMINISTRATOR);

        if (id <= 0) throw new IllegalArgumentException("ID pengguna tidak valid.");
        validasi(nama, username, password, role);
        return penggunaDao.update(new Pengguna(id, nama.trim(), username.trim(), password, role.trim()));
    }

    public boolean ubahStatusPengguna(int id, boolean aktif) throws SQLException {
        Session.requireRole(Session.ADMINISTRATOR);

        if (id <= 0) throw new IllegalArgumentException("ID pengguna tidak valid.");
        return penggunaDao.updateStatus(id, aktif ? "AKTIF" : "NONAKTIF");
    }

    public int hapusPengguna(int id) throws SQLException {
        Session.requireRole(Session.ADMINISTRATOR);

        if (id <= 0) throw new IllegalArgumentException("ID pengguna tidak valid.");
        if (view.Session.idPengguna == id) {
            throw new IllegalArgumentException("Akun yang sedang digunakan tidak boleh dihapus.");
        }
        return penggunaDao.delete(id);
    }
  
    public List<PetugasOperasionalModel> getAllPetugas() throws SQLException {
        Session.requireRole(Session.ADMINISTRATOR);

        return penggunaDao.getAllPetugas();
    }


    public List<String> getPenempatanAktif(String jenisPetugas) throws SQLException {
        Session.requireRole(Session.ADMINISTRATOR);

        if (jenisPetugas == null || jenisPetugas.isBlank()
                || "ADMINISTRATOR".equalsIgnoreCase(jenisPetugas)) return java.util.Collections.emptyList();
        return penggunaDao.getPenempatanAktif(jenisPetugas);
    }

    public boolean updateIdentitasPengguna(int id, String nama, String username) throws SQLException {
        Session.requireRole(Session.ADMINISTRATOR);

        if (id <= 0) throw new IllegalArgumentException("Pilih akun yang akan diubah.");
        ValidasiInput.wajibTeks(nama, "Nama lengkap", 100);
        ValidasiInput.wajibTeks(username, "Username", 50);
        return penggunaDao.updateIdentitas(id,nama.trim(),username.trim());
    }

    public boolean updateIdentitasPengguna(int id, String nama, String username, String passwordBaru) throws SQLException {
        Session.requireRole(Session.ADMINISTRATOR);

        if (id <= 0) throw new IllegalArgumentException("Pilih akun yang akan diubah.");
        ValidasiInput.wajibTeks(nama, "Nama lengkap", 100);
        ValidasiInput.wajibTeks(username, "Username", 50);
        if (passwordBaru == null || passwordBaru.isBlank()) {
            return penggunaDao.updateIdentitas(id, nama.trim(), username.trim());
        }
        ValidasiInput.wajibTeks(passwordBaru, "Password", 255);
        return penggunaDao.updateIdentitasDenganPassword(id, nama.trim(), username.trim(), passwordBaru);
    }
   
    public boolean updateDataAkunDanAtributPeran(int id, String nama, String username,
            String passwordBaru, String atributPeran, String peran) throws SQLException {
        Session.requireRole(Session.ADMINISTRATOR);

        if (id <= 0) throw new IllegalArgumentException("Pilih akun yang akan diubah.");
        ValidasiInput.wajibTeks(nama, "Nama lengkap", 100);
        ValidasiInput.wajibTeks(username, "Username", 50);
        String role = ValidasiInput.wajibTeks(peran, "Peran", 30).toUpperCase();
        String atribut = ValidasiInput.wajibTeks(atributPeran, "Atribut khusus peran", 100);
        if (passwordBaru != null && !passwordBaru.isBlank()) ValidasiInput.wajibTeks(passwordBaru, "Password", 255);
        if ("ADMINISTRATOR".equals(role)) {
            try { java.time.LocalDate.parse(atribut); }
            catch (java.time.format.DateTimeParseException ex) { throw new IllegalArgumentException("Tanggal pengangkatan harus berformat YYYY-MM-DD."); }
        } else if ("PETUGAS SPBU".equals(role)) {
            if (!atribut.equalsIgnoreCase("LULUS") && !atribut.equalsIgnoreCase("BELUM"))
                throw new IllegalArgumentException("Status pelatihan hanya boleh LULUS atau BELUM.");
        } else if (!"PETUGAS PENYIMPANAN".equals(role)) {
            throw new IllegalArgumentException("Peran akun tidak valid.");
        }
        return penggunaDao.updateDataAkunDanAtributPeran(id, nama.trim(), username.trim(), passwordBaru, atribut);
    }
    
    public int tambahPetugas(String nama, String username, String password, String jenisPetugas,
            int idPenempatan, String atribut) throws SQLException {
        Session.requireRole(Session.ADMINISTRATOR);

        ValidasiInput.wajibTeks(nama, "Nama lengkap", 100);
        ValidasiInput.wajibTeks(username, "Username", 50);
        ValidasiInput.wajibTeks(password, "Password", 255);
        String jenis=ValidasiInput.wajibTeks(jenisPetugas, "Jenis petugas", 30).toUpperCase();
        if (!jenis.equals("PETUGAS PENYIMPANAN") && !jenis.equals("PETUGAS SPBU"))
            throw new IllegalArgumentException("Jenis petugas harus PETUGAS PENYIMPANAN atau PETUGAS SPBU.");
        ValidasiInput.idPositif(idPenempatan, "ID penempatan");
        String nilaiAtribut=ValidasiInput.wajibTeks(atribut,
                jenis.equals("PETUGAS PENYIMPANAN") ? "Kualifikasi sertifikasi" : "Status pelatihan", 100);
        if (jenis.equals("PETUGAS SPBU") && !nilaiAtribut.equalsIgnoreCase("LULUS")
                && !nilaiAtribut.equalsIgnoreCase("BELUM"))
            throw new IllegalArgumentException("Status pelatihan hanya boleh LULUS atau BELUM.");
        String atributDb = jenis.equals("PETUGAS SPBU") ? nilaiAtribut.toUpperCase() : nilaiAtribut;
        return penggunaDao.tambahPetugas(new Pengguna(0,nama.trim(),username.trim(),password,jenis),
                jenis,idPenempatan,atributDb);
    }


    public int tambahPetugasDenganNamaPenempatan(String nama, String username, String password,
            String jenisPetugas, String namaPenempatan, String atribut) throws SQLException {
        Session.requireRole(Session.ADMINISTRATOR);

        ValidasiInput.wajibTeks(nama, "Nama lengkap", 100);
        ValidasiInput.wajibTeks(username, "Username", 50);
        ValidasiInput.wajibTeks(password, "Password", 255);
        ValidasiInput.wajibTeks(namaPenempatan, "Penempatan", 100);
        String jenis = ValidasiInput.wajibTeks(jenisPetugas, "Jenis petugas", 30).toUpperCase();
        String nilaiAtribut = ValidasiInput.wajibTeks(atribut, "Atribut petugas", 100);
        if (jenis.equals("PETUGAS SPBU") && !nilaiAtribut.equalsIgnoreCase("LULUS")
                && !nilaiAtribut.equalsIgnoreCase("BELUM")) {
            throw new IllegalArgumentException("Status pelatihan hanya boleh LULUS atau BELUM.");
        }
        return penggunaDao.tambahPetugasDenganNamaPenempatan(
                new Pengguna(0, nama.trim(), username.trim(), password, jenis),
                jenis, namaPenempatan.trim(), nilaiAtribut);
    }

    private void validasi(String nama, String username, String password, String role) {
        ValidasiInput.wajibTeks(nama, "Nama lengkap", 100);
        ValidasiInput.wajibTeks(username, "Username", 50);
        ValidasiInput.wajibTeks(password, "Password", 255);
        String peran = ValidasiInput.wajibTeks(role, "Peran", 30).toUpperCase();
        if (!peran.equals("ADMINISTRATOR") && !peran.equals("PETUGAS PENYIMPANAN")
                && !peran.equals("PETUGAS SPBU")) {
            throw new IllegalArgumentException("Peran pengguna tidak dikenali.");
        }
    }
}
