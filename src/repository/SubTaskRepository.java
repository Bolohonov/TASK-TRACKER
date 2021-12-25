package repository;

import service.EpicAndSubTaskFactory;
import service.Print;
import tasks.EpicTask;
import tasks.SubTask;
import tasks.SingleTask;

import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.Scanner;

public class SubTaskRepository {

    static EpicAndSubTaskFactory epicAndSubTaskFactory = new EpicAndSubTaskFactory();
    private static HashMap<Long, SubTask> subTasks = new HashMap<>();

    public static void setSubTaskStorage(EpicTask epicTask) {
        SubTask subTask = epicAndSubTaskFactory.createSubTask(epicTask);
        if (subTask != null) {
            subTasks.put(subTask.getId(), subTask);
        }
    }

    public static void setSubTaskFromUserSelect() {
        SubTask subTask = epicAndSubTaskFactory.createSubTaskFromUserSelect();
        if (subTask != null) {
            subTasks.add(subTask);
        }
    }

    public static LinkedList<SubTask> getSubTasksListFromUserSelect() {
        SingleTask singleTask = TaskRepository.selectUserTaskByID();
        LinkedList<SubTask> subTasksListFromSelect = new LinkedList<>();
        if (singleTask != null) {
            for (SubTask subTask : subTasks) {
                if (subTask.getTask().equals(singleTask)) {
                    subTasksListFromSelect.add(subTask);
                }
            }
        }
        return subTasksListFromSelect;
    }

    public static LinkedList<SubTask> getSubTasksListByTask(SingleTask singleTask) {
        LinkedList<SubTask> subTasksListByTask = new LinkedList<>();
        if (singleTask != null) {
            for (SubTask subTask : subTasks) {
                if (subTask.getTask().equals(singleTask)) {
                    subTasksListByTask.add(subTask);
                }
            }
        }
        return subTasksListByTask;
    }

    public static LinkedList<SubTask> getSubTasksListBySubTask(SubTask subTask) {
        HashMap<Long, SubTask> subTasksMap = new HashMap<>();
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

    public static HashMap<Long, SubTask> getTasks() {
        return subTasks;
    }

    public static void removeSubTask(EpicTask epicTask) {
        LinkedList<SubTask> subTasksListByTask = getSubTasksListByTask(singleTask);
        subTasks.removeAll(subTasksListByTask);
    }

    public static void removeSubTaskById() {
        SubTask selectedTask = selectUserSubTaskByID();
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

    public static SubTask getSubTaskByID(long id) {
        SubTask subTask = null;
        for (SubTask taskSelect : SubTaskRepository.getTasks()) {
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
        for (SubTask taskSelect : SubTaskRepository.getTasks()) {
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
        Print.printSubTaskList(getTasks());
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
