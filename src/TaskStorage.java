import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Scanner;

public class TaskStorage {

    public static LinkedList<Task> tasks = new LinkedList<>();
    public static LinkedHashMap<Task, LinkedList<SubTask>> epics = new LinkedHashMap<>();
    private static TaskStorage taskStorage;

    TaskStorage () {
    }

    public static TaskStorage getTaskStorage() {
        if (taskStorage == null) {
            return taskStorage = new TaskStorage();
        } else {
            return null;
        }
    }

    public static void setTaskStorage() {
        TaskSaver taskToSave = new TaskSaver();
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
}
