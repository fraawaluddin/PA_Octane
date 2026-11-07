package model;

public class Pengguna {
    private int idPengguna;
    private String namaPengguna;
    private String username;
    private String password;
    private String role;
    private String statusAktif;
    private String tanggalPengangkatan;
    private String kualifikasiSertifikasi;
    private String statusPelatihanPenerimaan;

    public Pengguna() {}

    public Pengguna(int idPengguna, String namaPengguna, String username, String password, String role) {
        this.idPengguna = idPengguna;
        this.namaPengguna = namaPengguna;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public int getIdPengguna() { return idPengguna; }
    public void setIdPengguna(int idPengguna) { this.idPengguna = idPengguna; }

    public String getNamaPengguna() { return namaPengguna; }
    public void setNamaPengguna(String namaPengguna) { this.namaPengguna = namaPengguna; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String getStatusAktif() { return statusAktif; }
    public void setStatusAktif(String statusAktif) { this.statusAktif = statusAktif; }

    public String getTanggalPengangkatan() { return tanggalPengangkatan; }
    public void setTanggalPengangkatan(String tanggalPengangkatan) { this.tanggalPengangkatan = tanggalPengangkatan; }
    public String getKualifikasiSertifikasi() { return kualifikasiSertifikasi; }
    public void setKualifikasiSertifikasi(String kualifikasiSertifikasi) { this.kualifikasiSertifikasi = kualifikasiSertifikasi; }
    public String getStatusPelatihanPenerimaan() { return statusPelatihanPenerimaan; }
    public void setStatusPelatihanPenerimaan(String statusPelatihanPenerimaan) { this.statusPelatihanPenerimaan = statusPelatihanPenerimaan; }
}
