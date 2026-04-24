package librarymanagement.view;

import librarymanagement.controller.BukuController;
import librarymanagement.model.KategoriModel;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

/**
 * FormBuku - View untuk manajemen buku perpustakaan
 */
public class FormBuku extends JPanel {
    
    // ============================================================
    // KOMPONEN GUI
    // ============================================================
    private JTextField txtJudul, txtPenulis, txtPenerbit, txtTahun, txtStok, txtSearch;
    private JComboBox<KategoriModel> cmbKategori;
    private JComboBox<String> cmbFilterKategori;
    private JButton btnTambah, btnUpdate, btnHapus, btnBersihkan;
    private JTable tblBuku;
    private DefaultTableModel tableModel;
    private BukuController controller;
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
    public FormBuku() {
        initComponents();
        controller = new BukuController(this);
    }
    
    // ============================================================
    // INISIALISASI
    // ============================================================
    private void initComponents() {
        setLayout(new BorderLayout(15, 15));
        setBackground(COLOR_BG);
        setBorder(new EmptyBorder(20, 25, 20, 25));
        
        // HEADER
        add(createHeaderPanel("📖 Manajemen Buku", "Kelola koleksi buku perpustakaan"), BorderLayout.NORTH);
        
        // ==== KIRI: Form Input ====
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBackground(COLOR_WHITE);
        leftPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220,225,230)),
            new EmptyBorder(18, 18, 18, 18)
        ));
        leftPanel.setPreferredSize(new Dimension(280, 0));
        
        JLabel lblFormTitle = new JLabel("Form Data Buku");
        lblFormTitle.setFont(new Font("Segoe UI", Font.BOLD, 15));
        lblFormTitle.setForeground(COLOR_PRIMARY);
        lblFormTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        leftPanel.add(lblFormTitle);
        leftPanel.add(Box.createVerticalStrut(5));
        leftPanel.add(createSeparator());
        leftPanel.add(Box.createVerticalStrut(12));
        
        // Field-field input
        leftPanel.add(createFieldLabel("Judul Buku *"));
        txtJudul = createFormTextField();
        leftPanel.add(txtJudul);
        leftPanel.add(Box.createVerticalStrut(8));
        
        leftPanel.add(createFieldLabel("Penulis *"));
        txtPenulis = createFormTextField();
        leftPanel.add(txtPenulis);
        leftPanel.add(Box.createVerticalStrut(8));
        
        leftPanel.add(createFieldLabel("Penerbit *"));
        txtPenerbit = createFormTextField();
        leftPanel.add(txtPenerbit);
        leftPanel.add(Box.createVerticalStrut(8));
        
        // Tahun & Stok dalam satu baris
        JPanel rowPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        rowPanel.setOpaque(false);
        rowPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        rowPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
        
        JPanel tahunPanel = new JPanel(new BorderLayout(0, 4));
        tahunPanel.setOpaque(false);
        tahunPanel.add(createFieldLabel("Tahun *"), BorderLayout.NORTH);
        txtTahun = new JTextField();
        styleSmallField(txtTahun);
        tahunPanel.add(txtTahun, BorderLayout.CENTER);
        
        JPanel stokPanel = new JPanel(new BorderLayout(0, 4));
        stokPanel.setOpaque(false);
        stokPanel.add(createFieldLabel("Stok *"), BorderLayout.NORTH);
        txtStok = new JTextField();
        styleSmallField(txtStok);
        stokPanel.add(txtStok, BorderLayout.CENTER);
        
        rowPanel.add(tahunPanel);
        rowPanel.add(stokPanel);
        leftPanel.add(rowPanel);
        leftPanel.add(Box.createVerticalStrut(8));
        
        leftPanel.add(createFieldLabel("Kategori *"));
        cmbKategori = new JComboBox<>();
        styleComboBox(cmbKategori);
        leftPanel.add(cmbKategori);
        leftPanel.add(Box.createVerticalStrut(18));
        
        // Tombol CRUD
        btnTambah    = createStyledButton("➕  Tambah",   COLOR_SUCCESS);
        btnUpdate    = createStyledButton("✏️  Update",   COLOR_WARNING);
        btnHapus     = createStyledButton("🗑️  Hapus",    COLOR_DANGER);
        btnBersihkan = createStyledButton("🔄  Bersihkan", COLOR_PRIMARY);
        
        for (JButton btn : new JButton[]{btnTambah, btnUpdate, btnHapus, btnBersihkan}) {
            btn.setAlignmentX(Component.LEFT_ALIGNMENT);
            leftPanel.add(btn);
            leftPanel.add(Box.createVerticalStrut(6));
        }
        leftPanel.add(Box.createVerticalGlue());
        
        JLabel lblHint = new JLabel("<html><font color='gray' size='3'>" +
            "💡 Klik baris tabel untuk<br>memilih data yang akan<br>diedit atau dihapus</font></html>");
        lblHint.setAlignmentX(Component.LEFT_ALIGNMENT);
        leftPanel.add(lblHint);
        
        // ==== KANAN: Tabel ====
        JPanel rightPanel = new JPanel(new BorderLayout(0, 10));
        rightPanel.setBackground(COLOR_WHITE);
        rightPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220,225,230)),
            new EmptyBorder(15, 15, 15, 15)
        ));
        
        // Toolbar: Search + Filter
        JPanel toolbarPanel = new JPanel(new BorderLayout(10, 0));
        toolbarPanel.setBackground(COLOR_WHITE);
        
        // Search
        JPanel searchPanel = new JPanel(new BorderLayout(5, 0));
        searchPanel.setBackground(COLOR_WHITE);
        JLabel lblSearch = new JLabel("🔍");
        lblSearch.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 14));
        txtSearch = new JTextField();
        txtSearch.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        txtSearch.setToolTipText("Cari berdasarkan judul, penulis, atau penerbit");
        txtSearch.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(189,195,199)),
            new EmptyBorder(5, 8, 5, 8)
        ));
        searchPanel.add(lblSearch, BorderLayout.WEST);
        searchPanel.add(txtSearch, BorderLayout.CENTER);
        
        // Filter kategori
        JPanel filterPanel = new JPanel(new BorderLayout(5, 0));
        filterPanel.setBackground(COLOR_WHITE);
        JLabel lblFilter = new JLabel("Filter: ");
        lblFilter.setFont(new Font("Segoe UI", Font.BOLD, 12));
        cmbFilterKategori = new JComboBox<>();
        cmbFilterKategori.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        cmbFilterKategori.setPreferredSize(new Dimension(150, 32));
        filterPanel.add(lblFilter, BorderLayout.WEST);
        filterPanel.add(cmbFilterKategori, BorderLayout.CENTER);
        
        toolbarPanel.add(searchPanel, BorderLayout.CENTER);
        toolbarPanel.add(filterPanel, BorderLayout.EAST);
        
        // Tabel - kolom "ID Asli" disembunyikan
        String[] columns = {"No", "ID Asli", "Judul", "Penulis", "Penerbit", "Tahun", "Stok", "Kategori"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        tblBuku = new JTable(tableModel);
        styleTable(tblBuku);
        
        // Sembunyikan kolom ID Asli (index 1)
        tblBuku.getColumnModel().getColumn(1).setMinWidth(0);
        tblBuku.getColumnModel().getColumn(1).setMaxWidth(0);
        tblBuku.getColumnModel().getColumn(1).setPreferredWidth(0);
        
        // Lebar kolom
        int[] colWidths = {40, 0, 200, 130, 120, 60, 50, 100};
        for (int i = 0; i < colWidths.length; i++) {
            if (i != 1) tblBuku.getColumnModel().getColumn(i).setPreferredWidth(colWidths[i]);
        }
        
        // Sorting pada kolom Judul (index 2)
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(tableModel);
        tblBuku.setRowSorter(sorter);
        
        JScrollPane scrollPane = new JScrollPane(tblBuku);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(220,225,230)));
        
        JLabel lblCount = new JLabel("Total: 0 buku");
        lblCount.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lblCount.setForeground(new Color(127,140,141));
        
        rightPanel.add(toolbarPanel, BorderLayout.NORTH);
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
        btnTambah.addActionListener(e    -> { if (controller != null) controller.tambah(); });
        btnUpdate.addActionListener(e    -> { if (controller != null) controller.update(selectedId); });
        btnHapus.addActionListener(e     -> { if (controller != null) controller.hapus(selectedId); });
        btnBersihkan.addActionListener(e -> { clearForm(); if (controller != null) controller.loadData(); });
        
        txtSearch.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (controller != null) controller.search(txtSearch.getText());
            }
        });
        
        cmbFilterKategori.addActionListener(e -> {
            if (controller == null) return;  // Guard: jangan panggil saat init
            controller.loadData();
        });
        
        tblBuku.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int viewRow = tblBuku.getSelectedRow();
                if (viewRow >= 0) {
                    // Convert view row ke model row (untuk sorting)
                    int modelRow = tblBuku.convertRowIndexToModel(viewRow);
                    selectedId = (int) tableModel.getValueAt(modelRow, 1); // ID Asli di kolom 1
                    txtJudul.setText(tableModel.getValueAt(modelRow, 2).toString());
                    txtPenulis.setText(tableModel.getValueAt(modelRow, 3).toString());
                    txtPenerbit.setText(tableModel.getValueAt(modelRow, 4).toString());
                    txtTahun.setText(tableModel.getValueAt(modelRow, 5).toString());
                    txtStok.setText(tableModel.getValueAt(modelRow, 6).toString());
                    // Set kategori yang sesuai
                    String namaKat = tableModel.getValueAt(modelRow, 7).toString();
                    setSelectedKategori(namaKat);
                    lblCount.setText("Total: " + tableModel.getRowCount() + " buku | Dipilih: " + 
                        tableModel.getValueAt(modelRow, 2));
                }
            }
        });
        
        tableModel.addTableModelListener(te -> {
            lblCount.setText("Total: " + tableModel.getRowCount() + " buku");
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
    
    private JLabel createFieldLabel(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 11));
        lbl.setForeground(new Color(44,62,80));
        lbl.setAlignmentX(Component.LEFT_ALIGNMENT);
        return lbl;
    }
    
    private JTextField createFormTextField() {
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
    
    private void styleSmallField(JTextField tf) {
        tf.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        tf.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(189,195,199)),
            new EmptyBorder(5, 8, 5, 8)
        ));
    }
    
    private void styleComboBox(JComboBox<?> cmb) {
        cmb.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        cmb.setMaximumSize(new Dimension(Integer.MAX_VALUE, 32));
        cmb.setAlignmentX(Component.LEFT_ALIGNMENT);
    }
    
    private JSeparator createSeparator() {
        JSeparator sep = new JSeparator();
        sep.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
        sep.setAlignmentX(Component.LEFT_ALIGNMENT);
        return sep;
    }
    
    private JButton createStyledButton(String text, Color color) {
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
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 34));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setFocusPainted(false);
        return btn;
    }
    
    private void styleTable(JTable table) {
        table.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        table.setRowHeight(28);
        table.setSelectionBackground(new Color(52, 152, 219, 100));
        table.setSelectionForeground(new Color(44, 62, 80));
        table.setGridColor(new Color(225,230,235));
        table.setShowHorizontalLines(true);
        table.setShowVerticalLines(false);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        table.getTableHeader().setBackground(COLOR_PRIMARY);
        table.getTableHeader().setForeground(Color.WHITE);
        table.getTableHeader().setReorderingAllowed(false);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable t, Object val,
                    boolean sel, boolean foc, int row, int col) {
                super.getTableCellRendererComponent(t, val, sel, foc, row, col);
                setBorder(new EmptyBorder(0, 8, 0, 8));
                if (!sel) {
                    // Highlight stok rendah (< 2) - kolom Stok sekarang di index 6
                    if (col == 6) {
                        try {
                            int stok = Integer.parseInt(val.toString());
                            if (stok == 0) setForeground(Color.RED);
                            else if (stok < 3) setForeground(new Color(211, 84, 0));
                            else { setForeground(new Color(39, 174, 96)); }
                        } catch (Exception e) {}
                    } else {
                        setForeground(new Color(44,62,80));
                    }
                    setBackground(row % 2 == 0 ? COLOR_WHITE : new Color(248, 249, 250));
                }
                return this;
            }
        });
    }
    
    // ============================================================
    // GETTER untuk Controller
    // ============================================================
    public String getJudul()   { return txtJudul.getText(); }
    public String getPenulis() { return txtPenulis.getText(); }
    public String getPenerbit(){ return txtPenerbit.getText(); }
    public String getTahun()   { return txtTahun.getText(); }
    public String getStok()    { return txtStok.getText(); }
    
    public int getSelectedKategoriId() {
        Object sel = cmbKategori.getSelectedItem();
        if (sel instanceof KategoriModel) {
            return ((KategoriModel) sel).getIdKategori();
        }
        return -1;
    }
    
    public DefaultTableModel getTableModel() { return tableModel; }
    
    /**
     * Load daftar kategori ke ComboBox
     */
    public void loadKategoriCombo(List<KategoriModel> list) {
        // Hapus listener sementara agar tidak trigger saat pengisian
        cmbKategori.removeAllItems();
        for (KategoriModel k : list) {
            cmbKategori.addItem(k);
        }
        
        // Isi filter kategori (String saja, tidak trigger controller)
        DefaultComboBoxModel<String> filterModel = new DefaultComboBoxModel<>();
        filterModel.addElement("-- Semua Kategori --");
        for (KategoriModel k : list) filterModel.addElement(k.getNamaKategori());
        cmbFilterKategori.setModel(filterModel);
    }
    
    /**
     * Set kategori yang dipilih berdasarkan nama
     */
    public void setSelectedKategori(String namaKategori) {
        for (int i = 0; i < cmbKategori.getItemCount(); i++) {
            KategoriModel k = cmbKategori.getItemAt(i);
            if (k.getNamaKategori().equals(namaKategori)) {
                cmbKategori.setSelectedIndex(i);
                break;
            }
        }
    }
    
    public void clearForm() {
        txtJudul.setText(""); txtPenulis.setText(""); txtPenerbit.setText("");
        txtTahun.setText(""); txtStok.setText(""); txtSearch.setText("");
        if (cmbKategori.getItemCount() > 0) cmbKategori.setSelectedIndex(0);
        selectedId = 0;
        tblBuku.clearSelection();
    }
}
