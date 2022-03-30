package repository;

import com.google.gson.*;
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
import java.util.HashMap;
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
            System.out.println(json);
            if (task instanceof SubTask) {
                EpicTask epic = (EpicTask) getTaskById(((SubTask) task).getEpicId());
                epic.addSubTask((SubTask) task);
                updateTask(epic);
            }
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
        kvTaskClient.delete("removeAllTasks");
    }

    @Override
    public void removeTaskById(int id) throws ManagerSaveException {
        try {
            super.removeTaskById(id);
            Task task = getTaskById(id);
            if (task instanceof SubTask) {
                EpicTask epic = (EpicTask) getTaskById(((SubTask) task).getEpicId());
                epic.removeSubTask((SubTask) task);
                updateTask(epic);
            }
            kvTaskClient.delete("removeTaskById=" + id);
        } catch (ManagerSaveException e) {
            System.out.println(e.getMessage() + " " + e.getStackTrace());
        }
    }

    @Override
    public Map<Integer, SingleTask> getSingleTasks() {
        Map<Integer, SingleTask> map = new HashMap<>();
        Gson gsonOfTask = ConfigTaskJsonAdapter.getGsonBuilder().create();
        String json = kvTaskClient.load("getSingleTasks");
        String[] array = json.split("\n");
        for(int i = 1; i < array.length; i++) {
            SingleTask task = gsonOfTask.fromJson(array[i], SingleTask.class);
            map.put(task.getId(), task);
        }
        return map;
    }

    @Override
    public Map<Integer, EpicTask> getEpicTasks() {
        Map<Integer, EpicTask> map = new HashMap<>();
        Gson gsonOfEpicTask = ConfigTaskJsonAdapter.getGsonBuilder().create();
        String json = kvTaskClient.load("getEpicTasks");
        String[] array = json.split("\n");
        for(int i = 1; i < array.length; i++) {
            EpicTask task = gsonOfEpicTask.fromJson(array[i], EpicTask.class);
            map.put(task.getId(), task);
        }
        return map;
    }

    @Override
    public Map<Integer, SubTask> getSubTasksByEpic(Task task) throws ManagerSaveException {
        super.getSubTasksByEpic(task);
        Map<Integer, SubTask> map = new HashMap<>();
        String json = kvTaskClient.load("getSubTasksByEpic=" + task.getId());
        String[] array = json.split(",");
        for(int i = 0; i < array.length; i++) {
            if(!array[i].isBlank()) {
                SubTask sub = (SubTask) getTaskById(Integer.parseInt(array[i]));
                map.put(sub.getId(), sub);
            }
        }
        return map;
    }

    @Override
    public List<Task> getHistory() {
        return super.getHistory();
    }
}
