package view;

import java.sql.SQLException;
import javax.swing.JOptionPane;
import model.DashboardAdminData;
import controller.DashboardAdminController;

public class DashboardAdmin extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(DashboardAdmin.class.getName());
    private final DashboardAdminController dashboardController = new DashboardAdminController();

    
    public DashboardAdmin() {
        initComponents();
        
        for (java.awt.event.ActionListener listener : penerimaan.getActionListeners()) {
            penerimaan.removeActionListener(listener);
        }
        penerimaan.addActionListener(evt -> bukaRiwayatPenerimaanAdmin());
        penerimaan.setEnabled(true);
        penerimaan.setVisible(true);
        penerimaan.setFocusable(true);
        penerimaan.setCursor(java.awt.Cursor.getPredefinedCursor(java.awt.Cursor.HAND_CURSOR));

        for (java.awt.event.ActionListener listener : distribusi.getActionListeners()) {
            distribusi.removeActionListener(listener);
        }
        distribusi.addActionListener(evt -> bukaRiwayatDistribusiAdmin());
        distribusi.setEnabled(true);
        distribusi.setVisible(true);
        distribusi.setFocusable(true);
        distribusi.setCursor(java.awt.Cursor.getPredefinedCursor(java.awt.Cursor.HAND_CURSOR));
        
        for (java.awt.event.ActionListener listener : spbu.getActionListeners()) {
            spbu.removeActionListener(listener);
        }
        spbu.addActionListener(evt -> bukaKelolaDataSPBU());
        spbu.setEnabled(true);
        spbu.setVisible(true);
        spbu.setFocusable(true);
        spbu.setOpaque(true);
        spbu.setCursor(java.awt.Cursor.getPredefinedCursor(java.awt.Cursor.HAND_CURSOR));
        
        navbar.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override public void mouseReleased(java.awt.event.MouseEvent evt) {
                java.awt.Point titik = javax.swing.SwingUtilities.convertPoint(navbar, evt.getPoint(), spbu);
                if (spbu.isEnabled() && spbu.isVisible()
                        && spbu.getBounds().contains(evt.getPoint())
                        && spbu.contains(titik)) {
                    bukaKelolaDataSPBU();
                }
            }
        });
        navbar.setPreferredSize(new java.awt.Dimension(350, 768));
        navbar.setMinimumSize(new java.awt.Dimension(350, 0));
        TampilanTabel.rapikanSemua(this);
        setExtendedState(javax.swing.JFrame.NORMAL);
        setSize(1366, 768);
        setLocationRelativeTo(null);
        setMinimumSize(new java.awt.Dimension(1100, 720));
        setResizable(false);
        setTitle("OCTANE - Dashboard Administrator");
        tampilIdentitas();
        muatRingkasanDashboard();
    }

    private void muatRingkasanDashboard() {
        try {
            DashboardAdminData data = dashboardController.muatRingkasan();
            jLabel8.setText(String.valueOf(data.getTotalLokasi()));
            jLabel9.setText(String.valueOf(data.getTotalSpbu()));
            jLabel10.setText(String.valueOf(data.getTotalJenisBbm()));
            jLabel11.setText(new java.text.DecimalFormat("#,##0.##")
                    .format(data.getTotalStok()) + " L");

            tableStokLokasi.setModel(modelTabel(
                    new String[]{"Lokasi Penyimpanan", "Total Stok"}, data.getStokPerLokasi()));
            tableStatusDistribusi.setModel(modelTabel(
                    new String[]{"Status Distribusi", "Jumlah"}, data.getDistribusiPerStatus()));
            tableDistribusiTerbaru.setModel(modelTabel(
                    new String[]{"ID", "Tanggal", "SPBU Tujuan", "Jenis BBM", "Jumlah (L)", "Status"},
                    data.getDistribusiTerbaru()));
            konfigurasiTabelRingkasan();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this,
                    "Ringkasan dashboard tidak dapat dimuat. Periksa koneksi database.\\n"
                    + ex.getMessage(),
                    "Koneksi Database", JOptionPane.WARNING_MESSAGE);
            logger.log(java.util.logging.Level.WARNING,
                    "Gagal memuat ringkasan dashboard administrator", ex);
        }
    }

    private void konfigurasiTabelRingkasan() {
        javax.swing.JTable[] tabel = {
            tableStokLokasi, tableStatusDistribusi, tableDistribusiTerbaru
        };
        for (javax.swing.JTable tabelAktif : tabel) {
            tabelAktif.setRowHeight(32);
            tabelAktif.setFillsViewportHeight(true);
            tabelAktif.setAutoCreateRowSorter(true);
            tabelAktif.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
        }

        tableStokLokasi.getColumnModel().getColumn(0).setPreferredWidth(210);
        tableStokLokasi.getColumnModel().getColumn(1).setPreferredWidth(125);

        tableStatusDistribusi.getColumnModel().getColumn(0).setPreferredWidth(180);
        tableStatusDistribusi.getColumnModel().getColumn(1).setPreferredWidth(80);

        int[] lebarTerbaru = {55, 135, 150, 115, 100, 145};
        for (int i = 0; i < lebarTerbaru.length; i++) {
            tableDistribusiTerbaru.getColumnModel().getColumn(i)
                    .setPreferredWidth(lebarTerbaru[i]);
        }

        javax.swing.table.DefaultTableCellRenderer angka =
                new javax.swing.table.DefaultTableCellRenderer();
        angka.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        tableStokLokasi.getColumnModel().getColumn(1).setCellRenderer(angka);
        tableStatusDistribusi.getColumnModel().getColumn(1).setCellRenderer(angka);
        tableDistribusiTerbaru.getColumnModel().getColumn(4).setCellRenderer(angka);

        javax.swing.table.DefaultTableCellRenderer tengah =
                new javax.swing.table.DefaultTableCellRenderer();
        tengah.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        tableDistribusiTerbaru.getColumnModel().getColumn(0).setCellRenderer(tengah);
        tableDistribusiTerbaru.getColumnModel().getColumn(5).setCellRenderer(tengah);
    }

    private javax.swing.table.DefaultTableModel modelTabel(String[] kolom, java.util.List<Object[]> data) {
        javax.swing.table.DefaultTableModel model = new javax.swing.table.DefaultTableModel(kolom, 0) {
            @Override public boolean isCellEditable(int row, int column) { return false; }
        };
        for (Object[] baris : data) model.addRow(baris);
        return model;
    }

    private void tampilIdentitas() {
        nama.setText(Session.namaLengkap);
        sebagai.setText(Session.peran + " · " + Session.lokasi);
    }
    
    private void bukaRiwayatPenerimaanAdmin() {
        Penerimaan halaman = new Penerimaan();
        halaman.setVisible(true);
        this.dispose();
    }

    private void bukaRiwayatDistribusiAdmin() {
        Distribusi halaman = new Distribusi();
        halaman.setVisible(true);
        this.dispose();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jFrame1 = new javax.swing.JFrame();
        header = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        nama = new javax.swing.JLabel();
        sebagai = new javax.swing.JLabel();
        total_lokasi_penyimpanan = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        spbu_terdaftar = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jenis_bbm = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        total_stok_gudang = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        lokasi_penyimpanan = new javax.swing.JPanel();
        labelJudulStokLokasi = new javax.swing.JLabel();
        scrolltableStokLokasi = new javax.swing.JScrollPane();
        tableStokLokasi = new javax.swing.JTable();
        status_distribusi = new javax.swing.JPanel();
        labelJudulStatusDistribusi = new javax.swing.JLabel();
        scrolltableStatusDistribusi = new javax.swing.JScrollPane();
        tableStatusDistribusi = new javax.swing.JTable();
        distribusi_terbaru = new javax.swing.JPanel();
        labelJudulDistribusiTerbaru = new javax.swing.JLabel();
        scrolltableDistribusiTerbaru = new javax.swing.JScrollPane();
        tableDistribusiTerbaru = new javax.swing.JTable();
        navbar = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        spbu = new javax.swing.JButton();
        dashboard1 = new javax.swing.JButton();
        jenisBBM = new javax.swing.JButton();
        lokasiPenyimpanan = new javax.swing.JButton();
        pengguna = new javax.swing.JButton();
        stokPenyimpanan = new javax.swing.JButton();
        stokSpbu = new javax.swing.JButton();
        penerimaan = new javax.swing.JButton();
        distribusi = new javax.swing.JButton();
        konfirmasi = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        Logout = new javax.swing.JButton();

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

        header.setBackground(new java.awt.Color(255, 255, 255));
        header.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel7.setText("DASHBOARD ADMINISTRATOR");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));

        nama.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        nama.setText("NAMA");

        sebagai.setText("Sebagai");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(73, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(sebagai)
                    .addComponent(nama))
                .addGap(14, 14, 14))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(nama)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(sebagai)
                .addContainerGap(14, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout headerLayout = new javax.swing.GroupLayout(header);
        header.setLayout(headerLayout);
        headerLayout.setHorizontalGroup(
            headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, headerLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 23, 23))
        );
        headerLayout.setVerticalGroup(
            headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(headerLayout.createSequentialGroup()
                .addGroup(headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(headerLayout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(headerLayout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        total_lokasi_penyimpanan.setBackground(new java.awt.Color(255, 255, 255));
        total_lokasi_penyimpanan.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel4.setText("Lokasi Penyimpanan");

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jLabel8.setText("0");

        javax.swing.GroupLayout total_lokasi_penyimpananLayout = new javax.swing.GroupLayout(total_lokasi_penyimpanan);
        total_lokasi_penyimpanan.setLayout(total_lokasi_penyimpananLayout);
        total_lokasi_penyimpananLayout.setHorizontalGroup(
            total_lokasi_penyimpananLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(total_lokasi_penyimpananLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(total_lokasi_penyimpananLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(jLabel8))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        total_lokasi_penyimpananLayout.setVerticalGroup(
            total_lokasi_penyimpananLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(total_lokasi_penyimpananLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLabel4)
                .addGap(8, 8, 8)
                .addComponent(jLabel8)
                .addContainerGap(19, Short.MAX_VALUE))
        );

        spbu_terdaftar.setBackground(new java.awt.Color(255, 255, 255));
        spbu_terdaftar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel5.setText("SPBU Terdaftar");

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jLabel9.setText("0");

        javax.swing.GroupLayout spbu_terdaftarLayout = new javax.swing.GroupLayout(spbu_terdaftar);
        spbu_terdaftar.setLayout(spbu_terdaftarLayout);
        spbu_terdaftarLayout.setHorizontalGroup(
            spbu_terdaftarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(spbu_terdaftarLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(spbu_terdaftarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(jLabel9))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        spbu_terdaftarLayout.setVerticalGroup(
            spbu_terdaftarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(spbu_terdaftarLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLabel5)
                .addGap(8, 8, 8)
                .addComponent(jLabel9)
                .addContainerGap(19, Short.MAX_VALUE))
        );

        jenis_bbm.setBackground(new java.awt.Color(255, 255, 255));
        jenis_bbm.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setText("Total Jenis BBM");

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jLabel10.setText("0");

        javax.swing.GroupLayout jenis_bbmLayout = new javax.swing.GroupLayout(jenis_bbm);
        jenis_bbm.setLayout(jenis_bbmLayout);
        jenis_bbmLayout.setHorizontalGroup(
            jenis_bbmLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jenis_bbmLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jenis_bbmLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel10))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jenis_bbmLayout.setVerticalGroup(
            jenis_bbmLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jenis_bbmLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLabel3)
                .addGap(8, 8, 8)
                .addComponent(jLabel10)
                .addContainerGap(19, Short.MAX_VALUE))
        );

        total_stok_gudang.setBackground(new java.awt.Color(255, 255, 255));
        total_stok_gudang.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel6.setText("Total Stok Gudang");

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jLabel11.setText("0 L");

        javax.swing.GroupLayout total_stok_gudangLayout = new javax.swing.GroupLayout(total_stok_gudang);
        total_stok_gudang.setLayout(total_stok_gudangLayout);
        total_stok_gudangLayout.setHorizontalGroup(
            total_stok_gudangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(total_stok_gudangLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(total_stok_gudangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(jLabel11))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        total_stok_gudangLayout.setVerticalGroup(
            total_stok_gudangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(total_stok_gudangLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLabel6)
                .addGap(8, 8, 8)
                .addComponent(jLabel11)
                .addContainerGap(19, Short.MAX_VALUE))
        );

        lokasi_penyimpanan.setBackground(new java.awt.Color(255, 255, 255));
        lokasi_penyimpanan.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        labelJudulStokLokasi.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        labelJudulStokLokasi.setText("Ringkasan Stok per Lokasi");

        tableStokLokasi.setFillsViewportHeight(true);
        tableStokLokasi.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        tableStokLokasi.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Lokasi Penyimpanan", "Total Stok"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableStokLokasi.getTableHeader().setReorderingAllowed(false);
        scrolltableStokLokasi.setViewportView(tableStokLokasi);

        javax.swing.GroupLayout lokasi_penyimpananLayout = new javax.swing.GroupLayout(lokasi_penyimpanan);
        lokasi_penyimpanan.setLayout(lokasi_penyimpananLayout);
        lokasi_penyimpananLayout.setHorizontalGroup(
            lokasi_penyimpananLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(labelJudulStokLokasi)
            .addComponent(scrolltableStokLokasi, javax.swing.GroupLayout.DEFAULT_SIZE, 512, Short.MAX_VALUE)
        );
        lokasi_penyimpananLayout.setVerticalGroup(
            lokasi_penyimpananLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(lokasi_penyimpananLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(labelJudulStokLokasi)
                .addGap(8, 8, 8)
                .addComponent(scrolltableStokLokasi, javax.swing.GroupLayout.DEFAULT_SIZE, 192, Short.MAX_VALUE)
                .addGap(10, 10, 10))
        );

        status_distribusi.setBackground(new java.awt.Color(255, 255, 255));
        status_distribusi.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        labelJudulStatusDistribusi.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        labelJudulStatusDistribusi.setText("Distribusi Berdasarkan Status");

        tableStatusDistribusi.setFillsViewportHeight(true);
        tableStatusDistribusi.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        tableStatusDistribusi.setModel(new javax.swing.table.DefaultTableModel(
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
        tableStatusDistribusi.getTableHeader().setReorderingAllowed(false);
        scrolltableStatusDistribusi.setViewportView(tableStatusDistribusi);

        javax.swing.GroupLayout status_distribusiLayout = new javax.swing.GroupLayout(status_distribusi);
        status_distribusi.setLayout(status_distribusiLayout);
        status_distribusiLayout.setHorizontalGroup(
            status_distribusiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(labelJudulStatusDistribusi)
            .addComponent(scrolltableStatusDistribusi, javax.swing.GroupLayout.DEFAULT_SIZE, 513, Short.MAX_VALUE)
        );
        status_distribusiLayout.setVerticalGroup(
            status_distribusiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(status_distribusiLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(labelJudulStatusDistribusi)
                .addGap(8, 8, 8)
                .addComponent(scrolltableStatusDistribusi, javax.swing.GroupLayout.DEFAULT_SIZE, 192, Short.MAX_VALUE)
                .addGap(10, 10, 10))
        );

        distribusi_terbaru.setBackground(new java.awt.Color(255, 255, 255));
        distribusi_terbaru.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        labelJudulDistribusiTerbaru.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        labelJudulDistribusiTerbaru.setText("Riwayat Distribusi Terbaru");

        tableDistribusiTerbaru.setFillsViewportHeight(true);
        tableDistribusiTerbaru.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        tableDistribusiTerbaru.setModel(new javax.swing.table.DefaultTableModel(
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
        tableDistribusiTerbaru.getTableHeader().setReorderingAllowed(false);
        scrolltableDistribusiTerbaru.setViewportView(tableDistribusiTerbaru);

        javax.swing.GroupLayout distribusi_terbaruLayout = new javax.swing.GroupLayout(distribusi_terbaru);
        distribusi_terbaru.setLayout(distribusi_terbaruLayout);
        distribusi_terbaruLayout.setHorizontalGroup(
            distribusi_terbaruLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(labelJudulDistribusiTerbaru)
            .addComponent(scrolltableDistribusiTerbaru)
        );
        distribusi_terbaruLayout.setVerticalGroup(
            distribusi_terbaruLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(distribusi_terbaruLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(labelJudulDistribusiTerbaru)
                .addGap(8, 8, 8)
                .addComponent(scrolltableDistribusiTerbaru, javax.swing.GroupLayout.DEFAULT_SIZE, 137, Short.MAX_VALUE)
                .addGap(10, 10, 10))
        );

        navbar.setBackground(new java.awt.Color(0, 51, 102));
        navbar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("OCTANE");

        spbu.setBackground(new java.awt.Color(0, 51, 102));
        spbu.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        spbu.setForeground(new java.awt.Color(255, 255, 255));
        spbu.setText("Kelola Data SPBU");
        spbu.addActionListener(this::spbuActionPerformed);

        dashboard1.setBackground(new java.awt.Color(0, 51, 102));
        dashboard1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        dashboard1.setForeground(new java.awt.Color(255, 255, 255));
        dashboard1.setText("Dashboard");
        dashboard1.addActionListener(this::dashboard1ActionPerformed);

        jenisBBM.setBackground(new java.awt.Color(0, 51, 102));
        jenisBBM.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jenisBBM.setForeground(new java.awt.Color(255, 255, 255));
        jenisBBM.setText("Kelola Jenis BBM");
        jenisBBM.addActionListener(this::jenisBBMActionPerformed);

        lokasiPenyimpanan.setBackground(new java.awt.Color(0, 51, 102));
        lokasiPenyimpanan.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lokasiPenyimpanan.setForeground(new java.awt.Color(255, 255, 255));
        lokasiPenyimpanan.setText("Kelola Lokasi Penyimpanan");
        lokasiPenyimpanan.addActionListener(this::lokasiPenyimpananActionPerformed);

        pengguna.setBackground(new java.awt.Color(0, 51, 102));
        pengguna.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        pengguna.setForeground(new java.awt.Color(255, 255, 255));
        pengguna.setText("Kelola Akun Pengguna");
        pengguna.addActionListener(this::penggunaActionPerformed);

        stokPenyimpanan.setBackground(new java.awt.Color(0, 51, 102));
        stokPenyimpanan.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        stokPenyimpanan.setForeground(new java.awt.Color(255, 255, 255));
        stokPenyimpanan.setText("Stok Seluruh Lokasi");
        stokPenyimpanan.addActionListener(this::stokPenyimpananActionPerformed);

        stokSpbu.setBackground(new java.awt.Color(0, 51, 102));
        stokSpbu.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        stokSpbu.setForeground(new java.awt.Color(255, 255, 255));
        stokSpbu.setText("Stok Seluruh SPBU");
        stokSpbu.addActionListener(this::stokSpbuActionPerformed);

        penerimaan.setBackground(new java.awt.Color(0, 51, 102));
        penerimaan.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        penerimaan.setForeground(new java.awt.Color(255, 255, 255));
        penerimaan.setText("Riwayat Penerimaan");
        penerimaan.addActionListener(this::penerimaanActionPerformed);

        distribusi.setBackground(new java.awt.Color(0, 51, 102));
        distribusi.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        distribusi.setForeground(new java.awt.Color(255, 255, 255));
        distribusi.setText("Riwayat Distribusi");
        distribusi.addActionListener(this::distribusiActionPerformed);

        konfirmasi.setBackground(new java.awt.Color(0, 51, 102));
        konfirmasi.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        konfirmasi.setForeground(new java.awt.Color(255, 255, 255));
        konfirmasi.setText("Laporan Persediaan dan Distribusi");
        konfirmasi.addActionListener(this::konfirmasiActionPerformed);

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Fuel for a Better Tomorrow");

        Logout.setText("Logout");
        Logout.addActionListener(this::LogoutActionPerformed);

        javax.swing.GroupLayout navbarLayout = new javax.swing.GroupLayout(navbar);
        navbar.setLayout(navbarLayout);
        navbarLayout.setHorizontalGroup(
            navbarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, navbarLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(102, 102, 102))
            .addGroup(navbarLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(navbarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Logout)
                    .addGroup(navbarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(spbu, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(pengguna, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(stokPenyimpanan, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(stokSpbu, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(penerimaan, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(distribusi, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(konfirmasi, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(dashboard1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jenisBBM, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lokasiPenyimpanan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap(20, Short.MAX_VALUE))
        );
        navbarLayout.setVerticalGroup(
            navbarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(navbarLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addGap(40, 40, 40)
                .addComponent(dashboard1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(pengguna, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(spbu, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jenisBBM, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lokasiPenyimpanan, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(stokPenyimpanan, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(stokSpbu, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(penerimaan, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(distribusi, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(konfirmasi, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 115, Short.MAX_VALUE)
                .addComponent(Logout)
                .addGap(27, 27, 27))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(navbar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(header, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(distribusi_terbaru, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(total_lokasi_penyimpanan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(18, 18, 18)
                                .addComponent(spbu_terdaftar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(18, 18, 18)
                                .addComponent(jenis_bbm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(18, 18, 18)
                                .addComponent(total_stok_gudang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lokasi_penyimpanan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(14, 14, 14)
                                .addComponent(status_distribusi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGap(32, 32, 32))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(header, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(total_lokasi_penyimpanan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(spbu_terdaftar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jenis_bbm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(total_stok_gudang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lokasi_penyimpanan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(status_distribusi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(distribusi_terbaru, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(navbar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void spbuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_spbuActionPerformed
        bukaKelolaDataSPBU();
    }//GEN-LAST:event_spbuActionPerformed

    private void bukaKelolaDataSPBU() {
        logger.info("Klik Kelola Data SPBU diterima. Session role=" + Session.peran
                + ", authenticated=" + Session.isAuthenticated());
        if (!Session.hasRole(Session.ADMINISTRATOR)) {
            JOptionPane.showMessageDialog(this,
                    "Sesi tidak memiliki hak Administrator. Silakan login ulang.",
                    "Akses Ditolak", JOptionPane.WARNING_MESSAGE);
            return;
        }
        spbu.setEnabled(false);
        javax.swing.SwingUtilities.invokeLater(() -> {
            try {
                SPBU halamanSpbu = new SPBU();
                halamanSpbu.setLocationRelativeTo(this);
                halamanSpbu.setVisible(true);
                halamanSpbu.toFront();
                halamanSpbu.requestFocus();
                
                this.dispose();
            } catch (Throwable ex) {
                logger.log(java.util.logging.Level.SEVERE,
                        "Gagal membuka halaman Kelola Data SPBU", ex);
                spbu.setEnabled(true);
                JOptionPane.showMessageDialog(this,
                        "Halaman Kelola Data SPBU gagal dibuka.\n"
                        + "Dashboard tetap dibuka agar navigasi tidak terputus.\n\n"
                        + "Detail: " + ex.getClass().getSimpleName() + ": " + ex.getMessage(),
                        "Gagal Membuka Kelola Data SPBU",
                        JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    private void dashboard1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dashboard1ActionPerformed
        tampilIdentitas();
        muatRingkasanDashboard();
    }//GEN-LAST:event_dashboard1ActionPerformed

    private void jenisBBMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jenisBBMActionPerformed
        new Jenis_BBM().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jenisBBMActionPerformed

    private void lokasiPenyimpananActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lokasiPenyimpananActionPerformed
        new Lokasi_Penyimpanan().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_lokasiPenyimpananActionPerformed

    private void penggunaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_penggunaActionPerformed
        new Pengguna().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_penggunaActionPerformed

    private void stokPenyimpananActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stokPenyimpananActionPerformed
        new Stok_Penyimpanan().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_stokPenyimpananActionPerformed

    private void stokSpbuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stokSpbuActionPerformed
        new Stok_SPBU().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_stokSpbuActionPerformed

    private void penerimaanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_penerimaanActionPerformed
        bukaRiwayatPenerimaanAdmin();
    }//GEN-LAST:event_penerimaanActionPerformed

    private void distribusiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_distribusiActionPerformed
        bukaRiwayatDistribusiAdmin();
    }//GEN-LAST:event_distribusiActionPerformed

    private void konfirmasiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_konfirmasiActionPerformed
        new Konfirmasi().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_konfirmasiActionPerformed

    private void LogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LogoutActionPerformed
        logout();
    }//GEN-LAST:event_LogoutActionPerformed

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
       
        java.awt.EventQueue.invokeLater(() -> new DashboardAdmin().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Logout;
    private javax.swing.JButton dashboard1;
    private javax.swing.JButton distribusi;
    private javax.swing.JPanel distribusi_terbaru;
    private javax.swing.JPanel header;
    private javax.swing.JFrame jFrame1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JButton jenisBBM;
    private javax.swing.JPanel jenis_bbm;
    private javax.swing.JButton konfirmasi;
    private javax.swing.JLabel labelJudulDistribusiTerbaru;
    private javax.swing.JLabel labelJudulStatusDistribusi;
    private javax.swing.JLabel labelJudulStokLokasi;
    private javax.swing.JButton lokasiPenyimpanan;
    private javax.swing.JPanel lokasi_penyimpanan;
    private javax.swing.JLabel nama;
    private javax.swing.JPanel navbar;
    private javax.swing.JButton penerimaan;
    private javax.swing.JButton pengguna;
    private javax.swing.JScrollPane scrolltableDistribusiTerbaru;
    private javax.swing.JScrollPane scrolltableStatusDistribusi;
    private javax.swing.JScrollPane scrolltableStokLokasi;
    private javax.swing.JLabel sebagai;
    private javax.swing.JButton spbu;
    private javax.swing.JPanel spbu_terdaftar;
    private javax.swing.JPanel status_distribusi;
    private javax.swing.JButton stokPenyimpanan;
    private javax.swing.JButton stokSpbu;
    private javax.swing.JTable tableDistribusiTerbaru;
    private javax.swing.JTable tableStatusDistribusi;
    private javax.swing.JTable tableStokLokasi;
    private javax.swing.JPanel total_lokasi_penyimpanan;
    private javax.swing.JPanel total_stok_gudang;
    // End of variables declaration//GEN-END:variables
}
