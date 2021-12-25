package repository;

import service.EpicTaskSaver;
import service.Scan;
import service.SubTaskSaver;
import service.TaskSaver;
import tasks.Task;
import tasks.EpicTask;
import tasks.SubTask;
import tasks.SingleTask;

import java.util.LinkedList;

public class TaskManager<T extends Task> {

    private LinkedList<T> tasks = new LinkedList<>();

    private final static TaskRepository taskRepository = new TaskRepository();
    private final static EpicTaskRepository epicTaskRepository = new EpicTaskRepository();
    private final static SubTaskRepository subTaskRepository = new SubTaskRepository();

    TaskManager<T> taskManager;

    T obj;

    public TaskManager() {
    }

    public TaskManager(T obj) {
        this.obj = obj;
    }

    public static void saveFromCommand() {
        int command = Scan.selectType();
        if (command == 1) {
            taskRepository.setTaskStorage();
        } else if (command == 2){
            epicTaskRepository.setTaskStorage();
        }
    }

    public static void saveSubTaskFromCommand() {
        SubTaskRepository.setSubTaskFromUserSelect();
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

    private void printList(LinkedList<T> tasks) {
        if (tasks.isEmpty()) {
            System.out.println("Список пуст!");
        } else {
            tasks.forEach((T task) -> System.out.println(task));
        }
    }

    public void printTasks(TaskRepository.getTasks()) {
        if (tasks.isEmpty()) {
            System.out.println("Список пуст!");
        } else {
            tasks.forEach((T task) -> System.out.println(task));
        }
    }

    public static void printEpics(LinkedList<EpicTask> list) {
        if (list.isEmpty()) {
            System.out.println("Список пуст!");
        } else {
            list.forEach((EpicTask epicTask) -> System.out.println(epicTask));
        }
    }

    public static void printSubTasks(LinkedList<SubTask> list) {
        if (list.isEmpty()) {
            System.out.println("Список пуст!");
        } else {
            list.forEach((SubTask subtask) -> System.out.println(subtask));
        }
    }

    public TaskManager getRepositoryObj(T obj) {
        return taskManager = new TaskManager<T>();
    }



}
