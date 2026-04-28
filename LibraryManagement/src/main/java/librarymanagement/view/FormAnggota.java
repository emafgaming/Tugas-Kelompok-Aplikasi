package librarymanagement.view;

import librarymanagement.controller.AnggotaController;
import librarymanagement.model.AnggotaModel;
import librarymanagement.util.AnggotaPhotoUtil;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * FormAnggota - View untuk manajemen anggota perpustakaan.
 */
public class FormAnggota extends JPanel {

    private JTextField txtNama;
    private JTextField txtAlamat;
    private JTextField txtNoHp;
    private JTextField txtSearch;
    private JComboBox<String> cmbStatus;
    private JComboBox<String> cmbFilterStatus;
    private JButton btnTambah;
    private JButton btnUpdate;
    private JButton btnHapus;
    private JButton btnBersihkan;
    private JButton btnPilihFoto;
    private JButton btnBukaFoto;
    private JTable tblAnggota;
    private DefaultTableModel tableModel;
    private AnggotaController controller;
    private FotoPreviewPanel fotoPreviewPanel;
    private JLabel lblFotoInfo;

    private int selectedId = 0;
    private File selectedFotoFile;
    private String currentFotoPdfPath;

    private static final Color COLOR_PRIMARY = new Color(26, 82, 118);
    private static final Color COLOR_SUCCESS = new Color(39, 174, 96);
    private static final Color COLOR_DANGER  = new Color(192, 57, 43);
    private static final Color COLOR_WARNING = new Color(211, 84, 0);
    private static final Color COLOR_BG      = new Color(240, 244, 248);
    private static final Color COLOR_WHITE   = Color.WHITE;

    public FormAnggota() {
        initComponents();
        controller = new AnggotaController(this);
    }

