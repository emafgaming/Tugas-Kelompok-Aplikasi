package librarymanagement.view;

import librarymanagement.controller.PeminjamanController;
import librarymanagement.model.AnggotaModel;
import librarymanagement.model.BukuModel;
import librarymanagement.model.PeminjamanModel;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

/**
 * FormPeminjaman - View untuk transaksi peminjaman dan pengembalian buku
 * Lengkap dengan kalkulasi denda otomatis
 */
public class FormPeminjaman extends JPanel {
    
    // ============================================================
    // KOMPONEN GUI
    // ============================================================
    // Panel Tab
    private JTabbedPane tabbedPane;
    
    // Tab Peminjaman
    private JComboBox<AnggotaModel> cmbAnggota;
    private JComboBox<BukuModel>    cmbBuku;
    private JTextField  txtTanggalPinjam, txtTanggalKembali, txtJumlah;
    private JLabel      lblInfoStok;
    private JButton     btnPinjam, btnBatal;
    
    // Tab Daftar & Pengembalian
    private JTextField  txtSearch;
    private JButton     btnKembalikan, btnRefresh;
    private JTable      tblPeminjaman;
    private DefaultTableModel tableModel;
    private int         selectedIdPinjam = 0;
    
    // Tab Laporan
    private JTable      tblLaporan;
    private DefaultTableModel laporanTableModel;
    private JButton     btnRefreshLaporan;
    
    private PeminjamanController controller;
    
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
    public FormPeminjaman() {
        initComponents();
        controller = new PeminjamanController(this);
    }
    
    // ============================================================
    // INISIALISASI
    // ============================================================
    private void initComponents() {
        setLayout(new BorderLayout(0, 15));
        setBackground(COLOR_BG);
        setBorder(new EmptyBorder(20, 25, 20, 25));
        
        // HEADER
        add(createHeader("📋 Peminjaman & Pengembalian Buku",
            "Kelola transaksi peminjaman dan pengembalian buku"), BorderLayout.NORTH);
        
        // TABBED PANE
        tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        tabbedPane.setFont(new Font("Segoe UI", Font.BOLD, 13));
        tabbedPane.setBackground(COLOR_BG);
        
        tabbedPane.addTab("📝 Form Peminjaman", createScrollableTab(createPeminjamanTab()));
        tabbedPane.addTab("📋 Daftar Peminjaman", createDaftarTab());
        tabbedPane.addTab("📊 Laporan", createLaporanTab());
        
        add(tabbedPane, BorderLayout.CENTER);
    }
    
    /**
     * Membungkus panel dalam JScrollPane agar bisa di-scroll jika konten melebihi layar
     */
    private JScrollPane createScrollableTab(JPanel panel) {
        JScrollPane scroll = new JScrollPane(panel);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        return scroll;
    }
    
    // ============================================================
    // TAB 1: FORM PEMINJAMAN BARU
    // ============================================================
    private JPanel createPeminjamanTab() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(COLOR_WHITE);
        panel.setBorder(new EmptyBorder(25, 30, 25, 30));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill      = GridBagConstraints.HORIZONTAL;
        gbc.insets    = new Insets(8, 8, 8, 8);
        gbc.weightx   = 1.0;
        
        // Title
        JLabel lblTitle = new JLabel("Form Peminjaman Buku Baru");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblTitle.setForeground(COLOR_PRIMARY);
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 4;
        panel.add(lblTitle, gbc);
        
        // Separator
        JSeparator sep = new JSeparator();
        sep.setForeground(new Color(220,225,230));
        gbc.gridy = 1;
        gbc.insets = new Insets(2, 8, 15, 8);
        panel.add(sep, gbc);
        
        // Anggota
        gbc.gridwidth = 1; gbc.gridy = 2;
        gbc.insets = new Insets(5, 8, 3, 8);
        panel.add(createLabel("Anggota *"), gbc);
        
        cmbAnggota = new JComboBox<>();
        cmbAnggota.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 4;
        gbc.insets = new Insets(0, 8, 10, 8);
        panel.add(cmbAnggota, gbc);
        
        // Buku
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 3;
        gbc.insets = new Insets(5, 8, 3, 8);
        panel.add(createLabel("Buku yang Dipinjam *"), gbc);
        
        cmbBuku = new JComboBox<>();
        cmbBuku.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        gbc.gridy = 5; gbc.gridwidth = 3;
        gbc.insets = new Insets(0, 8, 5, 8);
        panel.add(cmbBuku, gbc);
        
        // Jumlah
        gbc.gridx = 3; gbc.gridy = 4; gbc.gridwidth = 1;
        gbc.insets = new Insets(5, 0, 3, 8);
        panel.add(createLabel("Jumlah *"), gbc);
        
