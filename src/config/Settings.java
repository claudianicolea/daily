package config;

import util.MyColor;
import java.awt.*;
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
    private MyColor accentColor;
    private boolean showCompleted;
    private SubjectSortMode subjectSortMode;

    public Settings() {
        settingsID = UUID.randomUUID();
        accentColor = MyColor.GRAY;
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
    public MyColor getAccentColor() { return accentColor; }
    public java.awt.Color getAccentAwtColor() { return accentColor.toAwtColor(); }
    public void toggleShowCompleted() { showCompleted = !showCompleted; }
    public boolean getShowCompleted() {
        return showCompleted;
    }
    public void setSubjectSortMode(SubjectSortMode subjectSortMode) { this.subjectSortMode = subjectSortMode; }
    public SubjectSortMode getSubjectSortMode() { return subjectSortMode; }
}
