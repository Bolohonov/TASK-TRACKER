package repository;

import service.Print;
import service.Scan;
import tasks.EpicTask;
import tasks.SubTask;

import java.util.LinkedList;

public class SubTaskRepository {

    private static LinkedList<SubTask> subTasks = new LinkedList<>();


    static void createSubTask(EpicTask epicTask) {
        SubTask subTask;
        String[] userTask = Scan.saveLinesFromUser();
        subTask = new SubTask(epicTask, userTask[0], userTask[1]);
        try {
            epicTask.setSubTaskToList(subTask);
        } catch (NullPointerException exp) {
            System.out.println("Подзадача не найдена!");
        }
        subTasks.add(subTask);
        epicTask.setStatus(TaskStatus.IN_PROGRESS);
    }

    static void createSubTaskFromUserSelect() {
        EpicTask epicTask = EpicTaskRepository.selectUserTaskByID();
        if (epicTask != null) {
            SubTaskRepository.createSubTask(epicTask);
        } else {
            System.out.println("Эпика пока нет");
        }
    }

    static EpicTask createSubTaskFromEpicTask(EpicTask epicTask) {
        int command = -1;
        while (command != 0) {
            Print.printMenuToAddSubTask();
            command = Scan.getIntOrZero();
            switch (command) {
                case 0:
                    break;
                case 1:
                    try {
                        SubTaskRepository.createSubTask(epicTask);
                    } catch (NullPointerException exp) {
                        System.out.println("Эпика пока нет");
                    }
                    break;
                default:
                    System.out.println("Вы ввели неверное значение!");
                    break;
            }
        }
        return epicTask;
    }

    static LinkedList<SubTask> getSubTasksListFromUserSelect() {
        EpicTask epicTask = EpicTaskRepository.selectUserTaskByID();
        return epicTask.getSubTasksList();
    }

    static LinkedList<SubTask> getSubTasksListByTask(EpicTask epicTask) {
        LinkedList<SubTask> epicTasksListByTask = new LinkedList<>();
        if (epicTask != null) {
            for (SubTask subTask : subTasks) {
                if (subTask.getEpicTask().equals(epicTask)) {
                    epicTasksListByTask.add(subTask);
                }
            }
        }
        return epicTasksListByTask;
    }

    static LinkedList<SubTask> getTasks() {
        return subTasks;
    }

    static void removeSubTask(EpicTask epicTask) {
        LinkedList<SubTask> subTasksListByTask = getSubTasksListByTask(epicTask);
        epicTask.removeAllSubTasksFromList();
        subTasks.removeAll(subTasksListByTask);
    }

    static void removeSubTaskById() {
        SubTask selectedTask = selectUserSubTaskByID();
        selectedTask.getEpicTask().removeSubTaskFromList(selectedTask);
        subTasks.remove(selectedTask);
    }

    static SubTask selectUserSubTaskByID() {
        int id = Scan.selectId();
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

    static int getTaskIndex(SubTask subTask) {
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

    static void replaceSubTask(int index, SubTask subTask) {
        subTasks.set(index, subTask);
    }

}
