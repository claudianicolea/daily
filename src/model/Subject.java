package model;

public class Subject {
    private String subjectID, name;
    private final String profileID;

    public Subject(String subjectID, String profileID, String name) {
        this.subjectID = subjectID;
        this.profileID = profileID;
        this.name = name;
    }

    public void setSubjectID(String subjectID) { this.subjectID = subjectID; }
    public String getSubjectID() { return subjectID; }

    public String getProfileID() { return profileID; }

    public void setName(String name) { this.name = name; }
    public String getName() { return name; }
}