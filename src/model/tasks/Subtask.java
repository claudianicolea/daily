package model.tasks;

import java.util.UUID;

public class Subtask {
    private final UUID subtaskID;
    private String title;
    private boolean isDone;

    Subtask (String title) {
        subtaskID = UUID.randomUUID();
        this.title = title;
        isDone = false;
    }

    public UUID getSubtaskID() { return subtaskID; }

    public void setTitle(String title) { this.title = title; }

    public String getTitle() { return title; }

    public void setCompletionStatus(boolean isDone) {
        this.isDone = isDone;
    }

    public boolean getCompletionStatus() {
        return isDone;
    }
}
