package librarymanagement.view;

import librarymanagement.controller.LoginController;
import librarymanagement.controller.PeminjamanController;
import librarymanagement.model.BukuModel;
import librarymanagement.model.PeminjamanModel;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

/**
 * Panel khusus untuk laporan dan statistik perpustakaan.
 */
public class PanelLaporan extends JPanel {

    private JLabel lblTotalPinjam;
    private JLabel lblDipinjam;
    private JLabel lblTerlambat;
    private JLabel lblKembali;

    private JTable tblTerpopuler;
    private DefaultTableModel modelTerpopuler;

    private JTable tblRiwayat;
    private DefaultTableModel modelRiwayat;

    private final PeminjamanModel peminjamanModel;
    private final BukuModel bukuModel;

    private ChartPanel chartPanel;

    private static final Color COLOR_PRIMARY = new Color(26, 82, 118);
    private static final Color COLOR_BG = new Color(240, 244, 248);
    private static final Color COLOR_WHITE = Color.WHITE;

    public PanelLaporan() {
        peminjamanModel = new PeminjamanModel();
        bukuModel = new BukuModel();
        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout(0, 15));
        setBackground(COLOR_BG);
        setBorder(new EmptyBorder(20, 25, 20, 25));

        add(createHeader(), BorderLayout.NORTH);

        JPanel contentPanel = new JPanel(new BorderLayout(0, 15));
        contentPanel.setBackground(COLOR_BG);

        JPanel statsPanel = new JPanel(new GridLayout(1, 4, 15, 0));
        statsPanel.setBackground(COLOR_BG);

        lblTotalPinjam = new JLabel("0");
        lblDipinjam = new JLabel("0");
        lblTerlambat = new JLabel("0");
        lblKembali = new JLabel("0");

        statsPanel.add(createStatCard("Total Transaksi", lblTotalPinjam, new Color(52, 152, 219)));
        statsPanel.add(createStatCard("Sedang Dipinjam", lblDipinjam, new Color(230, 126, 34)));
        statsPanel.add(createStatCard("Terlambat", lblTerlambat, new Color(192, 57, 43)));
        statsPanel.add(createStatCard("Sudah Kembali", lblKembali, new Color(39, 174, 96)));

        JPanel tablesPanel = new JPanel(new GridLayout(1, 2, 15, 0));
        tablesPanel.setBackground(COLOR_BG);

