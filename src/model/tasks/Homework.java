package model.tasks;

public class Homework extends Task {
    private String lesson;

    public Homework(String title) {
        super(title);
    }

    public void setLesson(String lesson) {
        this.lesson = lesson;
    }
    public String getLesson() {
        return lesson;
    }
}
