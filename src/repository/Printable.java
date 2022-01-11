package repository;

import tasks.Task;

import java.util.LinkedList;

public interface Printable<T extends Task> {

    void printList(LinkedList<T> tasksList);

    void printTask(T task);
}
