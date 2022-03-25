package repository;

import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Paths;

public class Managers {
    private final TaskManager manager = new InMemoryTasksManager();
    final TaskManager httpTaskManager = new HTTPTaskManager(Paths
            .get(new URL("http://www.localhost:8078").getPath()));
    private final TaskManager managerToFile =
            new FileBackedTasksManager(Paths.get("resources/data.csv"));
    private final TaskCreator factory = new TaskFactory();
    private final TaskUpdater updater = new InMemoryTaskUpdater();

    public Managers() throws ManagerSaveException, MalformedURLException {
    }

    public TaskManager getDefault() {
        return httpTaskManager;
    }

    public TaskManager getInMemoryTasksManager() {
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
