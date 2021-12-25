package repository;

import service.Print;
import service.Scan;
import service.TaskUpdater;
import tasks.EpicTask;
import tasks.SubTask;
import tasks.SingleTask;

import java.util.LinkedList;
import java.util.Scanner;

public class TaskManager<T extends SingleTask> {

    private LinkedList<T> tasks = new LinkedList<>();

    private final static SingleTaskRepository singleTaskRepository = new SingleTaskRepository();
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
            singleTaskRepository.setTaskStorage();
        } else if (command == 2){
            epicTaskRepository.setTaskStorage();
        } else if (command == 3) {
            subTaskRepository.setSubTaskFromUserSelect();
        }
    }

    public static void saveSubTaskFromCommand() {
        subTaskRepository.setSubTaskFromUserSelect();
    }

    public T returnObject(long id) {
        for (SingleTask singleTask : singleTaskRepository.getTasks()) {
            if (singleTask.getId() == id) {
                obj = (T) singleTask;
            }
        }
        for (EpicTask epicTask : epicTaskRepository.getTasks()) {
            if (epicTask.getId() == id) {
                obj = (T) epicTask;
            }
        }
        for (SubTask subtask : subTaskRepository.getSubTasks()) {
            if (subtask.getId() == id) {
                obj = (T) subtask;
            }
        }
        return obj;
    }

    public void updateTask(T task) {
        try {
            if (task.getClass().equals(SingleTask.class)) {
                TaskUpdater.updateTask(task);
            } else if (task.getClass().equals(EpicTask.class)) {
                TaskUpdater.updateEpicTask((EpicTask) task);
            } else if (task.getClass().equals(SubTask.class)) {
                TaskUpdater.updateSubTask((SubTask) task);
            }
        } catch (NullPointerException exp) {
            Print.printWrongValue();
        }
    }


    public void printTask(T task) {
        try {
            System.out.println(task);
        } catch (NullPointerException exp) {
            System.out.println("Значение не найдено!");
        }
    }

    public static void printEpics() {
        LinkedList<EpicTask> list = epicTaskRepository.getTasks();
        if (list.isEmpty()) {
            System.out.println("Список пуст!");
        } else {
            list.forEach((EpicTask epicTask) -> System.out.println(epicTask));
        }
    }

    public static void printTasks() {
        LinkedList<SingleTask> list = singleTaskRepository.getTasks();
        if (list.isEmpty()) {
            System.out.println("Список пуст!");
        } else {
            list.forEach((SingleTask singleTask) -> System.out.println(singleTask));
        }
    }

    public static void printSubTasksFromUserSelect() {
        LinkedList<SubTask> list = subTaskRepository.getSubTasksListFromUserSelect();
        if (list.isEmpty()) {
            System.out.println("Список пуст!");
        } else {
            list.forEach((SubTask subtask) -> System.out.println(subtask));
        }
    }

    public void removeAllTasks() {
        try {
            singleTaskRepository.removeAllTasks();
        } catch (NullPointerException exp) {
            System.out.println("В списке не было задач");
        }
    }

    public void removeEpicTask() {
        try {
            epicTaskRepository.removeTask();
        } catch (NullPointerException exp) {
            System.out.println("Неверный ввод!");
        }
    }

    public void removeSubTaskById() {
        try {
            subTaskRepository.removeSubTaskById();
        } catch (NullPointerException exp) {
            System.out.println("Неверный ввод!");
        }
    }
}
