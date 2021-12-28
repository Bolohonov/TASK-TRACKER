package repository;

import service.Print;
import service.Scan;
import tasks.EpicTask;
import tasks.SubTask;

import java.util.LinkedList;

public class SubTaskRepository {

    static void createSubTask(EpicTask epicTask) {
        SubTask subTask;
        String[] userTask = Scan.saveLinesFromUser();
        subTask = new SubTask(epicTask, userTask[0], userTask[1], TaskManager.getId());
        try {
            epicTask.setSubTaskToList(subTask);
        } catch (NullPointerException exp) {
            System.out.println("Подзадача не найдена!");
        }
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
        if (epicTask.getSubTasksList().isEmpty()) {
            System.out.println("Список пуст!");
        }
        return epicTask.getSubTasksList();
    }

    static void removeSubTasks(EpicTask epicTask) {
        epicTask.removeAllSubTasksFromList();
    }

    static void removeSubTaskById() {
        SubTask selectedSubTask = selectUserSubTaskByID();
        EpicTask epicTask = selectedSubTask.getEpicTask();
        epicTask.removeSubTaskFromList(selectedSubTask);
    }

    static SubTask selectUserSubTaskByID() {
        int id = Scan.selectId();
        SubTask subTask = null;
        for (SubTask taskSelect : EpicTaskRepository.getSubTasks()) {
            if (taskSelect.getId() == id) {
                subTask = taskSelect;
            }
        }
        if (subTask == null) {
            System.out.println("Вы ввели неверный ID задачи");
        }
        return subTask;
    }

    static void replaceSubTask(SubTask subTask) {
        subTask.getEpicTask().setSubTaskToList(subTask);
    }

}
