package config;

import model.Subject;

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

    private DisplayMode displayMode;
    private boolean showCompleted;
    private SubjectSortMode subjectSortMode;

    public Settings() {
        displayMode = DisplayMode.LIGHT_MODE;
        showCompleted = false;
        subjectSortMode = SubjectSortMode.ALPHABETICAL;
    }

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

    public void toggleShowCompleted() { showCompleted = !showCompleted; }

    public boolean getShowCompleted() {
        return showCompleted;
    }

    public void setSubjectSortMode(SubjectSortMode subjectSortMode) { this.subjectSortMode = subjectSortMode; }

    public SubjectSortMode getSubjectSortMode() { return subjectSortMode; }
}
