package dao;

import model.Profile;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProfileDAO {
    public static void insertProfile(Profile profile) {
        String sql = """
            INSERT INTO profiles (
                name,
                email,
                password
            ) VALUES (?, ?, ?)
        """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement s = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            s.setString(1, profile.getName());
            s.setString(2, profile.getEmail());
            s.setString(3, profile.getPassword());

            s.executeUpdate();
            ResultSet generatedKeys = s.getGeneratedKeys();
            if (generatedKeys.next()) {
                profile.setProfileID(String.valueOf(generatedKeys.getInt(1)));
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Profile getProfileByEmail(String email) {
        String sql = "SELECT * FROM profiles WHERE email = ?";
        Profile profile = null;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement s = conn.prepareStatement(sql)) {

            s.setString(1, email);
            ResultSet r = s.executeQuery();

            if (r.next()) {
                String profileID = r.getString("profileID");
                String name = r.getString("name");
                String password = r.getString("password");

                profile = new Profile(
                    profileID,
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

    public static void updateProfile(Profile profile) {
        String sql = """
            UPDATE profiles
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
}