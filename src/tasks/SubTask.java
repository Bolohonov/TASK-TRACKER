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
