package model;

public abstract class PenggunaLogin {
    private final int idPengguna;
    private final String namaLengkap;
    private final String username;
    private final String lokasi;
    private final int idLokasiPenyimpanan;
    private final int idSpbu;

    protected PenggunaLogin(int idPengguna, String namaLengkap, String username,
            String lokasi, int idLokasiPenyimpanan, int idSpbu) {
        this.idPengguna = idPengguna;
        this.namaLengkap = namaLengkap;
        this.username = username;
        this.lokasi = lokasi;
        this.idLokasiPenyimpanan = idLokasiPenyimpanan;
        this.idSpbu = idSpbu;
    }

    public abstract String getPeran();

    public int getIdPengguna() { return idPengguna; }
    public String getNamaLengkap() { return namaLengkap; }
    public String getUsername() { return username; }
    public String getLokasi() { return lokasi; }
    public int getIdLokasiPenyimpanan() { return idLokasiPenyimpanan; }
    public int getIdSpbu() { return idSpbu; }
}
