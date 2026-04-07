package model;

import util.Assessment;
import util.TaskType;

import javax.swing.*;
import java.awt.*;
import java.sql.Date;
import java.sql.Timestamp;

public class Exam extends Task {
    private Assessment type;
    private boolean isMock;

    public Exam(String taskID, String subjectID, String title, Date deadline, TaskType tt, Timestamp timestamp, Assessment at, boolean isMock) {
        super(taskID, subjectID, title, deadline, tt, timestamp);
        type = at;
        this.isMock = isMock;
    }

    public void setAssessmentType(Assessment type) { this.type = type; }
    public Assessment getAssessmentType() {
        return type;
    }

    public void setMock(boolean isMock) {
        this.isMock = isMock;
    }
    public boolean isMock() {
        return isMock;
    }

    @Override
    public void showDetails(JPanel panel) {
        super.showDetails(panel);

        JLabel t = new JLabel("Type: Exam");
        t.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(t);

        JLabel type = new JLabel("Assessment: " + getAssessmentType());
        type.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(type);

        if (isMock()) {
            JLabel mock = new JLabel("Mock exam");
            mock.setAlignmentX(Component.CENTER_ALIGNMENT);
            panel.add(mock);
        }
    }
}
