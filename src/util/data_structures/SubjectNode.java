package util.data_structures;

import model.Subject;

public class SubjectNode {
    public Subject value;
    public SubjectNode next;
    public SubjectNode prev;

    SubjectNode(Subject value) {
        this.value = value;
    }
}
