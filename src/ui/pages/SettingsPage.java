package ui.pages;

import dao.SettingsDAO;
import main.Main;
import ui.elements.BodyText;
import ui.elements.Button;
import ui.elements.Title;
import util.Page;
import util.TaskSortMode;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

import static main.Main.settings;
import static model.Settings.colorMap;

public class SettingsPage extends JPanel {
    public SettingsPage() {
        setLayout(new BorderLayout());

        // top

        JPanel top = new JPanel();
        top.add(new Title("Settings"));
        top.setBackground(settings.getAccentColor());
        top.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.GRAY));
        add(top, BorderLayout.NORTH);

        // center

        JPanel centerWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 20));
        centerWrapper.setBackground(Color.WHITE);

        JPanel center = new JPanel();
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));
        center.setBackground(Color.WHITE);

        // accent color

        center.add(new BodyText("Choose accent color:"));
        center.add(Box.createRigidArea(new Dimension(0, 10)));

        JComboBox<String> colorComboBox = new JComboBox<>(colorMap.keySet().toArray(new String[0]));
        for (Map.Entry<String, Color> entry : colorMap.entrySet()) {
            if (entry.getValue().equals(settings.getAccentColor())) {
                colorComboBox.setSelectedItem(entry.getKey());
                break;
            }
        }
        colorComboBox.addActionListener(e -> {
            String selected = (String) colorComboBox.getSelectedItem();
            if (selected != null) {
                settings.setAccentColor(colorMap.get(selected));
                SettingsDAO.updateSettings(settings);
                Main.showPanel(Page.SETTINGS);
            }
        });
        colorComboBox.setAlignmentX(Component.CENTER_ALIGNMENT);
        center.add(colorComboBox);
        center.add(Box.createRigidArea(new Dimension(0, 20)));

        // task sort mode

        center.add(new BodyText("Choose task sorting mode:"));
        center.add(Box.createRigidArea(new Dimension(0, 10)));

        ButtonGroup taskSortModes = new ButtonGroup();

        JRadioButton timestampSort = new JRadioButton("Creation date (Earliest first)");
        timestampSort.addActionListener(e -> {
            settings.setTaskSortMode(TaskSortMode.CREATION_DATE);
            SettingsDAO.updateSettings(settings);
        });
        timestampSort.setAlignmentX(Component.CENTER_ALIGNMENT);
        taskSortModes.add(timestampSort);
        center.add(timestampSort);
        center.add(Box.createRigidArea(new Dimension(0, 5)));

        JRadioButton deadlineSort = new JRadioButton("Due date (Earliest first)");
        deadlineSort.addActionListener(e -> {
            settings.setTaskSortMode(TaskSortMode.DEADLINE);
            SettingsDAO.updateSettings(settings);
        });
        deadlineSort.setAlignmentX(Component.CENTER_ALIGNMENT);
        taskSortModes.add(deadlineSort);
        center.add(deadlineSort);
        center.add(Box.createRigidArea(new Dimension(0, 5)));

        JRadioButton alphSort = new JRadioButton("Title (A-Z)");
        alphSort.addActionListener(e -> {
            settings.setTaskSortMode(TaskSortMode.ALPHABETICAL);
            SettingsDAO.updateSettings(settings);
        });
        alphSort.setAlignmentX(Component.CENTER_ALIGNMENT);
        taskSortModes.add(alphSort);
        center.add(alphSort);

        switch (settings.getTaskSortMode()) {
            case CREATION_DATE -> timestampSort.setSelected(true);
            case DEADLINE -> deadlineSort.setSelected(true);
            case ALPHABETICAL -> alphSort.setSelected(true);
        }

        centerWrapper.add(center);
        add(centerWrapper, BorderLayout.CENTER);

        // button to return to homepage
        JPanel bottom = new JPanel();
        bottom.add(new Button(
                "Return",
                e -> {
                    Main.showPanel(Page.HOMEPAGE);
                },
                true
        ));
        bottom.setBackground(settings.getAccentColor());
        bottom.setBorder(BorderFactory.createMatteBorder(2, 0, 0, 0, Color.GRAY));
        add(bottom, BorderLayout.SOUTH);
    }
}