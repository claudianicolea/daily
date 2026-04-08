package model;

import util.TaskSortMode;

import java.awt.*;
import java.util.Map;

public class Settings {
    private String settingsID;
    private Color accentColor;
    private TaskSortMode taskSortMode;

    public static Map<String, Color> colorMap = Map.of(
            "Light grey", new Color(202, 202, 202),
            "Red", new Color(255,179,186),
            "Orange", new Color(255,223,186),
            "Yellow", new Color(255,255,186),
            "Green", new Color(186,255,201),
            "Blue", new Color(186,225,255),
            "Purple", new Color(195, 186,255),
            "Pink", new Color(250, 186,255)
    );

    public Settings() {
        this.settingsID = null;
        this.accentColor = colorMap.get("Light grey");
        this.taskSortMode = TaskSortMode.CREATION_DATE;
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
