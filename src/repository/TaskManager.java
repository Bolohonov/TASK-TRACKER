package repository;

import service.Print;
import service.Scan;
import tasks.EpicTask;
import tasks.SubTask;
import tasks.SingleTask;

import java.util.LinkedList;

public class TaskManager<T extends SingleTask> {

    private static final SingleTaskRepository singleTaskRepository = new SingleTaskRepository();
    private static final EpicTaskRepository epicTaskRepository = new EpicTaskRepository();
    private static final SubTaskRepository subTaskRepository = new SubTaskRepository();

    T obj;

    public TaskManager() {
    }

    public static void saveFromCommand() {
        int command = Scan.selectType();
        if (command == 1) {
            singleTaskRepository.createTask();
        } else if (command == 2) {
            epicTaskRepository.createTask();
        } else if (command == 3) {
            subTaskRepository.createSubTaskFromUserSelect();
        }
    }

    public static void saveSubTaskFromCommand() {
        subTaskRepository.createSubTaskFromUserSelect();
    }

    public T returnObject(int id) {
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
        for (SubTask subtask : subTaskRepository.getTasks()) {
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
            list.forEach(System.out::println);
        }
    }

    public static void printTasks() {
        LinkedList<SingleTask> list = singleTaskRepository.getTasks();
        if (list.isEmpty()) {
            System.out.println("Список пуст!");
        } else {
            list.forEach(System.out::println);
        }
    }

    public static void printSubTasksFromUserSelect() {
        LinkedList<SubTask> list = subTaskRepository.getSubTasksListFromUserSelect();
         if (list.isEmpty()) {
                System.out.println("Список пуст!");
        } else {
            list.forEach(System.out::println);
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
