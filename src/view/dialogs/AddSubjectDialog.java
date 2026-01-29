package view.dialogs;

import javax.swing.*;

import static main.App.padding;
import static view.UserInterfaceUtils.createButton;
import static view.UserInterfaceUtils.createTextField;

public class AddSubjectDialog extends JDialog {

    private String subjectName = null;

    private AddSubjectDialog(JFrame parent) {
        super(parent, "Add Subject", true);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(padding, padding, padding, padding));

        JTextField nameField = createTextField();
        panel.add(new JLabel("Subject name:"));
        panel.add(nameField);

        JPanel buttons = new JPanel();
        buttons.setLayout(new BoxLayout(buttons, BoxLayout.X_AXIS));

        JButton cancelButton = createButton("Cancel", e -> dispose());
        JButton addButton = createButton("Add", e -> {
            String name = nameField.getText().trim();
            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Enter a subject name.");
            } else {
                subjectName = name;
                dispose();
            }
        });

        buttons.add(cancelButton);
        buttons.add(Box.createHorizontalStrut(5));
        buttons.add(addButton);

        panel.add(Box.createVerticalStrut(10));
        panel.add(buttons);

        add(panel);
        pack();
        setLocationRelativeTo(parent);
        getRootPane().setDefaultButton(addButton);

        setVisible(true);
    }

    public static String showDialog(JFrame parent) {
        AddSubjectDialog dialog = new AddSubjectDialog(parent);
        return dialog.subjectName;
    }
}
