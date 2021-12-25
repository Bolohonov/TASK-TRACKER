package repository;

import service.*;
import tasks.Task;
import tasks.EpicTask;
import tasks.SubTask;
import tasks.SingleTask;

import java.util.HashMap;
import java.util.LinkedList;

public class TaskManager<T extends Task> {

    private final HashMap<Long, SingleTask> singleTasks = new HashMap<>();
    private final HashMap<Long, EpicTask> epicTasks = new HashMap<>();
    private final HashMap<Long, SubTask> subTasks = new HashMap<>();

    SingleTaskFactory singleTaskFactory = new SingleTaskFactory();
    EpicAndSubTaskFactory epicAndSubTaskFactory = new EpicAndSubTaskFactory();

    private HashMap<Long, T> tasks = new HashMap<>();

    TaskManager<T> taskManager;

    T obj;

    public TaskManager() {
    }

    public TaskManager(T obj) {
        this.obj = obj;
    }

    public T returnObject(long id) {
        for (Long i : SingleTaskRepository.getTasks().keySet()) {
            if (i == id) {
                obj = (T) SingleTaskRepository.getTasks().get(i);
            }
        }
        for (Long i : EpicTaskRepository.getTasks().keySet()) {
            if (i == id) {
                obj = (T) EpicTaskRepository.getTasks().get(i);
            }
        }
        for (Long i : SubTaskRepository.getTasks().keySet()) {
            if (i == id) {
                obj = (T) SubTaskRepository.getTasks().get(i);
            }
        }
        return obj;
    }

    public void printTaskMap(HashMap<Long, T> map) {
        if (map.isEmpty()) {
            System.out.println("Список пуст!");
        } else {
            for (Long id : map.keySet()) {
                System.out.println(map.get(id));
            }
        }
    }

    public void saveFromCommand() {
        int command = Scan.selectTaskTypeFromUser();
        if (command == 1) {
            setObjectToRepository((T)singleTaskFactory.createTask());
        } else if (command == 2){
            setObjectToRepository((T)epicAndSubTaskFactory.createTask());
        } else if (command == 3) {
            setObjectToRepository((T)epicAndSubTaskFactory.createSubTaskFromUserSelect());
        }
    }

    public void setObjectToRepository(T obj) {
        tasks.put(obj.getId(), obj);
    }

    public void setObjectToRepository() {
        if (obj.getClass().equals(SingleTask.class)) {
            SingleTaskRepository.setTaskStorage();
        } else if (obj.getClass().equals(SubTask.class)) {
            SubTaskRepository.setSubTaskFromUserSelect();
        }

    }

    public TaskManager getRepositoryObj(T obj) {
        return taskManager = new TaskManager<T>();
    }

}
