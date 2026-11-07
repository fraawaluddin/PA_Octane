package dao;

import model.PenggunaLogin;

public interface AutentikasiDao {
    PenggunaLogin autentikasi(String username, String password) throws Exception;
}
