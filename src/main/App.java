package main;

import model.StudentProfile;
import view.panels.*;

import javax.swing.*;
import java.awt.*;

public class App {
    public static String APP_NAME = "Daily";

    public static JFrame frame = new JFrame(APP_NAME);
    public static JPanel mainPanel; // holds all screens
    public static CardLayout cardLayout;

    public static int mainWindowWidth = 1000;
    public static int mainWindowHeight = 800;
    public static int padding = 20;
    public static int textBoxWidth = 300;
    public static StudentProfile user; // change when adding database?

    App() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(mainWindowWidth, mainWindowHeight);
        frame.setLocationRelativeTo(null);

        // CardLayout to switch screens
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        WelcomePanel welcome = new WelcomePanel(this);
        mainPanel.add(welcome, "welcome");

        frame.add(mainPanel);
        cardLayout.show(mainPanel, "welcome");
        frame.setVisible(true);
    }

    // methods to display panels

    public void showPanel(String name) {
        switch (name) {
            case "welcome":
                mainPanel.add(new WelcomePanel(this), "welcome");
                break;
            case "homepage":
                mainPanel.add(new HomePagePanel(this), "homepage");
                break;
            case "settings":
                mainPanel.add(new SettingsPanel(this), "settings");
                break;
            case "profile":
                mainPanel.add(new ProfilePanel(this), "profile");
                break;
            case "subjects":
                mainPanel.add(new SubjectsPanel(this), "subjects");
                break;
        }
        cardLayout.show(mainPanel, name);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new App());
    }
}
