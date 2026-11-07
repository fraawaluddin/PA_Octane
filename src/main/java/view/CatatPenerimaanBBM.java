package view;

import controller.OperasionalController;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import javax.swing.JOptionPane;

public class CatatPenerimaanBBM extends javax.swing.JFrame {

    private final OperasionalController operasionalController = new OperasionalController();
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(CatatPenerimaanBBM.class.getName());
   
    public CatatPenerimaanBBM() {
        initComponents();
        TampilanTabel.rapikanSemua(this);
        muatJenisBBM();
        setSize(1366, 768);
        setMinimumSize(new java.awt.Dimension(1100, 700));

        setLocationRelativeTo(null);
        tampilIdentitas();
    }

    private void tampilIdentitas() {
        nama.setText(Session.namaLengkap);
        sebagai.setText(Session.peran + " · " + Session.lokasi);
    }

    private void muatJenisBBM() {
        jenisBBM.removeAllItems();
        try {
            List<String> daftar = operasionalController.getJenisBbmAktif();
            for (String namaBbm : daftar) {
                jenisBBM.addItem(namaBbm);
            }
            if (jenisBBM.getItemCount() == 0) {
                jenisBBM.addItem("Tidak ada jenis BBM aktif");
                jenisBBM.setEnabled(false);
                simpan_penerimaan.setEnabled(false);
            } else {
                jenisBBM.setEnabled(true);
                simpan_penerimaan.setEnabled(true);
            }
        } catch (SQLException | SecurityException ex) {
            jenisBBM.addItem("Gagal memuat jenis BBM");
            jenisBBM.setEnabled(false);
            simpan_penerimaan.setEnabled(false);
            JOptionPane.showMessageDialog(this, ex.getMessage(),
                    "Gagal Memuat Jenis BBM", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void simpanPenerimaanBBM() {
        String namaBbmInput = jenisBBM.getSelectedItem() == null ? "" : jenisBBM.getSelectedItem().toString().trim();
        String jumlahStr = jumlahDiterima.getText().trim();
        if (namaBbmInput.isEmpty() || jumlahStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Jenis BBM dan jumlah diterima wajib diisi.",
                    "Input Belum Lengkap", JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            BigDecimal jumlah = new BigDecimal(jumlahStr.replace(",", "."));
            operasionalController.catatPenerimaan(Session.idPengguna,
                    Session.idLokasiPenyimpanan, namaBbmInput, jumlah);
            JOptionPane.showMessageDialog(this,
                    "Penerimaan BBM berhasil dicatat dan stok penyimpanan diperbarui.",
                    "Berhasil", JOptionPane.INFORMATION_MESSAGE);
            if (jenisBBM.getItemCount() > 0) jenisBBM.setSelectedIndex(0);
            jumlahDiterima.setText("");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Jumlah harus berupa angka yang valid.",
                    "Input Tidak Valid", JOptionPane.WARNING_MESSAGE);
        } catch (IllegalArgumentException | SQLException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(),
                    "Penerimaan Gagal", JOptionPane.ERROR_MESSAGE);
        }
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
        jPanel1 = new javax.swing.JPanel();
        nama = new javax.swing.JLabel();
        sebagai = new javax.swing.JLabel();
        distribusi_terbaru = new javax.swing.JPanel();
        label_lokasi = new javax.swing.JLabel();
        jenisBBM = new javax.swing.JComboBox();
        jumlahDiterima = new javax.swing.JTextField();
        simpan_penerimaan = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();

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
        getContentPane().setLayout(null);

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
                    .addGroup(navbarLayout.createSequentialGroup()
                        .addGap(83, 83, 83)
                        .addComponent(jLabel1))
                    .addGroup(navbarLayout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addGroup(navbarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(Logout1)
                            .addGroup(navbarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(catat_penerimaan_bbm, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(dashboard1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(catat_distribusi_bbm, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(stok_lokasi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(riwayat_penerimaan, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(riwayat_distribusi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                .addContainerGap(12, Short.MAX_VALUE))
        );
        navbarLayout.setVerticalGroup(
            navbarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(navbarLayout.createSequentialGroup()
                .addGap(62, 62, 62)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addGap(44, 44, 44)
                .addComponent(dashboard1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(45, 45, 45)
                .addComponent(catat_penerimaan_bbm, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(catat_distribusi_bbm, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(stok_lokasi, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(riwayat_penerimaan, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(riwayat_distribusi, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 263, Short.MAX_VALUE)
                .addComponent(Logout1)
                .addGap(23, 23, 23))
        );

        getContentPane().add(navbar);
        navbar.setBounds(0, 0, 244, 800);

        header.setBackground(new java.awt.Color(255, 255, 255));
        header.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel7.setText("CATAT PENERIMAAN BBM");

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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 502, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        headerLayout.setVerticalGroup(
            headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(headerLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        getContentPane().add(header);
        header.setBounds(256, 6, 1070, 104);

        distribusi_terbaru.setBackground(new java.awt.Color(255, 255, 255));
        distribusi_terbaru.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        label_lokasi.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        label_lokasi.setText("Catat Penerimaan BBM di Gudang Utama Samarinda");

        jenisBBM.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jenisBBM.setPreferredSize(new java.awt.Dimension(420, 40));
        jenisBBM.addActionListener(this::jenisBBMActionPerformed);

        jumlahDiterima.addActionListener(this::jumlahDiterimaActionPerformed);

        simpan_penerimaan.setBackground(new java.awt.Color(0, 102, 51));
        simpan_penerimaan.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        simpan_penerimaan.setForeground(new java.awt.Color(255, 255, 255));
        simpan_penerimaan.setText("Simpan Penerimaan");
        simpan_penerimaan.addActionListener(this::simpan_penerimaanActionPerformed);

        jLabel4.setBackground(new java.awt.Color(204, 204, 204));
        jLabel4.setForeground(new java.awt.Color(204, 204, 204));
        jLabel4.setText("Jenis BBM");

        jLabel5.setForeground(new java.awt.Color(204, 204, 204));
        jLabel5.setText("Jumlah diterima ( Liter )");

        jLabel6.setForeground(new java.awt.Color(204, 204, 204));
        jLabel6.setText("Tanggal dan petugas dicatat otomatis. Stok lokasi bertambah sesuai jumlah.");

        javax.swing.GroupLayout distribusi_terbaruLayout = new javax.swing.GroupLayout(distribusi_terbaru);
        distribusi_terbaru.setLayout(distribusi_terbaruLayout);
        distribusi_terbaruLayout.setHorizontalGroup(
            distribusi_terbaruLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(distribusi_terbaruLayout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(distribusi_terbaruLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(distribusi_terbaruLayout.createSequentialGroup()
                        .addComponent(jenisBBM, javax.swing.GroupLayout.PREFERRED_SIZE, 420, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jumlahDiterima, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(simpan_penerimaan, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 31, Short.MAX_VALUE))
                    .addGroup(distribusi_terbaruLayout.createSequentialGroup()
                        .addGroup(distribusi_terbaruLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addGroup(distribusi_terbaruLayout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addGap(349, 349, 349)
                                .addComponent(jLabel5))
                            .addComponent(label_lokasi))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        distribusi_terbaruLayout.setVerticalGroup(
            distribusi_terbaruLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(distribusi_terbaruLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(label_lokasi)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(distribusi_terbaruLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(distribusi_terbaruLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jenisBBM, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jumlahDiterima, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(simpan_penerimaan, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel6)
                .addContainerGap(14, Short.MAX_VALUE))
        );

        getContentPane().add(distribusi_terbaru);
        distribusi_terbaru.setBounds(277, 129, 1020, 158);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void dashboard1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dashboard1ActionPerformed
        new DashboardPenyimpanan().setVisible(true);
        this.dispose();
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

    private void jumlahDiterimaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jumlahDiterimaActionPerformed
        simpanPenerimaanBBM();
    }//GEN-LAST:event_jumlahDiterimaActionPerformed

    private void jenisBBMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jenisBBMActionPerformed
        jumlahDiterima.requestFocusInWindow();
    }//GEN-LAST:event_jenisBBMActionPerformed

    private void simpan_penerimaanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_simpan_penerimaanActionPerformed
        simpanPenerimaanBBM();
    }//GEN-LAST:event_simpan_penerimaanActionPerformed

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
        java.awt.EventQueue.invokeLater(() -> new CatatPenerimaanBBM().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Logout1;
    private javax.swing.JButton catat_distribusi_bbm;
    private javax.swing.JButton catat_penerimaan_bbm;
    private javax.swing.JButton dashboard1;
    private javax.swing.JPanel distribusi_terbaru;
    private javax.swing.JPanel header;
    private javax.swing.JFrame jFrame1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JComboBox jenisBBM;
    private javax.swing.JTextField jumlahDiterima;
    private javax.swing.JLabel label_lokasi;
    private javax.swing.JLabel nama;
    private javax.swing.JPanel navbar;
    private javax.swing.JButton riwayat_distribusi;
    private javax.swing.JButton riwayat_penerimaan;
    private javax.swing.JLabel sebagai;
    private javax.swing.JButton simpan_penerimaan;
    private javax.swing.JButton stok_lokasi;
    // End of variables declaration//GEN-END:variables
}
