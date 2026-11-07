package model;

import java.math.BigDecimal;
import java.sql.Timestamp;

public record KonfirmasiRiwayatModel(int idKonfirmasi, int idDistribusi, String namaSpbu, String namaPetugas, Timestamp tanggalKonfirmasi, BigDecimal jumlahAktual, String keterangan) {}
