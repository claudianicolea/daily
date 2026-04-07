package ui;

import dao.SubjectDAO;
import main.Main;
import model.Subject;
import util.LinkedList;
import util.Node;
import util.Page;

import javax.swing.*;

import java.awt.*;

import static main.Main.user;

public class SubjectsPage extends JPanel {
    // TODO Revise subjects page UI
    public SubjectsPage() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("Subjects");
        title.setAlignmentX(CENTER_ALIGNMENT);
        add(title);

        // subjects list
        JPanel subjects = new JPanel();
        subjects.setLayout(new BoxLayout(subjects, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(subjects);
        add(scrollPane);

        refreshSubjects(subjects);

        // add subject
        JButton addBtn = new JButton("Add");
        addBtn.addActionListener(e -> {
            String newSubjectName = JOptionPane.showInputDialog(
                    null,
                    "Subject name:",
                    "Add subject",
                    JOptionPane.PLAIN_MESSAGE
            );
            if (newSubjectName.isEmpty()) {
                JOptionPane.showMessageDialog(
                        null,
                        "Please add a subject name!",
                        "Warning",
                        JOptionPane.WARNING_MESSAGE
                );
                return;
            }
            Subject subject = new Subject(null, user.getProfileID(), newSubjectName);
            SubjectDAO.insertSubject(subject, subject.getProfileID());
            refreshSubjects(subjects);
        });
        addBtn.setAlignmentX(CENTER_ALIGNMENT);
        add(addBtn);

        // button to return to homepage
        JButton homepageBtn = new JButton("Return");
        homepageBtn.addActionListener(e -> Main.showPanel(Page.HOMEPAGE));
        homepageBtn.setAlignmentX(CENTER_ALIGNMENT);
        add(homepageBtn);
    }

    private void refreshSubjects(JPanel panel) {
        panel.removeAll();

        LinkedList subjects = SubjectDAO.getSubjectsByProfile(user.getProfileID());

        Node current = subjects.getHead();
        while (current != null) {
            Subject s = current.getSubjectValue();

            JPanel sPanel = new JPanel();
            JLabel name = new JLabel(s.getName());
            sPanel.add(name);

            JButton editBtn = new JButton("Edit");
            editBtn.addActionListener(e -> {
                String newSubjectName = JOptionPane.showInputDialog(
                        null,
                        "Subject name:",
                        "Edit subject",
                        JOptionPane.PLAIN_MESSAGE
                );
                if (newSubjectName.isEmpty()) {
                    JOptionPane.showMessageDialog(
                            null,
                            "Please add a subject name!",
                            "Warning",
                            JOptionPane.WARNING_MESSAGE
                    );
                    return;
                }
                s.setName(newSubjectName);
                SubjectDAO.updateSubject(s);
                refreshSubjects(panel);
            });
            sPanel.add(editBtn);

            JButton deleteBtn = new JButton("Delete");
            deleteBtn.addActionListener(e -> {
                int confirm = JOptionPane.showConfirmDialog(
                        null,
                        "Are you sure you want to delete " + s.getName() + "?",
                        "Delete Subject",
                        JOptionPane.YES_NO_OPTION
                );
                if (confirm == JOptionPane.YES_OPTION) {
                    SubjectDAO.deleteSubject(s.getSubjectID());
                    refreshSubjects(panel);
                }
            });
            sPanel.add(deleteBtn);
            panel.add(sPanel);
            current = current.getNext();
        }

        panel.revalidate();
        panel.repaint();
    }
}