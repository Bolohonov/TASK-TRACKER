package repository;

import service.TaskSaver;
import tasks.SingleTask;

import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.Scanner;

public class SingleTaskRepository {

    private static LinkedList<SingleTask> singleTasks = new LinkedList<>();

    public static void setTaskStorage() {
        TaskSaver taskSaver = new TaskSaver();
        SingleTask singleTask = (SingleTask) taskSaver.createTask();
        if (singleTask != null) {
            singleTasks.add(singleTask);
        }
    }

    public static LinkedList<SingleTask> getTasks() {
        return singleTasks;
    }

//    public static void removeTask() {
//        EpicTask epicTask = selectUserTaskByID();
//        if (singleTask != null) {
//            SubTaskRepository.removeSubTask(singleTask);
//            if (SubTaskRepository.getSubTasksListByTask(singleTask).isEmpty()) {
//                TaskRepository.singleTasks.remove(singleTask);
//            }
//        }
//    }

    public static void removeAllTasks() {
        SingleTaskRepository.getTasks().clear();
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
