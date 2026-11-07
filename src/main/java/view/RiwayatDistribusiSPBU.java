package view;

import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

public class RiwayatDistribusiSPBU extends javax.swing.JFrame {

    private final controller.LaporanController laporanController = new controller.LaporanController();       

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(RiwayatDistribusiSPBU.class.getName());
  
    public RiwayatDistribusiSPBU() {
        initComponents();
        TampilanTabel.rapikanSemua(this);

        if (Session.peran == null
                || !"PETUGAS SPBU".equalsIgnoreCase(Session.peran)
                || Session.idSpbu <= 0) {

            JOptionPane.showMessageDialog(
                    this,
                    "Akses ditolak. Silakan login sebagai petugas SPBU.",
                    "Peringatan",
                    JOptionPane.WARNING_MESSAGE
            );

            dispose();
            new Login().setVisible(true);
            return;
        }

        setExtendedState(javax.swing.JFrame.NORMAL);
        setSize(1366, 768);
        setLocationRelativeTo(null);
        setMinimumSize(new java.awt.Dimension(1100, 700));
        setResizable(false);

        konfigurasiTabelRiwayat();
        tampilIdentitas();
        loadDataRiwayatDistribusi();
    }

    private void tampilIdentitas() {
        nama.setText(Session.namaLengkap);
        sebagai.setText(Session.peran + " · " + Session.lokasi);
    }
   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jFrame1 = new javax.swing.JFrame();
        navbar = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        konfirmasi_pengiriman_bbm = new javax.swing.JButton();
        dashboard1 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        lihat_pengiriman = new javax.swing.JButton();
        lihat_stok = new javax.swing.JButton();
        riwayat_distribusi = new javax.swing.JButton();
        logout = new javax.swing.JButton();
        header = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        nama = new javax.swing.JLabel();
        sebagai = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblRiwayatDistribusi = new javax.swing.JTable();

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

        navbar.setBackground(new java.awt.Color(204, 102, 0));
        navbar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("OCTANE");

        konfirmasi_pengiriman_bbm.setBackground(new java.awt.Color(204, 102, 0));
        konfirmasi_pengiriman_bbm.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        konfirmasi_pengiriman_bbm.setForeground(new java.awt.Color(255, 255, 255));
        konfirmasi_pengiriman_bbm.setText("Konfirmasi Penerimaan BBM");
        konfirmasi_pengiriman_bbm.addActionListener(this::konfirmasi_pengiriman_bbmActionPerformed);

        dashboard1.setBackground(new java.awt.Color(204, 102, 0));
        dashboard1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        dashboard1.setForeground(new java.awt.Color(255, 255, 255));
        dashboard1.setText("Dashboard");
        dashboard1.addActionListener(this::dashboard1ActionPerformed);

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Fuel for a Better Tomorrow");

        lihat_pengiriman.setBackground(new java.awt.Color(204, 102, 0));
        lihat_pengiriman.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lihat_pengiriman.setForeground(new java.awt.Color(255, 255, 255));
        lihat_pengiriman.setText("Lihat Pengiriman");
        lihat_pengiriman.addActionListener(this::lihat_pengirimanActionPerformed);

        lihat_stok.setBackground(new java.awt.Color(204, 102, 0));
        lihat_stok.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lihat_stok.setForeground(new java.awt.Color(255, 255, 255));
        lihat_stok.setText("Lihat Stok");
        lihat_stok.addActionListener(this::lihat_stokActionPerformed);

        riwayat_distribusi.setBackground(new java.awt.Color(204, 102, 0));
        riwayat_distribusi.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        riwayat_distribusi.setForeground(new java.awt.Color(255, 255, 255));
        riwayat_distribusi.setText("RIWAYAT DISTRIBUSI SPBU");
        riwayat_distribusi.addActionListener(this::riwayat_distribusiActionPerformed);

        logout.setBackground(new java.awt.Color(204, 102, 0));
        logout.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        logout.setForeground(new java.awt.Color(242, 242, 242));
        logout.setText("Logout");
        logout.addActionListener(this::logoutActionPerformed);

