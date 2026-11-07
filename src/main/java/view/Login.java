package view;

import controller.LoginController;
import model.PenggunaLogin;

import java.awt.Dimension;
import javax.swing.JOptionPane;

public class Login extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(Login.class.getName());

    public Login() {
        initComponents();
        TampilanTabel.rapikanSemua(this);
        setSize(1000, 620);
        setMinimumSize(new Dimension(800, 500));
        setLocationRelativeTo(null);
        setTitle("OCTANE - Login");
        getRootPane().setDefaultButton(masuk);
        java.awt.EventQueue.invokeLater(() -> username.requestFocusInWindow());
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        username = new javax.swing.JTextField();
        password = new javax.swing.JPasswordField();
        masuk = new javax.swing.JButton();
        tampilkanPassword = new javax.swing.JCheckBox();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jPanel2.setBackground(new java.awt.Color(204, 102, 0));
        jPanel2.setPreferredSize(new java.awt.Dimension(400, 620));

        jLabel4.setBackground(new java.awt.Color(255, 255, 255));
        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("OCTANE");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Fuel for a Better Tomorrow");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(82, 82, 82)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(116, 116, 116)
                        .addComponent(jLabel4)))
                .addContainerGap(97, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(248, 248, 248)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel5)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        username.setPreferredSize(new java.awt.Dimension(300, 32));
        username.addActionListener(this::usernameActionPerformed);

        password.setPreferredSize(new java.awt.Dimension(300, 32));
        password.addActionListener(this::passwordActionPerformed);

        masuk.setBackground(new java.awt.Color(204, 102, 0));
        masuk.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        masuk.setForeground(new java.awt.Color(255, 255, 255));
        masuk.setText("Masuk");
        masuk.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        masuk.addActionListener(this::masukActionPerformed);

        tampilkanPassword.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        tampilkanPassword.setForeground(new java.awt.Color(204, 204, 204));
        tampilkanPassword.setText("Tampilkan Password");
        tampilkanPassword.addActionListener(this::tampilkanPasswordActionPerformed);

        jLabel1.setText("Password");

        jLabel2.setText("Username");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel3.setText("LOGIN");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 118, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(username, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(password, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
                                .addComponent(masuk, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(tampilkanPassword)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2))
                        .addGap(82, 82, 82))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(229, 229, 229))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(79, 79, 79)
                .addComponent(jLabel3)
                .addGap(52, 52, 52)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(username, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(password, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(tampilkanPassword)
                .addGap(46, 46, 46)
                .addComponent(masuk, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(147, Short.MAX_VALUE))
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void usernameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_usernameActionPerformed

    }//GEN-LAST:event_usernameActionPerformed

    private void passwordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_passwordActionPerformed

    }//GEN-LAST:event_passwordActionPerformed

    private void masukActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_masukActionPerformed
        prosesLogin();
    }//GEN-LAST:event_masukActionPerformed

    private void tampilkanPasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tampilkanPasswordActionPerformed
        if (tampilkanPassword.isSelected()) {
            password.setEchoChar((char) 0);
        } else {
            password.setEchoChar('\u2022');
        }
    }//GEN-LAST:event_tampilkanPasswordActionPerformed

    private void prosesLogin() {
        String userInput = username.getText().trim();
        String passwordInput = new String(password.getPassword());

        if (userInput.isEmpty() || passwordInput.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Username dan password wajib diisi.",
                    "Login Belum Lengkap", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Session.clear();
        final PenggunaLogin pengguna;
        try {
            pengguna = new LoginController().login(userInput, passwordInput);
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(),
                    "Validasi Login", JOptionPane.WARNING_MESSAGE);
            return;
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Autentikasi gagal mengakses database: " + ex.getMessage(),
                    "Kesalahan Database", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (pengguna == null) {
            JOptionPane.showMessageDialog(this,
                    "Username/password salah, akun tidak aktif, atau peran belum terdaftar.",
                    "Login Gagal", JOptionPane.ERROR_MESSAGE);
            password.setText("");
            password.requestFocusInWindow();
            return;
        }

        Session.idPengguna = pengguna.getIdPengguna();
        Session.namaLengkap = pengguna.getNamaLengkap();
        Session.username = pengguna.getUsername();
        Session.peran = pengguna.getPeran();
        Session.lokasi = pengguna.getLokasi();
        Session.idLokasiPenyimpanan = pengguna.getIdLokasiPenyimpanan();
        Session.idSpbu = pengguna.getIdSpbu();

        try {
            javax.swing.JFrame dashboard;
            switch (pengguna.getPeran()) {
                case "ADMINISTRATOR" -> dashboard = new DashboardAdmin();
                case "PETUGAS PENYIMPANAN" -> dashboard = new DashboardPenyimpanan();
                case "PETUGAS SPBU" -> dashboard = new DashboardSPBU();
                default -> {
                    Session.clear();
                    JOptionPane.showMessageDialog(this,
                            "Peran pengguna tidak dikenali.",
                            "Login Gagal", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
            dashboard.setVisible(true);
            dispose();
        } catch (RuntimeException ex) {
            Session.clear();
            java.util.logging.Logger.getLogger(Login.class.getName()).log(
                    java.util.logging.Level.SEVERE, "Login berhasil tetapi dashboard gagal dibuka.", ex);
            JOptionPane.showMessageDialog(this,
                    "Akun berhasil diautentikasi, tetapi dashboard gagal dibuka: "
                    + ex.getMessage()
                    + "\nPeriksa konfigurasi komponen pada dashboard.",
                    "Kesalahan Tampilan Dashboard", JOptionPane.ERROR_MESSAGE);
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

        java.awt.EventQueue.invokeLater(() -> new Login().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JButton masuk;
    private javax.swing.JPasswordField password;
    private javax.swing.JCheckBox tampilkanPassword;
    private javax.swing.JTextField username;
    // End of variables declaration//GEN-END:variables
}
