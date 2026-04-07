package ui;

import com.toedter.calendar.JDateChooser;
import dao.*;
import main.Main;
import model.*;
import util.*;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.time.LocalDate;

import static main.Main.user;

public class HomePage extends JPanel {
    Subject selectedSubject = null;
    Task selectedTask = null;

    public HomePage() {
        setLayout(new BorderLayout());

        // left - subjects

        JPanel subjects = new JPanel();
        subjects.setLayout(new BoxLayout(subjects, BoxLayout.Y_AXIS));

        JPanel left = new JPanel();
        left.setLayout(new BorderLayout());

        JLabel titleS = new JLabel("Subjects");
        titleS.setAlignmentX(CENTER_ALIGNMENT);
        left.add(titleS, BorderLayout.NORTH);

        left.add(new JScrollPane(subjects), BorderLayout.CENTER);
        left.setBackground(user.getSettings().getAccentColor());
        left.setPreferredSize(new Dimension(200, 0));

        JPanel tasks = new JPanel();
        tasks.setLayout(new BoxLayout(tasks, BoxLayout.Y_AXIS));
        tasks.setBackground(Color.WHITE);

        JPanel details = new JPanel();
        details.setLayout(new BoxLayout(details, BoxLayout.Y_AXIS));
        details.setBackground(Color.WHITE);

        // show subjects
        selectedSubject = refreshSubjects(subjects, tasks, details);

        // center - tasks

        JPanel center = new JPanel();
        center.setLayout(new BorderLayout());

        JButton addTaskBtn = new JButton("Add task");
        addTaskBtn.addActionListener(e -> {
            if (selectedSubject == null) {
                JOptionPane.showMessageDialog(null,
                        "Please select a subject first!",
                        "Warning",
                        JOptionPane.WARNING_MESSAGE
                );
                return;
            }
            refreshTasks(tasks, details);
            showAddTaskDialog(tasks, details);
        });

        refreshTasks(tasks, details);
        center.add(addTaskBtn, BorderLayout.NORTH);
        center.add(new JScrollPane(tasks), BorderLayout.CENTER);
        center.setBackground(Color.WHITE);

        // right - task details

        JPanel right = new JPanel();
        right.setLayout(new BorderLayout());

        JLabel titleD = new JLabel("Details");
        titleD.setAlignmentX(CENTER_ALIGNMENT);
        right.add(titleD, BorderLayout.NORTH);

        right.add(new JScrollPane(details), BorderLayout.CENTER);
        right.setBackground(user.getSettings().getAccentColor());
        right.setPreferredSize(new Dimension(200, 0));

        // bottom - buttons to profile, settings, subjects

        JPanel bottom = new JPanel();
        bottom.setLayout(new BoxLayout(bottom, BoxLayout.Y_AXIS));
        bottom.setBackground(Color.WHITE);

        JButton profileBtn = new JButton("Profile");
        profileBtn.addActionListener(e -> {
            Main.showPanel(Page.PROFILE);
        });
        profileBtn.setAlignmentX(CENTER_ALIGNMENT);
        bottom.add(profileBtn);

        JButton settingsBtn = new JButton("Settings");
        settingsBtn.addActionListener(e -> {
            Main.showPanel(Page.SETTINGS);
        });
        settingsBtn.setAlignmentX(CENTER_ALIGNMENT);
        bottom.add(settingsBtn);

        JButton subjectsBtn = new JButton("Subjects");
        subjectsBtn.addActionListener(e -> {
            Main.showPanel(Page.SUBJECTS);
        });
        subjectsBtn.setAlignmentX(CENTER_ALIGNMENT);
        bottom.add(subjectsBtn);

        // add each panel
        add(left, BorderLayout.WEST);
        add(center, BorderLayout.CENTER);
        add(right, BorderLayout.EAST);
        add(bottom, BorderLayout.SOUTH);
    }

