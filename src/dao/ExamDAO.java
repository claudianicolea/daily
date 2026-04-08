package dao;

import model.Exam;

import java.sql.*;

public class ExamDAO extends TaskDAO {
    public static void insertTask(Exam task, String subjectID) {
        String sql = """
            INSERT INTO tasks (
                subjectID,
                title,
                deadline,
                type,
                examType,
                isMock
            ) VALUES (?, ?, ?, ?, ?, ?)
            """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement s = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            s.setString(1, subjectID);
            s.setString(2, task.getTitle());
            s.setDate(3, task.getDeadline());
            s.setString(4, task.getType().toString());
            s.setString(5, task.getAssessmentType().toString());
            s.setBoolean(6, task.isMock());

            s.executeUpdate();

            ResultSet generatedKeys = s.getGeneratedKeys();
            if (generatedKeys.next()) {
                task.setTaskID(String.valueOf(generatedKeys.getInt(1)));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateTask(Exam task) {
        String sql = """
            UPDATE tasks SET
                title = ?,
                deadline = ?,
                type = ?,
                examType = ?,
                isMock = ?
            WHERE taskID = ?
            """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement s = conn.prepareStatement(sql)) {

            s.setString(1, task.getTitle());
            s.setDate(2, task.getDeadline());
            s.setString(3, task.getType().toString());
            s.setString(4, task.getAssessmentType().name());
            s.setBoolean(5, task.isMock());
            s.setString(6, task.getTaskID());

            s.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
