package view.panels;

import dao.SubjectDAO;
import main.App;
import model.Subject;
import view.dialogs.AddSubjectDialog;
import javax.swing.*;
import java.awt.*;
import java.util.List;
import static main.App.*;
import static view.UserInterfaceUtils.*;

public class SubjectsPanel extends JPanel {
    private final JPanel subjectsListPanel;
    private final SubjectDAO subjectDAO = new SubjectDAO();

    public SubjectsPanel(App app) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(padding, padding, padding, padding));

        add(createLabelH1("Subjects"));

        subjectsListPanel = new JPanel();
        subjectsListPanel.setLayout(new BoxLayout(subjectsListPanel, BoxLayout.Y_AXIS));
        add(subjectsListPanel);

        add(createButton("Add Subject", e -> {
            String newSubjectName = AddSubjectDialog.showDialog(frame);
            if (newSubjectName != null && !newSubjectName.isEmpty()) {
                Subject subject = new Subject(null, user.getProfileID(), newSubjectName, Subject.SubjectLevel.STANDARD_LEVEL, Subject.SubjectGroup.DP_CORE, null);
                subject.setProfileID(user.getProfileID()); // assign to current user
                subjectDAO.insertSubject(subject, subject.getProfileID());

                refreshSubjects();
            }
        }));

        add(createButton("Return to homepage", e -> app.showPanel("homepage")));
        refreshSubjects();
    }

    private void refreshSubjects() {
        subjectsListPanel.removeAll();

        List<Subject> subjects = subjectDAO.getSubjectsByProfile(user.getProfileID());
        for (Subject subject : subjects) {
            JPanel subjectPanel = new JPanel();
            subjectPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

            subjectPanel.add(createLabelP(subject.getName()));
            subjectPanel.add(createButton("Edit", e -> {
                String newName = JOptionPane.showInputDialog(subjectsListPanel, "Enter new name:", subject.getName());
                if (newName != null && !newName.isEmpty()) {
                    subject.setName(newName);
                    subjectDAO.updateSubject(subject);
                    refreshSubjects();
                }
            }));
            subjectPanel.add(createButton("Delete", e -> {
                int confirm = JOptionPane.showConfirmDialog(subjectsListPanel, "Are you sure?", "Delete Subject", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    subjectDAO.deleteSubject(subject.getSubjectID());
                    refreshSubjects();
                }
            }));

            subjectsListPanel.add(subjectPanel);
        }

        subjectsListPanel.revalidate();
        subjectsListPanel.repaint();
    }
}