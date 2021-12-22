package storage;

import services.TaskInputOutput;
import tasks.Task;

import java.util.LinkedList;

public class TaskStorage {

    public static LinkedList<Task> tasks = new LinkedList<>();
    private static TaskStorage taskStorage;

    public static TaskStorage getTaskStorage() {
        if (taskStorage == null) {
            return taskStorage = new TaskStorage();
        } else {
            return null;
        }
    }

    public static void setTaskStorage() {
        Task task = TaskInputOutput.saveTask();
        if (task != null) {
            tasks.add(task);
        }
    }

    public static LinkedList<Task> getTasks() {
        return tasks;
    }

    public static void removeTask() {
        Task task = TaskInputOutput.selectUserTaskByID();
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
}
