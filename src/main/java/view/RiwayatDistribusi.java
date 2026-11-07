package view;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;
import controller.LaporanController;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import model.RiwayatDistribusiModel;

public class RiwayatDistribusi extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(RiwayatDistribusi.class.getName());

 
    public RiwayatDistribusi() {
        initComponents();
        TampilanTabel.rapikanSemua(this);
        setSize(1366, 768);
        setMinimumSize(new java.awt.Dimension(1100, 700));

        setLocationRelativeTo(null);
        tampilkanIdentitasPengguna();
        tampilDataRiwayatDistribusi();
        pasangAksiPemeriksaan();
        aturTombolAksiPemeriksaan();
        table_riwayat_distribusi.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override public void mouseReleased(java.awt.event.MouseEvent e) {
                javax.swing.SwingUtilities.invokeLater(() -> aturTombolAksiPemeriksaan());
            }
        });
    }
    
    private void tampilkanIdentitasPengguna() {
        String namaAktif = Session.namaLengkap;
        String peranAktif = Session.peran;
        String lokasiAktif = Session.lokasi;

        nama.setText(namaAktif == null || namaAktif.isBlank()
                ? "Pengguna" : namaAktif);

        String keterangan = peranAktif == null || peranAktif.isBlank()
                ? "Belum ada sesi" : peranAktif;
        if (lokasiAktif != null && !lokasiAktif.isBlank()) {
            keterangan += " · " + lokasiAktif;
        }
        sebagai.setText(keterangan);
    }

    private void pasangAksiPemeriksaan() {
        javax.swing.JPopupMenu menu = new javax.swing.JPopupMenu();
        javax.swing.JMenuItem mulai = new javax.swing.JMenuItem("Mulai Pemeriksaan");
        javax.swing.JMenuItem selesai = new javax.swing.JMenuItem("Selesaikan Pemeriksaan");
        mulai.addActionListener(e -> prosesAksiPemeriksaan(false));
        selesai.addActionListener(e -> prosesAksiPemeriksaan(true));
        menu.add(mulai);
        menu.add(selesai);
        table_riwayat_distribusi.setComponentPopupMenu(menu);
        table_riwayat_distribusi.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) aturTombolAksiPemeriksaan();
        });
        mulaiPemeriksaanButton.addActionListener(e -> prosesAksiPemeriksaan(false));
        selesaikanPemeriksaanButton.addActionListener(e -> prosesAksiPemeriksaan(true));
        table_riwayat_distribusi.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override public void mousePressed(java.awt.event.MouseEvent e) { pilihBarisKonteks(e); }
            @Override public void mouseReleased(java.awt.event.MouseEvent e) { pilihBarisKonteks(e); }
            private void pilihBarisKonteks(java.awt.event.MouseEvent e) {
                if (e.isPopupTrigger()) {
                    int row = table_riwayat_distribusi.rowAtPoint(e.getPoint());
                    if (row >= 0) table_riwayat_distribusi.setRowSelectionInterval(row, row);
                }
            }
        });
    }

    private void aturTombolAksiPemeriksaan() {
        String peranAktif = Session.peran == null ? "" : Session.peran.trim();
        boolean petugasPenyimpanan = Session.isAuthenticated()
                && Session.PETUGAS_PENYIMPANAN.equalsIgnoreCase(peranAktif);
        int row = table_riwayat_distribusi.getSelectedRow();
        String status = "";
        if (row >= 0 && table_riwayat_distribusi.getModel().getRowCount() > 0) {
            int modelRow = table_riwayat_distribusi.convertRowIndexToModel(row);
            Object nilai = table_riwayat_distribusi.getModel().getValueAt(modelRow, 6);
            status = nilai == null ? "" : nilai.toString();
        }
        // Status dan hak akses divalidasi ketika tombol ditekan.
        // Keadaan enabled mengikuti properti yang tersimpan di GUI Builder (.form).
    }

    private void prosesAksiPemeriksaan(boolean selesai) {
        String peranAktif = Session.peran == null ? "" : Session.peran.trim();
        if (!Session.isAuthenticated()
                || !Session.PETUGAS_PENYIMPANAN.equalsIgnoreCase(peranAktif)) {
            JOptionPane.showMessageDialog(this,
                    "Aksi ini hanya dapat dilakukan oleh Petugas Penyimpanan.",
                    "Akses ditolak", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int row = table_riwayat_distribusi.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Pilih data distribusi terlebih dahulu.", "Informasi", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        int modelRow = table_riwayat_distribusi.convertRowIndexToModel(row);
        int idDistribusi = Integer.parseInt(
                table_riwayat_distribusi.getModel().getValueAt(modelRow, 0).toString());
        String status = table_riwayat_distribusi.getModel().getValueAt(modelRow, 6).toString();
        String statusWajib = selesai ? "DIPROSES" : "PERLU_PEMERIKSAAN";
        if (!statusWajib.equals(status)) {
            JOptionPane.showMessageDialog(this, "Aksi ini hanya dapat dilakukan pada status " + statusWajib + ".", "Status tidak sesuai", JOptionPane.WARNING_MESSAGE);
            return;
        }
        String catatanPemeriksaan = null;
        if (selesai) {
            catatanPemeriksaan = JOptionPane.showInputDialog(this,
                    "Tuliskan hasil pemeriksaan/penyebab selisih (5-500 karakter):",
                    "Hasil Pemeriksaan Distribusi", JOptionPane.QUESTION_MESSAGE);
            if (catatanPemeriksaan == null) return;
            catatanPemeriksaan = catatanPemeriksaan.trim();
            if (catatanPemeriksaan.length() < 5 || catatanPemeriksaan.length() > 500) {
                JOptionPane.showMessageDialog(this,
                        "Catatan hasil pemeriksaan wajib 5-500 karakter.",
                        "Catatan belum valid", JOptionPane.WARNING_MESSAGE);
                return;
            }
        } else {
            if (JOptionPane.showConfirmDialog(this,
                    "Pemeriksaan akan dimulai dan status berubah menjadi DIPROSES. Lanjutkan?",
                    "Konfirmasi Pemeriksaan", JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) return;
        }
        try {
            controller.OperasionalController operasional = new controller.OperasionalController();
            if (selesai) operasional.selesaikanPemeriksaanDistribusi(
                    idDistribusi, Session.idPengguna, catatanPemeriksaan);
            else operasional.mulaiPemeriksaanDistribusi(idDistribusi, Session.idPengguna);
            JOptionPane.showMessageDialog(this, selesai
                    ? "Hasil pemeriksaan tersimpan. Status menjadi SELESAI_DITANGANI dan stok SPBU diperbarui satu kali berdasarkan jumlah aktual."
                    : "Status distribusi berhasil diubah menjadi DIPROSES.", "Berhasil", JOptionPane.INFORMATION_MESSAGE);
            tampilDataRiwayatDistribusi();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Gagal memperbarui status distribusi: " + ex.getMessage(), "Kesalahan", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void tampilDataRiwayatDistribusi() {
        DefaultTableModel model = new DefaultTableModel() {
            @Override public boolean isCellEditable(int row, int column) { return false; }
            @Override public Class<?> getColumnClass(int column) {
                switch (column) {
                    case 0: return Integer.class;
                    case 1: return java.sql.Timestamp.class;
                    case 5: case 7: case 8: return java.math.BigDecimal.class;
                    default: return String.class;
                }
            }
        };
        String[] headers = {"ID", "Tanggal", "Dari", "Tujuan SPBU", "BBM",
            "Dikirim (Liter)", "Status", "Diterima (Liter)", "Selisih (Liter)", "Keterangan"};
        for (String h : headers) model.addColumn(h);

        try {
            List<RiwayatDistribusiModel> rows =
                    new LaporanController().getRiwayatDistribusi(Session.idLokasiPenyimpanan);
            for (RiwayatDistribusiModel r : rows) {
                java.math.BigDecimal dikirim = r.jumlahDikirim();
                java.math.BigDecimal aktual = r.jumlahAktual();
                java.math.BigDecimal selisih = aktual == null ? null : dikirim.subtract(aktual);
                model.addRow(new Object[]{
                    r.idDistribusi(), r.tanggalDistribusi(), r.namaLokasi(), r.namaSpbu(),
                    r.namaBbm(), dikirim, r.statusDistribusi(), aktual, selisih,
                    r.keterangan() == null || r.keterangan().isBlank() ? "-" : r.keterangan()
                });
            }

            table_riwayat_distribusi.setModel(model);
            table_riwayat_distribusi.clearSelection();
            TampilanTabel.rapikan(table_riwayat_distribusi);
            table_riwayat_distribusi.setRowHeight(36);
            table_riwayat_distribusi.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            table_riwayat_distribusi.setAutoCreateRowSorter(true);
            table_riwayat_distribusi.setFillsViewportHeight(true);

            int[] widths = {55, 145, 165, 165, 115, 105, 155, 105, 100, 230};
            int jumlahKolom = table_riwayat_distribusi.getColumnModel().getColumnCount();
            for (int i = 0; i < Math.min(widths.length, jumlahKolom); i++) {
                table_riwayat_distribusi.getColumnModel().getColumn(i).setPreferredWidth(widths[i]);
            }

            javax.swing.table.DefaultTableCellRenderer tengah =
                    new javax.swing.table.DefaultTableCellRenderer();
            tengah.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
            table_riwayat_distribusi.getColumnModel().getColumn(0).setCellRenderer(tengah);
            table_riwayat_distribusi.getColumnModel().getColumn(6).setCellRenderer(
                    new javax.swing.table.DefaultTableCellRenderer() {
                @Override public java.awt.Component getTableCellRendererComponent(
                        javax.swing.JTable table, Object value, boolean selected,
                        boolean focused, int row, int column) {
                    java.awt.Component c = super.getTableCellRendererComponent(
                            table, value, selected, focused, row, column);
                    setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
                    if (!selected) {
                        String status = value == null ? "" : value.toString();
                        if ("SELESAI".equalsIgnoreCase(status)) {
                            c.setForeground(new java.awt.Color(0, 120, 60));
                        } else if ("PERLU_PEMERIKSAAN".equalsIgnoreCase(status)) {
                            c.setForeground(new java.awt.Color(190, 90, 0));
                        } else if ("DIPROSES".equalsIgnoreCase(status)) {
                            c.setForeground(new java.awt.Color(0, 90, 170));
                        } else {
                            c.setForeground(table.getForeground());
                        }
                    }
                    return c;
                }
            });

            javax.swing.table.DefaultTableCellRenderer angka =
                    new javax.swing.table.DefaultTableCellRenderer() {
                private final java.text.DecimalFormat format =
                        new java.text.DecimalFormat("#,##0.##");
                @Override public java.awt.Component getTableCellRendererComponent(
                        javax.swing.JTable table, Object value, boolean selected,
                        boolean focused, int row, int column) {
                    String tampil = value instanceof java.math.BigDecimal
                            ? format.format(value) : (value == null ? "-" : value.toString());
                    return super.getTableCellRendererComponent(
                            table, tampil, selected, focused, row, column);
                }
            };
            angka.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
            for (int col : new int[]{5, 7, 8}) {
                table_riwayat_distribusi.getColumnModel().getColumn(col).setCellRenderer(angka);
            }

            javax.swing.table.DefaultTableCellRenderer tanggal =
                    new javax.swing.table.DefaultTableCellRenderer() {
                private final java.text.SimpleDateFormat format =
                        new java.text.SimpleDateFormat("dd MMM yyyy HH:mm");
                @Override public java.awt.Component getTableCellRendererComponent(
                        javax.swing.JTable table, Object value, boolean selected,
                        boolean focused, int row, int column) {
                    String tampil = value instanceof java.util.Date
                            ? format.format((java.util.Date) value) : "-";
                    return super.getTableCellRendererComponent(
                            table, tampil, selected, focused, row, column);
                }
            };
            table_riwayat_distribusi.getColumnModel().getColumn(1).setCellRenderer(tanggal);
            aturTombolAksiPemeriksaan();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this,
                    "Gagal menampilkan riwayat distribusi:\n" + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
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
        jScrollPane1 = new javax.swing.JScrollPane();
        table_riwayat_distribusi = new javax.swing.JTable();
        mulaiPemeriksaanButton = new javax.swing.JButton();
        selesaikanPemeriksaanButton = new javax.swing.JButton();

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

        mulaiPemeriksaanButton.setBackground(new java.awt.Color(0, 102, 51));
        mulaiPemeriksaanButton.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12));
        mulaiPemeriksaanButton.setForeground(new java.awt.Color(255, 255, 255));
        mulaiPemeriksaanButton.setText("Mulai Pemeriksaan");
        mulaiPemeriksaanButton.setEnabled(true);

        selesaikanPemeriksaanButton.setBackground(new java.awt.Color(0, 102, 51));
        selesaikanPemeriksaanButton.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12));
        selesaikanPemeriksaanButton.setForeground(new java.awt.Color(255, 255, 255));
        selesaikanPemeriksaanButton.setText("Selesaikan Pemeriksaan");
        selesaikanPemeriksaanButton.setEnabled(true);

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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(Logout1)
                .addGap(22, 22, 22))
        );

        header.setBackground(new java.awt.Color(255, 255, 255));
        header.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel7.setText("RIWAYAT DISTRIBUSI");

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
                .addGroup(headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addGroup(headerLayout.createSequentialGroup()
                        .addComponent(mulaiPemeriksaanButton, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(selesaikanPemeriksaanButton, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        headerLayout.setVerticalGroup(
            headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(headerLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(mulaiPemeriksaanButton)
                    .addComponent(selesaikanPemeriksaanButton))
                .addContainerGap(12, Short.MAX_VALUE))
            .addGroup(headerLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        table_riwayat_distribusi.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "", "", "", ""
            }
        ));
        jScrollPane1.setViewportView(table_riwayat_distribusi);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(navbar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(header, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1038, Short.MAX_VALUE)
                        .addContainerGap(44, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(navbar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(header, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(172, Short.MAX_VALUE))
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

    /**
     * @param args the command line arguments
     */
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
        java.awt.EventQueue.invokeLater(() -> new RiwayatDistribusi().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Logout1;
    private javax.swing.JButton catat_distribusi_bbm;
    private javax.swing.JButton catat_penerimaan_bbm;
    private javax.swing.JButton dashboard1;
    private javax.swing.JPanel header;
    private javax.swing.JFrame jFrame1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel nama;
    private javax.swing.JButton mulaiPemeriksaanButton;
    private javax.swing.JButton selesaikanPemeriksaanButton;
    private javax.swing.JPanel navbar;
    private javax.swing.JButton riwayat_distribusi;
    private javax.swing.JButton riwayat_penerimaan;
    private javax.swing.JLabel sebagai;
    private javax.swing.JButton stok_lokasi;
    private javax.swing.JTable table_riwayat_distribusi;
    // End of variables declaration//GEN-END:variables
}
