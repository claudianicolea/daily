package model;

import ui.elements.BodyText;
import util.TaskType;

import javax.swing.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.time.temporal.WeekFields;
import java.util.Locale;

public class Task {
    private String taskID, title;
    private Date deadline;
    private final TaskType type;
    private final Timestamp timestamp;

    public Task(String taskID, String title, Date deadline, TaskType type, Timestamp timestamp) {
        this.taskID = taskID;
        this.title = title;
        this.deadline = deadline;
        this.type = type;
        this.timestamp = timestamp;
    }

    public void setTaskID(String taskID) { this.taskID = taskID; }
    public String getTaskID() { return taskID; }

    public void setTitle(String title) {
        this.title = title;
    }
    public String getTitle() {
        return title;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }
    public Date getDeadline() {
        return deadline;
    }

    public TaskType getType() { return type; }

    public Timestamp getTimestamp() { return timestamp; }

    public void showDetails(JPanel panel) {
        panel.add(new BodyText("Title: " + getTitle()));
        panel.add(new BodyText("Deadline: " + getRelativeDeadline()));
    }

    public String getRelativeDeadline() {
        LocalDate today = LocalDate.now();
        LocalDate deadlineDate = this.deadline.toLocalDate();

        // yesterday, today, or tomorrow
        if (deadlineDate.equals(today.minusDays(1))) return "Yesterday";
        if (deadlineDate.equals(today)) return "Today";
        if (deadlineDate.equals(today.plusDays(1))) return "Tomorrow";

        WeekFields weekFields = WeekFields.of(Locale.getDefault());

        int currentWeek = today.get(weekFields.weekOfWeekBasedYear());
        int deadlineWeek = deadlineDate.get(weekFields.weekOfWeekBasedYear());

        // this monday, next tuesday, etc.
        if (today.getYear() == deadlineDate.getYear()) {
            if (deadlineWeek == currentWeek) {
                return "This " + deadlineDate.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.ENGLISH);
            } else if (deadlineWeek == currentWeek + 1) {
                return "Next " + deadlineDate.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.ENGLISH);
            }
        }

        return formatDeadline();
    }

    private String formatDeadline() {
        LocalDate d = this.deadline.toLocalDate();
        LocalDate today = LocalDate.now();

        DateTimeFormatter formatter;

        // if task is next year (year + 1) or before today - show year
        if (d.getYear() != today.getYear() || d.isBefore(today)) {
            formatter = DateTimeFormatter.ofPattern("d MMM uu");
            return d.format(formatter);
        }

        formatter = DateTimeFormatter.ofPattern("d MMM");
        return d.format(formatter);
    }
}