        JPanel leftPanel = new JPanel(new BorderLayout(0, 8));
        leftPanel.setBackground(COLOR_WHITE);
        leftPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 225, 230)),
            new EmptyBorder(15, 15, 15, 15)
        ));

        JLabel lblPop = new JLabel("Buku Paling Sering Dipinjam");
        lblPop.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblPop.setForeground(COLOR_PRIMARY);

        modelTerpopuler = new DefaultTableModel(new String[]{"Judul Buku", "Penulis", "Total Dipinjam"}, 0) {
            @Override public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tblTerpopuler = new JTable(modelTerpopuler);
        styleTable(tblTerpopuler);
        tblTerpopuler.getColumnModel().getColumn(0).setPreferredWidth(180);
        tblTerpopuler.getColumnModel().getColumn(1).setPreferredWidth(120);
        tblTerpopuler.getColumnModel().getColumn(2).setPreferredWidth(80);

        JScrollPane scrollPop = new JScrollPane(tblTerpopuler);
        scrollPop.setBorder(BorderFactory.createLineBorder(new Color(220, 225, 230)));

        leftPanel.add(lblPop, BorderLayout.NORTH);
        leftPanel.add(scrollPop, BorderLayout.CENTER);

        JPanel rightPanel = new JPanel(new BorderLayout(0, 8));
        rightPanel.setBackground(COLOR_WHITE);
        rightPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 225, 230)),
            new EmptyBorder(15, 15, 15, 15)
        ));

        JLabel lblRiwayat = new JLabel("Riwayat Transaksi Terbaru");
        lblRiwayat.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblRiwayat.setForeground(COLOR_PRIMARY);

        modelRiwayat = new DefaultTableModel(new String[]{"ID", "Anggota", "Judul Buku", "Tgl Pinjam", "Batas Kembali", "Status", "Denda"}, 0) {
            @Override public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tblRiwayat = new JTable(modelRiwayat);
        styleTable(tblRiwayat);
        tblRiwayat.getColumnModel().getColumn(0).setMinWidth(0);
        tblRiwayat.getColumnModel().getColumn(0).setMaxWidth(0);
        tblRiwayat.getColumnModel().getColumn(0).setPreferredWidth(0);

        JScrollPane scrollRiwayat = new JScrollPane(tblRiwayat);
        scrollRiwayat.setBorder(BorderFactory.createLineBorder(new Color(220, 225, 230)));

        JPanel riwayatBtnPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 4));
        riwayatBtnPanel.setBackground(COLOR_WHITE);

        JButton btnKembalikan = createActionButton("Kembalikan", new Color(39, 174, 96));
        JButton btnTerlambat = createActionButton("Terlambat/Denda", new Color(192, 57, 43));
        JButton btnHilang = createActionButton("Hilang", new Color(155, 89, 182));
        JButton btnHapus = createActionButton("Hapus Riwayat", new Color(127, 140, 141));

        if (LoginController.isDemo()) {
            for (JButton button : new JButton[]{btnKembalikan, btnTerlambat, btnHilang, btnHapus}) {
                button.setEnabled(false);
                button.setToolTipText("Fitur ini tidak tersedia di akun demo");
            }
        }

        riwayatBtnPanel.add(btnKembalikan);
        riwayatBtnPanel.add(btnTerlambat);
        riwayatBtnPanel.add(btnHilang);
        riwayatBtnPanel.add(btnHapus);

        JPanel riwayatTop = new JPanel(new BorderLayout(0, 5));
        riwayatTop.setBackground(COLOR_WHITE);
        riwayatTop.add(lblRiwayat, BorderLayout.NORTH);
        riwayatTop.add(riwayatBtnPanel, BorderLayout.SOUTH);

        rightPanel.add(riwayatTop, BorderLayout.NORTH);
        rightPanel.add(scrollRiwayat, BorderLayout.CENTER);

        tablesPanel.add(leftPanel);
        tablesPanel.add(rightPanel);

        btnKembalikan.addActionListener(e -> aksiKembalikan());
        btnTerlambat.addActionListener(e -> aksiTerlambat());
        btnHilang.addActionListener(e -> aksiHilang());
        btnHapus.addActionListener(e -> aksiHapusRiwayat());

        JButton btnRefresh = createActionButton("Refresh Laporan", COLOR_PRIMARY);
        btnRefresh.setPreferredSize(new Dimension(160, 34));
        btnRefresh.addActionListener(e -> loadData());

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 8));
        btnPanel.setBackground(COLOR_BG);
        btnPanel.add(btnRefresh);

        chartPanel = new ChartPanel();
        chartPanel.setPreferredSize(new Dimension(600, 260));

        JPanel topSection = new JPanel(new BorderLayout(0, 12));
        topSection.setBackground(COLOR_BG);
        topSection.add(statsPanel, BorderLayout.NORTH);
        topSection.add(chartPanel, BorderLayout.CENTER);

        JPanel innerContent = new JPanel();
        innerContent.setLayout(new BoxLayout(innerContent, BoxLayout.Y_AXIS));
        innerContent.setBackground(COLOR_BG);
        topSection.setAlignmentX(Component.LEFT_ALIGNMENT);
        tablesPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        innerContent.add(topSection);
        innerContent.add(Box.createVerticalStrut(12));
        innerContent.add(tablesPanel);

        contentPanel.add(innerContent, BorderLayout.CENTER);

        add(contentPanel, BorderLayout.CENTER);
        add(btnPanel, BorderLayout.SOUTH);
    }

    private int getSelectedRiwayatId() {
        int row = tblRiwayat.getSelectedRow();
        if (row < 0) {
            return -1;
        }
        return (int) modelRiwayat.getValueAt(row, 0);
    }

    private void aksiKembalikan() {
        if (!LoginController.ensureNotDemo(this, "Fitur kembalikan buku")) {
            return;
        }

        int idPinjam = getSelectedRiwayatId();
        if (idPinjam < 0) {
            JOptionPane.showMessageDialog(this, "Pilih transaksi yang ingin diproses!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        PeminjamanModel pinjam = peminjamanModel.getPeminjamanById(idPinjam);
        if (pinjam == null) {
            JOptionPane.showMessageDialog(this, "Data tidak ditemukan!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!"Dipinjam".equals(pinjam.getStatus())) {
            JOptionPane.showMessageDialog(this,
                "Hanya transaksi berstatus 'Dipinjam' yang bisa dikembalikan.\nStatus saat ini: " + pinjam.getStatus(),
                "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        String tanggalHariIni = PeminjamanModel.getTanggalHariIni();
        int confirm = JOptionPane.showConfirmDialog(this,
            "Konfirmasi pengembalian buku?\n\n" +
            "Anggota: " + pinjam.getNamaAnggota() + "\n" +
            "Batas kembali: " + pinjam.getTanggalKembali() + "\n" +
            "Tanggal hari ini: " + tanggalHariIni + "\n\n" +
            "Status akan diubah menjadi selesai.",
            "Konfirmasi Kembalikan", JOptionPane.YES_NO_OPTION);

        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        double denda = peminjamanModel.prosesPengembalian(idPinjam, tanggalHariIni);
        if (denda >= 0) {
            String pesan = "Buku berhasil dikembalikan.\n";
            pesan += denda > 0
                ? "Denda keterlambatan: " + PeminjamanController.formatRupiah(denda)
                : "Tepat waktu - Tidak ada denda.";
            JOptionPane.showMessageDialog(this, pesan, "Sukses", JOptionPane.INFORMATION_MESSAGE);
            loadData();
        } else {
            JOptionPane.showMessageDialog(this, "Gagal memproses pengembalian!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void aksiTerlambat() {
        if (!LoginController.ensureNotDemo(this, "Fitur terlambat / denda")) {
            return;
        }

        int idPinjam = getSelectedRiwayatId();
        if (idPinjam < 0) {
            JOptionPane.showMessageDialog(this, "Pilih transaksi yang ingin diproses!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        PeminjamanModel pinjam = peminjamanModel.getPeminjamanById(idPinjam);
        if (pinjam == null) {
            JOptionPane.showMessageDialog(this, "Data tidak ditemukan!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!"Dipinjam".equals(pinjam.getStatus())) {
            JOptionPane.showMessageDialog(this,
                "Hanya transaksi berstatus 'Dipinjam' yang bisa diproses denda.\nStatus saat ini: " + pinjam.getStatus(),
                "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        String tanggalHariIni = PeminjamanModel.getTanggalHariIni();
        double dendaPreview = PeminjamanModel.hitungDenda(pinjam.getTanggalKembali(), tanggalHariIni);

        try {
            LocalDate batas = LocalDate.parse(pinjam.getTanggalKembali());
            LocalDate hari = LocalDate.parse(tanggalHariIni);
            long hariTelat = ChronoUnit.DAYS.between(batas, hari);

            if (hariTelat <= 0) {
                JOptionPane.showMessageDialog(this,
                    "Peminjaman ini belum melewati batas.\n\n" +
                    "Anggota: " + pinjam.getNamaAnggota() + "\n" +
                    "Batas kembali: " + pinjam.getTanggalKembali() + "\n" +
                    "Hari ini: " + tanggalHariIni + "\n" +
                    "Sisa waktu: " + Math.abs(hariTelat) + " hari",
                    "Belum Terlambat", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(this,
                "Anggota terlambat " + hariTelat + " hari.\n" +
                "Total denda: " + PeminjamanController.formatRupiah(dendaPreview) + "\n\n" +
                "Proses pengembalian dengan denda?",
                "Konfirmasi Denda", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

            if (confirm != JOptionPane.YES_OPTION) {
                return;
            }

            double denda = peminjamanModel.prosesPengembalian(idPinjam, tanggalHariIni);
            if (denda >= 0) {
                JOptionPane.showMessageDialog(this,
                    "Pengembalian berhasil.\nDenda yang harus dibayar:\n" + PeminjamanController.formatRupiah(denda),
                    "Denda Terlambat", JOptionPane.INFORMATION_MESSAGE);
                loadData();
            } else {
                JOptionPane.showMessageDialog(this, "Gagal memproses transaksi.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void aksiHilang() {
        if (!LoginController.ensureNotDemo(this, "Fitur lapor buku hilang")) {
            return;
        }

        int idPinjam = getSelectedRiwayatId();
        if (idPinjam < 0) {
            JOptionPane.showMessageDialog(this, "Pilih transaksi yang ingin diproses!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        PeminjamanModel pinjam = peminjamanModel.getPeminjamanById(idPinjam);
        if (pinjam == null) {
            JOptionPane.showMessageDialog(this, "Data tidak ditemukan!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!"Dipinjam".equals(pinjam.getStatus())) {
            JOptionPane.showMessageDialog(this,
                "Hanya transaksi berstatus 'Dipinjam' yang bisa dilaporkan hilang.\nStatus saat ini: " + pinjam.getStatus(),
                "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
            "Lapor buku hilang untuk transaksi ini?\n\n" +
            "Anggota: " + pinjam.getNamaAnggota() + "\n" +
            "Stok buku tidak akan dikembalikan.\n" +
            "Denda akan dihitung sesuai jumlah buku.",
            "Konfirmasi Hilang", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        double dendaHilang = peminjamanModel.prosesLaporHilang(idPinjam);
        if (dendaHilang >= 0) {
            JOptionPane.showMessageDialog(this,
                "Transaksi berhasil ditandai hilang.\nTotal denda: " +
                PeminjamanController.formatRupiah(dendaHilang),
                "Lapor Hilang", JOptionPane.INFORMATION_MESSAGE);
            loadData();
        } else {
            JOptionPane.showMessageDialog(this, "Gagal memproses lapor hilang!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void aksiHapusRiwayat() {
        if (!LoginController.ensureNotDemo(this, "Fitur hapus riwayat transaksi")) {
            return;
        }

        int idPinjam = getSelectedRiwayatId();
        if (idPinjam < 0) {
            JOptionPane.showMessageDialog(this, "Pilih riwayat transaksi yang ingin dihapus!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        PeminjamanModel pinjam = peminjamanModel.getPeminjamanById(idPinjam);
        if (pinjam == null) {
            JOptionPane.showMessageDialog(this, "Data tidak ditemukan!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if ("Dipinjam".equals(pinjam.getStatus())) {
            JOptionPane.showMessageDialog(this,
                "Riwayat tidak bisa dihapus karena transaksi masih aktif.\nSelesaikan pengembalian terlebih dahulu.",
                "Transaksi Aktif", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
            "Hapus riwayat transaksi ini?\n\n" +
            "Anggota: " + pinjam.getNamaAnggota() + "\n" +
            "Status akhir: " + pinjam.getStatus() + "\n\n" +
            "Aksi ini hanya menghapus riwayat transaksi dari laporan.",
            "Konfirmasi Hapus Riwayat", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        if (peminjamanModel.deleteRiwayatPeminjaman(idPinjam)) {
            JOptionPane.showMessageDialog(this,
                "Riwayat transaksi berhasil dihapus.",
                "Sukses", JOptionPane.INFORMATION_MESSAGE);
            loadData();
        } else {
            JOptionPane.showMessageDialog(this,
                "Gagal menghapus riwayat transaksi.",
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void loadData() {
        loadStatistik();
        loadBukuTerpopuler();
        loadRiwayatTransaksi();
        if (chartPanel != null) {
            chartPanel.refreshData();
        }
    }

    private void loadStatistik() {
        int[] stats = peminjamanModel.getStatistik();
        lblTotalPinjam.setText(String.valueOf(stats[0]));
        lblDipinjam.setText(String.valueOf(stats[1]));
        lblTerlambat.setText(String.valueOf(stats[2]));
        lblKembali.setText(String.valueOf(stats[3]));
    }

    private void loadBukuTerpopuler() {
        modelTerpopuler.setRowCount(0);
        List<Object[]> list = bukuModel.getBukuTerpopuler();
        for (Object[] row : list) {
            modelTerpopuler.addRow(row);
        }
    }

    private void loadRiwayatTransaksi() {
        modelRiwayat.setRowCount(0);
        List<Object[]> list = peminjamanModel.getLaporanPeminjaman();
        String hariIni = PeminjamanModel.getTanggalHariIni();
        int limit = Math.min(list.size(), 30);

        for (int i = 0; i < limit; i++) {
            Object[] row = list.get(i);
            double denda = (Double) row[8];
            String status = (String) row[7];

            if ("Dipinjam".equals(status)) {
                double dendaPreview = PeminjamanModel.hitungDenda((String) row[5], hariIni);
                if (dendaPreview > 0) {
                    denda = dendaPreview;
                    status = "Terlambat!";
                }
            }

            modelRiwayat.addRow(new Object[]{
                row[0],
                row[1],
                row[2],
                row[4],
                row[5],
                status,
                PeminjamanController.formatRupiah(denda)
            });
        }
    }

    private JPanel createHeader() {
        JPanel panel = new JPanel(new BorderLayout()) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setPaint(new GradientPaint(0, 0, COLOR_PRIMARY, getWidth(), 0, new Color(40, 116, 166)));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                g2.dispose();
            }
        };
        panel.setOpaque(false);
        panel.setBorder(new EmptyBorder(15, 20, 15, 20));

        JLabel title = new JLabel("Laporan & Statistik");
        title.setFont(new Font("Segoe UI", Font.BOLD, 18));
        title.setForeground(Color.WHITE);

        JLabel subtitle = new JLabel("Pantau statistik dan riwayat transaksi perpustakaan secara real-time");
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        subtitle.setForeground(new Color(255, 255, 255, 180));

        JPanel textPanel = new JPanel(new GridLayout(2, 1, 0, 2));
        textPanel.setOpaque(false);
        textPanel.add(title);
        textPanel.add(subtitle);
        panel.add(textPanel, BorderLayout.CENTER);
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
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 12, 12);
                g2.fillRect(0, 0, 5, getHeight());
                g2.dispose();
            }
        };
        card.setOpaque(false);
        card.setBorder(new EmptyBorder(15, 20, 15, 15));

        lblValue.setFont(new Font("Segoe UI", Font.BOLD, 36));
        lblValue.setForeground(accentColor);
        lblValue.setHorizontalAlignment(SwingConstants.LEFT);

        JLabel lblTitle = new JLabel(title);
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
        table.setGridColor(new Color(225, 230, 235));
        table.setShowHorizontalLines(true);
        table.setShowVerticalLines(false);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        table.getTableHeader().setBackground(COLOR_PRIMARY);
        table.getTableHeader().setForeground(Color.WHITE);
        table.getTableHeader().setReorderingAllowed(false);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override public Component getTableCellRendererComponent(
                JTable table, Object value, boolean selected, boolean focused, int row, int column) {
                super.getTableCellRendererComponent(table, value, selected, focused, row, column);
                setBorder(new EmptyBorder(0, 8, 0, 8));
                if (!selected) {
                    String text = value != null ? value.toString() : "";
                    if ("Dipinjam".equals(text)) {
                        setForeground(new Color(52, 152, 219));
                        setFont(getFont().deriveFont(Font.BOLD));
                    } else if ("Kembali".equals(text)) {
                        setForeground(new Color(39, 174, 96));
                        setFont(getFont().deriveFont(Font.BOLD));
                    } else if ("Terlambat".equals(text) || "Terlambat!".equals(text)) {
                        setForeground(new Color(192, 57, 43));
                        setFont(getFont().deriveFont(Font.BOLD));
                    } else if ("Hilang".equals(text)) {
                        setForeground(new Color(155, 89, 182));
                        setFont(getFont().deriveFont(Font.BOLD));
                    } else {
                        setForeground(new Color(44, 62, 80));
                        setFont(getFont().deriveFont(Font.PLAIN));
                    }
                    setBackground(row % 2 == 0 ? COLOR_WHITE : new Color(248, 249, 250));
                }
                return this;
            }
        });
    }

    private JButton createActionButton(String text, Color color) {
        JButton btn = new JButton(text) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                Color bg = getModel().isEnabled()
                    ? (getModel().isRollover() ? color.darker() : color)
                    : new Color(180, 186, 194);
                g2.setColor(bg);
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
