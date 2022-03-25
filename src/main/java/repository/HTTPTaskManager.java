package repository;

import com.google.gson.Gson;
import services.KVTaskClient;
import tasks.EpicTask;
import tasks.SingleTask;
import tasks.SubTask;
import tasks.Task;

import java.net.URI;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

public class HTTPTaskManager extends FileBackedTasksManager {

    private Path path;
    private KVTaskClient kvTaskClient;
    protected static final HistoryManager history = new InMemoryHistoryManager();

    public HTTPTaskManager(Path path) throws ManagerSaveException {
        super(path);
        System.out.println(path.toString());
        kvTaskClient = new KVTaskClient(URI.create(path.toString()));
        this.path = path;
    }

    @Override
    public void putTask(Task task) {
        Gson gson = new Gson();
        String json = gson.toJson(task);
        kvTaskClient.put(String.valueOf(task.getId()), json);
    }

    @Override
    public boolean updateTask(Task task) throws ManagerSaveException {
        Gson gson = new Gson();
        String json = gson.toJson(task);
        kvTaskClient.put(String.valueOf(task.getId()), json );
        history.add(task);
        return true;
    }

    @Override
    public Task getTaskById(int id) throws ManagerSaveException {
        Gson gson = new Gson();
        String json = kvTaskClient.load(String.valueOf(id));
        Task task = gson.fromJson(json, Task.class);
        history.add(task);
        return task;
    }

    @Override
    public void removeAllTasks() throws ManagerSaveException {
        kvTaskClient.delete("removeAllTasks", null);
        history.clearHistory();
    }

    @Override
    public void removeTaskById(int id) throws ManagerSaveException {
        Gson gson = new Gson();
        kvTaskClient.delete("removeTaskById=", String.valueOf(id));
        history.remove(id);
    }

    @Override
    public Map<Integer, SingleTask> getSingleTasks() {
        Gson gson = new Gson();
        String json = kvTaskClient.load("getSingleTasks");
        String[] array = json.split("\n");
        Map<Integer, SingleTask> map = null;
        for(int i = 0; i < array.length; i++) {
            SingleTask task = gson.fromJson(array[i], SingleTask.class);
            map.put(task.getId(), task);
        }
        return map;
    }

    @Override
    public Map<Integer, EpicTask> getEpicTasks() {
        Gson gson = new Gson();
        String json = kvTaskClient.load("getEpicTasks");
        String[] array = json.split("\n");
        Map<Integer, EpicTask> map = null;
        for(int i = 0; i < array.length; i++) {
            EpicTask task = gson.fromJson(array[i], EpicTask.class);
            map.put(task.getId(), task);
        }
        return map;
    }

    @Override
    public Map<Integer, SubTask> getSubTasksByEpic(Task task) {
        Gson gson = new Gson();
        String json = kvTaskClient.load("getSubTasksByEpic=" + task.getId());
        EpicTask epic = gson.fromJson(json, EpicTask.class);
        return epic.getSubTasks();
    }

    @Override
    public List<Task> getHistory() {
        return history.getHistory();
    }
}
