package view.panels;

import dao.SettingsDAO;
import main.App;
import util.ColorUtils;
import view.Settings;

import javax.swing.*;
import java.util.Arrays;

import static main.App.padding;
import static main.App.user;
import static view.UserInterfaceUtils.*;

public class SettingsPanel extends JPanel {

    public SettingsPanel(App app) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(padding, padding, padding, padding));

        add(createLabelH1("Settings"));

        add(createLabelH2("Accent color: "));
        String[] colors = Arrays.stream(ColorUtils.ColorName.values()).map(Enum::name).toArray(String[]::new);
        JComboBox<String> accentColor = createComboBox(colors, color -> {
            user.getSettings().setAccentColor(ColorUtils.ColorName.valueOf(color));
            SettingsDAO.updateSettings(user.getSettings());
            System.out.println("Accent color changed to " + color);
        });
        add(accentColor);

        accentColor.setSelectedItem(user.getSettings().getAccentColor().getColorName().toString());

        add(createLabelH2("Sort tasks by: "));
        ButtonGroup sortGroupButtons = new ButtonGroup();

        JRadioButton sortAlpha = createRadioButton("Alphabetical order", () -> {
            user.getSettings().setTaskSortMode(Settings.TaskSortMode.ALPHABETICAL);
            SettingsDAO.updateSettings(user.getSettings());
            System.out.println("Tasks will now be sorted alphabetically.");
        });

        JRadioButton sortDate = createRadioButton("Date added", () -> {
            user.getSettings().setTaskSortMode(Settings.TaskSortMode.BY_DATE_ADDED);
            SettingsDAO.updateSettings(user.getSettings());
            System.out.println("Tasks will now be sorted by date added.");
        });

        JRadioButton sortDeadline = createRadioButton("Deadline", () -> {
            user.getSettings().setTaskSortMode(Settings.TaskSortMode.BY_DEADLINE);
            SettingsDAO.updateSettings(user.getSettings());
            System.out.println("Tasks will now be sorted by deadline.");
        });

        sortGroupButtons.add(sortAlpha);
        sortGroupButtons.add(sortDate);
        sortGroupButtons.add(sortDeadline);
        add(sortAlpha);
        add(sortDate);
        add(sortDeadline);

        switch (user.getSettings().getTaskSortMode()) {
            case ALPHABETICAL -> sortAlpha.setSelected(true);
            case BY_DATE_ADDED -> sortDate.setSelected(true);
            case BY_DEADLINE -> sortDeadline.setSelected(true);
        }

        add(createButton("Return to homepage", e -> app.showPanel("homepage")));
    }
}