package main;

import model.Profile;
import model.Settings;
import ui.*;
import util.Page;

import javax.swing.*;

public class Main {
    //TODO Optimize imports for all classes
    public static Profile user;
    public static Settings settings;
    public static JFrame frame = new JFrame("Daily");

    static void main() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        showPanel(Page.WELCOME);
        frame.setVisible(true);
    }

    public static void showPanel(Page page) {
        frame.getContentPane().removeAll();

        switch (page) {
            case WELCOME:
                frame.setSize(500, 400);
                frame.setLocationRelativeTo(null);
                frame.add(new WelcomePage());
                break;
            case HOMEPAGE:
                frame.setSize(1000, 500);
                frame.setLocationRelativeTo(null);
                frame.add(new HomePage());
                break;
            case SETTINGS:
                frame.setSize(500, 400);
                frame.setLocationRelativeTo(null);
                frame.add(new SettingsPage());
                break;
            case PROFILE:
                frame.setSize(500, 400);
                frame.setLocationRelativeTo(null);
                frame.add(new ProfilePage());
                break;
            case SUBJECTS:
                frame.setSize(500, 400);
                frame.setLocationRelativeTo(null);
                frame.add(new SubjectsPage());
                break;
        }

        frame.revalidate();
    }
}
