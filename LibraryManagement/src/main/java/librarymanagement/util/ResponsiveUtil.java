package librarymanagement.util;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

/**
 * ResponsiveUtil - Utility untuk membuat UI responsif
 * Mendukung berbagai ukuran layar: Desktop, Tablet, Mobile
 */
public class ResponsiveUtil {
    
    // Breakpoints untuk responsive design
    public static final int BREAKPOINT_MOBILE = 768;
    public static final int BREAKPOINT_TABLET = 1024;
    public static final int BREAKPOINT_DESKTOP = 1440;
    
    // Screen size categories
    public enum ScreenSize {
        MOBILE,    // < 768px
        TABLET,    // 768px - 1024px
        DESKTOP,   // 1024px - 1440px
        LARGE      // > 1440px
    }
    
    /**
     * Dapatkan kategori ukuran layar berdasarkan lebar
     */
    public static ScreenSize getScreenSize(int width) {
        if (width < BREAKPOINT_MOBILE) return ScreenSize.MOBILE;
        if (width < BREAKPOINT_TABLET) return ScreenSize.TABLET;
        if (width < BREAKPOINT_DESKTOP) return ScreenSize.DESKTOP;
        return ScreenSize.LARGE;
    }
    
    /**
     * Dapatkan ukuran layar saat ini dari komponen
     */
    public static ScreenSize getCurrentScreenSize(Component component) {
        Window window = SwingUtilities.getWindowAncestor(component);
        if (window != null) {
            return getScreenSize(window.getWidth());
        }
        return ScreenSize.DESKTOP;
    }
    
    /**
     * Scale font size berdasarkan ukuran layar
     */
    public static int scaleFontSize(int baseSize, ScreenSize screenSize) {
        switch (screenSize) {
            case MOBILE:  return (int)(baseSize * 0.85);
            case TABLET:  return (int)(baseSize * 0.95);
            case DESKTOP: return baseSize;
            case LARGE:   return (int)(baseSize * 1.1);
            default:      return baseSize;
        }
    }
    
    /**
     * Scale spacing berdasarkan ukuran layar
     */
    public static int scaleSpacing(int baseSpacing, ScreenSize screenSize) {
        switch (screenSize) {
            case MOBILE:  return (int)(baseSpacing * 0.7);
            case TABLET:  return (int)(baseSpacing * 0.85);
            case DESKTOP: return baseSpacing;
            case LARGE:   return (int)(baseSpacing * 1.15);
            default:      return baseSpacing;
        }
    }
    
    /**
     * Dapatkan lebar sidebar berdasarkan ukuran layar
     */
    public static int getSidebarWidth(ScreenSize screenSize) {
        switch (screenSize) {
            case MOBILE:  return 60;   // Icon only
            case TABLET:  return 200;
            case DESKTOP: return 260;
            case LARGE:   return 280;
            default:      return 260;
        }
    }
    
    /**
     * Dapatkan lebar form panel berdasarkan ukuran layar
     */
    public static int getFormPanelWidth(ScreenSize screenSize) {
        switch (screenSize) {
            case MOBILE:  return 0;    // Full width
            case TABLET:  return 280;
            case DESKTOP: return 320;
            case LARGE:   return 360;
            default:      return 320;
        }
    }
    
    /**
     * Cek apakah layout harus vertikal (mobile)
     */
    public static boolean shouldUseVerticalLayout(ScreenSize screenSize) {
        return screenSize == ScreenSize.MOBILE;
    }
    
    /**
     * Tambahkan responsive listener ke frame
     */
    public static void addResponsiveListener(JFrame frame, ResponsiveCallback callback) {
        final ScreenSize[] currentSize = {getScreenSize(frame.getWidth())};
        
        frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                ScreenSize newSize = getScreenSize(frame.getWidth());
                if (newSize != currentSize[0]) {
                    currentSize[0] = newSize;
                    SwingUtilities.invokeLater(() -> callback.onScreenSizeChanged(newSize));
                }
            }
        });
    }
    
    /**
     * Callback interface untuk perubahan ukuran layar
     */
    public interface ResponsiveCallback {
        void onScreenSizeChanged(ScreenSize newSize);
    }
    
    /**
     * Buat panel dengan padding responsif
     */
    public static void setResponsivePadding(JComponent component, int basePadding, ScreenSize screenSize) {
        int padding = scaleSpacing(basePadding, screenSize);
        component.setBorder(BorderFactory.createEmptyBorder(padding, padding, padding, padding));
    }
    
    /**
     * Dapatkan ukuran minimum window berdasarkan screen size
     */
    public static Dimension getMinimumWindowSize(ScreenSize screenSize) {
        switch (screenSize) {
            case MOBILE:  return new Dimension(360, 640);
            case TABLET:  return new Dimension(768, 600);
            case DESKTOP: return new Dimension(1024, 700);
            case LARGE:   return new Dimension(1280, 800);
            default:      return new Dimension(1024, 700);
        }
    }
}
