package repository;

import service.EpicTaskSaver;
import service.Print;
import tasks.EpicTask;

import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.Scanner;

public class EpicTaskRepository {

    private static LinkedList<EpicTask> epicTasks = new LinkedList<>();

    public static void setTaskStorage() {
        EpicTask task = EpicTaskSaver.createTask();
        if (task != null) {
            epicTasks.add(task);
        }
    }

    public static LinkedList<EpicTask> getTasks() {
        return epicTasks;
    }

    public static void removeTask() {
        EpicTask epicTask = selectUserTaskByID();
        if (epicTask != null) {
            SubTaskRepository.removeSubTask(epicTask);
            if (SubTaskRepository.getSubTasksListByTask(epicTask).isEmpty()) {
                EpicTaskRepository.epicTasks.remove(epicTask);
            }
        }
    }

    public static void removeAllTasks() {
        SubTaskRepository.getSubTasksList().clear();
        EpicTaskRepository.getTasks().clear();
    }

    public static void replaceTask(int index, EpicTask epicTask) {
        epicTasks.set(index, epicTask);
    }

    public static int getTaskIndex(EpicTask epicTask) {
        int index = -1;
        if (epicTask != null) {
            for (EpicTask t : epicTasks) {
                if (t.equals(epicTask)) {
                    index = epicTasks.indexOf(t);
                }
            }
        }
        return index;
    }

    public static EpicTask getTaskByID(long id) {
        EpicTask epicTask = null;
        for (EpicTask t : epicTasks) {
            if (t.getId() == id) {
                epicTask = t;
            }
        }
        if (epicTask == null) {
            System.out.println("Вы ввели неверный ID задачи");
        }
        return epicTask;
    }

    public static EpicTask selectUserTaskByID() {
        int id = selectId();
        EpicTask epicTask = null;
        for (EpicTask epicTaskSelect : EpicTaskRepository.getTasks()) {
            if (epicTaskSelect.getId() == id) {
                epicTask = epicTaskSelect;
            }
        }
        if (epicTask == null) {
            System.out.println("Вы ввели неверный ID задачи");
        }
        return epicTask;
    }

    public static int selectId() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Выберите задачу по ID: ");
        Print.printEpicTaskList(EpicTaskRepository.epicTasks);
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
