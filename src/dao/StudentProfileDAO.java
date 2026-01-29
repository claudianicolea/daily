package dao;

import model.DatabaseConnection;
import model.StudentProfile;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentProfileDAO {
    public static void insertStudentProfile(StudentProfile profile) {
        String sql = """
            INSERT INTO student_profile (
                settingsID,
                name,
                email,
                password
            ) VALUES (?, ?, ?, ?)
        """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement s = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            s.setString(1, profile.getSettingsID());
            s.setString(2, profile.getName());
            s.setString(3, profile.getEmail());
            s.setString(4, profile.getPassword());

            s.executeUpdate();

            ResultSet generatedKeys = s.getGeneratedKeys();
            if (generatedKeys.next()) {
                profile.setProfileID(String.valueOf(generatedKeys.getInt(1)));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static StudentProfile getStudentProfileByEmail(String email) {
        String sql = "SELECT * FROM student_profile WHERE email = ?";
        StudentProfile profile = null;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement s = conn.prepareStatement(sql)) {

            s.setString(1, email);
            ResultSet r = s.executeQuery();

            if (r.next()) {
                String profileID = r.getString("profileID");
                String settingsID = r.getString("settingsID");
                String name = r.getString("name");
                String password = r.getString("password");

                profile = new StudentProfile(
                        profileID,
                        settingsID,
                        name,
                        email,
                        password
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return profile;
    }

    public static void updateStudentProfile(StudentProfile profile) {
        String sql = """
            UPDATE student_profile
            SET name = ?, password = ?
            WHERE profileID = ?
        """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement s = conn.prepareStatement(sql)) {

            s.setString(1, profile.getName());
            s.setString(2, profile.getPassword());
            s.setString(3, profile.getProfileID());

            s.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteStudentProfile(String profileID) {
        String sql = "DELETE FROM student_profile WHERE profileID = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement s = conn.prepareStatement(sql)) {

            s.setString(1, profileID);
            s.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}