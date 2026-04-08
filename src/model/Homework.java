package model;

import util.TaskType;

import javax.swing.*;
import java.awt.*;
import java.sql.Date;
import java.sql.Timestamp;

public class Homework extends Task {
    private String lesson;

    public Homework(String taskID, String title, Date deadline, TaskType type, Timestamp timestamp, String lesson) {
        super(taskID, title, deadline, type, timestamp);
        this.lesson = lesson;
    }

    public void setLesson(String lesson) {
        this.lesson = lesson;
    }
    public String getLesson() {
        return lesson;
    }

    @Override
    public void showDetails(JPanel panel) {
        super.showDetails(panel);

        JLabel type = new JLabel("Type: Homework");
        type.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(type);

        if (!getLesson().isEmpty()) {
            JLabel lesson = new JLabel("Lesson: " + getLesson());
            lesson.setAlignmentX(Component.CENTER_ALIGNMENT);
            panel.add(lesson);
        }
    }
}
