package librarymanagement.view;

import librarymanagement.controller.LoginController;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;

/**
 * LoginForm - Tampilan login modern dengan split panel design
 */
public class LoginForm extends JFrame {

    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnLogin;
    private JCheckBox chkShowPassword;
    private LoginController controller;

    // Palet warna
    private static final Color C_NAVY      = new Color(15, 32, 65);
    private static final Color C_BLUE      = new Color(30, 90, 168);
    private static final Color C_ACCENT    = new Color(0, 188, 212);
    private static final Color C_WHITE     = Color.WHITE;
    private static final Color C_GRAY_DARK = new Color(55, 65, 81);
    private static final Color C_GRAY_MID  = new Color(107, 114, 128);
    private static final Color C_GRAY_LITE = new Color(243, 244, 246);
    private static final Color C_BORDER    = new Color(209, 213, 219);
    private static final Color C_SUCCESS   = new Color(16, 185, 129);

    public LoginForm() {
        initComponents();
        controller = new LoginController(this);
    }

    private void initComponents() {
        setTitle("Sistem Manajemen Perpustakaan");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 580);
        setLocationRelativeTo(null);
        setResizable(false);
        setUndecorated(false);

        // ================================================================
        // ROOT PANEL
        // ================================================================
        JPanel root = new JPanel(new GridLayout(1, 2, 0, 0));
        root.setBackground(C_NAVY);

        // ================================================================
        // KIRI — Panel Branding (gradient + dekorasi)
        // ================================================================
        JPanel leftPanel = new JPanel(null) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Gradient latar
                GradientPaint gp = new GradientPaint(
                    0, 0,          new Color(15, 32, 65),
                    0, getHeight(), new Color(10, 75, 155)
                );
                g2.setPaint(gp);
                g2.fillRect(0, 0, getWidth(), getHeight());

                // Lingkaran dekorasi besar (kiri bawah)
                g2.setColor(new Color(255, 255, 255, 15));
                g2.fillOval(-80, getHeight() - 300, 400, 400);

                // Lingkaran kecil (kanan atas)
                g2.setColor(new Color(0, 188, 212, 25));
                g2.fillOval(getWidth() - 120, -60, 220, 220);

