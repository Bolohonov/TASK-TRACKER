package storage;

import services.SubTaskInputOutput;
import services.TaskInputOutput;
import tasks.SubTask;
import tasks.Task;

import java.util.LinkedList;

public class SubTaskStorage {

    private static LinkedList<SubTask> subTasks = new LinkedList<>();

    public static void setSubTaskStorage(Task task) {
        SubTask subTask = SubTaskInputOutput.saveSubTask(task);
        if (subTask != null) {
            subTasks.add(subTask);
        }
    }

    public static void setSubTaskFromUserSelect() {
        SubTask subTask = SubTaskInputOutput.saveSubTaskFromUserSelect();
        if (subTask != null) {
            subTasks.add(subTask);
        }
    }

    public static LinkedList<SubTask> getSubTasksListFromUserSelect() {
        Task task = TaskInputOutput.selectUserTaskByID();
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

    public static LinkedList<SubTask> getSubTasksListByTask(Task task) {
        LinkedList<SubTask> subTasksListByTask = new LinkedList<>();
        if (task != null) {
            for (SubTask subTask : subTasks) {
                if (subTask.getTask().equals(task)) {
                    subTasksListByTask.add(subTask);
                }
            }
        }
        return subTasksListByTask;
    }

    public static LinkedList<SubTask> getSubTasksListBySubTask(SubTask subTask) {
        LinkedList<SubTask> subTasksListBySubTask = new LinkedList<>();
        Task task = subTask.getTask();
        if (task != null) {
            for (SubTask subT : subTasks) {
                if (subT.getTask().equals(task)) {
                    subTasksListBySubTask.add(subT);
                }
            }
        }
        return subTasksListBySubTask;
    }

    public static LinkedList<SubTask> getSubTasksList() {
        return subTasks;
    }

    public static void removeSubTask(Task task) {
        LinkedList<SubTask> subTasksListByTask = getSubTasksListByTask(task);
        subTasks.removeAll(subTasksListByTask);
    }

    public static void removeSubTaskById() {
        SubTask selectedTask = SubTaskInputOutput.selectUserSubTaskByID();
        subTasks.remove(selectedTask);
    }


    public static int getSubTaskIndex(SubTask subTask) {
        int index = -1;
        if (subTask != null) {
            for (SubTask t : subTasks) {
                if (t.equals(subTask)) {
                    index = subTasks.indexOf(t);
                }
            }
        }
        return index;
    }

    public static void replaceSubTask(int index, SubTask subTask) {
        subTasks.set(index, subTask);
    }

}
