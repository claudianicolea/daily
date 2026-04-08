package ui.elements;

import javax.swing.*;
import javax.swing.border.AbstractBorder;
import java.awt.*;
import java.awt.event.ActionListener;

import static main.Main.settings;

public class Button extends JButton {
    public Button(String text, ActionListener e) {
        super(text);

        setForeground(Color.BLACK);
        setBackground(Color.WHITE);
        setFont(new Font("Lucida Grande", Font.PLAIN, 15));

        // remove default button borders
        setFocusPainted(false);
        setContentAreaFilled(false);

        // rounded border
        setOpaque(true);
        setBorder(new RoundedBorder(10));

        addActionListener(e);
    }

    public Button(String text, ActionListener e, boolean alignCenter) {
        super(text);

        setForeground(Color.BLACK);
        setBackground(Color.WHITE);
        setFont(new Font("Lucida Grande", Font.PLAIN, 15));

        // remove default button borders
        setFocusPainted(false);
        setContentAreaFilled(false);

        // rounded border
        setOpaque(true);
        setBorder(new RoundedBorder(10));

        addActionListener(e);
        if (alignCenter) {
            setAlignmentX(CENTER_ALIGNMENT);
        }
    }

    public Button(String text, ActionListener e, boolean alignCenter, boolean isDefault) {
        super(text);

        setForeground(Color.BLACK);
        if (isDefault && settings != null && settings.getAccentColor() != null) setBackground(settings.getAccentColor());
        else setBackground(Color.WHITE);
        setFont(new Font("Lucida Grande", Font.PLAIN, 15));

        // remove default button borders
        setFocusPainted(false);
        setContentAreaFilled(false);

        // rounded border
        setOpaque(true);
        setBorder(new RoundedBorder(10));

        addActionListener(e);
        setAlignmentX(CENTER_ALIGNMENT);
    }

    // rounded borders
    private static class RoundedBorder extends AbstractBorder {
        private final int radius;

        RoundedBorder(int radius) {
            this.radius = radius;
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(Color.GRAY); // border color
            g2.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
        }

        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(radius / 2, radius / 2, radius / 2, radius / 2);
        }

        @Override
        public Insets getBorderInsets(Component c, Insets insets) {
            insets.left = insets.right = insets.top = insets.bottom = radius / 2;
            return insets;
        }
    }
}