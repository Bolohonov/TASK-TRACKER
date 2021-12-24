package tasks;

import repository.TaskManager;
import repository.TaskStatus;

import java.util.Objects;

public class SingleTask extends Task {

    private String name;
    private String description;
    private long id;
    private TaskStatus status;

    public SingleTask(String name, String description, long id, TaskStatus status) {
        super(name, description, id, status);
    }

    public SingleTask() {
        this.name = null;
        this.description = null;
        this.id = 0;
        this.status = TaskStatus.NEW;
    }

    public long calcAndCheckId() {
        long id = (long)(Math.random()*17+Math.random()*137);
        TaskManager rep = new TaskManager();
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
        SingleTask singleTask = (SingleTask) o;
        return id == singleTask.id &&
                Objects.equals(name, singleTask.name) &&
                Objects.equals(description, singleTask.description) &&
                Objects.equals(status, singleTask.status);
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

    public String getDescription() {
        return name;
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
