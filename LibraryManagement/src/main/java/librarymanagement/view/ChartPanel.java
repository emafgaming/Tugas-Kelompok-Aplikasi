package librarymanagement.view;

import librarymanagement.model.PeminjamanModel;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.util.List;

/**
 * ChartPanel - Panel grafik bar chart untuk monitoring peminjaman
 * Menggunakan Java2D Graphics, tanpa library eksternal
 */
public class ChartPanel extends JPanel {
    
    private int[] dataDipinjam  = {};
    private int[] dataKembali   = {};
    private int[] dataTerlambat = {};
    private int[] dataHilang    = {};
    private String[] labels     = {};
    
    private static final Color COLOR_DIPINJAM  = new Color(52, 152, 219);   // Blue
    private static final Color COLOR_KEMBALI   = new Color(46, 204, 113);   // Green
    private static final Color COLOR_TERLAMBAT = new Color(231, 76, 60);    // Red
    private static final Color COLOR_HILANG    = new Color(155, 89, 182);   // Purple
    private static final Color COLOR_BG        = Color.WHITE;
    private static final Color COLOR_GRID      = new Color(230, 235, 240);
    private static final Color COLOR_TEXT      = new Color(44, 62, 80);
    
    private PeminjamanModel peminjamanModel;
    
    public ChartPanel() {
        this.peminjamanModel = new PeminjamanModel();
        setBackground(COLOR_BG);
        setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 225, 230)),
            new EmptyBorder(15, 15, 15, 15)
        ));
        setPreferredSize(new Dimension(600, 300));
    }
    
    /**
     * Refresh data dari database
     */
    public void refreshData() {
        List<Object[]> stats = peminjamanModel.getStatistikBulanan();
        
        if (stats.isEmpty()) {
            // Data kosong - tampilkan dari statistik umum
            int[] statUmum = peminjamanModel.getStatistik();
            labels = new String[]{"Statistik"};
            dataDipinjam  = new int[]{statUmum[1]};
            dataKembali   = new int[]{statUmum[3]};
            dataTerlambat = new int[]{statUmum[2]};
            dataHilang    = new int[]{0};
        } else {
            int size = stats.size();
            labels        = new String[size];
            dataDipinjam  = new int[size];
            dataKembali   = new int[size];
            dataTerlambat = new int[size];
            dataHilang    = new int[size];
            
            for (int i = 0; i < size; i++) {
                Object[] row = stats.get(i);
                labels[i]        = (String) row[0]; // bulan (yyyy-MM)
                dataDipinjam[i]  = (int) row[2];
                dataKembali[i]   = (int) row[3];
                dataTerlambat[i] = (int) row[4];
                dataHilang[i]    = (int) row[5];
            }
        }
        
        repaint();
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        
        int w = getWidth();
        int h = getHeight();
        int marginLeft   = 55;
        int marginRight  = 20;
        int marginTop    = 50;
        int marginBottom = 65;
        
        int chartW = w - marginLeft - marginRight;
        int chartH = h - marginTop - marginBottom;
        
        // Title
        g2.setFont(new Font("Segoe UI", Font.BOLD, 15));
        g2.setColor(COLOR_TEXT);
        g2.drawString("📊 Grafik Peminjaman & Pengembalian", marginLeft, 30);
        
        if (labels.length == 0) {
            g2.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            g2.setColor(new Color(150, 150, 150));
            g2.drawString("Belum ada data peminjaman", w / 2 - 90, h / 2);
            g2.dispose();
            return;
        }
        
        // Find max value
        int maxVal = 1;
        for (int i = 0; i < labels.length; i++) {
            maxVal = Math.max(maxVal, dataDipinjam[i] + dataKembali[i] + dataTerlambat[i] + dataHilang[i]);
        }
        maxVal = Math.max(maxVal, 5); // minimum 5
        
        // Draw grid lines & Y-axis labels
        g2.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        int gridLines = 5;
        for (int i = 0; i <= gridLines; i++) {
            int y = marginTop + chartH - (i * chartH / gridLines);
            g2.setColor(COLOR_GRID);
            g2.drawLine(marginLeft, y, w - marginRight, y);
            g2.setColor(new Color(120, 130, 140));
            int val = maxVal * i / gridLines;
            g2.drawString(String.valueOf(val), marginLeft - 30, y + 4);
        }
        
        // Draw bars
        int numGroups = labels.length;
        int barCategories = 4; // dipinjam, kembali, terlambat, hilang
        int groupWidth = chartW / numGroups;
        int barWidth = Math.max(10, Math.min(25, (groupWidth - 20) / barCategories));
        int groupGap = (groupWidth - barWidth * barCategories) / 2;
        
        Color[] colors = {COLOR_DIPINJAM, COLOR_KEMBALI, COLOR_TERLAMBAT, COLOR_HILANG};
        
        for (int i = 0; i < numGroups; i++) {
            int[] values = {dataDipinjam[i], dataKembali[i], dataTerlambat[i], dataHilang[i]};
            int groupX = marginLeft + i * groupWidth + groupGap;
            
            for (int j = 0; j < barCategories; j++) {
                int barH = (int) ((double) values[j] / maxVal * chartH);
                int barX = groupX + j * barWidth;
                int barY = marginTop + chartH - barH;
                
                // Bar with rounded top
                g2.setColor(colors[j]);
                g2.fillRoundRect(barX, barY, barWidth - 2, barH, 4, 4);
                
                // Value label on top
                if (values[j] > 0) {
                    g2.setFont(new Font("Segoe UI", Font.BOLD, 9));
                    g2.setColor(COLOR_TEXT);
                    String valStr = String.valueOf(values[j]);
                    int textW = g2.getFontMetrics().stringWidth(valStr);
                    g2.drawString(valStr, barX + (barWidth - 2 - textW) / 2, barY - 3);
                }
            }
            
            // X-axis label (bulan)
            g2.setFont(new Font("Segoe UI", Font.PLAIN, 10));
            g2.setColor(COLOR_TEXT);
            String label = labels[i];
            int labelW = g2.getFontMetrics().stringWidth(label);
            g2.drawString(label, marginLeft + i * groupWidth + (groupWidth - labelW) / 2, 
                          marginTop + chartH + 18);
        }
        
        // Legend
        int legendY = h - 25;
        int legendX = marginLeft;
        String[] legendLabels = {"Dipinjam", "Kembali", "Terlambat", "Hilang"};
        g2.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        
        for (int i = 0; i < 4; i++) {
            g2.setColor(colors[i]);
            g2.fillRoundRect(legendX, legendY - 10, 14, 14, 3, 3);
            g2.setColor(COLOR_TEXT);
            g2.drawString(legendLabels[i], legendX + 18, legendY + 2);
            legendX += g2.getFontMetrics().stringWidth(legendLabels[i]) + 35;
        }
        
        // Axes
        g2.setColor(new Color(180, 190, 200));
        g2.setStroke(new BasicStroke(1.5f));
        g2.drawLine(marginLeft, marginTop, marginLeft, marginTop + chartH);
        g2.drawLine(marginLeft, marginTop + chartH, w - marginRight, marginTop + chartH);
        
        g2.dispose();
    }
}
