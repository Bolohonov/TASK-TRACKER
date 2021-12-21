import java.util.LinkedList;

public class SubTaskStorage {

    public static LinkedList<SubTask> subTasks = new LinkedList<>();

    private static SubTaskStorage subTaskStorage;

    private SubTaskStorage () {
    }

    public static SubTaskStorage getSubTaskStorage() {
        if (subTaskStorage == null) {
            return subTaskStorage = new SubTaskStorage();
        } else {
            return null;
        }
    }

    public static void setSubTaskStorage(Task task) {
        SubTaskSaver subTaskToSave = new SubTaskSaver();
        if (subTaskToSave.saveSubTask(task) != null) {
            subTasks.add(subTaskToSave.saveSubTask(task));
        }
    }

    public static void setSubTaskStorage() {
        SubTaskSaver subTaskToSave = new SubTaskSaver();
        if (subTaskToSave.saveSubTask(task) != null) {
            subTasks.add(subTaskToSave.saveSubTask(task));
        }
    }
}
