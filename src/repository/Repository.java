package repository;

import tasks.Task;

import java.util.LinkedHashMap;
import java.util.Map;

public class Repository<T extends Task> {

    private final Map<Integer, T> tasks = new LinkedHashMap<>();

    public Map<Integer, T> getTasksMap() {
        return tasks;
    }

    public void putTaskToMap(T task) {
        tasks.put(task.getId(), task);
    }

    public void removeAllTasks() {
        tasks.clear();
    }

    public void removeTask(T task) {
        tasks.remove(task.getId());
    }
}
