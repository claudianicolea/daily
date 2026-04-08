package ui.elements;

import javax.swing.*;
import java.awt.*;

public class BodyText extends JLabel {
    public BodyText(String text) {
        super(text);
        setFont(new Font("Lucida Grande", Font.PLAIN, 15));
        setForeground(Color.BLACK);
        setAlignmentX(CENTER_ALIGNMENT);
    }
}
