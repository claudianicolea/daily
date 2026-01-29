package model;

import util.DateUtils;

import java.sql.Timestamp;

public class Homework extends Task {
    private String lesson;

    public Homework(String taskID, String subjectID, String title, DateUtils deadline, String lesson) {
        super(taskID, subjectID, title, deadline, TaskType.HOMEWORK);
        this.lesson = lesson;
    }

    public Homework(String taskID, String subjectID, String title, DateUtils deadline, String lesson, Timestamp createdAt) {
        super(taskID, subjectID, title, deadline, TaskType.HOMEWORK, createdAt);
        this.lesson = lesson;
    }

    public void setLesson(String lesson) {
        this.lesson = lesson;
    }
    public String getLesson() {
        return lesson;
    }
}
