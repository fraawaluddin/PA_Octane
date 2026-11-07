package model;

public class LokasiPenyimpananModel {
    private int idLokasiPenyimpanan;
    private String namaLokasi;
    private String alamat;
    private String statusAktif;

    public LokasiPenyimpananModel() {}
    public LokasiPenyimpananModel(int idLokasiPenyimpanan, String namaLokasi, String alamat, String statusAktif) {
        this.idLokasiPenyimpanan = idLokasiPenyimpanan;
        this.namaLokasi = namaLokasi;
        this.alamat = alamat;
        this.statusAktif = statusAktif;
    }
    public int getIdLokasiPenyimpanan() { return idLokasiPenyimpanan; }
    public void setIdLokasiPenyimpanan(int idLokasiPenyimpanan) { this.idLokasiPenyimpanan = idLokasiPenyimpanan; }
    public String getNamaLokasi() { return namaLokasi; }
    public void setNamaLokasi(String namaLokasi) { this.namaLokasi = namaLokasi; }
    public String getAlamat() { return alamat; }
    public void setAlamat(String alamat) { this.alamat = alamat; }
    public String getStatusAktif() { return statusAktif; }
    public void setStatusAktif(String statusAktif) { this.statusAktif = statusAktif; }
}
