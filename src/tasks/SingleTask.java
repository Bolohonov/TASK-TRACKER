package tasks;

import repository.TaskManager;
import repository.TaskStatus;

import java.util.Objects;

public class SingleTask {

    private String name;
    private String description;
    private long id;
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
        return description;
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

    public long calcAndCheckId() {
        long id = (long) (Math.random() * 17 + Math.random() * 137);
        TaskManager manager = new TaskManager();
        if (manager.returnObject(id) == null) {
        } else {
            calcAndCheckId();
        }
        return id;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }
}
