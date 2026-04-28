package librarymanagement.view;

import librarymanagement.controller.LoginController;
import librarymanagement.model.AnggotaModel;
import librarymanagement.util.AnggotaPhotoUtil;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.awt.print.*;
import java.awt.geom.RoundRectangle2D;
import java.io.File;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.event.PopupMenuListener;
import javax.swing.event.PopupMenuEvent;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.graphics.image.LosslessFactory;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

/**
 * KartuAnggotaPanel - Panel untuk membuat dan mencetak kartu anggota
 * Desain seperti KTP (85.6mm x 53.98mm)
 */
public class KartuAnggotaPanel extends JPanel {

    private static final double KTP_WIDTH_MM = 85.6;
    private static final double KTP_HEIGHT_MM = 53.98;
    private static final int CARD_WIDTH = (int) Math.round(KTP_WIDTH_MM * 6.0);
    private static final int CARD_HEIGHT = (int) Math.round(KTP_HEIGHT_MM * 6.0);
    
    private JComboBox<AnggotaModel> cmbAnggota;
    private KartuPreviewPanel kartuPreview;
    private AnggotaModel anggotaModel;
    private AnggotaModel selectedAnggota;
    
    // Warna
    private static final Color COLOR_PRIMARY = new Color(26, 82, 118);
    private static final Color COLOR_BG      = new Color(240, 244, 248);
    private static final Color COLOR_WHITE   = Color.WHITE;
    private static final Color COLOR_SUCCESS = new Color(39, 174, 96);
    
    public KartuAnggotaPanel() {
        anggotaModel = new AnggotaModel();
        initComponents();
    }
    
