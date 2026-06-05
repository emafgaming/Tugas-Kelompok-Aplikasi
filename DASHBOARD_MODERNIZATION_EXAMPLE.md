# Contoh Modernisasi Dashboard

## Overview
Dokumen ini menjelaskan cara mengupdate Dashboard menjadi modern dan responsif.

## Perubahan Utama Dashboard

### 1. Sidebar yang Responsif

```java
// Sidebar akan menyesuaikan lebar berdasarkan screen size
private void updateSidebarWidth(ResponsiveUtil.ScreenSize screenSize) {
    int width = ResponsiveUtil.getSidebarWidth(screenSize);
    sidebarPanel.setPreferredSize(new Dimension(width, getHeight()));
    
    // Untuk mobile, sidebar bisa collapse menjadi icon-only
    if (screenSize == ResponsiveUtil.ScreenSize.MOBILE) {
        showIconOnlySidebar();
    } else {
        showFullSidebar();
    }
}
```

### 2. Modern Sidebar Button

```java
private JButton createModernMenuButton(String icon, String text, String panelName) {
    JButton btn = new JButton() {
        private boolean isActive = false;
        private boolean isHovered = false;
        
        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
                RenderingHints.VALUE_ANTIALIAS_ON);
            
            // Background
            if (isActive) {
                g2.setColor(ModernUIUtil.Colors.PRIMARY);
                g2.fillRoundRect(8, 4, getWidth() - 16, getHeight() - 8, 8, 8);
            } else if (isHovered) {
                g2.setColor(new Color(255, 255, 255, 10));
                g2.fillRoundRect(8, 4, getWidth() - 16, getHeight() - 8, 8, 8);
            }
            
            // Icon
            g2.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 18));
            g2.setColor(Color.WHITE);
            g2.drawString(icon, 20, getHeight() / 2 + 6);
            
            // Text
            g2.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            g2.drawString(text, 50, getHeight() / 2 + 5);
            
            g2.dispose();
        }
        
        {
            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    isHovered = true;
                    repaint();
                }
                
                @Override
                public void mouseExited(MouseEvent e) {
                    isHovered = false;
                    repaint();
                }
            });
        }
    };
    
    btn.setOpaque(false);
    btn.setContentAreaFilled(false);
    btn.setBorderPainted(false);
    btn.setFocusPainted(false);
    btn.setMaximumSize(new Dimension(260, 48));
    btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
    
    return btn;
}
```

### 3. Modern Welcome Panel dengan Stats Cards

