package repository;

import service.EpicTaskSaver;
import service.Scan;
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
        SubTaskRepository.getSubTasks().clear();
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

    public static EpicTask selectUserTaskByID() {
        int id = Scan.selectId();
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
}
