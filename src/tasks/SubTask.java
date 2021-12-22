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

    public int hashCode(String subTaskName, String subTaskDescription) {
        int hash = 19;
        if (name != null) {
            hash = name.hashCode();
        }
        if (description != null) {
            hash = hash + description.hashCode();
        }
        return hash;
    }

    public Task getTask() {
        return task;
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
