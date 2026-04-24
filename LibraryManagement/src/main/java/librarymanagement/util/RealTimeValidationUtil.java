package librarymanagement.util;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.util.regex.Pattern;

/**
 * RealTimeValidationUtil - Utility untuk validasi real-time saat user mengetik
 */
public class RealTimeValidationUtil {
    
    private static final Color COLOR_ERROR = new Color(192, 57, 43);
    private static final Color COLOR_SUCCESS = new Color(39, 174, 96);
    private static final Color COLOR_NORMAL = new Color(189, 195, 199);
    
    private static final Border BORDER_ERROR = BorderFactory.createCompoundBorder(
        BorderFactory.createLineBorder(COLOR_ERROR, 2),
        BorderFactory.createEmptyBorder(5, 8, 5, 8)
    );
    
    private static final Border BORDER_SUCCESS = BorderFactory.createCompoundBorder(
        BorderFactory.createLineBorder(COLOR_SUCCESS, 2),
        BorderFactory.createEmptyBorder(5, 8, 5, 8)
    );
    
    private static final Border BORDER_NORMAL = BorderFactory.createCompoundBorder(
        BorderFactory.createLineBorder(COLOR_NORMAL, 1),
        BorderFactory.createEmptyBorder(5, 8, 5, 8)
    );
    
    /**
     * Setup validasi real-time untuk field yang tidak boleh kosong
     */
    public static void setupNotEmptyValidation(JTextField field, JLabel errorLabel) {
        field.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) { validate(); }
            @Override
            public void removeUpdate(DocumentEvent e) { validate(); }
            @Override
            public void changedUpdate(DocumentEvent e) { validate(); }
            
