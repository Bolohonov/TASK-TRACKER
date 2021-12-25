package repository;

import service.Print;
import service.Scan;
import tasks.EpicTask;
import tasks.SubTask;
import tasks.SingleTask;

import java.util.InputMismatchException;
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
        }
    }

    public static void saveSubTaskFromCommand() {
        SubTaskRepository.setSubTaskFromUserSelect();
    }

    public T returnObject(long id) {
        for (SingleTask singleTask : SingleTaskRepository.getTasks()) {
            if (singleTask.getId() == id) {
                obj = (T) singleTask;
            }
        }
        for (EpicTask epicTask : EpicTaskRepository.getTasks()) {
            if (epicTask.getId() == id) {
                obj = (T) epicTask;
            }
        }
        for (SubTask subtask : SubTaskRepository.getSubTasks()) {
            if (subtask.getId() == id) {
                obj = (T) subtask;
            }
        }
        return obj;
    }

    public T setStatus(T task) {
        System.out.println("Текущий статус" + task.getStatus());
        System.out.println("Выберите статус");
        Print.printStatusList();
        int statusIndex = Scan.getScanOrZero();
        switch (statusIndex) {
            case 2:
                updateTaskStatus(task, TaskStatus.IN_PROGRESS);
                break;
            case 3:
                updateTaskStatus(task, TaskStatus.DONE);
                break;
            default:
                Print.printMistake();
                break;
        }
        return task;
    }

    public void replaceTask(T task) {
        if (task.getClass().equals(SingleTask.class)) {
            for (SingleTask task : singleTaskRepository.getTasks()) {
                if (task.getId() == )
            }
            singleTaskRepository.getTasks();
                    singleTasks.set(index, singleTask);
        }
        for (SingleTask singleTask : SingleTaskRepository.getTasks()) {
            if (singleTask.getId() == id) {
                obj = (T) singleTask;
            }
        }
        for (EpicTask epicTask : EpicTaskRepository.getTasks()) {
            if (epicTask.getId() == id) {
                obj = (T) epicTask;
            }
        }
        for (SubTask subtask : SubTaskRepository.getSubTasks()) {
            if (subtask.getId() == id) {
                obj = (T) subtask;
            }
        }
        return obj;
    }

    public static void updateTaskById(T task) {
        int command = -1;
        while (command != 0) {
            Print.printMenuToUpdateTask();
            Scan.getScanOrZero();
            switch (command) {
                case 0:
                    break;
                case 1:
                    System.out.println("Введите новое название задачи");
                    scanner = new Scanner(System.in);
                    String name = scanner.nextLine();
                    if (name != null) {
                        singleTask.setName(name);
                    }
                    TaskRepository.replaceTask(index, singleTask);
                    break;
                case 2:
                    System.out.println("Введите новое описание задачи");
                    scanner = new Scanner(System.in);
                    String description = scanner.nextLine();
                    if (description != null) {
                        singleTask.setDescription(description);
                    }
                    TaskRepository.replaceTask(index, singleTask);
                    break;
                case 3:
                    if (!checkEpicStatus(singleTask)) {
                        singleTask = setStatus(singleTask);
                    } else {
                        System.out.println("Статус не подлежит изменению!");
                    }
                    break;
                default:
                    Print.printMistake();
                    break;
            }
        }
    }

    public void updateTaskStatus(T task, TaskStatus status) {
        task.setStatus(status);
        task.getId();

        int index = TaskRepository.getTaskIndex(singleTask);
        TaskRepository.replaceTask(index, singleTask);
    }

    public void printTask(T task) {
        try {
            System.out.println(task);
        } catch (NullPointerException exp) {
            System.out.println("Значение не найдено!");
        }

    }

//    public void printTasks() {
//        if (singleTaskRepository.getTasks().isEmpty()) {
//            System.out.println("Список пуст!");
//        } else {
//            singleTaskRepository.getTasks().forEach((SingleTask task) -> System.out.println(task));
//        }
//    }

    public static void printEpics() {
        if (epicTaskRepository.getTasks().isEmpty()) {
            System.out.println("Список пуст!");
        } else {
            epicTaskRepository.getTasks().forEach((EpicTask epicTask)
                    -> System.out.println(epicTask));
        }
    }

    public static void printSubTasks() {
        if (subTaskRepository.getSubTasks().isEmpty()) {
            System.out.println("Список пуст!");
        } else {
            subTaskRepository.getSubTasks().forEach((SubTask subtask)
                    -> System.out.println(subtask));
        }
    }

    public static void printSubTasksFromUserSelect() {
        if (subTaskRepository.getSubTasksListFromUserSelect().isEmpty()) {
            System.out.println("Список пуст!");
        } else {
            subTaskRepository.getSubTasksListFromUserSelect().forEach((SubTask subtask)
                    -> System.out.println(subtask));
        }
    }

    public void removeAllTasks() {
        try {
            SingleTaskRepository.removeAllTasks();
        } catch (NullPointerException exp) {
            System.out.println("В списке не было задач");
        }
    }

    public void removeEpicTask() {
        try {
            EpicTaskRepository.removeTask();
        } catch (NullPointerException exp) {
            System.out.println("Неверный ввод!");
        }
    }

    public void removeSubTaskById() {
        try {
            SubTaskRepository.removeSubTaskById();
        } catch (NullPointerException exp) {
            System.out.println("Неверный ввод!");
        }
    }

    public void updateTask(T task) {

    }


}
