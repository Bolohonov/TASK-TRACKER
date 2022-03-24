package repository;

import java.nio.file.Paths;

public class Managers {
    private final TaskManager manager = new InMemoryTasksManager();
//    private final TaskManager httpTaskManager = new HTTPTaskManager(Paths
//            .get("/localhost"));
    private final TaskManager managerToFile =
            new FileBackedTasksManager(Paths.get("C:/WORK/Git/task-tracker-sprint7-hw/resources/data.csv"));
    private final TaskCreator factory = new TaskFactory();
    private final TaskUpdater updater = new InMemoryTaskUpdater();

    public Managers() throws ManagerSaveException {
    }

//    public TaskManager getDefault() {
//        return httpTaskManager;
//    }

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
