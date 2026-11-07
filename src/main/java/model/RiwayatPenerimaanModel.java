package model;

import java.math.BigDecimal;
import java.sql.Timestamp;

public record RiwayatPenerimaanModel(int idPenerimaan, Timestamp tanggalPenerimaan, String namaBbm, BigDecimal jumlahDiterima, String satuan, String namaPetugas) {}
