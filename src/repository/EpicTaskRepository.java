package repository;

import service.Scan;
import tasks.EpicTask;
import tasks.SubTask;

import java.util.LinkedList;

public class EpicTaskRepository {

    private static LinkedList<EpicTask> epicTasks = new LinkedList<>();

    static void createTask() {
        EpicTask epicTask;
        String[] userTask = Scan.saveLinesFromUser();
        epicTask = new EpicTask(userTask[0], userTask[1]);
        if (epicTask != null) {
            epicTasks.add(epicTask);
        }
        SubTaskRepository.createSubTaskFromEpicTask(epicTask);
    }

    static LinkedList<EpicTask> getTasks() {
        return epicTasks;
    }

    static LinkedList<SubTask> getSubTasks() {
        LinkedList<SubTask> subTasks = new LinkedList<>();
        for (EpicTask epicTask : epicTasks) {
            if (!epicTask.getSubTasksList().isEmpty()) {
                subTasks.addAll(epicTask.getSubTasksList());
            }
        }
        return subTasks;
    }

    static void removeTask() {
        EpicTask epicTask = selectUserTaskByID();
        if (epicTask != null) {
            SubTaskRepository.removeSubTasks(epicTask);
            if (epicTask.getSubTasksList().isEmpty()) {
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
