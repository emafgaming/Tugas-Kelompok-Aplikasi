package librarymanagement.util;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * TableUtil - Utility untuk meningkatkan tampilan dan fungsionalitas tabel
 */
public class TableUtil {
    
    private static final Color COLOR_PRIMARY = new Color(26, 82, 118);
    private static final Color COLOR_WHITE = Color.WHITE;
    private static final Color COLOR_HOVER = new Color(52, 152, 219, 30);
    private static final Color COLOR_SELECTED = new Color(52, 152, 219, 100);
    private static final Color COLOR_ZEBRA = new Color(248, 249, 250);
    
    /**
     * Apply modern styling ke tabel dengan hover effect
     */
    public static void applyModernStyle(JTable table) {
        // Basic styling
        table.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        table.setRowHeight(32); // Lebih tinggi untuk kenyamanan
        table.setSelectionBackground(COLOR_SELECTED);
        table.setSelectionForeground(new Color(44, 62, 80));
        table.setGridColor(new Color(225, 230, 235));
        table.setShowHorizontalLines(true);
        table.setShowVerticalLines(false);
        table.setIntercellSpacing(new Dimension(0, 1));
        
        // Header styling
        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 12));
        header.setBackground(COLOR_PRIMARY);
        header.setForeground(Color.WHITE);
        header.setReorderingAllowed(false);
        header.setPreferredSize(new Dimension(header.getWidth(), 36));
        
        // Selection mode
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        // Custom renderer dengan hover effect
        table.setDefaultRenderer(Object.class, new HoverTableCellRenderer());
        
        // Add hover listener
        addHoverEffect(table);
    }
    
    /**
     * Tambahkan hover effect ke tabel
     */
    private static void addHoverEffect(JTable table) {
        table.addMouseMotionListener(new MouseAdapter() {
            private int hoveredRow = -1;
            
            @Override
            public void mouseMoved(MouseEvent e) {
                int row = table.rowAtPoint(e.getPoint());
                if (row != hoveredRow) {
                    hoveredRow = row;
                    table.repaint();
                }
            }
        });
        
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                table.repaint();
            }
        });
    }
    
    /**
     * Custom cell renderer dengan hover dan zebra striping
     */
    static class HoverTableCellRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            
            super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            
            setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
            
            if (!isSelected) {
                // Zebra striping
                setBackground(row % 2 == 0 ? COLOR_WHITE : COLOR_ZEBRA);
                setForeground(new Color(44, 62, 80));
                
                // Hover effect
                Point mouse = table.getMousePosition();
                if (mouse != null) {
                    int hoveredRow = table.rowAtPoint(mouse);
                    if (row == hoveredRow) {
                        setBackground(COLOR_HOVER);
                    }
                }
            }
            
            return this;
        }
    }
    
    /**
     * Buat tabel sortable (klik header untuk sort)
     */
    public static void makeSortable(JTable table) {
        table.setAutoCreateRowSorter(true);
        
        TableRowSorter<TableModel> sorter = new TableRowSorter<>(table.getModel());
        table.setRowSorter(sorter);
        
        // Custom comparator untuk kolom angka
        for (int i = 0; i < table.getColumnCount(); i++) {
            sorter.setComparator(i, (o1, o2) -> {
                // Try to compare as numbers first
                try {
                    Integer num1 = Integer.parseInt(o1.toString().replaceAll("[^0-9]", ""));
                    Integer num2 = Integer.parseInt(o2.toString().replaceAll("[^0-9]", ""));
                    return num1.compareTo(num2);
                } catch (Exception e) {
                    // Fall back to string comparison
                    return o1.toString().compareTo(o2.toString());
                }
            });
        }
    }
    
    /**
     * Auto-resize kolom berdasarkan konten
     */
    public static void autoResizeColumns(JTable table) {
        TableColumnModel columnModel = table.getColumnModel();
        for (int column = 0; column < table.getColumnCount(); column++) {
            TableColumn tableColumn = columnModel.getColumn(column);
            int preferredWidth = tableColumn.getMinWidth();
            int maxWidth = tableColumn.getMaxWidth();
            
            // Header width
            TableCellRenderer headerRenderer = table.getTableHeader().getDefaultRenderer();
            Component headerComp = headerRenderer.getTableCellRendererComponent(
                table, tableColumn.getHeaderValue(), false, false, 0, column);
            preferredWidth = Math.max(preferredWidth, headerComp.getPreferredSize().width);
            
            // Cell width
            for (int row = 0; row < table.getRowCount(); row++) {
                TableCellRenderer cellRenderer = table.getCellRenderer(row, column);
                Component c = table.prepareRenderer(cellRenderer, row, column);
                int width = c.getPreferredSize().width + table.getIntercellSpacing().width;
                preferredWidth = Math.max(preferredWidth, width);
                
                if (preferredWidth >= maxWidth) {
                    preferredWidth = maxWidth;
                    break;
                }
            }
            
            tableColumn.setPreferredWidth(preferredWidth + 10); // Add padding
        }
    }
    
    /**
     * Highlight baris yang cocok dengan search query
     */
    public static void highlightSearchResults(JTable table, String query) {
        if (query == null || query.trim().isEmpty()) {
            table.clearSelection();
            return;
        }
        
        String lowerQuery = query.toLowerCase();
        for (int row = 0; row < table.getRowCount(); row++) {
            for (int col = 0; col < table.getColumnCount(); col++) {
                Object value = table.getValueAt(row, col);
                if (value != null && value.toString().toLowerCase().contains(lowerQuery)) {
                    table.setRowSelectionInterval(row, row);
                    table.scrollRectToVisible(table.getCellRect(row, 0, true));
                    return;
                }
            }
        }
    }
    
    /**
     * Export tabel ke CSV (bonus feature)
     */
    public static void exportToCSV(JTable table, String filename) {
        try {
            java.io.FileWriter fw = new java.io.FileWriter(filename);
            
            // Write headers
            for (int i = 0; i < table.getColumnCount(); i++) {
                fw.write(table.getColumnName(i));
                if (i < table.getColumnCount() - 1) fw.write(",");
            }
            fw.write("\n");
            
            // Write data
            for (int row = 0; row < table.getRowCount(); row++) {
                for (int col = 0; col < table.getColumnCount(); col++) {
                    Object value = table.getValueAt(row, col);
                    fw.write(value != null ? value.toString() : "");
                    if (col < table.getColumnCount() - 1) fw.write(",");
                }
                fw.write("\n");
            }
            
            fw.close();
            ValidationUtil.showSuccess(table, "Data berhasil di-export ke:\n" + filename);
        } catch (Exception e) {
            ValidationUtil.showError(table, "Gagal export data:\n" + e.getMessage());
        }
    }
}
