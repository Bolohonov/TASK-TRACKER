package tasks;

import repository.TaskStatus;

import java.util.Objects;

public class SubTask extends Task {
    Task task;
    String name;
    String description;
    long id;
    TaskStatus status;

    public SubTask(Task task, String name, String description, long id, TaskStatus status) {
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SubTask subtask = (SubTask) o;
        return id == subtask.id &&
                Objects.equals(name, subtask.name) &&
                Objects.equals(description, subtask.description) &&
                Objects.equals(status, subtask.status) &&
                (task.equals(subtask.task));
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(name, description, id, status);
        result = 31 * result + task.hashCode();
        return result;
    }
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
