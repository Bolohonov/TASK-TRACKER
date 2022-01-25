package repository;

import tasks.Task;

import java.util.Objects;

public class Node {

    private Task task;
    private Node next;
    private Node prev;

    Node(Node prev, Task task, Node next) {
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

    void setPrev(Node node) {
        this.prev = node;
    }

    void setNext(Node node) {
        this.next = node;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return task.equals(node.task) &&
                next.equals(node.next) &&
                prev.equals(node.prev) &&
                hashCode() == node.hashCode();
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(task, next, prev);
        result = 43 * result;
        return result;
    }
}
