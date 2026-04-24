package librarymanagement.view;

import librarymanagement.controller.LoginController;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;

/**
 * LoginForm - Tampilan login premium split-screen design
 */
public class LoginForm extends JFrame {

    private JTextField     txtUsername;
    private JPasswordField txtPassword;
    private JButton        btnLogin;
    private JCheckBox      chkShowPassword;
    private LoginController controller;

    // Palet warna premium
    private static final Color C_DARK_BG    = new Color(13, 27, 52);
    private static final Color C_DARK_BG2   = new Color(22, 48, 95);
    private static final Color C_BLUE       = new Color(41, 128, 185);
    private static final Color C_BLUE_LIGHT = new Color(52, 152, 219);
    private static final Color C_ACCENT     = new Color(26, 188, 156);
    private static final Color C_WHITE      = Color.WHITE;
    private static final Color C_GRAY_DARK  = new Color(44, 62, 80);
    private static final Color C_GRAY_MID   = new Color(127, 140, 141);
    private static final Color C_FIELD_BG   = new Color(248, 250, 252);
    private static final Color C_BORDER     = new Color(203, 213, 224);

    public LoginForm() {
        initComponents();
        controller = new LoginController(this);
    }

    private void initComponents() {
        setTitle("Sistem Manajemen Perpustakaan");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 640);
        setLocationRelativeTo(null);
        setResizable(false);

        // ROOT — grid 50/50
        JPanel root = new JPanel(new GridLayout(1, 2, 0, 0));

        // ============================================================
        // PANEL KIRI — Branding
        // ============================================================
        JPanel left = new JPanel(null) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Gradient gelap biru
                GradientPaint gp = new GradientPaint(0, 0, C_DARK_BG, getWidth(), getHeight(), C_DARK_BG2);
                g2.setPaint(gp);
                g2.fillRect(0, 0, getWidth(), getHeight());

                // Lingkaran dekorasi besar bawah kiri
                g2.setColor(new Color(41, 128, 185, 30));
                g2.fillOval(-100, getHeight() - 280, 420, 420);

                // Lingkaran sedang kanan atas
                g2.setColor(new Color(26, 188, 156, 20));
                g2.fillOval(getWidth() - 140, -80, 280, 280);

                // Lingkaran kecil tengah kiri
                g2.setColor(new Color(255, 255, 255, 10));
                g2.fillOval(-40, getHeight() / 2 - 80, 160, 160);

