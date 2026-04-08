package ui;

import dao.SettingsDAO;
import main.Main;
import util.Page;
import util.TaskSortMode;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

import static main.Main.settings;
import static model.Settings.colorMap;

//TODO Revise settings page UI
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

        JComboBox<String> colorList = new JComboBox<>(colorMap.keySet().toArray(new String[0]));
        for (Map.Entry<String, Color> entry : colorMap.entrySet()) {
            if (entry.getValue().equals(settings.getAccentColor())) {
                colorList.setSelectedItem(entry.getKey());
                break;
            }
        }

        colorList.addActionListener(e -> {
            String selected = (String) colorList.getSelectedItem();
            if (selected == null) return;

            settings.setAccentColor(colorMap.get(selected));
            SettingsDAO.updateSettings(settings);
        });
        colorList.setAlignmentX(CENTER_ALIGNMENT);
        add(colorList);

        // task sort mode
        JLabel taskSortModeLabel = new JLabel("Choose task sorting mode: ");
        taskSortModeLabel.setAlignmentX(CENTER_ALIGNMENT);
        add(taskSortModeLabel);

        ButtonGroup taskSortModes = new ButtonGroup();

        JRadioButton timestampSort = new JRadioButton("Creation date (Earliest first)");
        timestampSort.addActionListener(e -> {
            settings.setTaskSortMode(TaskSortMode.CREATION_DATE);
            SettingsDAO.updateSettings(settings);
        });
        timestampSort.setSelected(false);
        timestampSort.setAlignmentX(CENTER_ALIGNMENT);
        taskSortModes.add(timestampSort);
        add(timestampSort);

        JRadioButton deadlineSort = new JRadioButton("Due date (Earliest first)");
        deadlineSort.addActionListener(e -> {
            settings.setTaskSortMode(TaskSortMode.DEADLINE);
            SettingsDAO.updateSettings(settings);
        });
        deadlineSort.setSelected(false);
        deadlineSort.setAlignmentX(CENTER_ALIGNMENT);
        taskSortModes.add(deadlineSort);
        add(deadlineSort);

        JRadioButton alphSort = new JRadioButton("Title (A-Z)");
        alphSort.addActionListener(e -> {
            settings.setTaskSortMode(TaskSortMode.ALPHABETICAL);
            SettingsDAO.updateSettings(settings);
        });
        alphSort.setSelected(false);
        alphSort.setAlignmentX(CENTER_ALIGNMENT);
        taskSortModes.add(alphSort);
        add(alphSort);

        switch (settings.getTaskSortMode()) {
            case CREATION_DATE ->  timestampSort.setSelected(true);
            case ALPHABETICAL -> alphSort.setSelected(true);
            case DEADLINE -> deadlineSort.setSelected(true);
        }

        // button to return to homepage
        JButton homepageBtn = new JButton("Return");
        homepageBtn.addActionListener(e -> {
            Main.showPanel(Page.HOMEPAGE);
        });
        homepageBtn.setAlignmentX(CENTER_ALIGNMENT);
        add(homepageBtn);
    }
}