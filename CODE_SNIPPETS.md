# Code Snippets - Copy & Paste Ready

## 📋 Daftar Isi
- [Imports](#imports)
- [Colors](#colors)
- [Buttons](#buttons)
- [Text Fields](#text-fields)
- [Cards](#cards)
- [Tables](#tables)
- [Responsive](#responsive)
- [Animations](#animations)
- [Complete Examples](#complete-examples)

## 📦 Imports

```java
// Basic imports
import librarymanagement.util.ModernUIUtil;
import librarymanagement.util.ResponsiveUtil;
import librarymanagement.util.AnimationUtil;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

// For tables
import javax.swing.table.*;

// For custom painting
import java.awt.geom.*;
```

## 🎨 Colors

```java
// Import colors
import librarymanagement.util.ModernUIUtil.Colors;

// Use colors
setBackground(Colors.BG_PRIMARY);
setForeground(Colors.TEXT_PRIMARY);
setBorder(BorderFactory.createLineBorder(Colors.BORDER_DEFAULT));

// Common color combinations
// Primary button
Colors.PRIMARY, Colors.PRIMARY_DARK

// Success button
Colors.SUCCESS, Colors.SUCCESS_DARK

// Warning button
Colors.WARNING, Colors.WARNING_DARK

// Danger button
Colors.DANGER, Colors.DANGER_DARK

// Text colors
Colors.TEXT_PRIMARY    // Main text
Colors.TEXT_SECONDARY  // Secondary text
Colors.TEXT_TERTIARY   // Tertiary text

// Background colors
Colors.BG_PRIMARY      // White
Colors.BG_SECONDARY    // Gray 50
Colors.BG_TERTIARY     // Gray 100
```

## 🔘 Buttons

### Modern Button
```java
JButton btn = ModernUIUtil.createModernButton("Submit",
    Colors.PRIMARY, Colors.PRIMARY_DARK);
btn.addActionListener(e -> {
    // Your action here
});
```

### Button Variants
```java
// Primary
JButton btnPrimary = ModernUIUtil.createModernButton("Primary",
    Colors.PRIMARY, Colors.PRIMARY_DARK);

// Success
JButton btnSuccess = ModernUIUtil.createModernButton("Success",
    Colors.SUCCESS, Colors.SUCCESS_DARK);

// Warning
JButton btnWarning = ModernUIUtil.createModernButton("Warning",
    Colors.WARNING, Colors.WARNING_DARK);

// Danger
JButton btnDanger = ModernUIUtil.createModernButton("Danger",
    Colors.DANGER, Colors.DANGER_DARK);
```

### Custom Button with Icon
```java
JButton btn = ModernUIUtil.createModernButton("📚 Tambah Buku",
    Colors.SUCCESS, Colors.SUCCESS_DARK);
btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
```

## 📝 Text Fields

### Modern TextField
```java
JTextField field = ModernUIUtil.createModernTextField("Enter name");
field.setPreferredSize(new Dimension(300, 42));
```

### Custom TextField with Focus
```java
JTextField field = new JTextField() {
    private boolean isFocused = false;
    
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
            RenderingHints.VALUE_ANTIALIAS_ON);
        
        g2.setColor(Colors.BG_PRIMARY);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
        
        super.paintComponent(g);
        g2.dispose();
    }
    
    @Override
    protected void paintBorder(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
            RenderingHints.VALUE_ANTIALIAS_ON);
        
        g2.setColor(isFocused ? Colors.PRIMARY : Colors.BORDER_DEFAULT);
        g2.setStroke(new BasicStroke(isFocused ? 2 : 1));
        g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 8, 8);
        g2.dispose();
    }
    
    {
        setFont(new Font("Segoe UI", Font.PLAIN, 14));
        setForeground(Colors.TEXT_PRIMARY);
        setOpaque(false);
        setBorder(new EmptyBorder(12, 16, 12, 16));
        
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
```

### Password Field
```java
JPasswordField passField = new JPasswordField() {
    private boolean isFocused = false;
    
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
            RenderingHints.VALUE_ANTIALIAS_ON);
        
        g2.setColor(Colors.BG_PRIMARY);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
        
        super.paintComponent(g);
        g2.dispose();
    }
    
    @Override
    protected void paintBorder(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
            RenderingHints.VALUE_ANTIALIAS_ON);
        
        g2.setColor(isFocused ? Colors.PRIMARY : Colors.BORDER_DEFAULT);
        g2.setStroke(new BasicStroke(isFocused ? 2 : 1));
        g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 8, 8);
        g2.dispose();
    }
    
    {
        setFont(new Font("Segoe UI", Font.PLAIN, 14));
        setEchoChar('●');
        setForeground(Colors.TEXT_PRIMARY);
        setOpaque(false);
        setBorder(new EmptyBorder(12, 16, 12, 16));
        
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
```

## 🎴 Cards

### Simple Card
```java
JPanel card = ModernUIUtil.createCardPanel();
card.setLayout(new BorderLayout());
card.add(new JLabel("Content"), BorderLayout.CENTER);
```

### Stat Card
```java
JPanel card = new JPanel(new BorderLayout(12, 0)) {
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
            RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Shadow
        g2.setColor(new Color(0, 0, 0, 8));
        g2.fillRoundRect(2, 4, getWidth()-4, getHeight()-4, 12, 12);
        
        // Background
        g2.setColor(Colors.BG_PRIMARY);
        g2.fillRoundRect(0, 0, getWidth()-4, getHeight()-6, 12, 12);
        
        // Accent bar
        g2.setColor(Colors.PRIMARY);
        g2.fillRoundRect(0, 0, 4, getHeight()-6, 4, 4);
        
        g2.dispose();
    }
};
card.setOpaque(false);
card.setBorder(new EmptyBorder(16, 16, 16, 16));

// Icon
JPanel icon = ModernUIUtil.createIconCircle("📚", Colors.PRIMARY, 56);

// Text
JPanel textPanel = new JPanel();
textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
textPanel.setOpaque(false);

JLabel title = new JLabel("Total Buku");
title.setFont(new Font("Segoe UI", Font.PLAIN, 13));
title.setForeground(Colors.TEXT_SECONDARY);

JLabel value = new JLabel("1,234");
value.setFont(new Font("Segoe UI", Font.BOLD, 28));
value.setForeground(Colors.TEXT_PRIMARY);

textPanel.add(title);
textPanel.add(Box.createVerticalStrut(4));
textPanel.add(value);

card.add(icon, BorderLayout.WEST);
card.add(textPanel, BorderLayout.CENTER);
```

### Header Panel with Gradient
```java
JPanel header = new JPanel(new BorderLayout()) {
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
            RenderingHints.VALUE_ANTIALIAS_ON);
        
        GradientPaint gp = new GradientPaint(
            0, 0, Colors.PRIMARY,
            getWidth(), 0, Colors.PRIMARY_DARK
        );
        g2.setPaint(gp);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);
        g2.dispose();
    }
};
header.setOpaque(false);
header.setBorder(new EmptyBorder(20, 24, 20, 24));

JLabel title = new JLabel("📖 Manajemen Buku");
title.setFont(new Font("Segoe UI", Font.BOLD, 20));
title.setForeground(Color.WHITE);

JLabel subtitle = new JLabel("Kelola koleksi buku perpustakaan");
subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 12));
subtitle.setForeground(new Color(255, 255, 255, 200));

JPanel textPanel = new JPanel(new GridLayout(2, 1, 0, 4));
textPanel.setOpaque(false);
textPanel.add(title);
textPanel.add(subtitle);

header.add(textPanel, BorderLayout.WEST);
```

## 📊 Tables

### Modern Table Styling
```java
private void styleModernTable(JTable table) {
    // Basic styling
    table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
    table.setRowHeight(40);
    table.setShowHorizontalLines(true);
    table.setShowVerticalLines(false);
    table.setGridColor(Colors.BORDER_LIGHT);
    table.setSelectionBackground(new Color(59, 130, 246, 30));
    table.setSelectionForeground(Colors.TEXT_PRIMARY);
    table.setIntercellSpacing(new Dimension(0, 1));
    
    // Header styling
    JTableHeader header = table.getTableHeader();
    header.setFont(new Font("Segoe UI", Font.BOLD, 13));
    header.setBackground(Colors.BG_PRIMARY);
    header.setForeground(Colors.TEXT_PRIMARY);
    header.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, 
        Colors.BORDER_DEFAULT));
    header.setPreferredSize(new Dimension(header.getWidth(), 44));
    header.setReorderingAllowed(false);
    
    // Cell renderer
    table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
        @Override
        public Component getTableCellRendererComponent(JTable table, 
                Object value, boolean isSelected, boolean hasFocus, 
                int row, int column) {
            super.getTableCellRendererComponent(table, value, 
                isSelected, hasFocus, row, column);
            
            setBorder(new EmptyBorder(8, 12, 8, 12));
            
            if (!isSelected) {
                setBackground(row % 2 == 0 ? 
                    Colors.BG_PRIMARY : Colors.BG_SECONDARY);
                setForeground(Colors.TEXT_PRIMARY);
            }
            
            return this;
        }
    });
}
```

### Modern ScrollPane
```java
JScrollPane scrollPane = new JScrollPane(table);
ModernUIUtil.styleModernScrollBar(scrollPane);
scrollPane.setBorder(BorderFactory.createLineBorder(Colors.BORDER_LIGHT));
```

## 📱 Responsive

### Setup Responsive
```java
private ResponsiveUtil.ScreenSize currentScreenSize;

private void setupResponsive() {
    currentScreenSize = ResponsiveUtil.getCurrentScreenSize(this);
    
    addComponentListener(new ComponentAdapter() {
        @Override
        public void componentResized(ComponentEvent e) {
            ResponsiveUtil.ScreenSize newSize = 
                ResponsiveUtil.getCurrentScreenSize(MyPanel.this);
            
            if (newSize != currentScreenSize) {
                currentScreenSize = newSize;
                adjustLayout(newSize);
            }
        }
    });
}

private void adjustLayout(ResponsiveUtil.ScreenSize size) {
    if (ResponsiveUtil.shouldUseVerticalLayout(size)) {
        // Mobile: stack vertically
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    } else {
        // Desktop: side by side
        setLayout(new BorderLayout(16, 0));
    }
    
    // Adjust font sizes
    int fontSize = ResponsiveUtil.scaleFontSize(14, size);
    updateFontSizes(fontSize);
    
    // Adjust spacing
    int spacing = ResponsiveUtil.scaleSpacing(16, size);
    updateSpacing(spacing);
    
    revalidate();
    repaint();
}
```

### Responsive Font Scaling
```java
private void updateFontSizes(int baseSize) {
    for (Component comp : getComponents()) {
        if (comp instanceof JLabel) {
            JLabel label = (JLabel) comp;
            Font currentFont = label.getFont();
            label.setFont(currentFont.deriveFont((float) baseSize));
        }
    }
}
```

## 🎬 Animations

### Fade In
```java
AnimationUtil.fadeIn(component, 300);
```

### Slide In
```java
AnimationUtil.slideInFromLeft(component, 400);
```

### Smooth Scroll
```java
AnimationUtil.smoothScrollTo(scrollPane, targetY, true);
```

### Pulse Animation
```java
AnimationUtil.pulseAnimation(component, 3);
```

### Button Click Animation
```java
button.addActionListener(e -> {
    AnimationUtil.bounceButton(button);
    // Your action here
});
```

## 📦 Complete Examples

### Complete Form Panel
```java
public class ModernFormPanel extends JPanel {
    private ResponsiveUtil.ScreenSize currentScreenSize;
    
    public ModernFormPanel() {
        setLayout(new BorderLayout(16, 16));
        setBackground(Colors.BG_SECONDARY);
        setBorder(new EmptyBorder(24, 24, 24, 24));
        
        initComponents();
        setupResponsive();
    }
    
    private void initComponents() {
        // Header
        add(createHeader(), BorderLayout.NORTH);
        
        // Content
        JPanel content = new JPanel(new BorderLayout(16, 0));
        content.setBackground(Colors.BG_SECONDARY);
        
        // Form (left)
        JPanel formPanel = createFormPanel();
        
        // Table (right)
        JPanel tablePanel = createTablePanel();
        
        content.add(formPanel, BorderLayout.WEST);
        content.add(tablePanel, BorderLayout.CENTER);
        
        add(content, BorderLayout.CENTER);
    }
    
    private JPanel createHeader() {
        JPanel header = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
                    RenderingHints.VALUE_ANTIALIAS_ON);
                
                GradientPaint gp = new GradientPaint(
                    0, 0, Colors.PRIMARY,
                    getWidth(), 0, Colors.PRIMARY_DARK
                );
                g2.setPaint(gp);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);
                g2.dispose();
            }
        };
        header.setOpaque(false);
        header.setBorder(new EmptyBorder(20, 24, 20, 24));
        
        JLabel title = new JLabel("Form Title");
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        title.setForeground(Color.WHITE);
        
        header.add(title, BorderLayout.WEST);
        return header;
    }
    
    private JPanel createFormPanel() {
        JPanel panel = ModernUIUtil.createCardPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setPreferredSize(new Dimension(320, 0));
        
        // Add form fields here
        
        return panel;
    }
    
    private JPanel createTablePanel() {
        JPanel panel = ModernUIUtil.createCardPanel();
        panel.setLayout(new BorderLayout());
        
        // Add table here
        
        return panel;
    }
    
    private void setupResponsive() {
        currentScreenSize = ResponsiveUtil.getCurrentScreenSize(this);
        
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                ResponsiveUtil.ScreenSize newSize = 
                    ResponsiveUtil.getCurrentScreenSize(ModernFormPanel.this);
                
                if (newSize != currentScreenSize) {
                    currentScreenSize = newSize;
                    adjustLayout(newSize);
                }
            }
        });
    }
    
    private void adjustLayout(ResponsiveUtil.ScreenSize size) {
        // Adjust layout based on screen size
        revalidate();
        repaint();
    }
}
```

### Complete Stat Card
```java
public class StatCard extends JPanel {
    private String title;
    private String value;
    private String icon;
    private Color accentColor;
    
    public StatCard(String title, String value, String icon, Color accentColor) {
        this.title = title;
        this.value = value;
        this.icon = icon;
        this.accentColor = accentColor;
        
        setLayout(new BorderLayout(12, 0));
        setOpaque(false);
        setBorder(new EmptyBorder(16, 16, 16, 16));
        
        initComponents();
    }
    
    private void initComponents() {
        // Icon
        JPanel iconPanel = ModernUIUtil.createIconCircle(icon, accentColor, 56);
        
        // Text
        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setOpaque(false);
        
        JLabel lblTitle = new JLabel(title);
        lblTitle.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblTitle.setForeground(Colors.TEXT_SECONDARY);
        
        JLabel lblValue = new JLabel(value);
        lblValue.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblValue.setForeground(Colors.TEXT_PRIMARY);
        
        textPanel.add(lblTitle);
        textPanel.add(Box.createVerticalStrut(4));
        textPanel.add(lblValue);
        
        add(iconPanel, BorderLayout.WEST);
        add(textPanel, BorderLayout.CENTER);
        
        // Hover effect
        ModernUIUtil.addHoverEffect(this, 
            Colors.BG_PRIMARY, Colors.BG_SECONDARY);
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
            RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Shadow
        g2.setColor(new Color(0, 0, 0, 8));
        g2.fillRoundRect(2, 4, getWidth()-4, getHeight()-4, 12, 12);
        
        // Background
        g2.setColor(Colors.BG_PRIMARY);
        g2.fillRoundRect(0, 0, getWidth()-4, getHeight()-6, 12, 12);
        
        // Accent bar
        g2.setColor(accentColor);
        g2.fillRoundRect(0, 0, 4, getHeight()-6, 4, 4);
        
        g2.dispose();
    }
    
    // Getters and setters
    public void setValue(String value) {
        this.value = value;
        repaint();
    }
}

// Usage:
StatCard card = new StatCard("Total Buku", "1,234", "📚", Colors.PRIMARY);
panel.add(card);
```

---

**Copy, paste, and customize! 🚀**
