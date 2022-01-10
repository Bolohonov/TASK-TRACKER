package repository;

import tasks.Task;

import java.util.LinkedList;

public class Repository<T extends Task> {

    private final LinkedList<T> tasks = new LinkedList<>();

    public LinkedList<T> getTasks() {
        return tasks;
    }

    public void addTask(T task) {
        tasks.add(task);
    }

    public void removeAllTasks() {
        tasks.clear();
    }

    public void removeTask(T task) {
        tasks.remove(task);
    }
}
