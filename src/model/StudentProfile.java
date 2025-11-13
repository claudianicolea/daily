package model;

import config.Settings;

import java.util.ArrayList;
import java.util.UUID;

public class StudentProfile {
    private final UUID profileID;
    private String name;
    private final String email;
    private final char[] password;
    private Settings settings;
    private ArrayList<Subject> subjects;

    public StudentProfile(String name, String email, char[] password) {
        profileID = UUID.randomUUID();
        this.name = name;
        this.email = email;
        this.password = password;
        subjects = new ArrayList<>();
    }

    public UUID getProfileID() { return profileID; }

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
