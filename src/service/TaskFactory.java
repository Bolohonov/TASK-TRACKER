package service;

import repository.TaskStatus;
import tasks.EpicTask;
import tasks.Task;

public abstract class TaskFactory {

    @Override
    public abstract Task createTask();
}
