package librarymanagement.util;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * TableDoubleClickUtil - Utility untuk menambahkan double-click to edit functionality
 */
public class TableDoubleClickUtil {
    
    /**
     * Setup double-click to edit pada tabel
     * 
     * @param table Tabel yang akan ditambahkan functionality
     * @param onDoubleClick Callback yang akan dipanggil saat double-click
     */
    public static void setupDoubleClickEdit(JTable table, DoubleClickCallback onDoubleClick) {
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int row = table.getSelectedRow();
                    if (row >= 0) {
                        DefaultTableModel model = (DefaultTableModel) table.getModel();
                        
                        // Ambil data dari baris yang diklik
                        Object[] rowData = new Object[model.getColumnCount()];
                        for (int i = 0; i < model.getColumnCount(); i++) {
                            rowData[i] = model.getValueAt(row, i);
                        }
                        
                        // Panggil callback
                        onDoubleClick.onDoubleClick(row, rowData);
                    }
                }
            }
        });
    }
    
    /**
     * Setup double-click to edit dengan auto-fill form
     * 
     * @param table Tabel
     * @param fields Array of text fields yang akan di-fill (urutan sesuai kolom tabel)
     * @param startColumn Kolom mulai (biasanya 1, skip ID di kolom 0)
     * @param onDoubleClick Callback tambahan (optional)
     */
    public static void setupDoubleClickAutoFill(JTable table, JTextField[] fields, 
                                                int startColumn, Runnable onDoubleClick) {
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int row = table.getSelectedRow();
                    if (row >= 0) {
                        DefaultTableModel model = (DefaultTableModel) table.getModel();
                        
                        // Fill fields dengan data dari tabel
                        for (int i = 0; i < fields.length && (i + startColumn) < model.getColumnCount(); i++) {
                            Object value = model.getValueAt(row, i + startColumn);
                            if (fields[i] != null && value != null) {
                                fields[i].setText(value.toString());
                            }
                        }
                        
                        // Panggil callback jika ada
                        if (onDoubleClick != null) {
                            onDoubleClick.run();
                        }
                        
                        // Focus ke field pertama
                        if (fields.length > 0 && fields[0] != null) {
                            fields[0].requestFocus();
                            fields[0].selectAll();
                        }
                    }
                }
            }
        });
    }
    
    /**
     * Setup double-click dengan ComboBox support
     */
    public static void setupDoubleClickWithCombo(JTable table, 
                                                  JTextField[] textFields,
                                                  JComboBox<?>[] comboBoxes,
                                                  int[] comboColumns,
                                                  int startColumn,
                                                  Runnable onDoubleClick) {
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int row = table.getSelectedRow();
                    if (row >= 0) {
                        DefaultTableModel model = (DefaultTableModel) table.getModel();
                        
                        // Fill text fields
                        int textFieldIndex = 0;
                        for (int col = startColumn; col < model.getColumnCount(); col++) {
                            // Check if this column is a combo box
                            boolean isComboColumn = false;
                            int comboIndex = -1;
                            for (int i = 0; i < comboColumns.length; i++) {
                                if (comboColumns[i] == col) {
                                    isComboColumn = true;
                                    comboIndex = i;
                                    break;
                                }
                            }
                            
                            Object value = model.getValueAt(row, col);
                            
                            if (isComboColumn && comboIndex >= 0 && comboIndex < comboBoxes.length) {
                                // Set combo box value
                                JComboBox<?> combo = comboBoxes[comboIndex];
                                if (combo != null && value != null) {
                                    setComboBoxValue(combo, value.toString());
                                }
                            } else if (textFieldIndex < textFields.length) {
                                // Set text field value
                                JTextField field = textFields[textFieldIndex];
                                if (field != null && value != null) {
                                    field.setText(value.toString());
                                }
                                textFieldIndex++;
                            }
                        }
                        
                        // Panggil callback
                        if (onDoubleClick != null) {
                            onDoubleClick.run();
                        }
                        
                        // Focus ke field pertama
                        if (textFields.length > 0 && textFields[0] != null) {
                            textFields[0].requestFocus();
                            textFields[0].selectAll();
                        }
                    }
                }
            }
        });
    }
    
    /**
     * Set combo box value berdasarkan string
     */
    private static void setComboBoxValue(JComboBox<?> combo, String value) {
        for (int i = 0; i < combo.getItemCount(); i++) {
            Object item = combo.getItemAt(i);
            if (item != null && item.toString().equals(value)) {
                combo.setSelectedIndex(i);
                return;
            }
        }
    }
    
    /**
     * Callback interface untuk double-click
     */
    public interface DoubleClickCallback {
        void onDoubleClick(int row, Object[] rowData);
    }
    
    /**
     * Setup tooltip untuk memberitahu user tentang double-click
     */
    public static void addDoubleClickTooltip(JTable table) {
        table.setToolTipText("<html>" +
            "<b>Tips:</b><br>" +
            "• Double-click untuk edit<br>" +
            "• Single-click untuk select<br>" +
            "• Delete key untuk hapus<br>" +
            "</html>");
    }
}
