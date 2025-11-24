package model.dialogs;

import model.App;
import model.tasks.Task;
import util.Date;

import javax.swing.*;

import java.awt.*;

import static model.App.user;

public class EditNicknameDialog extends JDialog {

    public EditNicknameDialog(JFrame parent) {
        super(parent, "Edit nickname", true);

        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(500, 150));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(App.padding, App.padding, App.padding, App.padding));

        JTextField newNickname = App.createTextField();
        panel.add(newNickname);

        panel.add(App.createButton("Cancel", e -> dispose()));
        panel.add(App.createButton("Confirm", e -> {
            if (newNickname.getText().isEmpty()) {
                System.out.println("Incomplete text field when editing user nickname.");
                util.Error.showError(util.Error.ErrorType.INCOMPLETE_NICKNAME);
            }
            else {
                user.setName(newNickname.getText());
                System.out.println("User nickname was changed to " + newNickname.getText());
                dispose();
            }
        }));

        add(panel);
        pack();
        setLocationRelativeTo(parent);
        setVisible(true);
    }
}
