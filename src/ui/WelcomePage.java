package ui;

import dao.ProfileDAO;
import dao.SettingsDAO;
import main.Main;
import model.Profile;
import model.Settings;
import util.Page;

import javax.swing.*;
import java.util.regex.Pattern;

import static main.Main.settings;
import static main.Main.user;

public class WelcomePage extends JPanel {
    //TODO Revise welcome page UI
    public WelcomePage() {
        JLabel title = new JLabel("Daily");
        title.setAlignmentX(CENTER_ALIGNMENT);
        add(title);

        JButton signUpBtn = new JButton("Sign Up");
        signUpBtn.addActionListener(e -> showSignUpDialog());
        signUpBtn.setAlignmentX(CENTER_ALIGNMENT);
        signUpBtn.setFocusPainted(false);
        add(signUpBtn);

        JButton logInBtn = new JButton("Log In");
        logInBtn.addActionListener(e -> showLogInDialog());
        logInBtn.setAlignmentX(CENTER_ALIGNMENT);
        signUpBtn.setFocusPainted(false);
        add(logInBtn);

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    }

    private void showSignUpDialog() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        panel.add(new JLabel("Name:"));
        JTextField nameField = new JTextField(20);
        panel.add(nameField);

        panel.add(new JLabel("Email:"));
        JTextField emailField = new JTextField(20);
        panel.add(emailField);

        panel.add(new JLabel("Password:"));
        JPasswordField passwordField = new JPasswordField(20);
        panel.add(passwordField);

        int result = JOptionPane.showConfirmDialog(
                null,
                panel,
                "Create a new account",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        if (result == JOptionPane.OK_OPTION) {
            String name = nameField.getText();
            String email = emailField.getText();
            String password = new String(passwordField.getPassword());

            if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(
                        null,
                        "Please fill in all fields!",
                        "Warning",
                        JOptionPane.WARNING_MESSAGE
                );
            }

            if (!isValidEmail(email)) {
                JOptionPane.showMessageDialog(
                        null,
                        "Please enter a valid email address!",
                        "Warning",
                        JOptionPane.WARNING_MESSAGE
                );
            }

            // register new profile

            if (ProfileDAO.getProfileByEmail(email) != null) {
                JOptionPane.showMessageDialog(
                        null,
                        "Email already exists!",
                        "Warning",
                        JOptionPane.WARNING_MESSAGE
                );
            }

            Profile newProfile = new Profile(null, name, email, password);
            ProfileDAO.insertProfile(newProfile);
            Profile savedProfile = ProfileDAO.getProfileByEmail(email);

            if (savedProfile == null) {
                JOptionPane.showMessageDialog(
                        null,
                        "Failed to save student profile!",
                        "Warning",
                        JOptionPane.WARNING_MESSAGE
                );
                return;
            }

            Settings newSettings = new Settings();
            SettingsDAO.insertSettings(newSettings, savedProfile.getProfileID());
            Settings savedSettings = SettingsDAO.getSubjectsByProfile(savedProfile.getProfileID());

            if (savedSettings == null) {
                JOptionPane.showMessageDialog(
                        null,
                        "Failed to load settings!",
                        "Warning",
                        JOptionPane.WARNING_MESSAGE
                );
                return;
            }

            user = savedProfile;
            settings = savedSettings;

            JOptionPane.showMessageDialog(
                    null,
                    "Account created! Welcome, " + user.getName()
            );
            Main.showPanel(Page.HOMEPAGE);
        }
    }

    private void showLogInDialog() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        panel.add(new JLabel("Email:"));
        JTextField emailField = new JTextField(20);
        panel.add(emailField);

        panel.add(new JLabel("Password:"));
        JPasswordField passwordField = new JPasswordField(20);
        panel.add(passwordField);

        int result = JOptionPane.showConfirmDialog(
                null,
                panel,
                "Log into your account",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        if (result == JOptionPane.OK_OPTION) {
            String email = emailField.getText();
            String password = new String(passwordField.getPassword());

            if (email.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(
                        null,
                        "Please fill in all fields!",
                        "Warning",
                        JOptionPane.WARNING_MESSAGE
                );
            }

            if (!isValidEmail(email)) {
                JOptionPane.showMessageDialog(
                        null,
                        "Please enter a valid email address!",
                        "Warning",
                        JOptionPane.WARNING_MESSAGE
                );
            }

            // fetch user

            Profile fetchedUser = ProfileDAO.getProfileByEmail(email);
            if (fetchedUser == null) {
                JOptionPane.showMessageDialog(
                        null,
                        "Incorrect credentials. Please double check and retry!",
                        "Warning",
                        JOptionPane.WARNING_MESSAGE
                );
            }

            if (!fetchedUser.getPassword().equals(password)) {
                JOptionPane.showMessageDialog(
                        null,
                        "Incorrect credentials. Please double check and retry!",
                        "Warning",
                        JOptionPane.WARNING_MESSAGE
                );
            }

            user = fetchedUser;
            JOptionPane.showMessageDialog(
                    null,
                    "Welcome back, " + user.getName() + "!"
            );
            Main.showPanel(Page.HOMEPAGE);
        }
    }

    private boolean isValidEmail(String email) {
        if (email == null) return false;
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@" + "(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern p = Pattern.compile(emailRegex);
        return p.matcher(email).matches();
    }
}
