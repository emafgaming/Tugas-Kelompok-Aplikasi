package librarymanagement.util;

import javax.swing.*;
import java.awt.*;
import java.util.regex.Pattern;

/**
 * ValidationUtil - Utility untuk validasi input dan notifikasi
 */
public class ValidationUtil {
    
    // Regex patterns
    private static final Pattern PHONE_PATTERN = Pattern.compile("^[0-9]{10,13}$");
    private static final Pattern YEAR_PATTERN = Pattern.compile("^(19|20)\\d{2}$");
    private static final Pattern NUMBER_PATTERN = Pattern.compile("^[0-9]+$");
    private static final Pattern DATE_PATTERN = Pattern.compile("^\\d{4}-\\d{2}-\\d{2}$");
    
    /**
     * Validasi field tidak boleh kosong
     */
    public static boolean isNotEmpty(String value, String fieldName, Component parent) {
        if (value == null || value.trim().isEmpty()) {
            showError(parent, "Field '" + fieldName + "' tidak boleh kosong!");
            return false;
        }
        return true;
    }
    
    /**
     * Validasi nomor HP
     */
    public static boolean isValidPhone(String phone, Component parent) {
        if (!PHONE_PATTERN.matcher(phone).matches()) {
            showError(parent, "Nomor HP tidak valid!\nHarus 10-13 digit angka.");
            return false;
        }
        return true;
    }
    
    /**
     * Validasi tahun
     */
    public static boolean isValidYear(String year, Component parent) {
        if (!YEAR_PATTERN.matcher(year).matches()) {
            showError(parent, "Tahun tidak valid!\nHarus format 4 digit (contoh: 2024)");
            return false;
        }
        return true;
    }
    
    /**
     * Validasi angka positif
     */
    public static boolean isValidNumber(String value, String fieldName, Component parent) {
        if (!NUMBER_PATTERN.matcher(value).matches()) {
            showError(parent, "Field '" + fieldName + "' harus berisi angka!");
            return false;
        }
        try {
            int num = Integer.parseInt(value);
            if (num <= 0) {
                showError(parent, "Field '" + fieldName + "' harus lebih dari 0!");
                return false;
            }
        } catch (NumberFormatException e) {
            showError(parent, "Field '" + fieldName + "' tidak valid!");
            return false;
        }
        return true;
    }
    
    /**
     * Validasi format tanggal
     */
    public static boolean isValidDate(String date, Component parent) {
        if (!DATE_PATTERN.matcher(date).matches()) {
            showError(parent, "Format tanggal tidak valid!\nHarus: YYYY-MM-DD (contoh: 2024-01-15)");
            return false;
        }
        return true;
    }
    
    /**
     * Validasi stok mencukupi
     */
    public static boolean isStockAvailable(int requested, int available, Component parent) {
        if (requested > available) {
            showError(parent, "Stok tidak mencukupi!\nDiminta: " + requested + ", Tersedia: " + available);
            return false;
        }
        return true;
    }
    
    /**
     * Tampilkan pesan error dengan style modern
     */
    public static void showError(Component parent, String message) {
        JOptionPane.showMessageDialog(
            parent,
            message,
            "❌ Validasi Error",
            JOptionPane.ERROR_MESSAGE
        );
    }
    
    /**
     * Tampilkan pesan sukses dengan style modern
     */
    public static void showSuccess(Component parent, String message) {
        JOptionPane.showMessageDialog(
            parent,
            message,
            "✅ Berhasil",
            JOptionPane.INFORMATION_MESSAGE
        );
    }
    
    /**
     * Tampilkan pesan warning
     */
    public static void showWarning(Component parent, String message) {
        JOptionPane.showMessageDialog(
            parent,
            message,
            "⚠️ Peringatan",
            JOptionPane.WARNING_MESSAGE
        );
    }
    
    /**
     * Konfirmasi hapus dengan detail data
     */
    public static boolean confirmDelete(Component parent, String itemName, String details) {
        String message = "Apakah Anda yakin ingin menghapus data ini?\n\n" +
                        "📌 " + itemName + "\n" +
                        details + "\n\n" +
                        "⚠️ Data yang sudah dihapus tidak dapat dikembalikan!";
        
        int result = JOptionPane.showConfirmDialog(
            parent,
            message,
            "🗑️ Konfirmasi Hapus",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE
        );
        
        return result == JOptionPane.YES_OPTION;
    }
    
    /**
     * Konfirmasi aksi umum
     */
    public static boolean confirm(Component parent, String title, String message) {
        int result = JOptionPane.showConfirmDialog(
            parent,
            message,
            title,
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE
        );
        
        return result == JOptionPane.YES_OPTION;
    }
    
    /**
     * Highlight field yang error
     */
    public static void highlightError(JTextField field) {
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(192, 57, 43), 2),
            BorderFactory.createEmptyBorder(5, 8, 5, 8)
        ));
        field.requestFocus();
    }
    
    /**
     * Reset highlight field
     */
    public static void resetHighlight(JTextField field) {
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(189, 195, 199)),
            BorderFactory.createEmptyBorder(5, 8, 5, 8)
        ));
    }
}
