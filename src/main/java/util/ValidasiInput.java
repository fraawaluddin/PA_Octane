package util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public final class ValidasiInput {
    private ValidasiInput() { }

    public static String wajibTeks(String nilai, String label, int panjangMaksimum) {
        if (nilai == null || nilai.isBlank()) {
            throw new IllegalArgumentException(label + " wajib diisi.");
        }
        String hasil = nilai.trim();
        if (hasil.length() > panjangMaksimum) {
            throw new IllegalArgumentException(label + " maksimal " + panjangMaksimum + " karakter.");
        }
        return hasil;
    }

    public static String opsionalTeks(String nilai, String label, int panjangMaksimum) {
        if (nilai == null || nilai.isBlank()) return null;
        String hasil = nilai.trim();
        if (hasil.length() > panjangMaksimum) {
            throw new IllegalArgumentException(label + " maksimal " + panjangMaksimum + " karakter.");
        }
        return hasil;
    }
   
    public static BigDecimal desimalPositif(BigDecimal nilai, String label) {
        if (nilai == null || nilai.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException(label + " harus lebih besar dari 0.");
        }
        if (nilai.scale() > 2) {
            throw new IllegalArgumentException(label + " maksimal memiliki 2 angka di belakang koma.");
        }
        if (nilai.precision() - nilai.scale() > 13 || nilai.precision() > 15) {
            throw new IllegalArgumentException(label + " melebihi kapasitas DECIMAL(15,2) pada database.");
        }
        return nilai.setScale(2, RoundingMode.UNNECESSARY);
    }

    public static void idPositif(int id, String label) {
        if (id <= 0) throw new IllegalArgumentException(label + " tidak valid.");
    }
}
