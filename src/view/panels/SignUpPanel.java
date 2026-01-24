package view.panels;

import dao.SettingsDAO;
import dao.StudentProfileDAO;
import main.App;
import model.StudentProfile;
import util.SecurityUtils;
import view.Settings;

import javax.swing.*;
import java.awt.*;
import static main.App.*;
import static view.UserInterfaceUtils.*;

public class SignUpPanel extends JPanel {
    public SignUpPanel(App app) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(padding, padding, padding, padding));
        add(createLabelH1("Create a new account"));

        add(createLabelH2("Enter your nickname:"));
        JTextField name = createTextField();
        add(name);

        add(createLabelH2("Enter your email: "));
        JTextField email = createTextField();
        add(email);

        add(createLabelH2("Create a password:"));
        JPasswordField password = new JPasswordField();
        password.setMaximumSize(new Dimension(textBoxWidth, padding));
        password.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(password);

        add(createButton("Back", e -> app.showPanel("welcome")));
        add(createButton("Continue", e -> handleSignUp(app, name, email, password)));
    }

    private void handleSignUp(App app, JTextField name, JTextField email, JPasswordField password) {
        if (name.getText().isEmpty() || email.getText().isEmpty() || password.getPassword().length == 0) {
            System.out.println("Incomplete form submission when signing up.");
            JOptionPane.showMessageDialog(this, "Please fill in all required fields.");
            return;
        }

        if (!SecurityUtils.isValidEmail(email.getText())) {
            System.out.println("Invalid email address when signing up.");
            JOptionPane.showMessageDialog(this, "Please enter a valid email address.");
            return;
        }

        Settings defaultSettings = new Settings(null); // or however you create one
        SettingsDAO settingsDAO = new SettingsDAO();
        settingsDAO.insertSettings(defaultSettings);

        StudentProfile profile = new StudentProfile(
                null,
                defaultSettings.getSettingsID(),
                name.getText(),
                email.getText(),
                password.getPassword()
        );

        StudentProfileDAO dao = new StudentProfileDAO();

        if (dao.getStudentProfileByEmail(email.getText()) != null) {
            JOptionPane.showMessageDialog(this, "Email already exists!");
            return;
        }

        dao.insertStudentProfile(profile);

        StudentProfile savedProfile = dao.getStudentProfileByEmail(email.getText());

        if (savedProfile != null) {
            user = savedProfile; // global current user
            System.out.println("New student profile saved in DB: " + user.getProfileID());
            app.showPanel("homepage");
        } else {
            System.out.println("Failed to save student profile.");
            JOptionPane.showMessageDialog(this, "Failed to save student profile.");
        }
    }
}
