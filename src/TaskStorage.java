import java.util.LinkedList;

public class TaskStorage {

    public static LinkedList<Task> tasks = new LinkedList<>();
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
