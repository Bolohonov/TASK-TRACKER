package repository;

import tasks.Task;

public interface TaskUpdater {
    Task updateName(Task task, String name);

    Task updateDescription(Task task, String description);

    Task updateStatus(Task task, TaskStatus taskStatus);
}
