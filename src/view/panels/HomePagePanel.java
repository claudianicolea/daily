package view.panels;

import dao.ExamStudyDAO;
import dao.HomeworkDAO;
import dao.InternalAssessmentDAO;
import main.App;
import model.*;
import util.ColorUtils;
import util.data_structures.SubjectLinkedList;
import util.data_structures.SubjectNode;
import util.data_structures.TaskLinkedList;
import util.data_structures.TaskNode;
import view.dialogs.AddTaskDialog;
import view.dialogs.EditTaskDialog;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

import static dao.SubjectDAO.getSubjectsByProfile;
import static dao.TaskDAO.getTasksBySubject;
import static main.App.*;
import static view.UserInterfaceUtils.*;

public class HomePagePanel extends JPanel {
    private final JPanel subjectsListPanel;
    private final JPanel tasksListPanel;
    private final JPanel taskDetailsPanel;
    private Subject selectedSubject = null;

    public HomePagePanel(App app) {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(padding, padding, padding, padding));

        JPanel left = new JPanel();
        subjectsListPanel = new JPanel();
        subjectsListPanel.setLayout(new BoxLayout(subjectsListPanel, BoxLayout.Y_AXIS));
        left.setLayout(new BorderLayout());
        left.add(createLabelH1("Subjects"), BorderLayout.NORTH);
        left.add(new JScrollPane(subjectsListPanel), BorderLayout.CENTER);

        JPanel center = new JPanel();
        tasksListPanel = new JPanel();
        tasksListPanel.setLayout(new BoxLayout(tasksListPanel, BoxLayout.Y_AXIS));
        center.setLayout(new BorderLayout());

        center.add(createButton("Add Task", e -> {
            if (selectedSubject == null) {
                JOptionPane.showMessageDialog(this, "Please select a subject first.");
            } else {
                AddTaskDialog.showDialog(frame, selectedSubject);
                refreshTasks();
            }
        }), BorderLayout.NORTH);
        center.add(new JScrollPane(tasksListPanel), BorderLayout.CENTER);

        JPanel right = new JPanel();
        taskDetailsPanel = new JPanel();
        taskDetailsPanel.setLayout(new BoxLayout(taskDetailsPanel, BoxLayout.Y_AXIS));
        right.setLayout(new BorderLayout());
        right.add(createLabelH1("Task Details"), BorderLayout.NORTH);
        right.add(taskDetailsPanel, BorderLayout.CENTER);

        if (user != null) {
            left.setBackground(user.getSettings().getAccentAwtColor());
            center.setBackground(ColorUtils.WHITE.toAwtColor());
            right.setBackground(user.getSettings().getAccentAwtColor());
        }
        left.setPreferredSize(new Dimension(200, 0));
        right.setPreferredSize(new Dimension(250, 0));

        add(left, BorderLayout.WEST);
        add(center, BorderLayout.CENTER);
        add(right, BorderLayout.EAST);

        JPanel bottomButtons = new JPanel();
        bottomButtons.setLayout(new BoxLayout(bottomButtons, BoxLayout.Y_AXIS));

        bottomButtons.add(createButton("Profile", e -> app.showPanel("profile")));
        bottomButtons.add(Box.createVerticalStrut(5));
        bottomButtons.add(createButton("Settings", e -> app.showPanel("settings")));
        bottomButtons.add(Box.createVerticalStrut(5));
        bottomButtons.add(createButton("Edit subjects", e -> app.showPanel("subjects")));

        left.add(bottomButtons, BorderLayout.SOUTH);

