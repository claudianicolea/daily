package dao;

import model.DatabaseConnection;
import model.Subject;
import model.Subject.SubjectGroup;
import model.Subject.SubjectLevel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SubjectDAO {
    public void insertSubject(Subject subject, String profileID) {
        String sql = """
            INSERT INTO subject (
                profileID,
                name,
                level,
                subjectGroup,
                created_at
            )
            VALUES (?, ?, ?, ?, ?)
            """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement s = conn.prepareStatement(sql)) {

            s.setString(1, profileID);
            s.setString(2, subject.getName());
            s.setString(3, subject.getLevel().name());
            s.setString(4, subject.getGroup().name());
            s.setTimestamp(5, subject.getCreatedAt());

            s.executeUpdate();

            ResultSet generatedKeys = s.getGeneratedKeys();
            if (generatedKeys.next()) {
                subject.setSubjectID(String.valueOf(generatedKeys.getInt(1)));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Subject> getSubjectsByProfile(String profileID) {
        List<Subject> subjects = new ArrayList<>();
        String sql = "SELECT * FROM subject WHERE profileID = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement s = conn.prepareStatement(sql)) {

            s.setString(1, profileID);
            ResultSet r = s.executeQuery();

            while (r.next()) {
                Subject subject = new Subject(
                        r.getString("subjectID"),
                        r.getString("profileID"),
                        r.getString("name"),
                        SubjectLevel.valueOf(r.getString("level")),
                        SubjectGroup.valueOf(r.getString("subjectGroup")),
                        r.getTimestamp("created_at")
                );

                subjects.add(subject);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return subjects;
    }

    public void updateSubject(Subject subject) {
        String sql = """
            UPDATE subject
            SET name = ?,
                level = ?,
                subjectGroup = ?
            WHERE subjectID = ?
            """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement s = conn.prepareStatement(sql)) {

            s.setString(1, subject.getName());
            s.setString(2, subject.getLevel().name());
            s.setString(3, subject.getGroup().name());
            s.setString(4, subject.getSubjectID());

            s.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteSubject(String subjectID) {
        String sql = "DELETE FROM subject WHERE subjectID = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement s = conn.prepareStatement(sql)) {

            s.setString(1, subjectID);
            s.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
