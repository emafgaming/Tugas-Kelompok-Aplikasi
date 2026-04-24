package librarymanagement.util;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

/**
 * KeyboardShortcutUtil - Utility untuk menambahkan keyboard shortcuts
 */
public class KeyboardShortcutUtil {
    
    /**
     * Setup keyboard shortcuts untuk form CRUD
     * 
     * @param panel Panel/Form yang akan ditambahkan shortcuts
     * @param btnSave Tombol Save/Tambah
     * @param btnNew Tombol New/Bersihkan
     * @param btnDelete Tombol Delete/Hapus
     */
    public static void setupCRUDShortcuts(JPanel panel, JButton btnSave, 
                                          JButton btnNew, JButton btnDelete) {
        InputMap inputMap = panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = panel.getActionMap();
        
        // Ctrl+S untuk Save
        if (btnSave != null) {
            inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK), "save");
            actionMap.put("save", new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    btnSave.doClick();
                }
            });
        }
        
        // Ctrl+N untuk New/Clear
        if (btnNew != null) {
            inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_N, KeyEvent.CTRL_DOWN_MASK), "new");
            actionMap.put("new", new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    btnNew.doClick();
                }
            });
        }
        
        // Delete key untuk Delete
        if (btnDelete != null) {
            inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0), "delete");
            actionMap.put("delete", new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    btnDelete.doClick();
                }
            });
        }
        
        // Ctrl+F untuk Focus ke Search (jika ada)
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_F, KeyEvent.CTRL_DOWN_MASK), "search");
        actionMap.put("search", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Find search field and focus
                focusSearchField(panel);
            }
        });
        
        // ESC untuk Clear Selection
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "escape");
        actionMap.put("escape", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (btnNew != null) {
                    btnNew.doClick();
                }
            }
        });
    }
    
    /**
     * Setup shortcut untuk tabel
     * 
     * @param table Tabel yang akan ditambahkan shortcuts
     * @param onEnter Action saat Enter ditekan (biasanya untuk edit)
     * @param onDelete Action saat Delete ditekan (biasanya untuk hapus)
     */
    public static void setupTableShortcuts(JTable table, Runnable onEnter, Runnable onDelete) {
        InputMap inputMap = table.getInputMap(JComponent.WHEN_FOCUSED);
        ActionMap actionMap = table.getActionMap();
        
        // Enter untuk Edit
        if (onEnter != null) {
            inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "edit");
            actionMap.put("edit", new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (table.getSelectedRow() >= 0) {
                        onEnter.run();
                    }
                }
            });
        }
        
        // Delete untuk Hapus
        if (onDelete != null) {
            inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0), "delete");
            actionMap.put("delete", new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (table.getSelectedRow() >= 0) {
                        onDelete.run();
                    }
                }
            });
        }
    }
    
    /**
     * Cari dan focus ke search field
     */
    private static void focusSearchField(JPanel panel) {
        for (java.awt.Component comp : panel.getComponents()) {
            if (comp instanceof JTextField) {
                JTextField tf = (JTextField) comp;
                if (tf.getToolTipText() != null && 
                    tf.getToolTipText().toLowerCase().contains("cari")) {
                    tf.requestFocus();
                    tf.selectAll();
                    return;
                }
            } else if (comp instanceof JPanel) {
                searchTextFieldRecursive((JPanel) comp);
            }
        }
    }
    
    /**
     * Cari text field secara rekursif
     */
    private static void searchTextFieldRecursive(JPanel panel) {
        for (java.awt.Component comp : panel.getComponents()) {
            if (comp instanceof JTextField) {
                JTextField tf = (JTextField) comp;
                if (tf.getToolTipText() != null && 
                    tf.getToolTipText().toLowerCase().contains("cari")) {
                    tf.requestFocus();
                    tf.selectAll();
                    return;
                }
            } else if (comp instanceof JPanel) {
                searchTextFieldRecursive((JPanel) comp);
            }
        }
    }
    
    /**
     * Tampilkan tooltip keyboard shortcuts
     */
    public static String getShortcutsTooltip() {
        return "<html>" +
               "<b>Keyboard Shortcuts:</b><br>" +
               "Ctrl+S - Save/Tambah<br>" +
               "Ctrl+N - New/Bersihkan<br>" +
               "Delete - Hapus<br>" +
               "Ctrl+F - Focus Search<br>" +
               "ESC - Clear Selection<br>" +
               "Enter - Edit (di tabel)<br>" +
               "</html>";
    }
    
    /**
     * Setup tooltip untuk menampilkan shortcuts
     */
    public static void addShortcutTooltip(JComponent component) {
        component.setToolTipText(getShortcutsTooltip());
    }
}
