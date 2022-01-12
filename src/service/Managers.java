package service;

import repository.*;

public class Managers {

    private final TaskManager manager = new InMemoryTasksManager();
    private final TaskCreator factory = new TaskFactory();
    private final Printable printer = new TaskPrinter();
    private final TaskUpdater updater = new TaskUpdaterImpl();

    public TaskManager getDefault() {
        return manager;
    }

    public TaskCreator getFactory() {
        return factory;
    }

    public Printable getPrinter() {
        return printer;
    }

    public TaskUpdater getUpdate() {
        return updater;
    }
}