                // Garis dekoratif horizontal
                g2.setColor(new Color(255, 255, 255, 30));
                g2.setStroke(new BasicStroke(1.5f));
                for (int i = 0; i < 5; i++) {
                    g2.drawLine(30, 80 + i * 7, getWidth() - 30, 80 + i * 7);
                }
            }
        };
        leftPanel.setOpaque(false);

        // Ikon buku besar
        JLabel lblIcon = new JLabel("📚", SwingConstants.CENTER);
        lblIcon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 72));
        lblIcon.setBounds(0, 130, 450, 100);
        leftPanel.add(lblIcon);

        // Nama aplikasi
        JLabel lblAppName = new JLabel("PERPUSTAKAAN", SwingConstants.CENTER);
        lblAppName.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblAppName.setForeground(C_WHITE);
        lblAppName.setBounds(0, 240, 450, 40);
        leftPanel.add(lblAppName);

        // Tagline
        JLabel lblTagline = new JLabel("Sistem Manajemen Digital", SwingConstants.CENTER);
        lblTagline.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblTagline.setForeground(new Color(180, 210, 255));
        lblTagline.setBounds(0, 285, 450, 25);
        leftPanel.add(lblTagline);

        // Garis aksen bawah judul
        JPanel accentLine = new JPanel() {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                GradientPaint gp = new GradientPaint(0, 0, C_ACCENT, getWidth(), 0, new Color(100,200,255,0));
                g2.setPaint(gp);
                g2.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        accentLine.setBounds(160, 320, 130, 3);
        accentLine.setOpaque(false);
        leftPanel.add(accentLine);

        // 3 fitur highlight
        String[] features = {"✓  Manajemen Buku & Kategori", "✓  Transaksi Peminjaman", "✓  Laporan & Statistik"};
        for (int i = 0; i < features.length; i++) {
            JLabel lbl = new JLabel(features[i]);
            lbl.setFont(new Font("Segoe UI", Font.PLAIN, 13));
            lbl.setForeground(new Color(160, 200, 255));
            lbl.setBounds(95, 350 + i * 30, 300, 25);
            leftPanel.add(lbl);
        }

        // Copyright
        JLabel lblCopy = new JLabel("© 2024 Tugas Kelompok · All Rights Reserved", SwingConstants.CENTER);
        lblCopy.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        lblCopy.setForeground(new Color(100, 140, 200));
        lblCopy.setBounds(0, 520, 450, 20);
        leftPanel.add(lblCopy);

        // ================================================================
        // KANAN — Panel Form Login
        // ================================================================
        JPanel rightPanel = new JPanel(null);
        rightPanel.setBackground(C_WHITE);

        // Header form
        JLabel lblWelcome = new JLabel("Selamat Datang!");
        lblWelcome.setFont(new Font("Segoe UI", Font.BOLD, 26));
        lblWelcome.setForeground(C_GRAY_DARK);
        lblWelcome.setBounds(55, 80, 340, 38);
        rightPanel.add(lblWelcome);

        JLabel lblSubWelcome = new JLabel("Silakan masuk untuk melanjutkan");
        lblSubWelcome.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblSubWelcome.setForeground(C_GRAY_MID);
        lblSubWelcome.setBounds(55, 120, 340, 22);
        rightPanel.add(lblSubWelcome);

        // Divider
        JSeparator divider = new JSeparator();
        divider.setForeground(C_BORDER);
        divider.setBounds(55, 150, 340, 1);
        rightPanel.add(divider);

        // Label Username
        JLabel lblU = new JLabel("Username");
        lblU.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblU.setForeground(C_GRAY_DARK);
        lblU.setBounds(55, 175, 200, 20);
        rightPanel.add(lblU);

        // TextField username dengan border kustom
        txtUsername = new JTextField() {
            @Override protected void paintBorder(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(hasFocus() ? C_BLUE : C_BORDER);
                g2.setStroke(new BasicStroke(hasFocus() ? 2 : 1));
                g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 8, 8);
            }
        };
        txtUsername.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtUsername.setOpaque(false);
        txtUsername.setBorder(new EmptyBorder(8, 14, 8, 14));
        txtUsername.setBounds(55, 200, 340, 44);
        txtUsername.setBackground(C_GRAY_LITE);
        rightPanel.add(txtUsername);

        // Label password
        JLabel lblP = new JLabel("Password");
        lblP.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblP.setForeground(C_GRAY_DARK);
        lblP.setBounds(55, 260, 200, 20);
        rightPanel.add(lblP);

        // TextField password
        txtPassword = new JPasswordField() {
            @Override protected void paintBorder(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(hasFocus() ? C_BLUE : C_BORDER);
                g2.setStroke(new BasicStroke(hasFocus() ? 2 : 1));
                g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 8, 8);
            }
        };
        txtPassword.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtPassword.setEchoChar('●');
        txtPassword.setOpaque(false);
        txtPassword.setBorder(new EmptyBorder(8, 14, 8, 14));
        txtPassword.setBounds(55, 285, 340, 44);
        txtPassword.setBackground(C_GRAY_LITE);
        rightPanel.add(txtPassword);

        // Checkbox show password
        chkShowPassword = new JCheckBox("Tampilkan password");
        chkShowPassword.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        chkShowPassword.setForeground(C_GRAY_MID);
        chkShowPassword.setBackground(C_WHITE);
        chkShowPassword.setBounds(55, 335, 180, 20);
        chkShowPassword.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        rightPanel.add(chkShowPassword);

        // Tombol MASUK
        btnLogin = new JButton("MASUK") {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                Color base = getModel().isPressed() ? C_BLUE.darker()
                           : getModel().isRollover() ? new Color(20, 80, 160) : C_BLUE;
                g2.setColor(base);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);

                // Teks tombol
                g2.setFont(getFont());
                g2.setColor(C_WHITE);
                FontMetrics fm = g2.getFontMetrics();
                int tx = (getWidth() - fm.stringWidth(getText())) / 2;
                int ty = (getHeight() + fm.getAscent() - fm.getDescent()) / 2;
                g2.drawString(getText(), tx, ty);
                g2.dispose();
            }
        };
        btnLogin.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnLogin.setForeground(C_WHITE);
        btnLogin.setOpaque(false);
        btnLogin.setContentAreaFilled(false);
        btnLogin.setBorderPainted(false);
        btnLogin.setBounds(55, 370, 340, 48);
        btnLogin.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnLogin.setFocusPainted(false);
        rightPanel.add(btnLogin);

        // Info default credentials
        JPanel infoBox = new JPanel(null) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setColor(new Color(219, 234, 254));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                g2.setColor(new Color(147, 197, 253));
                g2.setStroke(new BasicStroke(1));
                g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 8, 8);
            }
        };
        infoBox.setOpaque(false);
        infoBox.setBounds(55, 430, 340, 78);

        JLabel lblInfoTitle = new JLabel("ℹ  Akun Default:");
        lblInfoTitle.setFont(new Font("Segoe UI", Font.BOLD, 11));
        lblInfoTitle.setForeground(new Color(29, 78, 216));
        lblInfoTitle.setBounds(12, 8, 300, 18);
        infoBox.add(lblInfoTitle);

        JLabel lblInfoAdmin = new JLabel("Admin: username = admin  |  password = admin123");
        lblInfoAdmin.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lblInfoAdmin.setForeground(new Color(30, 64, 175));
        lblInfoAdmin.setBounds(12, 28, 320, 16);
        infoBox.add(lblInfoAdmin);

        JLabel lblInfoPetugas = new JLabel("Petugas: username = petugas  |  password = petugas123");
        lblInfoPetugas.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lblInfoPetugas.setForeground(new Color(30, 64, 175));
        lblInfoPetugas.setBounds(12, 48, 320, 16);
        infoBox.add(lblInfoPetugas);

        rightPanel.add(infoBox);

        // ================================================================
        // ASSEMBLING
        // ================================================================
        root.add(leftPanel);
        root.add(rightPanel);
        setContentPane(root);

        // ================================================================
        // EVENTS
        // ================================================================
        btnLogin.addActionListener(e -> controller.prosesLogin());

        txtPassword.addKeyListener(new KeyAdapter() {
            @Override public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) controller.prosesLogin();
            }
        });
        txtUsername.addKeyListener(new KeyAdapter() {
            @Override public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) txtPassword.requestFocus();
            }
        });

        chkShowPassword.addActionListener(e -> {
            txtPassword.setEchoChar(chkShowPassword.isSelected() ? (char) 0 : '●');
        });

        // Repaint border saat focus berubah
        txtUsername.addFocusListener(new FocusAdapter() {
            @Override public void focusGained(FocusEvent e) { txtUsername.repaint(); }
            @Override public void focusLost(FocusEvent e) { txtUsername.repaint(); }
        });
        txtPassword.addFocusListener(new FocusAdapter() {
            @Override public void focusGained(FocusEvent e) { txtPassword.repaint(); }
            @Override public void focusLost(FocusEvent e) { txtPassword.repaint(); }
        });
    }

    // ================================================================
    // GETTER untuk Controller
    // ================================================================
    public String getUsername() { return txtUsername.getText(); }
    public String getPassword() { return new String(txtPassword.getPassword()); }
    public void focusUsername() { txtUsername.requestFocus(); }
    public void focusPassword() { txtPassword.requestFocus(); }
    public void clearPassword() { txtPassword.setText(""); }
    public void setStatus(String msg) { /* bisa tambah label status jika perlu */ }
}
