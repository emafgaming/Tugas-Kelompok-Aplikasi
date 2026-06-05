package librarymanagement.util;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;

/**
 * ModernUIUtil - Utility untuk membuat komponen UI modern dan profesional
 */
public class ModernUIUtil {
    
    // Modern Color Palette - Professional & Clean
    public static class Colors {
        // Primary Colors
        public static final Color PRIMARY = new Color(59, 130, 246);      // Blue 500
        public static final Color PRIMARY_DARK = new Color(37, 99, 235);  // Blue 600
        public static final Color PRIMARY_LIGHT = new Color(96, 165, 250); // Blue 400
        
        // Semantic Colors
        public static final Color SUCCESS = new Color(34, 197, 94);       // Green 500
        public static final Color SUCCESS_DARK = new Color(22, 163, 74);  // Green 600
        public static final Color WARNING = new Color(251, 146, 60);      // Orange 400
        public static final Color WARNING_DARK = new Color(249, 115, 22); // Orange 500
        public static final Color DANGER = new Color(239, 68, 68);        // Red 500
        public static final Color DANGER_DARK = new Color(220, 38, 38);   // Red 600
        public static final Color INFO = new Color(14, 165, 233);         // Sky 500
        
        // Neutral Colors
        public static final Color GRAY_50 = new Color(249, 250, 251);
        public static final Color GRAY_100 = new Color(243, 244, 246);
        public static final Color GRAY_200 = new Color(229, 231, 235);
        public static final Color GRAY_300 = new Color(209, 213, 219);
        public static final Color GRAY_400 = new Color(156, 163, 175);
        public static final Color GRAY_500 = new Color(107, 114, 128);
        public static final Color GRAY_600 = new Color(75, 85, 99);
        public static final Color GRAY_700 = new Color(55, 65, 81);
        public static final Color GRAY_800 = new Color(31, 41, 55);
        public static final Color GRAY_900 = new Color(17, 24, 39);
        
        // Background Colors
        public static final Color BG_PRIMARY = Color.WHITE;
        public static final Color BG_SECONDARY = GRAY_50;
        public static final Color BG_TERTIARY = GRAY_100;
        
        // Text Colors
        public static final Color TEXT_PRIMARY = GRAY_900;
        public static final Color TEXT_SECONDARY = GRAY_600;
        public static final Color TEXT_TERTIARY = GRAY_400;
        
        // Border Colors
        public static final Color BORDER_LIGHT = GRAY_200;
        public static final Color BORDER_DEFAULT = GRAY_300;
        public static final Color BORDER_DARK = GRAY_400;
    }
    
