package ui;

import main.Main;
import util.Page;

import javax.swing.*;

//TODO settings page
public class SettingsPage extends JPanel {
    public SettingsPage() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("Settings");
        title.setAlignmentX(CENTER_ALIGNMENT);
        add(title);

        // button to return to homepage
        JButton homepageBtn = new JButton("Return");
        homepageBtn.addActionListener(e -> {
            Main.showPanel(Page.HOMEPAGE);
        });
        homepageBtn.setAlignmentX(CENTER_ALIGNMENT);
        add(homepageBtn);
    }
}