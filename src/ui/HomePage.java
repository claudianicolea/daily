package ui;

import com.toedter.calendar.JDateChooser;
import dao.*;
import main.Main;
import model.*;
import util.*;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

import static main.Main.settings;
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
        left.setBackground(settings.getAccentColor());
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
        JScrollPane tasksScrollPane = new JScrollPane(tasks);
        tasksScrollPane.setBackground(Color.WHITE);
        center.add(tasksScrollPane, BorderLayout.CENTER);
        center.setBackground(settings.getAccentColor());

        // right - task details

        JPanel right = new JPanel();
        right.setLayout(new BorderLayout());

        JLabel titleD = new JLabel("Details");
        titleD.setAlignmentX(CENTER_ALIGNMENT);
        right.add(titleD, BorderLayout.NORTH);

        right.add(new JScrollPane(details), BorderLayout.CENTER);

        JPanel taskButtons = new JPanel();

        JButton editTaskBtn = new JButton("Edit");
        editTaskBtn.addActionListener(e -> showEditTaskDialog(tasks, details));
        taskButtons.add(editTaskBtn);

        JButton deleteTaskBtn = new JButton("Delete");
        deleteTaskBtn.addActionListener(e -> {
            if (selectedTask == null) {
                JOptionPane.showMessageDialog(null,
                        "Please select a task first!",
                        "Warning",
                        JOptionPane.WARNING_MESSAGE
                );
                return;
            }
            int confirm = JOptionPane.showConfirmDialog(
                    null,
                    "Are you sure you want to delete " + selectedTask.getTitle() + "?",
                    "Delete Task",
                    JOptionPane.YES_NO_OPTION
            );
            if (confirm == JOptionPane.YES_OPTION) {
                TaskDAO.deleteTask(selectedTask.getTaskID());
                refreshTasks(tasks, details);
            }
        });
        taskButtons.add(deleteTaskBtn);

        right.add(taskButtons, BorderLayout.SOUTH);
        right.setBackground(settings.getAccentColor());
        right.setPreferredSize(new Dimension(200, 0));

        // bottom - buttons to profile, settings, subjects

        JPanel bottom = new JPanel();
        bottom.setLayout(new BoxLayout(bottom, BoxLayout.Y_AXIS));
        bottom.setBackground(settings.getAccentColor());

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
        selectedTask = null;
        detailsPanel.revalidate();
        detailsPanel.repaint();

        if (selectedSubject == null) return;

        LinkedList tasks = TaskDAO.getTasksBySubject(selectedSubject.getSubjectID());

        switch (settings.getTaskSortMode()) {
            case ALPHABETICAL -> tasks.sortTasksByAlphabet();
            case CREATION_DATE -> tasks.sortTasksByCreationDate();
            case DEADLINE -> tasks.sortTasksByDeadline();
        }

        ButtonGroup group = new ButtonGroup();

        Node it = tasks.getHead();
        Task firstTask = null;
        JRadioButton firstBtn = null;

        while (it != null) {
            Task t = it.getTaskValue();

            JRadioButton taskBtn = new JRadioButton(t.getTitle() + " (" + t.getRelativeDeadline() + ")");
            group.add(taskBtn);
            taskBtn.setAlignmentX(CENTER_ALIGNMENT);

            if (t.getDeadline().toLocalDate().isBefore(LocalDate.now())) {
                taskBtn.setForeground(Color.LIGHT_GRAY);
            }

            taskBtn.addActionListener(e -> {
                selectedTask = t;
                detailsPanel.removeAll();
                t.showDetails(detailsPanel);
                detailsPanel.revalidate();
                detailsPanel.repaint();
            });

            // remember first task
            if (firstTask == null) {
                firstTask = t;
                firstBtn = taskBtn;
            }

            // restore task selection
            if (selectedTask != null && t.getTaskID().equals(selectedTask.getTaskID()) && !t.getDeadline().toLocalDate().isBefore(LocalDate.now())) {
                taskBtn.setSelected(true);
                selectedTask = t;
                detailsPanel.removeAll();
                t.showDetails(detailsPanel);
                detailsPanel.revalidate();
                detailsPanel.repaint();
            }

            tasksPanel.add(taskBtn);
            it = it.getNext();
        }

        // if there was no previously selected task, select the first one
        if (group.getSelection() == null && firstTask != null && !firstTask.getDeadline().toLocalDate().isBefore(LocalDate.now())) {
            firstBtn.setSelected(true);
            selectedTask = firstTask;

            detailsPanel.removeAll();
            firstTask.showDetails(detailsPanel);
            detailsPanel.revalidate();
            detailsPanel.repaint();
        }

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

            subjectBtn.setAlignmentX(CENTER_ALIGNMENT);
            subjectBtn.addActionListener(e -> {
                selectedSubject = s;
                refreshTasks(tasksPanel, detailsPanel);
            });

            if (selectedSubject == null) {
                selectedSubject = s;
                subjectBtn.setSelected(true);
                refreshTasks(tasksPanel, detailsPanel);
            }

            subjectsPanel.add(subjectBtn);
            it = it.getNext();
        }

        subjectsPanel.revalidate();
        subjectsPanel.repaint();
        subjectsPanel.setBackground(Color.WHITE);
        return selectedSubject;
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
        examPanel.setVisible(false);

        // type switching

        taskTypeCombo.addActionListener(e -> {
            TaskType selected = (TaskType) taskTypeCombo.getSelectedItem();

            homeworkPanel.setVisible(selected == TaskType.HOMEWORK);
            IAPanel.setVisible(selected == TaskType.IA);
            examPanel.setVisible(selected == TaskType.EXAM);

            panel.revalidate();
            panel.repaint();
        });
        taskTypeCombo.setSelectedIndex(0);
        taskTypeCombo.getActionListeners()[0].actionPerformed(null);

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
                        HomeworkDAO.insertTask(new Homework(null, title, dueDate, TaskType.HOMEWORK, null, lesson), selectedSubject.getSubjectID());
                        break;

                    case IA:
                        String section = (sectionField.getText() == null) ? null : sectionField.getText().trim();
                        IADAO.insertTask(new IA(null, title, dueDate, TaskType.IA, null, section, isExperimentToggle.isSelected(), isWritingToggle.isSelected()), selectedSubject.getSubjectID());
                        break;

                    case EXAM:
                        Assessment examType = (Assessment) examTypeCombo.getSelectedItem();
                        if (examType == null) {
                            JOptionPane.showMessageDialog(dialog, "Please fill in all required fields!");
                            return;
                        }
                        ExamDAO.insertTask(new Exam(null, title, dueDate, TaskType.EXAM, null, examType, isMockToggle.isSelected()), selectedSubject.getSubjectID());
                        break;
                }
                dialog.dispose();
                refreshTasks(tasksPanel, detailsPanel);
            }
            catch (Exception ex) {
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

    private void showEditTaskDialog(JPanel tasksPanel, JPanel detailsPanel) {
        if (selectedTask == null) {
            JOptionPane.showMessageDialog(null,
                    "Please select a task first!",
                    "Warning",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        JDialog dialog = new JDialog();
        dialog.setPreferredSize(new Dimension(400, 300));
        dialog.setLocationRelativeTo(null);
        dialog.setTitle("Edit Task");
        dialog.setModal(true);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        // general

        panel.add(new JLabel("Title:"));
        JTextField titleField = new JTextField(20);
        titleField.setText(selectedTask.getTitle());
        panel.add(titleField);

        panel.add(new JLabel("Deadline:"));
        JDateChooser dateChooser = new JDateChooser();
        dateChooser.setDate(new java.util.Date());
        dateChooser.setDateFormatString("dd-MM-yyyy");
        dateChooser.setDate(selectedTask.getDeadline());
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
        examPanel.setVisible(false);

        if (selectedTask instanceof Homework hw) {
            homeworkPanel.setVisible(true);
            lessonField.setText(hw.getLesson());
        }
        else if (selectedTask instanceof IA ia) {
            IAPanel.setVisible(true);
            sectionField.setText(ia.getSection());
            isExperimentToggle.setSelected(ia.isExperiment());
            isWritingToggle.setSelected(ia.isWriting());
        }
        else if (selectedTask instanceof Exam es) {
            examPanel.setVisible(true);
            examTypeCombo.setSelectedItem(es.getAssessmentType());
            isMockToggle.setSelected(es.isMock());
        }

        // buttons

        JPanel buttonPanel = new JPanel();

        JButton editBtn = new JButton("Edit");
        JButton cancelBtn = new JButton("Cancel");

        buttonPanel.add(editBtn);
        buttonPanel.add(cancelBtn);

        panel.add(buttonPanel);
        dialog.getRootPane().setDefaultButton(editBtn);

        // logic

        editBtn.addActionListener(e -> {
            String title = titleField.getText().trim();
            java.sql.Date dueDate = new java.sql.Date(dateChooser.getDate().getTime());

            if (title.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Please fill in all required fields!");
                return;
            }

            selectedTask.setTitle(title);
            selectedTask.setDeadline(dueDate);

            try {
                if (selectedTask instanceof Homework) {
                    ((Homework) selectedTask).setLesson((lessonField.getText() == null) ? null : lessonField.getText().trim());
                    HomeworkDAO.updateTask((Homework) selectedTask);
                }
                else if (selectedTask instanceof IA) {
                    ((IA) selectedTask).setSection((sectionField.getText() == null) ? null : sectionField.getText().trim());
                    IADAO.updateTask((IA) selectedTask);
                }
                else if (selectedTask instanceof Exam) {
                    if (examTypeCombo.getSelectedItem() == null) {
                        JOptionPane.showMessageDialog(dialog, "Please fill in all required fields!");
                        return;
                    }
                    ((Exam) selectedTask).setAssessmentType((Assessment) examTypeCombo.getSelectedItem());
                    ExamDAO.updateTask((Exam) selectedTask);
                }
                dialog.dispose();
                refreshTasks(tasksPanel, detailsPanel);
            }
            catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog, "Error editing task: " + ex.getMessage());
            }
        });
        cancelBtn.addActionListener(e -> dialog.dispose());

        // dialog
        dialog.add(panel);
        dialog.pack();
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }
}
