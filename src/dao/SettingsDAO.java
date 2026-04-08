package dao;

import model.Settings;
import util.TaskSortMode;

import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SettingsDAO {
    public static void insertSettings(Settings settings, String profileID) {
        String sql = """
            INSERT INTO settings (
                profileID,
                accentColor,
                taskSortMode
            ) VALUES (?, ?, ?)
        """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement s = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            s.setString(1, profileID);
            s.setString(2, toHex(settings.getAccentColor()));
            s.setString(3, String.valueOf(settings.getTaskSortMode()));

            s.executeUpdate();
            ResultSet generatedKeys = s.getGeneratedKeys();
            if (generatedKeys.next()) {
                settings.setSettingsID(generatedKeys.getString(1));
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Settings getSubjectsByProfile(String profileID) {
        String sql = "SELECT * FROM settings WHERE profileID = ?";
        Settings settings = null;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement s = conn.prepareStatement(sql)) {

            s.setString(1, profileID);
            ResultSet r = s.executeQuery();

            if (r.next()) {
                settings = new Settings(
                        r.getString("settingsID"),
                        toRgb(r.getString("accentColor")),
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

            String accentColor = String.format("#%02x%02x%02x", // store as hex code for color
                    settings.getAccentColor().getRed(),
                    settings.getAccentColor().getGreen(),
                    settings.getAccentColor().getBlue()
            );
            s.setString(1, accentColor);
            s.setString(2, String.valueOf(settings.getTaskSortMode()));
            s.setString(3, settings.getSettingsID());

            s.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static String toHex(Color rgb) {
        int r = rgb.getRed(), g = rgb.getGreen(), b = rgb.getBlue();
        return String.format("#%02X%02X%02X", r, g, b);
    }

    public static Color toRgb(String hex) {
        // Remove the leading '#' if present
        if (hex.startsWith("#")) {
            hex = hex.substring(1);
        }

        int r = Integer.parseInt(hex.substring(0, 2), 16);
        int g = Integer.parseInt(hex.substring(2, 4), 16);
        int b = Integer.parseInt(hex.substring(4, 6), 16);

        return new Color(r, g, b);
    }
}
