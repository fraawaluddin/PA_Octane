package model;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class PengirimanMenungguModel {
    private final int idDistribusi;
    private final Timestamp tanggalDistribusi;
    private final String asalPenyimpanan;
    private final String namaBbm;
    private final BigDecimal jumlahDikirim;
    private final String statusDistribusi;

    public PengirimanMenungguModel(int idDistribusi, Timestamp tanggalDistribusi,
            String asalPenyimpanan, String namaBbm, BigDecimal jumlahDikirim,
            String statusDistribusi) {
        this.idDistribusi = idDistribusi;
        this.tanggalDistribusi = tanggalDistribusi;
        this.asalPenyimpanan = asalPenyimpanan;
        this.namaBbm = namaBbm;
        this.jumlahDikirim = jumlahDikirim;
        this.statusDistribusi = statusDistribusi;
    }
    public int getIdDistribusi() { return idDistribusi; }
    public Timestamp getTanggalDistribusi() { return tanggalDistribusi; }
    public String getAsalPenyimpanan() { return asalPenyimpanan; }
    public String getNamaBbm() { return namaBbm; }
    public BigDecimal getJumlahDikirim() { return jumlahDikirim; }
    public String getStatusDistribusi() { return statusDistribusi; }
}
