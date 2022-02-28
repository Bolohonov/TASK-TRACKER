package service;

import main.java.tasks.Task;

import java.util.List;
import java.util.Map;

public interface Printable<T extends Task> {

    void printMap(Map<Integer, T> tasksMap);

    void printList(List<T> tasksList);

    void printTask(Task task);
}
