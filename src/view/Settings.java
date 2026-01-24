package view;

import util.ColorUtils;

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

    private String settingsID;
    private DisplayMode displayMode;
    private ColorUtils accentColor;
    private boolean showCompleted;
    private SubjectSortMode subjectSortMode;

    public Settings(String settingsID) {
        this.settingsID = settingsID;
        accentColor = ColorUtils.GREY;
        displayMode = DisplayMode.LIGHT_MODE;
        showCompleted = false;
        subjectSortMode = SubjectSortMode.ALPHABETICAL;
    }

    public void setSettingsID(String settingsID) { this.settingsID = settingsID; }
    public String getSettingsID() { return settingsID; }
    public void setDisplayMode(DisplayMode displayMode) { this.displayMode = displayMode; }
    public DisplayMode getDisplayMode() {
        return displayMode;
    }
    public void setAccentColor(ColorUtils.ColorName accentColor) { this.accentColor = ColorUtils.fromName(accentColor); }
    public ColorUtils getAccentColor() { return accentColor; }
    public java.awt.Color getAccentAwtColor() { return accentColor.toAwtColor(); }
    public void setShowCompleted(boolean showCompleted) { this.showCompleted = showCompleted; }
    public boolean getShowCompleted() {
        return showCompleted;
    }
    public void setSubjectSortMode(SubjectSortMode subjectSortMode) { this.subjectSortMode = subjectSortMode; }
    public SubjectSortMode getSubjectSortMode() { return subjectSortMode; }
}
