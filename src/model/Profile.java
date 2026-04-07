package model;

public class Profile {
    private String profileID;
    private final String settingsID;
    private String name;
    private final String email;
    private final String password;
    private Settings settings;

    public Profile(String profileID, String settingsID, String name, String email, String password) {
        this.profileID = profileID;
        this.settingsID = settingsID;
        this.name = name;
        this.email = email;
        this.password = password;
        settings = new Settings();
    }

    public void setProfileID(String profileID) { this.profileID = profileID; }
    public String getProfileID() { return profileID; }

    public String getSettingsID() { return settingsID; }

    public void setName(String name) { this.name = name; }
    public String getName() { return name; }

    public String getEmail() { return email; }

    public String getPassword() { return password; }

    public Settings getSettings() { return settings; }
}
