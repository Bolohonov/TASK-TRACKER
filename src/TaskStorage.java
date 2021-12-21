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
        if (taskToSave.saveTask() != null) {
            tasks.add(taskToSave.saveTask());
        }
    }

    public static LinkedList<Task> getTasks() {
        return tasks;
    }

    public static void setTasks(LinkedList<Task> tasks) {
        TaskStorage.tasks = tasks;
    }
}
