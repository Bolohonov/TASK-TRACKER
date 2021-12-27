package tasks;

import repository.TaskIdentifier;
import repository.TaskStatus;

import java.util.Objects;

public class SingleTask {

    private String name;
    private String description;
    private int id;
    private TaskStatus status;

    public SingleTask(String name, String description) {
        this.name = name;
        this.description = description;
        this.id = calcAndCheckId();
        this.status = TaskStatus.NEW;
    }

    public SingleTask() {
    }

    @Override
    public String toString() {
        return "Задача{" +
                "Имя='" + name + '\'' +
                ", Описание='" + description + '\'' +
                ", ID=" + id +
                ", Статус=" + status +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SingleTask task = (SingleTask) o;
        return id == task.id &&
                Objects.equals(name, task.name) &&
                Objects.equals(description, task.description) &&
                Objects.equals(status, task.status);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(name, description, id, status);
        result = 31 * result + id;
        return result;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public int calcAndCheckId() {
        TaskIdentifier identifier = new TaskIdentifier();
        return identifier.getId();
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }
}
