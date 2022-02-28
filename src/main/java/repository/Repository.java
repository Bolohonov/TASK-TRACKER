package main.java.repository;

import main.java.tasks.Task;

import java.util.LinkedHashMap;
import java.util.Map;

public class Repository<T extends Task> {

    private final Map<Integer, T> tasks = new LinkedHashMap<>();

    public Map<Integer, T> getTasks() {
        return tasks;
    }

    public void putTask(T task) {
        tasks.put(task.getId(), task);
    }

    public void removeAllTasks() {
        tasks.clear();
    }
}
