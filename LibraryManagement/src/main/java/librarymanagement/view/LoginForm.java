package librarymanagement.view;

import librarymanagement.controller.LoginController;
import librarymanagement.util.ModernUIUtil;
import librarymanagement.util.ResponsiveUtil;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;

/**
 * LoginForm - Modern & Responsive Login Interface
 * Mendukung Desktop, Tablet, dan Mobile layouts
 */
public class LoginForm extends JFrame {

    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnLogin;
    private JCheckBox chkShowPassword;
    private LoginController controller;
    private JPanel leftPanel;
    private JPanel rightPanel;
    private JPanel mainContainer;
    private ResponsiveUtil.ScreenSize currentScreenSize;

    // Modern Color Palette
    private static final Color C_PRIMARY = ModernUIUtil.Colors.PRIMARY;
    private static final Color C_PRIMARY_DARK = ModernUIUtil.Colors.PRIMARY_DARK;
    private static final Color C_SUCCESS = ModernUIUtil.Colors.SUCCESS;
    private static final Color C_BG = ModernUIUtil.Colors.BG_PRIMARY;
    private static final Color C_BG_SECONDARY = ModernUIUtil.Colors.BG_SECONDARY;
    private static final Color C_TEXT_PRIMARY = ModernUIUtil.Colors.TEXT_PRIMARY;
    private static final Color C_TEXT_SECONDARY = ModernUIUtil.Colors.TEXT_SECONDARY;
    private static final Color C_BORDER = ModernUIUtil.Colors.BORDER_DEFAULT;
    
    // Gradient colors
    private static final Color C_GRADIENT_START = new Color(59, 130, 246);
    private static final Color C_GRADIENT_END = new Color(37, 99, 235);

    public LoginForm() {
        initComponents();
        controller = new LoginController(this);
        setupResponsive();
    }

    private void initComponents() {
        setTitle("Sistem Manajemen Perpustakaan - Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1100, 700);
        setLocationRelativeTo(null);
        setMinimumSize(new Dimension(900, 600));

        // Main container
        mainContainer = new JPanel(new BorderLayout(0, 0));
        mainContainer.setBackground(C_BG_SECONDARY);

        // Create panels
        createLeftPanel();
        createRightPanel();

        // Add panels to container
        mainContainer.add(leftPanel, BorderLayout.CENTER);
        mainContainer.add(rightPanel, BorderLayout.EAST);

        setContentPane(mainContainer);
        
        // Setup event listeners
        setupEventListeners();
    }

    private void createLeftPanel() {
        leftPanel = new JPanel(null) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Modern gradient background
                GradientPaint gp = new GradientPaint(
                    0, 0, C_GRADIENT_START,
                    getWidth(), getHeight(), C_GRADIENT_END
                );
                g2.setPaint(gp);
                g2.fillRect(0, 0, getWidth(), getHeight());

                // Decorative circles with modern opacity
                g2.setColor(new Color(255, 255, 255, 15));
                g2.fillOval(-80, getHeight() - 200, 300, 300);
                g2.fillOval(getWidth() - 150, -80, 250, 250);
                
                // Grid pattern
                g2.setColor(new Color(255, 255, 255, 8));
                for (int row = 0; row < 12; row++) {
                    for (int col = 0; col < 10; col++) {
                        g2.fillOval(30 + col * 60, 30 + row * 60, 4, 4);
                    }
                }
            }
        };
        leftPanel.setOpaque(true);
        leftPanel.setBackground(C_PRIMARY);

