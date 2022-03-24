package repository;

import com.google.gson.Gson;
import services.KVTaskClient;
import tasks.SingleTask;
import tasks.Task;

import java.io.File;
import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

public class HTTPTaskManager extends FileBackedTasksManager {

    private Path path;
    private KVTaskClient kvTaskClient = new KVTaskClient(URI.create(path.toString()));

    public HTTPTaskManager(Path path) throws ManagerSaveException {
        super(path);
        this.path = path;
    }

    @Override
    public void putTask(Task task) throws IntersectionException, ManagerSaveException {
        Gson gson = new Gson();
        String json = gson.toJson(task);
        kvTaskClient.put("put", json );
    }

    @Override
    public boolean updateTask(Task task) throws ManagerSaveException {
        Gson gson = new Gson();
        String json = gson.toJson(task);
        kvTaskClient.put("put", json );
        return true;
    }

    @Override
    public Task getTaskById(int id) throws ManagerSaveException {
        Gson gson = new Gson();
        String json = kvTaskClient.load("getTaskById=" + id);
        Task task = gson.fromJson(json, Task.class);
        return task;
    }

    @Override
    public void removeAllTasks() throws ManagerSaveException {
        kvTaskClient.put("removeAllTasks", null);
    }

    @Override
    public void removeTaskById(int id) throws ManagerSaveException {
        Gson gson = new Gson();
        String json = kvTaskClient.load("removeTaskById=" + id);
    }
}
