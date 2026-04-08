package ui.pages;

import dao.ProfileDAO;
import main.Main;
import model.Settings;
import ui.elements.BodyText;
import ui.elements.Button;
import ui.elements.Dialog;
import ui.elements.Title;
import util.Page;

import javax.swing.*;
import java.awt.*;

import static main.Main.settings;
import static main.Main.user;

public class ProfilePage extends JPanel {
    public ProfilePage() {
        setLayout(new BorderLayout());

        // top

        JPanel top = new JPanel();
        top.add(new Title("Profile"));
        top.setBackground(settings.getAccentColor());
        top.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.GRAY));
        add(top, BorderLayout.NORTH);

        // center

        JPanel centerWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 20));
        centerWrapper.setBackground(Color.WHITE);

        JPanel center = new JPanel();
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));
        center.setBackground(Color.WHITE);

        // name

        JPanel namePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
        namePanel.setBackground(Color.WHITE);

        BodyText nameLabel = new BodyText("Name: " + user.getName());
        namePanel.add(nameLabel);

        namePanel.add(new Button(
                "Edit",
                e -> {
                    String newName = Dialog.showInput("Enter new name:", user.getName(), "Edit Name");

                    if (newName != null && !newName.isEmpty()) {
                        user.setName(newName);
                        ProfileDAO.updateProfile(user);
                        Main.showPanel(Page.PROFILE);
                    }
                },
                true
        ));

        namePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        namePanel.setMaximumSize(namePanel.getPreferredSize());

        center.add(namePanel);
        center.add(Box.createRigidArea(new Dimension(0, 20)));

        // email

        BodyText emailText = new BodyText("Email address: " + user.getEmail());
        emailText.setAlignmentX(Component.CENTER_ALIGNMENT);
        center.add(emailText);

        center.add(Box.createRigidArea(new Dimension(0, 20)));

        //log out

        Button logoutButton = new Button(
                "Log out",
                e -> {
                    int confirm = Dialog.showConfirm(
                            "Are you sure you want to log out?",
                            "Log out"
                    );
                    if (confirm == JOptionPane.YES_OPTION) {
                        Main.showPanel(Page.WELCOME);
                        Main.user = null;
                        Main.settings = new Settings();
                    }
                },
                true
        );
        logoutButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        center.add(logoutButton);

        centerWrapper.add(center);
        add(centerWrapper, BorderLayout.CENTER);

        // button to return to homepage
        JPanel bottom = new JPanel();
        bottom.add(new Button(
                "Return",
                e -> {
                    Main.showPanel(Page.HOMEPAGE);
                },
                true
        ));
        bottom.setBackground(settings.getAccentColor());
        bottom.setBorder(BorderFactory.createMatteBorder(2, 0, 0, 0, Color.GRAY));
        add(bottom, BorderLayout.SOUTH);
    }
}