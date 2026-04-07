package ui;

import dao.HomeworkDAO;
import dao.ProfileDAO;
import main.Main;
import model.Homework;
import util.Page;

import javax.swing.*;

import static main.Main.user;

public class ProfilePage extends JPanel {
    // TODO Revise profile page UI
    public ProfilePage() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("Profile");
        title.setAlignmentX(CENTER_ALIGNMENT);
        add(title);

        // edit name
        JLabel nameLabel = new JLabel("Name: " + user.getName());
        nameLabel.setAlignmentX(CENTER_ALIGNMENT);
        add(nameLabel);

        JButton editBtn = new JButton("Edit");
        editBtn.addActionListener(e -> {
            String newName = JOptionPane.showInputDialog(null,
                    "Enter new name:",
                    user.getName()
            );
            if (newName.isEmpty()) {
                JOptionPane.showMessageDialog(
                        null,
                        "Please add a name!",
                        "Warning",
                        JOptionPane.WARNING_MESSAGE
                );
                return;
            }
            user.setName(newName);
            ProfileDAO.updateProfile(user);
            nameLabel.setText("Name: " + user.getName());
            revalidate();
            repaint();
        });
        editBtn.setAlignmentX(CENTER_ALIGNMENT);
        add(editBtn);

        // show email
        JLabel email = new JLabel("Email address: " + user.getEmail());
        email.setAlignmentX(CENTER_ALIGNMENT);
        add(email);

        // log out

        JButton logOutBtn = new JButton("Log out");
        logOutBtn.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(
                    null,
                    "Are you sure you want to log out?",
                    "Log out",
                    JOptionPane.YES_NO_OPTION
            );
            if (confirm == JOptionPane.YES_OPTION) {
                Main.showPanel(Page.WELCOME);
                Main.user = null;
            }
        });
        logOutBtn.setAlignmentX(CENTER_ALIGNMENT);
        add(logOutBtn);

        // button to return to homepage
        JButton homepageBtn = new JButton("Return");
        homepageBtn.addActionListener(e -> {
            Main.showPanel(Page.HOMEPAGE);
        });
        homepageBtn.setAlignmentX(CENTER_ALIGNMENT);
        add(homepageBtn);
    }
}