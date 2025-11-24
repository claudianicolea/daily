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
        accentColor = MyColor.GREY;
        displayMode = DisplayMode.LIGHT_MODE;
        showCompleted = false;
        subjectSortMode = SubjectSortMode.ALPHABETICAL;
    }

    public UUID getSettingsID() { return settingsID; }
    public void setDisplayMode(DisplayMode displayMode) { this.displayMode = displayMode; }
    public DisplayMode getDisplayMode() {
        return displayMode;
    }
    public void setAccentColor(MyColor.ColorName accentColor) { this.accentColor = MyColor.fromName(accentColor); }
    public MyColor getAccentColor() { return accentColor; }
    public java.awt.Color getAccentAwtColor() { return accentColor.toAwtColor(); }
    public void setShowCompleted(boolean showCompleted) { this.showCompleted = showCompleted; }
    public boolean getShowCompleted() {
        return showCompleted;
    }
    public void setSubjectSortMode(SubjectSortMode subjectSortMode) { this.subjectSortMode = subjectSortMode; }
    public SubjectSortMode getSubjectSortMode() { return subjectSortMode; }
}
