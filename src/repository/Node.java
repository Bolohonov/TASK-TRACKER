package repository;

import tasks.Task;

import java.util.Objects;

public class Node {

    private int index;
    private Task task;
    private Node prevNode;
    private Node nextNode;

    public Node(int index, Task task, Node prevNode, Node nextNode) {
        this.index = index;
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

    int getIndex() {
        return index;
    }

    void setIndex(int i) {
        this.index = i;
    }

    void setIndexMinus() {
        this.index--;
    }

    void setIndexPlus() {
        this.index++;
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
        int result = Objects.hash(index, task);
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
}
