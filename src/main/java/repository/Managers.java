package repository;

import java.nio.file.Path;
import java.nio.file.Paths;

public class Managers {
    private final TaskManager manager = new InMemoryTasksManager();
    private final TaskManager managerToFile =
            new FileBackedTasksManager(Paths.get("./resources/data.csv").toFile());
    private final TaskCreator factory = new TaskFactory();
    private final TaskUpdater updater = new InMemoryTaskUpdater();

    public TaskManager getTaskManager() {
        return manager;
    }

    public TaskManager getTaskManagerToFile() {
        return managerToFile;
    }

    public TaskCreator getFactory() {
        return factory;
    }

    public TaskUpdater getUpdate() {
        return updater;
    }
}
