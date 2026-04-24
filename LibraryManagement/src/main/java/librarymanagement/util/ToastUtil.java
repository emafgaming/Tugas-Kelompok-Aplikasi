package librarymanagement.util;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * ToastUtil - Utility untuk menampilkan toast notification (auto-close)
 */
public class ToastUtil {
    
    public enum ToastType {
        SUCCESS(new Color(39, 174, 96), "✅"),
        ERROR(new Color(192, 57, 43), "❌"),
        WARNING(new Color(211, 84, 0), "⚠️"),
        INFO(new Color(52, 152, 219), "ℹ️");
        
        private final Color color;
        private final String icon;
        
        ToastType(Color color, String icon) {
            this.color = color;
            this.icon = icon;
        }
        
        public Color getColor() { return color; }
        public String getIcon() { return icon; }
    }
    
    /**
     * Tampilkan toast notification
     * 
     * @param parent Parent component
     * @param message Pesan yang akan ditampilkan
     * @param type Tipe toast (SUCCESS, ERROR, WARNING, INFO)
     * @param duration Durasi tampil dalam milidetik (default: 3000ms)
     */
    public static void showToast(Component parent, String message, ToastType type, int duration) {
        Window window = SwingUtilities.getWindowAncestor(parent);
        if (window == null) return;
        
        JWindow toast = new JWindow(window);
        toast.setAlwaysOnTop(true);
        
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Background dengan shadow effect
                g2.setColor(new Color(0, 0, 0, 50));
                g2.fillRoundRect(4, 4, getWidth()-4, getHeight()-4, 12, 12);
                
                // Background utama
                g2.setColor(type.getColor());
                g2.fillRoundRect(0, 0, getWidth()-4, getHeight()-4, 12, 12);
                
                // Border
                g2.setColor(type.getColor().darker());
                g2.setStroke(new BasicStroke(2));
                g2.drawRoundRect(0, 0, getWidth()-5, getHeight()-5, 12, 12);
                
                g2.dispose();
            }
        };
        panel.setOpaque(false);
        panel.setLayout(new BorderLayout(10, 0));
        panel.setBorder(BorderFactory.createEmptyBorder(12, 15, 12, 15));
        
        // Icon
        JLabel iconLabel = new JLabel(type.getIcon());
        iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 20));
        iconLabel.setForeground(Color.WHITE);
        
        // Message
        JLabel messageLabel = new JLabel("<html>" + message.replace("\n", "<br>") + "</html>");
        messageLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        messageLabel.setForeground(Color.WHITE);
        
        // Close button
        JLabel closeLabel = new JLabel("✕");
        closeLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        closeLabel.setForeground(new Color(255, 255, 255, 180));
        closeLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        closeLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                fadeOut(toast);
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                closeLabel.setForeground(Color.WHITE);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                closeLabel.setForeground(new Color(255, 255, 255, 180));
            }
        });
        
        panel.add(iconLabel, BorderLayout.WEST);
        panel.add(messageLabel, BorderLayout.CENTER);
        panel.add(closeLabel, BorderLayout.EAST);
        
        toast.add(panel);
        toast.pack();
        
        // Position di pojok kanan bawah
        Rectangle bounds = window.getBounds();
        int x = bounds.x + bounds.width - toast.getWidth() - 20;
        int y = bounds.y + bounds.height - toast.getHeight() - 60;
        toast.setLocation(x, y);
        
        // Fade in animation
        toast.setOpacity(0f);
        toast.setVisible(true);
        
        Timer fadeInTimer = new Timer(20, null);
        fadeInTimer.addActionListener(e -> {
            float opacity = toast.getOpacity();
            if (opacity < 1f) {
                toast.setOpacity(Math.min(1f, opacity + 0.1f));
            } else {
                fadeInTimer.stop();
            }
        });
        fadeInTimer.start();
        
        // Auto close setelah duration
        Timer closeTimer = new Timer(duration, e -> fadeOut(toast));
        closeTimer.setRepeats(false);
        closeTimer.start();
    }
    
    /**
     * Fade out animation
     */
    private static void fadeOut(JWindow toast) {
        Timer fadeOutTimer = new Timer(20, null);
        fadeOutTimer.addActionListener(e -> {
            float opacity = toast.getOpacity();
            if (opacity > 0f) {
                toast.setOpacity(Math.max(0f, opacity - 0.1f));
            } else {
                fadeOutTimer.stop();
                toast.dispose();
            }
        });
        fadeOutTimer.start();
    }
    
    /**
     * Shortcut methods
     */
    public static void showSuccess(Component parent, String message) {
        showToast(parent, message, ToastType.SUCCESS, 3000);
    }
    
    public static void showError(Component parent, String message) {
        showToast(parent, message, ToastType.ERROR, 4000);
    }
    
    public static void showWarning(Component parent, String message) {
        showToast(parent, message, ToastType.WARNING, 3500);
    }
    
    public static void showInfo(Component parent, String message) {
        showToast(parent, message, ToastType.INFO, 3000);
    }
    
    /**
     * Tampilkan toast dengan durasi custom
     */
    public static void showSuccess(Component parent, String message, int duration) {
        showToast(parent, message, ToastType.SUCCESS, duration);
    }
    
    public static void showError(Component parent, String message, int duration) {
        showToast(parent, message, ToastType.ERROR, duration);
    }
}
