package librarymanagement.view;

import librarymanagement.controller.PeminjamanController;
import librarymanagement.model.BukuModel;
import librarymanagement.model.PeminjamanModel;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.util.List;

/**
 * PanelLaporan - Panel khusus untuk laporan dan statistik perpustakaan
 * Menampilkan statistik ringkasan, buku terpopuler, dan riwayat transaksi
 */
public class PanelLaporan extends JPanel {
    
    // Statistik labels
    private JLabel lblTotalPinjam, lblDipinjam, lblTerlambat, lblKembali;
    
    // Tabel buku terpopuler
    private JTable tblTerpopuler;
    private DefaultTableModel modelTerpopuler;
    
    // Tabel riwayat transaksi
    private JTable tblRiwayat;
    private DefaultTableModel modelRiwayat;
    
    // Model
    private PeminjamanModel peminjamanModel;
    private BukuModel bukuModel;
    
    // Grafik
    private ChartPanel chartPanel;
    
    // Warna
    private static final Color COLOR_PRIMARY = new Color(26, 82, 118);
    private static final Color COLOR_BG      = new Color(240, 244, 248);
    private static final Color COLOR_WHITE   = Color.WHITE;
    
    // ============================================================
    // KONSTRUKTOR
    // ============================================================
    public PanelLaporan() {
        peminjamanModel = new PeminjamanModel();
        bukuModel       = new BukuModel();
        initComponents();
    }
    
