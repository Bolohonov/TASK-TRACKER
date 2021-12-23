package repository;

import service.Print;
import service.TaskSaver;
import tasks.SubTask;
import tasks.Task;

import java.util.LinkedList;
import java.util.Scanner;

public class TaskStorage {

    private static LinkedList<Task> tasks = new LinkedList<>();

    public static void setTaskStorage() {
        Task task = TaskSaver.saveTask();
        if (task != null) {
            tasks.add(task);
        }
    }

    public static LinkedList<Task> getTasks() {
        return tasks;
    }

    public static LinkedList<Task> getEpics() {
        LinkedList<Task> epicList = new LinkedList<>();
        for (Task task : tasks) {
            if (task.getStatus().equals(EpicStatus.EPIC)) {
                epicList.add(task);
            }
        }
        return epicList;
    }

    public static void removeTask() {
        Task task = selectUserTaskByID();
        if (task != null) {
            SubTaskStorage.removeSubTask(task);
            if (SubTaskStorage.getSubTasksListByTask(task).isEmpty()) {
                TaskStorage.tasks.remove(task);
            }
        }
    }

    public static void removeAllTasks() {
        SubTaskStorage.getSubTasksList().clear();
        TaskStorage.getTasks().clear();
    }

    public static void replaceTask(int index, Task task) {
        tasks.set(index, task);
    }

    public static int getTaskIndex(Task task) {
        int index = -1;
        if (task != null) {
            for (Task t : tasks) {
                if (t.equals(task)) {
                    index = tasks.indexOf(t);
                }
            }
        }
        return index;
    }

    public static Task selectUserTaskByID() {
        int id = selectId();
        Task task = null;
        for (Task taskSelect : TaskStorage.getTasks()) {
            if (taskSelect.getId() == id) {
                task = taskSelect;
            }
        }
        if (task == null) {
            System.out.println("Вы ввели неверный ID задачи");
        }
        return task;
    }

    public static int selectId() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Выберите задачу по ID: ");
        Print.printTaskList(TaskStorage.tasks);
        int id = scanner.nextInt();
        return id;
    }

    public static void getObjectByInt() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Выберите задачу по ID: ");
        int id = scanner.nextInt();
        Repository rep = new Repository();
        System.out.println(rep.returnObject(id).toString());
    }
}
