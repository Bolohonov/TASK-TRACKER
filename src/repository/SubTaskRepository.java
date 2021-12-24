package repository;

import service.Print;
import service.SubTaskSaver;
import service.TaskUpdater;
import tasks.SubTask;
import tasks.Task;

import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.Scanner;

public class SubTaskRepository {

    private static LinkedList<SubTask> subTasks = new LinkedList<>();

    public static void setSubTaskStorage(Task task) {
        SubTask subTask = SubTaskSaver.saveSubTask(task);
        if (subTask != null) {
            subTasks.add(subTask);
        }
    }

    public static void setSubTaskFromUserSelect() {
        SubTask subTask = SubTaskSaver.saveSubTaskFromUserSelect();
        if (subTask != null) {
            subTasks.add(subTask);
        }
    }

    public static LinkedList<SubTask> getSubTasksListFromUserSelect() {
        Task task = TaskRepository.selectUserTaskByID();
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
        SubTask selectedTask = selectUserSubTaskByID();
        Task task = selectedTask.getTask();
        subTasks.remove(selectedTask);
        if (!TaskUpdater.checkEpicStatus(task)) {
            TaskUpdater.updateEpicStatus(task, EpicStatus.NOT_EPIC);
        }
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

    public static SubTask getSubTaskByID(long id) {
        SubTask subTask = null;
        for (SubTask taskSelect : SubTaskRepository.getSubTasksList()) {
            if (taskSelect.getId() == id) {
                subTask = taskSelect;
            }
        }
        if (subTask == null) {
            System.out.println("Вы ввели неверный ID задачи");
        }
        return subTask;
    }

    public static SubTask selectUserSubTaskByID() {
        int id = selectSubTaskId();
        SubTask subTask = null;
        for (SubTask taskSelect : SubTaskRepository.getSubTasksList()) {
            if (taskSelect.getId() == id) {
                subTask = taskSelect;
            }
        }
        if (subTask == null) {
            System.out.println("Вы ввели неверный ID задачи");
        }
        return subTask;
    }

    public static int selectSubTaskId() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Выберите задачу по ID: ");
        Print.printSubTaskList(getSubTasksList());
        int id = 0;
        try {
            id = scanner.nextInt();
        } catch (InputMismatchException exp) {
            System.out.println("Вы ввели неверное значение!");
        }
        return id;
    }

    public static void replaceSubTask(int index, SubTask subTask) {
        subTasks.set(index, subTask);
    }

}
