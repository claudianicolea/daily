package model;

public class Subtask {
    private String subtaskID;
    private String taskID;
    private String title;
    private boolean isDone;

    public Subtask (String subtaskID, String taskID, String title) {
        this.subtaskID = subtaskID;
        this.taskID = taskID;
        this.title = title;
        isDone = false;
    }

    public void setSubtaskID(String subtaskID) { this.subtaskID = subtaskID; }
    public String getSubtaskID() { return subtaskID; }
    public void setTaskID(String taskID) { this.taskID = taskID; }
    public String getTaskID() { return taskID; }
    public void setTitle(String title) { this.title = title; }
    public String getTitle() { return title; }
    public void setCompletionStatus(boolean isDone) {
        this.isDone = isDone;
    }
    public boolean getCompletionStatus() {
        return isDone;
    }
}
