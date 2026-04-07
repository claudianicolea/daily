package dao;

import model.Settings;
import util.TaskSortMode;

import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SettingsDAO {
    public static void insertSettings(Settings settings) {
        String sql = """
            INSERT INTO settings (
                accentColor,
                taskSortMode
            ) VALUES (?, ?)
        """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement s = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            s.setString(1, String.valueOf(settings.getAccentColor()));
            s.setString(2, String.valueOf(settings.getTaskSortMode()));

            s.executeUpdate();

            ResultSet generatedKeys = s.getGeneratedKeys();
            if (generatedKeys.next()) {
                settings.setSettingsID(String.valueOf(generatedKeys.getInt(1)));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Settings getSettingsByID(String settingsID) {
        String sql = "SELECT * FROM settings WHERE settingsID = ?";
        Settings settings = null;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement s = conn.prepareStatement(sql)) {

            s.setString(1, settingsID);
            ResultSet r = s.executeQuery();

            if (r.next()) {
                settings = new Settings(settingsID,
                        Color.decode(r.getString("accentColor")),
                        TaskSortMode.valueOf(r.getString("taskSortMode"))
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return settings;
    }

    public static void updateSettings(Settings settings) {
        String sql = """
            UPDATE settings
            SET accentColor = ?,
                taskSortMode = ?
            WHERE settingsID = ?
        """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement s = conn.prepareStatement(sql)) {

            s.setString(1, String.valueOf(settings.getAccentColor()));
            s.setString(2, String.valueOf(settings.getTaskSortMode()));
            s.setString(3, settings.getSettingsID());

            s.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteSettings(String settingsID) {
        String sql = "DELETE FROM settings WHERE settingsID = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement s = conn.prepareStatement(sql)) {

            s.setString(1, settingsID);
            s.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
