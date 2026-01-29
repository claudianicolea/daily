package view.dialogs;

import dao.ExamStudyDAO;
import dao.HomeworkDAO;
import dao.InternalAssessmentDAO;
import model.*;
import util.DateUtils;

import javax.swing.*;

import static main.App.padding;
import static view.UserInterfaceUtils.*;

public class EditTaskDialog extends JDialog {

    private EditTaskDialog(Task task, JFrame parent, Subject subject) {
        super(parent, "Edit Task", true);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(padding, padding, padding, padding));

        panel.add(new JLabel("Task title:"));
        JTextField titleField = createTextField();
        titleField.setText(task.getTitle());
        panel.add(titleField);

        panel.add(new JLabel("Due date (yyyy-mm-dd):"));
        JTextField dueDateField = createTextField();
        DateUtils d = task.getDeadline();
        dueDateField.setText(String.format("%04d-%02d-%02d", d.getYear(), d.getMonth(), d.getDay()
        ));
        panel.add(dueDateField);

        // Homework

        JPanel homeworkPanel = new JPanel();
        homeworkPanel.setLayout(new BoxLayout(homeworkPanel, BoxLayout.Y_AXIS));

        homeworkPanel.add(new JLabel("Lesson:"));
        JTextField lessonField = createTextField();
        homeworkPanel.add(lessonField);

        // Internal Assessment

        JPanel internalAssessmentPanel = new JPanel();
        internalAssessmentPanel.setLayout(new BoxLayout(internalAssessmentPanel, BoxLayout.Y_AXIS));

        internalAssessmentPanel.add(new JLabel("Section:"));
        JTextField sectionField = createTextField();
        internalAssessmentPanel.add(sectionField);

        JToggleButton isExperimentToggle = createJToggleButton("Experiment", e -> {}, false);
        JToggleButton isWritingToggle = createJToggleButton("Writing", e -> {}, false);

        internalAssessmentPanel.add(isExperimentToggle);
        internalAssessmentPanel.add(isWritingToggle);

        // Exam Study

        JPanel examStudyPanel = new JPanel();
        examStudyPanel.setLayout(new BoxLayout(examStudyPanel, BoxLayout.Y_AXIS));

        examStudyPanel.add(new JLabel("Exam type:"));
        JComboBox<ExamStudy.Assessment> examTypeCombo = new JComboBox<>(ExamStudy.Assessment.values());
        examStudyPanel.add(examTypeCombo);

        JToggleButton isMockToggle = createJToggleButton("Mock", e -> {}, false);
        examStudyPanel.add(isMockToggle);

        // hide all subtype panels

        homeworkPanel.setVisible(false);
        internalAssessmentPanel.setVisible(false);
        examStudyPanel.setVisible(false);

        // pre-fill based on task type

        if (task instanceof Homework hw) {
            homeworkPanel.setVisible(true);
            lessonField.setText(hw.getLesson());

        } else if (task instanceof InternalAssessment ia) {
            internalAssessmentPanel.setVisible(true);
            sectionField.setText(ia.getSection());
            isExperimentToggle.setSelected(ia.isExperiment());
            isWritingToggle.setSelected(ia.isWriting());

        } else if (task instanceof ExamStudy es) {
            examStudyPanel.setVisible(true);
            examTypeCombo.setSelectedItem(es.getExamType());
            isMockToggle.setSelected(es.isMock());
        }

        panel.add(homeworkPanel);
        panel.add(internalAssessmentPanel);
        panel.add(examStudyPanel);

        // buttons

        JButton editButton = createButton("Edit", e -> {

            String title = titleField.getText().trim();
            String dueDateText = dueDateField.getText().trim();

            if (title.isEmpty() || dueDateText.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill in all required fields.");
                return;
            }

            DateUtils deadline;
            try {
                String[] parts = dueDateText.split("-");
                deadline = new DateUtils(
                        Integer.parseInt(parts[2]),
                        Integer.parseInt(parts[1]),
                        Integer.parseInt(parts[0])
                );
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Invalid date format. Use yyyy-mm-dd");
                return;
            }

            task.setTitle(title);
            task.setDeadline(deadline);

            // subtype updates

            if (task instanceof Homework hw) {
                hw.setLesson(lessonField.getText().trim());
                HomeworkDAO.updateTask(hw);

            } else if (task instanceof InternalAssessment ia) {
                ia.setSection(sectionField.getText().trim());
                ia.setExperiment(isExperimentToggle.isSelected());
                ia.setWriting(isWritingToggle.isSelected());
                InternalAssessmentDAO.updateTask(ia);

            } else if (task instanceof ExamStudy es) {
                es.setExamType((ExamStudy.Assessment) examTypeCombo.getSelectedItem());
                es.setMock(isMockToggle.isSelected());
                ExamStudyDAO.updateTask(es);
            }

            dispose();
        });

        panel.add(createButton("Cancel", e -> dispose()));
        panel.add(editButton);
        getRootPane().setDefaultButton(editButton);

        add(panel);
        pack();
        setLocationRelativeTo(parent);
        setVisible(true);
    }

    public static void showDialog(Task task, JFrame parent, Subject subject) {
        new EditTaskDialog(task, parent, subject);
    }
}