            private void validate() {
                String text = field.getText().trim();
                if (text.isEmpty()) {
                    field.setBorder(BORDER_ERROR);
                    if (errorLabel != null) {
                        errorLabel.setText("❌ Tidak boleh kosong");
                        errorLabel.setForeground(COLOR_ERROR);
                        errorLabel.setVisible(true);
                    }
                } else {
                    field.setBorder(BORDER_SUCCESS);
                    if (errorLabel != null) {
                        errorLabel.setText("✓");
                        errorLabel.setForeground(COLOR_SUCCESS);
                        errorLabel.setVisible(true);
                    }
                }
            }
        });
    }
    
    /**
     * Setup validasi real-time untuk nomor HP
     */
    public static void setupPhoneValidation(JTextField field, JLabel errorLabel) {
        Pattern phonePattern = Pattern.compile("^[0-9]{10,13}$");
        
        field.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) { validate(); }
            @Override
            public void removeUpdate(DocumentEvent e) { validate(); }
            @Override
            public void changedUpdate(DocumentEvent e) { validate(); }
            
            private void validate() {
                String text = field.getText().trim();
                if (text.isEmpty()) {
                    field.setBorder(BORDER_NORMAL);
                    if (errorLabel != null) {
                        errorLabel.setVisible(false);
                    }
                } else if (phonePattern.matcher(text).matches()) {
                    field.setBorder(BORDER_SUCCESS);
                    if (errorLabel != null) {
                        errorLabel.setText("✓ Valid");
                        errorLabel.setForeground(COLOR_SUCCESS);
                        errorLabel.setVisible(true);
                    }
                } else {
                    field.setBorder(BORDER_ERROR);
                    if (errorLabel != null) {
                        errorLabel.setText("❌ Harus 10-13 digit");
                        errorLabel.setForeground(COLOR_ERROR);
                        errorLabel.setVisible(true);
                    }
                }
            }
        });
    }
    
    /**
     * Setup validasi real-time untuk tahun
     */
    public static void setupYearValidation(JTextField field, JLabel errorLabel) {
        Pattern yearPattern = Pattern.compile("^(19|20)\\d{2}$");
        
        field.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) { validate(); }
            @Override
            public void removeUpdate(DocumentEvent e) { validate(); }
            @Override
            public void changedUpdate(DocumentEvent e) { validate(); }
            
            private void validate() {
                String text = field.getText().trim();
                if (text.isEmpty()) {
                    field.setBorder(BORDER_NORMAL);
                    if (errorLabel != null) {
                        errorLabel.setVisible(false);
                    }
                } else if (yearPattern.matcher(text).matches()) {
                    field.setBorder(BORDER_SUCCESS);
                    if (errorLabel != null) {
                        errorLabel.setText("✓");
                        errorLabel.setForeground(COLOR_SUCCESS);
                        errorLabel.setVisible(true);
                    }
                } else {
                    field.setBorder(BORDER_ERROR);
                    if (errorLabel != null) {
                        errorLabel.setText("❌ Format: YYYY");
                        errorLabel.setForeground(COLOR_ERROR);
                        errorLabel.setVisible(true);
                    }
                }
            }
        });
    }
    
    /**
     * Setup validasi real-time untuk angka positif
     */
    public static void setupNumberValidation(JTextField field, JLabel errorLabel) {
        Pattern numberPattern = Pattern.compile("^[0-9]+$");
        
        field.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) { validate(); }
            @Override
            public void removeUpdate(DocumentEvent e) { validate(); }
            @Override
            public void changedUpdate(DocumentEvent e) { validate(); }
            
            private void validate() {
                String text = field.getText().trim();
                if (text.isEmpty()) {
                    field.setBorder(BORDER_NORMAL);
                    if (errorLabel != null) {
                        errorLabel.setVisible(false);
                    }
                } else if (numberPattern.matcher(text).matches()) {
                    int num = Integer.parseInt(text);
                    if (num > 0) {
                        field.setBorder(BORDER_SUCCESS);
                        if (errorLabel != null) {
                            errorLabel.setText("✓");
                            errorLabel.setForeground(COLOR_SUCCESS);
                            errorLabel.setVisible(true);
                        }
                    } else {
                        field.setBorder(BORDER_ERROR);
                        if (errorLabel != null) {
                            errorLabel.setText("❌ Harus > 0");
                            errorLabel.setForeground(COLOR_ERROR);
                            errorLabel.setVisible(true);
                        }
                    }
                } else {
                    field.setBorder(BORDER_ERROR);
                    if (errorLabel != null) {
                        errorLabel.setText("❌ Harus angka");
                        errorLabel.setForeground(COLOR_ERROR);
                        errorLabel.setVisible(true);
                    }
                }
            }
        });
    }
    
    /**
     * Setup validasi real-time untuk tanggal (YYYY-MM-DD)
     */
    public static void setupDateValidation(JTextField field, JLabel errorLabel) {
        Pattern datePattern = Pattern.compile("^\\d{4}-\\d{2}-\\d{2}$");
        
        field.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) { validate(); }
            @Override
            public void removeUpdate(DocumentEvent e) { validate(); }
            @Override
            public void changedUpdate(DocumentEvent e) { validate(); }
            
            private void validate() {
                String text = field.getText().trim();
                if (text.isEmpty()) {
                    field.setBorder(BORDER_NORMAL);
                    if (errorLabel != null) {
                        errorLabel.setVisible(false);
                    }
                } else if (datePattern.matcher(text).matches()) {
                    field.setBorder(BORDER_SUCCESS);
                    if (errorLabel != null) {
                        errorLabel.setText("✓");
                        errorLabel.setForeground(COLOR_SUCCESS);
                        errorLabel.setVisible(true);
                    }
                } else {
                    field.setBorder(BORDER_ERROR);
                    if (errorLabel != null) {
                        errorLabel.setText("❌ Format: YYYY-MM-DD");
                        errorLabel.setForeground(COLOR_ERROR);
                        errorLabel.setVisible(true);
                    }
                }
            }
        });
    }
    
    /**
     * Setup validasi custom dengan regex
     */
    public static void setupCustomValidation(JTextField field, JLabel errorLabel, 
                                             Pattern pattern, String errorMessage) {
        field.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) { validate(); }
            @Override
            public void removeUpdate(DocumentEvent e) { validate(); }
            @Override
            public void changedUpdate(DocumentEvent e) { validate(); }
            
            private void validate() {
                String text = field.getText().trim();
                if (text.isEmpty()) {
                    field.setBorder(BORDER_NORMAL);
                    if (errorLabel != null) {
                        errorLabel.setVisible(false);
                    }
                } else if (pattern.matcher(text).matches()) {
                    field.setBorder(BORDER_SUCCESS);
                    if (errorLabel != null) {
                        errorLabel.setText("✓");
                        errorLabel.setForeground(COLOR_SUCCESS);
                        errorLabel.setVisible(true);
                    }
                } else {
                    field.setBorder(BORDER_ERROR);
                    if (errorLabel != null) {
                        errorLabel.setText("❌ " + errorMessage);
                        errorLabel.setForeground(COLOR_ERROR);
                        errorLabel.setVisible(true);
                    }
                }
            }
        });
    }
    
    /**
     * Reset border ke normal
     */
    public static void resetBorder(JTextField field) {
        field.setBorder(BORDER_NORMAL);
    }
    
    /**
     * Cek apakah field valid
     */
    public static boolean isFieldValid(JTextField field) {
        return field.getBorder() == BORDER_SUCCESS;
    }
}
