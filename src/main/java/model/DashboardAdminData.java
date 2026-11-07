package model;

import java.math.BigDecimal;
import java.util.List;

public class DashboardAdminData {
    private final int totalLokasi, totalSpbu, totalJenisBbm;
    private final BigDecimal totalStok;
    private final List<Object[]> stokPerLokasi, distribusiPerStatus, distribusiTerbaru;

    public DashboardAdminData(int totalLokasi, int totalSpbu, int totalJenisBbm,
            BigDecimal totalStok, List<Object[]> stokPerLokasi,
            List<Object[]> distribusiPerStatus, List<Object[]> distribusiTerbaru) {
        this.totalLokasi = totalLokasi;
        this.totalSpbu = totalSpbu;
        this.totalJenisBbm = totalJenisBbm;
        this.totalStok = totalStok;
        this.stokPerLokasi = List.copyOf(stokPerLokasi);
        this.distribusiPerStatus = List.copyOf(distribusiPerStatus);
        this.distribusiTerbaru = List.copyOf(distribusiTerbaru);
    }
    public int getTotalLokasi() { return totalLokasi; }
    public int getTotalSpbu() { return totalSpbu; }
    public int getTotalJenisBbm() { return totalJenisBbm; }
    public BigDecimal getTotalStok() { return totalStok; }
    public List<Object[]> getStokPerLokasi() { return stokPerLokasi; }
    public List<Object[]> getDistribusiPerStatus() { return distribusiPerStatus; }
    public List<Object[]> getDistribusiTerbaru() { return distribusiTerbaru; }
}