        refreshSubjects();
    }

    private void refreshSubjects() {
        subjectsListPanel.removeAll();
        SubjectLinkedList subjects = getSubjectsByProfile(user.getProfileID());

        SubjectNode current = subjects.getHead();
        while (current != null) {
            Subject subject = current.value;

            JButton btn = createButton(subject.getName(), e -> {
                selectedSubject = subject;
                refreshTasks();
            });

            subjectsListPanel.add(btn);
            current = current.next;
        }
        subjectsListPanel.revalidate();
        subjectsListPanel.repaint();
    }

    private void refreshTasks() {
        tasksListPanel.removeAll();
        taskDetailsPanel.removeAll();

        if (selectedSubject == null) return;

        TaskLinkedList tasks = getTasksBySubject(selectedSubject.getSubjectID());

        switch (user.getSettings().getTaskSortMode()) {
            case ALPHABETICAL -> sortTasksAlphabetically(tasks);
            case BY_DATE_ADDED -> sortTasksByDate(tasks);
            case BY_DEADLINE -> sortTasksByDeadline(tasks);
        }

        TaskNode current = tasks.getHead();

        while (current != null) {
            Task task = current.value;

            JButton taskButton = null;
            switch (task.getType()) {
                case HOMEWORK -> taskButton = createButton(task.getTitle(), e -> showTaskDetails((Homework) task));
                case INTERNAL_ASSESSMENT -> taskButton = createButton(task.getTitle(), e -> showTaskDetails((InternalAssessment) task));
                case EXAM_STUDY -> taskButton = createButton(task.getTitle(), e -> showTaskDetails((ExamStudy) task));
            }

            tasksListPanel.add(taskButton);
            current = current.next;
        }

        tasksListPanel.revalidate();
        tasksListPanel.repaint();
        taskDetailsPanel.revalidate();
        taskDetailsPanel.repaint();
    }

    // sort tasks alphabetically by title (case-insensitive)
    private void sortTasksAlphabetically(TaskLinkedList tasks) {
        for (TaskNode i = tasks.getHead(); i != null; i = i.next) {
            for (TaskNode j = i.next; j != null; j = j.next) {
                String a = i.value.getTitle().toLowerCase();
                String b = j.value.getTitle().toLowerCase();

                if (a.compareTo(b) > 0) {
                    tasks.swap(i, j);
                }
            }
        }
    }

    // sort tasks by creation date (ascending)
    private void sortTasksByDate(TaskLinkedList tasks) {
        for (TaskNode i = tasks.getHead(); i != null; i = i.next) {
            for (TaskNode j = i.next; j != null; j = j.next) {
                if (i.value.getCreatedAt().after(j.value.getCreatedAt())) {
                    tasks.swap(i, j);
                }
            }
        }
    }

    // sort tasks by deadline (ascending)
    private void sortTasksByDeadline(TaskLinkedList tasks) {
        for (TaskNode i = tasks.getHead(); i != null; i = i.next) {
            for (TaskNode j = i.next; j != null; j = j.next) {
                LocalDate a = i.value.getDeadline().toLocalDate();
                LocalDate b = j.value.getDeadline().toLocalDate();

                if (a.isAfter(b)) {
                    tasks.swap(i, j);
                }
            }
        }
    }

    private void showTaskDetails(Homework task) {
        taskDetailsPanel.removeAll();
        taskDetailsPanel.add(createLabelH2("Name: " + task.getTitle()));
        taskDetailsPanel.add(createLabelH2("Type: Homework"));
        taskDetailsPanel.add(createLabelH2("Due: " + task.getDeadline()));
        taskDetailsPanel.add(createLabelH2("Lesson: " + task.getLesson()));

        taskDetailsPanel.add(createButton("Edit", e -> {
            HomeworkDAO.updateTask(task);
            EditTaskDialog.showDialog(task, frame, selectedSubject);
            refreshTasks();
        }));
        taskDetailsPanel.add(createButton("Delete", e -> {
            int confirm = JOptionPane.showConfirmDialog(frame, "Are you sure?", "Delete Task", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                HomeworkDAO.deleteTask(task.getTaskID());
                refreshTasks();
            }
        }));

        taskDetailsPanel.revalidate();
        taskDetailsPanel.repaint();
    }
    private void showTaskDetails(InternalAssessment task) {
        taskDetailsPanel.removeAll();
        taskDetailsPanel.add(createLabelH2("Name: " + task.getTitle()));
        taskDetailsPanel.add(createLabelH2("Type: Internal Assessment"));
        taskDetailsPanel.add(createLabelH2("Due: " + task.getDeadline()));
        taskDetailsPanel.add(createLabelH2("Writing: " + task.isWriting()));
        taskDetailsPanel.add(createLabelH2("Experiment: " + task.isExperiment()));

        taskDetailsPanel.add(createButton("Edit", e -> {
            InternalAssessmentDAO.updateTask(task);
            EditTaskDialog.showDialog(task, frame, selectedSubject);
            refreshTasks();
        }));
        taskDetailsPanel.add(createButton("Delete", e -> {
            int confirm = JOptionPane.showConfirmDialog(frame, "Are you sure?", "Delete Task", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                InternalAssessmentDAO.deleteTask(task.getTaskID());
                refreshTasks();
            }
        }));

        taskDetailsPanel.revalidate();
        taskDetailsPanel.repaint();
    }
    private void showTaskDetails(ExamStudy task) {
        taskDetailsPanel.removeAll();
        taskDetailsPanel.add(createLabelH2("Name: " + task.getTitle()));
        taskDetailsPanel.add(createLabelH2("Type: Exam Study"));
        taskDetailsPanel.add(createLabelH2("Due: " + task.getDeadline()));

        String examTypeText = "Paper 1";
        switch (task.getExamType()) {
            case PAPER1:
                examTypeText = "Paper 1";
                break;
            case PAPER2:
                examTypeText = "Paper 2";
                break;
            case PAPER3:
                examTypeText = "Paper 3";
                break;
            case INDIVIDUAL_ORAL:
                examTypeText = "Individual Oral";
                break;
            case CLASS_TEST:
                examTypeText = "Class Test";
                break;
            default:
                break;
        }
        taskDetailsPanel.add(createLabelH2("Exam Type: " + examTypeText));
        taskDetailsPanel.add(createLabelH2("Mock: " + task.isMock()));

        taskDetailsPanel.add(createButton("Edit", e -> {
            ExamStudyDAO.updateTask(task);
            EditTaskDialog.showDialog(task, frame, selectedSubject);
            refreshTasks();
        }));
        taskDetailsPanel.add(createButton("Delete", e -> {
            int confirm = JOptionPane.showConfirmDialog(frame, "Are you sure?", "Delete Task", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                ExamStudyDAO.deleteTask(task.getTaskID());
                refreshTasks();
            }
        }));

        taskDetailsPanel.revalidate();
        taskDetailsPanel.repaint();
    }
}