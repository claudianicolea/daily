package util.data_structures;

import model.Task;

public class TaskLinkedList {
    private TaskNode head;
    private TaskNode tail;
    private int size;

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public TaskNode getHead() {
        return head;
    }

    public Task first() {
        return isEmpty() ? null : head.value;
    }

    public Task last() {
        return isEmpty() ? null : tail.value;
    }

    public void insertHead(Task task) {
        TaskNode node = new TaskNode(task);

        if (isEmpty()) {
            head = tail = node;
        } else {
            node.next = head;
            head.prev = node;
            head = node;
        }
        size++;
    }

    public void insertTail(Task task) {
        TaskNode node = new TaskNode(task);

        if (isEmpty()) {
            head = tail = node;
        } else {
            tail.next = node;
            node.prev = tail;
            tail = node;
        }
        size++;
    }

    public void remove(TaskNode node) {
        if (node == head) head = node.next;
        if (node == tail) tail = node.prev;
        if (node.prev != null) node.prev.next = node.next;
        if (node.next != null) node.next.prev = node.prev;

        size--;
    }

    public void swap(TaskNode a, TaskNode b) {
        Task temp = a.value;
        a.value = b.value;
        b.value = temp;
    }

}
