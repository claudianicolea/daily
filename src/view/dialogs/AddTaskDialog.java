package view.dialogs;

import dao.ExamStudyDAO;
import dao.HomeworkDAO;
import dao.InternalAssessmentDAO;
import model.ExamStudy;
import model.Homework;
import model.InternalAssessment;
import model.Subject;
import util.DateUtils;

import javax.swing.*;
import java.util.Objects;

import static main.App.padding;
import static view.UserInterfaceUtils.*;

public class AddTaskDialog extends JDialog {

    private AddTaskDialog(JFrame parent, Subject subject) {
        super(parent, "Add Task", true);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(padding, padding, padding, padding));

        panel.add(new JLabel("Task title:"));
        JTextField titleField = createTextField();
        panel.add(titleField);

        panel.add(new JLabel("Task type:"));
        JComboBox<String> taskTypeCombo = new JComboBox<>(new String[]{"Homework", "Internal Assessment", "Exam Study"});
        panel.add(taskTypeCombo);

        panel.add(new JLabel("Due date (yyyy-mm-dd):"));
        JTextField dueDateField = createTextField();
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
        JComboBox<String> examTypeCombo = new JComboBox<>(new String[]{"Paper 1", "Paper 2", "Paper 3", "Individual Oral", "Class Test"});
        examStudyPanel.add(examTypeCombo);

        JToggleButton isMockToggle = createJToggleButton("Mock", e -> {}, false);
        examStudyPanel.add(isMockToggle);

        // general

        panel.add(homeworkPanel);
        panel.add(internalAssessmentPanel);
        panel.add(examStudyPanel);

        homeworkPanel.setVisible(true);
        internalAssessmentPanel.setVisible(false);
        examStudyPanel.setVisible(false);

        taskTypeCombo.addActionListener(e -> {
            String selected = (String) taskTypeCombo.getSelectedItem();

            homeworkPanel.setVisible("Homework".equals(selected));
            internalAssessmentPanel.setVisible("Internal Assessment".equals(selected));
            examStudyPanel.setVisible("Exam Study".equals(selected));

            panel.revalidate();
            panel.repaint();
            pack();
        });

        // buttons

        JButton addButton = createButton("Add", e -> {
            String title = titleField.getText().trim();
            String dueDateText = dueDateField.getText().trim();

            if (title.isEmpty() || dueDateText.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill in all required fields.");
                return;
            }

            DateUtils deadline;
            try {
                String[] parts = dueDateText.split("-");
                int year = Integer.parseInt(parts[0]);
                int month = Integer.parseInt(parts[1]);
                int day = Integer.parseInt(parts[2]);
                deadline = new DateUtils(day, month, year);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Invalid date format. Use yyyy-mm-dd");
                return;
            }

            switch ((String) Objects.requireNonNull(taskTypeCombo.getSelectedItem())) {
                case "Homework":
                    String lesson = lessonField.getText().trim();

                    if (lesson.isEmpty()) {
                        JOptionPane.showMessageDialog(this, "Please fill in all required fields.");
                        return;
                    }

                    HomeworkDAO.insertTask(new Homework(null, subject.getSubjectID(), title, deadline, lesson));
                    break;
                case "Internal Assessment":
                    String section = sectionField.getText().trim();
                    boolean isExperiment = isExperimentToggle.isSelected();
                    boolean isWriting = isWritingToggle.isSelected();

                    if (section.isEmpty()) {
                        JOptionPane.showMessageDialog(this, "Please fill in all required fields.");
                        return;
                    }

                    InternalAssessmentDAO.insertTask(new InternalAssessment(null, subject.getSubjectID(), title, deadline, section, isExperiment, isWriting));
                    break;
                case "Exam Study":
                    String examTypeField = (String) Objects.requireNonNull(examTypeCombo.getSelectedItem());
                    boolean isMock = isMockToggle.isSelected();

                    if (examTypeField.isEmpty()) {
                        JOptionPane.showMessageDialog(this, "Please fill in all required fields.");
                        return;
                    }

                    ExamStudy.Assessment examType = ExamStudy.Assessment.PAPER1;
                    switch (examTypeField) {
                        case "Paper 1":
                            examType = ExamStudy.Assessment.PAPER1;
                            break;
                        case "Paper 2":
                            examType = ExamStudy.Assessment.PAPER2;
                            break;
                        case "Paper 3":
                            examType = ExamStudy.Assessment.PAPER3;
                            break;
                        case "Individual Oral":
                            examType = ExamStudy.Assessment.INDIVIDUAL_ORAL;
                            break;
                        case "Class Test":
                            examType = ExamStudy.Assessment.CLASS_TEST;
                            break;
                        default:
                            break;
                    }

                    ExamStudyDAO.insertTask(new ExamStudy(null, subject.getSubjectID(), title, deadline, examType, isMock));
                    break;
                default:
                    break;
            }

            dispose();
        });

        panel.add(createButton("Cancel", e -> dispose()));
        panel.add(addButton);
        getRootPane().setDefaultButton(addButton);

        add(panel);
        pack();
        setLocationRelativeTo(parent);
        setVisible(true);
    }

    public static void showDialog(JFrame parent, Subject subject) {
        new AddTaskDialog(parent, subject);
    }
}