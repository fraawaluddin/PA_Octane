package model;

import java.math.BigDecimal;

public record LaporanPersediaanModel(
        int idJenisBbm,
        String namaBbm,
        BigDecimal totalDiterima,
        BigDecimal totalDidistribusikan,
        BigDecimal stokGudang,
        BigDecimal stokSpbu,
        String satuan) {
}
