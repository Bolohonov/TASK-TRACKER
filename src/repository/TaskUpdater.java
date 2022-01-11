package repository;

import tasks.Task;

public class TaskUpdater implements Updateable {

    @Override
    public Task updateName(Task task, String name) {
        if (task != null) {
            task.setName(name);
        } else {
            System.out.println("Задача не найдена");
        }
        return task;
    }

    @Override
    public Task updateDescription(Task task, String description) {
        if (task != null) {
            task.setDescription(description);
        } else {
            System.out.println("Задача не найдена");
        }
        return task;
    }

    @Override
    public Task updateStatus(Task task, TaskStatus status) {
        if (task != null) {
            task.setStatus(status);
        } else {
            System.out.println("Задача не найдена");
        }
        return task;
    }
}
