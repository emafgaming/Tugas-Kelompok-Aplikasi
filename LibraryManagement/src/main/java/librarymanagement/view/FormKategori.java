package librarymanagement.view;

import librarymanagement.controller.KategoriController;
import librarymanagement.model.KategoriModel;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.*;

/**
 * FormKategori - View untuk manajemen kategori buku
 */
public class FormKategori extends JPanel {
    
    // ============================================================
    // KOMPONEN GUI
    // ============================================================
    private JTextField txtNamaKategori;
    private JTextField txtSearch;
    private JButton btnTambah, btnUpdate, btnHapus, btnBersihkan;
    private JTable tblKategori;
    private DefaultTableModel tableModel;
    private KategoriController controller;
    private int selectedId = 0;
    
    // Warna
    private static final Color COLOR_PRIMARY = new Color(26, 82, 118);
    private static final Color COLOR_SUCCESS = new Color(39, 174, 96);
    private static final Color COLOR_DANGER  = new Color(192, 57, 43);
    private static final Color COLOR_WARNING = new Color(211, 84, 0);
    private static final Color COLOR_BG      = new Color(240, 244, 248);
    private static final Color COLOR_WHITE   = Color.WHITE;
    
    // ============================================================
    // KONSTRUKTOR
    // ============================================================
    public FormKategori() {
        initComponents();
        controller = new KategoriController(this);
    }
    
