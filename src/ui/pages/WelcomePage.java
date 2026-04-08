package ui.pages;

import dao.ProfileDAO;
import dao.SettingsDAO;
import main.Main;
import model.Profile;
import model.Settings;
import ui.elements.BodyText;
import ui.elements.Button;
import ui.elements.Dialog;
import ui.elements.Title;
import util.Page;

import javax.swing.*;
import java.awt.*;
import java.util.regex.Pattern;

import static main.Main.settings;
import static main.Main.user;

public class WelcomePage extends JPanel {
    public WelcomePage() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        JPanel centerWrapper = new JPanel();
        centerWrapper.setLayout(new GridBagLayout());
        centerWrapper.setBackground(Color.WHITE);

        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBackground(Color.WHITE);

        // title
        content.add(new Title("Daily", true));
        content.add(Box.createRigidArea(new Dimension(0, 20)));

        // buttons

        JPanel buttonRow = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        buttonRow.setBackground(Color.WHITE);

        Button signUpBtn = new Button(
                "Sign up",
                e -> showSignUpDialog(),
                true
        );

        Button logInBtn = new Button(
                "Log in",
                e -> showLogInDialog(),
                true
        );

        buttonRow.add(signUpBtn);
        buttonRow.add(logInBtn);

        content.add(buttonRow);
        centerWrapper.add(content);

        add(centerWrapper, BorderLayout.CENTER);
    }

    private void showSignUpDialog() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        panel.add(new BodyText("Name:"));
        JTextField nameField = new JTextField(20);
        panel.add(nameField);

        panel.add(new BodyText("Email:"));
        JTextField emailField = new JTextField(20);
        panel.add(emailField);

        panel.add(new BodyText("Password:"));
        JPasswordField passwordField = new JPasswordField(20);
        panel.add(passwordField);

        int result = Dialog.showConfirmPanel(panel, "Sign up");

        if (result == JOptionPane.OK_OPTION) {
            String name = nameField.getText();
            String email = emailField.getText();
            String password = new String(passwordField.getPassword());

            if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Dialog.showWarning("Please fill in all fields!", "Warning");
                return; // exit early
            }

            if (!isValidEmail(email)) {
                Dialog.showWarning("Please enter a valid email address!", "Warning");
                return;
            }

            if (ProfileDAO.getProfileByEmail(email) != null) {
                Dialog.showWarning("Email already exists!", "Warning");
                return;
            }

            // register new profile

            Profile newProfile = new Profile(null, name, email, password);
            ProfileDAO.insertProfile(newProfile);
            Profile savedProfile = ProfileDAO.getProfileByEmail(email);

            if (savedProfile == null) {
                Dialog.showWarning(
                        "Failed to save student profile!",
                        "Warning"
                );
                return;
            }

            Settings newSettings = new Settings();
            SettingsDAO.insertSettings(newSettings, savedProfile.getProfileID());
            Settings savedSettings = SettingsDAO.getSubjectsByProfile(savedProfile.getProfileID());

            if (savedSettings == null) {
                Dialog.showWarning(
                        "Failed to load settings!",
                        "Warning"
                );
                return;
            }

            user = savedProfile;
            settings = savedSettings;

            Dialog.showMessage(
                    "Account created! Welcome, " + user.getName() + "!",
                    "Signed up successfully"
            );
            Main.showPanel(Page.HOMEPAGE);
        }
    }

    private void showLogInDialog() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        panel.add(new BodyText("Email:"));
        JTextField emailField = new JTextField(20);
        panel.add(emailField);

        panel.add(new BodyText("Password:"));
        JPasswordField passwordField = new JPasswordField(20);
        panel.add(passwordField);

        int result = Dialog.showConfirmPanel(panel, "Log into your account");

        if (result == JOptionPane.OK_OPTION) {
            String email = emailField.getText();
            String password = new String(passwordField.getPassword());

            if (email.isEmpty() || password.isEmpty()) {
                Dialog.showWarning("Please fill in all fields!", "Warning");
                return;
            }

            if (!isValidEmail(email)) {
                Dialog.showWarning("Please enter a valid email address!", "Warning");
                return;
            }

            Profile fetchedUser = ProfileDAO.getProfileByEmail(email);
            if (fetchedUser == null || !fetchedUser.getPassword().equals(password)) {
                Dialog.showWarning(
                        "Incorrect credentials. Please double check and retry!",
                        "Warning"
                );
                return;
            }

            user = fetchedUser;
            settings = SettingsDAO.getSubjectsByProfile(user.getProfileID());

            Dialog.showMessage(
                    "Welcome back, " + user.getName() + "!",
                    "Logged in successfully"
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
