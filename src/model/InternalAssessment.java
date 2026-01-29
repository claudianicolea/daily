package model;

import util.DateUtils;

import java.sql.Timestamp;

public class InternalAssessment extends Task {
    private String section;
    private boolean isExperiment;
    private boolean isWriting;

    public InternalAssessment(String taskID, String subjectID, String title, DateUtils deadline, String section, boolean isExperiment, boolean isWriting) {
        super(taskID, subjectID, title, deadline, TaskType.INTERNAL_ASSESSMENT);
        this.section = section;
        this.isExperiment = isExperiment;
        this.isWriting = isWriting;
    }

    public InternalAssessment(String taskID, String subjectID, String title, DateUtils deadline, String section, boolean isExperiment, boolean isWriting, Timestamp createdAt) {
        super(taskID, subjectID, title, deadline, TaskType.INTERNAL_ASSESSMENT, createdAt);
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
}
