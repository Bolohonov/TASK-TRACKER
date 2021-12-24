package repository;

import service.*;
import tasks.Task;
import tasks.EpicTask;
import tasks.SubTask;
import tasks.SingleTask;

import java.util.LinkedList;

public class TaskManager<T extends Task> {

    private LinkedList<T> tasks = new LinkedList<>();

    TaskManager<T> taskManager;

    private static TaskFactory taskFactory;

    T obj;

    public TaskManager() {
    }

    public TaskManager(T obj) {
        this.obj = obj;
    }

    public void saveFromCommand() {
        int command = Scan.selectTaskTypeFromUser();
        if (command == 1) {
            taskFactory = new SingleTaskFactory();
            taskFactory.createTask();
        } else if (command == 2){
            taskFactory = new EpicAndSubTaskFactory();
            taskFactory.createTask();
        } else if (command == 3) {
            taskFactory = new EpicAndSubTaskFactory();
            taskFactory.createTask();
        }
    }

    public T returnObject(long id) {
        for (SingleTask singleTask : TaskRepository.getTasks()) {
            if (singleTask.getId() == id) {
                obj = (T) singleTask;
            }
        }
        for (EpicTask epicTask : EpicTaskRepository.getTasks()) {
            if (epicTask.getId() == id) {
                obj = (T) epicTask;
            }
        }
        for (SubTask subtask : SubTaskRepository.getSubTasksList()) {
            if (subtask.getId() == id) {
                obj = (T) subtask;
            }
        }
        return obj;
    }

    public static void printTaskList(LinkedList<T> list) {
        if (list.isEmpty()) {
            System.out.println("Список пуст!");
        } else {
            list.forEach((T task) -> System.out.println(task));
        }
    }

    public void setObjectToRepository(T obj) {
        System.out.println(obj.getClass().toString());
    }

    public void setObjectToRepository() {
        if (obj.getClass().equals(SingleTask.class)) {
            TaskRepository.setTaskStorage();
        } else if (obj.getClass().equals(SubTask.class)) {
            SubTaskRepository.setSubTaskFromUserSelect();
        }

    }

    public TaskManager getRepositoryObj(T obj) {
        return taskManager = new TaskManager<T>();
    }

}
