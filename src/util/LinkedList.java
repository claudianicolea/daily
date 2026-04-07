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

    public void sortTasksByAlphabet() {
        head = mergeSortByTitle(head);
        Node temp = head;

        tail = null;
        while (temp != null) {
            if (temp.getNext() == null) tail = temp;
            temp = temp.getNext();
        }
    }

    public void sortTasksByDeadline() {
        head = mergeSortByDeadline(head);
        Node temp = head;

        tail = null;
        while (temp != null) {
            if (temp.getNext() == null) tail = temp;
            temp = temp.getNext();
        }
    }

    public void sortTasksByCreationDate() {
        head = mergeSortByTimestamp(head);
        Node temp = head;

        tail = null;
        while (temp != null) {
            if (temp.getNext() == null) tail = temp;
            temp = temp.getNext();
        }
    }

    private Node mergeSortByTitle(Node node) {
        if (node == null || node.getNext() == null) return node;

        Node middle = getMiddle(node);
        Node nextOfMiddle = middle.getNext();
        middle.setNext(null);
        if (nextOfMiddle != null) nextOfMiddle.setPrev(null);

        Node left = mergeSortByTitle(node);
        Node right = mergeSortByTitle(nextOfMiddle);

        return mergeByTitle(left, right);
    }

    private Node mergeSortByDeadline(Node node) {
        if (node == null || node.getNext() == null) return node;

        Node middle = getMiddle(node);
        Node nextOfMiddle = middle.getNext();
        middle.setNext(null);
        if (nextOfMiddle != null) nextOfMiddle.setPrev(null);

        Node left = mergeSortByDeadline(node);
        Node right = mergeSortByDeadline(nextOfMiddle);

        return mergeByDeadline(left, right);
    }

    private Node mergeSortByTimestamp(Node node) {
        if (node == null || node.getNext() == null) return node;

        Node middle = getMiddle(node);
        Node nextOfMiddle = middle.getNext();
        middle.setNext(null);
        if (nextOfMiddle != null) nextOfMiddle.setPrev(null);

        Node left = mergeSortByTimestamp(node);
        Node right = mergeSortByTimestamp(nextOfMiddle);

        return mergeByTimestamp(left, right);
    }

    private Node getMiddle(Node head) {
        if (head == null) return head;

        Node slow = head, fast = head;
        while (fast.getNext() != null && fast.getNext().getNext() != null) {
            slow = slow.getNext();
            fast = fast.getNext().getNext();
        }

        return slow;
    }

    private Node mergeByTitle(Node left, Node right) {
        if (left == null) return right;
        if (right == null) return left;

        Node result;

        if (left.getTaskValue().getTitle().compareToIgnoreCase(right.getTaskValue().getTitle()) <= 0) {
            result = left;
            result.setNext(mergeByTitle(left.getNext(), right)); // recursively merge the remaining nodes
            if (result.getNext() != null) result.getNext().setPrev(result);
            result.setPrev(null);
        } else {
            result = right;
            result.setNext(mergeByTitle(left, right.getNext()));
            if (result.getNext() != null) result.getNext().setPrev(result);
            result.setPrev(null);
        }
        return result;
    }

    private Node mergeByDeadline(Node left, Node right) {
        if (left == null) return right;
        if (right == null) return left;

        Node result;

        if (!left.getTaskValue().getDeadline().after(right.getTaskValue().getDeadline())) {
            result = left;
            result.setNext(mergeByDeadline(left.getNext(), right));
            if (result.getNext() != null) result.getNext().setPrev(result);
            result.setPrev(null);
        } else {
            result = right;
            result.setNext(mergeByDeadline(left, right.getNext()));
            if (result.getNext() != null) result.getNext().setPrev(result);
            result.setPrev(null);
        }
        return result;
    }

    private Node mergeByTimestamp(Node left, Node right) {
        if (left == null) return right;
        if (right == null) return left;

        Node result;

        if (!left.getTaskValue().getTimestamp().after(right.getTaskValue().getTimestamp())) {
            result = left;
            result.setNext(mergeByTimestamp(left.getNext(), right));
            if (result.getNext() != null) result.getNext().setPrev(result);
            result.setPrev(null);
        } else {
            result = right;
            result.setNext(mergeByTimestamp(left, right.getNext()));
            if (result.getNext() != null) result.getNext().setPrev(result);
            result.setPrev(null);
        }
        return result;
    }
}
