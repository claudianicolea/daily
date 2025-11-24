package model.dialogs;

import model.App;
import model.tasks.Task;
import util.Date;

import javax.swing.*;
import java.awt.*;

public class AddTaskDialog extends JDialog {

    public AddTaskDialog(JFrame parent) {
        super(parent, "Add Task", true);

        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(500, 400));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(App.padding, App.padding, App.padding, App.padding));

        panel.add(App.createLabelP("Title: "));
        JTextField title = App.createTextField();
        panel.add(title);

        panel.add(App.createLabelP("Deadline: "));
        Date deadline = Date.getTodayDate();
        // add menu to choose a date from the calendar

        panel.add(App.createLabelP("Description: "));
        JTextField description = App.createTextField();
        panel.add(description);

        panel.add(App.createButton("Cancel", e -> dispose()));
        panel.add(App.createButton("Add", e -> {
            if (title.getText().isEmpty()) {
                System.out.println("Incomplete text field naming a new task.");
                util.Error.showError(util.Error.ErrorType.INCOMPLETE_TASK_TITLE);
            }
            else {
                Task newTask = new Task(title.getText());
                newTask.setDeadline(deadline);
                newTask.setDescription(description.getText());
                System.out.println("New task was created with title " + title.getText());
                // save task
                dispose();
            }
        }));

        add(panel);
        pack();
        setLocationRelativeTo(parent);
        setVisible(true);
    }
}