```java
private JPanel createModernWelcomePanel() {
    JPanel panel = new JPanel();
    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
    panel.setBackground(ModernUIUtil.Colors.BG_SECONDARY);
    panel.setBorder(new EmptyBorder(24, 24, 24, 24));
    
    // Header
    JPanel header = createWelcomeHeader();
    header.setAlignmentX(Component.LEFT_ALIGNMENT);
    panel.add(header);
    panel.add(Box.createVerticalStrut(24));
    
    // Stats Grid
    JPanel statsGrid = new JPanel(new GridLayout(2, 2, 16, 16));
    statsGrid.setBackground(ModernUIUtil.Colors.BG_SECONDARY);
    statsGrid.setMaximumSize(new Dimension(Integer.MAX_VALUE, 240));
    statsGrid.setAlignmentX(Component.LEFT_ALIGNMENT);
    
    statsGrid.add(createStatCard("Total Buku", "1,234", "📚", 
        ModernUIUtil.Colors.PRIMARY));
    statsGrid.add(createStatCard("Anggota Aktif", "567", "👥", 
        ModernUIUtil.Colors.SUCCESS));
    statsGrid.add(createStatCard("Dipinjam", "89", "📋", 
        ModernUIUtil.Colors.WARNING));
    statsGrid.add(createStatCard("Terlambat", "12", "⚠️", 
        ModernUIUtil.Colors.DANGER));
    
    panel.add(statsGrid);
    panel.add(Box.createVerticalStrut(24));
    
    // Quick Actions
    JPanel quickActions = createQuickActionsPanel();
    quickActions.setAlignmentX(Component.LEFT_ALIGNMENT);
    panel.add(quickActions);
    
    return panel;
}

private JPanel createStatCard(String title, String value, String icon, Color color) {
    JPanel card = new JPanel(new BorderLayout(12, 0)) {
        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
                RenderingHints.VALUE_ANTIALIAS_ON);
            
            // Shadow
            g2.setColor(new Color(0, 0, 0, 8));
            g2.fillRoundRect(2, 4, getWidth() - 4, getHeight() - 4, 12, 12);
            
            // Background
            g2.setColor(ModernUIUtil.Colors.BG_PRIMARY);
            g2.fillRoundRect(0, 0, getWidth() - 4, getHeight() - 6, 12, 12);
            
            // Accent bar
            g2.setColor(color);
            g2.fillRoundRect(0, 0, 4, getHeight() - 6, 4, 4);
            
            g2.dispose();
        }
    };
    card.setOpaque(false);
    card.setBorder(new EmptyBorder(16, 16, 16, 16));
    
    // Icon circle
    JPanel iconPanel = ModernUIUtil.createIconCircle(icon, color, 56);
    
    // Text panel
    JPanel textPanel = new JPanel();
    textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
    textPanel.setOpaque(false);
    
    JLabel lblTitle = new JLabel(title);
    lblTitle.setFont(new Font("Segoe UI", Font.PLAIN, 13));
    lblTitle.setForeground(ModernUIUtil.Colors.TEXT_SECONDARY);
    
    JLabel lblValue = new JLabel(value);
    lblValue.setFont(new Font("Segoe UI", Font.BOLD, 28));
    lblValue.setForeground(ModernUIUtil.Colors.TEXT_PRIMARY);
    
    textPanel.add(lblTitle);
    textPanel.add(Box.createVerticalStrut(4));
    textPanel.add(lblValue);
    
    card.add(iconPanel, BorderLayout.WEST);
    card.add(textPanel, BorderLayout.CENTER);
    
    // Hover effect
    ModernUIUtil.addHoverEffect(card, 
        ModernUIUtil.Colors.BG_PRIMARY, 
        ModernUIUtil.Colors.BG_SECONDARY);
    
    return card;
}
```

### 4. Responsive Content Area

```java
private void adjustContentLayout(ResponsiveUtil.ScreenSize screenSize) {
    // Adjust form panel width
    int formWidth = ResponsiveUtil.getFormPanelWidth(screenSize);
    
    if (formWidth == 0) {
        // Mobile: Full width, stack vertically
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
    } else {
        // Desktop/Tablet: Side by side
        contentPanel.setLayout(new BorderLayout(16, 0));
    }
    
    // Adjust font sizes
    adjustFontSizes(screenSize);
    
    // Adjust spacing
    adjustSpacing(screenSize);
    
    contentPanel.revalidate();
    contentPanel.repaint();
}

private void adjustFontSizes(ResponsiveUtil.ScreenSize screenSize) {
    // Update all labels and text components
    updateComponentFonts(contentPanel, screenSize);
}

private void updateComponentFonts(Container container, ResponsiveUtil.ScreenSize screenSize) {
    for (Component comp : container.getComponents()) {
        if (comp instanceof JLabel) {
            JLabel label = (JLabel) comp;
            Font currentFont = label.getFont();
            int newSize = ResponsiveUtil.scaleFontSize(currentFont.getSize(), screenSize);
            label.setFont(currentFont.deriveFont((float) newSize));
        } else if (comp instanceof Container) {
            updateComponentFonts((Container) comp, screenSize);
        }
    }
}
```

### 5. Modern Table Styling

```java
private void styleModernTable(JTable table) {
    // Basic styling
    table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
    table.setRowHeight(40);
    table.setShowHorizontalLines(true);
    table.setShowVerticalLines(false);
    table.setGridColor(ModernUIUtil.Colors.BORDER_LIGHT);
    table.setSelectionBackground(new Color(59, 130, 246, 30));
    table.setSelectionForeground(ModernUIUtil.Colors.TEXT_PRIMARY);
    table.setIntercellSpacing(new Dimension(0, 1));
    
    // Header styling
    JTableHeader header = table.getTableHeader();
    header.setFont(new Font("Segoe UI", Font.BOLD, 13));
    header.setBackground(ModernUIUtil.Colors.BG_PRIMARY);
    header.setForeground(ModernUIUtil.Colors.TEXT_PRIMARY);
    header.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, 
        ModernUIUtil.Colors.BORDER_DEFAULT));
    header.setPreferredSize(new Dimension(header.getWidth(), 44));
    
    // Custom cell renderer
    table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            
            setBorder(new EmptyBorder(8, 12, 8, 12));
            
            if (!isSelected) {
                setBackground(row % 2 == 0 ? 
                    ModernUIUtil.Colors.BG_PRIMARY : 
                    ModernUIUtil.Colors.BG_SECONDARY);
                setForeground(ModernUIUtil.Colors.TEXT_PRIMARY);
            }
            
            return this;
        }
    });
}
```

