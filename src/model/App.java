package model;

import config.Settings;
import model.dialogs.*;
import util.*;
import util.Error;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.function.Consumer;
import static util.Error.*;

public class App {
    public static String APP_NAME = "Daily";

    public static JFrame frame = new JFrame(APP_NAME);
    public static JPanel mainPanel; // holds all screens
    public static CardLayout cardLayout;

    public static int mainWindowWidth = 1000;
    public static int mainWindowHeight = 800;
    public static int padding = 20;
    public static int textBoxWidth = 300;
    public static StudentProfile user;

    App() {
        // frame setup
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

        frame.add(mainPanel);
        frame.setVisible(true);
    }

    // methods to display panels

    private JPanel getWelcomePanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(padding, padding, padding, padding));
        panel.add(createLabelH1("Welcome to Daily"));

        panel.add(createButton("Sign up", e -> cardLayout.show(mainPanel, "signup")));
        panel.add(createButton("Log in", e -> cardLayout.show(mainPanel, "login")));

        return panel;
    }
    private JPanel getSignUpPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(padding, padding, padding, padding));
        panel.add(createLabelH1("Create a new account"));

        panel.add(createLabelH2("What is your nickname?"));
        JTextField name = createTextField();
        panel.add(name);

        panel.add(createLabelH2("Enter your email: "));
        JTextField email = createTextField();
        panel.add(email);

        panel.add(createLabelH2("Create a password:"));
        JPasswordField password = new JPasswordField();
        password.setMaximumSize(new Dimension(textBoxWidth, padding));
        password.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(password);

        panel.add(createButton("Back", e -> cardLayout.show(mainPanel, "welcome")));
        panel.add(createButton("Continue", e -> {
            if (name.getText().isEmpty() || email.getText().isEmpty() || password.getPassword().length == 0) {
                System.out.println("Incomplete form submission when signing up.");
                util.Error.showError(util.Error.ErrorType.INCOMPLETE_FORM_SUBMISSION);
            }
            else if (!Security.isValidEmail(email.getText())) {
                System.out.println("Invalid email address when signing up.");
                util.Error.showError(util.Error.ErrorType.INVALID_EMAIL);
            }
            else {
                user = new StudentProfile(name.getText(), email.getText(), password.getPassword());
                System.out.println("New student profile created with id " + user.getProfileID() + ", name " + user.getName() + ", email " + user.getEmail() + ", and password " + user.showHiddenPassword());
                mainPanel.add(getHomePagePanel(), "homepage");
                cardLayout.show(mainPanel, "homepage");
            }
        }));

        return panel;
    }
    private JPanel getLogInPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(padding, padding, padding, padding));
        panel.add(createLabelH1("Log into your account"));

        panel.add(createLabelH2("Email: "));
        JTextField email = createTextField();
        panel.add(email);

        panel.add(createLabelH2("Password: "));
        JPasswordField password = new JPasswordField();
        password.setMaximumSize(new Dimension(textBoxWidth, padding));
        password.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(password);

        panel.add(createButton("Back", e -> cardLayout.show(mainPanel, "welcome")));
        panel.add(createButton("Continue", e -> {
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
                user = new StudentProfile("name", email.getText(), password.getPassword());
                System.out.println("User with id " + user.getProfileID() + " logged into session.");
                mainPanel.add(getHomePagePanel(), "homepage");
                cardLayout.show(mainPanel, "homepage");
            }
        }));

        return panel;
    }
    private JPanel getHomePagePanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(padding, padding, padding, padding));

        JPanel left = new JPanel();
        JPanel center = new JPanel();
        JPanel right = new JPanel();

        if (user != null) {
            left.setBackground(user.getSettings().getAccentAwtColor());
            center.setBackground(MyColor.WHITE.toAwtColor());
            right.setBackground(user.getSettings().getAccentAwtColor());
        }

        left.setPreferredSize(new Dimension(200, 0));
        right.setPreferredSize(new Dimension(200, 0));

        panel.add(left, BorderLayout.WEST);
        panel.add(center, BorderLayout.CENTER);
        panel.add(right, BorderLayout.EAST);

        // left panel
        left.add(createButton("Profile", e -> {
            mainPanel.add(getProfilePanel(), "profile");
            cardLayout.show(mainPanel, "profile");
        }));
        left.add(createButton("Settings", e -> {
            mainPanel.add(getSettingsPanel(), "settings");
            cardLayout.show(mainPanel, "settings");
        }));
        left.add(createButton("Edit subjects", e -> {
            mainPanel.add(getSubjectsPanel(), "subjects");
            cardLayout.show(mainPanel, "subjects");
        }));
        left.add(createLabelH1("Subjects"));

        // center panel

        center.add(createButton("Add task", e -> new AddTaskDialog(frame)));
        center.add(createLabelH1("Tasks"));

        // right panel

        right.add(createLabelH1("Detailed view"));

        return panel;
    }
    private JPanel getProfilePanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(padding, padding, padding, padding));
        panel.add(createLabelH1("Profile"));

        panel.add(createLabelH2("Nickname: " + user.getName()));
        panel.add(createButton("Edit nickname", e -> {
            new EditNicknameDialog(frame);
            mainPanel.add(getProfilePanel(), "profile"); // rebuild panel to display new nickname
            App.cardLayout.show(mainPanel, "profile");
        }));
        panel.add(createLabelH2("Email address: " + user.getEmail()));

        panel.add(createButton("Return to homepage", e -> {
            mainPanel.add(getHomePagePanel(), "homepage");
            cardLayout.show(mainPanel, "homepage");
        }));

        return panel;
    }
    private JPanel getSettingsPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(padding, padding, padding, padding));
        panel.add(createLabelH1("Settings"));

        // display mode
        panel.add(createLabelH2("Display mode: "));
        ButtonGroup displayModeGroup = new ButtonGroup();
        JRadioButton lightMode = createRadioButton("Light mode", () -> {
            user.getSettings().setDisplayMode(Settings.DisplayMode.LIGHT_MODE);
            System.out.println("Display mode changed to light");
        });
        JRadioButton darkMode = createRadioButton("Dark mode", () -> {
            user.getSettings().setDisplayMode(Settings.DisplayMode.DARK_MODE);
            System.out.println("Display mode changed to dark");
        });
        displayModeGroup.add(lightMode);
        displayModeGroup.add(darkMode);
        panel.add(lightMode);
        panel.add(darkMode);

        // accent color
        panel.add(createLabelH2("Accent color: "));
        String[] colors = Arrays.stream(MyColor.ColorName.values()).map(Enum::name).toArray(String[]::new);
        JComboBox<String> accentColor = createComboBox(colors, color -> {
            user.getSettings().setAccentColor(MyColor.ColorName.valueOf(color));
            System.out.println("Accent color changed to " + color);
        });
        panel.add(accentColor);

        // subject sort mode
        panel.add(createLabelH2("Sort subjects by: "));
        ButtonGroup sortGroupButtons = new ButtonGroup();
        JRadioButton sortAlpha = createRadioButton("Alphabetical order", () -> {
            user.getSettings().setSubjectSortMode(Settings.SubjectSortMode.ALPHABETICAL);
            System.out.println("Subjects will now be sorted alphabetically.");
        });
        JRadioButton sortGroup = createRadioButton("Subject group", () -> {
            user.getSettings().setSubjectSortMode(Settings.SubjectSortMode.BY_SUBJECT_GROUP);
            System.out.println("Subjects will now be sorted by subject group.");
        });
        JRadioButton sortLevel = createRadioButton("Subject level", () -> {
            user.getSettings().setSubjectSortMode(Settings.SubjectSortMode.BY_SUBJECT_LEVEL);
            System.out.println("Subjects will now be sorted by subject level.");
        });
        sortGroupButtons.add(sortAlpha);
        sortGroupButtons.add(sortGroup);
        sortGroupButtons.add(sortLevel);
        panel.add(sortAlpha);
        panel.add(sortGroup);
        panel.add(sortLevel);

        // show completed tasks
        panel.add(createLabelH2("Show completed tasks: "));
        JToggleButton showCompleted = createJToggleButton(
                "Show completed",
                selected -> {
                    user.getSettings().setShowCompleted(selected);
                    System.out.println("Show completed tasks: " + selected);
                },
                user.getSettings().getShowCompleted()
        );
        panel.add(showCompleted);

        panel.add(createButton("Return to homepage", e -> {
            mainPanel.add(getHomePagePanel(), "homepage");
            cardLayout.show(mainPanel, "homepage");
        }));

        return panel;
    }
    private JPanel getSubjectsPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(padding, padding, padding, padding));
        panel.add(createLabelH1("Subjects"));

        panel.add(createButton("Return to homepage", e -> {
            mainPanel.add(getHomePagePanel(), "homepage");
            cardLayout.show(mainPanel, "homepage");
        }));

        return panel;
    }

    // methods to create Swing elements

    public static JButton createButton(String text, ActionListener e) {
        JButton b = new JButton(text);
        b.setAlignmentX(Component.CENTER_ALIGNMENT);
        b.addActionListener(e);
        return b;
    }
    public static JLabel createLabelH1(String text) {
        JLabel l = new JLabel(text);
        l.setFont(new Font("Arial", Font.BOLD, 24));
        l.setAlignmentX(Component.CENTER_ALIGNMENT);
        return l;
    }
    public static JLabel createLabelH2(String text) {
        JLabel l = new JLabel(text);
        l.setFont(new Font("Arial", Font.PLAIN, 18));
        l.setAlignmentX(Component.CENTER_ALIGNMENT);
        return l;
    }
    public static JLabel createLabelP(String text) {
        JLabel l = new JLabel(text);
        l.setFont(new Font("Arial", Font.PLAIN, 15));
        l.setAlignmentX(Component.CENTER_ALIGNMENT);
        return l;
    }
    public static JTextField createTextField() {
        JTextField t = new JTextField();
        t.setMaximumSize(new Dimension(textBoxWidth, padding));
        t.setAlignmentX(Component.CENTER_ALIGNMENT);
        return t;
    }
    private JRadioButton createRadioButton(String text, Runnable onClick) {
        JRadioButton r = new JRadioButton(text);
        r.addActionListener(e -> onClick.run());
        return r;
    }
    private JToggleButton createJToggleButton(String text, Consumer<Boolean> onToggle, boolean initialState) {
        JToggleButton t = new JToggleButton(text, initialState);
        t.addItemListener(e -> onToggle.accept(t.isSelected()));
        return t;
    }
    private JComboBox<String> createComboBox(String[] items, Consumer<String> onChange) {
        JComboBox<String> c = new JComboBox<>(items);
        c.addActionListener(e -> onChange.accept((String)c.getSelectedItem()));
        return c;
    }
}
