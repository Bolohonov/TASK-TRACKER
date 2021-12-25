package repository;

import service.SingleTaskFactory;
import tasks.SingleTask;

import java.util.*;

public class SingleTaskRepository {

    private static HashMap<Long, SingleTask> singleTasks = new HashMap<>();
    static SingleTaskFactory singleTaskFactory = new SingleTaskFactory();

    public static void setTaskStorage() {
        SingleTask singleTask = singleTaskFactory.createTask();
        if (singleTask != null) {
            singleTasks.put(singleTask.getId(),singleTask);
        }
    }

    public static HashMap<Long, SingleTask> getTasks() {
        return singleTasks;
    }

    public static void removeTask() {
        SingleTask singleTask = selectUserTaskByID();
        if (singleTask != null) {
            SubTaskRepository.removeSubTask(singleTask);
            if (SubTaskRepository.getSubTasksListByTask(singleTask).isEmpty()) {
                TaskRepository.singleTasks.remove(singleTask);
            }
        }
    }

    public static void removeAllTasks() {
        SubTaskRepository.getTasks().clear();
        TaskRepository.getTasks().clear();
    }

    public static void replaceTask(int index, SingleTask singleTask) {
        singleTasks.set(index, singleTask);
    }

    public static int getTaskIndex(SingleTask singleTask) {
        int index = -1;
        if (singleTask != null) {
            for (SingleTask t : singleTasks) {
                if (t.equals(singleTask)) {
                    index = singleTasks.indexOf(t);
                }
            }
        }
        return index;
    }

    public static SingleTask getTaskByID(long id) {
        SingleTask singleTask = null;
        for (SingleTask t : singleTasks) {
            if (t.getId() == id) {
                singleTask = t;
            }
        }
        if (singleTask == null) {
            System.out.println("Вы ввели неверный ID задачи");
        }
        return singleTask;
    }

    public static SingleTask selectUserTaskByID() {
        int id = selectId();
        SingleTask singleTask = null;
        for (SingleTask singleTaskSelect : TaskRepository.getTasks()) {
            if (singleTaskSelect.getId() == id) {
                singleTask = singleTaskSelect;
            }
        }
        if (singleTask == null) {
            System.out.println("Вы ввели неверный ID задачи");
        }
        return singleTask;
    }

    public static int selectId() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Выберите задачу по ID: ");
        TaskManager.printTaskMap(TaskRepository.singleTasks);
        int id = 0;
        try {
            id = scanner.nextInt();
        } catch (InputMismatchException exp) {
            System.out.println("Вы ввели неверное значение!");
        }

        return id;
    }

    public static void printObjectById() {
        System.out.println("Выберите задачу по ID: ");
        int id;
        try {
            Scanner scanner = new Scanner(System.in);
            id = scanner.nextInt();
        } catch(InputMismatchException exp) {
            System.out.println("Введите числовое значение!");
            id=0;
        }
        TaskManager rep = new TaskManager();
        System.out.println(rep.returnObject(id).toString());
    }
}
