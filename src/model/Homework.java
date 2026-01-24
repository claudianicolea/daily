package model;

public class Homework extends Task {
    private String lesson;

    public Homework(String taskID, String subjectID, String title) {
        super(taskID, subjectID, title);
    }

    public void setLesson(String lesson) {
        this.lesson = lesson;
    }
    public String getLesson() {
        return lesson;
    }
}
