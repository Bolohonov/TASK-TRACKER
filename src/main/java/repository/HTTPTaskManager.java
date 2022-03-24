package repository;

import services.KVTaskClient;

import java.io.File;
import java.nio.file.Path;

public class HTTPTaskManager extends FileBackedTasksManager {

    private void KVTaskClient = new KVTaskClient();

    public HTTPTaskManager(Path path) throws ManagerSaveException {
        super(path);
    }

    @Override
    public void save() {

    }

    @Override
    public void load() {

    }

}
