package librarymanagement.view;

import librarymanagement.controller.LoginController;
import librarymanagement.model.UserModel;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Dashboard - Halaman utama setelah login
 * Berisi sidebar navigasi dan konten area
 */
public class Dashboard extends JFrame {
    
    // ============================================================
    // KOMPONEN GUI
    // ============================================================
    private JPanel sidebarPanel;
    private JPanel contentPanel;
    private CardLayout cardLayout;
    private JLabel lblUserInfo;
    private JLabel lblRole;
    private UserModel currentUser;
    
    // Panels untuk setiap menu
    private FormBuku formBuku;
    private FormKategori formKategori;
    private FormAnggota formAnggota;
    private FormPeminjaman formPeminjaman;
    private PanelLaporan panelLaporan;
    private KartuAnggotaPanel kartuAnggotaPanel;
    
    // Warna tema
    private static final Color COLOR_SIDEBAR    = new Color(26, 42, 68);    // Dark Navy
    private static final Color COLOR_SIDEBAR_H  = new Color(40, 62, 100);   // Hover
    private static final Color COLOR_ACTIVE     = new Color(52, 152, 219);  // Active Blue
    private static final Color COLOR_BG         = new Color(240, 244, 248); // Light BG
    private static final Color COLOR_WHITE      = Color.WHITE;
    private static final Color COLOR_TEXT_LIGHT = new Color(189, 195, 199);
    
    private JButton activeButton = null; // Track tombol aktif
    
    // ============================================================
    // KONSTRUKTOR
    // ============================================================
    public Dashboard(UserModel user) {
        this.currentUser = user;
        initComponents();
        showWelcomePage();
    }
    
    // ============================================================
    // INISIALISASI KOMPONEN
    // ============================================================
    private void initComponents() {
        setTitle("📚 Sistem Manajemen Perpustakaan");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1280, 800);
        setLocationRelativeTo(null);
        setMinimumSize(new Dimension(1000, 650));
        
        setLayout(new BorderLayout(0, 0));
        
        // ============================================================
        // SIDEBAR (kiri)
        // ============================================================
        sidebarPanel = new JPanel();
        sidebarPanel.setBackground(COLOR_SIDEBAR);
        sidebarPanel.setLayout(new BoxLayout(sidebarPanel, BoxLayout.Y_AXIS));
        sidebarPanel.setPreferredSize(new Dimension(260, getHeight()));
        
