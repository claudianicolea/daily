package dao;

import model.*;
import util.DateUtils;
import util.data_structures.TaskLinkedList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TaskDAO {
    public static TaskLinkedList getTasksBySubject(String subjectID) {
        TaskLinkedList tasks = new TaskLinkedList();
        String sql = "SELECT * FROM task WHERE subjectID = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement s = conn.prepareStatement(sql)) {

            s.setString(1, subjectID);
            ResultSet r = s.executeQuery();

            while (r.next()) {
                Task.TaskType type = Task.TaskType.valueOf(r.getString("type"));
                Task task = null;

                switch (type) {
                    case EXAM_STUDY:
                        task = new ExamStudy(
                                r.getString("taskID"),
                                subjectID,
                                r.getString("title"),
                                new DateUtils(r.getDate("deadline")),
                                ExamStudy.Assessment.valueOf(r.getString("examType")),
                                r.getBoolean("isMock"),
                                r.getTimestamp("created_at")
                        );
                        break;

                    case HOMEWORK:
                        task = new Homework(
                                r.getString("taskID"),
                                subjectID,
                                r.getString("title"),
                                new DateUtils(r.getDate("deadline")),
                                r.getString("lesson"),
                                r.getTimestamp("created_at")
                        );
                        break;

                    case INTERNAL_ASSESSMENT:
                        task = new InternalAssessment(
                                r.getString("taskID"),
                                subjectID,
                                r.getString("title"),
                                new DateUtils(r.getDate("deadline")),
                                r.getString("section"),
                                r.getBoolean("isExperiment"),
                                r.getBoolean("isWriting"),
                                r.getTimestamp("created_at")
                        );
                        break;

                    default:
                        break;
                }

                if (task != null) {
                    task.setCompletionStatus(r.getBoolean("isDone"));
                    tasks.insertTail(task); // 👈 key change
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tasks;
    }


    public static void deleteTask(String taskID) {
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
