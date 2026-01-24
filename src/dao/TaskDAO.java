package dao;

import model.*;
import util.DateUtils;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TaskDAO {
    public void insertTask(Task task) {
        String sql = """
            INSERT INTO task (
                subjectID,
                title,
                deadline,
                isDone,
                type,
                examType,
                isMock,
                lesson,
                section,
                isExperiment,
                isWriting
            ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
            """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement s = conn.prepareStatement(sql)) {

            s.setString(1, task.getSubjectID());
            s.setString(2, task.getTitle());

            if (task.getDeadline() != null) {
                s.setDate(4, new java.sql.Date(task.getDeadline().toSqlDate().getTime()));
            } else {
                s.setNull(4, Types.DATE);
            }

            s.setBoolean(5, task.getCompletionStatus());

            // determine type
            String type;
            String examType = null;
            Boolean isMock = null;
            String lesson = null;
            String section = null;
            Boolean isExperiment = null;
            Boolean isWriting = null;

            if (task instanceof ExamStudy es) {
                type = "EXAM_STUDY";
                examType = es.getExamType().name();
                isMock = es.isMock();
            } else if (task instanceof Homework hw) {
                type = "HOMEWORK";
                lesson = hw.getLesson();
            } else if (task instanceof InternalAssessment ia) {
                type = "INTERNAL_ASSESSMENT";
                section = ia.getSection();
                isExperiment = ia.isExperiment();
                isWriting = ia.isWriting();
            } else {
                type = "TASK"; // fallback
            }

            s.setString(6, type);
            s.setString(7, examType);

            if (isMock != null) s.setBoolean(8, isMock);
            else s.setNull(8, Types.BOOLEAN);

            s.setString(9, lesson);
            s.setString(10, section);

            if (isExperiment != null) s.setBoolean(11, isExperiment);
            else s.setNull(11, Types.BOOLEAN);

            if (isWriting != null) s.setBoolean(12, isWriting);
            else s.setNull(12, Types.BOOLEAN);

            s.executeUpdate();

            ResultSet generatedKeys = s.getGeneratedKeys();
            if (generatedKeys.next()) {
                task.setTaskID(String.valueOf(generatedKeys.getInt(1)));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Task> getTasksBySubject(String subjectID) {
        List<Task> tasks = new ArrayList<>();
        String sql = "SELECT * FROM task WHERE subjectID = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement s = conn.prepareStatement(sql)) {

            s.setString(1, subjectID);
            ResultSet r = s.executeQuery();

            while (r.next()) {
                String taskID = r.getString("taskID");
                String title = r.getString("title");
                String type = r.getString("type");
                Date deadlineDate = r.getDate("deadline");
                boolean isDone = r.getBoolean("isDone");

                Task task;

                switch (type) {
                    case "EXAM_STUDY":
                        ExamStudy.Assessment examType = ExamStudy.Assessment.valueOf(r.getString("examType"));
                        boolean isMock = r.getBoolean("isMock");
                        task = new ExamStudy(taskID, subjectID, title, examType, isMock);
                        break;
                    case "HOMEWORK":
                        task = new Homework(taskID, subjectID, title);
                        ((Homework) task).setLesson(r.getString("lesson"));
                        break;
                    case "INTERNAL_ASSESSMENT":
                        task = new InternalAssessment(taskID, subjectID, title,
                                r.getBoolean("isExperiment"), r.getBoolean("isWriting"));
                        ((InternalAssessment) task).setSection(r.getString("section"));
                        break;
                    default:
                        task = new Task(taskID, subjectID, title);
                }

                // convert SQL date to DateUtils if exists
                if (deadlineDate != null) {
                    task.setDeadline(new DateUtils(deadlineDate));
                }
                task.setCompletionStatus(isDone);

                tasks.add(task);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tasks;
    }

    public void updateTask(Task task) {
        String sql = """
            UPDATE Task SET
                title = ?,
                deadline = ?,
                isDone = ?,
                type = ?,
                examType = ?,
                isMock = ?,
                lesson = ?,
                section = ?,
                isExperiment = ?,
                isWriting = ?
            WHERE taskID = ?
            """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement s = conn.prepareStatement(sql)) {

            s.setString(1, task.getTitle());

            if (task.getDeadline() != null) {
                s.setDate(2, new java.sql.Date(task.getDeadline().toSqlDate().getTime()));
            } else {
                s.setNull(2, Types.DATE);
            }

            s.setBoolean(3, task.getCompletionStatus());

            // same logic as insert
            String type;
            String examType = null;
            Boolean isMock = null;
            String lesson = null;
            String section = null;
            Boolean isExperiment = null;
            Boolean isWriting = null;

            if (task instanceof ExamStudy es) {
                type = "EXAM_STUDY";
                examType = es.getExamType().name();
                isMock = es.isMock();
            } else if (task instanceof Homework hw) {
                type = "HOMEWORK";
                lesson = hw.getLesson();
            } else if (task instanceof InternalAssessment ia) {
                type = "INTERNAL_ASSESSMENT";
                section = ia.getSection();
                isExperiment = ia.isExperiment();
                isWriting = ia.isWriting();
            } else {
                type = "TASK";
            }

            s.setString(4, type);
            s.setString(5, examType);

            if (isMock != null) s.setBoolean(6, isMock);
            else s.setNull(6, Types.BOOLEAN);

            s.setString(7, lesson);
            s.setString(8, section);

            if (isExperiment != null) s.setBoolean(9, isExperiment);
            else s.setNull(9, Types.BOOLEAN);

            if (isWriting != null) s.setBoolean(10, isWriting);
            else s.setNull(10, Types.BOOLEAN);

            s.setString(11, task.getTaskID());

            s.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteTask(String taskID) {
        String sql = "DELETE FROM task WHERE taskID = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement s = conn.prepareStatement(sql)) {

            s.setString(1, taskID);
            s.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
