package dao;


import view.Session;
import config.Koneksi;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import model.Pengguna;
import model.PetugasOperasionalModel;

public class PenggunaDaoImpl implements PenggunaDao {
    private static final String SELECT_PENGGUNA =
        "SELECT p.id_pengguna, p.nama_lengkap, p.username, p.password, p.status_aktif, "
      + "CASE WHEN a.id_pengguna IS NOT NULL THEN 'ADMINISTRATOR' "
      + "WHEN pp.id_pengguna IS NOT NULL THEN 'PETUGAS PENYIMPANAN' "
      + "WHEN ps.id_pengguna IS NOT NULL THEN 'PETUGAS SPBU' ELSE 'BELUM DITETAPKAN' END AS role_pengguna, "
      + "a.tanggal_pengangkatan, pp.kualifikasi_sertifikasi, ps.status_pelatihan_penerimaan "
      + "FROM pengguna p "
      + "LEFT JOIN administrator a ON a.id_pengguna=p.id_pengguna "
      + "LEFT JOIN petugas_penyimpanan pp ON pp.id_pengguna=p.id_pengguna "
      + "LEFT JOIN petugas_spbu ps ON ps.id_pengguna=p.id_pengguna ";

    @Override
    public Pengguna login(String username, String password) throws SQLException {
        String sql = SELECT_PENGGUNA
                + "WHERE p.username=? AND p.password=? AND p.status_aktif='AKTIF' "
                + "AND ((a.id_pengguna IS NOT NULL) + (pp.id_pengguna IS NOT NULL) + (ps.id_pengguna IS NOT NULL)) = 1";
        try (Connection c = Koneksi.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, password);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? map(rs) : null;
            }
        }
    }

    @Override
    public List<Pengguna> getAll() throws SQLException {
        Session.requireRole(Session.ADMINISTRATOR);

        List<Pengguna> hasil = new ArrayList<>();
       
        String sql = SELECT_PENGGUNA
                + "WHERE ((a.id_pengguna IS NOT NULL) + (pp.id_pengguna IS NOT NULL) + (ps.id_pengguna IS NOT NULL)) = 1 "
                + "ORDER BY p.id_pengguna";
        try (Connection c = Koneksi.getConnection(); Statement st = c.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) hasil.add(map(rs));
        }
        return hasil;
    }

    @Override
    public boolean insert(Pengguna p) throws SQLException {
        Session.requireRole(Session.ADMINISTRATOR);

        validasiData(p);
        if (!"ADMINISTRATOR".equalsIgnoreCase(p.getRole())) {
            throw new SQLException("Pembuatan akun petugas memerlukan pilihan lokasi/SPBU dan atribut penugasan. Form pengguna perlu dilengkapi terlebih dahulu.");
        }
        String sqlPengguna = "INSERT INTO pengguna(nama_lengkap,username,password,status_aktif) VALUES(?,?,?,'AKTIF')";
        try (Connection c = Koneksi.getConnection()) {
            c.setAutoCommit(false);
            try {
                int id;
                try (PreparedStatement ps = c.prepareStatement(sqlPengguna, Statement.RETURN_GENERATED_KEYS)) {
                    ps.setString(1, p.getNamaPengguna().trim());
                    ps.setString(2, p.getUsername().trim());
                    ps.setString(3, p.getPassword());
                    ps.executeUpdate();
                    try (ResultSet keys = ps.getGeneratedKeys()) {
                        if (!keys.next()) throw new SQLException("ID pengguna baru tidak berhasil dibuat.");
                        id = keys.getInt(1);
                    }
                }
                String tanggal = p.getTanggalPengangkatan();
                String sqlAdministrator = tanggal == null || tanggal.isBlank()
                        ? "INSERT INTO administrator(id_pengguna,tanggal_pengangkatan) VALUES(?,CURRENT_DATE())"
                        : "INSERT INTO administrator(id_pengguna,tanggal_pengangkatan) VALUES(?,?)";
                try (PreparedStatement ps = c.prepareStatement(sqlAdministrator)) {
                    ps.setInt(1, id);
                    if (tanggal != null && !tanggal.isBlank()) ps.setDate(2, java.sql.Date.valueOf(tanggal));
                    ps.executeUpdate();
                }
                c.commit();
                return true;
            } catch (SQLException ex) {
                c.rollback();
                throw ex;
            } finally {
                c.setAutoCommit(true);
            }
        }
    }
    
    @Override
    public boolean update(Pengguna p) throws SQLException {
        Session.requireRole(Session.ADMINISTRATOR);

        validasiData(p);
        if (p.getIdPengguna() <= 0) throw new SQLException("ID pengguna tidak valid.");
        String sql = "UPDATE pengguna SET nama_lengkap=?, username=?, password=? WHERE id_pengguna=?";
        try (Connection c = Koneksi.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, p.getNamaPengguna().trim());
            ps.setString(2, p.getUsername().trim());
            ps.setString(3, p.getPassword());
            ps.setInt(4, p.getIdPengguna());
            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public boolean updateStatus(int idPengguna, String statusAktif) throws SQLException {
        Session.requireRole(Session.ADMINISTRATOR);

        if (idPengguna <= 0) throw new SQLException("ID pengguna tidak valid.");
        if (!"AKTIF".equals(statusAktif) && !"NONAKTIF".equals(statusAktif)) {
            throw new SQLException("Status harus AKTIF atau NONAKTIF.");
        }
        String sql = "UPDATE pengguna SET status_aktif=? WHERE id_pengguna=?";
        try (Connection c = Koneksi.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, statusAktif);
            ps.setInt(2, idPengguna);
            return ps.executeUpdate() > 0;
        }
    }

   
    @Override
    public int delete(int id) throws SQLException {
        Session.requireRole(Session.ADMINISTRATOR);

        if (id <= 0) throw new SQLException("ID pengguna tidak valid.");
        try (Connection c = Koneksi.getConnection()) {
            c.setAutoCommit(false);
            try {
                boolean ada;
                try (PreparedStatement ps = c.prepareStatement(
                        "SELECT id_pengguna FROM pengguna WHERE id_pengguna=? FOR UPDATE")) {
                    ps.setInt(1, id);
                    try (ResultSet rs = ps.executeQuery()) { ada = rs.next(); }
                }
                if (!ada) { c.commit(); return 0; }

                boolean adaRiwayat = false;
                String[] pemeriksaan = {
                    "SELECT 1 FROM penerimaan_bbm WHERE id_petugas_penyimpanan=? LIMIT 1",
                    "SELECT 1 FROM distribusi_bbm WHERE id_petugas_penyimpanan=? LIMIT 1",
                    "SELECT 1 FROM konfirmasi_penerimaan WHERE id_petugas_spbu=? LIMIT 1"
                };
                for (String sql : pemeriksaan) {
                    try (PreparedStatement ps = c.prepareStatement(sql)) {
                        ps.setInt(1, id);
                        try (ResultSet rs = ps.executeQuery()) { if (rs.next()) { adaRiwayat = true; break; } }
                    }
                }
                if (adaRiwayat) {
                    try (PreparedStatement ps = c.prepareStatement(
                            "UPDATE pengguna SET status_aktif='NONAKTIF' WHERE id_pengguna=?")) {
                        ps.setInt(1, id); ps.executeUpdate();
                    }
                    c.commit();
                    return 2;
                }

                String[] subtype = {
                    "DELETE FROM administrator WHERE id_pengguna=?",
                    "DELETE FROM petugas_penyimpanan WHERE id_pengguna=?",
                    "DELETE FROM petugas_spbu WHERE id_pengguna=?"
                };
                for (String sql : subtype) {
                    try (PreparedStatement ps = c.prepareStatement(sql)) { ps.setInt(1, id); ps.executeUpdate(); }
                }
                int terhapus;
                try (PreparedStatement ps = c.prepareStatement("DELETE FROM pengguna WHERE id_pengguna=?")) {
                    ps.setInt(1, id); terhapus = ps.executeUpdate();
                }
                c.commit();
                return terhapus > 0 ? 1 : 0;
            } catch (SQLException ex) {
                c.rollback();
                if (ex.getErrorCode() == 1451) {
                    
                    try (PreparedStatement ps = c.prepareStatement(
                            "UPDATE pengguna SET status_aktif='NONAKTIF' WHERE id_pengguna=?")) {
                        ps.setInt(1, id); ps.executeUpdate();
                    }
                    c.commit();
                    return 2;
                }
                throw ex;
            } finally { c.setAutoCommit(true); }
        }
    }

    
    @Override
    public List<PetugasOperasionalModel> getAllPetugas() throws SQLException {
        Session.requireRole(Session.ADMINISTRATOR);

        List<PetugasOperasionalModel> hasil = new ArrayList<>();
        String sql = "SELECT p.id_pengguna,p.nama_lengkap,p.username,p.status_aktif, "
                + "CASE WHEN pp.id_pengguna IS NOT NULL THEN 'PETUGAS PENYIMPANAN' ELSE 'PETUGAS SPBU' END jenis_petugas, "
                + "COALESCE(pp.id_lokasi_penyimpanan,ps.id_spbu) id_penempatan, "
                + "COALESCE(lp.nama_lokasi,s.nama_spbu) nama_penempatan, "
                + "pp.kualifikasi_sertifikasi,ps.status_pelatihan_penerimaan "
                + "FROM pengguna p LEFT JOIN petugas_penyimpanan pp ON pp.id_pengguna=p.id_pengguna "
                + "LEFT JOIN lokasi_penyimpanan lp ON lp.id_lokasi_penyimpanan=pp.id_lokasi_penyimpanan "
                + "LEFT JOIN petugas_spbu ps ON ps.id_pengguna=p.id_pengguna "
                + "LEFT JOIN spbu s ON s.id_spbu=ps.id_spbu "
                + "WHERE pp.id_pengguna IS NOT NULL OR ps.id_pengguna IS NOT NULL ORDER BY p.nama_lengkap";
        try (Connection c=Koneksi.getConnection(); PreparedStatement ps=c.prepareStatement(sql); ResultSet rs=ps.executeQuery()) {
            while (rs.next()) hasil.add(new PetugasOperasionalModel(rs.getInt("id_pengguna"),rs.getString("nama_lengkap"),
                    rs.getString("username"),rs.getString("status_aktif"),rs.getString("jenis_petugas"),
                    rs.getInt("id_penempatan"),rs.getString("nama_penempatan"),rs.getString("kualifikasi_sertifikasi"),
                    rs.getString("status_pelatihan_penerimaan")));
        }
        return hasil;
    }


    @Override
    public List<String> getPenempatanAktif(String jenisPetugas) throws SQLException {
        Session.requireRole(Session.ADMINISTRATOR);

        String jenis = normalisasiJenisPetugas(jenisPetugas);
        String sql = jenis.equals("PETUGAS PENYIMPANAN")
                ? "SELECT nama_lokasi FROM lokasi_penyimpanan WHERE status_aktif='AKTIF' ORDER BY nama_lokasi"
                : "SELECT nama_spbu FROM spbu WHERE status_aktif='AKTIF' ORDER BY nama_spbu";
        List<String> hasil = new ArrayList<>();
        try (Connection c=Koneksi.getConnection(); PreparedStatement ps=c.prepareStatement(sql);
             ResultSet rs=ps.executeQuery()) {
            while (rs.next()) hasil.add(rs.getString(1));
        }
        return hasil;
    }

    @Override
    public boolean updateIdentitas(int idPengguna, String nama, String username) throws SQLException {
        Session.requireRole(Session.ADMINISTRATOR);

        if (idPengguna <= 0 || nama == null || nama.isBlank() || username == null || username.isBlank())
            throw new SQLException("ID, nama, dan username wajib valid.");
        String sql = "UPDATE pengguna SET nama_lengkap=?, username=? WHERE id_pengguna=?";
        try (Connection c=Koneksi.getConnection(); PreparedStatement ps=c.prepareStatement(sql)) {
            ps.setString(1,nama.trim()); ps.setString(2,username.trim()); ps.setInt(3,idPengguna);
            return ps.executeUpdate()>0;
        }
    }

    @Override
    public boolean updateIdentitasDenganPassword(int idPengguna, String nama, String username, String password) throws SQLException {
        Session.requireRole(Session.ADMINISTRATOR);

        if (idPengguna <= 0 || nama == null || nama.isBlank() || username == null || username.isBlank()
                || password == null || password.isBlank())
            throw new SQLException("ID, nama, username, dan password wajib valid.");
        String sql = "UPDATE pengguna SET nama_lengkap=?, username=?, password=? WHERE id_pengguna=?";
        try (Connection c=Koneksi.getConnection(); PreparedStatement ps=c.prepareStatement(sql)) {
            ps.setString(1,nama.trim()); ps.setString(2,username.trim()); ps.setString(3,password); ps.setInt(4,idPengguna);
            return ps.executeUpdate()>0;
        }
    }

    
    @Override
    public boolean updateDataAkunDanAtributPeran(int idPengguna, String nama, String username,
            String passwordBaru, String atributPeran) throws SQLException {
        Session.requireRole(Session.ADMINISTRATOR);

        if (idPengguna <= 0 || nama == null || nama.isBlank() || username == null || username.isBlank()) {
            throw new SQLException("ID, nama, dan username wajib valid.");
        }
        if (atributPeran == null || atributPeran.isBlank()) {
            throw new SQLException("Atribut khusus peran wajib diisi.");
        }
        try (Connection c = Koneksi.getConnection()) {
            c.setAutoCommit(false);
            try {
                String role;
                try (PreparedStatement cek = c.prepareStatement(
                        "SELECT a.id_pengguna AS admin_id, pp.id_pengguna AS penyimpanan_id, "
                      + "ps.id_pengguna AS spbu_id FROM pengguna p "
                      + "LEFT JOIN administrator a ON a.id_pengguna=p.id_pengguna "
                      + "LEFT JOIN petugas_penyimpanan pp ON pp.id_pengguna=p.id_pengguna "
                      + "LEFT JOIN petugas_spbu ps ON ps.id_pengguna=p.id_pengguna "
                      + "WHERE p.id_pengguna=? FOR UPDATE")) {
                    cek.setInt(1, idPengguna);
                    try (ResultSet rs = cek.executeQuery()) {
                        if (!rs.next()) throw new SQLException("Akun pengguna tidak ditemukan.");
                        int jumlah = (rs.getObject("admin_id") != null ? 1 : 0)
                                + (rs.getObject("penyimpanan_id") != null ? 1 : 0)
                                + (rs.getObject("spbu_id") != null ? 1 : 0);
                        if (jumlah != 1) throw new SQLException("Akun harus memiliki tepat satu peran sebelum diubah.");
                        role = rs.getObject("admin_id") != null ? "ADMINISTRATOR"
                                : rs.getObject("penyimpanan_id") != null ? "PETUGAS PENYIMPANAN" : "PETUGAS SPBU";
                    }
                }

                String sqlAkun = (passwordBaru == null || passwordBaru.isBlank())
                        ? "UPDATE pengguna SET nama_lengkap=?, username=? WHERE id_pengguna=?"
                        : "UPDATE pengguna SET nama_lengkap=?, username=?, password=? WHERE id_pengguna=?";
                try (PreparedStatement ps = c.prepareStatement(sqlAkun)) {
                    ps.setString(1, nama.trim());
                    ps.setString(2, username.trim());
                    if (passwordBaru == null || passwordBaru.isBlank()) ps.setInt(3, idPengguna);
                    else { ps.setString(3, passwordBaru); ps.setInt(4, idPengguna); }
                    if (ps.executeUpdate() == 0) throw new SQLException("Data akun tidak mengalami perubahan.");
                }

                String sqlSubtype;
                if ("ADMINISTRATOR".equals(role)) {
                    try { java.time.LocalDate.parse(atributPeran.trim()); }
                    catch (java.time.format.DateTimeParseException ex) {
                        throw new SQLException("Tanggal pengangkatan harus berformat YYYY-MM-DD.", ex);
                    }
                    sqlSubtype = "UPDATE administrator SET tanggal_pengangkatan=? WHERE id_pengguna=?";
                } else if ("PETUGAS PENYIMPANAN".equals(role)) {
                    if (atributPeran.trim().length() > 100) throw new SQLException("Kualifikasi/sertifikasi maksimal 100 karakter.");
                    sqlSubtype = "UPDATE petugas_penyimpanan SET kualifikasi_sertifikasi=? WHERE id_pengguna=?";
                } else {
                    String status = atributPeran.trim().toUpperCase();
                    if (!status.equals("LULUS") && !status.equals("BELUM"))
                        throw new SQLException("Status pelatihan hanya boleh LULUS atau BELUM.");
                    sqlSubtype = "UPDATE petugas_spbu SET status_pelatihan_penerimaan=? WHERE id_pengguna=?";
                    atributPeran = status;
                }
                try (PreparedStatement ps = c.prepareStatement(sqlSubtype)) {
                    ps.setString(1, atributPeran.trim());
                    ps.setInt(2, idPengguna);
                    if (ps.executeUpdate() == 0) throw new SQLException("Data khusus peran gagal diperbarui.");
                }
                c.commit();
                return true;
            } catch (SQLException ex) {
                c.rollback();
                throw ex;
            } finally {
                c.setAutoCommit(true);
            }
        }
    }
   
    @Override
    public int tambahPetugas(Pengguna p, String jenisPetugas, int idPenempatan, String atribut) throws SQLException {
        Session.requireRole(Session.ADMINISTRATOR);

        validasiData(p);
        if (idPenempatan <= 0) throw new SQLException("Penempatan petugas harus dipilih.");
        String jenis = normalisasiJenisPetugas(jenisPetugas);
        if (atribut == null || atribut.isBlank() || atribut.trim().length() > 100)
            throw new SQLException("Atribut petugas wajib diisi dan maksimal 100 karakter.");
        String cekPenempatan = jenis.equals("PETUGAS PENYIMPANAN")
                ? "SELECT 1 FROM lokasi_penyimpanan WHERE id_lokasi_penyimpanan=? AND status_aktif='AKTIF'"
                : "SELECT 1 FROM spbu WHERE id_spbu=? AND status_aktif='AKTIF'";
        String sqlAkun = "INSERT INTO pengguna(nama_lengkap,username,password,status_aktif) VALUES(?,?,?,'AKTIF')";
        try (Connection c=Koneksi.getConnection()) {
            c.setAutoCommit(false);
            try {
                try (PreparedStatement cek=c.prepareStatement(cekPenempatan)) {
                    cek.setInt(1,idPenempatan);
                    try (ResultSet rs=cek.executeQuery()) { if(!rs.next()) throw new SQLException("Penempatan tidak ditemukan atau sedang nonaktif."); }
                }
                int id;
                try (PreparedStatement ps=c.prepareStatement(sqlAkun,Statement.RETURN_GENERATED_KEYS)) {
                    ps.setString(1,p.getNamaPengguna().trim()); ps.setString(2,p.getUsername().trim()); ps.setString(3,p.getPassword()); ps.executeUpdate();
                    try(ResultSet keys=ps.getGeneratedKeys()) { if(!keys.next()) throw new SQLException("ID akun petugas gagal dibuat."); id=keys.getInt(1); }
                }
                String sqlSubtype = jenis.equals("PETUGAS PENYIMPANAN")
                        ? "INSERT INTO petugas_penyimpanan(id_pengguna,kualifikasi_sertifikasi,id_lokasi_penyimpanan) VALUES(?,?,?)"
                        : "INSERT INTO petugas_spbu(id_pengguna,id_spbu,status_pelatihan_penerimaan) VALUES(?,?,?)";
                try (PreparedStatement ps=c.prepareStatement(sqlSubtype)) {
                    ps.setInt(1, id);
                    if (jenis.equals("PETUGAS PENYIMPANAN")) {
                        
                        ps.setString(2, atribut.trim());
                        ps.setInt(3, idPenempatan);
                    } else {
                        
                        ps.setInt(2, idPenempatan);
                        ps.setString(3, atribut.trim().toUpperCase());
                    }
                    ps.executeUpdate();
                }
                c.commit(); return id;
            } catch(SQLException ex) { c.rollback(); throw ex; }
            finally { c.setAutoCommit(true); }
        }
    }


    @Override
    public int tambahPetugasDenganNamaPenempatan(Pengguna pengguna, String jenisPetugas,
            String namaPenempatan, String atribut) throws SQLException {
        if (namaPenempatan == null || namaPenempatan.isBlank()) {
            throw new SQLException("Penempatan wajib diisi.");
        }
        String jenis = normalisasiJenisPetugas(jenisPetugas);
        String sql = jenis.equals("PETUGAS PENYIMPANAN")
                ? "SELECT id_lokasi_penyimpanan FROM lokasi_penyimpanan WHERE nama_lokasi=? AND status_aktif='AKTIF'"
                : "SELECT id_spbu FROM spbu WHERE nama_spbu=? AND status_aktif='AKTIF'";
        try (Connection c = Koneksi.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, namaPenempatan.trim());
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    throw new SQLException("Penempatan aktif tidak ditemukan. Periksa nama lokasi/SPBU.");
                }
                int idPenempatan = rs.getInt(1);
                return tambahPetugas(pengguna, jenis, idPenempatan, atribut);
            }
        }
    }

    private String normalisasiJenisPetugas(String jenis) throws SQLException {
        if (jenis == null) throw new SQLException("Jenis petugas wajib dipilih.");
        String nilai=jenis.trim().toUpperCase();
        if (!nilai.equals("PETUGAS PENYIMPANAN") && !nilai.equals("PETUGAS SPBU")) throw new SQLException("Jenis petugas tidak valid.");
        return nilai;
    }

    private Pengguna map(ResultSet rs) throws SQLException {
        Pengguna p = new Pengguna(rs.getInt("id_pengguna"), rs.getString("nama_lengkap"),
                rs.getString("username"), rs.getString("password"), rs.getString("role_pengguna"));
        p.setStatusAktif(rs.getString("status_aktif"));
        p.setTanggalPengangkatan(rs.getString("tanggal_pengangkatan"));
        p.setKualifikasiSertifikasi(rs.getString("kualifikasi_sertifikasi"));
        p.setStatusPelatihanPenerimaan(rs.getString("status_pelatihan_penerimaan"));
        return p;
    }

    private void validasiData(Pengguna p) throws SQLException {
        if (p == null || p.getNamaPengguna() == null || p.getNamaPengguna().isBlank()
                || p.getUsername() == null || p.getUsername().isBlank()
                || p.getPassword() == null || p.getPassword().isBlank()
                || p.getRole() == null || p.getRole().isBlank()) {
            throw new SQLException("Nama, username, password, dan peran wajib diisi.");
        }
    }
}
