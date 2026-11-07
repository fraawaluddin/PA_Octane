package model;

import java.math.BigDecimal;
import java.util.List;

public class DashboardPenyimpananData {
    private final BigDecimal totalStok;
    private final BigDecimal totalPenerimaan;
    private final BigDecimal totalDistribusi;
    private final List<Object[]> stokPerJenis;
    private final List<Object[]> distribusiPerStatus;
    private final List<Object[]> distribusiTerbaru;

    public DashboardPenyimpananData(BigDecimal totalStok, BigDecimal totalPenerimaan,
            BigDecimal totalDistribusi, List<Object[]> stokPerJenis,
            List<Object[]> distribusiPerStatus, List<Object[]> distribusiTerbaru) {
        this.totalStok = totalStok;
        this.totalPenerimaan = totalPenerimaan;
        this.totalDistribusi = totalDistribusi;
        this.stokPerJenis = List.copyOf(stokPerJenis);
        this.distribusiPerStatus = List.copyOf(distribusiPerStatus);
        this.distribusiTerbaru = List.copyOf(distribusiTerbaru);
    }
    public BigDecimal getTotalStok() { return totalStok; }
    public BigDecimal getTotalPenerimaan() { return totalPenerimaan; }
    public BigDecimal getTotalDistribusi() { return totalDistribusi; }
    public List<Object[]> getStokPerJenis() { return stokPerJenis; }
    public List<Object[]> getDistribusiPerStatus() { return distribusiPerStatus; }
    public List<Object[]> getDistribusiTerbaru() { return distribusiTerbaru; }
}
