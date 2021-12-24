package tasks;

import repository.TaskManager;
import repository.TaskStatus;

public abstract class Task {

    private String name;
    private String description;
    private long id;
    private TaskStatus status;

    public Task(String name, String description, long id, TaskStatus status) {
        this.name = name;
        this.description = description;
        this.id = id;
        this.status = status;
    }

    public Task() {
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
