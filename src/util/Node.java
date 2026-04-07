package util;

import model.Subject;
import model.Task;

public class Node {
    private Subject subjectValue;
    private Task taskValue;
    private Node next, prev;

    public Node(Subject value) {
        subjectValue = value;
        next = null;
        prev = null;
    }

    public Node(Task value) {
        taskValue = value;
        next = null;
        prev = null;
    }

    public Subject getSubjectValue() { return subjectValue; }
    public void setSubjectValue(Subject subjectValue) { this.subjectValue = subjectValue; }

    public Task getTaskValue() { return taskValue; }
    public void setTaskValue(Task taskValue) { this.taskValue = taskValue; }

    public Node getNext() { return next; }
    public void setNext(Node next) { this.next = next; }

    public Node getPrev() { return prev; }
    public void setPrev(Node prev) { this.prev = prev; }
}
