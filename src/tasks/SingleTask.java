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
        long id = (long)(Math.random()*17+Math.random()*137);
        TaskManager rep = new TaskManager();
        if (rep.returnObject(id) == null) {
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
