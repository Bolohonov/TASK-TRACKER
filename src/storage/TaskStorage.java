package storage;

import services.TaskInputOutput;
import tasks.SubTask;
import tasks.Task;

import java.util.LinkedList;

public class TaskStorage {

    public static LinkedList<Task> tasks = new LinkedList<>();
    private static TaskStorage taskStorage;

    private static TaskStorage TaskStorage;

    public static TaskStorage getTaskStorage() {
        if (taskStorage == null) {
            return taskStorage = new TaskStorage();
        } else {
            return null;
        }
    }

    public static void setTaskStorage() {
        TaskInputOutput taskToSave = new TaskInputOutput();
        Task task = taskToSave.saveTask();
        if (task != null) {
            tasks.add(task);
        }
    }

    public static LinkedList<Task> getTasks() {
        return tasks;
    }

    public static void setTasks(LinkedList<Task> tasks) {
        TaskStorage.tasks = tasks;
    }

    public static void removeTask() {
        TaskInputOutput taskToSave = new TaskInputOutput();
        Task task = taskToSave.selectUserTaskByID();
        LinkedList<SubTask> subTaskList = SubTaskStorage.getSubTasksList();

        if (task != null) {
            for (SubTask sub : SubTaskStorage.getSubTasksList()) {
                if (sub.getTask().equals(task)) {
                    System.out.println(sub);
                }
            }
        }
    }
}
