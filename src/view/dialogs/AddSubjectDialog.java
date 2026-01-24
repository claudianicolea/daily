package view.dialogs;

import javax.swing.*;
import static main.App.padding;
import static view.UserInterfaceUtils.createButton;
import static view.UserInterfaceUtils.createTextField;

public class AddSubjectDialog extends JDialog {

    private String subjectName = null;

    private AddSubjectDialog(JFrame parent) {
        super(parent, "Add New Subject", true);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(padding, padding, padding, padding));

        JTextField nameField = createTextField();
        panel.add(new JLabel("Subject name:"));
        panel.add(nameField);

        panel.add(createButton("Cancel", e -> dispose()));
        panel.add(createButton("Add", e -> {
            String name = nameField.getText().trim();
            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter a subject name.");
            } else {
                subjectName = name;
                dispose();
            }
        }));

        add(panel);
        pack();
        setLocationRelativeTo(parent);
        setVisible(true);
    }

    public static String showDialog(JFrame parent) {
        AddSubjectDialog dialog = new AddSubjectDialog(parent);
        return dialog.subjectName;
    }
}
