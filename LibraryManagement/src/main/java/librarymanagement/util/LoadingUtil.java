package librarymanagement.util;

import javax.swing.*;
import java.awt.*;

/**
 * LoadingUtil - Utility untuk menampilkan loading indicator
 */
public class LoadingUtil {
    
    private static JDialog loadingDialog;
    private static JLabel loadingLabel;
    private static Timer currentTimer; // Store timer reference
    
    /**
     * Tampilkan loading dialog
     */
    public static void showLoading(Component parent, String message) {
        if (loadingDialog != null && loadingDialog.isVisible()) {
            return; // Already showing
        }
        
        Window window = SwingUtilities.getWindowAncestor(parent);
        loadingDialog = new JDialog(window, Dialog.ModalityType.MODELESS);
        loadingDialog.setUndecorated(true);
        loadingDialog.setLayout(new BorderLayout());
        
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Semi-transparent background
                g2.setColor(new Color(0, 0, 0, 180));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
                
                // Border
                g2.setColor(new Color(52, 152, 219));
                g2.setStroke(new BasicStroke(2));
                g2.drawRoundRect(1, 1, getWidth()-2, getHeight()-2, 15, 15);
                
                g2.dispose();
            }
        };
        panel.setOpaque(false);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        
        // Loading spinner (animated)
        JLabel spinner = new JLabel("⏳");
        spinner.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 32));
        spinner.setForeground(Color.WHITE);
        spinner.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Loading message
        loadingLabel = new JLabel(message);
        loadingLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        loadingLabel.setForeground(Color.WHITE);
        loadingLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        panel.add(spinner);
        panel.add(Box.createVerticalStrut(10));
        panel.add(loadingLabel);
        
        loadingDialog.add(panel);
        loadingDialog.pack();
        loadingDialog.setLocationRelativeTo(parent);
        
        // Animate spinner
        Timer timer = new Timer(200, e -> {
            String[] spinners = {"⏳", "⌛", "⏳", "⌛"};
            int index = (int) (System.currentTimeMillis() / 200 % spinners.length);
            spinner.setText(spinners[index]);
        });
        timer.start();
        
        // Store timer reference (we'll use a static variable)
        currentTimer = timer;
        
        loadingDialog.setVisible(true);
    }
    
    /**
     * Update loading message
     */
    public static void updateMessage(String message) {
        if (loadingLabel != null) {
            loadingLabel.setText(message);
        }
    }
    
    /**
     * Sembunyikan loading dialog
     */
    public static void hideLoading() {
        if (loadingDialog != null) {
            // Stop timer
            if (currentTimer != null) {
                currentTimer.stop();
                currentTimer = null;
            }
            
            loadingDialog.setVisible(false);
            loadingDialog.dispose();
            loadingDialog = null;
            loadingLabel = null;
        }
    }
    
    /**
     * Jalankan task dengan loading indicator
     * 
     * @param parent Parent component
     * @param message Loading message
     * @param task Task yang akan dijalankan
     * @param onComplete Callback setelah selesai
     */
    public static void runWithLoading(Component parent, String message, 
                                      Runnable task, Runnable onComplete) {
        showLoading(parent, message);
        
        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                task.run();
                return null;
            }
            
            @Override
            protected void done() {
                hideLoading();
                if (onComplete != null) {
                    onComplete.run();
                }
            }
        };
        
        worker.execute();
    }
    
    /**
     * Progress bar loading (untuk operasi dengan progress)
     */
    public static class ProgressLoading {
        private JDialog dialog;
        private JProgressBar progressBar;
        private JLabel messageLabel;
        
        public ProgressLoading(Component parent, String title) {
            Window window = SwingUtilities.getWindowAncestor(parent);
            dialog = new JDialog(window, title, Dialog.ModalityType.MODELESS);
            dialog.setLayout(new BorderLayout(10, 10));
            dialog.setUndecorated(false);
            
            JPanel panel = new JPanel(new BorderLayout(10, 10));
            panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
            panel.setBackground(Color.WHITE);
            
            messageLabel = new JLabel("Processing...");
            messageLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            
            progressBar = new JProgressBar(0, 100);
            progressBar.setStringPainted(true);
            progressBar.setPreferredSize(new Dimension(300, 25));
            
            panel.add(messageLabel, BorderLayout.NORTH);
            panel.add(progressBar, BorderLayout.CENTER);
            
            dialog.add(panel);
            dialog.pack();
            dialog.setLocationRelativeTo(parent);
        }
        
        public void show() {
            dialog.setVisible(true);
        }
        
        public void setProgress(int value) {
            progressBar.setValue(value);
        }
        
        public void setMessage(String message) {
            messageLabel.setText(message);
        }
        
        public void close() {
            dialog.setVisible(false);
            dialog.dispose();
        }
    }
}
