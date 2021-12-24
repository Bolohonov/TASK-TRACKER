package repository;

import tasks.Task;

import java.util.HashMap;

public abstract class Repository {

    private static HashMap<Long, Task> tasks;

    public HashMap<Long, Task> getTasks() {
        return tasks;
    }
    public Task getTaskById(Long id) {
        return tasks.get(id);
    }

    public void setTask(Task task) {
        tasks.put(task.getId(), task);
    }

}
