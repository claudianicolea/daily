package model;

public class Profile {
    private String profileID;
    private String name;
    private final String email;
    private final String password;

    public Profile(String profileID, String name, String email, String password) {
        this.profileID = profileID;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public void setProfileID(String profileID) { this.profileID = profileID; }
    public String getProfileID() { return profileID; }

    public void setName(String name) { this.name = name; }
    public String getName() { return name; }

    public String getEmail() { return email; }

    public String getPassword() { return password; }
}
