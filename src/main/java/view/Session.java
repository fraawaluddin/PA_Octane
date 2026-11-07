package view;

public final class Session {
    public static final String ADMINISTRATOR = "ADMINISTRATOR";
    public static final String PETUGAS_PENYIMPANAN = "PETUGAS PENYIMPANAN";
    public static final String PETUGAS_SPBU = "PETUGAS SPBU";

    private Session() {}
    public static int idPengguna;
    public static String namaLengkap;
    public static String username;
    public static String peran;
    public static String lokasi;
    public static int idLokasiPenyimpanan;
    public static int idSpbu;

    public static boolean isAuthenticated() {
        return idPengguna > 0 && peran != null && !peran.isBlank();
    }

    public static boolean hasRole(String... allowedRoles) {
        if (!isAuthenticated() || allowedRoles == null) return false;
        for (String role : allowedRoles) {
            if (role != null && role.equalsIgnoreCase(peran.trim())) return true;
        }
        return false;
    }

    public static void requireRole(String... allowedRoles) {
        if (!hasRole(allowedRoles)) {
            throw new SecurityException("Akses ditolak. Peran akun tidak memiliki izin untuk fitur ini.");
        }
    }

    public static void requireAuthenticated() {
        if (!isAuthenticated()) {
            throw new SecurityException("Sesi tidak valid. Silakan login kembali.");
        }
    }

    public static void clear() {
        idPengguna = 0;
        namaLengkap = null;
        username = null;
        peran = null;
        lokasi = null;
        idLokasiPenyimpanan = 0;
        idSpbu = 0;
    }
}
