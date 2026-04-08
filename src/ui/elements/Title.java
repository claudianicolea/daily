package ui.elements;

import javax.swing.*;
import java.awt.*;

public class Title extends JLabel {
    public Title(String text) {
        super(text);
        setFont(new Font("Lucida Grande", Font.BOLD, 20));
        setForeground(Color.BLACK);
        setAlignmentX(CENTER_ALIGNMENT);
    }

    public Title(String text, boolean frontPage) {
        super(text);
        setForeground(Color.BLACK);
        setAlignmentX(CENTER_ALIGNMENT);

        if (frontPage)
            setFont(new Font("Lucida Grande", Font.BOLD, 30));
        else
            setFont(new Font("Lucida Grande", Font.BOLD, 20));
    }
}
