package model;

public class InternalAssessment extends Task {
    private String section;
    private boolean isExperiment;
    private boolean isWriting;

    public InternalAssessment(String taskID, String subjectID, String title, boolean isExperiment, boolean isWriting) {
        super(taskID, subjectID, title);
        this.isExperiment = isExperiment;
        this.isWriting = isWriting;
    }

    public void setSection(String section) {
        this.section = section;
    }
    public String getSection() {
        return section;
    }
    public void isExperiment(boolean isExperiment) {
        this.isExperiment = isExperiment;
    }
    public boolean isExperiment() {
        return isExperiment;
    }
    public void isWriting(boolean isWriting) {
        this.isWriting = isWriting;
    }
    public boolean isWriting() {
        return isWriting;
    }
}
