package model;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class StokRingkasModel {
    private final int idStok;
    private final int idPemilik;
    private final String namaPemilik;
    private final int idJenisBbm;
    private final String namaBbm;
    private final BigDecimal jumlahStok;
    private final String satuan;
    private final Timestamp terakhirDiperbarui;

    public StokRingkasModel(int idStok, int idPemilik, String namaPemilik,
            int idJenisBbm, String namaBbm, BigDecimal jumlahStok,
            String satuan, Timestamp terakhirDiperbarui) {
        this.idStok = idStok;
        this.idPemilik = idPemilik;
        this.namaPemilik = namaPemilik;
        this.idJenisBbm = idJenisBbm;
        this.namaBbm = namaBbm;
        this.jumlahStok = jumlahStok;
        this.satuan = satuan;
        this.terakhirDiperbarui = terakhirDiperbarui;
    }
    public int getIdStok() { return idStok; }
    public int getIdPemilik() { return idPemilik; }
    public String getNamaPemilik() { return namaPemilik; }
    public int getIdJenisBbm() { return idJenisBbm; }
    public String getNamaBbm() { return namaBbm; }
    public BigDecimal getJumlahStok() { return jumlahStok; }
    public String getSatuan() { return satuan; }
    public Timestamp getTerakhirDiperbarui() { return terakhirDiperbarui; }
}
