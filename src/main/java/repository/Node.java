package repository;

import tasks.Task;

import java.util.Objects;

public class Node {

    private Task task;
    private Node prevNode;
    private Node nextNode;

    public Node(Task task, Node prevNode, Node nextNode) {
        this.task = task;
        this.prevNode = prevNode;
        this.nextNode = nextNode;
    }

    Task getTask() {
        return task;
    }

    void setTask(Task task) {
        this.task = task;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return this.equals(node.getTask()) && this.prevNode.equals(node.prevNode)
                && this.nextNode.equals(node.nextNode);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(task, prevNode, nextNode);
        result = 43 * result;
        return result;
    }

    public Node getPrevNode() {
        return prevNode;
    }

    public void setPrevNode(Node prevNode) {
        this.prevNode = prevNode;
    }

    public Node getNextNode() {
        return nextNode;
    }

    public void setNextNode(Node nextNode) {
        this.nextNode = nextNode;
    }
}
