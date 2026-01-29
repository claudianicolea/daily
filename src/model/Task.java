package model;

import util.DateUtils;

import java.sql.Timestamp;

public class Task {
    public enum TaskType {
        EXAM_STUDY,
        HOMEWORK,
        INTERNAL_ASSESSMENT
    }

    private String taskID;
    private String subjectID;
    private String title;
    private DateUtils deadline;
    private boolean isDone;
    private TaskType type;
    private Timestamp createdAt;

    public Task(String taskID, String subjectID, String title, DateUtils deadline, TaskType type) {
        this.taskID = taskID;
        this.subjectID = subjectID;
        this.title = title;
        this.deadline = deadline;
        isDone = false;
        this.type = type;
    }

    public Task(String taskID, String subjectID, String title, DateUtils deadline, TaskType type, Timestamp createdAt) {
        this.taskID = taskID;
        this.subjectID = subjectID;
        this.title = title;
        this.deadline = deadline;
        isDone = false; // TODO: Implement checking off items when completed
        this.type = type;
        this.createdAt = createdAt;
    }

    public void setTaskID(String taskID) { this.taskID = taskID; }
    public String getTaskID() { return taskID; }
    public void setSubjectID(String subjectID) { this.subjectID = subjectID; }
    public String getSubjectID() { return subjectID; }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getTitle() {
        return title;
    }
    public void setDeadline(DateUtils deadline) {
        this.deadline = deadline;
    }
    public DateUtils getDeadline() {
        return deadline;
    }
    public void setCompletionStatus(boolean isDone) {
        this.isDone = isDone;
    }
    public boolean getCompletionStatus() {
        return isDone;
    }
    public void setType(TaskType type) { this.type = type; }
    public TaskType getType() { return type; }
    public Timestamp getCreatedAt() { return createdAt; }
}
