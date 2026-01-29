package view.panels;

import dao.SettingsDAO;
import dao.StudentProfileDAO;
import main.App;
import model.StudentProfile;
import util.SecurityUtils;
import view.Settings;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

import static main.App.padding;
import static main.App.user;

public class WelcomePanel extends JPanel {
    public WelcomePanel(App app) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(padding, padding, padding, padding));

        JLabel title = new JLabel("Daily App - Task Management");
        title.setFont(title.getFont().deriveFont(24f));
        title.setAlignmentX(CENTER_ALIGNMENT);
        add(title);
        add(Box.createRigidArea(new Dimension(0, 20)));

        JButton signUpBtn = new JButton("Sign Up");
        signUpBtn.setAlignmentX(CENTER_ALIGNMENT);
        signUpBtn.addActionListener(e -> showSignUpDialog(app));
        add(signUpBtn);

        JButton logInBtn = new JButton("Log In");
        logInBtn.setAlignmentX(CENTER_ALIGNMENT);
        logInBtn.addActionListener(e -> showLogInDialog(app));
        add(logInBtn);
    }

    private void showSignUpDialog(App app) {
        JTextField nameField = new JTextField();
        JTextField emailField = new JTextField();
        JPasswordField passwordField = new JPasswordField();

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Nickname:"));
        panel.add(nameField);
        panel.add(new JLabel("Email:"));
        panel.add(emailField);
        panel.add(new JLabel("Password:"));
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
            String password = Arrays.toString(passwordField.getPassword());

            if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please fill in all required fields.");
                return;
            }

            if (!SecurityUtils.isValidEmail(email)) {
                JOptionPane.showMessageDialog(null, "Please enter a valid email address.");
                return;
            }

            Settings defaultSettings = new Settings(null);
            SettingsDAO.insertSettings(defaultSettings);

            StudentProfile profile = new StudentProfile(
                    null,
                    defaultSettings.getSettingsID(),
                    name,
                    email,
                    password
            );

            if (StudentProfileDAO.getStudentProfileByEmail(email) != null) {
                JOptionPane.showMessageDialog(null, "Email already exists!");
                return;
            }

            StudentProfileDAO.insertStudentProfile(profile);
            StudentProfile savedProfile = StudentProfileDAO.getStudentProfileByEmail(email);

            if (savedProfile != null) {
                user = savedProfile;
                JOptionPane.showMessageDialog(null, "Account created! Welcome, " + user.getName());
                app.showPanel("homepage");
            } else {
                JOptionPane.showMessageDialog(null, "Failed to save student profile.");
            }
        }
    }

    private void showLogInDialog(App app) {
        JTextField emailField = new JTextField();
        JPasswordField passwordField = new JPasswordField();

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Email:"));
        panel.add(emailField);
        panel.add(new JLabel("Password:"));
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
            String password = Arrays.toString(passwordField.getPassword());

            if (email.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please fill in all required fields.");
                return;
            }

            if (!SecurityUtils.isValidEmail(email)) {
                JOptionPane.showMessageDialog(null, "Please enter a valid email address.");
                return;
            }

            StudentProfile fetchedUser = StudentProfileDAO.getStudentProfileByEmail(email);

            if (fetchedUser == null || !SecurityUtils.verifyPassword(password, fetchedUser.getPassword())) {
                JOptionPane.showMessageDialog(null, "Incorrect credentials. Please double check and retry.");
                return;
            }

            user = fetchedUser;
            JOptionPane.showMessageDialog(null, "Welcome back, " + user.getName() + "!");
            app.showPanel("homepage");
        }
    }
}
