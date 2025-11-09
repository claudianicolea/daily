package model.tasks;

import util.Date;

import java.util.ArrayList;

public class Task {
    private String title;
    private String description;
    private Date deadline;
    private ArrayList<Subtask> subtasks;
    private boolean isDone;

    public Task(String title) {
        this.title = title;
        deadline = null;
        subtasks = new ArrayList<>();
        isDone = false;
    }

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

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public Date getDeadline() {
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
