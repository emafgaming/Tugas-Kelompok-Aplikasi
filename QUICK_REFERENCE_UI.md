# Quick Reference - Modern UI Components

## 🎨 Colors

```java
import librarymanagement.util.ModernUIUtil.Colors;

// Primary
Colors.PRIMARY          // #3B82F6
Colors.PRIMARY_DARK     // #2563EB
Colors.PRIMARY_LIGHT    // #60A5FA

// Semantic
Colors.SUCCESS          // #22C55E (Green)
Colors.WARNING          // #FB923C (Orange)
Colors.DANGER           // #EF4444 (Red)
Colors.INFO             // #0EA5E9 (Sky)

// Neutral
Colors.GRAY_50 to GRAY_900  // 10 shades
Colors.BG_PRIMARY       // White
Colors.BG_SECONDARY     // Gray 50
Colors.TEXT_PRIMARY     // Gray 900
Colors.TEXT_SECONDARY   // Gray 600
Colors.BORDER_DEFAULT   // Gray 300
```

## 🔘 Buttons

```java
// Primary Button
JButton btn = ModernUIUtil.createModernButton("Submit",
    Colors.PRIMARY, Colors.PRIMARY_DARK);

// Success Button
JButton btn = ModernUIUtil.createModernButton("Save",
    Colors.SUCCESS, Colors.SUCCESS_DARK);

// Danger Button
JButton btn = ModernUIUtil.createModernButton("Delete",
    Colors.DANGER, Colors.DANGER_DARK);

// Warning Button
JButton btn = ModernUIUtil.createModernButton("Update",
    Colors.WARNING, Colors.WARNING_DARK);
```

## 📝 Text Fields

```java
// Modern TextField
JTextField field = ModernUIUtil.createModernTextField("Placeholder");

// Custom TextField with focus state
JTextField field = new JTextField() {
    private boolean isFocused = false;
    
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
        addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) { 
                isFocused = true; repaint(); 
            }
            public void focusLost(FocusEvent e) { 
                isFocused = false; repaint(); 
            }
        });
    }
};
```

## 🎴 Cards

```java
// Simple Card
JPanel card = ModernUIUtil.createCardPanel();
card.setLayout(new BorderLayout());
card.add(new JLabel("Content"), BorderLayout.CENTER);

// Card with custom painting
JPanel card = new JPanel() {
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
        
        g2.dispose();
    }
};
```

## 🏷️ Badges

```java
// Success Badge
JLabel badge = ModernUIUtil.createBadge("Active", Colors.SUCCESS);

// Warning Badge
JLabel badge = ModernUIUtil.createBadge("Pending", Colors.WARNING);

// Danger Badge
JLabel badge = ModernUIUtil.createBadge("Overdue", Colors.DANGER);

// Info Badge
JLabel badge = ModernUIUtil.createBadge("New", Colors.INFO);
```

## 🎯 Icons

```java
// Icon Circle
JPanel icon = ModernUIUtil.createIconCircle("📚", Colors.PRIMARY, 48);

// Custom Icon
JLabel icon = new JLabel("📚");
icon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 24));
```

## 📏 Responsive

```java
// Get current screen size
ResponsiveUtil.ScreenSize size = ResponsiveUtil.getCurrentScreenSize(component);

// Scale font
int fontSize = ResponsiveUtil.scaleFontSize(14, size);

// Scale spacing
int spacing = ResponsiveUtil.scaleSpacing(20, size);

// Get sidebar width
int width = ResponsiveUtil.getSidebarWidth(size);

// Check if mobile
boolean isMobile = ResponsiveUtil.shouldUseVerticalLayout(size);

// Add responsive listener
ResponsiveUtil.addResponsiveListener(frame, newSize -> {
    adjustLayout(newSize);
});
```

## 🎬 Animations

```java
// Fade In
AnimationUtil.fadeIn(component, 300);

// Slide In
AnimationUtil.slideInFromLeft(component, 400);

// Color Transition
AnimationUtil.transitionColor(component, fromColor, toColor, 300, 
    color -> component.setBackground(color));

// Bounce
AnimationUtil.bounceButton(button);

// Pulse
AnimationUtil.pulseAnimation(component, 3);

// Smooth Scroll
AnimationUtil.smoothScrollTo(scrollPane, targetY, true);
```

## 📊 Tables

```java
// Style modern table
private void styleModernTable(JTable table) {
    table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
    table.setRowHeight(40);
    table.setShowHorizontalLines(true);
    table.setShowVerticalLines(false);
    table.setGridColor(Colors.BORDER_LIGHT);
    table.setSelectionBackground(new Color(59, 130, 246, 30));
    table.setSelectionForeground(Colors.TEXT_PRIMARY);
    
    // Header
    JTableHeader header = table.getTableHeader();
    header.setFont(new Font("Segoe UI", Font.BOLD, 13));
    header.setBackground(Colors.BG_PRIMARY);
    header.setForeground(Colors.TEXT_PRIMARY);
    header.setPreferredSize(new Dimension(header.getWidth(), 44));
    
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

## 📜 ScrollPane

```java
// Modern scrollbar
ModernUIUtil.styleModernScrollBar(scrollPane);

