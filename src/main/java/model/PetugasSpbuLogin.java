package model;

public final class PetugasSpbuLogin extends PenggunaLogin {
    public PetugasSpbuLogin(int id, String nama, String username,
            String lokasi, int idSpbu) {
        super(id, nama, username, lokasi, 0, idSpbu);
    }
    @Override public String getPeran() { return "PETUGAS SPBU"; }
}
