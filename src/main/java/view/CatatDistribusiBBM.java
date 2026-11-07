package view;

import controller.OperasionalController;
import java.math.BigDecimal;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import java.util.List;

public class CatatDistribusiBBM extends javax.swing.JFrame {

    private final OperasionalController operasionalController = new OperasionalController();
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(CatatDistribusiBBM.class.getName());

    public CatatDistribusiBBM() {
        initComponents();
        TampilanTabel.rapikanSemua(this);
        setSize(1366, 768);
        setMinimumSize(new java.awt.Dimension(1100, 700));

        setLocationRelativeTo(null);
        
        tampilIdentitas();
        muatJenisBBM();
    }

    private void tampilIdentitas() {
        nama.setText(Session.namaLengkap == null || Session.namaLengkap.isBlank()
                ? "Pengguna" : Session.namaLengkap);
        String peranAktif = Session.peran == null || Session.peran.isBlank()
                ? "Pengguna" : Session.peran;
        String lokasiAktif = Session.lokasi == null || Session.lokasi.isBlank()
                ? "" : " · " + Session.lokasi;
        sebagai.setText(peranAktif + lokasiAktif);
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
                simpanDistribusi.setEnabled(false);
            } else {
                jenisBBM.setEnabled(true);
                simpanDistribusi.setEnabled(true);
            }
        } catch (SQLException | SecurityException ex) {
            jenisBBM.addItem("Gagal memuat jenis BBM");
            jenisBBM.setEnabled(false);
            simpanDistribusi.setEnabled(false);
            JOptionPane.showMessageDialog(this, ex.getMessage(),
                    "Gagal Memuat Jenis BBM", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void simpanDistribusiBBM() {
        String namaSpbuInput = spbuTujuan.getText().trim();
        String namaBbmInput = jenisBBM.getSelectedItem() == null ? "" : jenisBBM.getSelectedItem().toString().trim();
        String jumlahStr = jumlahDiterima.getText().trim();
        if (namaSpbuInput.isEmpty() || namaBbmInput.isEmpty() || jumlahStr.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "SPBU tujuan, jenis BBM, dan jumlah dikirim wajib diisi.",
                    "Input Belum Lengkap", JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            BigDecimal jumlah = new BigDecimal(jumlahStr.replace(",", "."));
            operasionalController.catatDistribusi(Session.idPengguna,
                    Session.idLokasiPenyimpanan, namaSpbuInput, namaBbmInput, jumlah);
            JOptionPane.showMessageDialog(this,
                    "Distribusi berhasil dicatat. Stok penyimpanan telah diperbarui.",
                    "Berhasil", JOptionPane.INFORMATION_MESSAGE);
            spbuTujuan.setText("");
            if (jenisBBM.getItemCount() > 0) jenisBBM.setSelectedIndex(0);
            jumlahDiterima.setText("");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Jumlah harus berupa angka yang valid.",
                    "Input Tidak Valid", JOptionPane.WARNING_MESSAGE);
        } catch (IllegalArgumentException | SQLException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(),
                    "Distribusi Gagal", JOptionPane.ERROR_MESSAGE);
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
        Logout = new javax.swing.JButton();
        header = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        nama = new javax.swing.JLabel();
        sebagai = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        distribusi_terbaru = new javax.swing.JPanel();
        label_lokasi = new javax.swing.JLabel();
        spbuTujuan = new javax.swing.JTextField();
        jumlahDiterima = new javax.swing.JTextField();
        simpanDistribusi = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jenisBBM = new javax.swing.JComboBox();
        jLabel8 = new javax.swing.JLabel();

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

        Logout.setText("Logout");
        Logout.addActionListener(this::LogoutActionPerformed);

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
                            .addComponent(Logout)
                            .addGroup(navbarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(catat_penerimaan_bbm, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(dashboard1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(catat_distribusi_bbm, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(stok_lokasi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(riwayat_penerimaan, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(riwayat_distribusi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                .addContainerGap(48, Short.MAX_VALUE))
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 177, Short.MAX_VALUE)
                .addComponent(Logout)
                .addGap(24, 24, 24))
        );

        header.setBackground(new java.awt.Color(255, 255, 255));
        header.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

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
                .addContainerGap(146, Short.MAX_VALUE)
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

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel7.setText("CATAT DISTRIBUSI BBM");

        javax.swing.GroupLayout headerLayout = new javax.swing.GroupLayout(header);
        header.setLayout(headerLayout);
        headerLayout.setHorizontalGroup(
            headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, headerLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 306, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(200, 200, 200))
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

        distribusi_terbaru.setBackground(new java.awt.Color(255, 255, 255));
        distribusi_terbaru.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        label_lokasi.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        label_lokasi.setText("Catat Distribusi BBM dari Gudang Utama Samarinda");

        spbuTujuan.addActionListener(this::spbuTujuanActionPerformed);

        jumlahDiterima.addActionListener(this::jumlahDiterimaActionPerformed);

        simpanDistribusi.setBackground(new java.awt.Color(0, 102, 51));
        simpanDistribusi.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        simpanDistribusi.setForeground(new java.awt.Color(255, 255, 255));
        simpanDistribusi.setText("Simpan Distribusi");
        simpanDistribusi.addActionListener(this::simpanDistribusiActionPerformed);

        jLabel4.setBackground(new java.awt.Color(204, 204, 204));
        jLabel4.setForeground(new java.awt.Color(204, 204, 204));
        jLabel4.setText("Jenis BBM");

        jLabel5.setForeground(new java.awt.Color(204, 204, 204));
        jLabel5.setText("Jumlah dikirim (Liter)");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(204, 204, 204));
        jLabel6.setText("Status awal MENUNGGU_KONFIRMASI. Petugas SPBU mengonfirmasi jumlah aktual. Jika ada selisih, petugas penyimpanan menindaklanjuti melalui pemeriksaan. Stok lokasi berkurang saat distribusi dicatat.");

        jenisBBM.addActionListener(this::jenisBBMActionPerformed);

        jLabel8.setForeground(new java.awt.Color(204, 204, 204));
        jLabel8.setText("SPBU Tujuan");

        javax.swing.GroupLayout distribusi_terbaruLayout = new javax.swing.GroupLayout(distribusi_terbaru);
        distribusi_terbaru.setLayout(distribusi_terbaruLayout);
        distribusi_terbaruLayout.setHorizontalGroup(
            distribusi_terbaruLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(distribusi_terbaruLayout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(distribusi_terbaruLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(label_lokasi)
                    .addGroup(distribusi_terbaruLayout.createSequentialGroup()
                        .addGroup(distribusi_terbaruLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8)
                            .addComponent(spbuTujuan, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(distribusi_terbaruLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jenisBBM, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(distribusi_terbaruLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addGroup(distribusi_terbaruLayout.createSequentialGroup()
                                .addComponent(jumlahDiterima, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(simpanDistribusi, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 1098, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        distribusi_terbaruLayout.setVerticalGroup(
            distribusi_terbaruLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(distribusi_terbaruLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(label_lokasi)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(distribusi_terbaruLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(distribusi_terbaruLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(spbuTujuan, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jenisBBM, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jumlahDiterima, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(simpanDistribusi, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(14, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(navbar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(distribusi_terbaru, javax.swing.GroupLayout.PREFERRED_SIZE, 993, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(header, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(navbar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addComponent(header, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19)
                .addComponent(distribusi_terbaru, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(435, Short.MAX_VALUE))
        );

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
        jenisBBM.requestFocusInWindow();
    }//GEN-LAST:event_jumlahDiterimaActionPerformed

    private void jenisBBMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jenisBBMActionPerformed
        jumlahDiterima.requestFocusInWindow();
    }//GEN-LAST:event_jenisBBMActionPerformed

    private void spbuTujuanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_spbuTujuanActionPerformed
        simpanDistribusiBBM();
    }//GEN-LAST:event_spbuTujuanActionPerformed

    private void simpanDistribusiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_simpanDistribusiActionPerformed
        simpanDistribusiBBM();
    }//GEN-LAST:event_simpanDistribusiActionPerformed

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

        java.awt.EventQueue.invokeLater(() -> new CatatDistribusiBBM().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Logout;
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
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JComboBox jenisBBM;
    private javax.swing.JTextField jumlahDiterima;
    private javax.swing.JLabel label_lokasi;
    private javax.swing.JLabel nama;
    private javax.swing.JPanel navbar;
    private javax.swing.JButton riwayat_distribusi;
    private javax.swing.JButton riwayat_penerimaan;
    private javax.swing.JLabel sebagai;
    private javax.swing.JButton simpanDistribusi;
    private javax.swing.JTextField spbuTujuan;
    private javax.swing.JButton stok_lokasi;
    // End of variables declaration//GEN-END:variables
}
