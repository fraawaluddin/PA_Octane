package model;

import java.math.BigDecimal;
import java.sql.Timestamp;

public record DaftarPenerimaanModel(int idPenerimaan, String namaBbm, String namaLokasi, String namaPetugas, Timestamp tanggalPenerimaan, BigDecimal jumlahDiterima) {}
