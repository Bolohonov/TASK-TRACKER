package service;

import repository.InMemoryTasksManager;

public class Managers {

    private final InMemoryTasksManager manager = new InMemoryTasksManager();

    public InMemoryTasksManager getDefault() {
        return manager;
    }
}