    // ============================================================
    // INISIALISASI
    // ============================================================
    private void initComponents() {
        setLayout(new BorderLayout(0, 15));
        setBackground(COLOR_BG);
        setBorder(new EmptyBorder(20, 25, 20, 25));
        
        // HEADER
        add(createHeader(), BorderLayout.NORTH);
        
        // Konten: split menjadi atas (stats) dan bawah (tabel)
        JPanel contentPanel = new JPanel(new BorderLayout(0, 15));
        contentPanel.setBackground(COLOR_BG);
        
        // ==== STAT CARDS ====
        JPanel statsPanel = new JPanel(new GridLayout(1, 4, 15, 0));
        statsPanel.setBackground(COLOR_BG);
        
        lblTotalPinjam = new JLabel("0");
        lblDipinjam    = new JLabel("0");
        lblTerlambat   = new JLabel("0");
        lblKembali     = new JLabel("0");
        
        statsPanel.add(createStatCard("📚 Total Transaksi", lblTotalPinjam, new Color(52, 152, 219)));
        statsPanel.add(createStatCard("📋 Sedang Dipinjam", lblDipinjam,    new Color(230, 126, 34)));
        statsPanel.add(createStatCard("⚠️ Terlambat",       lblTerlambat,   new Color(192, 57, 43)));
        statsPanel.add(createStatCard("✅ Sudah Kembali",   lblKembali,     new Color(39, 174, 96)));
        
        // ==== TABEL KIRI + KANAN ====
        JPanel tablesPanel = new JPanel(new GridLayout(1, 2, 15, 0));
        tablesPanel.setBackground(COLOR_BG);
        
        // Tabel buku terpopuler
        JPanel leftPanel = new JPanel(new BorderLayout(0, 8));
        leftPanel.setBackground(COLOR_WHITE);
        leftPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220,225,230)),
            new EmptyBorder(15, 15, 15, 15)
        ));
        
        JLabel lblPop = new JLabel("🏆 Buku Paling Sering Dipinjam");
        lblPop.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblPop.setForeground(COLOR_PRIMARY);
        
        String[] colsPop = {"Judul Buku", "Penulis", "Total Dipinjam"};
        modelTerpopuler = new DefaultTableModel(colsPop, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        tblTerpopuler = new JTable(modelTerpopuler);
        styleTable(tblTerpopuler);
        tblTerpopuler.getColumnModel().getColumn(0).setPreferredWidth(180);
        tblTerpopuler.getColumnModel().getColumn(1).setPreferredWidth(120);
        tblTerpopuler.getColumnModel().getColumn(2).setPreferredWidth(80);
        
        JScrollPane scrollPop = new JScrollPane(tblTerpopuler);
        scrollPop.setBorder(BorderFactory.createLineBorder(new Color(220,225,230)));
        
        leftPanel.add(lblPop, BorderLayout.NORTH);
        leftPanel.add(scrollPop, BorderLayout.CENTER);
        
        // Tabel riwayat transaksi terbaru
        JPanel rightPanel = new JPanel(new BorderLayout(0, 8));
        rightPanel.setBackground(COLOR_WHITE);
        rightPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220,225,230)),
            new EmptyBorder(15, 15, 15, 15)
        ));
        
        JLabel lblRiwayat = new JLabel("📋 Riwayat Transaksi Terbaru");
        lblRiwayat.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblRiwayat.setForeground(COLOR_PRIMARY);
        
        // Tambah kolom ID (hidden) untuk referensi data
        String[] colsRiwayat = {"ID", "Anggota", "Judul Buku", "Tgl Pinjam", "Batas Kembali", "Status", "Denda"};
        modelRiwayat = new DefaultTableModel(colsRiwayat, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        tblRiwayat = new JTable(modelRiwayat);
        styleTable(tblRiwayat);
        
        // Sembunyikan kolom ID
        tblRiwayat.getColumnModel().getColumn(0).setMinWidth(0);
        tblRiwayat.getColumnModel().getColumn(0).setMaxWidth(0);
        tblRiwayat.getColumnModel().getColumn(0).setPreferredWidth(0);
        
        JScrollPane scrollRiwayat = new JScrollPane(tblRiwayat);
        scrollRiwayat.setBorder(BorderFactory.createLineBorder(new Color(220,225,230)));
        
        // ===== TOMBOL AKSI RIWAYAT =====
        JPanel riwayatBtnPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 4));
        riwayatBtnPanel.setBackground(COLOR_WHITE);
        
        JButton btnKembalikan = createActionButton("✅ Kembalikan", new Color(39, 174, 96));
        JButton btnTerlambat  = createActionButton("⚠️ Terlambat/Denda", new Color(192, 57, 43));
        JButton btnHilang     = createActionButton("📕 Hilang", new Color(155, 89, 182));
        
        riwayatBtnPanel.add(btnKembalikan);
        riwayatBtnPanel.add(btnTerlambat);
        riwayatBtnPanel.add(btnHilang);
        
        JPanel riwayatTop = new JPanel(new BorderLayout(0, 5));
        riwayatTop.setBackground(COLOR_WHITE);
        riwayatTop.add(lblRiwayat, BorderLayout.NORTH);
        riwayatTop.add(riwayatBtnPanel, BorderLayout.SOUTH);
        
        rightPanel.add(riwayatTop, BorderLayout.NORTH);
        rightPanel.add(scrollRiwayat, BorderLayout.CENTER);
        
        tablesPanel.add(leftPanel);
        tablesPanel.add(rightPanel);
        
        // ===== EVENT: Aksi tombol riwayat =====
        btnKembalikan.addActionListener(e -> aksiKembalikan());
        btnTerlambat.addActionListener(e -> aksiTerlambat());
        btnHilang.addActionListener(e -> aksiHilang());
        
        // Tombol refresh
        JButton btnRefresh = createActionButton("⟳  Refresh Laporan", COLOR_PRIMARY);
        btnRefresh.setPreferredSize(new Dimension(160, 34));
        btnRefresh.addActionListener(e -> loadData());
        
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 8));
        btnPanel.setBackground(COLOR_BG);
        btnPanel.add(btnRefresh);
        
        // ==== GRAFIK CHART ====
        chartPanel = new ChartPanel();
        chartPanel.setPreferredSize(new Dimension(600, 260));
        
        // ==== Layout vertikal: stats → chart → tables ====
        JPanel topSection = new JPanel(new BorderLayout(0, 12));
        topSection.setBackground(COLOR_BG);
        topSection.add(statsPanel, BorderLayout.NORTH);
        topSection.add(chartPanel, BorderLayout.CENTER);
        
        // Wrap semua konten dalam scroll
        JPanel innerContent = new JPanel();
        innerContent.setLayout(new BoxLayout(innerContent, BoxLayout.Y_AXIS));
        innerContent.setBackground(COLOR_BG);
        
        topSection.setAlignmentX(Component.LEFT_ALIGNMENT);
        topSection.setMaximumSize(new Dimension(Integer.MAX_VALUE, 400));
        tablesPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        innerContent.add(topSection);
        innerContent.add(Box.createVerticalStrut(12));
        innerContent.add(tablesPanel);
        
        contentPanel.add(innerContent, BorderLayout.CENTER);
        
        add(contentPanel, BorderLayout.CENTER);
        add(btnPanel, BorderLayout.SOUTH);
    }
    
    // ============================================================
    // AKSI TOMBOL RIWAYAT
    // ============================================================
    
    /**
     * Mendapatkan ID peminjaman dari baris yang dipilih
     */
    private int getSelectedRiwayatId() {
        int row = tblRiwayat.getSelectedRow();
        if (row < 0) return -1;
        return (int) modelRiwayat.getValueAt(row, 0); // Kolom ID (hidden)
    }
    
    /**
     * Aksi KEMBALIKAN - ubah status dari Dipinjam → Kembali
     */
    private void aksiKembalikan() {
        int idPinjam = getSelectedRiwayatId();
        if (idPinjam < 0) {
            JOptionPane.showMessageDialog(this, "Pilih transaksi yang ingin diproses!", 
                "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        PeminjamanModel pinjam = peminjamanModel.getPeminjamanById(idPinjam);
        if (pinjam == null) {
            JOptionPane.showMessageDialog(this, "Data tidak ditemukan!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (!"Dipinjam".equals(pinjam.getStatus())) {
            JOptionPane.showMessageDialog(this, 
                "Hanya transaksi berstatus 'Dipinjam' yang bisa dikembalikan!\nStatus saat ini: " + pinjam.getStatus(), 
                "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        String tanggalHariIni = PeminjamanModel.getTanggalHariIni();
        
        int confirm = JOptionPane.showConfirmDialog(this,
            "Konfirmasi pengembalian buku?\n\n" +
            "Anggota: " + pinjam.getNamaAnggota() + "\n" +
            "Batas kembali: " + pinjam.getTanggalKembali() + "\n" +
            "Tanggal hari ini: " + tanggalHariIni + "\n\n" +
            "Status akan diubah menjadi: KEMBALI",
            "Konfirmasi Kembalikan", JOptionPane.YES_NO_OPTION);
        
        if (confirm != JOptionPane.YES_OPTION) return;
        
        double denda = peminjamanModel.prosesPengembalian(idPinjam, tanggalHariIni);
        if (denda >= 0) {
            String pesan = "Buku berhasil dikembalikan!\n";
            if (denda > 0) {
                pesan += "Denda keterlambatan: " + PeminjamanController.formatRupiah(denda);
            } else {
                pesan += "Tepat waktu - Tidak ada denda.";
            }
            JOptionPane.showMessageDialog(this, pesan, "Sukses", JOptionPane.INFORMATION_MESSAGE);
            loadData();
        } else {
            JOptionPane.showMessageDialog(this, "Gagal memproses pengembalian!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Aksi TERLAMBAT/DENDA - tampilkan popup berapa lama telat & berapa dendanya
     */
    private void aksiTerlambat() {
        int idPinjam = getSelectedRiwayatId();
        if (idPinjam < 0) {
            JOptionPane.showMessageDialog(this, "Pilih transaksi yang ingin diproses!", 
                "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        PeminjamanModel pinjam = peminjamanModel.getPeminjamanById(idPinjam);
        if (pinjam == null) {
            JOptionPane.showMessageDialog(this, "Data tidak ditemukan!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (!"Dipinjam".equals(pinjam.getStatus())) {
            JOptionPane.showMessageDialog(this, 
                "Hanya transaksi berstatus 'Dipinjam' yang bisa diproses denda!\nStatus saat ini: " + pinjam.getStatus(), 
                "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        String tanggalHariIni = PeminjamanModel.getTanggalHariIni();
        double dendaPreview = PeminjamanModel.hitungDenda(pinjam.getTanggalKembali(), tanggalHariIni);
        
        // Hitung hari telat
        try {
            java.time.LocalDate batas = java.time.LocalDate.parse(pinjam.getTanggalKembali());
            java.time.LocalDate hari  = java.time.LocalDate.parse(tanggalHariIni);
            long hariTelat = java.time.temporal.ChronoUnit.DAYS.between(batas, hari);
            
            if (hariTelat <= 0) {
                JOptionPane.showMessageDialog(this, 
                    "Peminjaman ini BELUM melewati batas!\n\n" +
                    "Anggota: " + pinjam.getNamaAnggota() + "\n" +
                    "Batas kembali: " + pinjam.getTanggalKembali() + "\n" +
                    "Hari ini: " + tanggalHariIni + "\n" +
                    "Sisa waktu: " + Math.abs(hariTelat) + " hari",
                    "Belum Terlambat", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            
            int confirm = JOptionPane.showConfirmDialog(this,
                "⚠️ DENDA KETERLAMBATAN\n\n" +
                "Anggota: " + pinjam.getNamaAnggota() + "\n" +
                "Batas kembali: " + pinjam.getTanggalKembali() + "\n" +
                "Tanggal hari ini: " + tanggalHariIni + "\n\n" +
                "📅 Terlambat: " + hariTelat + " hari\n" +
                "💰 Denda: " + hariTelat + " hari × Rp5.000 = " + PeminjamanController.formatRupiah(dendaPreview) + "\n\n" +
                "Proses pengembalian dengan denda?",
                "Konfirmasi Denda Terlambat", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            
            if (confirm != JOptionPane.YES_OPTION) return;
            
            double denda = peminjamanModel.prosesPengembalian(idPinjam, tanggalHariIni);
            if (denda >= 0) {
                JOptionPane.showMessageDialog(this, 
                    "Pengembalian berhasil!\n\n" +
                    "Anggota harus membayar denda:\n" +
                    PeminjamanController.formatRupiah(denda),
                    "Denda Terlambat", JOptionPane.INFORMATION_MESSAGE);
                loadData();
            } else {
                JOptionPane.showMessageDialog(this, "Gagal memproses!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Aksi HILANG - mendenda anggota Rp100.000 per buku, stok tidak dikembalikan
     */
    private void aksiHilang() {
        int idPinjam = getSelectedRiwayatId();
        if (idPinjam < 0) {
            JOptionPane.showMessageDialog(this, "Pilih transaksi yang ingin diproses!", 
                "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        PeminjamanModel pinjam = peminjamanModel.getPeminjamanById(idPinjam);
        if (pinjam == null) {
            JOptionPane.showMessageDialog(this, "Data tidak ditemukan!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (!"Dipinjam".equals(pinjam.getStatus())) {
            JOptionPane.showMessageDialog(this, 
                "Hanya transaksi berstatus 'Dipinjam' yang bisa dilaporkan hilang!\nStatus saat ini: " + pinjam.getStatus(), 
                "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this,
            "📕 LAPOR BUKU HILANG\n\n" +
            "Anggota: " + pinjam.getNamaAnggota() + "\n\n" +
            "Pilihan ganti rugi:\n" +
            "  1. Membeli buku baru yang sama\n" +
            "  2. Membayar denda Rp100.000 per buku\n\n" +
            "Stok buku TIDAK akan dikembalikan.\n\n" +
            "Proses lapor hilang dengan denda Rp100.000?",
            "Konfirmasi Lapor Hilang", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        
        if (confirm != JOptionPane.YES_OPTION) return;
        
        double dendaHilang = peminjamanModel.prosesLaporHilang(idPinjam);
        if (dendaHilang >= 0) {
            JOptionPane.showMessageDialog(this, 
                "Buku berhasil dilaporkan HILANG!\n\n" +
                "Total denda: " + PeminjamanController.formatRupiah(dendaHilang) + "\n\n" +
                "Catatan:\nAnggota dapat mengganti dengan membeli buku baru\nsebagai pengganti denda.",
                "Lapor Hilang Berhasil", JOptionPane.INFORMATION_MESSAGE);
            loadData();
        } else {
            JOptionPane.showMessageDialog(this, "Gagal memproses lapor hilang!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Load semua data laporan
     */
    public void loadData() {
        loadStatistik();
        loadBukuTerpopuler();
        loadRiwayatTransaksi();
        if (chartPanel != null) chartPanel.refreshData();
    }
    
    /**
     * Load statistik ringkasan
     */
    private void loadStatistik() {
        int[] stats = peminjamanModel.getStatistik();
        lblTotalPinjam.setText(String.valueOf(stats[0]));
        lblDipinjam.setText(String.valueOf(stats[1]));
        lblTerlambat.setText(String.valueOf(stats[2]));
        lblKembali.setText(String.valueOf(stats[3]));
    }
    
    /**
     * Load buku terpopuler
     */
    private void loadBukuTerpopuler() {
        modelTerpopuler.setRowCount(0);
        List<Object[]> list = bukuModel.getBukuTerpopuler();
        for (Object[] row : list) {
            modelTerpopuler.addRow(row);
        }
    }
    
    /**
     * Load riwayat transaksi terbaru (10 terakhir)
     */
    private void loadRiwayatTransaksi() {
        modelRiwayat.setRowCount(0);
        List<Object[]> list = peminjamanModel.getLaporanPeminjaman();
        String hariIni = PeminjamanModel.getTanggalHariIni();
        int limit = Math.min(list.size(), 30);
        for (int i = 0; i < limit; i++) {
            Object[] row = list.get(i);
            double denda = (Double) row[8];
            String status = (String) row[7];
            
            // Real-time denda preview untuk yang terlambat
            if ("Dipinjam".equals(status)) {
                double dendaPreview = PeminjamanModel.hitungDenda((String) row[5], hariIni);
                if (dendaPreview > 0) {
                    denda = dendaPreview;
                    status = "Terlambat!";
                }
            }
            
            modelRiwayat.addRow(new Object[]{
                row[0], // ID peminjaman (hidden)
                row[1], // nama anggota
                row[2], // judul buku
                row[4], // tgl pinjam
                row[5], // batas kembali
                status,
                PeminjamanController.formatRupiah(denda)
            });
        }
    }
    
    // ============================================================
    // HELPER METHODS
    // ============================================================
    private JPanel createHeader() {
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
        JLabel t = new JLabel("📊 Laporan & Statistik"); t.setFont(new Font("Segoe UI", Font.BOLD, 18)); t.setForeground(Color.WHITE);
        JLabel s = new JLabel("Pantau statistik dan laporan perpustakaan secara real-time");
        s.setFont(new Font("Segoe UI", Font.PLAIN, 11)); s.setForeground(new Color(255,255,255,180));
        JPanel tp = new JPanel(new GridLayout(2,1,0,2)); tp.setOpaque(false); tp.add(t); tp.add(s);
        panel.add(tp, BorderLayout.CENTER);
        return panel;
    }
    
    private JPanel createStatCard(String title, JLabel lblValue, Color accentColor) {
        JPanel card = new JPanel(new BorderLayout(0, 8)) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(COLOR_WHITE);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);
                g2.setColor(accentColor);
                g2.setStroke(new BasicStroke(3));
                g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 12, 12);
                g2.fillRect(0, 0, 5, getHeight());
                g2.dispose();
            }
        };
        card.setOpaque(false);
        card.setBorder(new EmptyBorder(15, 20, 15, 15));
        
        lblValue.setFont(new Font("Segoe UI", Font.BOLD, 36));
        lblValue.setForeground(accentColor);
        lblValue.setHorizontalAlignment(SwingConstants.LEFT);
        
        JLabel lblTitle = new JLabel("<html>" + title + "</html>");
        lblTitle.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lblTitle.setForeground(new Color(100, 110, 130));
        
        card.add(lblValue, BorderLayout.CENTER);
        card.add(lblTitle, BorderLayout.SOUTH);
        return card;
    }
    
    private void styleTable(JTable table) {
        table.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        table.setRowHeight(28);
        table.setSelectionBackground(new Color(52, 152, 219, 100));
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
                    String v = val != null ? val.toString() : "";
                    if (v.equals("Dipinjam"))       { setForeground(new Color(52,152,219)); setFont(getFont().deriveFont(Font.BOLD)); }
                    else if (v.equals("Kembali"))    { setForeground(new Color(39,174,96));  setFont(getFont().deriveFont(Font.BOLD)); }
                    else if (v.equals("Terlambat") || v.equals("Terlambat!"))  { setForeground(new Color(192,57,43)); setFont(getFont().deriveFont(Font.BOLD)); }
                    else if (v.equals("Hilang"))     { setForeground(new Color(155,89,182)); setFont(getFont().deriveFont(Font.BOLD)); }
                    else { setForeground(new Color(44,62,80)); setFont(getFont().deriveFont(Font.PLAIN)); }
                    setBackground(row % 2 == 0 ? COLOR_WHITE : new Color(248,249,250));
                }
                return this;
            }
        });
    }
    
    /**
     * Buat tombol aksi yang styled dan terlihat jelas
     */
    private JButton createActionButton(String text, Color color) {
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
        btn.setFont(new Font("Segoe UI", Font.BOLD, 11));
        btn.setForeground(Color.WHITE);
        btn.setOpaque(false);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setPreferredSize(new Dimension(145, 30));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }
}
