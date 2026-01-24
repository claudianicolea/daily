package view.dialogs;

import javax.swing.*;
import java.awt.*;
import static main.App.padding;
import static main.App.user;
import static view.UserInterfaceUtils.createButton;
import static view.UserInterfaceUtils.createTextField;

public class EditNicknameDialog extends JDialog {
    private String updatedNickname = null;

    private EditNicknameDialog(JFrame parent) {
        super(parent, "Edit nickname", true);

        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(500, 150));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(padding, padding, padding, padding));

        JTextField newNickname = createTextField();
        newNickname.setText(user.getName()); // prefill current nickname
        panel.add(newNickname);

        panel.add(createButton("Cancel", e -> dispose()));
        panel.add(createButton("Confirm", e -> {
            String nicknameText = newNickname.getText().trim();
            if (nicknameText.isEmpty()) {
                System.out.println("Incomplete text field when editing user nickname.");
                JOptionPane.showMessageDialog(this, "Please fill in all required fields.");
                return;
            }
            user.setName(nicknameText);
            updatedNickname = nicknameText;
            System.out.println("User nickname was changed to " + nicknameText);
            dispose();
        }));

        add(panel);
        pack();
        setLocationRelativeTo(parent);
        setVisible(true);
    }

    public static String showDialog(JFrame parent) {
        EditNicknameDialog dialog = new EditNicknameDialog(parent);
        return dialog.updatedNickname;
    }
}