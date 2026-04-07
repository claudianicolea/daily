package model;

import util.TaskSortMode;

import java.awt.*;

public class Settings {
    private String settingsID;
    private Color accentColor;
    private TaskSortMode taskSortMode;

    public Settings() {
        settingsID = null;
        accentColor = Color.GRAY;
        taskSortMode = TaskSortMode.CREATION_DATE;
    }

    public Settings(String settingsID, Color accentColor, TaskSortMode taskSortMode) {
        this.settingsID = settingsID;
        this.accentColor = accentColor;
        this.taskSortMode = taskSortMode;
    }

    public void setSettingsID(String settingsID) { this.settingsID = settingsID; }
    public String getSettingsID() { return settingsID; }

    public void setAccentColor(Color accentColor) { this.accentColor = accentColor; }
    public Color getAccentColor() { return accentColor; }

    public void setTaskSortMode(TaskSortMode taskSortMode) { this.taskSortMode = taskSortMode; }
    public TaskSortMode getTaskSortMode() { return taskSortMode; }
}