    /**
     * Buat modern button dengan style yang konsisten
     */
    public static JButton createModernButton(String text, Color bgColor, Color hoverColor) {
        JButton button = new JButton(text) {
            private Color currentBg = bgColor;
            private boolean isHovered = false;
            
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Background
                g2.setColor(currentBg);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                
                // Subtle shadow when hovered
                if (isHovered && isEnabled()) {
                    g2.setColor(new Color(0, 0, 0, 20));
                    g2.fillRoundRect(0, 2, getWidth(), getHeight(), 8, 8);
                }
                
                // Text
                g2.setColor(getForeground());
                g2.setFont(getFont());
                FontMetrics fm = g2.getFontMetrics();
                int textX = (getWidth() - fm.stringWidth(getText())) / 2;
                int textY = (getHeight() + fm.getAscent() - fm.getDescent()) / 2;
                g2.drawString(getText(), textX, textY);
                
                g2.dispose();
            }
            
            {
                addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseEntered(MouseEvent e) {
                        if (isEnabled()) {
                            isHovered = true;
                            currentBg = hoverColor;
                            repaint();
                        }
                    }
                    
                    @Override
                    public void mouseExited(MouseEvent e) {
                        isHovered = false;
                        currentBg = bgColor;
                        repaint();
                    }
                    
                    @Override
                    public void mousePressed(MouseEvent e) {
                        if (isEnabled()) {
                            currentBg = hoverColor.darker();
                            repaint();
                        }
                    }
                    
                    @Override
                    public void mouseReleased(MouseEvent e) {
                        if (isEnabled()) {
                            currentBg = isHovered ? hoverColor : bgColor;
                            repaint();
                        }
                    }
                });
            }
        };
        
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Segoe UI", Font.BOLD, 13));
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(120, 38));
        
        return button;
    }
    
    /**
     * Buat modern text field dengan floating label effect
     */
    public static JTextField createModernTextField(String placeholder) {
        JTextField textField = new JTextField() {
            private boolean isFocused = false;
            
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Background
                g2.setColor(Colors.BG_PRIMARY);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                
                super.paintComponent(g);
                g2.dispose();
            }
            
            @Override
            protected void paintBorder(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                if (isFocused) {
                    g2.setColor(Colors.PRIMARY);
                    g2.setStroke(new BasicStroke(2));
                } else {
                    g2.setColor(Colors.BORDER_DEFAULT);
                    g2.setStroke(new BasicStroke(1));
                }
                
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 8, 8);
                g2.dispose();
            }
            
            {
                addFocusListener(new FocusAdapter() {
                    @Override
                    public void focusGained(FocusEvent e) {
                        isFocused = true;
                        repaint();
                    }
                    
                    @Override
                    public void focusLost(FocusEvent e) {
                        isFocused = false;
                        repaint();
                    }
                });
            }
        };
        
        textField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        textField.setForeground(Colors.TEXT_PRIMARY);
        textField.setOpaque(false);
        textField.setBorder(new EmptyBorder(10, 14, 10, 14));
        textField.setPreferredSize(new Dimension(200, 42));
        
        // Placeholder effect
        textField.putClientProperty("JTextField.placeholderText", placeholder);
        
        return textField;
    }
    
    /**
     * Buat modern card panel dengan shadow
     */
    public static JPanel createCardPanel() {
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Shadow
                g2.setColor(new Color(0, 0, 0, 10));
                g2.fillRoundRect(2, 4, getWidth() - 4, getHeight() - 4, 12, 12);
                
                // Card background
                g2.setColor(Colors.BG_PRIMARY);
                g2.fillRoundRect(0, 0, getWidth() - 4, getHeight() - 6, 12, 12);
                
                // Border
                g2.setColor(Colors.BORDER_LIGHT);
                g2.drawRoundRect(0, 0, getWidth() - 5, getHeight() - 7, 12, 12);
                
                g2.dispose();
            }
        };
        
        panel.setOpaque(false);
        panel.setBorder(new EmptyBorder(16, 16, 16, 16));
        
        return panel;
    }
    
    /**
     * Buat modern scrollbar
     */
    public static void styleModernScrollBar(JScrollPane scrollPane) {
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUI(new ModernScrollBarUI());
        scrollPane.getHorizontalScrollBar().setUI(new ModernScrollBarUI());
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.getHorizontalScrollBar().setUnitIncrement(16);
    }
    
    /**
     * Custom ScrollBar UI yang modern
     */
    private static class ModernScrollBarUI extends BasicScrollBarUI {
        @Override
        protected void configureScrollBarColors() {
            this.thumbColor = Colors.GRAY_300;
            this.thumbDarkShadowColor = Colors.GRAY_400;
            this.thumbHighlightColor = Colors.GRAY_200;
            this.trackColor = Colors.GRAY_100;
        }
        
        @Override
        protected JButton createDecreaseButton(int orientation) {
            return createZeroButton();
        }
        
        @Override
        protected JButton createIncreaseButton(int orientation) {
            return createZeroButton();
        }
        
        private JButton createZeroButton() {
            JButton button = new JButton();
            button.setPreferredSize(new Dimension(0, 0));
            button.setMinimumSize(new Dimension(0, 0));
            button.setMaximumSize(new Dimension(0, 0));
            return button;
        }
        
        @Override
        protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            g2.setColor(thumbColor);
            if (isDragging) {
                g2.setColor(thumbDarkShadowColor);
            }
            
            int arc = 8;
            g2.fillRoundRect(thumbBounds.x + 2, thumbBounds.y + 2, 
                           thumbBounds.width - 4, thumbBounds.height - 4, arc, arc);
            
            g2.dispose();
        }
        
        @Override
        protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setColor(trackColor);
            g2.fillRect(trackBounds.x, trackBounds.y, trackBounds.width, trackBounds.height);
            g2.dispose();
        }
    }
    
    /**
     * Buat badge label
     */
    public static JLabel createBadge(String text, Color bgColor) {
        JLabel badge = new JLabel(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                g2.setColor(bgColor);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);
                
                super.paintComponent(g);
                g2.dispose();
            }
        };
        
        badge.setFont(new Font("Segoe UI", Font.BOLD, 11));
        badge.setForeground(Color.WHITE);
        badge.setHorizontalAlignment(SwingConstants.CENTER);
        badge.setOpaque(false);
        badge.setBorder(new EmptyBorder(4, 12, 4, 12));
        
        return badge;
    }
    
    /**
     * Buat divider horizontal
     */
    public static JSeparator createDivider() {
        JSeparator separator = new JSeparator();
        separator.setForeground(Colors.BORDER_LIGHT);
        separator.setBackground(Colors.BORDER_LIGHT);
        return separator;
    }
    
    /**
     * Tambahkan hover effect ke panel
     */
    public static void addHoverEffect(JPanel panel, Color normalColor, Color hoverColor) {
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                panel.setBackground(hoverColor);
                panel.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                panel.setBackground(normalColor);
                panel.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        });
    }
    
    /**
     * Buat icon label dengan background circle
     */
    public static JPanel createIconCircle(String icon, Color bgColor, int size) {
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Circle background
                g2.setColor(new Color(bgColor.getRed(), bgColor.getGreen(), bgColor.getBlue(), 30));
                g2.fillOval(0, 0, getWidth(), getHeight());
                
                g2.dispose();
            }
        };
        
        panel.setLayout(new GridBagLayout());
        panel.setOpaque(false);
        panel.setPreferredSize(new Dimension(size, size));
        panel.setMinimumSize(new Dimension(size, size));
        panel.setMaximumSize(new Dimension(size, size));
        
        JLabel iconLabel = new JLabel(icon);
        iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, size / 2));
        panel.add(iconLabel);
        
        return panel;
    }
}
