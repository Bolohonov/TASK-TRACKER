import java.util.LinkedList;

public class TaskStorage {

    public static LinkedList<Task> tasks = new LinkedList<>();
    private static TaskStorage taskStorage;

    private TaskStorage () {
    }

    public static TaskStorage getTaskStorage() {
        if (taskStorage == null) {
            return taskStorage = new TaskStorage();
        } else {
            return null;
        }
    }

    public static void setTaskStorage() {
        TaskToSave taskToSave = new TaskToSave();
        if (taskToSave.saveTask() != null) {
            tasks.add(taskToSave.saveTask());
        }
    }
}
