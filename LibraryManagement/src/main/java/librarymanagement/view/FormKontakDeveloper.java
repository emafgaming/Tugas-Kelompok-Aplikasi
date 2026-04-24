package librarymanagement.view;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.net.URI;

/**
 * FormKontakDeveloper - Form untuk pengguna demo yang tertarik dengan sistem
 */
public class FormKontakDeveloper extends JPanel {
    
    private static final Color COLOR_PRIMARY = new Color(26, 82, 118);
    private static final Color COLOR_SUCCESS = new Color(39, 174, 96);
    private static final Color COLOR_BG      = new Color(240, 244, 248);
    private static final Color COLOR_WHITE   = Color.WHITE;
    
    public FormKontakDeveloper() {
        initComponents();
    }
    
    private void initComponents() {
        setLayout(new BorderLayout(15, 15));
        setBackground(COLOR_BG);
        setBorder(new EmptyBorder(20, 25, 20, 25));
        
        // HEADER
        add(createHeaderPanel("📞 Hubungi Developer", "Tertarik dengan sistem ini? Hubungi kami!"), BorderLayout.NORTH);
        
        // CENTER - Konten utama
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBackground(COLOR_WHITE);
        centerPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220,225,230)),
            new EmptyBorder(30, 40, 30, 40)
        ));
        
        // Intro text
        JLabel lblIntro = new JLabel("<html><div style='text-align: center; line-height: 1.6;'>" +
            "<h2 style='color: #1a5276; margin-bottom: 10px;'>🎉 Terima kasih telah mencoba sistem kami!</h2>" +
            "<p style='color: #555; font-size: 13px;'>Jika Anda tertarik untuk menggunakan atau mengembangkan sistem ini,<br>" +
            "silakan hubungi developer melalui kontak di bawah ini:</p>" +
            "</div></html>");
        lblIntro.setAlignmentX(Component.CENTER_ALIGNMENT);
        centerPanel.add(lblIntro);
        centerPanel.add(Box.createVerticalStrut(30));
        
        // Contact cards
        centerPanel.add(createContactCard("📧", "Email", "ezzarmuhammadakbarfirdaus@gmail.com", "mailto:ezzarmuhammadakbarfirdaus@gmail.com"));
        centerPanel.add(Box.createVerticalStrut(15));
        centerPanel.add(createContactCard("📱", "WhatsApp", "+62 87852211145", "https://wa.me/6287852211145"));
        centerPanel.add(Box.createVerticalStrut(15));
        centerPanel.add(createContactCard("💼", "LinkedIn", "linkedin.com/in/ezzar-muhammad-akbar-firdaus", "https://www.linkedin.com/in/ezzar-muhammad-akbar-firdaus-51492537a/"));
        centerPanel.add(Box.createVerticalStrut(15));
        centerPanel.add(createContactCard("🐙", "GitHub", "github.com/emafgaming", "https://github.com/emafgaming"));
        
        centerPanel.add(Box.createVerticalStrut(30));
        
        // Info tambahan
        JTextArea txtInfo = new JTextArea(
            "💡 Informasi Sistem:\n\n" +
            "  • Sistem Manajemen Perpustakaan berbasis Java Swing\n" +
            "  • Database SQLite dengan arsitektur MVC\n" +
            "  • Fitur lengkap: CRUD Buku, Anggota, Peminjaman, Laporan\n" +
            "  • UI Modern dengan Material Design\n" +
            "  • Mudah dikustomisasi sesuai kebutuhan\n\n" +
            "📦 Tersedia untuk:\n" +
            "  • Proyek tugas akhir / skripsi\n" +
            "  • Sistem perpustakaan sekolah / kampus\n" +
            "  • Customization & development\n" +
            "  • Training & konsultasi"
        );
        txtInfo.setEditable(false);
        txtInfo.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        txtInfo.setBackground(new Color(232, 245, 233));
        txtInfo.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 230, 201)),
            new EmptyBorder(15, 18, 15, 18)
        ));
        txtInfo.setForeground(new Color(33, 70, 33));
        txtInfo.setAlignmentX(Component.CENTER_ALIGNMENT);
        centerPanel.add(txtInfo);
        
        // Wrapper dengan scroll
        JScrollPane scrollPane = new JScrollPane(centerPanel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        add(scrollPane, BorderLayout.CENTER);
    }
    
    private JPanel createHeaderPanel(String title, String subtitle) {
        JPanel panel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                GradientPaint gp = new GradientPaint(0, 0, COLOR_PRIMARY, getWidth(), 0, new Color(40,116,166));
                g2.setPaint(gp);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                g2.dispose();
            }
        };
        panel.setOpaque(false);
        panel.setBorder(new EmptyBorder(15, 20, 15, 20));
        JLabel lblTitle = new JLabel(title);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTitle.setForeground(Color.WHITE);
        JLabel lblSub = new JLabel(subtitle);
        lblSub.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lblSub.setForeground(new Color(255,255,255,180));
        JPanel tp = new JPanel(new GridLayout(2,1,0,2));
        tp.setOpaque(false);
        tp.add(lblTitle); tp.add(lblSub);
        panel.add(tp, BorderLayout.CENTER);
        return panel;
    }
    
    private JPanel createContactCard(String icon, String label, String value, String link) {
        JPanel card = new JPanel(new BorderLayout(15, 0));
        card.setBackground(COLOR_WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220,225,230)),
            new EmptyBorder(15, 20, 15, 20)
        ));
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 70));
        card.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Icon
        JLabel lblIcon = new JLabel(icon);
        lblIcon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 28));
        
        // Text panel
        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setOpaque(false);
        
        JLabel lblLabel = new JLabel(label);
        lblLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblLabel.setForeground(new Color(44, 62, 80));
        
        JLabel lblValue = new JLabel(value);
        lblValue.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblValue.setForeground(new Color(52, 152, 219));
        lblValue.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        textPanel.add(lblLabel);
        textPanel.add(Box.createVerticalStrut(3));
        textPanel.add(lblValue);
        
        // Button
        JButton btnOpen = new JButton("Buka") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getModel().isRollover() ? COLOR_SUCCESS.darker() : COLOR_SUCCESS);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 6, 6);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        btnOpen.setFont(new Font("Segoe UI", Font.BOLD, 11));
        btnOpen.setForeground(Color.WHITE);
        btnOpen.setOpaque(false);
        btnOpen.setContentAreaFilled(false);
        btnOpen.setBorderPainted(false);
        btnOpen.setPreferredSize(new Dimension(80, 32));
        btnOpen.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnOpen.setFocusPainted(false);
        
        btnOpen.addActionListener(e -> openLink(link));
        
        card.add(lblIcon, BorderLayout.WEST);
        card.add(textPanel, BorderLayout.CENTER);
        card.add(btnOpen, BorderLayout.EAST);
        
        // Hover effect
        card.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                card.setBackground(new Color(248, 249, 250));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                card.setBackground(COLOR_WHITE);
            }
        });
        
        return card;
    }
    
    private void openLink(String url) {
        try {
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().browse(new URI(url));
            } else {
                JOptionPane.showMessageDialog(this,
                    "Tidak dapat membuka browser.\n\nSilakan copy link ini:\n" + url,
                    "Info", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Error membuka link!\n\nSilakan copy link ini:\n" + url,
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
