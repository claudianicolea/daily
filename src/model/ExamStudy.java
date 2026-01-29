package model;

import util.DateUtils;

import java.sql.Timestamp;

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

    public ExamStudy(String taskID, String subjectID, String title, DateUtils deadline, Assessment examType, boolean isMock) {
        super(taskID, subjectID, title, deadline, TaskType.EXAM_STUDY);
        this.examType = examType;
        this.isMock = isMock;
    }

    public ExamStudy(String taskID, String subjectID, String title, DateUtils deadline, Assessment examType, boolean isMock, Timestamp createdAt) {
        super(taskID, subjectID, title, deadline, TaskType.EXAM_STUDY, createdAt);
        this.examType = examType;
        this.isMock = isMock;
    }

    public void setExamType(Assessment examType) {
        this.examType = examType;
    }
    public Assessment getExamType() {
        return examType;
    }
    public void setMock(boolean isMock) {
        this.isMock = isMock;
    }
    public boolean isMock() {
        return isMock;
    }
}
