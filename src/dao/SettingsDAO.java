package dao;

import model.DatabaseConnection;
import util.ColorUtils;
import view.Settings;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SettingsDAO {
    public void insertSettings(Settings settings) {
        String sql = """
            INSERT INTO settings (
                displayMode,
                accentColor,
                showCompleted,
                subjectSortMode
            ) VALUES (?, ?, ?, ?)
        """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement s = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            s.setString(1, settings.getDisplayMode().name());
            s.setString(2, settings.getAccentColor().getColorName().name());
            s.setBoolean(3, settings.getShowCompleted());
            s.setString(4, settings.getSubjectSortMode().name());

            s.executeUpdate();

            ResultSet generatedKeys = s.getGeneratedKeys();
            if (generatedKeys.next()) {
                settings.setSettingsID(String.valueOf(generatedKeys.getInt(1)));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Settings getSettingsByID(String settingsID) {
        String sql = "SELECT * FROM settings WHERE settingsID = ?";
        Settings settings = null;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement s = conn.prepareStatement(sql)) {

            s.setString(1, settingsID);
            ResultSet r = s.executeQuery();

            if (r.next()) {
                settings = new Settings(settingsID);
                settings.setDisplayMode(Settings.DisplayMode.valueOf(r.getString("displayMode")));
                settings.setAccentColor(ColorUtils.ColorName.valueOf(r.getString("accentColor")));
                settings.setShowCompleted(r.getBoolean("showCompleted"));
                settings.setSubjectSortMode(Settings.SubjectSortMode.valueOf(r.getString("subjectSortMode")));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return settings;
    }

    public void updateSettings(Settings settings) {
        String sql = """
            UPDATE settings
            SET displayMode = ?,
                accentColor = ?,
                showCompleted = ?,
                subjectSortMode = ?
            WHERE settingsID = ?
        """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement s = conn.prepareStatement(sql)) {

            s.setString(1, settings.getDisplayMode().name());
            s.setString(2, settings.getAccentColor().getColorName().name());
            s.setBoolean(3, settings.getShowCompleted());
            s.setString(4, settings.getSubjectSortMode().name());
            s.setString(5, settings.getSettingsID());

            s.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteSettings(String settingsID) {
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
