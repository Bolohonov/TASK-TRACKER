package repository;

import service.EpicAndSubTaskFactory;
import service.Scan;
import tasks.EpicTask;
import tasks.SingleTask;

import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.Scanner;

public class EpicTaskRepository {

    private static LinkedList<EpicTask> epicTasks = new LinkedList<>();

    public static void setTaskStorage() {
        EpicTask task = EpicAndSubTaskFactory.createTask();
        if (task != null) {
            epicTasks.add(task);
        }
    }

    public static HashMap<Long, EpicTask> getTasks() {
        return epicTasks;
    }

    public static void removeTask() {
        EpicTask EpicTask = selectEpicTaskByID();
        if (EpicTask != null) {
            SubTaskRepository.removeSubTask(EpicTask);
            if (SubTaskRepository.getSubTasksListByTask(singleTask).isEmpty()) {
                EpicTaskRepository.epicTask.remove(singleTask);
            }
        }
    }

    public static void removeAllTasks() {
        SubTaskRepository.getTasks().clear();
        EpicTaskRepository.getTasks().clear();
    }

    public static void replaceTask(int index, EpicTask epicTask) {
        epicTasks.set(index, epicTask);
    }

    public static int getTaskIndex(EpicTask epicTask) {
        int index = -1;
        if (epicTask != null) {
            for (EpicTask et : epicTasks) {
                if (et.equals(epicTask)) {
                    index = epicTask.indexOf(et);
                }
            }
        }
        return index;
    }

    public static SingleTask getTaskByID(long id) {
        SingleTask singleTask = null;
        for (SingleTask t : tasks) {
            if (t.getId() == id) {
                singleTask = t;
            }
        }
        if (singleTask == null) {
            System.out.println("Вы ввели неверный ID задачи");
        }
        return singleTask;
    }

    public static EpicTask selectEpicTaskByID() {
        int id = Scan.selectId();
        EpicTask epicTask = null;
        for (EpicTask epicTaskSelect : EpicTaskRepository.getTasks()) {
            if (epicTaskSelect.getId() == id) {
                epicTaskSelect = epicTaskSelect;
            }
        }
        if (epicTask == null) {
            System.out.println("Вы ввели неверный ID задачи");
        }
        return epicTask;
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
