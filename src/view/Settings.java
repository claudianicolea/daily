package view;

import util.ColorUtils;

public class Settings {
    public enum TaskSortMode {
        BY_DATE_ADDED,
        ALPHABETICAL,
        BY_DEADLINE
    }

    private String settingsID;
    private ColorUtils accentColor;
    private TaskSortMode taskSortMode;

    public Settings(String settingsID) {
        this.settingsID = settingsID;
        accentColor = ColorUtils.GREY;
        taskSortMode = TaskSortMode.BY_DATE_ADDED;
    }

    public void setSettingsID(String settingsID) { this.settingsID = settingsID; }
    public String getSettingsID() { return settingsID; }
    public void setAccentColor(ColorUtils.ColorName accentColor) { this.accentColor = ColorUtils.fromName(accentColor); }
    public ColorUtils getAccentColor() { return accentColor; }
    public java.awt.Color getAccentAwtColor() { return accentColor.toAwtColor(); }
    public void setTaskSortMode(TaskSortMode taskSortMode) { this.taskSortMode = taskSortMode; }
    public TaskSortMode getTaskSortMode() { return taskSortMode; }
}
