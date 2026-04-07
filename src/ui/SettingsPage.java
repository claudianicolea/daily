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

        // accent color
        JLabel accentColorLabel = new JLabel("Choose accent color: ");
        accentColorLabel.setAlignmentX(CENTER_ALIGNMENT);
        add(accentColorLabel);

        // task sort mode
        JLabel taskSortModeLabel = new JLabel("Choose task sorting mode: ");
        taskSortModeLabel.setAlignmentX(CENTER_ALIGNMENT);
        add(taskSortModeLabel);

        // button to return to homepage
        JButton homepageBtn = new JButton("Return");
        homepageBtn.addActionListener(e -> {
            Main.showPanel(Page.HOMEPAGE);
        });
        homepageBtn.setAlignmentX(CENTER_ALIGNMENT);
        add(homepageBtn);
    }
}