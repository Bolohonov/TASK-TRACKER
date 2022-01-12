package repository;

import tasks.Task;

import java.util.List;

public interface Printable<T extends Task> {

    void printList(List<T> tasksList);

    void printTask(Task task);
}
