package storage;

import services.SubTaskInputOutput;
import tasks.SubTask;
import tasks.Task;

import java.util.LinkedList;

public class SubTaskStorage {

    private static LinkedList<SubTask> subTasks = new LinkedList<>();

    private static SubTaskStorage subTaskStorage;

    private SubTaskStorage () {
    }

    public static void setSubTaskStorage(Task task) {
        SubTaskInputOutput subTaskToSave = new SubTaskInputOutput();
        SubTask subTask = subTaskToSave.saveSubTask(task);
        if (subTask != null) {
            subTasks.add(subTask);
        }
    }

    public static void setSubTaskFromUserSelect() {
        SubTaskInputOutput subTaskToSave = new SubTaskInputOutput();
        SubTask subTask = subTaskToSave.saveSubTaskFromUserSelect();
        if (subTask != null) {
            subTasks.add(subTask);
        }
    }

    public static void printSubTasksList() {
        for (SubTask subTask : subTasks) {
            System.out.println(subTask.toString());
        }
    }

    public static LinkedList<SubTask> getSubTasksListFromUserSelect() {
        SubTaskInputOutput selectTask = new SubTaskInputOutput();
        Task task = selectTask.selectUserTaskByID();
        LinkedList<SubTask> subTasksListFromSelect = new LinkedList<>();
        if (task != null) {
            for (SubTask subTask : subTasks) {
                if (subTask.getTask().equals(task)) {
                    subTasksListFromSelect.add(subTask);
                }
            }
        }
        return subTasksListFromSelect;
    }

    public static LinkedList<SubTask> getSubTasksList() {
        return subTasks;
    }
}
