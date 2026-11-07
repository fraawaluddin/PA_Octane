package model;

public final class AdministratorLogin extends PenggunaLogin {
    public AdministratorLogin(int id, String nama, String username, String lokasi) {
        super(id, nama, username, lokasi, 0, 0);
    }
    @Override public String getPeran() { return "ADMINISTRATOR"; }
}
