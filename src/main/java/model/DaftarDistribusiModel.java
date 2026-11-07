package model;

import java.math.BigDecimal;
import java.sql.Timestamp;


public record DaftarDistribusiModel(int idDistribusi, String namaBbm, String namaLokasi, String namaSpbu, String namaPetugas, Timestamp tanggalDistribusi, BigDecimal jumlahDikirim, String statusDistribusi) {}
