package model;

import ui.elements.BodyText;
import util.TaskType;

import javax.swing.*;
import java.sql.Date;
import java.sql.Timestamp;

public class IA extends Task {
    private String section;
    private boolean isExperiment, isWriting;

    public IA(String taskID, String title, Date deadline, TaskType type, Timestamp timestamp, String section, boolean isExperiment, boolean isWriting) {
        super(taskID, title, deadline, type, timestamp);
        this.section = section;
        this.isExperiment = isExperiment;
        this.isWriting = isWriting;
    }

    public void setSection(String section) {
        this.section = section;
    }
    public String getSection() {
        return section;
    }

    public void setExperiment(boolean isExperiment) {
        this.isExperiment = isExperiment;
    }
    public boolean isExperiment() {
        return isExperiment;
    }

    public void setWriting(boolean isWriting) {
        this.isWriting = isWriting;
    }
    public boolean isWriting() {
        return isWriting;
    }

    @Override
    public void showDetails(JPanel panel) {
        super.showDetails(panel);

        panel.add(new BodyText("Type: Internal Assessment"));

        if (!getSection().isEmpty()) {
            panel.add(new BodyText("Section: " + getSection()));
        }

        if (isExperiment()) {
            panel.add(new BodyText("Experiment part"));
        }

        if (isWriting()) {
            panel.add(new BodyText("Writing part"));
        }
    }
}