                // Grid dots dekoratif
                g2.setColor(new Color(255, 255, 255, 12));
                for (int row = 0; row < 10; row++) {
                    for (int col = 0; col < 8; col++) {
                        g2.fillOval(40 + col * 55, 40 + row * 55, 3, 3);
                    }
                }
            }
        };
        left.setOpaque(false);

        // Garis aksen atas
        JPanel topAccent = new JPanel() {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                GradientPaint gp = new GradientPaint(0, 0, C_ACCENT, getWidth(), 0, new Color(41, 128, 185));
                g2.setPaint(gp);
                g2.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        topAccent.setBounds(0, 0, 500, 4);
        topAccent.setOpaque(false);
        left.add(topAccent);

        // Ikon buku
        JLabel lblIcon = new JLabel("📚", SwingConstants.CENTER);
        lblIcon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 80));
        lblIcon.setBounds(0, 110, 500, 100);
        left.add(lblIcon);

        // Nama aplikasi
        JLabel lblApp = new JLabel("PERPUSTAKAAN", SwingConstants.CENTER);
        lblApp.setFont(new Font("Segoe UI", Font.BOLD, 30));
        lblApp.setForeground(C_WHITE);
        lblApp.setBounds(0, 215, 500, 42);
        left.add(lblApp);

        // Tagline
        JLabel lblTag = new JLabel("Sistem Manajemen Digital Modern", SwingConstants.CENTER);
        lblTag.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblTag.setForeground(new Color(163, 200, 240));
        lblTag.setBounds(0, 262, 500, 24);
        left.add(lblTag);

        // Garis aksen bawah judul
        JPanel accentLine = new JPanel() {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                GradientPaint gp = new GradientPaint(0, 0, new Color(26, 188, 156, 0),
                        getWidth() / 2, 0, C_ACCENT);
                GradientPaint gp2 = new GradientPaint(getWidth() / 2, 0, C_ACCENT,
                        getWidth(), 0, new Color(26, 188, 156, 0));
                g2.setPaint(gp); g2.fillRect(0, 0, getWidth() / 2, getHeight());
                g2.setPaint(gp2); g2.fillRect(getWidth() / 2, 0, getWidth() / 2, getHeight());
            }
        };
        accentLine.setBounds(150, 296, 200, 3);
        accentLine.setOpaque(false);
        left.add(accentLine);

        // Feature cards
        String[][] features = {
            {"📖", "Manajemen Buku & Kategori"},
            {"👥", "Data Anggota Perpustakaan"},
            {"📋", "Transaksi Peminjaman"},
            {"📊", "Laporan & Statistik"}
        };
        for (int i = 0; i < features.length; i++) {
            JPanel card = new JPanel(null) {
                @Override protected void paintComponent(Graphics g) {
                    Graphics2D g2 = (Graphics2D) g;
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.setColor(new Color(255, 255, 255, 18));
                    g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                    g2.setColor(new Color(255, 255, 255, 35));
                    g2.setStroke(new BasicStroke(1f));
                    g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 10, 10);
                }
            };
            card.setOpaque(false);
            card.setBounds(60, 330 + i * 52, 380, 40);

            JLabel icn = new JLabel(features[i][0]);
            icn.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 18));
            icn.setBounds(12, 10, 30, 20);
            card.add(icn);

            JLabel txt = new JLabel(features[i][1]);
            txt.setFont(new Font("Segoe UI", Font.PLAIN, 13));
            txt.setForeground(new Color(200, 225, 255));
            txt.setBounds(48, 11, 310, 18);
            card.add(txt);

            left.add(card);
        }

        // Copyright
        JLabel lblCopy = new JLabel("© 2024 Tugas Kelompok  ·  All Rights Reserved", SwingConstants.CENTER);
        lblCopy.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        lblCopy.setForeground(new Color(80, 120, 170));
        lblCopy.setBounds(0, 604, 500, 18);
        left.add(lblCopy);

        // ============================================================
        // PANEL KANAN — Form Login
        // ============================================================
        JPanel right = new JPanel(null);
        right.setBackground(C_WHITE);

        // Strip aksen kiri
        JPanel sideStrip = new JPanel() {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                GradientPaint gp = new GradientPaint(0, 0, C_ACCENT, 0, getHeight(), C_BLUE_LIGHT);
                g2.setPaint(gp);
                g2.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        sideStrip.setBounds(0, 0, 5, 640);
        sideStrip.setOpaque(false);
        right.add(sideStrip);

        // Sambutan
        JLabel lblWelcome = new JLabel("Selamat Datang!");
        lblWelcome.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblWelcome.setForeground(C_GRAY_DARK);
        lblWelcome.setBounds(60, 90, 380, 40);
        right.add(lblWelcome);

        JLabel lblSub = new JLabel("Silakan masuk untuk melanjutkan");
        lblSub.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblSub.setForeground(C_GRAY_MID);
        lblSub.setBounds(60, 134, 350, 22);
        right.add(lblSub);

        // Divider
        JPanel divider = new JPanel() {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                GradientPaint gp = new GradientPaint(0, 0, C_BLUE_LIGHT, getWidth(), 0, new Color(52, 152, 219, 0));
                g2.setPaint(gp);
                g2.fillRect(0, 0, getWidth(), 2);
            }
        };
        divider.setBounds(60, 165, 380, 2);
        divider.setOpaque(false);
        right.add(divider);

        // Label Username
        JLabel lblU = new JLabel("👤  Username");
        lblU.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblU.setForeground(C_GRAY_DARK);
        lblU.setBounds(60, 190, 200, 20);
        right.add(lblU);

        txtUsername = new JTextField() {
            @Override protected void paintBorder(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(hasFocus() ? C_BLUE_LIGHT : C_BORDER);
                g2.setStroke(new BasicStroke(hasFocus() ? 2f : 1f));
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 10, 10);
            }
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(C_FIELD_BG);
                g2.fillRoundRect(1, 1, getWidth() - 2, getHeight() - 2, 9, 9);
                super.paintComponent(g);
            }
        };
        txtUsername.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtUsername.setOpaque(false);
        txtUsername.setBorder(new EmptyBorder(10, 14, 10, 14));
        txtUsername.setBounds(60, 215, 380, 46);
        right.add(txtUsername);

        // Label Password
        JLabel lblP = new JLabel("🔒  Password");
        lblP.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblP.setForeground(C_GRAY_DARK);
        lblP.setBounds(60, 278, 200, 20);
        right.add(lblP);

        txtPassword = new JPasswordField() {
            @Override protected void paintBorder(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(hasFocus() ? C_BLUE_LIGHT : C_BORDER);
                g2.setStroke(new BasicStroke(hasFocus() ? 2f : 1f));
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 10, 10);
            }
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(C_FIELD_BG);
                g2.fillRoundRect(1, 1, getWidth() - 2, getHeight() - 2, 9, 9);
                super.paintComponent(g);
            }
        };
        txtPassword.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtPassword.setEchoChar('●');
        txtPassword.setOpaque(false);
        txtPassword.setBorder(new EmptyBorder(10, 14, 10, 14));
        txtPassword.setBounds(60, 303, 380, 46);
        right.add(txtPassword);

        // Checkbox
        chkShowPassword = new JCheckBox("Tampilkan Password");
        chkShowPassword.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        chkShowPassword.setForeground(C_GRAY_MID);
        chkShowPassword.setBackground(C_WHITE);
        chkShowPassword.setBounds(60, 358, 200, 22);
        chkShowPassword.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        right.add(chkShowPassword);

        // Tombol Login — gradient
        btnLogin = new JButton("  MASUK") {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                Color c1 = getModel().isPressed() ? new Color(20, 90, 160) :
                           getModel().isRollover() ? new Color(26, 188, 156) : C_BLUE_LIGHT;
                Color c2 = getModel().isPressed() ? new Color(15, 60, 120) :
                           getModel().isRollover() ? new Color(41, 128, 185) : C_BLUE;
                GradientPaint gp = new GradientPaint(0, 0, c1, getWidth(), getHeight(), c2);
                g2.setPaint(gp);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);
                // Shine overlay
                g2.setColor(new Color(255, 255, 255, 30));
                g2.fillRoundRect(0, 0, getWidth(), getHeight() / 2, 12, 12);
                // Teks
                g2.setFont(getFont());
                g2.setColor(C_WHITE);
                FontMetrics fm = g2.getFontMetrics();
                int tx = (getWidth() - fm.stringWidth(getText())) / 2;
                int ty = (getHeight() + fm.getAscent() - fm.getDescent()) / 2;
                g2.drawString(getText(), tx, ty);
                g2.dispose();
            }
        };
        btnLogin.setFont(new Font("Segoe UI", Font.BOLD, 15));
        btnLogin.setForeground(C_WHITE);
        btnLogin.setOpaque(false);
        btnLogin.setContentAreaFilled(false);
        btnLogin.setBorderPainted(false);
        btnLogin.setBounds(60, 395, 380, 50);
        btnLogin.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnLogin.setFocusPainted(false);
        right.add(btnLogin);

        // Info box kredensial
        JPanel infoBox = new JPanel(null) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(235, 248, 255));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);
                g2.setColor(new Color(144, 202, 249));
                g2.setStroke(new BasicStroke(1f));
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 12, 12);
                // Left accent bar
                g2.setColor(C_BLUE_LIGHT);
                g2.fillRoundRect(0, 0, 4, getHeight(), 4, 4);
            }
        };
        infoBox.setOpaque(false);
        infoBox.setBounds(60, 460, 380, 70);

        JLabel lblInfoTitle = new JLabel("ℹ  Akun Demo untuk Mencoba:");
        lblInfoTitle.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblInfoTitle.setForeground(new Color(21, 101, 192));
        lblInfoTitle.setBounds(16, 10, 300, 18);
        infoBox.add(lblInfoTitle);

        JLabel lblDemo = new JLabel("Demo: username = demo  |  password = demo123");
        lblDemo.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblDemo.setForeground(new Color(230, 126, 34));
        lblDemo.setBounds(16, 35, 350, 17);
        infoBox.add(lblDemo);
        
        JLabel lblDemoDesc = new JLabel("         (Read-Only - Hanya bisa melihat, tidak bisa edit/hapus)");
        lblDemoDesc.setFont(new Font("Segoe UI", Font.ITALIC, 10));
        lblDemoDesc.setForeground(new Color(192, 57, 43));
        lblDemoDesc.setBounds(16, 52, 360, 14);
        infoBox.add(lblDemoDesc);

        right.add(infoBox);

        // ============================================================
        // ASSEMBLING
        // ============================================================
        root.add(left);
        root.add(right);
        setContentPane(root);

        // ============================================================
        // EVENTS
        // ============================================================
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

        chkShowPassword.addActionListener(e ->
            txtPassword.setEchoChar(chkShowPassword.isSelected() ? (char) 0 : '●'));

        txtUsername.addFocusListener(new FocusAdapter() {
            @Override public void focusGained(FocusEvent e) { txtUsername.repaint(); }
            @Override public void focusLost(FocusEvent e)   { txtUsername.repaint(); }
        });
        txtPassword.addFocusListener(new FocusAdapter() {
            @Override public void focusGained(FocusEvent e) { txtPassword.repaint(); }
            @Override public void focusLost(FocusEvent e)   { txtPassword.repaint(); }
        });
    }

    // ============================================================
    // GETTER untuk Controller
    // ============================================================
    public String getUsername() { return txtUsername.getText(); }
    public String getPassword() { return new String(txtPassword.getPassword()); }
    public void focusUsername() { txtUsername.requestFocus(); }
    public void focusPassword() { txtPassword.requestFocus(); }
    public void clearPassword() { txtPassword.setText(""); }
    public void setStatus(String msg) { /* bisa tambah label status jika perlu */ }
}
