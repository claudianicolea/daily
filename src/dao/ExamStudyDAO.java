package dao;

import model.DatabaseConnection;
import model.ExamStudy;

import java.sql.*;

public class ExamStudyDAO extends TaskDAO {
    public static void insertTask(ExamStudy task) {
        String sql = """
            INSERT INTO task (
                subjectID,
                title,
                deadline,
                isDone,
                type,
                examType,
                isMock
            ) VALUES (?, ?, ?, ?, ?, ?, ?)
            """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement s = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            s.setString(1, task.getSubjectID());
            s.setString(2, task.getTitle());
            s.setDate(3, new java.sql.Date(task.getDeadline().toSqlDate().getTime()));
            s.setBoolean(4, task.getCompletionStatus());
            s.setString(5, task.getType().toString());
            s.setString(6, task.getExamType().toString());
            s.setBoolean(7, task.isMock());

            s.executeUpdate();

            ResultSet generatedKeys = s.getGeneratedKeys();
            if (generatedKeys.next()) {
                task.setTaskID(String.valueOf(generatedKeys.getInt(1)));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateTask(ExamStudy task) {
        String sql = """
            UPDATE task SET
                title = ?,
                deadline = ?,
                isDone = ?,
                type = ?,
                examType = ?,
                isMock = ?
            WHERE taskID = ?
            """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement s = conn.prepareStatement(sql)) {

            s.setString(1, task.getTitle());
            s.setDate(2, new java.sql.Date(task.getDeadline().toSqlDate().getTime()));
            s.setBoolean(3, task.getCompletionStatus());
            s.setString(4, task.getType().toString());
            s.setString(5, task.getExamType().name());
            s.setBoolean(6, task.isMock());
            s.setString(7, task.getTaskID());

            s.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