        // Logo area — gradient
        JPanel logoPanel = new JPanel(null) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                GradientPaint gp = new GradientPaint(0, 0, new Color(10, 22, 46), getWidth(), 0, new Color(20, 40, 80));
                g2.setPaint(gp);
                g2.fillRect(0, 0, getWidth(), getHeight());
                // accent bottom border
                g2.setColor(new Color(52, 152, 219, 120));
                g2.fillRect(0, getHeight() - 2, getWidth(), 2);
                g2.dispose();
            }
        };
        logoPanel.setOpaque(false);
        logoPanel.setMaximumSize(new Dimension(260, 76));
        logoPanel.setMinimumSize(new Dimension(260, 76));
        logoPanel.setPreferredSize(new Dimension(260, 76));
        
        JLabel lblIconLogo = new JLabel("📚");
        lblIconLogo.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 26));
        lblIconLogo.setBounds(16, 22, 36, 32);
        logoPanel.add(lblIconLogo);
        
        JLabel lblLogoText = new JLabel("PERPUSTAKAAN");
        lblLogoText.setFont(new Font("Segoe UI", Font.BOLD, 15));
        lblLogoText.setForeground(COLOR_WHITE);
        lblLogoText.setBounds(56, 20, 180, 22);
        logoPanel.add(lblLogoText);
        
        JLabel lblLogoSub = new JLabel("Sistem Manajemen Digital");
        lblLogoSub.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        lblLogoSub.setForeground(new Color(130, 160, 200));
        lblLogoSub.setBounds(56, 42, 180, 16);
        logoPanel.add(lblLogoSub);
        
        sidebarPanel.add(logoPanel);
        
        // User info panel
        JPanel userPanel = new JPanel(null);
        userPanel.setBackground(new Color(18, 32, 58));
        userPanel.setMaximumSize(new Dimension(260, 100));
        userPanel.setMinimumSize(new Dimension(260, 100));
        userPanel.setPreferredSize(new Dimension(260, 100));
        
        // Avatar circle
        JPanel avatar = new JPanel() {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(COLOR_ACTIVE);
                g2.fillOval(0, 0, getWidth(), getHeight());
                g2.setColor(COLOR_WHITE);
                g2.setFont(new Font("Segoe UI", Font.BOLD, 18));
                FontMetrics fm = g2.getFontMetrics();
                String initial = currentUser.getUsername().substring(0, 1).toUpperCase();
                g2.drawString(initial, (getWidth() - fm.stringWidth(initial)) / 2,
                    (getHeight() + fm.getAscent() - fm.getDescent()) / 2);
                g2.dispose();
            }
        };
        avatar.setBounds(16, 18, 44, 44);
        avatar.setOpaque(false);
        userPanel.add(avatar);

        lblUserInfo = new JLabel(currentUser.getUsername().toUpperCase());
        lblUserInfo.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblUserInfo.setForeground(COLOR_WHITE);
        lblUserInfo.setBounds(70, 22, 170, 20);
        userPanel.add(lblUserInfo);

        // Role badge
        JPanel roleBadge = new JPanel() {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                Color bc = "Admin".equals(currentUser.getRole()) ? new Color(52,152,219) : new Color(46,204,113);
                g2.setColor(bc);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                g2.dispose();
            }
        };
        roleBadge.setBounds(70, 48, "Admin".equals(currentUser.getRole()) ? 52 : 68, 20);
        roleBadge.setOpaque(false);
        roleBadge.setLayout(new BorderLayout());
        lblRole = new JLabel(currentUser.getRole(), SwingConstants.CENTER);
        lblRole.setFont(new Font("Segoe UI", Font.BOLD, 10));
        lblRole.setForeground(COLOR_WHITE);
        roleBadge.add(lblRole);
        userPanel.add(roleBadge);
        sidebarPanel.add(userPanel);
        
        // Divider
        sidebarPanel.add(createDivider("MENU UTAMA"));
        
        // Tombol menu
        JButton btnDashboard = createMenuButton("🏠", "Dashboard", null);
        JButton btnBuku      = createMenuButton("📖", "Manajemen Buku", "buku");
        JButton btnKategori  = createMenuButton("🏷️", "Kategori Buku", "kategori");
        JButton btnAnggota   = createMenuButton("👥", "Manajemen Anggota", "anggota");
        JButton btnPinjam    = createMenuButton("📋", "Peminjaman Buku", "peminjaman");
        
        sidebarPanel.add(btnDashboard);
        sidebarPanel.add(btnBuku);
        sidebarPanel.add(btnKategori);
        sidebarPanel.add(btnAnggota);
        sidebarPanel.add(btnPinjam);
        
        sidebarPanel.add(createDivider("LAPORAN"));
        
        JButton btnLaporan = createMenuButton("📊", "Laporan & Statistik", "laporan");
        JButton btnKartu   = createMenuButton("🪪", "Kartu Anggota", "kartu");
        sidebarPanel.add(btnLaporan);
        sidebarPanel.add(btnKartu);
        
        // Spacer
        sidebarPanel.add(Box.createVerticalGlue());
        
        // Tombol Logout di bawah
        JSeparator sepLogout = new JSeparator();
        sepLogout.setForeground(new Color(255,255,255,40));
        sepLogout.setMaximumSize(new Dimension(220, 1));
        sidebarPanel.add(sepLogout);
        
        JButton btnLogout = createMenuButton("🚪", "Logout", null);
        btnLogout.setBackground(new Color(231, 76, 60, 150));
        sidebarPanel.add(btnLogout);
        sidebarPanel.add(Box.createVerticalStrut(10));
        
        // ============================================================
        // CONTENT AREA (kanan)
        // ============================================================
        cardLayout  = new CardLayout();
        contentPanel = new JPanel(cardLayout);
        contentPanel.setBackground(COLOR_BG);
        
        // Welcome panel
        contentPanel.add(createWelcomePanel(), "home");
        
        // Form panels
        formBuku      = new FormBuku();
        formKategori  = new FormKategori();
        formAnggota   = new FormAnggota();
        formPeminjaman = new FormPeminjaman();
        panelLaporan  = new PanelLaporan();
        kartuAnggotaPanel = new KartuAnggotaPanel();
        
        contentPanel.add(formBuku, "buku");
        contentPanel.add(formKategori, "kategori");
        contentPanel.add(formAnggota, "anggota");
        contentPanel.add(formPeminjaman, "peminjaman");
        contentPanel.add(panelLaporan, "laporan");
        contentPanel.add(kartuAnggotaPanel, "kartu");
        
        // ============================================================
        // EVENT LISTENERS
        // ============================================================
        btnDashboard.addActionListener(e -> {
            setActive(btnDashboard);
            showPanel("home");
        });
        btnBuku.addActionListener(e -> {
            setActive(btnBuku);
            showPanel("buku");
        });
        btnKategori.addActionListener(e -> {
            setActive(btnKategori);
            showPanel("kategori");
        });
        btnAnggota.addActionListener(e -> {
            setActive(btnAnggota);
            showPanel("anggota");
        });
        btnPinjam.addActionListener(e -> {
            setActive(btnPinjam);
            showPanel("peminjaman");
        });
        btnLaporan.addActionListener(e -> {
            setActive(btnLaporan);
            showPanel("laporan");
            panelLaporan.loadData();
        });
        btnKartu.addActionListener(e -> {
            setActive(btnKartu);
            showPanel("kartu");
        });
        btnLogout.addActionListener(e -> LoginController.logout(this));
        
        // Batasan akses berdasarkan role
        if (!"Admin".equals(currentUser.getRole())) {
            // Petugas tidak bisa akses manajemen user (sudah tidak ada di menu)
        }
        
        add(sidebarPanel, BorderLayout.WEST);
        add(contentPanel, BorderLayout.CENTER);
        
        // Set tombol Dashboard sebagai aktif awal
        setActive(btnDashboard);
    }
    
    /**
     * Buat panel separator dengan label
     */
    private JPanel createDivider(String label) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 16, 8));
        panel.setBackground(COLOR_SIDEBAR);
        panel.setMaximumSize(new Dimension(260, 32));
        JLabel lbl = new JLabel(label);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 10));
        lbl.setForeground(new Color(80, 105, 140));
        panel.add(lbl);
        return panel;
    }
    
    /**
     * Buat tombol sidebar - TEKS MENTOK KIRI BANGET!
     */
    private JButton createMenuButton(String icon, String text, String panel) {
        JButton btn = new JButton() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
                
                // Background highlight
                if (this == activeButton) {
                    // Active state — highlight DARI KIRI
                    g2.setColor(COLOR_ACTIVE);
                    g2.fillRoundRect(8, 4, getWidth() - 16, getHeight() - 8, 8, 8);
                } else if (getModel().isRollover()) {
                    // Hover state DARI KIRI
                    g2.setColor(new Color(52, 73, 94));
                    g2.fillRoundRect(8, 4, getWidth() - 16, getHeight() - 8, 8, 8);
                }
                
                // Gambar icon emoji di posisi KIRI BANGET
                g2.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 18));
                g2.setColor(getForeground());
                g2.drawString(icon, 16, getHeight() / 2 + 6); // ICON: x=16px (lebih kiri)
                
                // Gambar teks di posisi KIRI BANGET (dekat icon)
                g2.setFont(new Font("Segoe UI", Font.PLAIN, 13));
                g2.drawString(text, 42, getHeight() / 2 + 5); // TEKS: x=42px (lebih kiri, dekat icon)
                
                g2.dispose();
            }
        };
        
        // JANGAN set text (kita gambar manual di paintComponent)
        btn.setText("");
        btn.setForeground(COLOR_TEXT_LIGHT);
        
        btn.setOpaque(false);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setBorder(null);
        btn.setMaximumSize(new Dimension(260, 48));
        btn.setPreferredSize(new Dimension(260, 48));
        btn.setMinimumSize(new Dimension(260, 48));
        btn.setAlignmentX(Component.LEFT_ALIGNMENT);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setFocusPainted(false);
        return btn;
    }
    
    /**
     * Set tombol aktif di sidebar
     */
    private void setActive(JButton btn) {
        if (activeButton != null) {
            activeButton.setForeground(COLOR_TEXT_LIGHT);
            activeButton.repaint();
        }
        activeButton = btn;
        btn.setForeground(COLOR_WHITE);
        btn.repaint();
    }
    
    /**
     * Tampilkan panel berdasarkan nama card
     */
    private void showPanel(String panelName) {
        cardLayout.show(contentPanel, panelName);
    }
    
    private void showWelcomePage() {
        showPanel("home");
    }
    
    /**
     * Get current date time formatted
     */
    private String getCurrentDateTime() {
        java.time.LocalDateTime now = java.time.LocalDateTime.now();
        java.time.format.DateTimeFormatter formatter = 
            java.time.format.DateTimeFormatter.ofPattern("EEEE, dd MMMM yyyy • HH:mm:ss", 
                new java.util.Locale("id", "ID"));
        return now.format(formatter);
    }
    
    /**
     * Buat panel sambutan / dashboard home
     */
    private JPanel createWelcomePanel() {
        // Panel utama yang akan discroll
        JPanel innerPanel = new JPanel();
        innerPanel.setLayout(new BoxLayout(innerPanel, BoxLayout.Y_AXIS));
        innerPanel.setBackground(COLOR_BG);
        innerPanel.setBorder(new EmptyBorder(25, 25, 25, 25));
        
        // Header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(COLOR_BG);
        headerPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 65));
        headerPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel lblHello = new JLabel("Selamat Datang, " + currentUser.getUsername() + "! 👋");
        lblHello.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblHello.setForeground(new Color(44, 62, 80));
        
        JLabel lblDate = new JLabel(getCurrentDateTime());
        lblDate.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblDate.setForeground(new Color(127, 140, 141));
        
        Timer timer = new Timer(1000, e -> lblDate.setText(getCurrentDateTime()));
        timer.start();
        
        headerPanel.add(lblHello, BorderLayout.NORTH);
        headerPanel.add(lblDate, BorderLayout.CENTER);
        
        innerPanel.add(headerPanel);
        innerPanel.add(Box.createVerticalStrut(20));
        
        // Stats cards - 2 baris, ukuran compact
        JPanel cardsRow1 = new JPanel(new GridLayout(1, 2, 18, 0));
        cardsRow1.setBackground(COLOR_BG);
        cardsRow1.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));
        cardsRow1.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        cardsRow1.add(createStatCard("📚", "Manajemen Buku", "Kelola koleksi buku perpustakaan", new Color(52, 152, 219)));
        cardsRow1.add(createStatCard("👥", "Manajemen Anggota", "Data anggota perpustakaan", new Color(46, 204, 113)));
        
        JPanel cardsRow2 = new JPanel(new GridLayout(1, 2, 18, 0));
        cardsRow2.setBackground(COLOR_BG);
        cardsRow2.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));
        cardsRow2.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        cardsRow2.add(createStatCard("📋", "Transaksi Peminjaman", "Catat & kelola peminjaman buku", new Color(231, 76, 60)));
        cardsRow2.add(createStatCard("📊", "Laporan", "Laporan dan statistik perpustakaan", new Color(155, 89, 182)));
        
        innerPanel.add(cardsRow1);
        innerPanel.add(Box.createVerticalStrut(14));
        innerPanel.add(cardsRow2);
        innerPanel.add(Box.createVerticalStrut(20));
        
        // Instruksi penggunaan
        JTextArea txtInfo = new JTextArea(
            "ℹ️  Cara Penggunaan:\n\n" +
            "  • Pilih menu di sidebar kiri untuk navigasi\n" +
            "  • Gunakan tombol Tambah, Edit, Hapus untuk kelola data\n" +
            "  • Gunakan kolom Cari untuk mencari data\n" +
            "  • Peminjaman: Pilih anggota & buku, lalu klik Pinjam\n" +
            "  • Pengembalian: Pilih data peminjaman, klik Kembalikan\n" +
            "  • Denda otomatis dihitung Rp1.000/hari keterlambatan"
        );
        txtInfo.setEditable(false);
        txtInfo.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        txtInfo.setBackground(new Color(232, 245, 233));
        txtInfo.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 230, 201)),
            new EmptyBorder(15, 18, 15, 18)
        ));
        txtInfo.setForeground(new Color(33, 70, 33));
        txtInfo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 180));
        txtInfo.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        innerPanel.add(txtInfo);
        
        // Bungkus dalam JScrollPane
        JScrollPane scrollPane = new JScrollPane(innerPanel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBackground(COLOR_BG);
        wrapper.add(scrollPane, BorderLayout.CENTER);
        
        return wrapper;
    }
    
    /**
     * Buat kartu statistik di halaman home - IMPROVED VERSION
     */
    private JPanel createStatCard(String icon, String title, String desc, Color color) {
        JPanel card = new JPanel(new BorderLayout(0, 6)) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Card background
                g2.setColor(COLOR_WHITE);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                
                // Accent bar
                g2.setColor(color);
                g2.fillRoundRect(0, 0, 6, getHeight(), 6, 6);
                
                // Subtle border
                g2.setColor(new Color(220, 225, 230));
                g2.setStroke(new BasicStroke(1f));
                g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 10, 10);
                
                g2.dispose();
            }
        };
        card.setOpaque(false);
        card.setBorder(new EmptyBorder(14, 18, 14, 18));
        
        // Hover effect
        card.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                card.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                card.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        });
        
        // Icon panel compact
        JPanel iconPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), 20));
                g2.fillOval(0, 0, 44, 44);
                g2.dispose();
            }
        };
        iconPanel.setOpaque(false);
        iconPanel.setPreferredSize(new Dimension(44, 44));
        iconPanel.setLayout(new GridBagLayout());
        
        JLabel lblIcon = new JLabel(icon);
        lblIcon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 22));
        iconPanel.add(lblIcon);
        
        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setOpaque(false);
        
        JLabel lblTitle = new JLabel(title);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblTitle.setForeground(new Color(44, 62, 80));
        
        JLabel lblDesc = new JLabel("<html>" + desc + "</html>");
        lblDesc.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lblDesc.setForeground(new Color(127, 140, 141));
        
        textPanel.add(lblTitle);
        textPanel.add(Box.createVerticalStrut(3));
        textPanel.add(lblDesc);
        
        card.add(iconPanel, BorderLayout.WEST);
        card.add(Box.createHorizontalStrut(10), BorderLayout.LINE_START);
        card.add(textPanel, BorderLayout.CENTER);
        
        return card;
    }
}
