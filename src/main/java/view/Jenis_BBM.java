package view;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import controller.JenisBbmController;
import java.sql.SQLException;
import java.util.List;
import model.JenisBbmModel;
import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

public class Jenis_BBM extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(Jenis_BBM.class.getName());
    private final JenisBbmController jenisBbmController = new JenisBbmController();

    /**
     * Creates new form DashboardAdmin
     */
    public Jenis_BBM() {
        initComponents();
        navbar2.setPreferredSize(new java.awt.Dimension(350, 768));
        navbar2.setMinimumSize(new java.awt.Dimension(350, 0));
        TampilanTabel.rapikanSemua(this);

        setExtendedState(javax.swing.JFrame.NORMAL);
        setSize(1366, 768);
        setLocationRelativeTo(null);
        setResizable(false);

        table_jenis_bbm.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        table_jenis_bbm.setRowHeight(40);
        btnUbah.setEnabled(false);
        btnHapus.setEnabled(false);
        btnBatal.setEnabled(false);

        tampilIdentitas();
        tampilDataJenisBBM();
        initTableEvents();
        getRootPane().getInputMap(javax.swing.JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put(javax.swing.KeyStroke.getKeyStroke("ESCAPE"), "batalEditJenisBbm");
        getRootPane().getActionMap().put("batalEditJenisBbm",
                new javax.swing.AbstractAction() {
                    @Override public void actionPerformed(java.awt.event.ActionEvent e) {
                        if (idJenisBbmDipilih != null) batalUbahJenisBBM();
                        else table_jenis_bbm.clearSelection();
                    }
                });
        table_jenis_bbm.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        table_jenis_bbm.getSelectionModel().addListSelectionListener(evt -> {
            if (!evt.getValueIsAdjusting()) {
                boolean dipilih = table_jenis_bbm.getSelectedRow() >= 0;
                btnUbah.setEnabled(dipilih);
                btnHapus.setEnabled(dipilih);
                btnBatal.setEnabled(dipilih && idJenisBbmDipilih == null);
            }
        });
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
    public void tampilDataJenisBBM() {
        DefaultTableModel model = new DefaultTableModel() {
            @Override public boolean isCellEditable(int row, int column) { return false; }
        };
        model.addColumn("ID BBM");
        model.addColumn("NAMA BBM");
        model.addColumn("STATUS");
        try {
            List<JenisBbmModel> daftar = jenisBbmController.getAll();
            for (JenisBbmModel d : daftar) {
                model.addRow(new Object[]{d.getIdJenisBbm(), d.getNamaBbm(), d.getStatusAktif()});
            }
            table_jenis_bbm.setModel(model);
            TampilanTabel.rapikan(table_jenis_bbm);
            table_jenis_bbm.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
            table_jenis_bbm.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
            table_jenis_bbm.getColumnModel().getColumn(1).setPreferredWidth(720);
            table_jenis_bbm.getColumnModel().getColumn(2).setPreferredWidth(135);
            table_jenis_bbm.getColumnModel().getColumn(1).setMinWidth(220);
            table_jenis_bbm.getColumnModel().getColumn(2).setMinWidth(110);
            table_jenis_bbm.getColumnModel().getColumn(0).setMinWidth(0);
            table_jenis_bbm.getColumnModel().getColumn(0).setMaxWidth(0);
            table_jenis_bbm.getColumnModel().getColumn(0).setWidth(0);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Gagal menampilkan data jenis BBM: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void initTableEvents() {
        table_jenis_bbm.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 && table_jenis_bbm.getSelectedRow() != -1) {
                    mulaiUbahJenisBBM();
                }
            }
        });
    }

    private Integer idJenisBbmDipilih = null;

    private void mulaiUbahJenisBBM() {
        int rowView = table_jenis_bbm.getSelectedRow();
        if (rowView < 0) {
            JOptionPane.showMessageDialog(this, "Pilih jenis BBM pada tabel terlebih dahulu.");
            return;
        }
        int row = table_jenis_bbm.convertRowIndexToModel(rowView);
        idJenisBbmDipilih = Integer.valueOf(String.valueOf(table_jenis_bbm.getModel().getValueAt(row, 0)));
        nama_jenis_bbm.setText(String.valueOf(table_jenis_bbm.getModel().getValueAt(row, 1)));
        jLabel9.setText("Ubah Data Jenis BBM");
        simpan.setText("Simpan Perubahan");
        btnBatal.setText("Batal");
        btnBatal.setEnabled(true);
    }

    private void batalUbahJenisBBM() {
        idJenisBbmDipilih = null;
        nama_jenis_bbm.setText("");
        jLabel9.setText("Tambah Data Jenis BBM");
        simpan.setText("Simpan");
        btnBatal.setText("Ubah Status");
        btnBatal.setEnabled(false);
        table_jenis_bbm.clearSelection();
        btnUbah.setEnabled(false);
        btnHapus.setEnabled(false);
    }

    private void hapusJenisBBMTerpilih() {
        int rowView = table_jenis_bbm.getSelectedRow();
        if (rowView < 0) return;
        int row = table_jenis_bbm.convertRowIndexToModel(rowView);
        deleteJenisBBM(row);
        batalUbahJenisBBM();
    }

    private void ubahStatusJenisBBMTerpilih() {
        int rowView = table_jenis_bbm.getSelectedRow();
        if (rowView < 0) return;
        int row = table_jenis_bbm.convertRowIndexToModel(rowView);
        int id = Integer.parseInt(String.valueOf(table_jenis_bbm.getModel().getValueAt(row, 0)));
        String status = String.valueOf(table_jenis_bbm.getModel().getValueAt(row, 2));
        String baru = "AKTIF".equalsIgnoreCase(status) ? "NONAKTIF" : "AKTIF";
        int pilihan = JOptionPane.showConfirmDialog(this,
                "Ubah status jenis BBM menjadi " + baru + "?",
                "Konfirmasi Ubah Status", JOptionPane.YES_NO_OPTION);
        if (pilihan != JOptionPane.YES_OPTION) return;
        try {
            jenisBbmController.ubahStatus(id, baru);
            tampilDataJenisBBM();
            table_jenis_bbm.clearSelection();
            JOptionPane.showMessageDialog(this, "Status jenis BBM berhasil diubah menjadi " + baru + ".");
        } catch (SQLException | IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(),
                    "Gagal Mengubah Status", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void openEditDialog(int row) {
        int id = Integer.parseInt(String.valueOf(table_jenis_bbm.getValueAt(row, 0)));
        String namaAwal = String.valueOf(table_jenis_bbm.getValueAt(row, 1));
        String namaBaru = JOptionPane.showInputDialog(this, "Nama Jenis BBM:", namaAwal);
        if (namaBaru == null) return;
        try {
            jenisBbmController.ubah(id, namaBaru, "Liter");
            tampilDataJenisBBM();
            JOptionPane.showMessageDialog(this, "Data jenis BBM berhasil diperbarui.");
        } catch (IllegalArgumentException | SQLException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Gagal Mengubah", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void toggleStatusJenisBBM(int row) {
        int id=(int)table_jenis_bbm.getValueAt(row,0);
        String sekarang=table_jenis_bbm.getValueAt(row,2).toString();
        String baru=sekarang.equalsIgnoreCase("AKTIF")?"NONAKTIF":"AKTIF";
        if(JOptionPane.showConfirmDialog(this,"Ubah status menjadi "+baru+"?","Konfirmasi Status",JOptionPane.YES_NO_OPTION)!=JOptionPane.YES_OPTION)return;
        try { jenisBbmController.ubahStatus(id,baru); tampilDataJenisBBM(); }
        catch(SQLException | IllegalArgumentException ex){JOptionPane.showMessageDialog(this,ex.getMessage(),"Gagal Mengubah Status",JOptionPane.ERROR_MESSAGE);}
    }

    public void deleteJenisBBM(int row) {
        int id=(int)table_jenis_bbm.getValueAt(row,0);
        String nama=table_jenis_bbm.getValueAt(row,1).toString();
        if(JOptionPane.showConfirmDialog(this,"Hapus jenis BBM "+nama+"? Jika masih digunakan oleh stok atau transaksi, statusnya akan diubah menjadi NONAKTIF agar riwayat tetap terjaga.","Konfirmasi Hapus",JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE)!=JOptionPane.YES_OPTION)return;
        try {
            int hasil = jenisBbmController.hapus(id);
            tampilDataJenisBBM();
            if (hasil == 1) JOptionPane.showMessageDialog(this, "Jenis BBM berhasil dihapus.");
            else if (hasil == 2) JOptionPane.showMessageDialog(this, "Jenis BBM masih digunakan oleh stok atau riwayat transaksi, sehingga statusnya diubah menjadi NONAKTIF.", "Jenis BBM Dinonaktifkan", JOptionPane.INFORMATION_MESSAGE);
            else JOptionPane.showMessageDialog(this, "Jenis BBM tidak ditemukan.", "Informasi", JOptionPane.WARNING_MESSAGE);
        } catch(SQLException ex){ JOptionPane.showMessageDialog(this,ex.getMessage(),"Kesalahan Database",JOptionPane.ERROR_MESSAGE); }
    }

    private class ActionPanelRenderer implements TableCellRenderer {

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            String status = table.getValueAt(row, 2).toString();
            return new ActionPanel(status);
        }
    }

    private class ActionPanelEditor extends AbstractCellEditor implements TableCellEditor {

        private final ActionPanel panel;

        public ActionPanelEditor(Jenis_BBM parentFrame, JTable table) {
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
                    parentFrame.toggleStatusJenisBBM(row);
                }
            });

            panel.btnDelete.addActionListener(e -> {
                int row = table.getSelectedRow();
                fireEditingStopped();
                if (row != -1) {
                    parentFrame.deleteJenisBBM(row);
                }
            });
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            String status = table.getValueAt(row, 2).toString();
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
        header = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        nama = new javax.swing.JLabel();
        sebagai = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        table_jenis_bbm = new javax.swing.JTable();
        navbar2 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        spbu2 = new javax.swing.JButton();
        dashboard3 = new javax.swing.JButton();
        jenisBBM2 = new javax.swing.JButton();
        lokasiPenyimpanan2 = new javax.swing.JButton();
        pengguna2 = new javax.swing.JButton();
        stokPenyimpanan2 = new javax.swing.JButton();
        stokSpbu2 = new javax.swing.JButton();
        penerimaan2 = new javax.swing.JButton();
        distribusi2 = new javax.swing.JButton();
        konfirmasi2 = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        Logout2 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        simpan = new javax.swing.JButton();
        nama_jenis_bbm = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        btnUbah = new javax.swing.JButton();
        btnHapus = new javax.swing.JButton();
        btnBatal = new javax.swing.JButton();

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

        header.setBackground(new java.awt.Color(255, 255, 255));
        header.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel7.setText("KELOLA JENIS BBM");

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
                .addGap(35, 35, 35)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 575, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36))
        );
        headerLayout.setVerticalGroup(
            headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(headerLayout.createSequentialGroup()
                .addGroup(headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(headerLayout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addComponent(jLabel7))
                    .addGroup(headerLayout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(23, Short.MAX_VALUE))
        );

        getContentPane().add(header);
        header.setBounds(312, 0, 1000, 104);

        table_jenis_bbm.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(table_jenis_bbm);

        getContentPane().add(jScrollPane1);
        jScrollPane1.setBounds(312, 296, 990, 402);

        navbar2.setBackground(new java.awt.Color(0, 51, 102));
        navbar2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel5.setBackground(new java.awt.Color(255, 255, 255));
        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("OCTANE");

        spbu2.setBackground(new java.awt.Color(0, 51, 102));
        spbu2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        spbu2.setForeground(new java.awt.Color(255, 255, 255));
        spbu2.setText("Kelola Data SPBU");
        spbu2.addActionListener(this::spbu2ActionPerformed);

        dashboard3.setBackground(new java.awt.Color(0, 51, 102));
        dashboard3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        dashboard3.setForeground(new java.awt.Color(255, 255, 255));
        dashboard3.setText("Dashboard");
        dashboard3.addActionListener(this::dashboard3ActionPerformed);

        jenisBBM2.setBackground(new java.awt.Color(0, 51, 102));
        jenisBBM2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jenisBBM2.setForeground(new java.awt.Color(255, 255, 255));
        jenisBBM2.setText("Kelola Jenis BBM");
        jenisBBM2.addActionListener(this::jenisBBM2ActionPerformed);

        lokasiPenyimpanan2.setBackground(new java.awt.Color(0, 51, 102));
        lokasiPenyimpanan2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lokasiPenyimpanan2.setForeground(new java.awt.Color(255, 255, 255));
        lokasiPenyimpanan2.setText("Kelola Lokasi Penyimpanan");
        lokasiPenyimpanan2.addActionListener(this::lokasiPenyimpanan2ActionPerformed);

        pengguna2.setBackground(new java.awt.Color(0, 51, 102));
        pengguna2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        pengguna2.setForeground(new java.awt.Color(255, 255, 255));
        pengguna2.setText("Kelola Akun Pengguna");
        pengguna2.addActionListener(this::pengguna2ActionPerformed);

        stokPenyimpanan2.setBackground(new java.awt.Color(0, 51, 102));
        stokPenyimpanan2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        stokPenyimpanan2.setForeground(new java.awt.Color(255, 255, 255));
        stokPenyimpanan2.setText("Stok Seluruh Lokasi");
        stokPenyimpanan2.addActionListener(this::stokPenyimpanan2ActionPerformed);

        stokSpbu2.setBackground(new java.awt.Color(0, 51, 102));
        stokSpbu2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        stokSpbu2.setForeground(new java.awt.Color(255, 255, 255));
        stokSpbu2.setText("Stok Seluruh SPBU");
        stokSpbu2.addActionListener(this::stokSpbu2ActionPerformed);

        penerimaan2.setBackground(new java.awt.Color(0, 51, 102));
        penerimaan2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        penerimaan2.setForeground(new java.awt.Color(255, 255, 255));
        penerimaan2.setText("Riwayat Penerimaan");
        penerimaan2.addActionListener(this::penerimaan2ActionPerformed);

        distribusi2.setBackground(new java.awt.Color(0, 51, 102));
        distribusi2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        distribusi2.setForeground(new java.awt.Color(255, 255, 255));
        distribusi2.setText("Riwayat Distribusi");
        distribusi2.addActionListener(this::distribusi2ActionPerformed);

        konfirmasi2.setBackground(new java.awt.Color(0, 51, 102));
        konfirmasi2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        konfirmasi2.setForeground(new java.awt.Color(255, 255, 255));
        konfirmasi2.setText("Laporan Persediaan dan Distribusi");
        konfirmasi2.addActionListener(this::konfirmasi2ActionPerformed);

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Fuel for a Better Tomorrow");

        Logout2.setText("Logout");
        Logout2.addActionListener(this::Logout2ActionPerformed);

        javax.swing.GroupLayout navbar2Layout = new javax.swing.GroupLayout(navbar2);
        navbar2.setLayout(navbar2Layout);
        navbar2Layout.setHorizontalGroup(
            navbar2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, navbar2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel5)
                .addGap(102, 102, 102))
            .addGroup(navbar2Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(navbar2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Logout2)
                    .addGroup(navbar2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(spbu2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(pengguna2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(stokPenyimpanan2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(stokSpbu2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(penerimaan2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(distribusi2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(konfirmasi2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(dashboard3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jenisBBM2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lokasiPenyimpanan2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap(8, Short.MAX_VALUE))
        );
        navbar2Layout.setVerticalGroup(
            navbar2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(navbar2Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6)
                .addGap(40, 40, 40)
                .addComponent(dashboard3, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(pengguna2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(spbu2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jenisBBM2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lokasiPenyimpanan2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(stokPenyimpanan2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(stokSpbu2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(penerimaan2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(distribusi2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(konfirmasi2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 46, Short.MAX_VALUE)
                .addComponent(Logout2)
                .addGap(16, 16, 16))
        );

        getContentPane().add(navbar2);
        navbar2.setBounds(0, 0, 294, 730);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        simpan.setBackground(new java.awt.Color(0, 51, 102));
        simpan.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        simpan.setForeground(new java.awt.Color(255, 255, 255));
        simpan.setText("Simpan");
        simpan.addActionListener(this::simpanActionPerformed);

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(204, 204, 204));
        jLabel8.setText("Nama Jenis BBM");

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel9.setText("Tambah Data Jenis BBM");

        btnUbah.setText("Ubah");
        btnUbah.addActionListener(this::btnUbahActionPerformed);

        btnHapus.setText("Hapus");
        btnHapus.addActionListener(this::btnHapusActionPerformed);

        btnBatal.setText("Ubah Status");
        btnBatal.addActionListener(this::btnBatalActionPerformed);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9)
                    .addComponent(jLabel8)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(340, 340, 340)
                        .addComponent(btnUbah, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnHapus, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnBatal, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(simpan, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(nama_jenis_bbm, javax.swing.GroupLayout.PREFERRED_SIZE, 853, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(115, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jLabel9)
                .addGap(14, 14, 14)
                .addComponent(jLabel8)
                .addGap(4, 4, 4)
                .addComponent(nama_jenis_bbm, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnUbah, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnHapus, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBatal, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(simpan, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(14, 14, 14))
        );

        getContentPane().add(jPanel2);
        jPanel2.setBounds(312, 122, 990, 168);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void spbu2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_spbu2ActionPerformed
        new SPBU().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_spbu2ActionPerformed

    private void dashboard3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dashboard3ActionPerformed
        new DashboardAdmin().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_dashboard3ActionPerformed

    private void jenisBBM2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jenisBBM2ActionPerformed
        new Jenis_BBM().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jenisBBM2ActionPerformed

    private void lokasiPenyimpanan2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lokasiPenyimpanan2ActionPerformed
        new Lokasi_Penyimpanan().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_lokasiPenyimpanan2ActionPerformed

    private void pengguna2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pengguna2ActionPerformed
        new Pengguna().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_pengguna2ActionPerformed

    private void stokPenyimpanan2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stokPenyimpanan2ActionPerformed
        new Stok_Penyimpanan().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_stokPenyimpanan2ActionPerformed

    private void stokSpbu2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stokSpbu2ActionPerformed
        new Stok_SPBU().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_stokSpbu2ActionPerformed

    private void penerimaan2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_penerimaan2ActionPerformed
        new Penerimaan().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_penerimaan2ActionPerformed

    private void distribusi2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_distribusi2ActionPerformed
        new Distribusi().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_distribusi2ActionPerformed

    private void konfirmasi2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_konfirmasi2ActionPerformed
        new Konfirmasi().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_konfirmasi2ActionPerformed

    private void btnUbahActionPerformed(java.awt.event.ActionEvent evt) {
        mulaiUbahJenisBBM();
    }

    private void btnHapusActionPerformed(java.awt.event.ActionEvent evt) {
        hapusJenisBBMTerpilih();
    }

    private void btnBatalActionPerformed(java.awt.event.ActionEvent evt) {
        if (idJenisBbmDipilih != null) {
            batalUbahJenisBBM();
        } else {
            ubahStatusJenisBBMTerpilih();
        }
    }

    private void simpanActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            if (idJenisBbmDipilih != null) {
                jenisBbmController.ubah(idJenisBbmDipilih, nama_jenis_bbm.getText(), "Liter");
                JOptionPane.showMessageDialog(this,"Data jenis BBM berhasil diperbarui.");
            } else {
                jenisBbmController.tambah(nama_jenis_bbm.getText(),"Liter");
                JOptionPane.showMessageDialog(this,"Data jenis BBM berhasil ditambahkan.");
            }
            batalUbahJenisBBM();
            tampilDataJenisBBM();
        } catch(IllegalArgumentException | SQLException ex){JOptionPane.showMessageDialog(this,ex.getMessage(),"Gagal Menambah",JOptionPane.ERROR_MESSAGE);}
    }                                      


    private void Logout2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Logout2ActionPerformed
        logout();
    }//GEN-LAST:event_Logout2ActionPerformed

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
        java.awt.EventQueue.invokeLater(() -> new Jenis_BBM().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Logout2;
    private javax.swing.JButton btnBatal;
    private javax.swing.JButton btnHapus;
    private javax.swing.JButton btnUbah;
    private javax.swing.JButton dashboard3;
    private javax.swing.JButton distribusi2;
    private javax.swing.JPanel header;
    private javax.swing.JFrame jFrame1;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton jenisBBM2;
    private javax.swing.JButton konfirmasi2;
    private javax.swing.JButton lokasiPenyimpanan2;
    private javax.swing.JLabel nama;
    private javax.swing.JTextField nama_jenis_bbm;
    private javax.swing.JPanel navbar2;
    private javax.swing.JButton penerimaan2;
    private javax.swing.JButton pengguna2;
    private javax.swing.JLabel sebagai;
    private javax.swing.JButton simpan;
    private javax.swing.JButton spbu2;
    private javax.swing.JButton stokPenyimpanan2;
    private javax.swing.JButton stokSpbu2;
    private javax.swing.JTable table_jenis_bbm;
    // End of variables declaration//GEN-END:variables
}
