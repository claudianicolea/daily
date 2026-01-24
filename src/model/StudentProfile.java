package model;

import view.Settings;
import java.util.ArrayList;

public class StudentProfile {
    private String profileID;
    private String settingsID;
    private String name;
    private final String email;
    private final char[] password;
    private Settings settings;
    private ArrayList<Subject> subjects;

    public StudentProfile(String profileID, String settingsID, String name, String email, char[] password) {
        this.profileID = profileID;
        this.settingsID = settingsID;
        this.name = name;
        this.email = email;
        this.password = password;
        this.settings = new Settings(settingsID);
        subjects = new ArrayList<>();
    }

    public void setProfileID(String subjectID) { this.profileID = profileID; }
    public String getProfileID() { return profileID; }
    public void setSettingsID(String settingsID) { this.settingsID = settingsID; }
    public String getSettingsID() { return settingsID; }
    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
    public String getEmail() {
        return email;
    }
    public String showHiddenPassword() {
        return "*".repeat(password.length);
    }
    public char[] getPassword() {
        return password;
    }
    public void setSettings(Settings settings) {
        this.settings = settings;
    }
    public Settings getSettings() {
        return settings;
    }
    public void addSubject(Subject subject) {
        subjects.add(subject);
    }
    public void removeSubject(Subject subject) {
        subjects.remove(subject);
    }
}
