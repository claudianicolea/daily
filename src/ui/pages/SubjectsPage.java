package ui.pages;

import dao.SubjectDAO;
import main.Main;
import model.Subject;
import ui.elements.BodyText;
import ui.elements.Button;
import ui.elements.Dialog;
import ui.elements.Title;
import util.LinkedList;
import util.Node;
import util.Page;

import javax.swing.*;
import java.awt.*;

import static main.Main.settings;
import static main.Main.user;

public class SubjectsPage extends JPanel {
    public SubjectsPage() {
        setLayout(new BorderLayout());

        // top

        JPanel top = new JPanel();
        top.add(new Title("Subjects"));
        top.setBackground(settings.getAccentColor());
        top.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 2, Color.GRAY));
        add(top, BorderLayout.NORTH);

        //center

        JPanel center = new JPanel();
        center.setLayout(new BorderLayout());

        JPanel subjects = new JPanel();
        subjects.setBackground(Color.WHITE);

        // add subject
        center.add(new Button(
                "Add",
                e -> {
                    String newSubjectName = Dialog.showInput(
                            "Subject name:",
                            "",
                            "Add Subject"
                    );

                    if (newSubjectName != null && !newSubjectName.isEmpty()) {
                        Subject s = new Subject(null, user.getProfileID(), newSubjectName);
                        SubjectDAO.insertSubject(s, s.getProfileID());
                        refreshSubjects(subjects);
                    }
                },
                true
        ), BorderLayout.NORTH);

        // subjects list

        subjects.setLayout(new BoxLayout(subjects, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(subjects);
        scrollPane.setBackground(Color.WHITE);
        center.add(scrollPane, BorderLayout.CENTER);

        refreshSubjects(subjects);

        center.setBackground(Color.WHITE);
        add(center, BorderLayout.CENTER);

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

    private void refreshSubjects(JPanel panel) {
        panel.removeAll();

        LinkedList subjects = SubjectDAO.getSubjectsByProfile(user.getProfileID());
        Node current = subjects.getHead();

        while (current != null) {
            Subject s = current.getSubjectValue();

            // each subject block
            JPanel sPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));

            BodyText name = new BodyText(s.getName());
            sPanel.add(name);

            sPanel.add(new Button("Edit", e -> {
                String newSubjectName = Dialog.showInput(
                        "Enter new subject name:", s.getName(), "Edit Subject");
                if (newSubjectName != null && !newSubjectName.isEmpty()) {
                    s.setName(newSubjectName);
                    SubjectDAO.updateSubject(s);
                    refreshSubjects(panel);
                }
            }));
            sPanel.add(new Button("Delete", e -> {
                int confirm = Dialog.showConfirm(
                        "Are you sure you want to delete " + s.getName() + "?", "Delete Subject");
                if (confirm == JOptionPane.YES_OPTION) {
                    SubjectDAO.deleteSubject(s.getSubjectID());
                    refreshSubjects(panel);
                }
            }));

            // prevent stretching
            sPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
            sPanel.setMaximumSize(sPanel.getPreferredSize());
            sPanel.setBackground(Color.WHITE);

            panel.add(sPanel);
            panel.add(Box.createVerticalStrut(5)); // spacing between subjects

            current = current.getNext();
        }

        panel.revalidate();
        panel.repaint();
    }
}