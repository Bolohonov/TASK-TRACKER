package tasks;

import repository.TaskStatus;

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

    public void setId(int hash) {
        this.id = hash;
    }

    @Override
    public String toString() {
        return "Подзадача{" +
                "Имя эпика=" + task.getName() +
                ", Имя подзадачи='" + name + '\'' +
                ", Описание='" + description + '\'' +
                ", ID=" + id +
                ", Статус=" + status +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        int hash = 37;
        if (name != null) {
            hash = name.hashCode();
        }
        if (description != null) {
            hash = hash + description.hashCode();
        }
        return hash;
    }
}
