package repository;

import service.Print;
import service.TaskSaver;
import tasks.Task;

import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.Scanner;

public class TaskRepository {

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
            if (task.getEpic().equals(EpicStatus.EPIC)) {
                epicList.add(task);
            }
        }
        return epicList;
    }

    public static void removeTask() {
        Task task = selectUserTaskByID();
        if (task != null) {
            SubTaskRepository.removeSubTask(task);
            if (SubTaskRepository.getSubTasksListByTask(task).isEmpty()) {
                TaskRepository.tasks.remove(task);
            }
        }
    }

    public static void removeAllTasks() {
        SubTaskRepository.getSubTasksList().clear();
        TaskRepository.getTasks().clear();
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

    public static Task getTaskByID(long id) {
        Task task = null;
        for (Task t : tasks) {
            if (t.getId() == id) {
                task = t;
            }
        }
        if (task == null) {
            System.out.println("Вы ввели неверный ID задачи");
        }
        return task;
    }

    public static Task selectUserTaskByID() {
        int id = selectId();
        Task task = null;
        for (Task taskSelect : TaskRepository.getTasks()) {
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
        Print.printTaskList(TaskRepository.tasks);
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
