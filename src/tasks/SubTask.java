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

    public void setId(int id) {
        this.id = id;
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

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        int hash = 17;
        int random = (int)(Math.random() * 137);
        if (name != null) {
            hash = name.hashCode();
        }
        if (description != null) {
            hash = hash + description.hashCode()+random;
        }
        return hash;
    }
}
