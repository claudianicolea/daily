package util.data_structures;

import model.Task;

public class TaskNode {
    public Task value;
    public TaskNode next;
    public TaskNode prev;

    TaskNode(Task value) {
        this.value = value;
    }
}
