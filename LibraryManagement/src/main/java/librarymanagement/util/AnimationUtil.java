package librarymanagement.util;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * AnimationUtil - Utility untuk animasi smooth pada komponen UI
 */
public class AnimationUtil {
    
    /**
     * Fade in animation untuk komponen
     */
    public static void fadeIn(JComponent component, int duration) {
        component.setVisible(true);
        Timer timer = new Timer(10, null);
        final float[] alpha = {0.0f};
        final float increment = 1.0f / (duration / 10);
        
        timer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                alpha[0] += increment;
                if (alpha[0] >= 1.0f) {
                    alpha[0] = 1.0f;
                    timer.stop();
                }
                component.repaint();
            }
        });
        timer.start();
    }
    
    /**
     * Slide in animation dari kiri
     */
    public static void slideInFromLeft(JComponent component, int duration) {
        final int targetX = component.getX();
        final int startX = targetX - 200;
        component.setLocation(startX, component.getY());
        component.setVisible(true);
        
        Timer timer = new Timer(10, null);
        final int[] currentX = {startX};
        final int increment = (targetX - startX) / (duration / 10);
        
        timer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentX[0] += increment;
                if (currentX[0] >= targetX) {
                    currentX[0] = targetX;
                    timer.stop();
                }
                component.setLocation(currentX[0], component.getY());
            }
        });
        timer.start();
    }
    
    /**
     * Smooth color transition
     */
    public static void transitionColor(JComponent component, Color from, Color to, int duration, ColorSetter setter) {
        Timer timer = new Timer(10, null);
        final int steps = duration / 10;
        final int[] currentStep = {0};
        
        timer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentStep[0]++;
                float ratio = (float) currentStep[0] / steps;
                
                int red = (int) (from.getRed() + ratio * (to.getRed() - from.getRed()));
                int green = (int) (from.getGreen() + ratio * (to.getGreen() - from.getGreen()));
                int blue = (int) (from.getBlue() + ratio * (to.getBlue() - from.getBlue()));
                
                Color current = new Color(red, green, blue);
                setter.setColor(current);
                component.repaint();
                
                if (currentStep[0] >= steps) {
                    timer.stop();
                }
            }
        });
        timer.start();
    }
    
    /**
     * Interface untuk set color
     */
    public interface ColorSetter {
        void setColor(Color color);
    }
    
    /**
     * Bounce effect untuk button
     */
    public static void bounceButton(JButton button) {
        final Dimension originalSize = button.getSize();
        final int maxScale = 5;
        
        Timer timer = new Timer(20, null);
        final int[] step = {0};
        final boolean[] growing = {true};
        
        timer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (growing[0]) {
                    step[0]++;
                    if (step[0] >= maxScale) growing[0] = false;
                } else {
                    step[0]--;
                    if (step[0] <= 0) {
                        timer.stop();
                        button.setSize(originalSize);
                        button.revalidate();
                        return;
                    }
                }
                
                int newWidth = originalSize.width + step[0];
                int newHeight = originalSize.height + step[0];
                button.setSize(newWidth, newHeight);
                button.revalidate();
            }
        });
        timer.start();
    }
    
    /**
     * Ripple effect untuk button click
     */
    public static void rippleEffect(JComponent component, Point clickPoint) {
        final int maxRadius = Math.max(component.getWidth(), component.getHeight());
        final int[] currentRadius = {0};
        final int[] alpha = {255};
        
        Timer timer = new Timer(15, null);
        timer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentRadius[0] += maxRadius / 20;
                alpha[0] -= 15;
                
                if (currentRadius[0] >= maxRadius || alpha[0] <= 0) {
                    timer.stop();
                }
                
                component.repaint();
            }
        });
        timer.start();
    }
    
    /**
     * Smooth scroll ke posisi tertentu
     */
    public static void smoothScrollTo(JScrollPane scrollPane, int targetValue, boolean vertical) {
        JScrollBar scrollBar = vertical ? scrollPane.getVerticalScrollBar() : scrollPane.getHorizontalScrollBar();
        final int startValue = scrollBar.getValue();
        final int distance = targetValue - startValue;
        final int duration = 300; // ms
        final int steps = duration / 10;
        
        Timer timer = new Timer(10, null);
        final int[] currentStep = {0};
        
        timer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentStep[0]++;
                float ratio = easeInOutQuad((float) currentStep[0] / steps);
                int newValue = startValue + (int) (distance * ratio);
                scrollBar.setValue(newValue);
                
                if (currentStep[0] >= steps) {
                    scrollBar.setValue(targetValue);
                    timer.stop();
                }
            }
        });
        timer.start();
    }
    
    /**
     * Easing function untuk animasi yang lebih natural
     */
    private static float easeInOutQuad(float t) {
        return t < 0.5f ? 2 * t * t : -1 + (4 - 2 * t) * t;
    }
    
    /**
     * Pulse animation untuk notifikasi
     */
    public static void pulseAnimation(JComponent component, int cycles) {
        final float[] scale = {1.0f};
        final boolean[] growing = {true};
        final int[] cycleCount = {0};
        
        Timer timer = new Timer(30, null);
        timer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (growing[0]) {
                    scale[0] += 0.05f;
                    if (scale[0] >= 1.1f) {
                        scale[0] = 1.1f;
                        growing[0] = false;
                    }
                } else {
                    scale[0] -= 0.05f;
                    if (scale[0] <= 1.0f) {
                        scale[0] = 1.0f;
                        growing[0] = true;
                        cycleCount[0]++;
                        
                        if (cycleCount[0] >= cycles) {
                            timer.stop();
                        }
                    }
                }
                component.repaint();
            }
        });
        timer.start();
    }
}