        txtJumlah = createTextField("1");
        gbc.gridy = 5;
        gbc.insets = new Insets(0, 0, 5, 8);
        panel.add(txtJumlah, gbc);
        
        // Info stok
        lblInfoStok = new JLabel("Stok tersedia: -");
        lblInfoStok.setFont(new Font("Segoe UI", Font.ITALIC, 11));
        lblInfoStok.setForeground(new Color(52, 152, 219));
        gbc.gridx = 0; gbc.gridy = 6; gbc.gridwidth = 4;
        gbc.insets = new Insets(0, 8, 10, 8);
        panel.add(lblInfoStok, gbc);
        
        // Tanggal Pinjam
        gbc.gridy = 7; gbc.gridwidth = 2;
        gbc.insets = new Insets(5, 8, 3, 8);
        panel.add(createLabel("Tanggal Pinjam *"), gbc);
        
        gbc.gridx = 2;
        panel.add(createLabel("Tanggal Kembali (Batas) *"), gbc);
        
        txtTanggalPinjam  = createTextField(PeminjamanModel.getTanggalHariIni());
        txtTanggalKembali = createTextField(PeminjamanModel.getTanggalKembaliDefault());
        
        gbc.gridx = 0; gbc.gridy = 8; gbc.gridwidth = 2;
        gbc.insets = new Insets(0, 8, 10, 8);
        panel.add(txtTanggalPinjam, gbc);
        
        gbc.gridx = 2;
        panel.add(txtTanggalKembali, gbc);
        
        // Info tanggal
        JLabel lblDateInfo = new JLabel("⚠️  Format tanggal: YYYY-MM-DD (contoh: 2024-01-15)  |  Denda: Rp1.000/hari keterlambatan");
        lblDateInfo.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lblDateInfo.setForeground(new Color(211, 84, 0));
        gbc.gridx = 0; gbc.gridy = 9; gbc.gridwidth = 4;
        gbc.insets = new Insets(0, 8, 15, 8);
        panel.add(lblDateInfo, gbc);
        
        // Tombol
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        btnPanel.setBackground(COLOR_WHITE);
        
        btnPinjam = createStyledBtn("✅  PINJAM SEKARANG", COLOR_SUCCESS, 180, 40);
        btnBatal  = createStyledBtn("✕  Batal", new Color(127,140,141), 100, 40);
        
        btnPanel.add(btnPinjam);
        btnPanel.add(btnBatal);
        
        gbc.gridy = 10;
        gbc.insets = new Insets(5, 5, 5, 5);
        panel.add(btnPanel, gbc);
        
        // EVENT
        btnPinjam.addActionListener(e -> controller.pinjam());
        btnBatal.addActionListener(e  -> clearForm());
        
        cmbBuku.addActionListener(e -> {
            Object sel = cmbBuku.getSelectedItem();
            if (controller != null && sel instanceof BukuModel) {
                controller.tampilkanInfoBuku(((BukuModel) sel).getIdBuku());
            }
        });
        
