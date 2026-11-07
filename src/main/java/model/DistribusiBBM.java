package model;

import java.util.Date;

public class DistribusiBBM {
    private int idDistribusi;
    private int idSpbu;
    private int idBbm;
    private double jumlahLiter;
    private Date tanggal;
    private String status;

    public DistribusiBBM() {}

    public int getIdDistribusi() { return idDistribusi; }
    public void setIdDistribusi(int idDistribusi) { this.idDistribusi = idDistribusi; }

    public int getIdSpbu() { return idSpbu; }
    public void setIdSpbu(int idSpbu) { this.idSpbu = idSpbu; }

    public int getIdBbm() { return idBbm; }
    public void setIdBbm(int idBbm) { this.idBbm = idBbm; }

    public double getJumlahLiter() { return jumlahLiter; }
    public void setJumlahLiter(double jumlahLiter) { this.jumlahLiter = jumlahLiter; }

    public Date getTanggal() { return tanggal; }
    public void setTanggal(Date tanggal) { this.tanggal = tanggal; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
