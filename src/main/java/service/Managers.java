package service;

import repository.*;

import java.io.File;
import java.nio.file.Paths;

public class Managers {
    File file = Paths.get("./resources/data.csv").toFile();
    //private final TaskManager manager = new FileBackedTasksManager(file);
    private final TaskManager manager = new InMemoryTasksManager();
    private final TaskCreator factory = new TaskFactory();
    private final Printable printer = new TaskPrinter();
    private final TaskUpdater updater = new InMemoryTaskUpdater();

    public TaskManager getTaskManager() {
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
