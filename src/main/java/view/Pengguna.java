package view;

import config.Koneksi;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import controller.PenggunaController;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.PetugasOperasionalModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.table.TableColumn;

public class Pengguna extends javax.swing.JFrame {

    private final PenggunaController penggunaController = new PenggunaController();

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(Pengguna.class.getName());
    
    public Pengguna() {
        initComponents();
        TampilanTabel.rapikanSemua(this);

        setExtendedState(javax.swing.JFrame.NORMAL);
        setSize(1366, 768);
        setLocationRelativeTo(null);
        setResizable(false);

        table_pengguna.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        table_pengguna.setRowHeight(30);
        table_pengguna.setSelectionBackground(new java.awt.Color(187, 212, 235));
        table_pengguna.setSelectionForeground(new java.awt.Color(25, 45, 65));
        table_pengguna.getTableHeader().setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12));
        table_pengguna.getTableHeader().setBackground(new java.awt.Color(225, 235, 245));


        tampilIdentitas();
        aturTabelPengguna();
        muatDataPengguna();
        simpan.addActionListener(evt -> simpanPengguna());
        peran.addActionListener(evt -> muatPilihanPenempatan());
        table_pengguna.addMouseListener(new MouseAdapter() {
            @Override public void mouseClicked(MouseEvent e) {
                int rowView=table_pengguna.rowAtPoint(e.getPoint());
                int colView=table_pengguna.columnAtPoint(e.getPoint());
                if(rowView>=0 && colView==8) {
                    int row=table_pengguna.convertRowIndexToModel(rowView);
                    int id=Integer.parseInt(String.valueOf(table_pengguna.getModel().getValueAt(row,0)));
                    ubahStatusPengguna(id,String.valueOf(table_pengguna.getModel().getValueAt(row,8)));
                }
            }
        });

        btnUbah.addActionListener(evt -> ubahDariTombol());
        btnHapus.addActionListener(evt -> hapusDariTombol());
        cmbFilterPeran.addActionListener(evt -> terapkanFilterPeran());
        table_pengguna.getSelectionModel().addListSelectionListener(evt -> {
            if (!evt.getValueIsAdjusting()) perbaruiStatusTombol();
        });
        perbaruiStatusTombol();

        muatPilihanPenempatan();
    }


    /**
     * Menyiapkan kolom tabel pada komponen JTable yang sudah tersedia di Form Builder.
     * Tidak membuat komponen GUI baru dan tidak mengubah struktur .form.
     */
    private void aturTabelPengguna() {
        DefaultTableModel model = new DefaultTableModel(
                new Object[]{"ID", "Nama", "Username", "Peran", "Penempatan", "Tanggal Pengangkatan", "Kualifikasi / Sertifikasi", "Status Pelatihan", "Status"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table_pengguna.setModel(model);
            TampilanTabel.rapikan(table_pengguna);
        table_pengguna.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
    }

    private String nilaiTabel(String nilai) {
        return nilai == null || nilai.isBlank() ? "-" : nilai;
    }

    /**
     * Mengambil data melalui Controller, bukan menjalankan SQL dari View.
     */
    private void muatDataPengguna() {
        DefaultTableModel model = (DefaultTableModel) table_pengguna.getModel();
        model.setRowCount(0);

        try {
            List<model.Pengguna> daftarPengguna = penggunaController.getAllPengguna();
            List<PetugasOperasionalModel> daftarPetugas = penggunaController.getAllPetugas();

            Map<Integer, String> penempatanPetugas = new HashMap<>();
            for (PetugasOperasionalModel petugas : daftarPetugas) {
                String penempatan = petugas.getNamaPenempatan() == null
                        ? "-" : petugas.getNamaPenempatan();
                penempatanPetugas.put(petugas.getIdPengguna(), penempatan);
            }

            for (model.Pengguna penggunaData : daftarPengguna) {
                String penempatan = penempatanPetugas.getOrDefault(
                        penggunaData.getIdPengguna(), "-");
                String status = penggunaData.getStatusAktif() == null ? "AKTIF" : penggunaData.getStatusAktif();
                model.addRow(new Object[]{
                    penggunaData.getIdPengguna(),
                    penggunaData.getNamaPengguna(),
                    penggunaData.getUsername(),
                    penggunaData.getRole(),
                    penempatan,
                    nilaiTabel(penggunaData.getTanggalPengangkatan()),
                    nilaiTabel(penggunaData.getKualifikasiSertifikasi()),
                    nilaiTabel(penggunaData.getStatusPelatihanPenerimaan()),
                    status
                });
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Data pengguna gagal dimuat.\nPeriksa koneksi database dan struktur data.\n\n"
                    + "Detail: " + ex.getMessage(),
                    "Kesalahan Memuat Data", JOptionPane.ERROR_MESSAGE);
        }
    }
 
    private Integer idPenggunaSedangDiubah = null;

    private void muatPilihanPenempatan() {
        String jenis = peran.getSelectedItem() == null
                ? "" : peran.getSelectedItem().toString().trim().toUpperCase();

        penempatan.removeAllItems();
        penempatan.addItem("Pilih penempatan...");
        aturInputAtributPeran(jenis);

        // Administrator tidak memiliki penempatan operasional.
        boolean perluPenempatan = "PETUGAS PENYIMPANAN".equals(jenis)
                || "PETUGAS SPBU".equals(jenis);
        penempatan.setEnabled(perluPenempatan && idPenggunaSedangDiubah == null);

        if (!perluPenempatan) {
            return;
        }

        try {
            List<String> daftarPenempatan = penggunaController.getPenempatanAktif(jenis);
            for (String item : daftarPenempatan) {
                penempatan.addItem(item);
            }
            if (daftarPenempatan.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Belum ada " + ("PETUGAS SPBU".equals(jenis) ? "SPBU" : "lokasi penyimpanan")
                        + " yang berstatus AKTIF.",
                        "Penempatan Tidak Tersedia", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Daftar penempatan gagal dimuat: " + ex.getMessage(),
                    "Kesalahan Database", JOptionPane.ERROR_MESSAGE);
        }
    }
   
    private void aturInputAtributPeran(String jenis) {
        if ("ADMINISTRATOR".equals(jenis)) {
            jLabelAtribut.setText("Tanggal Pengangkatan");
            atributPeran.setToolTipText("Format tanggal: YYYY-MM-DD");
            atributPeran.setText("");
            atributPeran.setEnabled(idPenggunaSedangDiubah == null);
        } else if ("PETUGAS PENYIMPANAN".equals(jenis)) {
            jLabelAtribut.setText("Kualifikasi / Sertifikasi");
            atributPeran.setToolTipText("Wajib diisi, maksimal 100 karakter");
            atributPeran.setText("");
            atributPeran.setEnabled(idPenggunaSedangDiubah == null);
        } else if ("PETUGAS SPBU".equals(jenis)) {
            jLabelAtribut.setText("Status Pelatihan (LULUS/BELUM)");
            atributPeran.setToolTipText("Isi LULUS atau BELUM");
            atributPeran.setText("BELUM");
            atributPeran.setEnabled(idPenggunaSedangDiubah == null);
        } else {
            jLabelAtribut.setText("Atribut Peran");
            atributPeran.setText("");
            atributPeran.setEnabled(false);
        }
    }

    private void simpanPengguna() {
        String nm=nama_lengkap.getText().trim(), usr=username.getText().trim();
        String role=peran.getSelectedItem() == null ? "" : peran.getSelectedItem().toString().trim().toUpperCase();
        String tempat=String.valueOf(penempatan.getSelectedItem());
        String passwordBaru = new String(password.getPassword()).trim();
        if(nm.isEmpty()||usr.isEmpty()||role.isEmpty()||role.equals("PILIH PERAN...")) {
            JOptionPane.showMessageDialog(this,"Nama, username, dan peran wajib diisi.");
            return;
        }
        try {
            if(idPenggunaSedangDiubah!=null) {
                String atribut = atributPeran.getText().trim();
                penggunaController.updateDataAkunDanAtributPeran(
                        idPenggunaSedangDiubah, nm, usr, passwordBaru, atribut, role);
                JOptionPane.showMessageDialog(this, passwordBaru.isEmpty()
                        ? "Data pengguna dan atribut peran berhasil diperbarui. Password tetap."
                        : "Data pengguna, password, dan atribut peran berhasil diperbarui.");
            } else {
                if(passwordBaru.isEmpty()) {
                    JOptionPane.showMessageDialog(this,"Password wajib diisi untuk akun baru.");
                    return;
                }
                String atribut = atributPeran.getText().trim();
                if("ADMINISTRATOR".equals(role)) {
                    penggunaController.tambahAdministrator(nm,usr,passwordBaru,atribut);
                    JOptionPane.showMessageDialog(this,"Akun administrator berhasil dibuat.");
                } else if("PETUGAS PENYIMPANAN".equals(role)||"PETUGAS SPBU".equals(role)) {
                    if(tempat==null||tempat.equals("Pilih penempatan...")) {
                        JOptionPane.showMessageDialog(this,"Pilih penempatan aktif terlebih dahulu."); return;
                    }
                    penggunaController.tambahPetugasDenganNamaPenempatan(nm,usr,passwordBaru,role,tempat,atribut);
                    JOptionPane.showMessageDialog(this,"Akun petugas berhasil dibuat.");
                } else {
                    JOptionPane.showMessageDialog(this,"Peran tidak valid."); return;
                }
            }
            resetFormPengguna(); muatDataPengguna();
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(this,"Proses gagal: "+ex.getMessage(),"Kesalahan",JOptionPane.ERROR_MESSAGE);
        }
    }


    private void ubahDariTombol() {
        int rowView=table_pengguna.getSelectedRow();
        if(rowView<0) {
            JOptionPane.showMessageDialog(this,"Pilih satu akun pada tabel terlebih dahulu.");
            return;
        }
        int row=table_pengguna.convertRowIndexToModel(rowView);
        DefaultTableModel m=(DefaultTableModel)table_pengguna.getModel();
        idPenggunaSedangDiubah=Integer.valueOf(String.valueOf(m.getValueAt(row,0)));
        nama_lengkap.setText(String.valueOf(m.getValueAt(row,1)));
        username.setText(String.valueOf(m.getValueAt(row,2)));
        peran.setSelectedItem(String.valueOf(m.getValueAt(row,3)));
        muatPilihanPenempatan();
        Object lokasi=m.getValueAt(row,4);
        if(lokasi!=null && !"-".equals(lokasi.toString())) penempatan.setSelectedItem(lokasi.toString());
        String roleTerpilih = String.valueOf(m.getValueAt(row,3)).trim().toUpperCase();
        int kolomAtribut = "ADMINISTRATOR".equals(roleTerpilih) ? 5
                : "PETUGAS PENYIMPANAN".equals(roleTerpilih) ? 6 : 7;
        jLabelAtribut.setText("ADMINISTRATOR".equals(roleTerpilih) ? "Tanggal Pengangkatan"
                : "PETUGAS PENYIMPANAN".equals(roleTerpilih) ? "Kualifikasi / Sertifikasi"
                : "Status Pelatihan (LULUS/BELUM)");
        Object nilaiAtribut = m.getValueAt(row, kolomAtribut);
        atributPeran.setText(nilaiAtribut == null || "-".equals(nilaiAtribut.toString()) ? "" : nilaiAtribut.toString());
        atributPeran.setEnabled(true);
        jLabel4.setText("Ubah Data Pengguna");
        jLabelPassword.setText("Password Baru (opsional)");
        simpan.setText("Simpan Perubahan");
        simpan.setToolTipText("Simpan perubahan identitas pengguna");
        btnBatal.setVisible(true);
        peran.setEnabled(false);
        penempatan.setEnabled(false);
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(
                javax.swing.BorderFactory.createLineBorder(new java.awt.Color(30, 100, 160)),
                "Mode Ubah Data"));
    }

    private void hapusDariTombol() {
        int rowView=table_pengguna.getSelectedRow();
        if(rowView<0) {
            JOptionPane.showMessageDialog(this,"Pilih satu akun pada tabel terlebih dahulu.");
            return;
        }
        int row=table_pengguna.convertRowIndexToModel(rowView);
        int id=Integer.parseInt(String.valueOf(table_pengguna.getModel().getValueAt(row,0)));
        hapusPenggunaTerpilih(id);
    }

    private void perbaruiStatusTombol() {
        boolean dipilih=table_pengguna.getSelectedRow()>=0;
        btnUbah.setEnabled(dipilih);
        btnHapus.setEnabled(dipilih);
    }

    private void terapkanFilterPeran() {
        String filter=String.valueOf(cmbFilterPeran.getSelectedItem());
        javax.swing.table.TableRowSorter<DefaultTableModel> sorter =
                new javax.swing.table.TableRowSorter<>((DefaultTableModel)table_pengguna.getModel());
        table_pengguna.setRowSorter(sorter);
        if("Semua Peran".equals(filter)) sorter.setRowFilter(null);
        else sorter.setRowFilter(javax.swing.RowFilter.regexFilter("^"+java.util.regex.Pattern.quote(filter)+"$",3));
    }

    private void hapusPenggunaTerpilih(int id) {
        if (id == Session.idPengguna) {
            JOptionPane.showMessageDialog(this, "Akun yang sedang digunakan tidak boleh dihapus.",
                    "Penghapusan Ditolak", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int ok=JOptionPane.showConfirmDialog(this,
          "Hapus akun ID "+id+"? Jika akun sudah memiliki riwayat transaksi, akun akan otomatis dinonaktifkan agar riwayat tetap tersimpan.",
          "Konfirmasi Hapus",JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE);
        if(ok!=JOptionPane.YES_OPTION)return;
        try {
            int hasil=penggunaController.hapusPengguna(id);
            if(hasil==1) JOptionPane.showMessageDialog(this,"Akun berhasil dihapus.");
            else if(hasil==2) JOptionPane.showMessageDialog(this,
              "Akun memiliki riwayat transaksi sehingga tidak dihapus. Status akun berhasil diubah menjadi NONAKTIF.",
              "Akun Dinonaktifkan",JOptionPane.INFORMATION_MESSAGE);
            else JOptionPane.showMessageDialog(this,"Akun tidak ditemukan.","Informasi",JOptionPane.WARNING_MESSAGE);
            muatDataPengguna();
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(this,
              "Terjadi kendala saat menghapus akun: "+ex.getMessage(),
              "Penghapusan Ditolak",JOptionPane.WARNING_MESSAGE);
        }
    }

    private void ubahStatusPengguna(int id,String status) {
        boolean aktif=!"AKTIF".equalsIgnoreCase(status);
        int ok=JOptionPane.showConfirmDialog(this,(aktif?"Aktifkan":"Nonaktifkan")+" akun ini?",
                "Konfirmasi Status",JOptionPane.YES_NO_OPTION);
        if(ok!=JOptionPane.YES_OPTION)return;
        try { penggunaController.ubahStatusPengguna(id,aktif); muatDataPengguna(); }
        catch(Exception ex){JOptionPane.showMessageDialog(this,ex.getMessage());}
    }

    private void resetFormPengguna() {
        idPenggunaSedangDiubah=null;
        nama_lengkap.setText(""); username.setText(""); password.setText(""); peran.setSelectedIndex(0);
        penempatan.setSelectedIndex(0); penempatan.setEnabled(true); peran.setEnabled(true);
        simpan.setText("Simpan");
        jLabel4.setText("Tambah Data Pengguna");
        jLabelPassword.setText("Password");
        jLabelAtribut.setText("Atribut Peran");
        atributPeran.setEnabled(false);
        btnBatal.setVisible(false);
        jPanel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
    }

    private void tampilIdentitas() {
        nama.setText(Session.namaLengkap);
        sebagai.setText(Session.peran + " · " + Session.lokasi);
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
        jScrollPane1 = new javax.swing.JScrollPane();
        table_spbu = new javax.swing.JTable();
        header = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        nama = new javax.swing.JLabel();
        sebagai = new javax.swing.JLabel();
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
        jScrollPane2 = new javax.swing.JScrollPane();
        table_pengguna = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        simpan = new javax.swing.JButton();
        nama_lengkap = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        username = new javax.swing.JTextField();
        jLabelPassword = new javax.swing.JLabel();
        password = new javax.swing.JPasswordField();
        peran = new javax.swing.JComboBox();
        penempatan = new javax.swing.JComboBox();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabelAtribut = new javax.swing.JLabel();
        atributPeran = new javax.swing.JTextField();
        cmbFilterPeran = new javax.swing.JComboBox();
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

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        header.setBackground(new java.awt.Color(255, 255, 255));
        header.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel7.setText("KELOLA AKUN PENGGUNA");

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
                .addContainerGap(83, Short.MAX_VALUE)
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
                .addGap(32, 32, 32)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 536, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(44, 44, 44))
        );
        headerLayout.setVerticalGroup(
            headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(headerLayout.createSequentialGroup()
                .addGroup(headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(headerLayout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addComponent(jLabel7))
                    .addGroup(headerLayout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(21, Short.MAX_VALUE))
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 105, Short.MAX_VALUE)
                .addComponent(Logout)
                .addGap(27, 27, 27))
        );

        table_pengguna.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(table_pengguna);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        simpan.setBackground(new java.awt.Color(0, 51, 102));
        simpan.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        simpan.setForeground(new java.awt.Color(255, 255, 255));
        simpan.setText("Simpan");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(204, 204, 204));
        jLabel3.setText("Nama Lengkap");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel4.setText("Tambah Data Pengguna");

        jLabelPassword.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        jLabelPassword.setForeground(new java.awt.Color(204, 204, 204));
        jLabelPassword.setText("Password");

        password.setToolTipText("Wajib diisi untuk akun baru; kosongkan jika tidak ingin mengubah password saat edit");

        peran.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Pilih peran...", "ADMINISTRATOR", "PETUGAS PENYIMPANAN", "PETUGAS SPBU" }));

        penempatan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Pilih penempatan..." }));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(204, 204, 204));
        jLabel5.setText("Username");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(204, 204, 204));
        jLabel6.setText("Peran");

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(204, 204, 204));
        jLabel8.setText("Penempatan");

        jLabelAtribut.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        jLabelAtribut.setForeground(new java.awt.Color(204, 204, 204));
        jLabelAtribut.setText("Atribut Peran");

        cmbFilterPeran.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Semua Peran", "ADMINISTRATOR", "PETUGAS PENYIMPANAN", "PETUGAS SPBU" }));

        btnUbah.setText("Ubah");
        btnUbah.setBackground(new java.awt.Color(30, 136, 229));
        btnUbah.setForeground(new java.awt.Color(255, 255, 255));

        btnHapus.setText("Hapus");
        btnHapus.setBackground(new java.awt.Color(198, 40, 40));
        btnHapus.setForeground(new java.awt.Color(255, 255, 255));

        btnBatal.setText("Batal");
        btnBatal.setBackground(new java.awt.Color(117, 117, 117));
        btnBatal.setForeground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(nama_lengkap, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(8, 8, 8)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(username, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(8, 8, 8)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabelPassword)
                            .addComponent(password, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(8, 8, 8)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(peran, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(8, 8, 8)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8)
                            .addComponent(penempatan, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jLabelAtribut)
                    .addComponent(atributPeran, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(cmbFilterPeran, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(110, 110, Short.MAX_VALUE)
                        .addComponent(btnUbah, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(btnHapus, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(btnBatal, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(simpan, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(24, 24, 24))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLabel4)
                .addGap(8, 8, 8)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel5)
                    .addComponent(jLabelPassword)
                    .addComponent(jLabel6)
                    .addComponent(jLabel8))
                .addGap(4, 4, 4)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nama_lengkap, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(username, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(password, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(peran, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(penempatan, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(8, 8, 8)
                .addComponent(jLabelAtribut)
                .addGap(4, 4, 4)
                .addComponent(atributPeran, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbFilterPeran, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnUbah, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnHapus, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBatal, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(simpan, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(navbar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(header, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(43, 43, 43)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane2)
                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(header, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(navbar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
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

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new Pengguna().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Logout;
    private javax.swing.JTextField atributPeran;
    private javax.swing.JButton btnBatal;
    private javax.swing.JButton btnHapus;
    private javax.swing.JButton btnUbah;
    private javax.swing.JComboBox cmbFilterPeran;
    private javax.swing.JButton dashboard1;
    private javax.swing.JButton distribusi;
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
    private javax.swing.JLabel jLabelAtribut;
    private javax.swing.JLabel jLabelPassword;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JButton jenisBBM;
    private javax.swing.JButton konfirmasi;
    private javax.swing.JButton lokasiPenyimpanan;
    private javax.swing.JLabel nama;
    private javax.swing.JTextField nama_lengkap;
    private javax.swing.JPanel navbar;
    private javax.swing.JPasswordField password;
    private javax.swing.JComboBox penempatan;
    private javax.swing.JButton penerimaan;
    private javax.swing.JButton pengguna;
    private javax.swing.JComboBox peran;
    private javax.swing.JLabel sebagai;
    private javax.swing.JButton simpan;
    private javax.swing.JButton spbu;
    private javax.swing.JButton stokPenyimpanan;
    private javax.swing.JButton stokSpbu;
    private javax.swing.JTable table_pengguna;
    private javax.swing.JTable table_spbu;
    private javax.swing.JTextField username;
    // End of variables declaration//GEN-END:variables
}
