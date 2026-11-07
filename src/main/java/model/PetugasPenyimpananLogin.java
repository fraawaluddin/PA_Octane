package model;

public final class PetugasPenyimpananLogin extends PenggunaLogin {
    public PetugasPenyimpananLogin(int id, String nama, String username,
            String lokasi, int idLokasi) {
        super(id, nama, username, lokasi, idLokasi, 0);
    }
    @Override public String getPeran() { return "PETUGAS PENYIMPANAN"; }
}
