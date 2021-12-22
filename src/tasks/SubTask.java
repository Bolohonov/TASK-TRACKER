package tasks;

import storage.TaskStatus;

public class SubTask extends Task {
        Task task;
        String name;
        String description;
        int id;
        TaskStatus status;

    public SubTask(Task task, String name, String description, int id, TaskStatus status) {
        this.task = task;
        this.name = name;
        this.description = description;
        this.id = id;
        this.status = status;
    }

    public Task getTask() {
        return task;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "tasks.SubTask{" +
                "task=" + task.getName() +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", id=" + id +
                ", status=" + status +
                '}';
    }
}
