package ui.elements;

import javax.swing.*;
import java.awt.*;

public class RadioButton extends JRadioButton {
    public RadioButton(String text) {
        super(text);
        setFont(new Font("Lucida Grande", Font.PLAIN, 15));
        setAlignmentX(CENTER_ALIGNMENT);
    }
}
