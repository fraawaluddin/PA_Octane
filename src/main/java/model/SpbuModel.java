package model;

public class SpbuModel {

    private int idSpbu;
    private String namaSpbu;
    private String alamat;
    private String statusAktif;

    public SpbuModel() {
    }

    public SpbuModel(int idSpbu, String namaSpbu,
                     String alamat, String statusAktif) {
        this.idSpbu = idSpbu;
        this.namaSpbu = namaSpbu;
        this.alamat = alamat;
        this.statusAktif = statusAktif;
    }

    public int getIdSpbu() {
        return idSpbu;
    }

    public void setIdSpbu(int idSpbu) {
        this.idSpbu = idSpbu;
    }

    public String getNamaSpbu() {
        return namaSpbu;
    }

    public void setNamaSpbu(String namaSpbu) {
        this.namaSpbu = namaSpbu;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getStatusAktif() {
        return statusAktif;
    }

    public void setStatusAktif(String statusAktif) {
        this.statusAktif = statusAktif;
    }
}