package repository;

import java.io.File;

public class HTTPTaskManager extends FileBackedTasksManager {
    public HTTPTaskManager(File file) throws ManagerSaveException {
        super(file);
    }
}
