package model;

import util.TaskType;

import javax.swing.*;
import java.awt.*;
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

        JLabel type = new JLabel("Type: Internal Assessment");
        type.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(type);

        if (!getSection().isEmpty()) {
            JLabel section = new JLabel("Section: " + getSection());
            section.setAlignmentX(Component.CENTER_ALIGNMENT);
            panel.add(section);
        }

        if (isExperiment()) {
            JLabel exp = new JLabel("Experiment part");
            exp.setAlignmentX(Component.CENTER_ALIGNMENT);
            panel.add(exp);
        }

        if (isWriting()) {
            JLabel writing = new JLabel("Writing part");
            writing.setAlignmentX(Component.CENTER_ALIGNMENT);
            panel.add(writing);
        }
    }
}
