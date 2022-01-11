package repository;

import tasks.Task;

public class TaskUpdater implements Updateable {

    @Override
    public Task updateName(Task task, String name) {
        if (task != null) {
            task.setName(name);
            return task;
        } else {
            System.out.println("Задача не найдена");
            return null;
        }
    }

    @Override
    public Task updateDescription(Task task, String description) {
        if (task != null) {
            task.setDescription(description);
            return task;
        } else {
            System.out.println("Задача не найдена");
            return null;
        }
    }

    @Override
    public Task updateStatus(Task task, TaskStatus status) {
        if (task != null) {
            task.setStatus(status);
            return task;
        } else {
            System.out.println("Задача не найдена");
            return null;
        }
    }
}
