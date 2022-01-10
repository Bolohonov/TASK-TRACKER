package service;

import repository.InMemoryTasksManager;
import repository.TaskManager;

public class Managers {

    private final TaskManager manager = new InMemoryTasksManager();

    public TaskManager getDefault() {
        return manager;
    }
}
