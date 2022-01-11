package service;

import repository.*;

public class Managers {

    private final TaskManager manager = new InMemoryTasksManager();
    private final Saveable factory = new TaskFactory();
    private final Printable printer = new TaskPrinter();
    private final Updateable updater = new TaskUpdater();

    public TaskManager getDefault() {
        return manager;
    }

    public Saveable getFactory() {
        return factory;
    }

    public Printable getPrinter() {
        return printer;
    }

    public Updateable getUpdate() {
        return updater;
    }
}
