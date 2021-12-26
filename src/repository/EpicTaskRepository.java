package repository;

import service.Scan;
import tasks.EpicTask;

import java.util.LinkedList;

public class EpicTaskRepository {

    private static LinkedList<EpicTask> epicTasks = new LinkedList<>();

    private static final SubTaskRepository subTaskRepository = new SubTaskRepository();

    static void createTask() {
        EpicTask epicTask;
        String[] userTask = Scan.saveLinesFromUser();
        epicTask = new EpicTask(userTask[0], userTask[1]);
        if (epicTask != null) {
            epicTasks.add(epicTask);
        }
        subTaskRepository.createSubTaskFromEpicTask(epicTask);
    }

    static LinkedList<EpicTask> getTasks() {
        return epicTasks;
    }

    static void removeTask() {
        EpicTask epicTask = selectUserTaskByID();
        if (epicTask != null) {
            subTaskRepository.removeSubTask(epicTask);
            if (subTaskRepository.getSubTasksListByTask(epicTask).isEmpty()) {
                EpicTaskRepository.epicTasks.remove(epicTask);
            }
        }
    }

    static void replaceTask(int index, EpicTask epicTask) {
        epicTasks.set(index, epicTask);
    }

    static int getTaskIndex(EpicTask epicTask) {
        int index = -1;
        if (epicTask != null) {
            for (EpicTask t : epicTasks) {
                if (t.equals(epicTask)) {
                    index = epicTasks.indexOf(t);
                }
            }
        }
        return index;
    }

    static EpicTask selectUserTaskByID() {
        int id = Scan.selectId();
        EpicTask epicTask = null;
        for (EpicTask epicTaskSelect : EpicTaskRepository.getTasks()) {
            if (epicTaskSelect.getId() == id) {
                epicTask = epicTaskSelect;
            }
        }
        if (epicTask == null) {
            System.out.println("Вы ввели неверный ID задачи");
        }
        return epicTask;
    }
}
