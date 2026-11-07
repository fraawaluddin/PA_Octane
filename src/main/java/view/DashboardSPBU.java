package view;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.text.DecimalFormat;
import controller.DashboardSpbuController;
import controller.KonfirmasiPenerimaanController;
import controller.StokController;
import model.PengirimanMenungguModel;
import model.StokRingkasModel;
import java.math.BigDecimal;
import view.LihatPengirimanSPBU;

public class DashboardSPBU extends javax.swing.JFrame {
    
    private int idDistribusiAktif = -1;
    private final DashboardSpbuController dashboardController = new DashboardSpbuController();
    private final KonfirmasiPenerimaanController konfirmasiController = new KonfirmasiPenerimaanController();
    private final StokController stokController = new StokController();

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(DashboardSPBU.class.getName());
  
    public DashboardSPBU() {
        initComponents();
        TampilanTabel.rapikanSemua(this);
        muatStatistikSPBU();  
        muatStokSPBU();
        muatStatusPelatihan();
        
        txtDari.setEditable(false);
        txtDikirim.setEditable(false);

        loadPengirimanMenungguKonfirmasi();
        
        setExtendedState(javax.swing.JFrame.NORMAL);
        setSize(1366, 768);
        setLocationRelativeTo(null);
        setMinimumSize(new java.awt.Dimension(1100, 720));
        setResizable(false);
        setTitle("OCTANE - Dashboard Petugas SPBU");
        konfigurasiTabelStokSPBU();
        tampilIdentitas();
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
        total_lokasi_penyimpanan = new javax.swing.JPanel();
        lblMenungguKonfirmasi = new javax.swing.JLabel();
        lblJumlahMenunggu = new javax.swing.JLabel();
        spbu_terdaftar = new javax.swing.JPanel();
        lblPerluPemeriksaan = new javax.swing.JLabel();
        lblJumlahPemeriksaan = new javax.swing.JLabel();
        jenis_bbm = new javax.swing.JPanel();
        lblPengirimanSelesai = new javax.swing.JLabel();
        lblJumlahSelesai = new javax.swing.JLabel();
        lokasi_penyimpanan = new javax.swing.JPanel();
        lblJudulStok = new javax.swing.JLabel();
        lblTotalStok = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblStokSPBU = new javax.swing.JTable();
        status_distribusi = new javax.swing.JPanel();
        lblJudulPelatihan = new javax.swing.JLabel();
        lblStatusPelatihan = new javax.swing.JLabel();
        lokasi_penyimpanan1 = new javax.swing.JPanel();
        txtDari = new javax.swing.JTextField();
        txtDikirim = new javax.swing.JTextField();
        txtJumlahAktual = new javax.swing.JTextField();
        btnKonfirmasiPengiriman = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
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
        setResizable(false);
        getContentPane().setLayout(null);

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
            .addGroup(navbarLayout.createSequentialGroup()
                .addGap(0, 19, Short.MAX_VALUE)
                .addGroup(navbarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, navbarLayout.createSequentialGroup()
                        .addGroup(navbarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(navbarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(lihat_pengiriman, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lihat_stok, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(riwayat_distribusi, javax.swing.GroupLayout.DEFAULT_SIZE, 225, Short.MAX_VALUE))
                            .addComponent(dashboard1, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(konfirmasi_pengiriman_bbm))
                        .addGap(22, 22, 22))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, navbarLayout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(84, 84, 84))))
            .addGroup(navbarLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(logout)
                .addContainerGap(182, Short.MAX_VALUE))
        );
        navbarLayout.setVerticalGroup(
            navbarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(navbarLayout.createSequentialGroup()
                .addGap(62, 62, 62)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addGap(50, 50, 50)
                .addComponent(dashboard1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(43, 43, 43)
                .addComponent(konfirmasi_pengiriman_bbm, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lihat_pengiriman, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lihat_stok, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(riwayat_distribusi, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 247, Short.MAX_VALUE)
                .addComponent(logout)
                .addGap(27, 27, 27))
        );

        getContentPane().add(navbar);
        navbar.setBounds(0, 0, 270, 743);

        header.setBackground(new java.awt.Color(255, 255, 255));
        header.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel7.setText("Dashboard Petugas SPBU");

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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 480, Short.MAX_VALUE)
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
                .addContainerGap(19, Short.MAX_VALUE))
        );

        getContentPane().add(header);
        header.setBounds(281, 6, 1050, 104);

        total_lokasi_penyimpanan.setBackground(new java.awt.Color(255, 255, 255));
        total_lokasi_penyimpanan.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        lblMenungguKonfirmasi.setForeground(new java.awt.Color(102, 102, 102));
        lblMenungguKonfirmasi.setText("Menunggu Konfirmasi");

        lblJumlahMenunggu.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lblJumlahMenunggu.setText("0");

        javax.swing.GroupLayout total_lokasi_penyimpananLayout = new javax.swing.GroupLayout(total_lokasi_penyimpanan);
        total_lokasi_penyimpanan.setLayout(total_lokasi_penyimpananLayout);
        total_lokasi_penyimpananLayout.setHorizontalGroup(
            total_lokasi_penyimpananLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(total_lokasi_penyimpananLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(total_lokasi_penyimpananLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblMenungguKonfirmasi)
                    .addComponent(lblJumlahMenunggu, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(184, Short.MAX_VALUE))
        );
        total_lokasi_penyimpananLayout.setVerticalGroup(
            total_lokasi_penyimpananLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(total_lokasi_penyimpananLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblMenungguKonfirmasi)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblJumlahMenunggu, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(90, Short.MAX_VALUE))
        );

        getContentPane().add(total_lokasi_penyimpanan);
        total_lokasi_penyimpanan.setBounds(309, 128, 313, 146);

        spbu_terdaftar.setBackground(new java.awt.Color(255, 255, 255));
        spbu_terdaftar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        lblPerluPemeriksaan.setForeground(new java.awt.Color(102, 102, 102));
        lblPerluPemeriksaan.setText("Perlu Pemeriksaan");

        lblJumlahPemeriksaan.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lblJumlahPemeriksaan.setText("0");

        javax.swing.GroupLayout spbu_terdaftarLayout = new javax.swing.GroupLayout(spbu_terdaftar);
        spbu_terdaftar.setLayout(spbu_terdaftarLayout);
        spbu_terdaftarLayout.setHorizontalGroup(
            spbu_terdaftarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(spbu_terdaftarLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(spbu_terdaftarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblPerluPemeriksaan)
                    .addComponent(lblJumlahPemeriksaan, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(172, Short.MAX_VALUE))
        );
        spbu_terdaftarLayout.setVerticalGroup(
            spbu_terdaftarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(spbu_terdaftarLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblPerluPemeriksaan)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblJumlahPemeriksaan, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        getContentPane().add(spbu_terdaftar);
        spbu_terdaftar.setBounds(650, 130, 299, 146);

        jenis_bbm.setBackground(new java.awt.Color(255, 255, 255));
        jenis_bbm.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        lblPengirimanSelesai.setForeground(new java.awt.Color(102, 102, 102));
        lblPengirimanSelesai.setText("Pengiriman Selesai");

        lblJumlahSelesai.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lblJumlahSelesai.setText("0");

        javax.swing.GroupLayout jenis_bbmLayout = new javax.swing.GroupLayout(jenis_bbm);
        jenis_bbm.setLayout(jenis_bbmLayout);
        jenis_bbmLayout.setHorizontalGroup(
            jenis_bbmLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jenis_bbmLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jenis_bbmLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblPengirimanSelesai)
                    .addComponent(lblJumlahSelesai, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(213, Short.MAX_VALUE))
        );
        jenis_bbmLayout.setVerticalGroup(
            jenis_bbmLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jenis_bbmLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblPengirimanSelesai)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblJumlahSelesai, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        getContentPane().add(jenis_bbm);
        jenis_bbm.setBounds(980, 130, 340, 146);

        lokasi_penyimpanan.setBackground(new java.awt.Color(255, 255, 255));
        lokasi_penyimpanan.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        lblJudulStok.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblJudulStok.setText("Total Stok ");

        lblTotalStok.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblTotalStok.setText("Total: 0 liter");

        tblStokSPBU.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Jenis BBM", "Jumlah Stok"
            }
        ));
        jScrollPane1.setViewportView(tblStokSPBU);

        javax.swing.GroupLayout lokasi_penyimpananLayout = new javax.swing.GroupLayout(lokasi_penyimpanan);
        lokasi_penyimpanan.setLayout(lokasi_penyimpananLayout);
        lokasi_penyimpananLayout.setHorizontalGroup(
            lokasi_penyimpananLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(lokasi_penyimpananLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(lokasi_penyimpananLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTotalStok, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblJudulStok, javax.swing.GroupLayout.PREFERRED_SIZE, 356, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(23, Short.MAX_VALUE))
        );
        lokasi_penyimpananLayout.setVerticalGroup(
            lokasi_penyimpananLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(lokasi_penyimpananLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(lblJudulStok)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblTotalStok)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 118, Short.MAX_VALUE)
                .addGap(18, 18, 18))
        );

        getContentPane().add(lokasi_penyimpanan);
        lokasi_penyimpanan.setBounds(309, 469, 495, 209);

        status_distribusi.setBackground(new java.awt.Color(255, 255, 255));
        status_distribusi.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        lblJudulPelatihan.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblJudulPelatihan.setText("Status Pelatihan Penerimaan");

        lblStatusPelatihan.setFont(new java.awt.Font("Segoe UI Black", 1, 18)); // NOI18N
        lblStatusPelatihan.setText("LULUS");

        javax.swing.GroupLayout status_distribusiLayout = new javax.swing.GroupLayout(status_distribusi);
        status_distribusi.setLayout(status_distribusiLayout);
        status_distribusiLayout.setHorizontalGroup(
            status_distribusiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(status_distribusiLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(status_distribusiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblJudulPelatihan, javax.swing.GroupLayout.PREFERRED_SIZE, 356, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblStatusPelatihan, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(125, Short.MAX_VALUE))
        );
        status_distribusiLayout.setVerticalGroup(
            status_distribusiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(status_distribusiLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(lblJudulPelatihan)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblStatusPelatihan)
                .addContainerGap(20, Short.MAX_VALUE))
        );

        getContentPane().add(status_distribusi);
        status_distribusi.setBounds(820, 470, 500, 96);

        lokasi_penyimpanan1.setBackground(new java.awt.Color(255, 255, 255));
        lokasi_penyimpanan1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 102, 0), 1, true));

        txtDari.addActionListener(this::txtDariActionPerformed);

        txtDikirim.addActionListener(this::txtDikirimActionPerformed);

        btnKonfirmasiPengiriman.setBackground(new java.awt.Color(204, 102, 0));
        btnKonfirmasiPengiriman.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnKonfirmasiPengiriman.setForeground(new java.awt.Color(255, 255, 255));
        btnKonfirmasiPengiriman.setText("Konfirmasi Pengiriman");
        btnKonfirmasiPengiriman.addActionListener(this::btnKonfirmasiPengirimanActionPerformed);

        jLabel3.setBackground(new java.awt.Color(204, 204, 204));
        jLabel3.setForeground(new java.awt.Color(204, 204, 204));
        jLabel3.setText("Dari");

        jLabel4.setBackground(new java.awt.Color(204, 204, 204));
        jLabel4.setForeground(new java.awt.Color(204, 204, 204));
        jLabel4.setText("Dikirim");

        jLabel5.setBackground(new java.awt.Color(204, 204, 204));
        jLabel5.setForeground(new java.awt.Color(204, 204, 204));
        jLabel5.setText("Jumlah Aktual Diterima");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel6.setText("Pengiriman Menunggu Konfirmasi");

        jLabel8.setBackground(new java.awt.Color(204, 204, 204));
        jLabel8.setForeground(new java.awt.Color(204, 204, 204));
        jLabel8.setText("Keterangan");

        javax.swing.GroupLayout lokasi_penyimpanan1Layout = new javax.swing.GroupLayout(lokasi_penyimpanan1);
        lokasi_penyimpanan1.setLayout(lokasi_penyimpanan1Layout);
        lokasi_penyimpanan1Layout.setHorizontalGroup(
            lokasi_penyimpanan1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(lokasi_penyimpanan1Layout.createSequentialGroup()
                .addGroup(lokasi_penyimpanan1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(lokasi_penyimpanan1Layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addGroup(lokasi_penyimpanan1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addGroup(lokasi_penyimpanan1Layout.createSequentialGroup()
                                .addComponent(txtDari, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(lokasi_penyimpanan1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtDikirim, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel4))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(lokasi_penyimpanan1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(lokasi_penyimpanan1Layout.createSequentialGroup()
                                        .addComponent(jLabel5)
                                        .addGap(42, 42, 42)
                                        .addComponent(jLabel8)
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addGroup(lokasi_penyimpanan1Layout.createSequentialGroup()
                                        .addComponent(txtJumlahAktual, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtKeterangan, javax.swing.GroupLayout.DEFAULT_SIZE, 377, Short.MAX_VALUE))))
                            .addComponent(jLabel3)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, lokasi_penyimpanan1Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnKonfirmasiPengiriman, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        lokasi_penyimpanan1Layout.setVerticalGroup(
            lokasi_penyimpanan1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, lokasi_penyimpanan1Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jLabel6)
                .addGap(18, 18, 18)
                .addGroup(lokasi_penyimpanan1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(lokasi_penyimpanan1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtDari, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDikirim, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtJumlahAktual, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtKeterangan, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnKonfirmasiPengiriman, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        getContentPane().add(lokasi_penyimpanan1);
        lokasi_penyimpanan1.setBounds(309, 292, 1010, 159);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void dashboard1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dashboard1ActionPerformed
        
        tampilIdentitas();
        muatStatistikSPBU();
        muatStokSPBU();
        muatStatusPelatihan();
        loadPengirimanMenungguKonfirmasi();
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
        new RiwayatDistribusiSPBU().setVisible(true);
        this.dispose();
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

    private void txtDariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDariActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDariActionPerformed

    private void txtDikirimActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDikirimActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDikirimActionPerformed

    private void btnKonfirmasiPengirimanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKonfirmasiPengirimanActionPerformed
        if (idDistribusiAktif <= 0) {
            JOptionPane.showMessageDialog(this, "Tidak ada pengiriman yang menunggu konfirmasi.", "Informasi", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        String jumlahText = txtJumlahAktual.getText().trim();
        String keterangan = txtKeterangan.getText().trim();
        if (jumlahText.isEmpty() || keterangan.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Jumlah aktual dan keterangan wajib diisi.", "Validasi", JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            BigDecimal jumlah = new BigDecimal(jumlahText);
            String status = konfirmasiController.konfirmasi(idDistribusiAktif, Session.idSpbu, Session.idPengguna, jumlah, keterangan);
            JOptionPane.showMessageDialog(this, "Konfirmasi berhasil disimpan. Status: " + status, "Berhasil", JOptionPane.INFORMATION_MESSAGE);
            muatStatistikSPBU();
            muatStokSPBU();
            loadPengirimanMenungguKonfirmasi();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Jumlah aktual harus berupa angka yang valid.", "Validasi", JOptionPane.WARNING_MESSAGE);
            txtJumlahAktual.requestFocus();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Gagal menyimpan konfirmasi:\n" + ex.getMessage(), "Kesalahan", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnKonfirmasiPengirimanActionPerformed

    private void muatStatistikSPBU() {
        try {
            int[] statistik = dashboardController.getStatistik(Session.idSpbu);
            lblJumlahMenunggu.setText(String.valueOf(statistik[0]));
            lblJumlahPemeriksaan.setText(String.valueOf(statistik[1]));
            lblJumlahSelesai.setText(String.valueOf(statistik[2]));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal memuat statistik SPBU: " + e.getMessage(), "Kesalahan Database", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void muatStokSPBU() {
        DefaultTableModel model = new DefaultTableModel(new Object[]{"Jenis BBM", "Jumlah Stok (Liter)"}, 0) {
            @Override public boolean isCellEditable(int row, int column) { return false; }
            @Override public Class<?> getColumnClass(int column) {
                return column == 1 ? BigDecimal.class : String.class;
            }
        };
        DecimalFormat format = new DecimalFormat("#,##0.##");
        try {
            for (StokRingkasModel stok : stokController.getRingkasanStokSpbu(Session.idSpbu)) {
                model.addRow(new Object[]{stok.getNamaBbm(), stok.getJumlahStok()});
            }
            tblStokSPBU.setModel(model);
            TampilanTabel.rapikan(tblStokSPBU);
            konfigurasiTabelStokSPBU();
            lblTotalStok.setText("Total: " + format.format(dashboardController.getTotalStok(Session.idSpbu)) + " liter");
            lblJudulStok.setText("Total Stok " + Session.lokasi);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal memuat stok SPBU: " + e.getMessage(), "Kesalahan Database", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void konfigurasiTabelStokSPBU() {
        tblStokSPBU.setRowHeight(34);
        tblStokSPBU.setFillsViewportHeight(true);
        tblStokSPBU.setAutoCreateRowSorter(true);
        tblStokSPBU.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
        int jumlahKolom = tblStokSPBU.getColumnModel().getColumnCount();
        if (jumlahKolom >= 2) {
            tblStokSPBU.getColumnModel().getColumn(0).setPreferredWidth(180);
            tblStokSPBU.getColumnModel().getColumn(1).setPreferredWidth(130);
            if (jumlahKolom >= 3) {
                tblStokSPBU.getColumnModel().getColumn(2).setPreferredWidth(75);
            }
            javax.swing.table.DefaultTableCellRenderer angka =
                    new javax.swing.table.DefaultTableCellRenderer() {
                private final DecimalFormat format = new DecimalFormat("#,##0.##");
                @Override public java.awt.Component getTableCellRendererComponent(
                        javax.swing.JTable table, Object value, boolean selected,
                        boolean focused, int row, int column) {
                    String tampil = value instanceof BigDecimal
                            ? format.format(value) : (value == null ? "-" : value.toString());
                    return super.getTableCellRendererComponent(
                            table, tampil, selected, focused, row, column);
                }
            };
            angka.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
            tblStokSPBU.getColumnModel().getColumn(1).setCellRenderer(angka);
        }
    }

    private void muatStatusPelatihan() {
        try {
            String status = dashboardController.getStatusPelatihan(Session.idPengguna, Session.idSpbu);
            lblStatusPelatihan.setText(status == null || status.trim().isEmpty() ? "BELUM DIISI" : status);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal memuat status pelatihan: " + e.getMessage(), "Kesalahan Database", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadPengirimanMenungguKonfirmasi() {
        try {
            PengirimanMenungguModel pengiriman = dashboardController.getPengirimanTerbaru(Session.idSpbu);
            if (pengiriman == null) {
                idDistribusiAktif = -1;
                txtDari.setText(""); txtDikirim.setText(""); txtJumlahAktual.setText(""); txtKeterangan.setText("");
                btnKonfirmasiPengiriman.setEnabled(false);
                return;
            }
            idDistribusiAktif = pengiriman.getIdDistribusi();
            btnKonfirmasiPengiriman.setEnabled(true);
            txtDari.setText(pengiriman.getAsalPenyimpanan());
            txtDikirim.setText(pengiriman.getNamaBbm() + " - " + pengiriman.getJumlahDikirim() + " liter");
            txtJumlahAktual.setText(""); txtKeterangan.setText("");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal memuat pengiriman: " + e.getMessage(), "Kesalahan Database", JOptionPane.ERROR_MESSAGE);
        }
    }
        
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
           
            java.awt.EventQueue.invokeLater(() -> new DashboardSPBU().setVisible(true));
        }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnKonfirmasiPengiriman;
    private javax.swing.JButton dashboard1;
    private javax.swing.JPanel header;
    private javax.swing.JFrame jFrame1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel jenis_bbm;
    private javax.swing.JButton konfirmasi_pengiriman_bbm;
    private javax.swing.JLabel lblJudulPelatihan;
    private javax.swing.JLabel lblJudulStok;
    private javax.swing.JLabel lblJumlahMenunggu;
    private javax.swing.JLabel lblJumlahPemeriksaan;
    private javax.swing.JLabel lblJumlahSelesai;
    private javax.swing.JLabel lblMenungguKonfirmasi;
    private javax.swing.JLabel lblPengirimanSelesai;
    private javax.swing.JLabel lblPerluPemeriksaan;
    private javax.swing.JLabel lblStatusPelatihan;
    private javax.swing.JLabel lblTotalStok;
    private javax.swing.JButton lihat_pengiriman;
    private javax.swing.JButton lihat_stok;
    private javax.swing.JButton logout;
    private javax.swing.JPanel lokasi_penyimpanan;
    private javax.swing.JPanel lokasi_penyimpanan1;
    private javax.swing.JLabel nama;
    private javax.swing.JPanel navbar;
    private javax.swing.JButton riwayat_distribusi;
    private javax.swing.JLabel sebagai;
    private javax.swing.JPanel spbu_terdaftar;
    private javax.swing.JPanel status_distribusi;
    private javax.swing.JTable tblStokSPBU;
    private javax.swing.JPanel total_lokasi_penyimpanan;
    private javax.swing.JTextField txtDari;
    private javax.swing.JTextField txtDikirim;
    private javax.swing.JTextField txtJumlahAktual;
    private javax.swing.JTextField txtKeterangan;
    // End of variables declaration//GEN-END:variables
}
