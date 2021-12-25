package service;

import repository.TaskStatus;
import tasks.SingleTask;
import tasks.Task;

public class SingleTaskFactory {

    private static SingleTask singleTask;

    public SingleTask createTask() {
        if (!Scan.getLinesFromUser()[0].equals(null)) {
            singleTask = new SingleTask(Scan.getLinesFromUser()[0], Scan.getLinesFromUser()[1], 0,
                    TaskStatus.NEW);
            singleTask.setId(singleTask.calcAndCheckId());
        } else {
            System.out.println("Поле Название должно быть заполнено");
            return singleTask;
        }
        return singleTask;
    }


}
