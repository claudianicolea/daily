package model;

import util.DateUtils;
import java.util.ArrayList;

public class Task {
    public enum TaskType {
        EXAM_STUDY,
        HOMEWORK,
        INTERNAL_ASSESSMENT
    }

    private String taskID;
    private String subjectID;
    private String title;
    private String description;
    private DateUtils deadline;
    private ArrayList<Subtask> subtasks;
    private boolean isDone;

    public Task(String taskID, String subjectID, String title) {
        this.taskID = taskID;
        this.subjectID = subjectID;
        this.title = title;
        deadline = null;
        subtasks = new ArrayList<>();
        isDone = false;
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
    public void setDescription(String description) {
        this.description = description;
    }
    public String getDescription() {
        return description;
    }
    public void setDeadline(DateUtils deadline) {
        this.deadline = deadline;
    }
    public DateUtils getDeadline() {
        return deadline;
    }
    public void addSubtask(Subtask subtask) {
        subtasks.add(subtask);
    }
    public void removeSubtask(Subtask subtask) {
        subtasks.remove(subtask);
    }
    public void setCompletionStatus(boolean isDone) {
        this.isDone = isDone;
    }
    public boolean getCompletionStatus() {
        return isDone;
    }
}
