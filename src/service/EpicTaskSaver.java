package service;

import repository.TaskStatus;
import tasks.EpicTask;

public class EpicTaskSaver extends TaskFactory{


//    public static EpicTask saveEpicTask() {
//        EpicTaskSaver e = new EpicTaskSaver();
//
//    }

    public static EpicTask createTask() {
        EpicTask epicTask = null;
        String[] userTask = Scan.saveLinesFromUser();
        if (!userTask[0].equals(null)) {
            long id = 0;
            epicTask = new EpicTask(userTask[0], userTask[1], id,
                    TaskStatus.NEW);
            id = epicTask.calcAndCheckId();
            epicTask.setId(id);
        } else {
            System.out.println("Поле Название должно быть заполнено");
            return epicTask;
        }
        return epicTask;
    }
}