// Or manual
scrollPane.setBorder(null);
scrollPane.getVerticalScrollBar().setUnitIncrement(16);
scrollPane.getHorizontalScrollBar().setUnitIncrement(16);
```

## 🎨 Gradients

```java
// Horizontal gradient
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

// Vertical gradient
GradientPaint gp = new GradientPaint(
    0, 0, Colors.PRIMARY,
    0, getHeight(), Colors.PRIMARY_DARK
);
```

## 🔲 Borders

```java
// Rounded border
setBorder(BorderFactory.createCompoundBorder(
    BorderFactory.createLineBorder(Colors.BORDER_DEFAULT),
    new EmptyBorder(16, 16, 16, 16)
));

// Custom rounded border
@Override
protected void paintBorder(Graphics g) {
    Graphics2D g2 = (Graphics2D) g.create();
    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
        RenderingHints.VALUE_ANTIALIAS_ON);
    
    g2.setColor(Colors.BORDER_DEFAULT);
    g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 12, 12);
    
    g2.dispose();
}
```

## 📐 Layouts

```java
// Responsive layout
if (ResponsiveUtil.shouldUseVerticalLayout(screenSize)) {
    setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
} else {
    setLayout(new BorderLayout(16, 0));
}

// Grid with gap
setLayout(new GridLayout(2, 2, 16, 16));

// FlowLayout with gap
setLayout(new FlowLayout(FlowLayout.LEFT, 16, 16));
```

## 🎯 Hover Effects

```java
// Add hover effect to panel
ModernUIUtil.addHoverEffect(panel, 
    Colors.BG_PRIMARY, 
    Colors.BG_SECONDARY);

// Custom hover
panel.addMouseListener(new MouseAdapter() {
    @Override
    public void mouseEntered(MouseEvent e) {
        panel.setBackground(Colors.BG_SECONDARY);
        panel.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }
    
    @Override
    public void mouseExited(MouseEvent e) {
        panel.setBackground(Colors.BG_PRIMARY);
        panel.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }
});
```

## 📱 Mobile Detection

```java
// Check if mobile
ResponsiveUtil.ScreenSize size = ResponsiveUtil.getCurrentScreenSize(this);
if (size == ResponsiveUtil.ScreenSize.MOBILE) {
    // Mobile-specific code
}

// Or use helper
if (ResponsiveUtil.shouldUseVerticalLayout(size)) {
    // Stack vertically
}
```

## 🎨 Typography

```java
// Heading 1
label.setFont(new Font("Segoe UI", Font.BOLD, 32));
label.setForeground(Colors.TEXT_PRIMARY);

// Heading 2
label.setFont(new Font("Segoe UI", Font.BOLD, 24));

// Heading 3
label.setFont(new Font("Segoe UI", Font.BOLD, 18));

// Body
label.setFont(new Font("Segoe UI", Font.PLAIN, 14));
label.setForeground(Colors.TEXT_PRIMARY);

// Caption
label.setFont(new Font("Segoe UI", Font.PLAIN, 11));
label.setForeground(Colors.TEXT_SECONDARY);
```

## 🔧 Common Patterns

### Header Panel
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
```

### Stat Card
```java
JPanel card = new JPanel(new BorderLayout(12, 0));
card.setOpaque(false);
card.setBorder(new EmptyBorder(16, 16, 16, 16));

JPanel icon = ModernUIUtil.createIconCircle("📚", Colors.PRIMARY, 56);

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

### Form Field
```java
JPanel fieldPanel = new JPanel(new BorderLayout(0, 8));
fieldPanel.setOpaque(false);

JLabel label = new JLabel("Username");
label.setFont(new Font("Segoe UI", Font.BOLD, 13));
label.setForeground(Colors.TEXT_PRIMARY);

JTextField field = ModernUIUtil.createModernTextField("Enter username");

fieldPanel.add(label, BorderLayout.NORTH);
fieldPanel.add(field, BorderLayout.CENTER);
```

## 💡 Tips

1. **Always use antialiasing** untuk custom painting
2. **Use EmptyBorder** untuk padding internal
3. **Set opaque(false)** untuk custom painted components
4. **Use SwingUtilities.invokeLater** untuk UI updates
5. **Dispose Graphics2D** setelah digunakan
6. **Test pada berbagai screen sizes**
7. **Use consistent spacing** (8, 12, 16, 24px)
8. **Use consistent border radius** (8, 12px)

## 🚀 Quick Start Template

```java
import librarymanagement.util.ModernUIUtil;
import librarymanagement.util.ResponsiveUtil;
import librarymanagement.util.AnimationUtil;
import javax.swing.*;
import java.awt.*;

public class ModernPanel extends JPanel {
    private ResponsiveUtil.ScreenSize currentScreenSize;
    
    public ModernPanel() {
        setLayout(new BorderLayout(16, 16));
        setBackground(ModernUIUtil.Colors.BG_SECONDARY);
        setBorder(new EmptyBorder(24, 24, 24, 24));
        
        initComponents();
        setupResponsive();
    }
    
    private void initComponents() {
        // Your components here
    }
    
    private void setupResponsive() {
        currentScreenSize = ResponsiveUtil.getCurrentScreenSize(this);
        
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                ResponsiveUtil.ScreenSize newSize = 
                    ResponsiveUtil.getCurrentScreenSize(ModernPanel.this);
                
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

---

**Happy Coding! 🎨**
