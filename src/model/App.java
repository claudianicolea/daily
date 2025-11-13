package model;

import util.Error;
import util.Security;

import javax.swing.*;
import java.awt.*;

import static util.Error.showError;

public class App {
    public static String APP_NAME = "Daily";

    public static JPanel mainPanel; // holds all screens
    public static CardLayout cardLayout;

    public static int mainWindowWidth = 1000;
    public static int mainWindowHeight = 800;
    public static int padding = 20;
    public static int textBoxWidth = 300;

    App() {
        // frame setup
        JFrame frame = new JFrame(APP_NAME);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(mainWindowWidth, mainWindowHeight);
        frame.setLocationRelativeTo(null);

        // CardLayout for screen switching
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // add screens to the main panel
        mainPanel.add(getWelcomePanel(), "welcome");
        mainPanel.add(getSignUpPanel(), "signup");
        mainPanel.add(getLogInPanel(), "login");
        mainPanel.add(getHomePagePanel(), "homepage");
        mainPanel.add(getProfilePanel(), "profile");
        mainPanel.add(getSettingsPanel(), "settings");
        mainPanel.add(getSubjectsPanel(), "subjects");

        frame.add(mainPanel);
        frame.setVisible(true);
    }

    private JPanel getWelcomePanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(padding, padding, padding, padding));

        JLabel l1 = new JLabel("Welcome to Daily!");
        l1.setAlignmentX(Component.CENTER_ALIGNMENT);
        l1.setFont(new Font("Arial", Font.BOLD, 24));
        panel.add(l1);
        panel.add(Box.createRigidArea(new Dimension(0, padding)));

        JButton signUpButton = new JButton("Sign up");
        signUpButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(signUpButton);
        panel.add(Box.createRigidArea(new Dimension(0, padding)));

        JButton logInButton = new JButton("Log in");
        logInButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(logInButton);
        panel.add(Box.createRigidArea(new Dimension(0, padding)));

        // button actions to switch pages
        signUpButton.addActionListener(e -> cardLayout.show(mainPanel, "signup"));
        logInButton.addActionListener(e -> cardLayout.show(mainPanel, "login"));

        return panel;
    }
    private JPanel getSignUpPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(padding, padding, padding, padding));

        JLabel title = new JLabel("Create a new account");
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(title);
        panel.add(Box.createRigidArea(new Dimension(0, padding)));

        JLabel l1 = new JLabel("What is your nickname?");
        l1.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(l1);

        JTextField name = new JTextField();
        name.setMaximumSize(new Dimension(textBoxWidth, padding));
        name.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(name);
        panel.add(Box.createRigidArea(new Dimension(0, padding)));

        JLabel l2 = new JLabel("Enter your email: ");
        l2.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(l2);

        JTextField email = new JTextField();
        email.setMaximumSize(new Dimension(textBoxWidth, padding));
        email.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(email);
        panel.add(Box.createRigidArea(new Dimension(0, padding)));

        JLabel l3 = new JLabel("Create a password: ");
        l3.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(l3);

        JPasswordField password = new JPasswordField();
        password.setMaximumSize(new Dimension(textBoxWidth, padding));
        password.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(password);
        panel.add(Box.createRigidArea(new Dimension(0, padding)));

        JButton backButton = new JButton("Back");
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        backButton.addActionListener(e -> cardLayout.show(mainPanel, "welcome"));
        panel.add(backButton);

        JButton continueButton = new JButton("Continue");
        continueButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        continueButton.addActionListener(e -> {
            if (name.getText().isEmpty() || email.getText().isEmpty() || password.getPassword().length == 0) {
                System.out.println("Incomplete form submission when signing up.");
                util.Error.showError(util.Error.ErrorType.INCOMPLETE_FORM_SUBMISSION);
            }
            else if (!Security.isValidEmail(email.getText())) {
                System.out.println("Invalid email address when signing up.");
                util.Error.showError(util.Error.ErrorType.INVALID_EMAIL);
            }
            else {
                StudentProfile newStudent = new StudentProfile(name.getText(), email.getText(), password.getPassword());
                System.out.println("New student profile created with id " + newStudent.getProfileID() + ", name " + newStudent.getName() + ", email " + newStudent.getEmail() + ", and password " + newStudent.showHiddenPassword());
                cardLayout.show(mainPanel, "homepage");
            }
        });
        panel.add(continueButton);

        return panel;
    }
    private JPanel getLogInPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(padding, padding, padding, padding));

        JLabel title = new JLabel("Log into your account");
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(title);
        panel.add(Box.createRigidArea(new Dimension(0, padding)));

        JLabel l1 = new JLabel("Email: ");
        l1.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(l1);

        JTextField email = new JTextField();
        email.setMaximumSize(new Dimension(textBoxWidth, padding));
        email.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(email);
        panel.add(Box.createRigidArea(new Dimension(0, padding)));

        JLabel l2 = new JLabel("Password: ");
        l2.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(l2);

        JPasswordField password = new JPasswordField();
        password.setMaximumSize(new Dimension(textBoxWidth, padding));
        password.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(password);
        panel.add(Box.createRigidArea(new Dimension(0, padding)));

        JButton backButton = new JButton("Back");
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        backButton.addActionListener(e -> cardLayout.show(mainPanel, "welcome"));
        panel.add(backButton);

        JButton continueBtn = new JButton("Continue");
        continueBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        continueBtn.addActionListener(e -> {
            if (email.getText().isEmpty() || password.getPassword().length == 0) {
                System.out.println("Incomplete form submission when logging in.");
                util.Error.showError(util.Error.ErrorType.INCOMPLETE_FORM_SUBMISSION);
            }
            else if (!Security.isValidEmail(email.getText())) {
                System.out.println("Invalid email address when logging in.");
                util.Error.showError(util.Error.ErrorType.INVALID_EMAIL);
            }
            else if (!Security.verifyCredentials()) {
                System.out.println("Incorrect credentials when logging in.");
                showError(Error.ErrorType.INCORRECT_CREDENTIALS);
            }
            else {
                // fetch user data from database instead of creating a new one
                StudentProfile userSession = new StudentProfile("name", email.getText(), password.getPassword());
                System.out.println("User with id " + userSession.getProfileID() + " logged into session.");
                cardLayout.show(mainPanel, "homepage");
            }
        });
        panel.add(continueBtn);

        return panel;
    }
    private JPanel getHomePagePanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(padding, padding, padding, padding));

        // fetch students data from database and display on here

        JButton profileBtn = new JButton("Profile");
        profileBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        profileBtn.addActionListener(e -> {
            cardLayout.show(mainPanel, "profile");
        });
        panel.add(profileBtn);

        JButton settingsBtn = new JButton("Settings");
        settingsBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        settingsBtn.addActionListener(e -> {
            cardLayout.show(mainPanel, "settings");
        });
        panel.add(settingsBtn);

        JButton subjectsBtn = new JButton("Edit subjects");
        subjectsBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        subjectsBtn.addActionListener(e -> {
            cardLayout.show(mainPanel, "subjects");
        });
        panel.add(subjectsBtn);

        return panel;
    }
    private JPanel getProfilePanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(padding, padding, padding, padding));

        JLabel title = new JLabel("Profile");
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(title);
        panel.add(Box.createRigidArea(new Dimension(0, padding)));

        JButton homepageBtn = new JButton("Return to homepage");
        homepageBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        homepageBtn.addActionListener(e -> {
            cardLayout.show(mainPanel, "homepage");
        });
        panel.add(homepageBtn);

        return panel;
    }
    private JPanel getSettingsPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(padding, padding, padding, padding));

        JLabel title = new JLabel("Settings");
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(title);
        panel.add(Box.createRigidArea(new Dimension(0, padding)));

        JButton homepageBtn = new JButton("Return to homepage");
        homepageBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        homepageBtn.addActionListener(e -> {
            cardLayout.show(mainPanel, "homepage");
        });
        panel.add(homepageBtn);

        return panel;
    }
    private JPanel getSubjectsPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(padding, padding, padding, padding));

        JLabel title = new JLabel("Subjects");
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(title);
        panel.add(Box.createRigidArea(new Dimension(0, padding)));

        JButton homepageBtn = new JButton("Return to homepage");
        homepageBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        homepageBtn.addActionListener(e -> {
            cardLayout.show(mainPanel, "homepage");
        });
        panel.add(homepageBtn);

        return panel;
    }
}
