package model;

import java.math.BigDecimal;
import java.sql.Timestamp;

public record RiwayatDistribusiModel(int idDistribusi, Timestamp tanggalDistribusi, String namaLokasi, String namaSpbu, String namaBbm, String satuan, BigDecimal jumlahDikirim, String statusDistribusi, BigDecimal jumlahAktual, String keterangan) {}
