package model;

public final class PetugasOperasionalModel {
    private final int idPengguna;
    private final String namaLengkap;
    private final String username;
    private final String statusAktif;
    private final String jenisPetugas;
    private final int idPenempatan;
    private final String namaPenempatan;
    private final String kualifikasiSertifikasi;
    private final String statusPelatihan;

    public PetugasOperasionalModel(int idPengguna, String namaLengkap, String username,
            String statusAktif, String jenisPetugas, int idPenempatan,
            String namaPenempatan, String kualifikasiSertifikasi, String statusPelatihan) {
        this.idPengguna = idPengguna;
        this.namaLengkap = namaLengkap;
        this.username = username;
        this.statusAktif = statusAktif;
        this.jenisPetugas = jenisPetugas;
        this.idPenempatan = idPenempatan;
        this.namaPenempatan = namaPenempatan;
        this.kualifikasiSertifikasi = kualifikasiSertifikasi;
        this.statusPelatihan = statusPelatihan;
    }

    public int getIdPengguna() { return idPengguna; }
    public String getNamaLengkap() { return namaLengkap; }
    public String getUsername() { return username; }
    public String getStatusAktif() { return statusAktif; }
    public String getJenisPetugas() { return jenisPetugas; }
    public int getIdPenempatan() { return idPenempatan; }
    public String getNamaPenempatan() { return namaPenempatan; }
    public String getKualifikasiSertifikasi() { return kualifikasiSertifikasi; }
    public String getStatusPelatihan() { return statusPelatihan; }
}
