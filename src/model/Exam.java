package model;

import ui.elements.BodyText;
import util.Assessment;
import util.TaskType;

import javax.swing.*;
import java.sql.Date;
import java.sql.Timestamp;

public class Exam extends Task {
    private Assessment type;
    private boolean isMock;

    public Exam(String taskID, String title, Date deadline, TaskType tt, Timestamp timestamp, Assessment at, boolean isMock) {
        super(taskID, title, deadline, tt, timestamp);
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

        panel.add(new BodyText("Type: Exam"));
        panel.add(new BodyText("Assessment: " + getAssessmentType()));

        if (isMock()) {
            panel.add(new BodyText("Mock exam"));
        }
    }
}
