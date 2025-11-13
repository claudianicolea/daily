package model;

import model.tasks.Task;
import java.util.ArrayList;
import java.util.UUID;

public class Subject {
    public enum SubjectLevel {
        STANDARD_LEVEL,
        HIGHER_LEVEL,
        CORE_SUBJECT
    }

    public enum SubjectGroup {
        LANGUAGE_A,
        LANGUAGE_B,
        SCIENCES,
        HUMANITIES,
        MATHEMATICS,
        ARTS,
        DP_CORE
    }

    private final UUID subjectID;
    private String name;
    private SubjectLevel level;
    private SubjectGroup group;
    private String teacherName;
    private String teacherEmail;
    private ArrayList<Task> tasks;

    public Subject(String name, SubjectLevel level, SubjectGroup group) {
        subjectID = UUID.randomUUID();
        this.name = name;
        this.level = level;
        this.group = group;
        tasks = new ArrayList<>();
    }

    public UUID getSubjectID() { return subjectID; }

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

    public void setTeacherName(String name) {
        teacherName = name;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherEmail(String email) {
        teacherEmail = email;
    }

    public String getTeacherEmail() {
        return teacherEmail;
    }

    public void addTask(Task task) {
        tasks.add(task);
    }

    public void removeTask(Task task) {
        tasks.remove(task);
    }

    public ArrayList<Task> searchTasks(String keyword) {
        ArrayList<Task> searchResults = new ArrayList<>();
        return searchResults;
    }
}
