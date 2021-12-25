package service;

import tasks.SingleTask;

public class TaskSaver {

    public static SingleTask createTask() {
        SingleTask singleTask;
        String[] userTask = Scan.saveLinesFromUser();
        singleTask = new SingleTask(userTask[0], userTask[1]);
        return singleTask;
    }
}
