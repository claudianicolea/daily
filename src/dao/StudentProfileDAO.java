package dao;

import model.DatabaseConnection;
import model.StudentProfile;
import util.SecurityUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentProfileDAO {
    public void insertStudentProfile(StudentProfile profile) {
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
            s.setString(4, SecurityUtils.hashPassword(new String(profile.getPassword()))); // TODO: Figure out hashing or don't implement it

            s.executeUpdate();

            ResultSet generatedKeys = s.getGeneratedKeys();
            if (generatedKeys.next()) {
                profile.setProfileID(String.valueOf(generatedKeys.getInt(1)));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public StudentProfile getStudentProfileByEmail(String email) {
        String sql = "SELECT * FROM student_profile WHERE email = ?";
        StudentProfile profile = null;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String profileID = rs.getString("profileID");
                String settingsID = rs.getString("settingsID");
                String name = rs.getString("name");
                String password = rs.getString("password");

                profile = new StudentProfile(
                        profileID,
                        settingsID,
                        name,
                        email,
                        password.toCharArray()
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return profile;
    }

    public void updateStudentProfile(StudentProfile profile) {
        String sql = """
            UPDATE student_profile
            SET name = ?, password = ?
            WHERE profileID = ?
        """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, profile.getName());
            stmt.setString(2, SecurityUtils.hashPassword(new String(profile.showHiddenPassword())));
            stmt.setString(3, profile.getProfileID());

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteStudentProfile(String profileID) {
        String sql = "DELETE FROM student_profile WHERE profileID = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, profileID);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<StudentProfile> getAllProfiles() {
        List<StudentProfile> profiles = new ArrayList<>();
        String sql = "SELECT * FROM student_profile";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String profileID = rs.getString("profileID");
                String settingsID = rs.getString("settingsID");
                String name = rs.getString("name");
                String email = rs.getString("email");
                String password = rs.getString("password");

                StudentProfile profile = new StudentProfile(
                        profileID,
                        settingsID,
                        name,
                        email,
                        password.toCharArray()
                );

                profiles.add(profile);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return profiles;
    }
}