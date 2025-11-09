package util;

import model.App;
import model.App.*;

import javax.swing.*;
import java.awt.*;

public class Error {
    public enum ErrorType {
        INVALID_EMAIL,
        INCOMPLETE_FORM_SUBMISSION,
        INCORRECT_CREDENTIALS
    }

    public static void showError(Error.ErrorType err) {
        JFrame frame = new JFrame("Error");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(App.mainWindowWidth / 2, App.mainWindowHeight / 2);
        frame.setLocationRelativeTo(null);

        JPanel errorPanel = new JPanel(App.cardLayout);
        errorPanel.add(errorPanel(frame, err), "error");

        frame.add(errorPanel);
        frame.setVisible(true);
    }

    // error pop up panel
    public static JPanel errorPanel(JFrame frame, Error.ErrorType err) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(App.padding, App.padding, App.padding, App.padding));

        switch (err) {
            case Error.ErrorType.INCOMPLETE_FORM_SUBMISSION:
                JLabel l1 = new JLabel("Please complete all fields before continuing");
                l1.setAlignmentX(Component.CENTER_ALIGNMENT);
                panel.add(l1);
                break;
            case Error.ErrorType.INCORRECT_CREDENTIALS:
                JLabel l2 = new JLabel("Incorrect credentials");
                l2.setAlignmentX(Component.CENTER_ALIGNMENT);
                panel.add(l2);
                JLabel l3 = new JLabel("Please double check and retry");
                l3.setAlignmentX(Component.CENTER_ALIGNMENT);
                panel.add(l3);
                break;
            case Error.ErrorType.INVALID_EMAIL:
                JLabel l4 = new JLabel("Please enter a valid email address");
                l4.setAlignmentX(Component.CENTER_ALIGNMENT);
                panel.add(l4);
                break;
        }

        JButton closeButton = new JButton("Close");
        closeButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        closeButton.addActionListener(e -> {
            frame.dispose();
        });
        panel.add(closeButton);

        return panel;
    }
}
