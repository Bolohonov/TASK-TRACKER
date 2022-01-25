package repository;

import tasks.Task;

import java.util.Objects;

public class Node {

    private int index;
    private Task task;
    private Node next;
    private Node prev;

    Node(int index, Node prev, Task task, Node next) {
        this.index = index;
        this.task = task;
        this.next = next;
        this.prev = prev;
    }

    Task getTask() {
        return task;
    }

    Node getNext() {
        return next;
    }

    Node getPrev() {
        return prev;
    }

    void setTask(Task task) {
        this.task = task;
    }

    void setPrev(Node node) {
        this.prev = node;
    }

    void setNext(Node node) {
        this.next = node;
    }

    int getTaskId() {
        return this.getTask().getId();
    }

    int getIndex() {
        return index;
    }

    void setIndex(int i) {
        this.index = i;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return task.equals(node.getTask()) && index == node.getIndex();
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(task, next, prev, index);
        result = 43 * result;
        return result;
    }
}
