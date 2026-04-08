package ui.pages;

import com.toedter.calendar.JDateChooser;
import dao.*;
import main.Main;
import model.*;
import ui.elements.*;
import ui.elements.Button;
import ui.elements.Dialog;
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

        JPanel subjectsTitlePanel = new JPanel();
        subjectsTitlePanel.add(new Title("Subjects"));
        subjectsTitlePanel.setBackground(settings.getAccentColor());
        subjectsTitlePanel.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 2, Color.GRAY));
        left.add(subjectsTitlePanel, BorderLayout.NORTH);

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

        JPanel tasksTitlePanel = new JPanel();
        tasksTitlePanel.add(new Title("Tasks"));
        tasksTitlePanel.setBackground(settings.getAccentColor());
        tasksTitlePanel.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 2, Color.GRAY));
        center.add(tasksTitlePanel, BorderLayout.NORTH);

        JPanel center2 = new JPanel();
        center2.setLayout(new BorderLayout());

        refreshTasks(tasks, details);
        center2.add(new Button(
                "Add task",
                e -> {
                    if (selectedSubject == null) {
                        Dialog.showWarning(
                                "Please select a subject first!",
                                "Warning"
                        );
                        return;
                    }

                    refreshTasks(tasks, details);
                    showAddTaskDialog(tasks, details);
                }
        ), BorderLayout.NORTH);

        JScrollPane tasksScrollPane = new JScrollPane(tasks);
        tasksScrollPane.setBackground(Color.WHITE);
        center2.add(tasksScrollPane, BorderLayout.CENTER);
        center2.setBackground(Color.WHITE);

        center.setBackground(settings.getAccentColor());
        center.add(center2, BorderLayout.CENTER);

        // right - task details

        JPanel right = new JPanel();
        right.setLayout(new BorderLayout());

        JPanel detailsTitlePanel = new JPanel();
        detailsTitlePanel.add(new Title("Details"));
        detailsTitlePanel.setBackground(settings.getAccentColor());
        detailsTitlePanel.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 2, Color.GRAY));
        right.add(detailsTitlePanel, BorderLayout.NORTH);

        right.add(new JScrollPane(details), BorderLayout.CENTER);

        JPanel taskButtons = new JPanel();

        // edit task
        taskButtons.add(new Button(
                "Edit",
                e -> showEditTaskDialog(tasks, details)
        ));

        // delete task
        taskButtons.add(new Button(
                "Delete",
                e -> {
                    if (selectedTask == null) {
                        Dialog.showWarning("Please select a task first!", "Warning");
                        return;
                    }

                    int confirm = Dialog.showConfirm(
                            "Are you sure you want to delete " + selectedTask.getTitle() + "?",
                            "Delete Task"
                    );

                    if (confirm == JOptionPane.YES_OPTION) {
                        TaskDAO.deleteTask(selectedTask.getTaskID());
                        refreshTasks(tasks, details);
                    }
                }
        ));

        right.add(taskButtons, BorderLayout.SOUTH);
        right.setBackground(settings.getAccentColor());
        right.setPreferredSize(new Dimension(200, 0));

        // bottom - buttons to profile, settings, subjects

        JPanel bottom = new JPanel();
        bottom.setBackground(settings.getAccentColor());

        bottom.add(new Button("Profile", e -> Main.showPanel(Page.PROFILE), true));
        bottom.add(new Button("Settings", e -> Main.showPanel(Page.SETTINGS), true));
        bottom.add(new Button("Subjects", e -> Main.showPanel(Page.SUBJECTS), true));

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
        RadioButton firstBtn = null;

        while (it != null) {
            Task t = it.getTaskValue();

            RadioButton taskBtn = new RadioButton(t.getTitle() + " (" + t.getRelativeDeadline() + ")");
            group.add(taskBtn);

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

        // if there was no previously selected task, select the first one that is due
        if (group.getSelection() == null) {
            Node it2 = tasks.getHead();
            while (it2 != null) {
                Task t = it2.getTaskValue();
                if (!t.getDeadline().toLocalDate().isBefore(LocalDate.now())) {

                    // find button
                    for (Component comp : tasksPanel.getComponents()) {
                        if (comp instanceof RadioButton btn && btn.getText().startsWith(t.getTitle())) {
                            btn.setSelected(true);
                            selectedTask = t;
                            detailsPanel.removeAll();
                            t.showDetails(detailsPanel);
                            detailsPanel.revalidate();
                            detailsPanel.repaint();
                            break;
                        }
                    }
                    break;
                }
                it2 = it2.getNext();
            }
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

            RadioButton subjectBtn = new RadioButton(s.getName());
            group.add(subjectBtn);

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

        panel.add(new BodyText("Title:"));
        JTextField titleField = new JTextField(20);
        panel.add(titleField);

        panel.add(new BodyText("Type:"));
        JComboBox<TaskType> taskTypeCombo = new JComboBox<>(TaskType.values());
        panel.add(taskTypeCombo);

        panel.add(new BodyText("Deadline:"));
        JDateChooser dateChooser = new JDateChooser();
        dateChooser.setDate(new java.util.Date());
        dateChooser.setDateFormatString("dd-MM-yyyy");
        panel.add(dateChooser);

        // homework

        JPanel homeworkPanel = new JPanel();
        homeworkPanel.setLayout(new BoxLayout(homeworkPanel, BoxLayout.Y_AXIS));

        homeworkPanel.add(new BodyText("Lesson:"));
        JTextField lessonField = new JTextField(20);
        homeworkPanel.add(lessonField);

        // IA

        JPanel IAPanel = new JPanel();
        IAPanel.setLayout(new BoxLayout(IAPanel, BoxLayout.Y_AXIS));

        IAPanel.add(new BodyText("Section:"));
        JTextField sectionField = new JTextField(20);
        IAPanel.add(sectionField);

        JToggleButton isExperimentToggle = new JToggleButton("Experiment");
        JToggleButton isWritingToggle = new JToggleButton("Writing");
        IAPanel.add(isExperimentToggle);
        IAPanel.add(isWritingToggle);

        // exam

        JPanel examPanel = new JPanel();
        examPanel.setLayout(new BoxLayout(examPanel, BoxLayout.Y_AXIS));

        examPanel.add(new BodyText("Assessment type:"));
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

        buttonPanel.add(new Button(
                "Cancel",
                e -> dialog.dispose()
        ));
        Button addBtn = new Button(
                "Add",
                e -> {
                    String title = titleField.getText().trim();
                    java.sql.Date dueDate = new java.sql.Date(dateChooser.getDate().getTime());
                    TaskType selected = (TaskType) taskTypeCombo.getSelectedItem();

                    if (title.isEmpty() || selected == null) {
                        Dialog.showWarning(
                                "Please fill in all required fields!",
                                "Warning"
                        );
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
                                    Dialog.showWarning(
                                            "Please fill in all required fields!",
                                            "Warning"
                                    );
                                    return;
                                }
                                ExamDAO.insertTask(new Exam(null, title, dueDate, TaskType.EXAM, null, examType, isMockToggle.isSelected()), selectedSubject.getSubjectID());
                                break;
                        }
                        dialog.dispose();
                        refreshTasks(tasksPanel, detailsPanel);
                    }
                    catch (Exception ex) {
                        Dialog.showWarning(
                                "Error adding task: " + ex.getMessage(),
                                "Warning"
                        );
                    }
                },
                false,
                true
        );
        buttonPanel.add(addBtn);

        panel.add(buttonPanel);
        dialog.getRootPane().setDefaultButton(addBtn);

        dialog.add(panel);
        dialog.pack();
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }

    private void showEditTaskDialog(JPanel tasksPanel, JPanel detailsPanel) {
        if (selectedTask == null) {
            Dialog.showWarning(
                    "Please select a task first!",
                    "Warning"
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

        panel.add(new BodyText("Title:"));
        JTextField titleField = new JTextField(20);
        titleField.setText(selectedTask.getTitle());
        panel.add(titleField);

        panel.add(new BodyText("Deadline:"));
        JDateChooser dateChooser = new JDateChooser();
        dateChooser.setDate(new java.util.Date());
        dateChooser.setDateFormatString("dd-MM-yyyy");
        dateChooser.setDate(selectedTask.getDeadline());
        panel.add(dateChooser);

        // homework

        JPanel homeworkPanel = new JPanel();
        homeworkPanel.setLayout(new BoxLayout(homeworkPanel, BoxLayout.Y_AXIS));

        homeworkPanel.add(new BodyText("Lesson:"));
        JTextField lessonField = new JTextField(20);
        homeworkPanel.add(lessonField);

        // IA

        JPanel IAPanel = new JPanel();
        IAPanel.setLayout(new BoxLayout(IAPanel, BoxLayout.Y_AXIS));

        IAPanel.add(new BodyText("Section:"));
        JTextField sectionField = new JTextField(20);
        IAPanel.add(sectionField);

        JToggleButton isExperimentToggle = new JToggleButton("Experiment");
        JToggleButton isWritingToggle = new JToggleButton("Writing");
        IAPanel.add(isExperimentToggle);
        IAPanel.add(isWritingToggle);

        // exam

        JPanel examPanel = new JPanel();
        examPanel.setLayout(new BoxLayout(examPanel, BoxLayout.Y_AXIS));

        examPanel.add(new BodyText("Assessment type:"));
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

        buttonPanel.add(new Button(
                "Cancel",
                e -> dialog.dispose()
        ));
        Button editBtn = new Button(
                "Edit",
                e -> {
                    String title = titleField.getText().trim();
                    java.sql.Date dueDate = new java.sql.Date(dateChooser.getDate().getTime());

                    if (title.isEmpty()) {
                        Dialog.showWarning(
                                "Please fill in all required fields!",
                                "Warning"
                        );
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
                                Dialog.showWarning(
                                        "Please fill in all required fields!",
                                        "Warning"
                                );
                                return;
                            }
                            ((Exam) selectedTask).setAssessmentType((Assessment) examTypeCombo.getSelectedItem());
                            ExamDAO.updateTask((Exam) selectedTask);
                        }
                        dialog.dispose();
                        refreshTasks(tasksPanel, detailsPanel);
                    }
                    catch (Exception ex) {
                        Dialog.showWarning(
                                "Error editing task: " + ex.getMessage(),
                                "Warning"
                        );
                    }
                },
                false,
                true
        );
        buttonPanel.add(editBtn);

        panel.add(buttonPanel);
        dialog.getRootPane().setDefaultButton(editBtn);

        // dialog
        dialog.add(panel);
        dialog.pack();
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }
}