### 6. Smooth Panel Transitions

```java
private void showPanelWithAnimation(String panelName) {
    // Fade out current panel
    Component currentPanel = getCurrentVisiblePanel();
    if (currentPanel != null) {
        AnimationUtil.fadeOut(currentPanel, 200, () -> {
            // Switch panel
            cardLayout.show(contentPanel, panelName);
            
            // Fade in new panel
            Component newPanel = getCurrentVisiblePanel();
            if (newPanel != null) {
                AnimationUtil.fadeIn(newPanel, 200);
            }
        });
    } else {
        cardLayout.show(contentPanel, panelName);
    }
}

private Component getCurrentVisiblePanel() {
    for (Component comp : contentPanel.getComponents()) {
        if (comp.isVisible()) {
            return comp;
        }
    }
    return null;
}
```

### 7. Mobile-Friendly Navigation

```java
private void createMobileNavigation() {
    // Hamburger menu button
    JButton btnMenu = new JButton("☰") {
        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
                RenderingHints.VALUE_ANTIALIAS_ON);
            
            if (getModel().isPressed()) {
                g2.setColor(ModernUIUtil.Colors.PRIMARY_DARK);
            } else if (getModel().isRollover()) {
                g2.setColor(ModernUIUtil.Colors.PRIMARY);
            } else {
                g2.setColor(ModernUIUtil.Colors.PRIMARY);
            }
            
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
            
            // Draw hamburger icon
            g2.setColor(Color.WHITE);
            g2.setStroke(new BasicStroke(2));
            int centerY = getHeight() / 2;
            g2.drawLine(12, centerY - 6, getWidth() - 12, centerY - 6);
            g2.drawLine(12, centerY, getWidth() - 12, centerY);
            g2.drawLine(12, centerY + 6, getWidth() - 12, centerY + 6);
            
            g2.dispose();
        }
    };
    
    btnMenu.setPreferredSize(new Dimension(48, 48));
    btnMenu.setOpaque(false);
    btnMenu.setContentAreaFilled(false);
    btnMenu.setBorderPainted(false);
    btnMenu.setCursor(new Cursor(Cursor.HAND_CURSOR));
    
    btnMenu.addActionListener(e -> toggleMobileSidebar());
}

private void toggleMobileSidebar() {
    if (sidebarPanel.isVisible()) {
        // Slide out
        AnimationUtil.slideOut(sidebarPanel, 300, () -> {
            sidebarPanel.setVisible(false);
        });
    } else {
        // Slide in
        sidebarPanel.setVisible(true);
        AnimationUtil.slideInFromLeft(sidebarPanel, 300);
    }
}
```

## Implementation Checklist

Untuk mengupdate Dashboard yang ada:

- [ ] Import utility classes baru
- [ ] Update color constants
- [ ] Implement responsive sidebar
- [ ] Update menu buttons dengan modern style
- [ ] Create modern welcome panel dengan stats cards
- [ ] Update table styling
- [ ] Add smooth transitions
- [ ] Implement mobile navigation
- [ ] Add responsive listener
- [ ] Test pada berbagai screen sizes
- [ ] Optimize performance

## Testing

Test pada resolusi berikut:
- 1920x1080 (Desktop Large)
- 1366x768 (Laptop)
- 1024x768 (Tablet Landscape)
- 768x1024 (Tablet Portrait)
- 375x667 (Mobile)

## Performance Tips

1. **Lazy Load Panels**: Load form panels hanya saat dibutuhkan
2. **Cache Painted Components**: Cache gradient dan complex graphics
3. **Debounce Resize Events**: Jangan update layout terlalu sering
4. **Use CardLayout**: Efficient untuk switching panels
5. **Dispose Resources**: Remove listeners saat panel tidak digunakan

## Next Steps

1. Apply perubahan ini ke Dashboard.java
2. Test thoroughly
3. Update form panels lainnya dengan pattern yang sama
4. Document any issues atau improvements
