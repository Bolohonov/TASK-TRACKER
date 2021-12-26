package repository;

import service.Scan;
import tasks.EpicTask;
import tasks.SubTask;

import java.util.LinkedList;

public class SubTaskRepository {

    private static LinkedList<SubTask> subTasks = new LinkedList<>();

    public static void setSubTaskStorage(EpicTask epicTask) {
        SubTask subTask = SubTaskSaver.createSubTask(epicTask);
        if (subTask != null) {
            subTasks.add(subTask);
        }
    }

    public static void setSubTaskFromUserSelect() {
        SubTask subTask = SubTaskSaver.createSubTaskFromUserSelect();
        if (subTask != null) {
            subTasks.add(subTask);
        }
    }

    public static LinkedList<SubTask> getSubTasksListFromUserSelect() {
        EpicTask epicTask = EpicTaskRepository.selectUserTaskByID();
        LinkedList<SubTask> subTasksListFromSelect = new LinkedList<>();
        if (epicTask != null) {
            for (SubTask subTask : subTasks) {
                if (subTask.getTask().equals(epicTask)) {
                    subTasksListFromSelect.add(subTask);
                }
            }
        }
        return subTasksListFromSelect;
    }

    public static LinkedList<SubTask> getSubTasksListByTask(EpicTask epicTask) {
        LinkedList<SubTask> epicTasksListByTask = new LinkedList<>();
        if (epicTask != null) {
            for (SubTask subTask : subTasks) {
                if (subTask.getTask().equals(epicTask)) {
                    epicTasksListByTask.add(subTask);
                }
            }
        }
        return epicTasksListByTask;
    }

    public static LinkedList<SubTask> getSubTasksListBySubTask(SubTask subTask) {
        LinkedList<SubTask> subTasksListBySubTask = new LinkedList<>();
        EpicTask epicTask = subTask.getTask();
        if (epicTask != null) {
            for (SubTask subT : subTasks) {
                if (subT.getTask().equals(epicTask)) {
                    subTasksListBySubTask.add(subT);
                }
            }
        }
        return subTasksListBySubTask;
    }

    public static LinkedList<SubTask> getSubTasks() {
        return subTasks;
    }

    public static void removeSubTask(EpicTask epicTask) {
        LinkedList<SubTask> subTasksListByTask = getSubTasksListByTask(epicTask);
        subTasks.removeAll(subTasksListByTask);
    }

    public static void removeSubTaskById() {
        SubTask selectedTask = selectUserSubTaskByID();
        subTasks.remove(selectedTask);
    }

    public static int getTaskIndex(SubTask subTask) {
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

    public static SubTask selectUserSubTaskByID() {
        int id = Scan.selectId();
        SubTask subTask = null;
        for (SubTask taskSelect : SubTaskRepository.getSubTasks()) {
            if (taskSelect.getId() == id) {
                subTask = taskSelect;
            }
        }
        if (subTask == null) {
            System.out.println("Вы ввели неверный ID задачи");
        }
        return subTask;
    }

    public static void replaceSubTask(int index, SubTask subTask) {
        subTasks.set(index, subTask);
    }

}