    private void refreshTasks(JPanel tasksPanel, JPanel detailsPanel) {
        tasksPanel.removeAll();
        detailsPanel.removeAll();

        if (selectedSubject == null) return;

        LinkedList tasks = TaskDAO.getTasksBySubject(selectedSubject.getSubjectID());

        switch (user.getSettings().getTaskSortMode()) {
            case ALPHABETICAL -> sortByAlphabet(tasks);
            case CREATION_DATE -> sortByCreationDate(tasks);
            case DEADLINE -> sortByDeadline(tasks);
        }

        ButtonGroup group = new ButtonGroup();

        Node it = tasks.getHead();
        boolean first = true;

        while (it != null) {
            Task t = it.getTaskValue();

            JRadioButton taskBtn = new JRadioButton(t.getTitle() + " (" + t.getRelativeDeadline() + ")");
            group.add(taskBtn);

            taskBtn.setOpaque(true);
            taskBtn.setContentAreaFilled(true);
            taskBtn.setForeground(Color.BLACK);
            taskBtn.setAlignmentX(CENTER_ALIGNMENT);
            taskBtn.setFocusPainted(false);

            if (t.getDeadline().toLocalDate().isBefore(LocalDate.now())) {
                taskBtn.setForeground(Color.LIGHT_GRAY);
            }

            taskBtn.addActionListener(e -> {
                selectedTask = t;
                detailsPanel.removeAll();
                t.showDetails(detailsPanel);
                updateRadioStyles(tasksPanel);

                detailsPanel.revalidate();
                detailsPanel.repaint();
            });

            if (first) {
                taskBtn.setSelected(true);
                selectedTask = t;
                t.showDetails(detailsPanel);
                first = false;
            }

            tasksPanel.add(taskBtn);
            it = it.getNext();
        }

        updateRadioStyles(tasksPanel);

        tasksPanel.revalidate();
        tasksPanel.repaint();
    }

    private Subject refreshSubjects(JPanel subjectsPanel, JPanel tasksPanel, JPanel detailsPanel) {
        subjectsPanel.removeAll();
        LinkedList subjects = SubjectDAO.getSubjectsByProfile(user.getProfileID());

        ButtonGroup group = new ButtonGroup();

        Node it = subjects.getHead();

        while (it != null) {
            Subject s = it.getSubjectValue();

            JRadioButton subjectBtn = new JRadioButton(s.getName());
            group.add(subjectBtn);

            subjectBtn.setOpaque(true);
            subjectBtn.setContentAreaFilled(true);
            subjectBtn.setForeground(Color.BLACK);
            subjectBtn.setAlignmentX(CENTER_ALIGNMENT);
            subjectBtn.setFocusPainted(false);

            subjectBtn.addActionListener(e -> {
                selectedSubject = s;
                refreshTasks(tasksPanel, detailsPanel);
                updateRadioStyles(subjectsPanel);
            });

            if (selectedSubject == null) {
                selectedSubject = s;
                subjectBtn.setSelected(true);
                refreshTasks(tasksPanel, detailsPanel);
            }

            subjectsPanel.add(subjectBtn);
            it = it.getNext();
        }

        updateRadioStyles(subjectsPanel);

        subjectsPanel.revalidate();
        subjectsPanel.repaint();
        subjectsPanel.setBackground(Color.WHITE);
        return selectedSubject;
    }

    private void updateRadioStyles(JPanel panel) {
        for (Component comp : panel.getComponents()) {

            if (comp instanceof JPanel inner) {
                for (Component c : inner.getComponents()) {
                    if (c instanceof JRadioButton rb) {
                        if (rb.isSelected()) {
                            rb.setBackground(user.getSettings().getAccentColor());
                        } else {
                            rb.setBackground(Color.WHITE);
                        }
                    }
                }
            }

            if (comp instanceof JRadioButton rb) {
                if (rb.isSelected()) {
                    rb.setBackground(user.getSettings().getAccentColor());
                } else {
                    rb.setBackground(Color.WHITE);
                }
            }
        }
    }

