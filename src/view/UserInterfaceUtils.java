package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.function.Consumer;
import static main.App.padding;
import static main.App.textBoxWidth;

public class UserInterfaceUtils {
    public static JButton createButton(String text, ActionListener e) {
        JButton b = new JButton(text);
        b.setAlignmentX(Component.CENTER_ALIGNMENT);
        b.addActionListener(e);
        return b;
    }
    public static JLabel createLabelH1(String text) {
        JLabel l = new JLabel(text);
        l.setFont(new Font("Arial", Font.BOLD, 24));
        l.setAlignmentX(Component.CENTER_ALIGNMENT);
        return l;
    }
    public static JLabel createLabelH2(String text) {
        JLabel l = new JLabel(text);
        l.setFont(new Font("Arial", Font.PLAIN, 18));
        l.setAlignmentX(Component.CENTER_ALIGNMENT);
        return l;
    }
    public static JLabel createLabelP(String text) {
        JLabel l = new JLabel(text);
        l.setFont(new Font("Arial", Font.PLAIN, 15));
        l.setAlignmentX(Component.CENTER_ALIGNMENT);
        return l;
    }
    public static JTextField createTextField() {
        JTextField t = new JTextField();
        t.setMaximumSize(new Dimension(textBoxWidth, padding));
        t.setAlignmentX(Component.CENTER_ALIGNMENT);
        return t;
    }
    public static JRadioButton createRadioButton(String text, Runnable onClick) {
        JRadioButton r = new JRadioButton(text);
        r.addActionListener(e -> onClick.run());
        return r;
    }
    public static JToggleButton createJToggleButton(String text, Consumer<Boolean> onToggle, boolean initialState) {
        JToggleButton t = new JToggleButton(text, initialState);
        t.addItemListener(e -> onToggle.accept(t.isSelected()));
        return t;
    }
    public static JComboBox<String> createComboBox(String[] items, Consumer<String> onChange) {
        JComboBox<String> c = new JComboBox<>(items);
        c.addActionListener(e -> onChange.accept((String)c.getSelectedItem()));
        return c;
    }
}
