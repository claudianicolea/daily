package model.tasks;

public class Subtask {
    private String title;
    private boolean isDone;

    Subtask (String title) {
        this.title = title;
        isDone = false;
    }

    public void setTitle(String title) { this.title = title; }

    public String getTitle() { return title; }

    public void setCompletionStatus(boolean isDone) {
        this.isDone = isDone;
    }

    public boolean getCompletionStatus() {
        return isDone;
    }
}
