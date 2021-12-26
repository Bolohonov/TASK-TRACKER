package repository;

import service.Scan;
import tasks.SingleTask;

public class SingleTaskSaver {

    public static SingleTask createTask() {
        SingleTask singleTask;
        String[] userTask = Scan.saveLinesFromUser();
        singleTask = new SingleTask(userTask[0], userTask[1]);
        return singleTask;
    }
}
