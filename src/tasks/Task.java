package tasks;

import repository.EpicStatus;
import repository.RepositoryTaskManager;
import repository.TaskStatus;

import java.util.Objects;

public class Task {

    private String name;
    private String description;
    private long id;
    private TaskStatus status;

    public Task(String name, String description, long id, TaskStatus status, EpicStatus epic) {
        this.name = name;
        this.description = description;
        this.id = id;
        this.status = status;
    }

    public Task() {
        this.name = null;
        this.description = null;
        this.id = 0;
        this.status = TaskStatus.NEW;
    }

    public long calcAndCheckId() {
        long id = (long)(Math.random()*17+Math.random()*137);
        RepositoryTaskManager rep = new RepositoryTaskManager();
        if (rep.returnObject(id) == null) {
        } else {
            calcAndCheckId();
        }
        return id;
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
        Task task = (Task) o;
        return id == task.id &&
                Objects.equals(name, task.name) &&
                Objects.equals(description, task.description) &&
                Objects.equals(status, task.status);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(name, description, id, status);
        result = 31 * result;
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

}
