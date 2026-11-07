package view;

import controller.KonfirmasiPenerimaanController;
import java.sql.SQLException;
import java.util.List;
import model.PengirimanMenungguModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import javax.swing.event.ListSelectionEvent;

  

    public class KonfirmasiPenerimaanBBM extends javax.swing.JFrame {
        
        private final KonfirmasiPenerimaanController konfirmasiController =
                new KonfirmasiPenerimaanController();
        private int idDistribusiAktif = -1;
        private BigDecimal jumlahDikirimAktif = BigDecimal.ZERO;

        private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(KonfirmasiPenerimaanBBM.class.getName());
        
       public KonfirmasiPenerimaanBBM() {
            initComponents();
        TampilanTabel.rapikanSemua(this);

            nama.setText(Session.namaLengkap);
            sebagai.setText("Petugas SPBU");

            setExtendedState(javax.swing.JFrame.NORMAL);
        setSize(1366, 768);
        setLocationRelativeTo(null);
        setResizable(false);
            setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

            aturFormAwal();
            konfigurasiTabelPengiriman();
            loadPengirimanMenungguKonfirmasi();
            tblPengirimanMenunggu.getSelectionModel().addListSelectionListener(
                    (ListSelectionEvent e) -> {
                        if (!e.getValueIsAdjusting()) {
                            pilihPengirimanDariTabel();
                        }
                    });
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
        lokasi_penyimpanan1 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        scrollPengiriman = new javax.swing.JScrollPane();
        tblPengirimanMenunggu = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        lblDari = new javax.swing.JLabel();
        lblBBM = new javax.swing.JLabel();
        lblJumlahDikirim = new javax.swing.JLabel();
        lblJumlahAktual = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        btnKonfirmasi = new javax.swing.JButton();
        txtAsalPenyimpanan = new javax.swing.JTextField();
        txtJenisBBM = new javax.swing.JTextField();
        txtJumlahDikirim = new javax.swing.JTextField();
        txtJumlahAktual = new javax.swing.JTextField();
        txtKeterangan = new javax.swing.JTextField();

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
        setPreferredSize(new java.awt.Dimension(1435, 800));
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
        riwayat_distribusi.setText("Riwayat Distribusi");
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
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, navbarLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(navbarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, navbarLayout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, navbarLayout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(87, 87, 87))))
            .addGroup(navbarLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(navbarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(logout)
                    .addComponent(konfirmasi_pengiriman_bbm)
                    .addComponent(dashboard1, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lihat_pengiriman, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lihat_stok, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(riwayat_distribusi, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(25, Short.MAX_VALUE))
        );
        navbarLayout.setVerticalGroup(
            navbarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(navbarLayout.createSequentialGroup()
                .addGap(62, 62, 62)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addGap(47, 47, 47)
                .addComponent(dashboard1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(46, 46, 46)
                .addComponent(konfirmasi_pengiriman_bbm, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lihat_pengiriman, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lihat_stok, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(riwayat_distribusi, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(logout)
                .addGap(20, 20, 20))
        );

        header.setBackground(new java.awt.Color(255, 255, 255));
        header.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel7.setText("Konfirmasi Penerimaan BBM");

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
                .addContainerGap(54, Short.MAX_VALUE)
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
                .addContainerGap()
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 370, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 498, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39))
        );
        headerLayout.setVerticalGroup(
            headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(headerLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(25, Short.MAX_VALUE))
        );

        lokasi_penyimpanan1.setBackground(new java.awt.Color(255, 255, 255));
        lokasi_penyimpanan1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 102, 0), 1, true));
        lokasi_penyimpanan1.setPreferredSize(new java.awt.Dimension(900, 270));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel6.setText("Pilih Pengiriman yang Menunggu Konfirmasi");

        scrollPengiriman.setPreferredSize(new java.awt.Dimension(460, 80));

        tblPengirimanMenunggu.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID Distribusi", "Tanggal", "Asal Penyimpanan", "Jenis BBM", "Jumlah Dikirim (L)", "Status"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Object.class, java.lang.String.class, java.lang.String.class, java.math.BigDecimal.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblPengirimanMenunggu.getTableHeader().setReorderingAllowed(false);
        scrollPengiriman.setViewportView(tblPengirimanMenunggu);

        javax.swing.GroupLayout lokasi_penyimpanan1Layout = new javax.swing.GroupLayout(lokasi_penyimpanan1);
        lokasi_penyimpanan1.setLayout(lokasi_penyimpanan1Layout);
        lokasi_penyimpanan1Layout.setHorizontalGroup(
            lokasi_penyimpanan1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(lokasi_penyimpanan1Layout.createSequentialGroup()
                .addGroup(lokasi_penyimpanan1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(lokasi_penyimpanan1Layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(jLabel6))
                    .addGroup(lokasi_penyimpanan1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(scrollPengiriman, javax.swing.GroupLayout.PREFERRED_SIZE, 991, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(18, Short.MAX_VALUE))
        );
        lokasi_penyimpanan1Layout.setVerticalGroup(
            lokasi_penyimpanan1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(lokasi_penyimpanan1Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scrollPengiriman, javax.swing.GroupLayout.DEFAULT_SIZE, 302, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel8.setText("Form Konfirmasi");

        lblDari.setText("Dari");

        lblBBM.setText("BBM");

        lblJumlahDikirim.setText("Jumlah Dikirim");

        lblJumlahAktual.setText("Jumlah Aktual Diterima");

        jLabel10.setText("Keterangan (wajib diisi)");

        btnKonfirmasi.setBackground(new java.awt.Color(204, 102, 0));
        btnKonfirmasi.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnKonfirmasi.setForeground(new java.awt.Color(255, 255, 255));
        btnKonfirmasi.setText("Konfirmasi Penerimaan");
        btnKonfirmasi.addActionListener(this::btnKonfirmasiActionPerformed);

        txtAsalPenyimpanan.addActionListener(this::txtAsalPenyimpananActionPerformed);

        txtJumlahAktual.addActionListener(this::txtJumlahAktualActionPerformed);

        txtKeterangan.addActionListener(this::txtKeteranganActionPerformed);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblDari)
                                    .addComponent(txtAsalPenyimpanan, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGap(38, 38, 38)
                                        .addComponent(lblBBM))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtJenisBBM, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtJumlahDikirim, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblJumlahDikirim))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblJumlahAktual)
                                    .addComponent(txtJumlahAktual, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(txtKeterangan, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 698, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnKonfirmasi, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(257, 257, 257))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8)
                            .addComponent(jLabel10))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jLabel8)
                .addGap(14, 14, 14)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDari)
                    .addComponent(lblBBM)
                    .addComponent(lblJumlahDikirim)
                    .addComponent(lblJumlahAktual))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtAsalPenyimpanan, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtJenisBBM, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtJumlahDikirim, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtJumlahAktual, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jLabel10)
                .addGap(2, 2, 2)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtKeterangan, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnKonfirmasi, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(14, 14, 14))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(navbar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lokasi_penyimpanan1, javax.swing.GroupLayout.DEFAULT_SIZE, 1017, Short.MAX_VALUE)
                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 1017, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(header, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(40, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(header, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lokasi_penyimpanan1, javax.swing.GroupLayout.PREFERRED_SIZE, 355, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 33, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(navbar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void dashboard1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dashboard1ActionPerformed
        new DashboardSPBU().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_dashboard1ActionPerformed

    private void konfirmasi_pengiriman_bbmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_konfirmasi_pengiriman_bbmActionPerformed
       
    }//GEN-LAST:event_konfirmasi_pengiriman_bbmActionPerformed

    private void logoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logoutActionPerformed
        int pilihan = javax.swing.JOptionPane.showConfirmDialog(
            this,
            "Apakah yakin ingin keluar dari akun?",
            "Konfirmasi Logout",
            javax.swing.JOptionPane.YES_NO_OPTION,
            javax.swing.JOptionPane.QUESTION_MESSAGE
        );
        if (pilihan == javax.swing.JOptionPane.YES_OPTION) {
            Session.clear();
            new Login().setVisible(true);
            this.dispose();
        }
    }//GEN-LAST:event_logoutActionPerformed

    private void lihat_pengirimanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lihat_pengirimanActionPerformed
        new LihatPengirimanSPBU().setVisible(true);
        this.dispose();  
    }//GEN-LAST:event_lihat_pengirimanActionPerformed

    private void lihat_stokActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lihat_stokActionPerformed
        new Stok_SPBU().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_lihat_stokActionPerformed

    private void riwayat_distribusiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_riwayat_distribusiActionPerformed
        new RiwayatDistribusiSPBU().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_riwayat_distribusiActionPerformed

    private void btnKonfirmasiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKonfirmasiActionPerformed
        String jumlahText = txtJumlahAktual.getText().trim();
        String keterangan = txtKeterangan.getText().trim();

        if (idDistribusiAktif == -1) {
            JOptionPane.showMessageDialog(
                this,
                "Tidak ada pengiriman yang menunggu konfirmasi.",
                "Informasi",
                JOptionPane.INFORMATION_MESSAGE
            );
            return;
        }

        if (jumlahText.isEmpty()) {
            JOptionPane.showMessageDialog(
                this,
                "Jumlah aktual diterima wajib diisi.",
                "Validasi",
                JOptionPane.WARNING_MESSAGE
            );
            txtJumlahAktual.requestFocus();
            return;
        }

        if (keterangan.isEmpty()) {
            JOptionPane.showMessageDialog(
                this,
                "Keterangan wajib diisi.",
                "Validasi",
                JOptionPane.WARNING_MESSAGE
            );
            txtKeterangan.requestFocus();
            return;
        }

        BigDecimal jumlahAktual;

        try {
            jumlahAktual = new BigDecimal(jumlahText.replace(",", "."));

            if (jumlahAktual.compareTo(BigDecimal.ZERO) <= 0) {
                JOptionPane.showMessageDialog(
                    this,
                    "Jumlah aktual harus lebih besar dari 0.",
                    "Validasi",
                    JOptionPane.WARNING_MESSAGE
                );
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(
                this,
                "Jumlah aktual harus berupa angka yang valid.",
                "Validasi",
                JOptionPane.WARNING_MESSAGE
            );
            txtJumlahAktual.requestFocus();
            return;
        }

        int pilihan = JOptionPane.showConfirmDialog(
                this,
                "Simpan konfirmasi penerimaan untuk distribusi ID " + idDistribusiAktif + "?",
                "Konfirmasi Penerimaan",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );
        if (pilihan != JOptionPane.YES_OPTION) {
            return;
        }

        try {
            String statusBaru = konfirmasiController.konfirmasi(
                    idDistribusiAktif,
                    Session.idSpbu,
                    Session.idPengguna,
                    jumlahAktual,
                    keterangan
            );

            String pesan = "SELESAI".equals(statusBaru)
                    ? "Konfirmasi berhasil disimpan. Stok SPBU telah diperbarui."
                    : "Konfirmasi berhasil disimpan, tetapi terdapat selisih jumlah. "
                      + "Distribusi ditandai PERLU_PEMERIKSAAN dan stok SPBU belum ditambahkan.";

            JOptionPane.showMessageDialog(
                    this,
                    pesan,
                    "Konfirmasi Berhasil",
                    JOptionPane.INFORMATION_MESSAGE
            );
            loadPengirimanMenungguKonfirmasi();

        } catch (IllegalArgumentException | SQLException e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Gagal menyimpan konfirmasi:\n" + e.getMessage(),
                    "Kesalahan",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }//GEN-LAST:event_btnKonfirmasiActionPerformed

    private void txtAsalPenyimpananActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtAsalPenyimpananActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAsalPenyimpananActionPerformed

    private void txtKeteranganActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtKeteranganActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtKeteranganActionPerformed

    private void txtJumlahAktualActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtJumlahAktualActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtJumlahAktualActionPerformed

    private void konfigurasiTabelPengiriman() {
        tblPengirimanMenunggu.setModel(new DefaultTableModel(
                new Object[][]{},
                new String[]{"ID Distribusi", "Tanggal", "Asal Penyimpanan",
                    "Jenis BBM", "Jumlah Dikirim (L)", "Status"}
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });
        tblPengirimanMenunggu.setSelectionMode(
                javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tblPengirimanMenunggu.setRowHeight(30);
        tblPengirimanMenunggu.setFillsViewportHeight(true);
        tblPengirimanMenunggu.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        tblPengirimanMenunggu.getTableHeader().setReorderingAllowed(false);
        tblPengirimanMenunggu.getTableHeader().setFont(
                new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12));
        tblPengirimanMenunggu.setFont(
                new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 12));
        tblPengirimanMenunggu.setSelectionBackground(new java.awt.Color(255, 230, 204));
        tblPengirimanMenunggu.setSelectionForeground(new java.awt.Color(40, 40, 40));
    }

    private void aturFormAwal() {
        txtAsalPenyimpanan.setEditable(false);
        txtJenisBBM.setEditable(false);
        txtJumlahDikirim.setEditable(false);
        txtJumlahAktual.setEditable(false);
        txtKeterangan.setEditable(false);

        txtAsalPenyimpanan.setText("");
        txtJenisBBM.setText("");
        txtJumlahDikirim.setText("");
        txtJumlahAktual.setText("");
        txtKeterangan.setText("");

        btnKonfirmasi.setEnabled(false);

        tblPengirimanMenunggu.setSelectionMode(
                javax.swing.ListSelectionModel.SINGLE_SELECTION
        );
    }
    
    private void loadPengirimanMenungguKonfirmasi() {
        DefaultTableModel model = (DefaultTableModel) tblPengirimanMenunggu.getModel();
        model.setRowCount(0);
        idDistribusiAktif = -1;
        jumlahDikirimAktif = BigDecimal.ZERO;
        aturFormAwal();

        try {
            List<PengirimanMenungguModel> daftar =
                    konfirmasiController.getPengirimanMenunggu(Session.idSpbu);
            for (PengirimanMenungguModel item : daftar) {
                model.addRow(new Object[]{
                    item.getIdDistribusi(),
                    new SimpleDateFormat("dd-MM-yyyy HH:mm").format(item.getTanggalDistribusi()),
                    item.getAsalPenyimpanan(), item.getNamaBbm(),
                    item.getJumlahDikirim(), item.getStatusDistribusi()
                });
            }
        } catch (SQLException | IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this,
                    "Gagal memuat pengiriman: " + e.getMessage(),
                    "Kesalahan Database", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void pilihPengirimanDariTabel() {
        int baris = tblPengirimanMenunggu.getSelectedRow();
        if (baris == -1) {
            idDistribusiAktif = -1;
            jumlahDikirimAktif = BigDecimal.ZERO;
            aturFormAwal();
            return;
        }

        int modelRow = tblPengirimanMenunggu.convertRowIndexToModel(baris);
        Object nilaiId = tblPengirimanMenunggu.getModel().getValueAt(modelRow, 0);
        if (nilaiId == null) return;

        idDistribusiAktif = Integer.parseInt(nilaiId.toString());
        try {
            PengirimanMenungguModel detail = konfirmasiController.getDetailPengiriman(
                    idDistribusiAktif, Session.idSpbu);
            if (detail == null) {
                JOptionPane.showMessageDialog(this,
                        "Pengiriman tidak ditemukan atau sudah berubah status.",
                        "Informasi", JOptionPane.WARNING_MESSAGE);
                loadPengirimanMenungguKonfirmasi();
                return;
            }

            jumlahDikirimAktif = detail.getJumlahDikirim();
            txtAsalPenyimpanan.setText(detail.getAsalPenyimpanan());
            txtJenisBBM.setText(detail.getNamaBbm());
            txtJumlahDikirim.setText(jumlahDikirimAktif.toPlainString() + " Liter");
            txtJumlahAktual.setText("");
            txtKeterangan.setText("");
            txtJumlahAktual.setEditable(true);
            txtKeterangan.setEditable(true);
            btnKonfirmasi.setEnabled(true);
            txtJumlahAktual.requestFocus();
        } catch (SQLException | IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this,
                    "Gagal mengambil detail pengiriman: " + e.getMessage(),
                    "Kesalahan Database", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {        
        
        java.awt.EventQueue.invokeLater(() -> new KonfirmasiPenerimaanBBM().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnKonfirmasi;
    private javax.swing.JButton dashboard1;
    private javax.swing.JPanel header;
    private javax.swing.JFrame jFrame1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JButton konfirmasi_pengiriman_bbm;
    private javax.swing.JLabel lblBBM;
    private javax.swing.JLabel lblDari;
    private javax.swing.JLabel lblJumlahAktual;
    private javax.swing.JLabel lblJumlahDikirim;
    private javax.swing.JButton lihat_pengiriman;
    private javax.swing.JButton lihat_stok;
    private javax.swing.JButton logout;
    private javax.swing.JPanel lokasi_penyimpanan1;
    private javax.swing.JLabel nama;
    private javax.swing.JPanel navbar;
    private javax.swing.JButton riwayat_distribusi;
    private javax.swing.JScrollPane scrollPengiriman;
    private javax.swing.JLabel sebagai;
    private javax.swing.JTable tblPengirimanMenunggu;
    private javax.swing.JTextField txtAsalPenyimpanan;
    private javax.swing.JTextField txtJenisBBM;
    private javax.swing.JTextField txtJumlahAktual;
    private javax.swing.JTextField txtJumlahDikirim;
    private javax.swing.JTextField txtKeterangan;
    // End of variables declaration//GEN-END:variables
}
