package model;

import java.sql.Timestamp;
import java.util.ArrayList;

public class Subject {
    private String subjectID;
    private String profileID;
    private String name;
    private Timestamp createdAt;

    public Subject(String subjectID, String profileID, String name, Timestamp createdAt) {
        this.subjectID = subjectID;
        this.profileID = profileID;
        this.name = name;
        this.createdAt = createdAt;
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
    public Timestamp getCreatedAt() { return createdAt; }

    // TODO: Implement search Tasks function (optional)
    public ArrayList<Task> searchTasks(String keyword) {
        ArrayList<Task> searchResults = new ArrayList<>();
        return searchResults;
    }
}