    // ============================================================
    // INISIALISASI
    // ============================================================
    private void initComponents() {
        setLayout(new BorderLayout(15, 15));
        setBackground(COLOR_BG);
        setBorder(new EmptyBorder(20, 25, 20, 25));
        
        // ==== HEADER ====
        JPanel headerPanel = createHeaderPanel("🏷️ Manajemen Kategori Buku", 
            "Kelola kategori untuk pengelompokan koleksi buku");
        add(headerPanel, BorderLayout.NORTH);
        
        // ==== PANEL KIRI (Form Input) ====
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBackground(COLOR_WHITE);
        leftPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220,225,230)),
            new EmptyBorder(20, 20, 20, 20)
        ));
        leftPanel.setPreferredSize(new Dimension(260, 0));
        
        JLabel lblFormTitle = new JLabel("Form Kategori");
        lblFormTitle.setFont(new Font("Segoe UI", Font.BOLD, 15));
        lblFormTitle.setForeground(COLOR_PRIMARY);
        lblFormTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JSeparator sep = new JSeparator();
        sep.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
        sep.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        leftPanel.add(lblFormTitle);
        leftPanel.add(Box.createVerticalStrut(8));
        leftPanel.add(sep);
        leftPanel.add(Box.createVerticalStrut(15));
        
        // Field Nama Kategori
        leftPanel.add(createFieldLabel("Nama Kategori *"));
        leftPanel.add(Box.createVerticalStrut(5));
        txtNamaKategori = createTextField();
        leftPanel.add(txtNamaKategori);
        leftPanel.add(Box.createVerticalStrut(20));
        
        // Tombol-tombol
        btnTambah    = createButton("➕  Tambah",   COLOR_SUCCESS);
        btnUpdate    = createButton("✏️  Update",   COLOR_WARNING);
        btnHapus     = createButton("🗑️  Hapus",    COLOR_DANGER);
        btnBersihkan = createButton("🔄  Bersihkan", COLOR_PRIMARY);
        
        btnTambah.setAlignmentX(Component.LEFT_ALIGNMENT);
        btnUpdate.setAlignmentX(Component.LEFT_ALIGNMENT);
        btnHapus.setAlignmentX(Component.LEFT_ALIGNMENT);
        btnBersihkan.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        leftPanel.add(btnTambah);
        leftPanel.add(Box.createVerticalStrut(8));
        leftPanel.add(btnUpdate);
        leftPanel.add(Box.createVerticalStrut(8));
        leftPanel.add(btnHapus);
        leftPanel.add(Box.createVerticalStrut(8));
        leftPanel.add(btnBersihkan);
        leftPanel.add(Box.createVerticalGlue());
        
        // Info hint
        JLabel lblHint = new JLabel("<html><font color='gray' size='3'>" +
            "💡 Klik baris tabel untuk<br>memilih data yang akan<br>diedit atau dihapus</font></html>");
        lblHint.setAlignmentX(Component.LEFT_ALIGNMENT);
        leftPanel.add(lblHint);
        
        // ==== PANEL KANAN (Tabel) ====
        JPanel rightPanel = new JPanel(new BorderLayout(0, 10));
        rightPanel.setBackground(COLOR_WHITE);
        rightPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220,225,230)),
            new EmptyBorder(15, 15, 15, 15)
        ));
        
        // Search bar
        JPanel searchPanel = new JPanel(new BorderLayout(8, 0));
        searchPanel.setBackground(COLOR_WHITE);
        JLabel lblSearch = new JLabel("🔍 Cari: ");
        lblSearch.setFont(new Font("Segoe UI", Font.BOLD, 12));
        txtSearch = new JTextField();
        txtSearch.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        txtSearch.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(189,195,199)),
            new EmptyBorder(5, 8, 5, 8)
        ));
        searchPanel.add(lblSearch, BorderLayout.WEST);
        searchPanel.add(txtSearch, BorderLayout.CENTER);
        
        // Tabel - hidden ID column
        String[] columns = {"No", "ID Asli", "Nama Kategori"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        tblKategori = new JTable(tableModel);
        styleTable(tblKategori);
        
        // Sembunyikan kolom ID Asli (index 1)
        tblKategori.getColumnModel().getColumn(1).setMinWidth(0);
        tblKategori.getColumnModel().getColumn(1).setMaxWidth(0);
        tblKategori.getColumnModel().getColumn(1).setPreferredWidth(0);
        
        tblKategori.getColumnModel().getColumn(0).setPreferredWidth(50);
        tblKategori.getColumnModel().getColumn(2).setPreferredWidth(300);
        
        JScrollPane scrollPane = new JScrollPane(tblKategori);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(220,225,230)));
        
        // Label jumlah data
        JLabel lblCount = new JLabel("Total: 0 kategori");
        lblCount.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lblCount.setForeground(new Color(127,140,141));
        
        rightPanel.add(searchPanel, BorderLayout.NORTH);
        rightPanel.add(scrollPane, BorderLayout.CENTER);
        rightPanel.add(lblCount, BorderLayout.SOUTH);
        
        // ==== ASSEMBLING ====
        JPanel centerPanel = new JPanel(new BorderLayout(15, 0));
        centerPanel.setBackground(COLOR_BG);
        centerPanel.add(leftPanel, BorderLayout.WEST);
        centerPanel.add(rightPanel, BorderLayout.CENTER);
        add(centerPanel, BorderLayout.CENTER);
        
        // ============================================================
        // EVENT LISTENERS
        // ============================================================
        btnTambah.addActionListener(e -> { if (controller != null) controller.tambah(); });
        btnUpdate.addActionListener(e -> { if (controller != null) controller.update(selectedId); });
        btnHapus.addActionListener(e  -> { if (controller != null) controller.hapus(selectedId); });
        btnBersihkan.addActionListener(e -> {
            clearForm();
            if (controller != null) controller.loadData();
        });
        
        txtSearch.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (controller != null) controller.search(txtSearch.getText());
            }
        });
        
        // Klik tabel untuk isi form
        tblKategori.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = tblKategori.getSelectedRow();
                if (row >= 0) {
                    selectedId = (int) tableModel.getValueAt(row, 1); // ID Asli di kolom 1
                    txtNamaKategori.setText(tableModel.getValueAt(row, 2).toString());
                    // Update jumlah data
                    lblCount.setText("Total: " + tableModel.getRowCount() + " kategori | Dipilih: No " + 
                        tableModel.getValueAt(row, 0));
                }
            }
        });
        
        // Update count setiap kali data berubah
        tableModel.addTableModelListener(e -> {
            lblCount.setText("Total: " + tableModel.getRowCount() + " kategori");
        });
    }
    
    // ============================================================
    // HELPER METHODS
    // ============================================================
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
        
        JLabel lblTitle    = new JLabel(title);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTitle.setForeground(Color.WHITE);
        
        JLabel lblSubtitle = new JLabel(subtitle);
        lblSubtitle.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lblSubtitle.setForeground(new Color(255,255,255,180));
        
        JPanel textPanel = new JPanel(new GridLayout(2,1,0,2));
        textPanel.setOpaque(false);
        textPanel.add(lblTitle);
        textPanel.add(lblSubtitle);
        panel.add(textPanel, BorderLayout.CENTER);
        return panel;
    }
    
    private JLabel createFieldLabel(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lbl.setForeground(new Color(44,62,80));
        lbl.setAlignmentX(Component.LEFT_ALIGNMENT);
        return lbl;
    }
    
    private JTextField createTextField() {
        JTextField tf = new JTextField();
        tf.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        tf.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(189,195,199)),
            new EmptyBorder(6, 10, 6, 10)
        ));
        tf.setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));
        tf.setAlignmentX(Component.LEFT_ALIGNMENT);
        return tf;
    }
    
    private JButton createButton(String text, Color color) {
        JButton btn = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getModel().isRollover() ? color.darker() : color);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 6, 6);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btn.setForeground(Color.WHITE);
        btn.setOpaque(false);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setFocusPainted(false);
        return btn;
    }
    
    private void styleTable(JTable table) {
        table.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        table.setRowHeight(30);
        table.setSelectionBackground(new Color(52, 152, 219, 100));
        table.setSelectionForeground(new Color(44, 62, 80));
        table.setGridColor(new Color(220,225,230));
        table.setShowHorizontalLines(true);
        table.setShowVerticalLines(false);
        table.setIntercellSpacing(new Dimension(0, 1));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        table.getTableHeader().setBackground(COLOR_PRIMARY);
        table.getTableHeader().setForeground(Color.WHITE);
        table.getTableHeader().setReorderingAllowed(false);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        // Striped rows
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable t, Object val,
                    boolean sel, boolean foc, int row, int col) {
                super.getTableCellRendererComponent(t, val, sel, foc, row, col);
                setBorder(new EmptyBorder(0, 10, 0, 10));
                if (!sel) setBackground(row % 2 == 0 ? COLOR_WHITE : new Color(248, 249, 250));
                return this;
            }
        });
    }
    
    // ============================================================
    // GETTER untuk Controller
    // ============================================================
    public String getNamaKategori()          { return txtNamaKategori.getText(); }
    public DefaultTableModel getTableModel() { return tableModel; }
    
    public void clearForm() {
        txtNamaKategori.setText("");
        txtSearch.setText("");
        selectedId = 0;
        tblKategori.clearSelection();
    }
}
