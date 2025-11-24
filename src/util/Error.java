package util;

import model.App;

import javax.swing.*;
import java.awt.*;

public class Error {

    public enum ErrorType {
        INVALID_EMAIL,
        INCOMPLETE_FORM_SUBMISSION,
        INCORRECT_CREDENTIALS,
        INCOMPLETE_TASK_TITLE,
        INCOMPLETE_NICKNAME
    }

    public static void showError(ErrorType err) {
        JDialog dialog = new JDialog(App.frame, "Error", true); // true = modal
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(App.padding, App.padding, App.padding, App.padding));

        switch (err) {
            case INCOMPLETE_FORM_SUBMISSION:
                panel.add(App.createLabelP("Please complete all fields before continuing"));
                break;
            case INCORRECT_CREDENTIALS:
                panel.add(App.createLabelP("Incorrect credentials"));
                panel.add(App.createLabelP("Please double check and retry"));
                break;
            case INVALID_EMAIL:
                panel.add(App.createLabelP("Please enter a valid email address"));
                break;
            case INCOMPLETE_TASK_TITLE:
                panel.add(App.createLabelP("Please add a task title to continue"));
                break;
            case INCOMPLETE_NICKNAME:
                panel.add(App.createLabelP("Please enter your new desired nickname"));
                break;
        }

        panel.add(App.createButton("Close", e -> dialog.dispose()));

        dialog.add(panel);
        dialog.pack();
        dialog.setLocationRelativeTo(App.frame);
        dialog.setVisible(true);
    }
}