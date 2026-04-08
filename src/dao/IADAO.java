package dao;

import model.IA;

import java.sql.*;

public class IADAO extends TaskDAO {
    public static void insertTask(IA task, String subjectID) {
        String sql = """
            INSERT INTO tasks (
                subjectID,
                title,
                deadline,
                type,
                section,
                isExperiment,
                isWriting
            ) VALUES (?, ?, ?, ?, ?, ?, ?)
            """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement s = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            s.setString(1, subjectID);
            s.setString(2, task.getTitle());
            s.setDate(3, task.getDeadline());
            s.setString(4, task.getType().toString());
            s.setString(5, task.getSection());
            s.setBoolean(6, task.isExperiment());
            s.setBoolean(7, task.isWriting());

            s.executeUpdate();

            ResultSet generatedKeys = s.getGeneratedKeys();
            if (generatedKeys.next()) {
                task.setTaskID(String.valueOf(generatedKeys.getInt(1)));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateTask(IA task) {
        String sql = """
            UPDATE tasks SET
                title = ?,
                deadline = ?,
                type = ?,
                section = ?,
                isExperiment = ?,
                isWriting = ?
            WHERE taskID = ?
            """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement s = conn.prepareStatement(sql)) {

            s.setString(1, task.getTitle());
            s.setDate(2, task.getDeadline());
            s.setString(3, task.getType().toString());
            s.setString(4, task.getSection());
            s.setBoolean(5, task.isExperiment());
            s.setBoolean(6, task.isWriting());
            s.setString(7, task.getTaskID());

            s.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
