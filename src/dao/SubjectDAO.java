package dao;

import model.Subject;
import util.LinkedList;
import util.Node;

import java.sql.*;

public class SubjectDAO {
    public static void insertSubject(Subject subject, String profileID) {
        String sql = """
            INSERT INTO subjects (
                profileID,
                subjectName
            )
            VALUES (?, ?)
            """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement s = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            s.setString(1, profileID);
            s.setString(2, subject.getName());

            s.executeUpdate();

            ResultSet generatedKeys = s.getGeneratedKeys();
            if (generatedKeys.next()) {
                subject.setSubjectID(String.valueOf(generatedKeys.getInt(1)));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static LinkedList getSubjectsByProfile(String profileID) {
        LinkedList subjects = new LinkedList();
        String sql = "SELECT * FROM subjects WHERE profileID = ? ORDER BY timestamp ASC";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement s = conn.prepareStatement(sql)) {

            s.setString(1, profileID);
            ResultSet r = s.executeQuery();

            while (r.next()) {
                Subject subject = new Subject(
                        r.getString("subjectID"),
                        r.getString("profileID"),
                        r.getString("subjectName")
                );

                subjects.insertBack(new Node(subject));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return subjects;
    }

    public static void updateSubject(Subject subject) {
        String sql = """
            UPDATE subjects
            SET subjectName = ?
            WHERE subjectID = ?
            """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement s = conn.prepareStatement(sql)) {

            s.setString(1, subject.getName());
            s.setString(2, subject.getSubjectID());

            s.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteSubject(String subjectID) {
        String sql = "DELETE FROM subjects WHERE subjectID = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement s = conn.prepareStatement(sql)) {

            s.setString(1, subjectID);
            s.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}