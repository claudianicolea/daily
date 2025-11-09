package model;

import model.tasks.Task;
import util.Color;

import java.util.ArrayList;

public class Subject {
    public enum SubjectLevel {
        STANDARD_LEVEL,
        HIGHER_LEVEL,
        CORE_SUBJECT
    }

    public enum SubjectGroup {
        LANGUAGE_A,
        LANGUAGE_B,
        SCIENCES,
        HUMANITIES,
        MATHEMATICS,
        ARTS,
        DP_CORE
    }

    private String name;
    private SubjectLevel level;
    private SubjectGroup group;
    private Color color;
    private String teacherName;
    private String teacherEmail;
    private ArrayList<Task> tasks;

    public Subject(String name, SubjectLevel level, SubjectGroup group) {
        this.name = name;
        this.level = level;
        this.group = group;
        tasks = new ArrayList<>();
        color = new Color();
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setLevel(SubjectLevel level) {
        this.level = level;
    }

    public SubjectLevel getLevel() {
        return level;
    }

    public void setGroup(SubjectGroup group) { this.group = group; }

    public SubjectGroup getGroup() { return group; }

    public void setColor(Color.ColorName colorName) {
        switch(colorName) {
            case Color.ColorName.RED:
                color.setRed();
                break;
            case Color.ColorName.ORANGE:
                color.setOrange();
                break;
            case Color.ColorName.YELLOW:
                color.setYellow();
                break;
            case Color.ColorName.GREEN:
                color.setGreen();
                break;
            case Color.ColorName.BLUE:
                color.setBlue();
                break;
            case Color.ColorName.PURPLE:
                color.setPurple();
                break;
            case Color.ColorName.PINK:
                color.setPink();
                break;
            case Color.ColorName.BROWN:
                color.setBrown();
                break;
            case Color.ColorName.BEIGE:
                color.setBeige();
                break;
            case Color.ColorName.WHITE:
                color.setWhite();
                break;
            case Color.ColorName.BLACK:
                color.setBlack();
                break;
        }
    }

    public void setTeacherName(String name) {
        teacherName = name;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherEmail(String email) {
        teacherEmail = email;
    }

    public String getTeacherEmail() {
        return teacherEmail;
    }

    public void addTask(Task task) {
        tasks.add(task);
    }

    public void removeTask(Task task) {
        tasks.remove(task);
    }
}
