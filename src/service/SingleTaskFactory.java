package service;

import repository.TaskStatus;
import tasks.SingleTask;
import tasks.Task;

public class SingleTaskFactory extends TaskFactory{

    private static Task singleTask;

    @Override
    public Task createTask() {
        if (!Scan.getLinesFromUser()[0].equals(null)) {
            long id = 0;
            singleTask = new SingleTask(Scan.getLinesFromUser()[0], Scan.getLinesFromUser()[1], id,
                    TaskStatus.NEW);
            id = singleTask.calcAndCheckId();
            singleTask.setId(id);
        } else {
            System.out.println("Поле Название должно быть заполнено");
            return singleTask;
        }
        return singleTask;
    }
}
