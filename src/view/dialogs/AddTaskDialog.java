package view.dialogs;

import dao.TaskDAO;
import model.Subject;
import model.Task;
import util.DateUtils;
import javax.swing.*;
import static main.App.padding;
import static view.UserInterfaceUtils.createButton;
import static view.UserInterfaceUtils.createTextField;

public class AddTaskDialog extends JDialog {

    private AddTaskDialog(JFrame parent, Subject subject) {
        super(parent, "Add New Task", true);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(padding, padding, padding, padding));

        panel.add(new JLabel("Task title:"));
        JTextField titleField = createTextField();
        panel.add(titleField);

        panel.add(new JLabel("Task type:"));
        String[] types = {"Homework", "Internal Assessment", "Exam Study"};
        JComboBox<String> typeCombo = new JComboBox<>(types);
        panel.add(typeCombo);

        panel.add(new JLabel("Due date (yyyy-MM-dd):"));
        JTextField dueDateField = createTextField();
        panel.add(dueDateField);

        panel.add(new JLabel("Description:"));
        JTextField descriptionField = createTextField();
        panel.add(descriptionField);

        panel.add(createButton("Cancel", e -> dispose()));
        panel.add(createButton("Add Task", e -> {
            String title = titleField.getText().trim();
            String type = (String) typeCombo.getSelectedItem();
            String dueDateText = dueDateField.getText().trim();
            String description = descriptionField.getText().trim();

            if (title.isEmpty() || dueDateText.isEmpty() || type.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill in all required fields.");
                return;
            }

            DateUtils deadline;
            try {
                String[] parts = dueDateText.split("-");
                int year = Integer.parseInt(parts[0]);
                int month = Integer.parseInt(parts[1]);
                int day = Integer.parseInt(parts[2]);
                deadline = new DateUtils(day, month, year);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Invalid date format. Use yyyy-MM-dd");
                return;
            }

            Task task = new Task(null, subject.getSubjectID(), title);
            task.setDescription(description);
            task.setDeadline(deadline);

            TaskDAO dao = new TaskDAO();
            dao.insertTask(task);

            dispose();
        }));

        add(panel);
        pack();
        setLocationRelativeTo(parent);
        setVisible(true);
    }

    public static void showDialog(JFrame parent, Subject subject) {
        new AddTaskDialog(parent, subject);
    }
}