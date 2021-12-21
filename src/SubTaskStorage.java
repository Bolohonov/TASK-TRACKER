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
        SubTask subTask = subTaskToSave.saveSubTask(task);
        if (subTask != null) {
            subTasks.add(subTask);
        }
    }

    public static void setSubTaskFromUserChoice() {
        SubTaskSaver subTaskToSave = new SubTaskSaver();
        SubTask subTask = subTaskToSave.saveSubTaskFromUserChoice();
        if (subTask != null) {
            subTasks.add(subTask);
        }
    }

    public static void getSubTasksList() {
        for (SubTask subTask : subTasks) {
            System.out.println(subTask.toString());
        }
    }

    public static void getSubTasksListFromUserChoice() {
        SubTaskSaver chooseTask = new SubTaskSaver();
        Task task = chooseTask.chooseUserTask();
        if (task != null) {
            for (SubTask subTask : subTasks) {
                if (subTask.getTask().equals(task)) {
                    System.out.println(subTask);
                }
            }
        }
    }


}
