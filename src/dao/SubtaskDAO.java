package dao;

import model.DatabaseConnection;
import model.Subtask;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SubtaskDAO {

    public void insertSubtask(Subtask subtask) {
        String sql = """
            INSERT INTO subtask (
                taskID,
                title,
                isDone
            ) VALUES (?, ?, ?)
            """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement s = conn.prepareStatement(sql)) {

            s.setString(1, subtask.getTaskID());
            s.setString(2, subtask.getTitle());
            s.setBoolean(3, subtask.getCompletionStatus());

            s.executeUpdate();

            ResultSet generatedKeys = s.getGeneratedKeys();
            if (generatedKeys.next()) {
                subtask.setSubtaskID(String.valueOf(generatedKeys.getInt(1)));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Subtask> getSubtasksByTask(String taskID) {
        List<Subtask> subtasks = new ArrayList<>();
        String sql = "SELECT * FROM subtask WHERE taskID = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement s = conn.prepareStatement(sql)) {

            s.setString(1, taskID);
            ResultSet r = s.executeQuery();

            while (r.next()) {
                Subtask subtask = new Subtask(
                        r.getString("subtaskID"),
                        r.getString("taskID"),
                        r.getString("title")
                );
                subtask.setCompletionStatus(r.getBoolean("isDone"));

                subtasks.add(subtask);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return subtasks;
    }

    public void updateSubtask(Subtask subtask) {
        String sql = """
            UPDATE subtask
            SET title = ?, isDone = ?
            WHERE subtaskID = ?
            """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement s = conn.prepareStatement(sql)) {

            s.setString(1, subtask.getTitle());
            s.setBoolean(2, subtask.getCompletionStatus());
            s.setString(3, subtask.getSubtaskID());

            s.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteSubtask(String subtaskID) {
        String sql = "DELETE FROM subtask WHERE subtaskID = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement s = conn.prepareStatement(sql)) {

            s.setString(1, subtaskID);
            s.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
