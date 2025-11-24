package model.tasks;

public class ExamStudy extends Task {
    public enum Assessment {
        PAPER1,
        PAPER2,
        PAPER3,
        INDIVIDUAL_ORAL,
        CLASS_TEST
    }

    private Assessment examType;
    private boolean isMock;

    public ExamStudy(String title, Assessment examType, boolean isMock) {
        super(title);
        this.examType = examType;
        this.isMock = isMock;
    }

    public void setExamType(Assessment examType) {
        this.examType = examType;
    }
    public Assessment getExamType() {
        return examType;
    }
    public void isMock(boolean isMock) {
        this.isMock = isMock;
    }
    public boolean isMock() {
        return isMock;
    }
}
