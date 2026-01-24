package view.panels;

import dao.SubjectDAO;
import dao.TaskDAO;
import main.App;
import model.Subject;
import model.Task;
import util.ColorUtils;
import view.dialogs.AddTaskDialog;
import javax.swing.*;
import java.awt.*;
import java.util.List;
import static main.App.*;
import static view.UserInterfaceUtils.*;

public class HomePagePanel extends JPanel {
    private final JPanel subjectsListPanel;
    private final JPanel tasksListPanel;
    private final JPanel taskDetailsPanel;

    private final SubjectDAO subjectDAO = new SubjectDAO();
    private final TaskDAO taskDAO = new TaskDAO();

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
            AddTaskDialog.showDialog(frame, selectedSubject);
            refreshTasks();
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

        left.add(createButton("Profile", e -> app.showPanel("profile")), BorderLayout.SOUTH);
        left.add(createButton("Settings", e -> app.showPanel("settings")), BorderLayout.SOUTH);
        left.add(createButton("Edit subjects", e -> app.showPanel("subjects")), BorderLayout.SOUTH);

        refreshSubjects();
    }

    private void refreshSubjects() {
        subjectsListPanel.removeAll();
        List<Subject> subjects = subjectDAO.getSubjectsByProfile(user.getProfileID());
        for (Subject subject : subjects) {
            JButton btn = createButton(subject.getName(), e -> {
                selectedSubject = subject;
                refreshTasks();
            });
            subjectsListPanel.add(btn);
        }
        subjectsListPanel.revalidate();
        subjectsListPanel.repaint();

        // auto-select first subject
        if (!subjects.isEmpty() && selectedSubject == null) {
            selectedSubject = subjects.get(0);
            refreshTasks();
        }
    }

    private void refreshTasks() {
        tasksListPanel.removeAll();
        taskDetailsPanel.removeAll();

        if (selectedSubject == null) return;

        List<Task> tasks = taskDAO.getTasksBySubject(selectedSubject.getSubjectID());
        for (Task task : tasks) {
            JButton taskButton = createButton(task.getTitle(), e -> showTaskDetails(task));
            tasksListPanel.add(taskButton);
        }

        tasksListPanel.revalidate();
        tasksListPanel.repaint();
        taskDetailsPanel.revalidate();
        taskDetailsPanel.repaint();
    }

    private void showTaskDetails(Task task) {
        taskDetailsPanel.removeAll();
        taskDetailsPanel.add(createLabelH2("Name: " + task.getTitle()));
        taskDetailsPanel.add(createLabelH2("Type: " + task.getClass().getSimpleName()));
        taskDetailsPanel.add(createLabelH2("Due: " + task.getDeadline()));
        taskDetailsPanel.add(createLabelH2("Description: " + task.getDescription()));

        taskDetailsPanel.add(createButton("Edit", e -> {
            // TODO: implement edit dialog
        }));
        taskDetailsPanel.add(createButton("Delete", e -> {
            taskDAO.deleteTask(task.getTaskID());
            refreshTasks();
        }));

        taskDetailsPanel.revalidate();
        taskDetailsPanel.repaint();
    }
}