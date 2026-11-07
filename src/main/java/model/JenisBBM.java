package model;

public class JenisBBM {
    private int idBbm;
    private String namaBbm;
    private double hargaPerLiter;

    public JenisBBM() {}

    public JenisBBM(int idBbm, String namaBbm, double hargaPerLiter) {
        this.idBbm = idBbm;
        this.namaBbm = namaBbm;
        this.hargaPerLiter = hargaPerLiter;
    }

    public int getIdBbm() { return idBbm; }
    public void setIdBbm(int idBbm) { this.idBbm = idBbm; }

    public String getNamaBbm() { return namaBbm; }
    public void setNamaBbm(String namaBbm) { this.namaBbm = namaBbm; }

    public double getHargaPerLiter() { return hargaPerLiter; }
    public void setHargaPerLiter(double hargaPerLiter) { this.hargaPerLiter = hargaPerLiter; }
}
