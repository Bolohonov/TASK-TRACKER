package storage;

import services.Print;
import services.TaskSaver;
import tasks.Task;

import java.util.LinkedList;
import java.util.Scanner;

public class TaskStorage {

    public static LinkedList<Task> tasks = new LinkedList<>();

    public static void setTaskStorage() {
        Task task = TaskSaver.saveTask();
        if (task != null) {
            tasks.add(task);
        }
    }

    public static LinkedList<Task> getTasks() {
        return tasks;
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
}
