package librarymanagement.view;

import librarymanagement.controller.AnggotaController;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.*;

/**
 * FormAnggota - View untuk manajemen anggota perpustakaan
 */
public class FormAnggota extends JPanel {
    
    // ============================================================
    // KOMPONEN GUI
    // ============================================================
    private JTextField txtNama, txtAlamat, txtNoHp, txtSearch;
    private JComboBox<String> cmbStatus, cmbFilterStatus;
    private JButton btnTambah, btnUpdate, btnHapus, btnBersihkan;
    private JTable tblAnggota;
    private DefaultTableModel tableModel;
    private AnggotaController controller;
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
    public FormAnggota() {
        initComponents();
        controller = new AnggotaController(this);
    }
    
    // ============================================================
    // INISIALISASI
    // ============================================================
    private void initComponents() {
        setLayout(new BorderLayout(15, 15));
        setBackground(COLOR_BG);
        setBorder(new EmptyBorder(20, 25, 20, 25));
        
        // HEADER
        add(createHeaderPanel("👥 Manajemen Anggota", "Kelola data anggota perpustakaan"), BorderLayout.NORTH);
        
        // ==== KIRI: Form Input ====
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBackground(COLOR_WHITE);
        leftPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220,225,230)),
            new EmptyBorder(18, 18, 18, 18)
        ));
        leftPanel.setPreferredSize(new Dimension(270, 0));
        
        JLabel lblTitle = new JLabel("Form Data Anggota");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 15));
        lblTitle.setForeground(COLOR_PRIMARY);
        lblTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        leftPanel.add(lblTitle);
        leftPanel.add(Box.createVerticalStrut(5));
        leftPanel.add(createSep());
        leftPanel.add(Box.createVerticalStrut(12));
        
        leftPanel.add(createLabel("Nama Lengkap *"));
        txtNama = createField();
        leftPanel.add(txtNama);
        leftPanel.add(Box.createVerticalStrut(8));
        
        leftPanel.add(createLabel("Alamat *"));
        txtAlamat = createField();
        leftPanel.add(txtAlamat);
        leftPanel.add(Box.createVerticalStrut(8));
        
        leftPanel.add(createLabel("No. HP *"));
        txtNoHp = createField();
        leftPanel.add(txtNoHp);
        leftPanel.add(Box.createVerticalStrut(8));
        
        leftPanel.add(createLabel("Status"));
        cmbStatus = new JComboBox<>(new String[]{"Aktif", "Tidak Aktif"});
        cmbStatus.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        cmbStatus.setMaximumSize(new Dimension(Integer.MAX_VALUE, 32));
        cmbStatus.setAlignmentX(Component.LEFT_ALIGNMENT);
        leftPanel.add(cmbStatus);
        leftPanel.add(Box.createVerticalStrut(18));
        
        btnTambah    = createBtn("➕  Tambah",   COLOR_SUCCESS);
        btnUpdate    = createBtn("✏️  Update",   COLOR_WARNING);
        btnHapus     = createBtn("🗑️  Hapus",    COLOR_DANGER);
        btnBersihkan = createBtn("🔄  Bersihkan", COLOR_PRIMARY);
        
        for (JButton b : new JButton[]{btnTambah, btnUpdate, btnHapus, btnBersihkan}) {
            b.setAlignmentX(Component.LEFT_ALIGNMENT);
            leftPanel.add(b);
            leftPanel.add(Box.createVerticalStrut(6));
        }
        leftPanel.add(Box.createVerticalGlue());
        
        JLabel hint = new JLabel("<html><font color='gray' size='3'>" +
            "💡 Klik baris tabel untuk<br>memilih data yang akan<br>diedit atau dihapus</font></html>");
        hint.setAlignmentX(Component.LEFT_ALIGNMENT);
        leftPanel.add(hint);
        
        // ==== KANAN: Tabel ====
        JPanel rightPanel = new JPanel(new BorderLayout(0, 10));
        rightPanel.setBackground(COLOR_WHITE);
        rightPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220,225,230)),
            new EmptyBorder(15, 15, 15, 15)
        ));
        
        // Toolbar
        JPanel toolbarPanel = new JPanel(new BorderLayout(10, 0));
        toolbarPanel.setBackground(COLOR_WHITE);
        
        JPanel searchPanel = new JPanel(new BorderLayout(5, 0));
        searchPanel.setBackground(COLOR_WHITE);
        searchPanel.add(new JLabel("🔍 "), BorderLayout.WEST);
        txtSearch = new JTextField();
        txtSearch.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(189,195,199)),
            new EmptyBorder(5, 8, 5, 8)
        ));
        txtSearch.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        txtSearch.setToolTipText("Cari berdasarkan nama, no. HP, atau alamat");
        searchPanel.add(txtSearch, BorderLayout.CENTER);
        
        JPanel filterPanel = new JPanel(new BorderLayout(5, 0));
        filterPanel.setBackground(COLOR_WHITE);
        JLabel lblFilter = new JLabel("Filter Status: ");
        lblFilter.setFont(new Font("Segoe UI", Font.BOLD, 12));
        cmbFilterStatus = new JComboBox<>(new String[]{"Semua", "Aktif", "Tidak Aktif"});
        cmbFilterStatus.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        cmbFilterStatus.setPreferredSize(new Dimension(140, 32));
        filterPanel.add(lblFilter, BorderLayout.WEST);
        filterPanel.add(cmbFilterStatus, BorderLayout.CENTER);
        
        toolbarPanel.add(searchPanel, BorderLayout.CENTER);
        toolbarPanel.add(filterPanel, BorderLayout.EAST);
        
        // Tabel - kolom "ID Asli" disembunyikan, "No" sebagai nomor urut
        String[] cols = {"No", "ID Asli", "Nama", "Alamat", "No. HP", "Status"};
        tableModel = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        tblAnggota = new JTable(tableModel);
        styleTable(tblAnggota);
        
        // Sembunyikan kolom ID Asli (index 1)
        tblAnggota.getColumnModel().getColumn(1).setMinWidth(0);
        tblAnggota.getColumnModel().getColumn(1).setMaxWidth(0);
        tblAnggota.getColumnModel().getColumn(1).setPreferredWidth(0);
        
        tblAnggota.getColumnModel().getColumn(0).setPreferredWidth(40);
        tblAnggota.getColumnModel().getColumn(2).setPreferredWidth(150);
        tblAnggota.getColumnModel().getColumn(3).setPreferredWidth(200);
        tblAnggota.getColumnModel().getColumn(4).setPreferredWidth(110);
        tblAnggota.getColumnModel().getColumn(5).setPreferredWidth(90);
        
        // Sorting pada kolom Nama (index 2)
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(tableModel);
        tblAnggota.setRowSorter(sorter);
        
        JScrollPane scrollPane = new JScrollPane(tblAnggota);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(220,225,230)));
        
        JLabel lblCount = new JLabel("Total: 0 anggota");
        lblCount.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lblCount.setForeground(new Color(127,140,141));
        
        rightPanel.add(toolbarPanel, BorderLayout.NORTH);
        rightPanel.add(scrollPane, BorderLayout.CENTER);
        rightPanel.add(lblCount, BorderLayout.SOUTH);
        
        // Assembling
        JPanel center = new JPanel(new BorderLayout(15, 0));
        center.setBackground(COLOR_BG);
        center.add(leftPanel, BorderLayout.WEST);
        center.add(rightPanel, BorderLayout.CENTER);
        add(center, BorderLayout.CENTER);
        
        // ============================================================
        // EVENT LISTENERS
        // ============================================================
        btnTambah.addActionListener(e    -> { if (controller != null) controller.tambah(); });
        btnUpdate.addActionListener(e    -> { if (controller != null) controller.update(selectedId); });
        btnHapus.addActionListener(e     -> { if (controller != null) controller.hapus(selectedId); });
        btnBersihkan.addActionListener(e -> { clearForm(); if (controller != null) controller.loadData(); });
        
        txtSearch.addKeyListener(new KeyAdapter() {
            @Override public void keyReleased(KeyEvent e) {
                if (controller != null) controller.search(txtSearch.getText());
            }
        });
        
        cmbFilterStatus.addActionListener(e -> {
            String status = (String) cmbFilterStatus.getSelectedItem();
            if (controller != null) controller.filterByStatus(status);
        });
        
        tblAnggota.addMouseListener(new MouseAdapter() {
            @Override public void mouseClicked(MouseEvent e) {
                int viewRow = tblAnggota.getSelectedRow();
                if (viewRow >= 0) {
                    // Convert view row ke model row (untuk sorting)
                    int modelRow = tblAnggota.convertRowIndexToModel(viewRow);
                    selectedId = (int) tableModel.getValueAt(modelRow, 1); // ID Asli di kolom 1
                    txtNama.setText(tableModel.getValueAt(modelRow, 2).toString());
                    txtAlamat.setText(tableModel.getValueAt(modelRow, 3).toString());
                    txtNoHp.setText(tableModel.getValueAt(modelRow, 4).toString());
                    cmbStatus.setSelectedItem(tableModel.getValueAt(modelRow, 5).toString());
                    lblCount.setText("Total: " + tableModel.getRowCount() + " anggota | Dipilih: " + 
                        tableModel.getValueAt(modelRow, 2));
                    
                    // Disable tombol Tambah saat ada data yang dipilih
                    btnTambah.setEnabled(false);
                }
            }
        });
        
        tableModel.addTableModelListener(te -> {
            lblCount.setText("Total: " + tableModel.getRowCount() + " anggota");
        });
    }
    
    // ============================================================
    // HELPER METHODS
    // ============================================================
    private JPanel createHeaderPanel(String title, String subtitle) {
        JPanel panel = new JPanel(new BorderLayout()) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setPaint(new GradientPaint(0, 0, COLOR_PRIMARY, getWidth(), 0, new Color(40,116,166)));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                g2.dispose();
            }
        };
        panel.setOpaque(false);
        panel.setBorder(new EmptyBorder(15, 20, 15, 20));
        JLabel t = new JLabel(title); t.setFont(new Font("Segoe UI", Font.BOLD, 18)); t.setForeground(Color.WHITE);
        JLabel s = new JLabel(subtitle); s.setFont(new Font("Segoe UI", Font.PLAIN, 11)); s.setForeground(new Color(255,255,255,180));
        JPanel tp = new JPanel(new GridLayout(2,1,0,2)); tp.setOpaque(false); tp.add(t); tp.add(s);
        panel.add(tp, BorderLayout.CENTER);
        return panel;
    }
    
    private JLabel createLabel(String text) {
        JLabel l = new JLabel(text);
        l.setFont(new Font("Segoe UI", Font.BOLD, 11));
        l.setForeground(new Color(44,62,80));
        l.setAlignmentX(Component.LEFT_ALIGNMENT);
        return l;
    }
    
    private JTextField createField() {
        JTextField tf = new JTextField();
        tf.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        tf.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(189,195,199)),
            new EmptyBorder(5, 8, 5, 8)
        ));
        tf.setMaximumSize(new Dimension(Integer.MAX_VALUE, 32));
        tf.setAlignmentX(Component.LEFT_ALIGNMENT);
        return tf;
    }
    
    private JSeparator createSep() {
        JSeparator s = new JSeparator();
        s.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
        s.setAlignmentX(Component.LEFT_ALIGNMENT);
        return s;
    }
    
    private JButton createBtn(String text, Color color) {
        JButton btn = new JButton(text) {
            @Override protected void paintComponent(Graphics g) {
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
        btn.setOpaque(false); btn.setContentAreaFilled(false); btn.setBorderPainted(false);
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 34));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR)); btn.setFocusPainted(false);
        return btn;
    }
    
    private void styleTable(JTable table) {
        table.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        table.setRowHeight(28);
        table.setSelectionBackground(new Color(52, 152, 219, 100));
        table.setSelectionForeground(new Color(44, 62, 80));
        table.setGridColor(new Color(225,230,235));
        table.setShowHorizontalLines(true); table.setShowVerticalLines(false);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        table.getTableHeader().setBackground(COLOR_PRIMARY);
        table.getTableHeader().setForeground(Color.WHITE);
        table.getTableHeader().setReorderingAllowed(false);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override public Component getTableCellRendererComponent(JTable t, Object val,
                    boolean sel, boolean foc, int row, int col) {
                super.getTableCellRendererComponent(t, val, sel, foc, row, col);
                setBorder(new EmptyBorder(0, 8, 0, 8));
                if (!sel) {
                    // Status coloring - kolom Status sekarang di index 5
                    if (col == 5 && val != null) {
                        setForeground("Aktif".equals(val.toString()) ? new Color(39,174,96) : new Color(192,57,43));
                    } else {
                        setForeground(new Color(44,62,80));
                    }
                    setBackground(row % 2 == 0 ? COLOR_WHITE : new Color(248,249,250));
                }
                return this;
            }
        });
    }
    
    // ============================================================
    // GETTER untuk Controller
    // ============================================================
    public String getNama()            { return txtNama.getText(); }
    public String getAlamat()          { return txtAlamat.getText(); }
    public String getNoHp()            { return txtNoHp.getText(); }
    public String getStatus()          { return (String) cmbStatus.getSelectedItem(); }
    public DefaultTableModel getTableModel() { return tableModel; }
    
    public void clearForm() {
        txtNama.setText(""); txtAlamat.setText(""); txtNoHp.setText("");
        txtSearch.setText(""); cmbStatus.setSelectedIndex(0);
        selectedId = 0; tblAnggota.clearSelection();
        // Aktifkan kembali tombol Tambah
        btnTambah.setEnabled(true);
    }
}
