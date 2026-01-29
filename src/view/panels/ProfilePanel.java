package view.panels;

import dao.StudentProfileDAO;
import main.App;

import javax.swing.*;

import static main.App.padding;
import static main.App.user;
import static view.UserInterfaceUtils.*;

public class ProfilePanel extends JPanel {

    private JLabel nicknameLabel;

    public ProfilePanel(App app) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(padding, padding, padding, padding));
        add(createLabelH1("Profile"));

        nicknameLabel = createLabelH2("Nickname: " + user.getName());
        add(nicknameLabel);

        JButton editButton = createButton("Edit", e -> {
            String newNickname = JOptionPane.showInputDialog(this, "Enter new nickname:", user.getName());

            if (newNickname != null && !newNickname.isEmpty()) {
                user.setName(newNickname);
                StudentProfileDAO.updateStudentProfile(user);
                nicknameLabel.setText("Nickname: " + user.getName());
                revalidate();
                repaint();
            }
        });
        add(editButton);


        add(createLabelH2("Email address: " + user.getEmail()));
        add(createButton("Return to homepage", e -> app.showPanel("homepage")));
    }
}