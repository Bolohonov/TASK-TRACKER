package repository;

import service.EpicTaskSaver;
import service.Print;
import tasks.EpicTask;
import tasks.SingleTask;

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

    public static LinkedList<SingleTask> getEpics() {
        LinkedList<SingleTask> epicList = new LinkedList<>();
        for (SingleTask singleTask : tasks) {
            if (singleTask.getEpic().equals(EpicStatus.EPIC)) {
                epicList.add(singleTask);
            }
        }
        return epicList;
    }

    public static void removeTask() {
        SingleTask singleTask = selectUserTaskByID();
        if (singleTask != null) {
            SubTaskRepository.removeSubTask(singleTask);
            if (SubTaskRepository.getSubTasksListByTask(singleTask).isEmpty()) {
                EpicTaskRepository.tasks.remove(singleTask);
            }
        }
    }

    public static void removeAllTasks() {
        SubTaskRepository.getSubTasksList().clear();
        EpicTaskRepository.getTasks().clear();
    }

    public static void replaceTask(int index, SingleTask singleTask) {
        tasks.set(index, singleTask);
    }

    public static int getTaskIndex(SingleTask singleTask) {
        int index = -1;
        if (singleTask != null) {
            for (SingleTask t : tasks) {
                if (t.equals(singleTask)) {
                    index = tasks.indexOf(t);
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

    public static SingleTask selectUserTaskByID() {
        int id = selectId();
        SingleTask singleTask = null;
        for (SingleTask singleTaskSelect : EpicTaskRepository.getTasks()) {
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
        Print.printTaskList(EpicTaskRepository.tasks);
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
        RepositoryTaskManager rep = new RepositoryTaskManager();
        System.out.println(rep.returnObject(id).toString());
    }
}
