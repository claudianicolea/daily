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

    private final SettingsDAO settingsDAO = new SettingsDAO();

    public SettingsPanel(App app) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(padding, padding, padding, padding));

        add(createLabelH1("Settings"));

        add(createLabelH2("Display mode: "));
        ButtonGroup displayModeGroup = new ButtonGroup();

        JRadioButton lightMode = createRadioButton("Light mode", () -> {
            user.getSettings().setDisplayMode(Settings.DisplayMode.LIGHT_MODE);
            settingsDAO.updateSettings(user.getSettings());
            System.out.println("Display mode changed to light");
        });

        JRadioButton darkMode = createRadioButton("Dark mode", () -> {
            user.getSettings().setDisplayMode(Settings.DisplayMode.DARK_MODE);
            settingsDAO.updateSettings(user.getSettings());
            System.out.println("Display mode changed to dark");
        });

        displayModeGroup.add(lightMode);
        displayModeGroup.add(darkMode);
        add(lightMode);
        add(darkMode);

        if (user.getSettings().getDisplayMode() == Settings.DisplayMode.LIGHT_MODE) {
            lightMode.setSelected(true);
        } else {
            darkMode.setSelected(true);
        }

        add(createLabelH2("Accent color: "));
        String[] colors = Arrays.stream(ColorUtils.ColorName.values()).map(Enum::name).toArray(String[]::new);
        JComboBox<String> accentColor = createComboBox(colors, color -> {
            user.getSettings().setAccentColor(ColorUtils.ColorName.valueOf(color));
            settingsDAO.updateSettings(user.getSettings());
            System.out.println("Accent color changed to " + color);
        });
        add(accentColor);

        accentColor.setSelectedItem(user.getSettings().getAccentColor().getColorName());

        add(createLabelH2("Sort subjects by: "));
        ButtonGroup sortGroupButtons = new ButtonGroup();

        JRadioButton sortAlpha = createRadioButton("Alphabetical order", () -> {
            user.getSettings().setSubjectSortMode(Settings.SubjectSortMode.ALPHABETICAL);
            settingsDAO.updateSettings(user.getSettings());
            System.out.println("Subjects will now be sorted alphabetically.");
        });

        JRadioButton sortGroup = createRadioButton("Subject group", () -> {
            user.getSettings().setSubjectSortMode(Settings.SubjectSortMode.BY_SUBJECT_GROUP);
            settingsDAO.updateSettings(user.getSettings());
            System.out.println("Subjects will now be sorted by subject group.");
        });

        JRadioButton sortLevel = createRadioButton("Subject level", () -> {
            user.getSettings().setSubjectSortMode(Settings.SubjectSortMode.BY_SUBJECT_LEVEL);
            settingsDAO.updateSettings(user.getSettings());
            System.out.println("Subjects will now be sorted by subject level.");
        });

        sortGroupButtons.add(sortAlpha);
        sortGroupButtons.add(sortGroup);
        sortGroupButtons.add(sortLevel);
        add(sortAlpha);
        add(sortGroup);
        add(sortLevel);

        switch (user.getSettings().getSubjectSortMode()) {
            case ALPHABETICAL -> sortAlpha.setSelected(true);
            case BY_SUBJECT_GROUP -> sortGroup.setSelected(true);
            case BY_SUBJECT_LEVEL -> sortLevel.setSelected(true);
        }

        add(createLabelH2("Show completed tasks: "));
        JToggleButton showCompleted = createJToggleButton(
                "Show completed",
                selected -> {
                    user.getSettings().setShowCompleted(selected);
                    settingsDAO.updateSettings(user.getSettings());
                    System.out.println("Show completed tasks: " + selected);
                },
                user.getSettings().getShowCompleted()
        );
        add(showCompleted);

        add(createButton("Return to homepage", e -> app.showPanel("homepage")));
    }
}