    private void showAddTaskDialog(JPanel tasksPanel, JPanel detailsPanel) {
        JDialog dialog = new JDialog();
        dialog.setPreferredSize(new Dimension(400, 300));
        dialog.setLocationRelativeTo(null);
        dialog.setTitle("Add Task");
        dialog.setModal(true);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        // general

        panel.add(new JLabel("Title:"));
        JTextField titleField = new JTextField(20);
        panel.add(titleField);

        panel.add(new JLabel("Type:"));
        JComboBox<TaskType> taskTypeCombo = new JComboBox<>(TaskType.values());
        panel.add(taskTypeCombo);

        panel.add(new JLabel("Deadline:"));
        JDateChooser dateChooser = new JDateChooser();
        dateChooser.setDate(new java.util.Date());
        dateChooser.setDateFormatString("dd-MM-yyyy");
        panel.add(dateChooser);

        // homework

        JPanel homeworkPanel = new JPanel();
        homeworkPanel.setLayout(new BoxLayout(homeworkPanel, BoxLayout.Y_AXIS));

        homeworkPanel.add(new JLabel("Lesson:"));
        JTextField lessonField = new JTextField(20);
        homeworkPanel.add(lessonField);

        // IA

        JPanel IAPanel = new JPanel();
        IAPanel.setLayout(new BoxLayout(IAPanel, BoxLayout.Y_AXIS));

        IAPanel.add(new JLabel("Section:"));
        JTextField sectionField = new JTextField(20);
        IAPanel.add(sectionField);

        JToggleButton isExperimentToggle = new JToggleButton("Experiment");
        JToggleButton isWritingToggle = new JToggleButton("Writing");
        IAPanel.add(isExperimentToggle);
        IAPanel.add(isWritingToggle);

        // exam

        JPanel examPanel = new JPanel();
        examPanel.setLayout(new BoxLayout(examPanel, BoxLayout.Y_AXIS));

        examPanel.add(new JLabel("Assessment type:"));
        JComboBox<Assessment> examTypeCombo = new JComboBox<>(Assessment.values());
        examPanel.add(examTypeCombo);

        JToggleButton isMockToggle = new JToggleButton("Mock");
        examPanel.add(isMockToggle);

        // add subpanels

        panel.add(homeworkPanel);
        panel.add(IAPanel);
        panel.add(examPanel);
        homeworkPanel.setVisible(false);
        IAPanel.setVisible(false);
        examPanel.setVisible(true); // exam is autoselected -> displayed first

        // type switching

        taskTypeCombo.addActionListener(e -> {
            TaskType selected = (TaskType) taskTypeCombo.getSelectedItem();

            homeworkPanel.setVisible(selected == TaskType.HOMEWORK);
            IAPanel.setVisible(selected == TaskType.IA);
            examPanel.setVisible(selected == TaskType.EXAM);

            panel.revalidate();
            panel.repaint();
        });

        // buttons

        JPanel buttonPanel = new JPanel();

        JButton addBtn = new JButton("Add");
        JButton cancelBtn = new JButton("Cancel");

        buttonPanel.add(addBtn);
        buttonPanel.add(cancelBtn);

        panel.add(buttonPanel);
        dialog.getRootPane().setDefaultButton(addBtn);

        // logic

        addBtn.addActionListener(e -> {
            String title = titleField.getText().trim();
            java.sql.Date dueDate = new java.sql.Date(dateChooser.getDate().getTime());
            TaskType selected = (TaskType) taskTypeCombo.getSelectedItem();

            if (title.isEmpty() || selected == null) {
                JOptionPane.showMessageDialog(dialog, "Please fill in all required fields!");
                return;
            }

            try {
                switch (selected) {
                    case HOMEWORK:
                        String lesson = (lessonField.getText() == null) ? null : lessonField.getText().trim();
                        HomeworkDAO.insertTask(new Homework(null, selectedSubject.getSubjectID(), title, dueDate, TaskType.HOMEWORK, null, lesson));
                        break;

                    case IA:
                        String section = (sectionField.getText() == null) ? null : sectionField.getText().trim();
                        IADAO.insertTask(new IA(null, selectedSubject.getSubjectID(), title, dueDate, TaskType.IA, null, section, isExperimentToggle.isSelected(), isWritingToggle.isSelected()));
                        break;

                    case EXAM:
                        Assessment examType = (Assessment) examTypeCombo.getSelectedItem();
                        if (examType == null) {
                            JOptionPane.showMessageDialog(dialog, "Please fill in all required fields!");
                            return;
                        }
                        ExamDAO.insertTask(new Exam(null, selectedSubject.getSubjectID(), title, dueDate, TaskType.EXAM, null, examType, isMockToggle.isSelected()));
                        break;
                }
                dialog.dispose();
                refreshTasks(tasksPanel, detailsPanel);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog, "Error adding task: " + ex.getMessage());
            }
        });
        cancelBtn.addActionListener(e -> dialog.dispose());

        // dialog
        dialog.add(panel);
        dialog.pack();
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }

    //TODO edit task
    private void showEditTaskDialog() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
    }

    //TODO Sort by alphabet
    private void sortByAlphabet(LinkedList ll) {

    }

    //TODO Sort by deadline
    private void sortByDeadline(LinkedList ll) {

    }

    //TODO Sort by creation date
    private void sortByCreationDate(LinkedList ll) {

    }
}
