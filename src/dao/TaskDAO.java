package dao;

import model.*;
import util.*;
import util.LinkedList;

import java.sql.*;
import java.util.*;

public class TaskDAO {
    public static LinkedList getTasksBySubject(String subjectID) {
        LinkedList tasks = new LinkedList();
        String sql = "SELECT * FROM tasks WHERE subjectID = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement s = conn.prepareStatement(sql)) {

            s.setString(1, subjectID);
            ResultSet r = s.executeQuery();

            while (r.next()) {
                TaskType type = TaskType.valueOf(r.getString("type"));
                Task task = null;

                switch (type) {
                    case EXAM:
                        task = new Exam(
                                r.getString("taskID"),
                                r.getString("title"),
                                r.getDate("deadline"),
                                TaskType.EXAM,
                                r.getTimestamp("timestamp"),
                                Assessment.valueOf(r.getString("examType")),
                                r.getBoolean("isMock")
                        );
                        break;

                    case HOMEWORK:
                        task = new Homework(
                                r.getString("taskID"),
                                r.getString("title"),
                                r.getDate("deadline"),
                                TaskType.HOMEWORK,
                                r.getTimestamp("timestamp"),
                                r.getString("lesson")
                        );
                        break;

                    case IA:
                        task = new IA(
                                r.getString("taskID"),
                                r.getString("title"),
                                r.getDate("deadline"),
                                TaskType.IA,
                                r.getTimestamp("timestamp"),
                                r.getString("section"),
                                r.getBoolean("isExperiment"),
                                r.getBoolean("isWriting")
                        );
                        break;

                    default:
                        break;
                }

                if (task != null) {
                    tasks.insertBack(new Node(task));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tasks;
    }

    public static void deleteTask(String taskID) {
        String sql = "DELETE FROM tasks WHERE taskID = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement s = conn.prepareStatement(sql)) {

            s.setString(1, taskID);
            s.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