    private void initComponents() {
        setLayout(new BorderLayout(15, 15));
        setBackground(COLOR_BG);
        setBorder(new EmptyBorder(20, 25, 20, 25));
        
        // HEADER
        add(createHeaderPanel(), BorderLayout.NORTH);
        
        // CENTER - Preview kartu
        JPanel centerPanel = new JPanel(new BorderLayout(0, 15));
        centerPanel.setBackground(COLOR_BG);
        
        // Toolbar - Pilih anggota (2 baris agar semua button terlihat)
        JPanel toolPanel = new JPanel();
        toolPanel.setLayout(new BoxLayout(toolPanel, BoxLayout.Y_AXIS));
        toolPanel.setBackground(COLOR_WHITE);
        toolPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 225, 230)),
            new EmptyBorder(12, 15, 12, 15)
        ));
        
        // Baris 1: Label + ComboBox
        JPanel row1 = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        row1.setBackground(COLOR_WHITE);
        row1.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel lblPilih = new JLabel("👤 Pilih Anggota:");
        lblPilih.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblPilih.setForeground(COLOR_PRIMARY);
        
        cmbAnggota = new JComboBox<>();
        cmbAnggota.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        cmbAnggota.setPreferredSize(new Dimension(350, 34));
        loadAnggotaCombo();
        
        row1.add(lblPilih);
        row1.add(cmbAnggota);
        
        // Baris 2: Semua button
        JPanel row2 = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        row2.setBackground(COLOR_WHITE);
        row2.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JButton btnPreview = createStyledButton("👁️  Preview Kartu", COLOR_PRIMARY);
        JButton btnCetak   = createStyledButton("🖨️  Cetak Kartu", COLOR_SUCCESS);
        JButton btnSimpan  = createStyledButton("💾  Simpan Gambar", new Color(52, 152, 219));
        JButton btnRefresh = createStyledButton("⟳  Refresh Data", new Color(230, 126, 34));
        
        row2.add(btnPreview);
        row2.add(btnCetak);
        row2.add(btnSimpan);
        row2.add(btnRefresh);
        
        // Disable tombol untuk akun demo (kecuali Preview dan Refresh)
        if (LoginController.isDemo()) {
            btnCetak.setEnabled(false);
            btnCetak.setToolTipText("🔒 Fitur ini dinonaktifkan dalam mode demo");
            btnSimpan.setEnabled(false);
            btnSimpan.setToolTipText("🔒 Fitur ini dinonaktifkan dalam mode demo");
        }
        
        toolPanel.add(row1);
        toolPanel.add(Box.createVerticalStrut(10));
        toolPanel.add(row2);
        
        // Preview area
        kartuPreview = new KartuPreviewPanel();
        JPanel previewWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 20));
        previewWrapper.setBackground(COLOR_BG);
        previewWrapper.add(kartuPreview);
        
        JScrollPane scrollPreview = new JScrollPane(previewWrapper);
        scrollPreview.setBorder(null);
        scrollPreview.getVerticalScrollBar().setUnitIncrement(16);
        
        centerPanel.add(toolPanel, BorderLayout.NORTH);
        centerPanel.add(scrollPreview, BorderLayout.CENTER);
        
        add(centerPanel, BorderLayout.CENTER);
        
        // EVENTS
        btnPreview.addActionListener(e -> previewKartu());
        btnCetak.addActionListener(e -> cetakKartu());
        btnSimpan.addActionListener(e -> simpanKartu());
        btnRefresh.addActionListener(e -> {
            loadAnggotaCombo();
            JOptionPane.showMessageDialog(this, "Data anggota berhasil di-refresh!", 
                "Info", JOptionPane.INFORMATION_MESSAGE);
        });
        
        // Auto-refresh combo saat dibuka
        cmbAnggota.addPopupMenuListener(new PopupMenuListener() {
            @Override public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
                loadAnggotaCombo();
            }
            @Override public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {}
            @Override public void popupMenuCanceled(PopupMenuEvent e) {}
        });
    }
    
    private void loadAnggotaCombo() {
        Object selectedBefore = cmbAnggota.getSelectedItem();
        cmbAnggota.removeAllItems();
        List<AnggotaModel> list = anggotaModel.getAllAnggota();
        for (AnggotaModel a : list) {
            cmbAnggota.addItem(a);
        }
        // Restore previous selection if possible
        if (selectedBefore != null) {
            cmbAnggota.setSelectedItem(selectedBefore);
        }
    }
    
    private void previewKartu() {
        Object sel = cmbAnggota.getSelectedItem();
        if (sel instanceof AnggotaModel) {
            selectedAnggota = anggotaModel.getAnggotaById(((AnggotaModel) sel).getIdAnggota());
            kartuPreview.setAnggota(selectedAnggota);
        } else {
            JOptionPane.showMessageDialog(this, "Pilih anggota terlebih dahulu!", 
                "Peringatan", JOptionPane.WARNING_MESSAGE);
        }
    }
    
    private void cetakKartu() {
        if (selectedAnggota == null) {
            JOptionPane.showMessageDialog(this, "Preview kartu terlebih dahulu!", 
                "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        PrinterJob job = PrinterJob.getPrinterJob();
        PageFormat ktpPageFormat = job.validatePage(createKtpPageFormat());
        job.setPrintable((graphics, pageFormat, pageIndex) -> {
            if (pageIndex > 0) return Printable.NO_SUCH_PAGE;
            
            Graphics2D g2 = (Graphics2D) graphics;
            g2.translate(pageFormat.getImageableX(), pageFormat.getImageableY());
            
            // Scale kartu ke halaman
            double scaleX = pageFormat.getImageableWidth() / CARD_WIDTH;
            double scaleY = pageFormat.getImageableHeight() / CARD_HEIGHT;
            double scale = Math.min(scaleX, scaleY);
            g2.scale(scale, scale);
            
            kartuPreview.drawKartu(g2, CARD_WIDTH, CARD_HEIGHT);
            return Printable.PAGE_EXISTS;
        }, ktpPageFormat);
        
        if (!job.printDialog()) {
            return;
        }

        if (isPdfPrinter(job)) {
            exportExactSizePdf();
            return;
        }

        try {
            job.print();
            JOptionPane.showMessageDialog(this, "Kartu berhasil dicetak!", 
                "Sukses", JOptionPane.INFORMATION_MESSAGE);
        } catch (PrinterException e) {
            JOptionPane.showMessageDialog(this, "Gagal mencetak: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void simpanKartu() {
        if (selectedAnggota == null) {
            JOptionPane.showMessageDialog(this, "Preview kartu terlebih dahulu!", 
                "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Simpan Kartu Anggota");
        chooser.setAcceptAllFileFilterUsed(false);
        FileNameExtensionFilter pdfFilter = new FileNameExtensionFilter("PDF File", "pdf");
        FileNameExtensionFilter pngFilter = new FileNameExtensionFilter("PNG Image", "png");
        chooser.addChoosableFileFilter(pdfFilter);
        chooser.addChoosableFileFilter(pngFilter);
        chooser.setFileFilter(pdfFilter);
        chooser.setSelectedFile(new File("Kartu_" + selectedAnggota.getNama().replace(" ", "_") + ".pdf"));
        int result = chooser.showSaveDialog(this);
        
        if (result == JFileChooser.APPROVE_OPTION) {
            try {
                BufferedImage img = renderKartuImage();
                File targetFile = ensureSelectedExtension(chooser.getSelectedFile(), chooser.getFileFilter());

                if (targetFile.getName().toLowerCase().endsWith(".pdf")) {
                    saveAsPdf(targetFile, img);
                } else {
                    ImageIO.write(img, "png", targetFile);
                }

                JOptionPane.showMessageDialog(this, 
                    "Kartu berhasil disimpan!\n" + targetFile.getAbsolutePath(), 
                    "Sukses", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Gagal menyimpan: " + e.getMessage(), 
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    // ============================================================
    // HELPER METHODS
    // ============================================================
    private JPanel createHeaderPanel() {
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
        
        JLabel t = new JLabel("🪪 Kartu Anggota Perpustakaan");
        t.setFont(new Font("Segoe UI", Font.BOLD, 18));
        t.setForeground(Color.WHITE);
        
        JLabel s = new JLabel("Buat dan cetak kartu identitas anggota perpustakaan");
        s.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        s.setForeground(new Color(255, 255, 255, 180));
        
        JPanel tp = new JPanel(new GridLayout(2, 1, 0, 2));
        tp.setOpaque(false);
        tp.add(t);
        tp.add(s);
        panel.add(tp, BorderLayout.CENTER);
        return panel;
    }
    
    private JButton createStyledButton(String text, Color color) {
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
        btn.setPreferredSize(new Dimension(140, 32));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setFocusPainted(false);
        return btn;
    }

    private PageFormat createKtpPageFormat() {
        double widthPoints = mmToPoints(KTP_WIDTH_MM);
        double heightPoints = mmToPoints(KTP_HEIGHT_MM);

        Paper paper = new Paper();
        paper.setSize(widthPoints, heightPoints);
        paper.setImageableArea(0, 0, widthPoints, heightPoints);

        PageFormat pageFormat = new PageFormat();
        pageFormat.setPaper(paper);
        pageFormat.setOrientation(PageFormat.PORTRAIT);
        return pageFormat;
    }

    private double mmToPoints(double mm) {
        return (mm / 25.4) * 72.0;
    }

    private BufferedImage renderKartuImage() {
        BufferedImage img = new BufferedImage(CARD_WIDTH, CARD_HEIGHT, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = img.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        kartuPreview.drawKartu(g2, CARD_WIDTH, CARD_HEIGHT);
        g2.dispose();
        return img;
    }

    private File ensureSelectedExtension(File file, javax.swing.filechooser.FileFilter filter) {
        String name = file.getName().toLowerCase();
        if (filter instanceof FileNameExtensionFilter) {
            String[] extensions = ((FileNameExtensionFilter) filter).getExtensions();
            if (extensions.length > 0 && !name.endsWith("." + extensions[0].toLowerCase())) {
                return new File(file.getParentFile(), file.getName() + "." + extensions[0]);
            }
        }
        return file;
    }

    private void saveAsPdf(File file, BufferedImage image) throws Exception {
        float widthPoints = (float) mmToPoints(KTP_WIDTH_MM);
        float heightPoints = (float) mmToPoints(KTP_HEIGHT_MM);

        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage(new PDRectangle(widthPoints, heightPoints));
            document.addPage(page);

            PDImageXObject pdImage = LosslessFactory.createFromImage(document, image);
            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                contentStream.drawImage(pdImage, 0, 0, widthPoints, heightPoints);
            }

            document.save(file);
        }
    }

    private boolean isPdfPrinter(PrinterJob job) {
        if (job == null || job.getPrintService() == null) {
            return false;
        }

        String printerName = job.getPrintService().getName().toLowerCase(java.util.Locale.ROOT);
        return printerName.contains("pdf");
    }

    private void exportExactSizePdf() {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Simpan Kartu Anggota Sebagai PDF");
        chooser.setAcceptAllFileFilterUsed(false);

        FileNameExtensionFilter pdfFilter = new FileNameExtensionFilter("PDF File", "pdf");
        chooser.addChoosableFileFilter(pdfFilter);
        chooser.setFileFilter(pdfFilter);
        chooser.setSelectedFile(new File("Kartu_" + selectedAnggota.getNama().replace(" ", "_") + ".pdf"));

        int result = chooser.showSaveDialog(this);
        if (result != JFileChooser.APPROVE_OPTION) {
            return;
        }

        try {
            BufferedImage image = renderKartuImage();
            File targetFile = ensureSelectedExtension(chooser.getSelectedFile(), chooser.getFileFilter());
            saveAsPdf(targetFile, image);

            JOptionPane.showMessageDialog(this,
                "Printer PDF terdeteksi.\nKartu disimpan sebagai PDF ukuran KTP tanpa area putih tambahan.\n"
                    + targetFile.getAbsolutePath(),
                "Sukses", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal menyimpan PDF: " + e.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    // ============================================================
    // INNER CLASS: Preview Kartu (ukuran KTP)
    // ============================================================
    private class KartuPreviewPanel extends JPanel {
        
        private AnggotaModel anggota;
        private BufferedImage fotoPdfPreview;
        
        public KartuPreviewPanel() {
            setPreferredSize(new Dimension(CARD_WIDTH, CARD_HEIGHT));
            setBackground(COLOR_BG);
        }
        
        public void setAnggota(AnggotaModel anggota) {
            this.anggota = anggota;
            this.fotoPdfPreview = anggota != null
                ? AnggotaPhotoUtil.renderPdfFirstPage(anggota.getFotoPdfPath(), 95, 120)
                : null;
            repaint();
        }
        
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            
            if (anggota == null) {
                // Placeholder
                g2.setColor(new Color(220, 225, 230));
                g2.fillRoundRect(0, 0, CARD_WIDTH, CARD_HEIGHT, 12, 12);
                g2.setColor(new Color(150, 160, 170));
                g2.setFont(new Font("Segoe UI", Font.PLAIN, 16));
                g2.drawString("Pilih anggota dan klik Preview untuk melihat kartu", 65, CARD_HEIGHT / 2 + 10);
            } else {
                drawKartu(g2, CARD_WIDTH, CARD_HEIGHT);
            }
            
            g2.dispose();
        }
        
        public void drawKartu(Graphics2D g2, int w, int h) {
            if (anggota == null) return;
            
            // Background kartu - gradient biru gelap
            GradientPaint bgGrad = new GradientPaint(0, 0, new Color(15, 32, 60), w, h, new Color(26, 82, 118));
            g2.setPaint(bgGrad);
            g2.fillRoundRect(0, 0, w, h, 12, 12);
            
            // Border
            g2.setColor(new Color(52, 152, 219, 100));
            g2.setStroke(new BasicStroke(2));
            g2.drawRoundRect(1, 1, w - 3, h - 3, 12, 12);
            
            // === HEADER ===
            // Background header strip
            g2.setColor(new Color(52, 152, 219, 60));
            g2.fillRect(0, 0, w, 65);
            
            // Logo icon area
            g2.setColor(new Color(52, 152, 219));
            g2.fillOval(20, 12, 42, 42);
            g2.setColor(Color.WHITE);
            g2.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 22));
            g2.drawString("📚", 26, 40);
            
            // Nama perpustakaan
            g2.setColor(Color.WHITE);
            g2.setFont(new Font("Segoe UI", Font.BOLD, 18));
            g2.drawString("PERPUSTAKAAN DIGITAL", 75, 32);
            
            g2.setFont(new Font("Segoe UI", Font.PLAIN, 10));
            g2.setColor(new Color(180, 210, 240));
            g2.drawString("Sistem Manajemen Perpustakaan — Kartu Identitas Anggota", 75, 50);
            
            // === GARIS PEMISAH ===
            GradientPaint lineGrad = new GradientPaint(0, 0, new Color(52, 152, 219), w, 0, new Color(46, 204, 113));
            g2.setPaint(lineGrad);
            g2.fillRect(20, 68, w - 40, 3);
            
            // === INFORMASI ANGGOTA ===
            int infoX = 140;
            int infoY = 100;
            int lineH = 28;
            
            // Labels
            g2.setFont(new Font("Segoe UI", Font.PLAIN, 11));
            g2.setColor(new Color(150, 180, 210));
            g2.drawString("ID Anggota", infoX, infoY);
            g2.drawString("Nama Lengkap", infoX, infoY + lineH);
            g2.drawString("Alamat", infoX, infoY + lineH * 2);
            g2.drawString("No. Telepon", infoX, infoY + lineH * 3);
            g2.drawString("Status", infoX, infoY + lineH * 4);
            
            // Values
            g2.setFont(new Font("Segoe UI", Font.BOLD, 13));
            g2.setColor(Color.WHITE);
            int valX = infoX + 120;
            g2.drawString(":  A" + String.format("%04d", anggota.getIdAnggota()), valX, infoY);
            g2.drawString(":  " + anggota.getNama(), valX, infoY + lineH);
            
            // Alamat - potong jika terlalu panjang
            String alamat = anggota.getAlamat();
            if (alamat.length() > 30) alamat = alamat.substring(0, 30) + "...";
            g2.drawString(":  " + alamat, valX, infoY + lineH * 2);
            
            g2.drawString(":  " + anggota.getNoHp(), valX, infoY + lineH * 3);
            
            // Status badge
            String status = anggota.getStatus();
            Color statusColor = "Aktif".equals(status) ? new Color(46, 204, 113) : new Color(231, 76, 60);
            g2.drawString(":  ", valX, infoY + lineH * 4);
            g2.setColor(statusColor);
            g2.fillRoundRect(valX + 18, infoY + lineH * 4 - 13, 55, 18, 6, 6);
            g2.setColor(Color.WHITE);
            g2.setFont(new Font("Segoe UI", Font.BOLD, 10));
            g2.drawString(status, valX + 25, infoY + lineH * 4);
            
            // === FOTO PLACEHOLDER (kiri) ===
            int fotoX = 25;
            int fotoY = 90;
            int fotoW = 95;
            int fotoH = 120;
            
            g2.setColor(new Color(40, 70, 110));
            g2.fillRoundRect(fotoX, fotoY, fotoW, fotoH, 8, 8);
            g2.setColor(new Color(100, 140, 180));
            g2.setStroke(new BasicStroke(1.5f));
            g2.drawRoundRect(fotoX, fotoY, fotoW, fotoH, 8, 8);

            if (fotoPdfPreview != null) {
                Shape oldClip = g2.getClip();
                g2.setClip(new RoundRectangle2D.Float(fotoX + 3, fotoY + 3, fotoW - 6, fotoH - 6, 8, 8));
                g2.drawImage(fotoPdfPreview, fotoX + 3, fotoY + 3, fotoW - 6, fotoH - 6, null);
                g2.setClip(oldClip);
            } else {
                g2.setColor(new Color(80, 120, 160));
                g2.fillOval(fotoX + 30, fotoY + 15, 35, 35);
                g2.fillRoundRect(fotoX + 18, fotoY + 55, 60, 45, 20, 20);

                g2.setColor(new Color(150, 180, 210));
                g2.setFont(new Font("Segoe UI", Font.PLAIN, 9));
                g2.drawString("TANPA FOTO", fotoX + 17, fotoY + 115);
            }
            
            // === FOOTER ===
            // Garis pemisah bawah
            g2.setColor(new Color(52, 152, 219, 80));
            g2.fillRect(20, h - 75, w - 40, 1);
            
            // Tanda tangan area (kanan bawah)
            g2.setColor(new Color(150, 180, 210));
            g2.setFont(new Font("Segoe UI", Font.PLAIN, 10));
            g2.drawString("Kepala Perpustakaan", w - 165, h - 55);
            
            // Garis tanda tangan
            g2.setColor(new Color(200, 220, 240));
            g2.setStroke(new BasicStroke(1));
            g2.drawLine(w - 185, h - 25, w - 30, h - 25);
            
            g2.setColor(new Color(150, 180, 210));
            g2.setFont(new Font("Segoe UI", Font.ITALIC, 9));
            g2.drawString("NIP. _______________", w - 170, h - 12);
            
            // Tanggal terbit (kiri bawah)
            g2.setColor(new Color(120, 150, 180));
            g2.setFont(new Font("Segoe UI", Font.PLAIN, 9));
            String tglTerbit = "Diterbitkan: " + java.time.LocalDate.now().format(
                java.time.format.DateTimeFormatter.ofPattern("dd MMMM yyyy", new java.util.Locale("id", "ID")));
            g2.drawString(tglTerbit, 25, h - 12);
        }
    }
}
