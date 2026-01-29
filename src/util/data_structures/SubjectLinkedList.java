package util.data_structures;

import model.Subject;

public class SubjectLinkedList {
    private SubjectNode head;
    private SubjectNode tail;
    private int size;

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public SubjectNode getHead() {
        return head;
    }

    public Subject first() {
        return isEmpty() ? null : head.value;
    }

    public Subject last() {
        return isEmpty() ? null : tail.value;
    }

    public void insertHead(Subject subject) {
        SubjectNode node = new SubjectNode(subject);

        if (isEmpty()) {
            head = tail = node;
        } else {
            node.next = head;
            head.prev = node;
            head = node;
        }
        size++;
    }

    public void insertTail(Subject subject) {
        SubjectNode node = new SubjectNode(subject);

        if (isEmpty()) {
            head = tail = node;
        } else {
            tail.next = node;
            node.prev = tail;
            tail = node;
        }
        size++;
    }

    public void remove(SubjectNode node) {
        if (node == head) head = node.next;
        if (node == tail) tail = node.prev;
        if (node.prev != null) node.prev.next = node.next;
        if (node.next != null) node.next.prev = node.prev;

        size--;
    }
}
