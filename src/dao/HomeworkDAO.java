package dao;

import model.Homework;

import java.sql.*;

public class HomeworkDAO extends TaskDAO {
    public static void insertTask(Homework task){
        String sql = """
            INSERT INTO tasks (
                subjectID,
                title,
                deadline,
                type,
                lesson
            ) VALUES (?, ?, ?, ?, ?)
            """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement s = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            s.setString(1, task.getSubjectID());
            s.setString(2, task.getTitle());
            s.setDate(3, task.getDeadline());
            s.setString(4, task.getType().toString());
            s.setString(5, task.getLesson());

            s.executeUpdate();

            ResultSet generatedKeys = s.getGeneratedKeys();
            if (generatedKeys.next()) {
                task.setTaskID(String.valueOf(generatedKeys.getInt(1)));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateTask(Homework task) {
        String sql = """
            UPDATE tasks SET
                title = ?,
                deadline = ?,
                type = ?,
                lesson = ?
            WHERE taskID = ?
            """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement s = conn.prepareStatement(sql)) {

            s.setString(1, task.getTitle());
            s.setDate(2, task.getDeadline());
            s.setString(3, task.getType().toString());
            s.setString(4, task.getLesson());
            s.setString(5, task.getTaskID());

            s.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