        return panel;
    }
    
    // ============================================================
    // TAB 2: DAFTAR PEMINJAMAN & PENGEMBALIAN
    // ============================================================
    private JPanel createDaftarTab() {
        JPanel panel = new JPanel(new BorderLayout(0, 10));
        panel.setBackground(COLOR_WHITE);
        panel.setBorder(new EmptyBorder(15, 15, 15, 15));
        
        // Toolbar
        JPanel toolbarPanel = new JPanel(new BorderLayout(10, 0));
        toolbarPanel.setBackground(COLOR_WHITE);
        toolbarPanel.setBorder(new EmptyBorder(0, 0, 10, 0));
        
        JPanel searchPanel = new JPanel(new BorderLayout(5, 0));
        searchPanel.setBackground(COLOR_WHITE);
        searchPanel.add(new JLabel("🔍 "), BorderLayout.WEST);
        txtSearch = new JTextField();
        txtSearch.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(189,195,199)),
            new EmptyBorder(5, 8, 5, 8)
        ));
        txtSearch.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        txtSearch.setToolTipText("Cari berdasarkan nama anggota");
        searchPanel.add(txtSearch, BorderLayout.CENTER);
        
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        btnPanel.setBackground(COLOR_WHITE);
        
        btnKembalikan = createStyledBtn("🔄  KEMBALIKAN", COLOR_SUCCESS, 150, 34);
        btnRefresh    = createStyledBtn("⟳  Refresh", COLOR_PRIMARY, 100, 34);
        JButton btnAktif  = createStyledBtn("📋  Aktif Saja", COLOR_WARNING, 110, 34);
        JButton btnHilang = createStyledBtn("📕  Lapor Hilang", new Color(155, 89, 182), 140, 34);
        
        btnPanel.add(btnKembalikan);
        btnPanel.add(btnHilang);
        btnPanel.add(btnAktif);
        btnPanel.add(btnRefresh);
        
        toolbarPanel.add(searchPanel, BorderLayout.CENTER);
        toolbarPanel.add(btnPanel, BorderLayout.EAST);
        
        // Tabel - hidden ID column
        String[] cols = {"No", "ID Pinjam", "Nama Anggota", "Tgl Pinjam", "Batas Kembali", "Tgl Dikembalikan", "Status", "Denda"};
        tableModel = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        tblPeminjaman = new JTable(tableModel);
        styleTable(tblPeminjaman);
        
        // Sembunyikan kolom ID Pinjam (index 1)
        tblPeminjaman.getColumnModel().getColumn(1).setMinWidth(0);
        tblPeminjaman.getColumnModel().getColumn(1).setMaxWidth(0);
        tblPeminjaman.getColumnModel().getColumn(1).setPreferredWidth(0);
        
        int[] w = {40, 0, 150, 100, 110, 110, 90, 120};
        for (int i = 0; i < w.length; i++) {
            if (i != 1) tblPeminjaman.getColumnModel().getColumn(i).setPreferredWidth(w[i]);
        }
        
        // Sorting pada tabel peminjaman
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(tableModel);
        tblPeminjaman.setRowSorter(sorter);
        
        JScrollPane scrollPane = new JScrollPane(tblPeminjaman);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(220,225,230)));
        
        JLabel lblInfo = new JLabel("Pilih baris → Kembalikan / Lapor Hilang | Denda: Rp5.000/hari | Buku hilang: Rp100.000/buku");
        lblInfo.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lblInfo.setForeground(new Color(127,140,141));
        
        panel.add(toolbarPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(lblInfo, BorderLayout.SOUTH);
        
        // EVENT
        btnKembalikan.addActionListener(e -> controller.kembalikan(selectedIdPinjam));
        btnHilang.addActionListener(e     -> controller.laporHilang(selectedIdPinjam));
        btnRefresh.addActionListener(e    -> { selectedIdPinjam = 0; controller.loadData(); });
        btnAktif.addActionListener(e      -> { selectedIdPinjam = 0; controller.loadPeminjamanAktif(); });
        
        txtSearch.addKeyListener(new KeyAdapter() {
            @Override public void keyReleased(KeyEvent e) {
                controller.search(txtSearch.getText());
            }
        });
        
        tblPeminjaman.addMouseListener(new MouseAdapter() {
            @Override public void mouseClicked(MouseEvent e) {
                int viewRow = tblPeminjaman.getSelectedRow();
                if (viewRow >= 0) {
                    int modelRow = tblPeminjaman.convertRowIndexToModel(viewRow);
                    selectedIdPinjam = (int) tableModel.getValueAt(modelRow, 1); // ID Asli di kolom 1
                }
            }
        });
        
        return panel;
    }
    
    // ============================================================
    // TAB 3: LAPORAN
    // ============================================================
    private JPanel createLaporanTab() {
        JPanel panel = new JPanel(new BorderLayout(0, 10));
        panel.setBackground(COLOR_WHITE);
        panel.setBorder(new EmptyBorder(15, 15, 15, 15));
        
        JPanel topPanel = new JPanel(new BorderLayout(10, 0));
        topPanel.setBackground(COLOR_WHITE);
        
        JLabel lblTitle = new JLabel("📊 Laporan Peminjaman Buku");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 15));
        lblTitle.setForeground(COLOR_PRIMARY);
        
        btnRefreshLaporan = createStyledBtn("⟳  Refresh Laporan", COLOR_PRIMARY, 160, 34);
        
        topPanel.add(lblTitle, BorderLayout.WEST);
        topPanel.add(btnRefreshLaporan, BorderLayout.EAST);
        
        // Tabel laporan - hidden ID column
        String[] cols = {"No", "ID", "Nama Anggota", "Judul Buku", "Jml", "Tgl Pinjam", "Batas Kembali", "Tgl Dikembalikan", "Status", "Denda"};
        laporanTableModel = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        tblLaporan = new JTable(laporanTableModel);
        styleTable(tblLaporan);
        
        // Sembunyikan kolom ID (index 1)
        tblLaporan.getColumnModel().getColumn(1).setMinWidth(0);
        tblLaporan.getColumnModel().getColumn(1).setMaxWidth(0);
        tblLaporan.getColumnModel().getColumn(1).setPreferredWidth(0);
        
        int[] w = {35, 0, 130, 160, 40, 95, 95, 105, 80, 110};
        for (int i = 0; i < w.length; i++) {
            if (i != 1) tblLaporan.getColumnModel().getColumn(i).setPreferredWidth(w[i]);
        }
        
        JScrollPane scrollPane = new JScrollPane(tblLaporan);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(220,225,230)));
        
        JLabel lblInfo = new JLabel("Laporan lengkap semua transaksi peminjaman buku");
        lblInfo.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lblInfo.setForeground(new Color(127,140,141));
        
        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(lblInfo, BorderLayout.SOUTH);
        
        // EVENT
        btnRefreshLaporan.addActionListener(e -> controller.loadLaporan());
        
        return panel;
    }
    
    // ============================================================
    // HELPER METHODS
    // ============================================================
    private JPanel createHeader(String title, String subtitle) {
        JPanel panel = new JPanel(new BorderLayout()) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setPaint(new GradientPaint(0, 0, COLOR_PRIMARY, getWidth(), 0, new Color(40,116,166)));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                g2.dispose();
            }
        };
        panel.setOpaque(false); panel.setBorder(new EmptyBorder(15, 20, 15, 20));
        JLabel t = new JLabel(title); t.setFont(new Font("Segoe UI", Font.BOLD, 18)); t.setForeground(Color.WHITE);
        JLabel s = new JLabel(subtitle); s.setFont(new Font("Segoe UI", Font.PLAIN, 11)); s.setForeground(new Color(255,255,255,180));
        JPanel tp = new JPanel(new GridLayout(2,1,0,2)); tp.setOpaque(false); tp.add(t); tp.add(s);
        panel.add(tp, BorderLayout.CENTER);
        return panel;
    }
    
    private JLabel createLabel(String text) {
        JLabel l = new JLabel(text);
        l.setFont(new Font("Segoe UI", Font.BOLD, 12));
        l.setForeground(new Color(44,62,80));
        return l;
    }
    
    private JTextField createTextField(String defaultText) {
        JTextField tf = new JTextField(defaultText);
        tf.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        tf.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(189,195,199)),
            new EmptyBorder(5, 8, 5, 8)
        ));
        return tf;
    }
    
    private JButton createStyledBtn(String text, Color color, int w, int h) {
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
        btn.setPreferredSize(new Dimension(w, h));
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
                    // Color coding status
                    String cellVal = val != null ? val.toString() : "";
                    if (cellVal.equals("Dipinjam")) { setForeground(new Color(52, 152, 219)); setFont(getFont().deriveFont(Font.BOLD)); }
                    else if (cellVal.equals("Kembali")) { setForeground(new Color(39,174,96)); setFont(getFont().deriveFont(Font.BOLD)); }
                    else if (cellVal.equals("Terlambat")) { setForeground(new Color(192,57,43)); setFont(getFont().deriveFont(Font.BOLD)); }
                    else { setForeground(new Color(44,62,80)); setFont(getFont().deriveFont(Font.PLAIN)); }
                    setBackground(row % 2 == 0 ? COLOR_WHITE : new Color(248,249,250));
                }
                return this;
            }
        });
    }
    
    // ============================================================
    // GETTER untuk Controller
    // ============================================================
    public int getSelectedAnggotaId() {
        Object sel = cmbAnggota.getSelectedItem();
        return (sel instanceof AnggotaModel) ? ((AnggotaModel) sel).getIdAnggota() : -1;
    }
    
    public int getSelectedBukuId() {
        Object sel = cmbBuku.getSelectedItem();
        return (sel instanceof BukuModel) ? ((BukuModel) sel).getIdBuku() : -1;
    }
    
    public String getTanggalPinjam()  { return txtTanggalPinjam.getText(); }
    public String getTanggalKembali() { return txtTanggalKembali.getText(); }
    public String getJumlah()         { return txtJumlah.getText(); }
    
    public DefaultTableModel getTableModel()        { return tableModel; }
    public DefaultTableModel getLaporanTableModel() { return laporanTableModel; }
    
    public void setInfoStok(String info) { lblInfoStok.setText(info); }
    
    public void loadAnggotaCombo(List<AnggotaModel> list) {
        cmbAnggota.removeAllItems();
        for (AnggotaModel a : list) cmbAnggota.addItem(a);
    }
    
    public void loadBukuCombo(List<BukuModel> list) {
        cmbBuku.removeAllItems();
        for (BukuModel b : list) cmbBuku.addItem(b);
    }
    
    public void clearForm() {
        if (cmbAnggota.getItemCount() > 0) cmbAnggota.setSelectedIndex(0);
        if (cmbBuku.getItemCount() > 0) cmbBuku.setSelectedIndex(0);
        txtJumlah.setText("1");
        txtTanggalPinjam.setText(PeminjamanModel.getTanggalHariIni());
        txtTanggalKembali.setText(PeminjamanModel.getTanggalKembaliDefault());
        lblInfoStok.setText("Stok tersedia: -");
    }
}
