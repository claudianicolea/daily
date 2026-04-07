package util;

public class LinkedList {
    private Node head;
    private Node tail;
    private int size;

    public int size() { return size; }
    public boolean isEmpty() { return size == 0; }

    public void insertFront(Node node) {
        if (isEmpty()) {
            head = node;
            node.setPrev(null);
            node.setNext(null);

            tail = node;
            tail.setPrev(null);
            tail.setNext(null);
        }
        else {
            Node temp = head;
            temp.setPrev(node);
            node.setNext(temp);
            node.setPrev(null);
            head = node;
        }
        size++;
    }
    public Node getHead() { return head; }

    public void insertBack(Node node) {
        if (isEmpty()) {
            head = node;
            node.setPrev(null);
            node.setNext(null);

            tail = node;
            tail.setPrev(null);
            tail.setNext(null);
        }
        else {
            Node temp = tail;
            temp.setNext(node);
            node.setPrev(temp);
            node.setNext(null);
            tail = node;
        }
        size++;
    }
    public Node getTail() { return tail; }

    public void remove(Node node) {
        if (isEmpty()) return;

        Node i = head;
        while (i != null) {
            if (i == node) {
                // next of previous
                if (i.getNext() != null)
                    i.getPrev().setNext(i.getNext());
                else
                    i.getPrev().setNext(null);

                // previous of next
                if (i.getPrev() != null)
                    i.getNext().setPrev(i.getPrev());
                else
                    i.getNext().setPrev(null);
            }
            i = i.getNext();
        }
        size--;
    }

    public void remove(int position) {
        if (isEmpty()) return;

        Node it = head;
        for (int i = 0; i < position; i++) {
            it = it.getNext();
        }
        remove(it);
        size--;
    }
}
