package model;

public class JenisBbmModel {
    private int idJenisBbm;
    private String namaBbm;
    private String satuan;
    private String statusAktif;

    public JenisBbmModel() {}
    public JenisBbmModel(int idJenisBbm, String namaBbm, String satuan, String statusAktif) {
        this.idJenisBbm = idJenisBbm;
        this.namaBbm = namaBbm;
        this.satuan = satuan;
        this.statusAktif = statusAktif;
    }
    public int getIdJenisBbm() { return idJenisBbm; }
    public void setIdJenisBbm(int idJenisBbm) { this.idJenisBbm = idJenisBbm; }
    public String getNamaBbm() { return namaBbm; }
    public void setNamaBbm(String namaBbm) { this.namaBbm = namaBbm; }
    public String getSatuan() { return satuan; }
    public void setSatuan(String satuan) { this.satuan = satuan; }
    public String getStatusAktif() { return statusAktif; }
    public void setStatusAktif(String statusAktif) { this.statusAktif = statusAktif; }
}
