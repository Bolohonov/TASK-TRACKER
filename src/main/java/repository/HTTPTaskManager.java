package repository;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import services.ConfigTaskJsonAdapter;
import services.KVTaskClient;
import services.TaskJsonAdapter;
import tasks.EpicTask;
import tasks.SingleTask;
import tasks.SubTask;
import tasks.Task;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HTTPTaskManager extends FileBackedTasksManager {

    private Path path;
    private KVTaskClient kvTaskClient;

    public HTTPTaskManager(Path path) throws ManagerSaveException, URISyntaxException {
        super(Paths.get("resources/data.csv"));
        this.path = path;
        URI url = new URI("http://" + path.toString().substring(1) + ":8078");
        kvTaskClient = new KVTaskClient(url);
    }

    @Override
    public void putTask(Task task) {
        try {
            super.putTask(task);
            Gson gson = ConfigTaskJsonAdapter.getGsonBuilder().create();
            String json = gson.toJson(task);
            kvTaskClient.put(String.valueOf(task.getId()), json);
        } catch (IntersectionException | ManagerSaveException e) {
            System.out.println(e.getMessage() + " " + e.getStackTrace());
        }
    }

    @Override
    public boolean updateTask(Task task) {
        boolean isUpdate = false;
        try {
            super.updateTask(task);
            Gson gson = ConfigTaskJsonAdapter.getGsonBuilder().create();
            String json = gson.toJson(task);
            kvTaskClient.put(String.valueOf(task.getId()), json);
            isUpdate = true;
        } catch (ManagerSaveException e) {
            System.out.println(e.getMessage() + " " + e.getStackTrace());
        }
        return isUpdate;
    }

    @Override
    public Task getTaskById(int id) throws ManagerSaveException {
        super.getTaskById(id);
        Gson gson = ConfigTaskJsonAdapter.getGsonBuilder().create();
        String json = kvTaskClient.load(String.valueOf(id));
        Task task = gson.fromJson(json, Task.class);
        return task;
    }

    @Override
    public void removeAllTasks() throws ManagerSaveException {
        super.removeAllTasks();
        kvTaskClient.delete("removeAllTasks", null);
    }

    @Override
    public void removeTaskById(int id) throws ManagerSaveException {
        super.removeTaskById(id);
        kvTaskClient.delete("removeTaskById=", String.valueOf(id));
    }

    @Override
    public Map<Integer, SingleTask> getSingleTasks() {
        Gson gson = ConfigTaskJsonAdapter.getGsonBuilder().create();
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
        Gson gson = ConfigTaskJsonAdapter.getGsonBuilder().create();
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
        super.getSubTasksByEpic(task);
        Gson gson = ConfigTaskJsonAdapter.getGsonBuilder().create();
        String json = kvTaskClient.load("getSubTasksByEpic=" + task.getId());
        EpicTask epic = gson.fromJson(json, EpicTask.class);
        return epic.getSubTasks();
    }

    @Override
    public List<Task> getHistory() {
        return super.getHistory();
    }
}
