package view;

import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;
import java.util.Locale;
import controller.DashboardPenyimpananController;
import model.DashboardPenyimpananData;

public class DashboardPenyimpanan extends javax.swing.JFrame {

    private final DashboardPenyimpananController dashboardController = new DashboardPenyimpananController();

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(DashboardPenyimpanan.class.getName());

    
    public DashboardPenyimpanan() {
        initComponents();
        TampilanTabel.rapikanSemua(this);
        setSize(1366, 768);
        setMinimumSize(new java.awt.Dimension(1100, 700));

        setLocationRelativeTo(null);
        setTitle("OCTANE - Dashboard Petugas Penyimpanan");
        tampilIdentitas();
        muatDashboard();
       
        jButton1.addActionListener(evt -> bukaFormPenerimaan());
        jButton2.addActionListener(evt -> bukaFormDistribusi());
    }

    private void tampilIdentitas() {
        nama.setText(Session.namaLengkap == null ? "Petugas Penyimpanan" : Session.namaLengkap);
        sebagai.setText(Session.peran == null ? "PETUGAS PENYIMPANAN" : Session.peran
                + (Session.lokasi == null || Session.lokasi.isBlank() ? "" : " · " + Session.lokasi));
    }

    private void muatDashboard() {
        try {
            DashboardPenyimpananData data = dashboardController.muatRingkasan(Session.idLokasiPenyimpanan);
            lblNilaiTotalStok.setText(formatAngka(data.getTotalStok()) + " L");
            lblNilaiPenerimaan.setText(formatAngka(data.getTotalPenerimaan()) + " L");
            lblNilaiDistribusi.setText(formatAngka(data.getTotalDistribusi()) + " L");

            tblStokJenis.setModel(modelTabel(
                    new String[]{"Jenis BBM", "Jumlah Stok (Liter)"}, data.getStokPerJenis()));
            tblStatusDistribusi.setModel(modelTabel(
                    new String[]{"Status Distribusi", "Jumlah"}, data.getDistribusiPerStatus()));
            tblDistribusiTerbaru.setModel(modelTabel(
                    new String[]{"ID", "Tanggal", "SPBU Tujuan", "Jenis BBM", "Jumlah (L)", "Status"},
                    data.getDistribusiTerbaru()));
            konfigurasiTabelRingkasan();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this,
                    "Ringkasan dashboard tidak dapat dimuat. Periksa koneksi database.\\n" + ex.getMessage(),
                    "Koneksi Database", JOptionPane.WARNING_MESSAGE);
            logger.log(java.util.logging.Level.WARNING, "Gagal memuat dashboard penyimpanan", ex);
        }
    }

    private void konfigurasiTabelRingkasan() {
        javax.swing.JTable[] tabel = {
            tblStokJenis, tblStatusDistribusi, tblDistribusiTerbaru
        };
        for (javax.swing.JTable item : tabel) {
            item.setRowHeight(32);
            item.setFillsViewportHeight(true);
            item.setAutoCreateRowSorter(true);
            item.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
        }

        tblStokJenis.getColumnModel().getColumn(0).setPreferredWidth(170);
        tblStokJenis.getColumnModel().getColumn(1).setPreferredWidth(120);

        tblStatusDistribusi.getColumnModel().getColumn(0).setPreferredWidth(180);
        tblStatusDistribusi.getColumnModel().getColumn(1).setPreferredWidth(80);

        int[] lebarTerbaru = {55, 135, 150, 115, 100, 145};
        for (int i = 0; i < lebarTerbaru.length; i++) {
            tblDistribusiTerbaru.getColumnModel().getColumn(i)
                    .setPreferredWidth(lebarTerbaru[i]);
        }

        javax.swing.table.DefaultTableCellRenderer angka =
                new javax.swing.table.DefaultTableCellRenderer();
        angka.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        tblStokJenis.getColumnModel().getColumn(1).setCellRenderer(angka);
        tblStatusDistribusi.getColumnModel().getColumn(1).setCellRenderer(angka);
        tblDistribusiTerbaru.getColumnModel().getColumn(4).setCellRenderer(angka);

        javax.swing.table.DefaultTableCellRenderer tengah =
                new javax.swing.table.DefaultTableCellRenderer();
        tengah.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        tblDistribusiTerbaru.getColumnModel().getColumn(0).setCellRenderer(tengah);
        tblDistribusiTerbaru.getColumnModel().getColumn(5).setCellRenderer(tengah);
    }

    private DefaultTableModel modelTabel(String[] kolom, List<Object[]> data) {
        DefaultTableModel model = new DefaultTableModel(kolom, 0) {
            @Override public boolean isCellEditable(int row, int column) { return false; }
        };
        for (Object[] baris : data) model.addRow(baris);
        return model;
    }

    private String formatAngka(BigDecimal nilai) {
        DecimalFormatSymbols simbol = new DecimalFormatSymbols(Locale.forLanguageTag("id-ID"));
        return new DecimalFormat("#,##0.##", simbol).format(
                nilai == null ? BigDecimal.ZERO : nilai);
    }

    private void bukaFormPenerimaan() {
        new CatatPenerimaanBBM().setVisible(true);
        dispose();
    }

    private void bukaFormDistribusi() {
        new CatatDistribusiBBM().setVisible(true);
        dispose();
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jFrame1 = new javax.swing.JFrame();
        navbar = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        catat_penerimaan_bbm = new javax.swing.JButton();
        dashboard1 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        catat_distribusi_bbm = new javax.swing.JButton();
        stok_lokasi = new javax.swing.JButton();
        riwayat_penerimaan = new javax.swing.JButton();
        riwayat_distribusi = new javax.swing.JButton();
        Logout1 = new javax.swing.JButton();
        header = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        container = new javax.swing.JPanel();
        nama = new javax.swing.JLabel();
        sebagai = new javax.swing.JLabel();
        total_lokasi_penyimpanan = new javax.swing.JPanel();
        lblJudulTotalStok = new javax.swing.JLabel();
        lblNilaiTotalStok = new javax.swing.JLabel();
        spbu_terdaftar = new javax.swing.JPanel();
        lblJudulPenerimaan = new javax.swing.JLabel();
        lblNilaiPenerimaan = new javax.swing.JLabel();
        jenis_bbm = new javax.swing.JPanel();
        lblJudulDistribusi = new javax.swing.JLabel();
        lblNilaiDistribusi = new javax.swing.JLabel();
        lokasi_penyimpanan = new javax.swing.JPanel();
        lblJudulStokJenis = new javax.swing.JLabel();
        scrollStokJenis = new javax.swing.JScrollPane();
        tblStokJenis = new javax.swing.JTable();
        status_distribusi = new javax.swing.JPanel();
        lblJudulStatusDistribusi = new javax.swing.JLabel();
        scrollStatusDistribusi = new javax.swing.JScrollPane();
        tblStatusDistribusi = new javax.swing.JTable();
        distribusi_terbaru = new javax.swing.JPanel();
        lblJudulDistribusiTerbaru = new javax.swing.JLabel();
        scrollDistribusiTerbaru = new javax.swing.JScrollPane();
        tblDistribusiTerbaru = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        javax.swing.GroupLayout jFrame1Layout = new javax.swing.GroupLayout(jFrame1.getContentPane());
        jFrame1.getContentPane().setLayout(jFrame1Layout);
        jFrame1Layout.setHorizontalGroup(
            jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jFrame1Layout.setVerticalGroup(
            jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        navbar.setBackground(new java.awt.Color(0, 102, 51));
        navbar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("OCTANE");

        catat_penerimaan_bbm.setBackground(new java.awt.Color(0, 102, 51));
        catat_penerimaan_bbm.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        catat_penerimaan_bbm.setForeground(new java.awt.Color(255, 255, 255));
        catat_penerimaan_bbm.setText("Catat Penerimaan BBM");
        catat_penerimaan_bbm.addActionListener(this::catat_penerimaan_bbmActionPerformed);

        dashboard1.setBackground(new java.awt.Color(0, 102, 51));
        dashboard1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        dashboard1.setForeground(new java.awt.Color(255, 255, 255));
        dashboard1.setText("Dashboard");
        dashboard1.addActionListener(this::dashboard1ActionPerformed);

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Fuel for a Better Tomorrow");

        catat_distribusi_bbm.setBackground(new java.awt.Color(0, 102, 51));
        catat_distribusi_bbm.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        catat_distribusi_bbm.setForeground(new java.awt.Color(255, 255, 255));
        catat_distribusi_bbm.setText("Catat Distribusi BBM");
        catat_distribusi_bbm.addActionListener(this::catat_distribusi_bbmActionPerformed);

        stok_lokasi.setBackground(new java.awt.Color(0, 102, 51));
        stok_lokasi.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        stok_lokasi.setForeground(new java.awt.Color(255, 255, 255));
        stok_lokasi.setText("Stok Lokasi");
        stok_lokasi.addActionListener(this::stok_lokasiActionPerformed);

        riwayat_penerimaan.setBackground(new java.awt.Color(0, 102, 51));
        riwayat_penerimaan.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        riwayat_penerimaan.setForeground(new java.awt.Color(255, 255, 255));
        riwayat_penerimaan.setText("Riwayat Penerimaan");
        riwayat_penerimaan.addActionListener(this::riwayat_penerimaanActionPerformed);

        riwayat_distribusi.setBackground(new java.awt.Color(0, 102, 51));
        riwayat_distribusi.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        riwayat_distribusi.setForeground(new java.awt.Color(255, 255, 255));
        riwayat_distribusi.setText("Riwayat Distribusi");
        riwayat_distribusi.addActionListener(this::riwayat_distribusiActionPerformed);

        Logout1.setText("Logout");
        Logout1.addActionListener(this::Logout1ActionPerformed);

        javax.swing.GroupLayout navbarLayout = new javax.swing.GroupLayout(navbar);
        navbar.setLayout(navbarLayout);
        navbarLayout.setHorizontalGroup(
            navbarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(navbarLayout.createSequentialGroup()
                .addGroup(navbarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(navbarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(riwayat_distribusi, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(riwayat_penerimaan, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(stok_lokasi, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(catat_distribusi_bbm, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(catat_penerimaan_bbm)
                        .addGroup(navbarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(navbarLayout.createSequentialGroup()
                                .addGap(19, 19, 19)
                                .addComponent(Logout1))
                            .addGroup(navbarLayout.createSequentialGroup()
                                .addGap(51, 51, 51)
                                .addComponent(dashboard1, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(navbarLayout.createSequentialGroup()
                        .addGap(95, 95, 95)
                        .addComponent(jLabel1)))
                .addContainerGap(51, Short.MAX_VALUE))
        );
        navbarLayout.setVerticalGroup(
            navbarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(navbarLayout.createSequentialGroup()
                .addGap(62, 62, 62)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addGap(38, 38, 38)
                .addComponent(dashboard1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(51, 51, 51)
                .addComponent(catat_penerimaan_bbm, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(catat_distribusi_bbm, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(stok_lokasi, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(riwayat_penerimaan, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(riwayat_distribusi, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 264, Short.MAX_VALUE)
                .addComponent(Logout1)
                .addGap(29, 29, 29))
        );

        header.setBackground(new java.awt.Color(255, 255, 255));
        header.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        header.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel7.setText("DASHBOARD PETUGAS PENYIMPANAN");

        container.setBackground(new java.awt.Color(255, 255, 255));
        container.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));

        nama.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        nama.setText("NAMA");

        sebagai.setText("Sebagai");

        javax.swing.GroupLayout containerLayout = new javax.swing.GroupLayout(container);
        container.setLayout(containerLayout);
        containerLayout.setHorizontalGroup(
            containerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(containerLayout.createSequentialGroup()
                .addContainerGap(118, Short.MAX_VALUE)
                .addGroup(containerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(nama)
                    .addComponent(sebagai, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        containerLayout.setVerticalGroup(
            containerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, containerLayout.createSequentialGroup()
                .addContainerGap(14, Short.MAX_VALUE)
                .addComponent(nama)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(sebagai, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout headerLayout = new javax.swing.GroupLayout(header);
        header.setLayout(headerLayout);
        headerLayout.setHorizontalGroup(
            headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, headerLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 256, Short.MAX_VALUE)
                .addComponent(container, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15))
        );
        headerLayout.setVerticalGroup(
            headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(headerLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(container, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(25, Short.MAX_VALUE))
        );

        total_lokasi_penyimpanan.setBackground(new java.awt.Color(255, 255, 255));
        total_lokasi_penyimpanan.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        lblJudulTotalStok.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblJudulTotalStok.setText("Total Stok Tersedia");

        lblNilaiTotalStok.setFont(new java.awt.Font("Segoe UI", 1, 22)); // NOI18N
        lblNilaiTotalStok.setText("0 L");

        javax.swing.GroupLayout total_lokasi_penyimpananLayout = new javax.swing.GroupLayout(total_lokasi_penyimpanan);
        total_lokasi_penyimpanan.setLayout(total_lokasi_penyimpananLayout);
        total_lokasi_penyimpananLayout.setHorizontalGroup(
            total_lokasi_penyimpananLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(total_lokasi_penyimpananLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(total_lokasi_penyimpananLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblJudulTotalStok)
                    .addComponent(lblNilaiTotalStok))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        total_lokasi_penyimpananLayout.setVerticalGroup(
            total_lokasi_penyimpananLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(total_lokasi_penyimpananLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(lblJudulTotalStok)
                .addGap(10, 10, 10)
                .addComponent(lblNilaiTotalStok)
                .addContainerGap(40, Short.MAX_VALUE))
        );

        spbu_terdaftar.setBackground(new java.awt.Color(255, 255, 255));
        spbu_terdaftar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        lblJudulPenerimaan.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblJudulPenerimaan.setText("Total Penerimaan");

        lblNilaiPenerimaan.setFont(new java.awt.Font("Segoe UI", 1, 22)); // NOI18N
        lblNilaiPenerimaan.setText("0 L");

        javax.swing.GroupLayout spbu_terdaftarLayout = new javax.swing.GroupLayout(spbu_terdaftar);
        spbu_terdaftar.setLayout(spbu_terdaftarLayout);
        spbu_terdaftarLayout.setHorizontalGroup(
            spbu_terdaftarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(spbu_terdaftarLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(spbu_terdaftarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblJudulPenerimaan)
                    .addComponent(lblNilaiPenerimaan))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        spbu_terdaftarLayout.setVerticalGroup(
            spbu_terdaftarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(spbu_terdaftarLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(lblJudulPenerimaan)
                .addGap(10, 10, 10)
                .addComponent(lblNilaiPenerimaan)
                .addContainerGap(40, Short.MAX_VALUE))
        );

        jenis_bbm.setBackground(new java.awt.Color(255, 255, 255));
        jenis_bbm.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        lblJudulDistribusi.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblJudulDistribusi.setText("Total Distribusi");

        lblNilaiDistribusi.setFont(new java.awt.Font("Segoe UI", 1, 22)); // NOI18N
        lblNilaiDistribusi.setText("0 L");

        javax.swing.GroupLayout jenis_bbmLayout = new javax.swing.GroupLayout(jenis_bbm);
        jenis_bbm.setLayout(jenis_bbmLayout);
        jenis_bbmLayout.setHorizontalGroup(
            jenis_bbmLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jenis_bbmLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jenis_bbmLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblJudulDistribusi)
                    .addComponent(lblNilaiDistribusi))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jenis_bbmLayout.setVerticalGroup(
            jenis_bbmLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jenis_bbmLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(lblJudulDistribusi)
                .addGap(10, 10, 10)
                .addComponent(lblNilaiDistribusi)
                .addContainerGap(40, Short.MAX_VALUE))
        );

        lokasi_penyimpanan.setBackground(new java.awt.Color(255, 255, 255));
        lokasi_penyimpanan.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        lblJudulStokJenis.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        lblJudulStokJenis.setText("Stok BBM per Jenis");

        tblStokJenis.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Jenis BBM", "Jumlah Stok"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblStokJenis.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        tblStokJenis.setFillsViewportHeight(true);
        tblStokJenis.getTableHeader().setReorderingAllowed(false);
        scrollStokJenis.setViewportView(tblStokJenis);

        javax.swing.GroupLayout lokasi_penyimpananLayout = new javax.swing.GroupLayout(lokasi_penyimpanan);
        lokasi_penyimpanan.setLayout(lokasi_penyimpananLayout);
        lokasi_penyimpananLayout.setHorizontalGroup(
            lokasi_penyimpananLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(lokasi_penyimpananLayout.createSequentialGroup()
                .addGroup(lokasi_penyimpananLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblJudulStokJenis)
                    .addComponent(scrollStokJenis, javax.swing.GroupLayout.PREFERRED_SIZE, 366, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        lokasi_penyimpananLayout.setVerticalGroup(
            lokasi_penyimpananLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(lokasi_penyimpananLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(lblJudulStokJenis)
                .addGap(8, 8, 8)
                .addComponent(scrollStokJenis, javax.swing.GroupLayout.DEFAULT_SIZE, 192, Short.MAX_VALUE)
                .addGap(10, 10, 10))
        );

        status_distribusi.setBackground(new java.awt.Color(255, 255, 255));
        status_distribusi.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        lblJudulStatusDistribusi.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        lblJudulStatusDistribusi.setText("Distribusi Berdasarkan Status");

        tblStatusDistribusi.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Status Distribusi", "Jumlah"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblStatusDistribusi.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        tblStatusDistribusi.setFillsViewportHeight(true);
        tblStatusDistribusi.getTableHeader().setReorderingAllowed(false);
        scrollStatusDistribusi.setViewportView(tblStatusDistribusi);

        javax.swing.GroupLayout status_distribusiLayout = new javax.swing.GroupLayout(status_distribusi);
        status_distribusi.setLayout(status_distribusiLayout);
        status_distribusiLayout.setHorizontalGroup(
            status_distribusiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(status_distribusiLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblJudulStatusDistribusi)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(status_distribusiLayout.createSequentialGroup()
                .addComponent(scrollStatusDistribusi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        status_distribusiLayout.setVerticalGroup(
            status_distribusiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(status_distribusiLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(lblJudulStatusDistribusi)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scrollStatusDistribusi, javax.swing.GroupLayout.DEFAULT_SIZE, 192, Short.MAX_VALUE)
                .addGap(10, 10, 10))
        );

        distribusi_terbaru.setBackground(new java.awt.Color(255, 255, 255));
        distribusi_terbaru.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        lblJudulDistribusiTerbaru.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        lblJudulDistribusiTerbaru.setText("Riwayat Distribusi Terbaru");

        tblDistribusiTerbaru.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Tanggal", "SPBU Tujuan", "Jenis BBM", "Jumlah (L)", "Status"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblDistribusiTerbaru.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        tblDistribusiTerbaru.setFillsViewportHeight(true);
        tblDistribusiTerbaru.getTableHeader().setReorderingAllowed(false);
        scrollDistribusiTerbaru.setViewportView(tblDistribusiTerbaru);

        javax.swing.GroupLayout distribusi_terbaruLayout = new javax.swing.GroupLayout(distribusi_terbaru);
        distribusi_terbaru.setLayout(distribusi_terbaruLayout);
        distribusi_terbaruLayout.setHorizontalGroup(
            distribusi_terbaruLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(distribusi_terbaruLayout.createSequentialGroup()
                .addGroup(distribusi_terbaruLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblJudulDistribusiTerbaru)
                    .addGroup(distribusi_terbaruLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(scrollDistribusiTerbaru, javax.swing.GroupLayout.PREFERRED_SIZE, 841, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        distribusi_terbaruLayout.setVerticalGroup(
            distribusi_terbaruLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(distribusi_terbaruLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(lblJudulDistribusiTerbaru)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scrollDistribusiTerbaru, javax.swing.GroupLayout.DEFAULT_SIZE, 137, Short.MAX_VALUE)
                .addGap(12, 12, 12))
        );

        jButton1.setBackground(new java.awt.Color(0, 102, 51));
        jButton1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("+ Catat Penerimaan BBM");
        jButton1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jButton2.setBackground(new java.awt.Color(0, 0, 0));
        jButton2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("+ Catat Distribusi BBM");
        jButton2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(navbar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(total_lokasi_penyimpanan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(18, 18, 18)
                                .addComponent(spbu_terdaftar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(18, 18, 18)
                                .addComponent(jenis_bbm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 186, Short.MAX_VALUE)
                                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lokasi_penyimpanan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(status_distribusi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(distribusi_terbaru, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(48, 48, 48))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addComponent(header, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(navbar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addComponent(header, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(total_lokasi_penyimpanan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(spbu_terdaftar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jenis_bbm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(16, 16, 16)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lokasi_penyimpanan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(status_distribusi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(distribusi_terbaru, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void dashboard1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dashboard1ActionPerformed
        tampilIdentitas();
    }//GEN-LAST:event_dashboard1ActionPerformed

    private void catat_penerimaan_bbmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_catat_penerimaan_bbmActionPerformed
        new CatatPenerimaanBBM().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_catat_penerimaan_bbmActionPerformed

    private void catat_distribusi_bbmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_catat_distribusi_bbmActionPerformed
        new CatatDistribusiBBM().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_catat_distribusi_bbmActionPerformed

    private void stok_lokasiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stok_lokasiActionPerformed
        new StokLokasi().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_stok_lokasiActionPerformed

    private void riwayat_penerimaanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_riwayat_penerimaanActionPerformed
        new RiwayatPenerimaan().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_riwayat_penerimaanActionPerformed

    private void riwayat_distribusiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_riwayat_distribusiActionPerformed
        new RiwayatDistribusi().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_riwayat_distribusiActionPerformed

    private void Logout1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Logout1ActionPerformed
        logout();
    }//GEN-LAST:event_Logout1ActionPerformed

    private void logout() {
        int konfirmasi = JOptionPane.showConfirmDialog(
                this,
                "Apakah Anda yakin ingin keluar dari sistem?",
                "Konfirmasi Logout",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );

        if (konfirmasi == JOptionPane.YES_OPTION) {
            Session.clear();

            new Login().setVisible(true);
            this.dispose();
        }
    }
   
    public static void main(String args[]) {        
        
        java.awt.EventQueue.invokeLater(() -> new DashboardPenyimpanan().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Logout1;
    private javax.swing.JButton catat_distribusi_bbm;
    private javax.swing.JButton catat_penerimaan_bbm;
    private javax.swing.JPanel container;
    private javax.swing.JButton dashboard1;
    private javax.swing.JPanel distribusi_terbaru;
    private javax.swing.JPanel header;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JFrame jFrame1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jenis_bbm;
    private javax.swing.JLabel lblJudulDistribusi;
    private javax.swing.JLabel lblJudulDistribusiTerbaru;
    private javax.swing.JLabel lblJudulPenerimaan;
    private javax.swing.JLabel lblJudulStatusDistribusi;
    private javax.swing.JLabel lblJudulStokJenis;
    private javax.swing.JLabel lblJudulTotalStok;
    private javax.swing.JLabel lblNilaiDistribusi;
    private javax.swing.JLabel lblNilaiPenerimaan;
    private javax.swing.JLabel lblNilaiTotalStok;
    private javax.swing.JPanel lokasi_penyimpanan;
    private javax.swing.JLabel nama;
    private javax.swing.JPanel navbar;
    private javax.swing.JButton riwayat_distribusi;
    private javax.swing.JButton riwayat_penerimaan;
    private javax.swing.JScrollPane scrollDistribusiTerbaru;
    private javax.swing.JScrollPane scrollStatusDistribusi;
    private javax.swing.JScrollPane scrollStokJenis;
    private javax.swing.JLabel sebagai;
    private javax.swing.JPanel spbu_terdaftar;
    private javax.swing.JPanel status_distribusi;
    private javax.swing.JButton stok_lokasi;
    private javax.swing.JTable tblDistribusiTerbaru;
    private javax.swing.JTable tblStatusDistribusi;
    private javax.swing.JTable tblStokJenis;
    private javax.swing.JPanel total_lokasi_penyimpanan;
    // End of variables declaration//GEN-END:variables
}
