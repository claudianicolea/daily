package config;

import util.Color;
import java.util.UUID;

public class Settings {
    public enum DisplayMode {
        LIGHT_MODE,
        DARK_MODE
    }

    public enum SubjectSortMode {
        BY_SUBJECT_GROUP,
        ALPHABETICAL,
        BY_SUBJECT_LEVEL
    }

    private final UUID settingsID;
    private DisplayMode displayMode;
    private Color.ColorName accentColor;
    private boolean showCompleted;
    private SubjectSortMode subjectSortMode;

    public Settings() {
        settingsID = UUID.randomUUID();
        accentColor = Color.ColorName.BLACK;
        displayMode = DisplayMode.LIGHT_MODE;
        showCompleted = false;
        subjectSortMode = SubjectSortMode.ALPHABETICAL;
    }

    public UUID getSettingsID() { return settingsID; }

    public void toggleDisplayMode() {
        switch(displayMode) {
            case LIGHT_MODE:
                displayMode = DisplayMode.DARK_MODE;
            case DARK_MODE:
                displayMode = DisplayMode.LIGHT_MODE;
        }
    }

    public DisplayMode getDisplayMode() {
        return displayMode;
    }

    public void setAccentColor(Color.ColorName accentColor) { this.accentColor = accentColor; }

    public Color.ColorName getAccentColor() { return accentColor; }

    public void toggleShowCompleted() { showCompleted = !showCompleted; }

    public boolean getShowCompleted() {
        return showCompleted;
    }

    public void setSubjectSortMode(SubjectSortMode subjectSortMode) { this.subjectSortMode = subjectSortMode; }

    public SubjectSortMode getSubjectSortMode() { return subjectSortMode; }
}