        javax.swing.GroupLayout navbarLayout = new javax.swing.GroupLayout(navbar);
        navbar.setLayout(navbarLayout);
        navbarLayout.setHorizontalGroup(
            navbarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(navbarLayout.createSequentialGroup()
                .addGap(0, 19, Short.MAX_VALUE)
                .addGroup(navbarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, navbarLayout.createSequentialGroup()
                        .addGroup(navbarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(navbarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(konfirmasi_pengiriman_bbm, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(dashboard1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lihat_pengiriman, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lihat_stok, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(riwayat_distribusi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGap(22, 22, 22))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, navbarLayout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(84, 84, 84))))
            .addGroup(navbarLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(logout)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        navbarLayout.setVerticalGroup(
            navbarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(navbarLayout.createSequentialGroup()
                .addGap(62, 62, 62)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addGap(48, 48, 48)
                .addComponent(dashboard1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(45, 45, 45)
                .addComponent(konfirmasi_pengiriman_bbm, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lihat_pengiriman, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lihat_stok, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(riwayat_distribusi, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 268, Short.MAX_VALUE)
                .addComponent(logout)
                .addContainerGap())
        );

        header.setBackground(new java.awt.Color(255, 255, 255));
        header.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel7.setText("Riwayat Distribusi");

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
                .addContainerGap(171, Short.MAX_VALUE)
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 635, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25))
        );
        headerLayout.setVerticalGroup(
            headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(headerLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(25, Short.MAX_VALUE))
        );

        tblRiwayatDistribusi.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID Distribusi", "Tanggal Pengiriman", "Asal Penyimpanan", "Jenis BBM", "Jumlah Dikirim", "Jumlah Aktual", "Status Distribusi", "Keterangan"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.sql.Timestamp.class, java.lang.String.class, java.lang.String.class, java.math.BigDecimal.class, java.math.BigDecimal.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(tblRiwayatDistribusi);
        if (tblRiwayatDistribusi.getColumnModel().getColumnCount() > 0) {
            tblRiwayatDistribusi.getColumnModel().getColumn(0).setPreferredWidth(105);
            tblRiwayatDistribusi.getColumnModel().getColumn(1).setPreferredWidth(165);
            tblRiwayatDistribusi.getColumnModel().getColumn(2).setPreferredWidth(220);
            tblRiwayatDistribusi.getColumnModel().getColumn(3).setPreferredWidth(145);
            tblRiwayatDistribusi.getColumnModel().getColumn(4).setPreferredWidth(145);
            tblRiwayatDistribusi.getColumnModel().getColumn(5).setPreferredWidth(145);
            tblRiwayatDistribusi.getColumnModel().getColumn(6).setPreferredWidth(190);
            tblRiwayatDistribusi.getColumnModel().getColumn(7).setPreferredWidth(300);
        }

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(navbar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(header, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(jScrollPane2)
                        .addGap(26, 26, 26))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(navbar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addComponent(header, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2)
                .addGap(24, 24, 24))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void dashboard1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dashboard1ActionPerformed
       new DashboardSPBU().setVisible(true);
       this.dispose();
    }//GEN-LAST:event_dashboard1ActionPerformed

    private void konfirmasi_pengiriman_bbmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_konfirmasi_pengiriman_bbmActionPerformed
        new KonfirmasiPenerimaanBBM().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_konfirmasi_pengiriman_bbmActionPerformed

    private void lihat_pengirimanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lihat_pengirimanActionPerformed
        new LihatPengirimanSPBU().setVisible(true);
        this.dispose();   
    }//GEN-LAST:event_lihat_pengirimanActionPerformed

    private void lihat_stokActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lihat_stokActionPerformed
        new StokSPBUPetugas().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_lihat_stokActionPerformed

    private void riwayat_distribusiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_riwayat_distribusiActionPerformed
        loadDataRiwayatDistribusi();
    }//GEN-LAST:event_riwayat_distribusiActionPerformed

    private void logoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logoutActionPerformed
        int pilihan = JOptionPane.showConfirmDialog(
            this,
            "Apakah yakin ingin keluar dari akun?",
            "Konfirmasi Logout",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE
        );

        if (pilihan == JOptionPane.YES_OPTION) {
            Session.clear();

            new Login().setVisible(true);
            this.dispose();
        }
    }//GEN-LAST:event_logoutActionPerformed

    
    private void konfigurasiTabelRiwayat() {
        tblRiwayatDistribusi.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        tblRiwayatDistribusi.setFillsViewportHeight(true);
        tblRiwayatDistribusi.setRowHeight(36);
        tblRiwayatDistribusi.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 12));
        tblRiwayatDistribusi.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tblRiwayatDistribusi.setShowHorizontalLines(true);
        tblRiwayatDistribusi.setShowVerticalLines(true);
        tblRiwayatDistribusi.setGridColor(new java.awt.Color(220, 225, 230));
        tblRiwayatDistribusi.setSelectionBackground(new java.awt.Color(255, 235, 215));
        tblRiwayatDistribusi.setSelectionForeground(new java.awt.Color(45, 45, 45));
        tblRiwayatDistribusi.getTableHeader().setReorderingAllowed(false);
        tblRiwayatDistribusi.getTableHeader().setResizingAllowed(true);
        tblRiwayatDistribusi.getTableHeader().setPreferredSize(new java.awt.Dimension(0, 40));
        tblRiwayatDistribusi.getTableHeader().setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12));

        int[] widths = {105, 165, 220, 145, 145, 145, 190, 300};
        for (int i = 0; i < widths.length && i < tblRiwayatDistribusi.getColumnModel().getColumnCount(); i++) {
            tblRiwayatDistribusi.getColumnModel().getColumn(i).setPreferredWidth(widths[i]);
        }

        DefaultTableCellRenderer center = new DefaultTableCellRenderer();
        center.setHorizontalAlignment(SwingConstants.CENTER);
        if (tblRiwayatDistribusi.getColumnModel().getColumnCount() > 0) {
            tblRiwayatDistribusi.getColumnModel().getColumn(0).setCellRenderer(center);
        }
        DefaultTableCellRenderer numeric = new DefaultTableCellRenderer() {
            private final DecimalFormat format = new DecimalFormat("#,##0.##");
            @Override
            protected void setValue(Object value) {
                setText(value instanceof BigDecimal ? format.format(value)
                        : value == null ? "—" : value.toString());
            }
        };
        numeric.setHorizontalAlignment(SwingConstants.RIGHT);
        if (tblRiwayatDistribusi.getColumnModel().getColumnCount() > 5) {
            tblRiwayatDistribusi.getColumnModel().getColumn(4).setCellRenderer(numeric);
            tblRiwayatDistribusi.getColumnModel().getColumn(5).setCellRenderer(numeric);
        }
        DefaultTableCellRenderer dateRenderer = new DefaultTableCellRenderer() {
            private final SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm");
            @Override
            protected void setValue(Object value) {
                setText(value instanceof java.util.Date ? format.format(value)
                        : value == null ? "—" : value.toString());
            }
        };
        if (tblRiwayatDistribusi.getColumnModel().getColumnCount() > 1) {
            tblRiwayatDistribusi.getColumnModel().getColumn(1).setCellRenderer(dateRenderer);
        }
        tblRiwayatDistribusi.setRowSorter(new TableRowSorter<>(tblRiwayatDistribusi.getModel()));
    }

    private void loadDataRiwayatDistribusi() {
        DefaultTableModel model = new DefaultTableModel(new Object[][]{}, new String[]{
            "ID Distribusi", "Tanggal Pengiriman", "Asal Penyimpanan", "Jenis BBM",
            "Jumlah Dikirim", "Jumlah Aktual", "Status Distribusi", "Keterangan"
        }) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                switch (columnIndex) {
                    case 0: return Integer.class;
                    case 1: return java.sql.Timestamp.class;
                    case 4:
                    case 5: return BigDecimal.class;
                    default: return String.class;
                }
            }
        };
        try {
            for (model.RiwayatDistribusiModel data
                    : laporanController.getRiwayatDistribusiSpbu(Session.idSpbu)) {
                model.addRow(new Object[]{
                    data.idDistribusi(),
                    data.tanggalDistribusi(),
                    data.namaLokasi(),
                    data.namaBbm(),
                    data.jumlahDikirim(),
                    data.jumlahAktual(),
                    data.statusDistribusi(),
                    data.keterangan() != null && !data.keterangan().isBlank()
                            ? data.keterangan() : "Belum ada konfirmasi"
                });
            }
            tblRiwayatDistribusi.setModel(model);
            konfigurasiTabelRiwayat();
        } catch (java.sql.SQLException | IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this,
                    "Gagal memuat riwayat distribusi: " + e.getMessage(),
                    "Kesalahan Database", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * @param args the command line arguments
     */
        public static void main(String args[]) {            
            //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
            /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
             */
            try {
                for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                    if ("Nimbus".equals(info.getName())) {
                        javax.swing.UIManager.setLookAndFeel(info.getClassName());
                        break;
                    }
                }
            } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
                logger.log(java.util.logging.Level.SEVERE, null, ex);
            }
            //</editor-fold>
           
            java.awt.EventQueue.invokeLater(() -> new RiwayatDistribusiSPBU().setVisible(true));
        }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton dashboard1;
    private javax.swing.JPanel header;
    private javax.swing.JFrame jFrame1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JButton konfirmasi_pengiriman_bbm;
    private javax.swing.JButton lihat_pengiriman;
    private javax.swing.JButton lihat_stok;
    private javax.swing.JButton logout;
    private javax.swing.JLabel nama;
    private javax.swing.JPanel navbar;
    private javax.swing.JButton riwayat_distribusi;
    private javax.swing.JLabel sebagai;
    private javax.swing.JTable tblRiwayatDistribusi;
    // End of variables declaration//GEN-END:variables
}