        // Logo and branding
        JLabel lblIcon = new JLabel("📚", SwingConstants.CENTER);
        lblIcon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 72));
        lblIcon.setBounds(0, 140, 600, 90);
        leftPanel.add(lblIcon);

        JLabel lblApp = new JLabel("PERPUSTAKAAN", SwingConstants.CENTER);
        lblApp.setFont(new Font("Segoe UI", Font.BOLD, 32));
        lblApp.setForeground(Color.WHITE);
        lblApp.setBounds(0, 240, 600, 45);
        leftPanel.add(lblApp);

        JLabel lblTag = new JLabel("Sistem Manajemen Digital Modern", SwingConstants.CENTER);
        lblTag.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        lblTag.setForeground(new Color(255, 255, 255, 200));
        lblTag.setBounds(0, 290, 600, 25);
        leftPanel.add(lblTag);

        // Feature cards
        String[][] features = {
            {"Manajemen Buku & Kategori"},
            {"Data Anggota Perpustakaan"},
            {"Transaksi Peminjaman"},
            {"Laporan & Statistik"}
        };
        
        for (int i = 0; i < features.length; i++) {
            JPanel card = createFeatureCard(features[i][0]);
            card.setBounds(80, 360 + i * 55, 440, 45);
            leftPanel.add(card);
        }

        // Copyright
        JLabel lblCopy = new JLabel("© 2024 Tugas Kelompok · All Rights Reserved", SwingConstants.CENTER);
        lblCopy.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lblCopy.setForeground(new Color(255, 255, 255, 150));
        lblCopy.setBounds(0, 640, 600, 20);
        leftPanel.add(lblCopy);
    }

    private JPanel createFeatureCard(String text) {
        JPanel card = new JPanel(null) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Card background
                g2.setColor(new Color(255, 255, 255, 20));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);
                
                // Border
                g2.setColor(new Color(255, 255, 255, 40));
                g2.setStroke(new BasicStroke(1.5f));
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 12, 12);
            }
        };
        card.setOpaque(false);

        // Checkmark icon
        JLabel icon = new JLabel("✓");
        icon.setFont(new Font("Segoe UI", Font.BOLD, 18));
        icon.setForeground(new Color(34, 197, 94));
        icon.setBounds(15, 12, 25, 20);
        card.add(icon);

        // Feature text
        JLabel txt = new JLabel(text);
        txt.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txt.setForeground(Color.WHITE);
        txt.setBounds(50, 13, 370, 18);
        card.add(txt);

        return card;
    }

    private void createRightPanel() {
        rightPanel = new JPanel(null);
        rightPanel.setBackground(C_BG);
        rightPanel.setPreferredSize(new Dimension(500, 700));

        // Welcome section
        JLabel lblWelcome = new JLabel("Selamat Datang");
        lblWelcome.setFont(new Font("Segoe UI", Font.BOLD, 32));
        lblWelcome.setForeground(C_TEXT_PRIMARY);
        lblWelcome.setBounds(50, 80, 400, 45);
        rightPanel.add(lblWelcome);

        JLabel lblSub = new JLabel("Silakan masuk untuk melanjutkan");
        lblSub.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblSub.setForeground(C_TEXT_SECONDARY);
        lblSub.setBounds(50, 130, 400, 25);
        rightPanel.add(lblSub);

        // Username field
        JLabel lblUsername = new JLabel("Username");
        lblUsername.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblUsername.setForeground(C_TEXT_PRIMARY);
        lblUsername.setBounds(50, 180, 400, 20);
        rightPanel.add(lblUsername);

        txtUsername = createModernTextField();
        txtUsername.setBounds(50, 205, 400, 45);
        rightPanel.add(txtUsername);

        // Password field
        JLabel lblPassword = new JLabel("Password");
        lblPassword.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblPassword.setForeground(C_TEXT_PRIMARY);
        lblPassword.setBounds(50, 270, 400, 20);
        rightPanel.add(lblPassword);

        txtPassword = createModernPasswordField();
        txtPassword.setBounds(50, 295, 400, 45);
        rightPanel.add(txtPassword);

        // Show password checkbox
        chkShowPassword = new JCheckBox("Tampilkan Password");
        chkShowPassword.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        chkShowPassword.setForeground(C_TEXT_SECONDARY);
        chkShowPassword.setBackground(C_BG);
        chkShowPassword.setBounds(50, 350, 200, 25);
        chkShowPassword.setCursor(new Cursor(Cursor.HAND_CURSOR));
        chkShowPassword.setFocusPainted(false);
        rightPanel.add(chkShowPassword);

        // Login button
        btnLogin = ModernUIUtil.createModernButton("MASUK", C_PRIMARY, C_PRIMARY_DARK);
        btnLogin.setBounds(50, 395, 400, 48);
        btnLogin.setFont(new Font("Segoe UI", Font.BOLD, 15));
        rightPanel.add(btnLogin);

        // Demo account info
        JPanel infoBox = createInfoBox();
        infoBox.setBounds(50, 470, 400, 110);
        rightPanel.add(infoBox);
    }

    private JTextField createModernTextField() {
        JTextField field = new JTextField() {
            private boolean isFocused = false;
            
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                g2.setColor(C_BG);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                
                super.paintComponent(g);
                g2.dispose();
            }
            
            @Override
            protected void paintBorder(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                if (isFocused) {
                    g2.setColor(C_PRIMARY);
                    g2.setStroke(new BasicStroke(2));
                } else {
                    g2.setColor(C_BORDER);
                    g2.setStroke(new BasicStroke(1));
                }
                
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 10, 10);
                g2.dispose();
            }
            
            {
                addFocusListener(new FocusAdapter() {
                    @Override
                    public void focusGained(FocusEvent e) {
                        isFocused = true;
                        repaint();
                    }
                    
                    @Override
                    public void focusLost(FocusEvent e) {
                        isFocused = false;
                        repaint();
                    }
                });
            }
        };
        
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setForeground(C_TEXT_PRIMARY);
        field.setOpaque(false);
        field.setBorder(new EmptyBorder(12, 16, 12, 16));
        
        return field;
    }

    private JPasswordField createModernPasswordField() {
        JPasswordField field = new JPasswordField() {
            private boolean isFocused = false;
            
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                g2.setColor(C_BG);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                
                super.paintComponent(g);
                g2.dispose();
            }
            
            @Override
            protected void paintBorder(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                if (isFocused) {
                    g2.setColor(C_PRIMARY);
                    g2.setStroke(new BasicStroke(2));
                } else {
                    g2.setColor(C_BORDER);
                    g2.setStroke(new BasicStroke(1));
                }
                
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 10, 10);
                g2.dispose();
            }
            
            {
                addFocusListener(new FocusAdapter() {
                    @Override
                    public void focusGained(FocusEvent e) {
                        isFocused = true;
                        repaint();
                    }
                    
                    @Override
                    public void focusLost(FocusEvent e) {
                        isFocused = false;
                        repaint();
                    }
                });
            }
        };
        
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setEchoChar('●');
        field.setForeground(C_TEXT_PRIMARY);
        field.setOpaque(false);
        field.setBorder(new EmptyBorder(12, 16, 12, 16));
        
        return field;
    }

    private JPanel createInfoBox() {
        JPanel box = new JPanel(null) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Background
                g2.setColor(new Color(239, 246, 255));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);
                
                // Left accent bar
                g2.setColor(C_PRIMARY);
                g2.fillRoundRect(0, 0, 4, getHeight(), 4, 4);
                
                // Border
                g2.setColor(new Color(191, 219, 254));
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 12, 12);
            }
        };
        box.setOpaque(false);

        JLabel lblTitle = new JLabel("Akun Demo untuk Mencoba:");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblTitle.setForeground(new Color(30, 64, 175));
        lblTitle.setBounds(18, 12, 350, 20);
        box.add(lblTitle);

        JLabel lblDemo = new JLabel("Username: demo  |  Password: demo123");
        lblDemo.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblDemo.setForeground(new Color(234, 88, 12));
        lblDemo.setBounds(18, 38, 350, 20);
        box.add(lblDemo);
        
        JLabel lblDesc = new JLabel("(Read-Only - Hanya bisa melihat, tidak bisa edit/hapus)");
        lblDesc.setFont(new Font("Segoe UI", Font.ITALIC, 11));
        lblDesc.setForeground(new Color(127, 29, 29));
        lblDesc.setBounds(18, 62, 360, 18);
        box.add(lblDesc);

        return box;
    }

    private void setupEventListeners() {
        btnLogin.addActionListener(e -> controller.prosesLogin());

        txtPassword.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    controller.prosesLogin();
                }
            }
        });

        txtUsername.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    txtPassword.requestFocus();
                }
            }
        });

        chkShowPassword.addActionListener(e ->
            txtPassword.setEchoChar(chkShowPassword.isSelected() ? (char) 0 : '●'));
    }

    private void setupResponsive() {
        currentScreenSize = ResponsiveUtil.getCurrentScreenSize(this);
        
        ResponsiveUtil.addResponsiveListener(this, newSize -> {
            currentScreenSize = newSize;
            adjustLayout(newSize);
        });
    }

    private void adjustLayout(ResponsiveUtil.ScreenSize screenSize) {
        SwingUtilities.invokeLater(() -> {
            if (ResponsiveUtil.shouldUseVerticalLayout(screenSize)) {
                // Mobile layout - stack vertically
                mainContainer.remove(leftPanel);
                mainContainer.remove(rightPanel);
                
                leftPanel.setPreferredSize(new Dimension(getWidth(), 200));
                rightPanel.setPreferredSize(new Dimension(getWidth(), getHeight() - 200));
                
                mainContainer.add(leftPanel, BorderLayout.NORTH);
                mainContainer.add(rightPanel, BorderLayout.CENTER);
            } else {
                // Desktop/Tablet layout - side by side
                mainContainer.remove(leftPanel);
                mainContainer.remove(rightPanel);
                
                int rightWidth = ResponsiveUtil.getFormPanelWidth(screenSize) + 100;
                rightPanel.setPreferredSize(new Dimension(rightWidth, getHeight()));
                
                mainContainer.add(leftPanel, BorderLayout.CENTER);
                mainContainer.add(rightPanel, BorderLayout.EAST);
            }
            
            mainContainer.revalidate();
            mainContainer.repaint();
        });
    }

    // Getter methods for controller
    public String getUsername() { return txtUsername.getText(); }
    public String getPassword() { return new String(txtPassword.getPassword()); }
    public void focusUsername() { txtUsername.requestFocus(); }
    public void focusPassword() { txtPassword.requestFocus(); }
    public void clearPassword() { txtPassword.setText(""); }
    public void setStatus(String msg) { /* Optional: add status label */ }
}
