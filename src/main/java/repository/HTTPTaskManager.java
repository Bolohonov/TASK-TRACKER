package repository;

import java.io.File;
import java.nio.file.Path;

public class HTTPTaskManager extends FileBackedTasksManager {
    public HTTPTaskManager(Path path) throws ManagerSaveException {
        super(path);
    }
}
