package repository;

import service.Scan;
import tasks.EpicTask;

public class EpicTaskSaver {

    public static EpicTask createTask() {
        EpicTask epicTask;
        String[] userTask = Scan.saveLinesFromUser();
        epicTask = new EpicTask(userTask[0], userTask[1]);
        SubTaskSaver.createSubTaskFromEpicTask(epicTask);
        return epicTask;
    }
}
