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

    public static void setSubTaskFromUserChoice() {
        SubTaskSaver subTaskToSave = new SubTaskSaver();
        if (subTaskToSave.saveSubTaskFromUserChoice() != null) {
            subTasks.add(subTaskToSave.saveSubTaskFromUserChoice());
        }
    }

    public static void getSubTasksList() {
        for (SubTask subTask : subTaskStorage.subTasks) {
            System.out.println(subTask.toString());
        }
    }

    public static void getSubTasksListFromUserChoice() {
        SubTaskSaver chooseTask = new SubTaskSaver();
        Task task = null;
        if (chooseTask.chooseUserTask() != null) {
            task = chooseTask.chooseUserTask();
        }
        for (SubTask subTask : subTaskStorage.subTasks) {
            if (subTask.getTask().equals(task)) {
                System.out.println(subTask.toString());
            }
        }
    }


}
