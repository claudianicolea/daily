package model;

import java.sql.Timestamp;
import java.util.ArrayList;

public class Subject {
    public enum SubjectLevel {
        STANDARD_LEVEL,
        HIGHER_LEVEL,
        CORE_SUBJECT
    }

    public enum SubjectGroup {
        ARTS,
        DP_CORE,
        HUMANITIES,
        LANGUAGE_A,
        LANGUAGE_B,
        MATHEMATICS,
        SCIENCES
    }

    private String subjectID;
    private String profileID;
    private String name;
    private SubjectLevel level;
    private SubjectGroup group;
    private Timestamp createdAt;
    private ArrayList<Task> tasks;

    public Subject(String subjectID, String profileID, String name, SubjectLevel level, SubjectGroup group, Timestamp createdAt) {
        this.subjectID = subjectID;
        this.profileID = profileID;
        this.name = name;
        this.level = level;
        this.group = group;
        this.createdAt = createdAt;
        tasks = new ArrayList<>();
    }

    public void setSubjectID(String subjectID) { this.subjectID = subjectID; }
    public String getSubjectID() { return subjectID; }
    public void setProfileID(String subjectID) { this.profileID = profileID; }
    public String getProfileID() { return profileID; }
    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
    public void setLevel(SubjectLevel level) {
        this.level = level;
    }
    public SubjectLevel getLevel() {
        return level;
    }
    public void setGroup(SubjectGroup group) { this.group = group; }
    public SubjectGroup getGroup() { return group; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }
    public Timestamp getCreatedAt() { return createdAt; }

    public void addTask(Task task) {
        tasks.add(task);
    }
    public void removeTask(Task task) {
        tasks.remove(task);
    }

    // TODO -- Implement searchTasks function
    public ArrayList<Task> searchTasks(String keyword) {
        ArrayList<Task> searchResults = new ArrayList<>();
        return searchResults;
    }
}