    private void initComponents() {
        setLayout(new BorderLayout(15, 15));
        setBackground(COLOR_BG);
        setBorder(new EmptyBorder(20, 25, 20, 25));

        add(createHeaderPanel("Manajemen Anggota", "Kelola data anggota beserta foto PDF 3x4"), BorderLayout.NORTH);

        JPanel leftPanel = new JPanel() {
            @Override
            public Dimension getPreferredSize() {
                Dimension size = super.getPreferredSize();
                return new Dimension(320, size.height);
            }
        };
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBackground(COLOR_WHITE);
        leftPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 225, 230)),
            new EmptyBorder(18, 18, 18, 18)
        ));

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
        leftPanel.add(Box.createVerticalStrut(12));

        leftPanel.add(createLabel("Foto Anggota (PDF, maks 2 MB)"));
        fotoPreviewPanel = new FotoPreviewPanel();
        fotoPreviewPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        leftPanel.add(fotoPreviewPanel);
        leftPanel.add(Box.createVerticalStrut(6));

        lblFotoInfo = new JLabel();
        lblFotoInfo.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        lblFotoInfo.setForeground(new Color(95, 110, 125));
        lblFotoInfo.setAlignmentX(Component.LEFT_ALIGNMENT);
        leftPanel.add(lblFotoInfo);
        leftPanel.add(Box.createVerticalStrut(8));

        JPanel fotoBtnPanel = new JPanel(new GridLayout(1, 2, 8, 0));
        fotoBtnPanel.setOpaque(false);
        fotoBtnPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 34));
        fotoBtnPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        btnPilihFoto = createBtn("Pilih PDF", COLOR_PRIMARY);
        btnBukaFoto = createBtn("Buka PDF", new Color(52, 152, 219));
        fotoBtnPanel.add(btnPilihFoto);
        fotoBtnPanel.add(btnBukaFoto);
        leftPanel.add(fotoBtnPanel);
        leftPanel.add(Box.createVerticalStrut(18));

        btnTambah    = createBtn("Tambah", COLOR_SUCCESS);
        btnUpdate    = createBtn("Update", COLOR_WARNING);
        btnHapus     = createBtn("Hapus", COLOR_DANGER);
        btnBersihkan = createBtn("Bersihkan", COLOR_PRIMARY);

        for (JButton button : new JButton[]{btnTambah, btnUpdate, btnHapus, btnBersihkan}) {
            button.setAlignmentX(Component.LEFT_ALIGNMENT);
            leftPanel.add(button);
            leftPanel.add(Box.createVerticalStrut(6));
        }
        leftPanel.add(Box.createVerticalGlue());

        JLabel hint = new JLabel("<html><font color='gray' size='3'>" +
            "Klik baris tabel untuk memuat detail anggota,<br>termasuk preview foto PDF jika tersedia.</font></html>");
        hint.setAlignmentX(Component.LEFT_ALIGNMENT);
        leftPanel.add(hint);

        JPanel rightPanel = new JPanel(new BorderLayout(0, 10));
        rightPanel.setBackground(COLOR_WHITE);
        rightPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 225, 230)),
            new EmptyBorder(15, 15, 15, 15)
        ));

        JPanel toolbarPanel = new JPanel(new BorderLayout(10, 0));
        toolbarPanel.setBackground(COLOR_WHITE);

        JPanel searchPanel = new JPanel(new BorderLayout(5, 0));
        searchPanel.setBackground(COLOR_WHITE);
        searchPanel.add(new JLabel("Search "), BorderLayout.WEST);
        txtSearch = new JTextField();
        txtSearch.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(189, 195, 199)),
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

        String[] cols = {"No", "ID Asli", "Nama", "Alamat", "No. HP", "Status", "Foto PDF"};
        tableModel = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tblAnggota = new JTable(tableModel);
        styleTable(tblAnggota);

        tblAnggota.getColumnModel().getColumn(1).setMinWidth(0);
        tblAnggota.getColumnModel().getColumn(1).setMaxWidth(0);
        tblAnggota.getColumnModel().getColumn(1).setPreferredWidth(0);

        tblAnggota.getColumnModel().getColumn(0).setPreferredWidth(40);
        tblAnggota.getColumnModel().getColumn(2).setPreferredWidth(150);
        tblAnggota.getColumnModel().getColumn(3).setPreferredWidth(220);
        tblAnggota.getColumnModel().getColumn(4).setPreferredWidth(110);
        tblAnggota.getColumnModel().getColumn(5).setPreferredWidth(90);
        tblAnggota.getColumnModel().getColumn(6).setPreferredWidth(90);

        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(tableModel);
        tblAnggota.setRowSorter(sorter);

        JScrollPane scrollPane = new JScrollPane(tblAnggota);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(220, 225, 230)));

        JLabel lblCount = new JLabel("Total: 0 anggota");
        lblCount.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lblCount.setForeground(new Color(127, 140, 141));

        rightPanel.add(toolbarPanel, BorderLayout.NORTH);
        rightPanel.add(scrollPane, BorderLayout.CENTER);
        rightPanel.add(lblCount, BorderLayout.SOUTH);

        JScrollPane leftScrollPane = new JScrollPane(leftPanel);
        leftScrollPane.setBorder(null);
        leftScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        leftScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        leftScrollPane.getVerticalScrollBar().setUnitIncrement(16);
        leftScrollPane.getViewport().setBackground(COLOR_BG);
        leftScrollPane.setPreferredSize(new Dimension(340, 0));

        JPanel center = new JPanel(new BorderLayout(15, 0));
        center.setBackground(COLOR_BG);
        center.add(leftScrollPane, BorderLayout.WEST);
        center.add(rightPanel, BorderLayout.CENTER);
        add(center, BorderLayout.CENTER);

        btnTambah.addActionListener(e -> {
            if (controller != null) controller.tambah();
        });
        btnUpdate.addActionListener(e -> {
            if (controller != null) controller.update(selectedId);
        });
        btnHapus.addActionListener(e -> {
            if (controller != null) controller.hapus(selectedId);
        });
        btnBersihkan.addActionListener(e -> {
            clearForm();
            if (controller != null) controller.loadData();
        });
        btnPilihFoto.addActionListener(e -> {
            if (controller != null) controller.pilihFotoPdf();
        });
        btnBukaFoto.addActionListener(e -> {
            if (controller != null) controller.bukaFotoPdf();
        });

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
                    int modelRow = tblAnggota.convertRowIndexToModel(viewRow);
                    selectedId = (int) tableModel.getValueAt(modelRow, 1);
                    if (controller != null) {
                        controller.pilihAnggota(selectedId);
                    }
                    lblCount.setText("Total: " + tableModel.getRowCount() + " anggota | Dipilih: " +
                        tableModel.getValueAt(modelRow, 2));
                }
            }
        });

        tableModel.addTableModelListener(te -> lblCount.setText("Total: " + tableModel.getRowCount() + " anggota"));

        clearForm();
    }

    private JPanel createHeaderPanel(String title, String subtitle) {
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
        JLabel t = new JLabel(title);
        t.setFont(new Font("Segoe UI", Font.BOLD, 18));
        t.setForeground(Color.WHITE);
        JLabel s = new JLabel(subtitle);
        s.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        s.setForeground(new Color(255, 255, 255, 180));
        JPanel tp = new JPanel(new GridLayout(2, 1, 0, 2));
        tp.setOpaque(false);
        tp.add(t);
        tp.add(s);
        panel.add(tp, BorderLayout.CENTER);
        return panel;
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.BOLD, 11));
        label.setForeground(new Color(44, 62, 80));
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        return label;
    }

    private JTextField createField() {
        JTextField textField = new JTextField();
        textField.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        textField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(189, 195, 199)),
            new EmptyBorder(5, 8, 5, 8)
        ));
        textField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 32));
        textField.setAlignmentX(Component.LEFT_ALIGNMENT);
        return textField;
    }

    private JSeparator createSep() {
        JSeparator separator = new JSeparator();
        separator.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
        separator.setAlignmentX(Component.LEFT_ALIGNMENT);
        return separator;
    }

    private JButton createBtn(String text, Color color) {
        JButton btn = new JButton(text) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getModel().isEnabled()
                    ? (getModel().isRollover() ? color.darker() : color)
                    : new Color(180, 186, 194));
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
                JTable t, Object val, boolean selected, boolean focused, int row, int col) {
                super.getTableCellRendererComponent(t, val, selected, focused, row, col);
                setBorder(new EmptyBorder(0, 8, 0, 8));
                if (!selected) {
                    String value = val != null ? val.toString() : "";
                    if (col == 5) {
                        setForeground("Aktif".equals(value) ? new Color(39, 174, 96) : new Color(192, 57, 43));
                    } else if (col == 6) {
                        setForeground("Ada".equals(value) ? new Color(52, 152, 219) : new Color(127, 140, 141));
                    } else {
                        setForeground(new Color(44, 62, 80));
                    }
                    setBackground(row % 2 == 0 ? COLOR_WHITE : new Color(248, 249, 250));
                }
                return this;
            }
        });
    }

    public File chooseFotoPdf() {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Pilih Foto Anggota (PDF)");
        chooser.setFileFilter(new FileNameExtensionFilter("PDF Files", "pdf"));

        if (selectedFotoFile != null && selectedFotoFile.getParentFile() != null) {
            chooser.setCurrentDirectory(selectedFotoFile.getParentFile());
        } else {
            File currentFile = AnggotaPhotoUtil.resolvePdfFile(currentFotoPdfPath);
            if (currentFile != null && currentFile.getParentFile() != null) {
                chooser.setCurrentDirectory(currentFile.getParentFile());
            }
        }

        int result = chooser.showOpenDialog(this);
        return result == JFileChooser.APPROVE_OPTION ? chooser.getSelectedFile() : null;
    }

    public void displayAnggota(AnggotaModel anggota) {
        txtNama.setText(anggota.getNama());
        txtAlamat.setText(anggota.getAlamat());
        txtNoHp.setText(anggota.getNoHp());
        cmbStatus.setSelectedItem(anggota.getStatus());

        selectedFotoFile = null;
        currentFotoPdfPath = anggota.getFotoPdfPath();
        selectedId = anggota.getIdAnggota();
        btnTambah.setEnabled(false);
        refreshFotoPreview();
    }

    public void setSelectedFotoFile(File file) {
        this.selectedFotoFile = file;
        refreshFotoPreview();
    }

    public void setStoredFotoPdfPath(String fotoPdfPath) {
        this.currentFotoPdfPath = fotoPdfPath;
        this.selectedFotoFile = null;
        refreshFotoPreview();
    }

    private void refreshFotoPreview() {
        String source = getFotoPdfSource();
        BufferedImage previewImage = AnggotaPhotoUtil.renderPdfFirstPage(source, 120, 150);
        fotoPreviewPanel.setPreviewImage(previewImage);
        updateFotoInfo();

        File resolved = AnggotaPhotoUtil.resolvePdfFile(source);
        btnBukaFoto.setEnabled(resolved != null && resolved.exists());
    }

    private void updateFotoInfo() {
        if (selectedFotoFile != null) {
            lblFotoInfo.setText("<html>File baru: <b>" + selectedFotoFile.getName() + "</b><br>" +
                "Ukuran: " + formatFileSize(selectedFotoFile.length()) + " | Belum disimpan</html>");
            return;
        }

        if (currentFotoPdfPath != null && !currentFotoPdfPath.trim().isEmpty()) {
            lblFotoInfo.setText("<html>Tersimpan: <b>" + AnggotaPhotoUtil.getDisplayName(currentFotoPdfPath) + "</b><br>" +
                "Path DB: " + currentFotoPdfPath + "</html>");
            return;
        }

        lblFotoInfo.setText("<html>Belum ada foto anggota.<br>Format wajib PDF, ukuran maksimal 2 MB.</html>");
    }

    private String formatFileSize(long size) {
        if (size >= 1024 * 1024) {
            return String.format("%.2f MB", size / (1024.0 * 1024.0));
        }
        if (size >= 1024) {
            return String.format("%.1f KB", size / 1024.0);
        }
        return size + " B";
    }

    public String getFotoPdfSource() {
        return selectedFotoFile != null ? selectedFotoFile.getAbsolutePath() : currentFotoPdfPath;
    }

    public String getNama() {
        return txtNama.getText();
    }

    public String getAlamat() {
        return txtAlamat.getText();
    }

    public String getNoHp() {
        return txtNoHp.getText();
    }

    public String getStatus() {
        return (String) cmbStatus.getSelectedItem();
    }

    public File getSelectedFotoFile() {
        return selectedFotoFile;
    }

    public String getCurrentFotoPdfPath() {
        return currentFotoPdfPath;
    }

    public DefaultTableModel getTableModel() {
        return tableModel;
    }

    public void clearForm() {
        txtNama.setText("");
        txtAlamat.setText("");
        txtNoHp.setText("");
        txtSearch.setText("");
        cmbStatus.setSelectedIndex(0);

        selectedId = 0;
        selectedFotoFile = null;
        currentFotoPdfPath = null;

        fotoPreviewPanel.setPreviewImage(null);
        updateFotoInfo();
        btnBukaFoto.setEnabled(false);

        tblAnggota.clearSelection();
        btnTambah.setEnabled(true);
    }

    private static class FotoPreviewPanel extends JPanel {
        private BufferedImage previewImage;

        FotoPreviewPanel() {
            setPreferredSize(new Dimension(120, 150));
            setMaximumSize(new Dimension(120, 150));
            setMinimumSize(new Dimension(120, 150));
            setOpaque(false);
        }

        void setPreviewImage(BufferedImage previewImage) {
            this.previewImage = previewImage;
            repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            g2.setColor(new Color(232, 237, 242));
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);
            g2.setColor(new Color(200, 208, 216));
            g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 12, 12);

            if (previewImage != null) {
                g2.drawImage(previewImage, 6, 6, getWidth() - 12, getHeight() - 12, null);
            } else {
                g2.setColor(new Color(180, 188, 197));
                g2.fillOval(38, 24, 44, 44);
                g2.fillRoundRect(28, 72, 64, 40, 18, 18);
                g2.setFont(new Font("Segoe UI", Font.PLAIN, 11));
                g2.setColor(new Color(110, 122, 135));
                g2.drawString("Preview PDF", 22, 132);
            }

            g2.dispose();
        }
    }
}
