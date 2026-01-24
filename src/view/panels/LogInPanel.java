package view.panels;

import dao.StudentProfileDAO;
import main.App;
import model.StudentProfile;
import util.SecurityUtils;
import javax.swing.*;
import java.awt.*;
import static main.App.*;
import static view.UserInterfaceUtils.*;

public class LogInPanel extends JPanel {
    public LogInPanel(App app) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(padding, padding, padding, padding));
        add(createLabelH1("Log into your account"));

        add(createLabelH2("Email: "));
        JTextField email = createTextField();
        add(email);

        add(createLabelH2("Password: "));
        JPasswordField password = new JPasswordField();
        password.setMaximumSize(new Dimension(textBoxWidth, padding));
        password.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(password);

        add(createButton("Back", e -> app.showPanel("welcome")));
        add(createButton("Continue", e -> handleLogIn(app, email, password)));
    }

    private void handleLogIn(App app, JTextField emailField, JPasswordField passwordField) {
        String email = emailField.getText();
        char[] password = passwordField.getPassword();

        if (email.isEmpty() || password.length == 0) {
            System.out.println("Incomplete form submission when logging in.");
            JOptionPane.showMessageDialog(this, "Please fill in all required fields.");
            return;
        }

        if (!SecurityUtils.isValidEmail(email)) {
            System.out.println("Invalid email address when logging in.");
            JOptionPane.showMessageDialog(this, "Please enter a valid email address.");
            return;
        }

        StudentProfileDAO dao = new StudentProfileDAO();
        StudentProfile fetchedUser = dao.getStudentProfileByEmail(email);

        if (fetchedUser == null) {
            System.out.println("No user found with email: " + email);
            JOptionPane.showMessageDialog(this, "Incorrect credentials. Please double check and retry.");
            return;
        }

        if (!SecurityUtils.verifyPassword(password, fetchedUser.getPassword())) {
            System.out.println("Incorrect password for user: " + email);
            JOptionPane.showMessageDialog(this, "Incorrect credentials. Please double check and retry.");
            return;
        }

        user = fetchedUser;
        System.out.println("User with id " + user.getProfileID() + " logged into session.");
        app.showPanel("homepage");
    }
}
