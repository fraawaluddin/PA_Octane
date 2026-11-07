package controller;

import dao.AutentikasiDaoImpl;
import model.PenggunaLogin;
import dao.AutentikasiDao;

public class LoginController {
    private final AutentikasiDao autentikasiDAO;

    public LoginController() {
        this(new AutentikasiDaoImpl());
    }

    public LoginController(AutentikasiDao autentikasiDAO) {
        this.autentikasiDAO = autentikasiDAO;
    }

    public PenggunaLogin login(String username, String password) throws Exception {
        if (username == null || username.isBlank() || password == null || password.isEmpty()) {
            throw new IllegalArgumentException("Username dan password wajib diisi.");
        }
        return autentikasiDAO.autentikasi(username.trim(), password);
    }
}
