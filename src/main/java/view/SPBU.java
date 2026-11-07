package view;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import controller.SpbuController;
import model.SpbuModel;
import java.util.List;

public class SPBU extends javax.swing.JFrame {
    
    private final SpbuController spbuController =
            new SpbuController();

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(SPBU.class.getName());

    /**
     * Creates new form DashboardAdmin
     */
    public SPBU() {
        initComponents();
        setTitle("OCTANE - Kelola Data SPBU");
        TampilanTabel.rapikanSemua(this);

        setExtendedState(javax.swing.JFrame.NORMAL);
        setSize(1366, 768);
        setMinimumSize(new java.awt.Dimension(1100, 650));
        setLocationRelativeTo(null);
        setResizable(true);

        table_spbu.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        table_spbu.setRowHeight(40);

        tampilIdentitas();
        initTableEvents();
        javax.swing.SwingUtilities.invokeLater(this::loadTableData);
    }

    private void tampilIdentitas() {
        String namaPengguna = Session.namaLengkap;
        String peranPengguna = Session.peran;
        String lokasiPengguna = Session.lokasi;
        nama.setText(namaPengguna == null || namaPengguna.isBlank() ? "Pengguna" : namaPengguna);
        String identitasPeran = peranPengguna == null || peranPengguna.isBlank() ? "" : peranPengguna;
        if (lokasiPengguna != null && !lokasiPengguna.isBlank()
                && !"-".equals(lokasiPengguna.trim())) {
            identitasPeran += " · " + lokasiPengguna;
        }
        sebagai.setText(identitasPeran.isBlank() ? "Belum ada sesi" : identitasPeran);
    }
    public void loadTableData() {
        DefaultTableModel model = new DefaultTableModel() {
            @Override public boolean isCellEditable(int row, int column) { return false; }
        };
        model.addColumn("ID SPBU");
        model.addColumn("Nama SPBU");
        model.addColumn("Alamat");
        model.addColumn("Status Aktif");
        try {
            List<SpbuModel> daftarSPBU = spbuController.getAllSpbu();
            for (SpbuModel spbu : daftarSPBU) {
                model.addRow(new Object[]{spbu.getIdSpbu(), spbu.getNamaSpbu(),
                    spbu.getAlamat(), spbu.getStatusAktif()});
            }
            table_spbu.setModel(model);
            TampilanTabel.rapikan(table_spbu);
            table_spbu.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
            table_spbu.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
            table_spbu.getColumnModel().getColumn(1).setPreferredWidth(230);
            table_spbu.getColumnModel().getColumn(2).setPreferredWidth(430);
            table_spbu.getColumnModel().getColumn(3).setPreferredWidth(130);
            table_spbu.getColumnModel().getColumn(0).setMinWidth(0);
            table_spbu.getColumnModel().getColumn(0).setMaxWidth(0);
            table_spbu.getColumnModel().getColumn(0).setWidth(0);
        } catch (SQLException | RuntimeException e) {
            logger.log(java.util.logging.Level.SEVERE, "Gagal memuat data SPBU", e);
            JOptionPane.showMessageDialog(this,
                    "Gagal memuat data SPBU.\nPeriksa hak akses Administrator dan koneksi database.\n\n"
                    + "Detail: " + e.getMessage(),
                    "Gagal Memuat Data SPBU", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void initTableEvents() {
        table_spbu.setSelectionMode(
            javax.swing.ListSelectionModel.SINGLE_SELECTION
        );
       
        btnUbah.setEnabled(false);
        btnHapus.setEnabled(false);
        btnStatus.setEnabled(false);
        btnBatal.setEnabled(false);

        table_spbu.getSelectionModel().addListSelectionListener(evt -> {
            if (!evt.getValueIsAdjusting()) {
                boolean dipilih = table_spbu.getSelectedRow() >= 0;

                btnUbah.setEnabled(dipilih);
                btnHapus.setEnabled(dipilih);
                btnStatus.setEnabled(dipilih);
            }
        });
        
        btnUbah.addActionListener(e -> mulaiUbahSpbu());
        
        btnHapus.addActionListener(e -> hapusSpbuTerpilih());
       
        btnStatus.addActionListener(e -> ubahStatusSpbuTerpilih());
       
        btnBatal.addActionListener(e -> batalUbahSpbu());
        
        table_spbu.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2
                        && table_spbu.getSelectedRow() >= 0) {
                    mulaiUbahSpbu();
                }
            }
        });
    }

    private Integer idSpbuSedangDiubah = null;

    private void mulaiUbahSpbu() {
        int rowView = table_spbu.getSelectedRow();
        if (rowView < 0) return;
        int row = table_spbu.convertRowIndexToModel(rowView);
        idSpbuSedangDiubah = Integer.valueOf(String.valueOf(table_spbu.getModel().getValueAt(row, 0)));
        namaSpbu.setText(String.valueOf(table_spbu.getModel().getValueAt(row, 1)));
        alamat.setText(String.valueOf(table_spbu.getModel().getValueAt(row, 2)));
        jLabel4.setText("Ubah Data SPBU");
        simpan.setText("Simpan Perubahan");
        btnBatal.setEnabled(true);
    }

    private void batalUbahSpbu() {
        idSpbuSedangDiubah = null;
        namaSpbu.setText("");
        alamat.setText("");
        jLabel4.setText("Tambah Data SPBU");
        simpan.setText("Simpan");
        btnBatal.setEnabled(false);
        table_spbu.clearSelection();
        btnUbah.setEnabled(false);
        btnHapus.setEnabled(false);
        btnStatus.setEnabled(false);
    }

    private void hapusSpbuTerpilih() {
        int row = table_spbu.getSelectedRow();

        if (row < 0) {
            JOptionPane.showMessageDialog(
                this,
                "Pilih data SPBU terlebih dahulu.",
                "Peringatan",
                JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        deleteSpbu(row);
    }

    private void ubahStatusSpbuTerpilih() {
        int row = table_spbu.getSelectedRow();

        if (row < 0) {
            JOptionPane.showMessageDialog(
                this,
                "Pilih data SPBU terlebih dahulu.",
                "Peringatan",
                JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        toggleStatusSpbu(row);
    }

    public void openEditDialog(int rowView) {
        if (rowView < 0 || rowView >= table_spbu.getRowCount()) {
            return;
        }

        int row = table_spbu.convertRowIndexToModel(rowView);

        int idSpbu = Integer.parseInt(
            table_spbu.getModel().getValueAt(row, 0).toString()
        );

        String currentNama =
            table_spbu.getModel().getValueAt(row, 1).toString();

        String currentAlamat =
            table_spbu.getModel().getValueAt(row, 2).toString();

        UbahDataSPBU dialog = new UbahDataSPBU(
            this,
            true,
            currentNama,
            currentAlamat
        );

        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);

        if (!dialog.isBerhasilDisimpan()) {
            return;
        }

        try {
            boolean berhasil = spbuController.ubahSpbu(
                idSpbu,
                dialog.getNamaSPBU(),
                dialog.getAlamatSPBU()
            );

            if (berhasil) {
                JOptionPane.showMessageDialog(
                    this,
                    "Data SPBU berhasil diperbarui!",
                    "Sukses",
                    JOptionPane.INFORMATION_MESSAGE
                );

                batalUbahSpbu();
                loadTableData();
            } else {
                JOptionPane.showMessageDialog(
                    this,
                    "Data SPBU tidak ditemukan atau tidak mengalami perubahan.",
                    "Informasi",
                    JOptionPane.INFORMATION_MESSAGE
                );
            }

        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(
                this,
                e.getMessage(),
                "Peringatan",
                JOptionPane.WARNING_MESSAGE
            );
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(
                this,
                "Gagal memperbarui data SPBU: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }
    
    public void toggleStatusSpbu(int row) {

        int idSpbu = (int) table_spbu.getValueAt(row, 0);

        String statusSaatIni =
                table_spbu.getValueAt(row, 3).toString();

        String statusBaru = statusSaatIni.equalsIgnoreCase("AKTIF")
                ? "NONAKTIF"
                : "AKTIF";

        int confirm = JOptionPane.showConfirmDialog(
            this,
            "Ubah status SPBU menjadi " + statusBaru + "?",
            "Konfirmasi Status",
            JOptionPane.YES_NO_OPTION
        );

        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        try {

            boolean berhasil = spbuController.ubahStatusSpbu(
                idSpbu,
                statusBaru
            );

            if (berhasil) {

                JOptionPane.showMessageDialog(
                    this,
                    "Status SPBU berhasil diubah menjadi " + statusBaru + "!",
                    "Sukses",
                    JOptionPane.INFORMATION_MESSAGE
                );

                loadTableData();

            } else {

                JOptionPane.showMessageDialog(
                    this,
                    "Status SPBU gagal diubah.",
                    "Informasi",
                    JOptionPane.WARNING_MESSAGE
                );
            }

        } catch (IllegalArgumentException e) {

            JOptionPane.showMessageDialog(
                this,
                e.getMessage(),
                "Peringatan",
                JOptionPane.WARNING_MESSAGE
            );

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(
                this,
                "Gagal mengubah status SPBU: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }

    public void deleteSpbu(int row) {
        int idSpbu = (int) table_spbu.getValueAt(row, 0);
        String namaSpbu = table_spbu.getValueAt(row, 1).toString();

        int konfirmasi = JOptionPane.showConfirmDialog(
                this,
                "Apakah yakin ingin menghapus SPBU "
                        + namaSpbu + "?\n\n"
                        + "Jika SPBU masih memiliki petugas, stok, atau riwayat transaksi, "
                        + "sistem akan menonaktifkannya agar data historis tetap terjaga.",
                "Konfirmasi Hapus SPBU",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );

        if (konfirmasi != JOptionPane.YES_OPTION) {
            return;
        }

        try {
            int hasil = spbuController.hapusSpbu(idSpbu);
            if (hasil == 1) {
                JOptionPane.showMessageDialog(this, "Data SPBU berhasil dihapus.", "Berhasil", JOptionPane.INFORMATION_MESSAGE);
                loadTableData();
            } else if (hasil == 2) {
                JOptionPane.showMessageDialog(this,
                        "SPBU memiliki petugas, stok, atau riwayat transaksi sehingga tidak dihapus. Statusnya diubah menjadi NONAKTIF agar riwayat tetap utuh.",
                        "SPBU Dinonaktifkan", JOptionPane.INFORMATION_MESSAGE);
                loadTableData();
            } else {
                JOptionPane.showMessageDialog(this, "Data SPBU tidak ditemukan.", "Peringatan", JOptionPane.WARNING_MESSAGE);
            }

        } catch (SQLException e) {
            if ("23000".equals(e.getSQLState())) {
                String statusSpbu = table_spbu.getValueAt(row, 3).toString();

                String pesan;

                if (statusSpbu.equalsIgnoreCase("NONAKTIF")) {
                    pesan = "SPBU ini sudah berstatus NONAKTIF, "
                            + "tetapi tidak dapat dihapus karena masih memiliki "
                            + "data petugas, stok, atau riwayat transaksi.\n\n"
                            + "Data SPBU tetap dipertahankan untuk menjaga "
                            + "riwayat dan pertanggungjawaban operasional.";
                } else {
                    pesan = "SPBU tidak dapat dihapus karena masih memiliki "
                            + "data petugas, stok, atau riwayat transaksi.\n\n"
                            + "Jika SPBU sudah tidak beroperasi, silakan "
                            + "nonaktifkan terlebih dahulu. Riwayat transaksi "
                            + "tetap akan dipertahankan.";
                }

                JOptionPane.showMessageDialog(
                        this,
                        pesan,
                        "Penghapusan Ditolak",
                        JOptionPane.WARNING_MESSAGE
                );
            }
        }
    }

    private class ActionPanelRenderer implements TableCellRenderer {

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            String status = table.getValueAt(row, 3).toString();
            return new ActionPanel(status);
        }
    }

    private class ActionPanelEditor extends AbstractCellEditor implements TableCellEditor {

        private final ActionPanel panel;

        public ActionPanelEditor(SPBU parentFrame, JTable table) {
            panel = new ActionPanel("AKTIF");

            panel.btnEdit.addActionListener(e -> {
                int row = table.getSelectedRow();
                fireEditingStopped();
                if (row != -1) {
                    parentFrame.openEditDialog(row);
                }
            });

            panel.btnToggle.addActionListener(e -> {
                int row = table.getSelectedRow();
                fireEditingStopped();
                if (row != -1) {
                    parentFrame.toggleStatusSpbu(row);
                }
            });

            panel.btnDelete.addActionListener(e -> {
                int row = table.getSelectedRow();
                fireEditingStopped();
                if (row != -1) {
                    parentFrame.deleteSpbu(row);
                }
            });
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            String status = table.getValueAt(row, 3).toString();
            panel.updateToggleLabel(status);
            return panel;
        }

        @Override
        public Object getCellEditorValue() {
            return "";
        }
    }

    private class ActionPanel extends JPanel {

        public JButton btnEdit = new JButton("Ubah");
        public JButton btnToggle = new JButton("Nonaktifkan");
        public JButton btnDelete = new JButton("Hapus");

        public ActionPanel(String status) {
            setLayout(new FlowLayout(FlowLayout.CENTER, 3, 3));
            setOpaque(true);

            btnEdit.setBackground(new java.awt.Color(255, 193, 7));
            btnDelete.setBackground(new java.awt.Color(220, 53, 69));
            btnDelete.setForeground(java.awt.Color.WHITE);

            updateToggleLabel(status);
            java.awt.Font fontAksi = new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 11);
            for (JButton tombol : new JButton[]{btnEdit, btnToggle, btnDelete}) {
                tombol.setFont(fontAksi);
                tombol.setMargin(new java.awt.Insets(4, 7, 4, 7));
            }

            add(btnEdit);
            add(btnToggle);
            add(btnDelete);
        }

        public void updateToggleLabel(String status) {
            if ("AKTIF".equalsIgnoreCase(status)) {
                btnToggle.setText("Nonaktifkan");
                btnToggle.setBackground(new java.awt.Color(108, 117, 125));
                btnToggle.setForeground(java.awt.Color.WHITE);
            } else {
                btnToggle.setText("Aktifkan");
                btnToggle.setBackground(new java.awt.Color(40, 167, 69));
                btnToggle.setForeground(java.awt.Color.WHITE);
            }
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
        header = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        nama = new javax.swing.JLabel();
        sebagai = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        table_spbu = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        simpan = new javax.swing.JButton();
        namaSpbu = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        alamat = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        btnUbah = new javax.swing.JButton();
        btnHapus = new javax.swing.JButton();
        btnBatal = new javax.swing.JButton();
        btnStatus = new javax.swing.JButton();

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
                .addContainerGap(19, Short.MAX_VALUE))
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(Logout)
                .addGap(15, 15, 15))
        );

        header.setBackground(new java.awt.Color(255, 255, 255));
        header.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel7.setText("KELOLA DATA SPBU");

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
                .addGap(24, 24, 24)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(37, 37, 37))
        );
        headerLayout.setVerticalGroup(
            headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(headerLayout.createSequentialGroup()
                .addGroup(headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(headerLayout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(headerLayout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addComponent(jLabel7)))
                .addContainerGap(23, Short.MAX_VALUE))
        );

        table_spbu.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(table_spbu);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        simpan.setBackground(new java.awt.Color(0, 51, 102));
        simpan.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        simpan.setForeground(new java.awt.Color(255, 255, 255));
        simpan.setText("Simpan");
        simpan.addActionListener(this::simpanActionPerformed);

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(204, 204, 204));
        jLabel3.setText("Nama SPBU");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel4.setText("Tambah Data SPBU");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(204, 204, 204));
        jLabel5.setText("Alamat");

        btnUbah.setText("Ubah");

        btnHapus.setText("Hapus");

        btnBatal.setText("Batal");

        btnStatus.setText("Ubah Status");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(btnUbah, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(8, 8, 8)
                                .addComponent(btnHapus, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(8, 8, 8)
                                .addComponent(btnBatal, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(8, 8, 8)
                                .addComponent(btnStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(8, 8, 8)
                                .addComponent(simpan, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(226, 226, 226))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(alamat, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(namaSpbu, javax.swing.GroupLayout.DEFAULT_SIZE, 552, Short.MAX_VALUE)
                                .addContainerGap())))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jLabel4)
                .addGap(14, 14, 14)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel5))
                .addGap(4, 4, 4)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(namaSpbu, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(alamat, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnUbah, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnHapus, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBatal, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(simpan, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(14, 14, 14))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(navbar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane1)
                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(0, 24, Short.MAX_VALUE))
                    .addComponent(header, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(navbar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addComponent(header, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 502, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void spbuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_spbuActionPerformed
        new SPBU().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_spbuActionPerformed

    private void dashboard1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dashboard1ActionPerformed
        new DashboardAdmin().setVisible(true);
        this.dispose();
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
        new Penerimaan().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_penerimaanActionPerformed

    private void distribusiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_distribusiActionPerformed
        new Distribusi().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_distribusiActionPerformed

    private void konfirmasiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_konfirmasiActionPerformed
        new Konfirmasi().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_konfirmasiActionPerformed

    private void simpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_simpanActionPerformed
        String inputNama = namaSpbu.getText().trim();
        String inputAlamat = alamat.getText().trim();
        if (inputNama.isEmpty() || inputAlamat.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nama SPBU dan alamat wajib diisi!",
                    "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            boolean berhasil;
            if (idSpbuSedangDiubah != null) {
                berhasil = spbuController.ubahSpbu(idSpbuSedangDiubah, inputNama, inputAlamat);
            } else {
                berhasil = spbuController.tambahSpbu(inputNama, inputAlamat);
            }
            if (berhasil) {
                JOptionPane.showMessageDialog(this,
                        idSpbuSedangDiubah == null ? "Data SPBU berhasil ditambahkan!" : "Data SPBU berhasil diperbarui!",
                        "Sukses", JOptionPane.INFORMATION_MESSAGE);
                batalUbahSpbu();
                loadTableData();
            } else {
                JOptionPane.showMessageDialog(this, "Data SPBU tidak ditemukan atau tidak berubah.",
                        "Informasi", JOptionPane.WARNING_MESSAGE);
            }
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Peringatan", JOptionPane.WARNING_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Proses data SPBU gagal: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_simpanActionPerformed

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
        java.awt.EventQueue.invokeLater(() -> new SPBU().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Logout;
    private javax.swing.JTextField alamat;
    private javax.swing.JButton btnBatal;
    private javax.swing.JButton btnHapus;
    private javax.swing.JButton btnStatus;
    private javax.swing.JButton btnUbah;
    private javax.swing.JButton dashboard1;
    private javax.swing.JButton distribusi;
    private javax.swing.JPanel header;
    private javax.swing.JFrame jFrame1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton jenisBBM;
    private javax.swing.JButton konfirmasi;
    private javax.swing.JButton lokasiPenyimpanan;
    private javax.swing.JLabel nama;
    private javax.swing.JTextField namaSpbu;
    private javax.swing.JPanel navbar;
    private javax.swing.JButton penerimaan;
    private javax.swing.JButton pengguna;
    private javax.swing.JLabel sebagai;
    private javax.swing.JButton simpan;
    private javax.swing.JButton spbu;
    private javax.swing.JButton stokPenyimpanan;
    private javax.swing.JButton stokSpbu;
    private javax.swing.JTable table_spbu;
    // End of variables declaration//GEN-END:variables
}
