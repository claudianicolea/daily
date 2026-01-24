package view.panels;

import dao.StudentProfileDAO;
import main.App;
import view.dialogs.EditNicknameDialog;
import javax.swing.*;
import static main.App.*;
import static view.UserInterfaceUtils.*;

public class ProfilePanel extends JPanel {

    private JLabel nicknameLabel; // dynamic label reference

    public ProfilePanel(App app) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(padding, padding, padding, padding));
        add(createLabelH1("Profile"));

        nicknameLabel = createLabelH2("Nickname: " + user.getName());
        add(nicknameLabel);

        add(createButton("Edit nickname", e -> {
            String newNickname = EditNicknameDialog.showDialog(frame);
            if (newNickname != null && !newNickname.isEmpty() && !newNickname.equals(user.getName())) {
                nicknameLabel.setText("Nickname: " + newNickname);
                StudentProfileDAO dao = new StudentProfileDAO();
                dao.updateStudentProfile(user);
            }
        }));

        add(createLabelH2("Email address: " + user.getEmail()));
        add(createButton("Return to homepage", e -> app.showPanel("homepage")));
    }
}