package tasks;

import repository.EpicStatus;
import repository.TaskStatus;

public class Task {

    private String name;
    private String description;
    private int id;
    private TaskStatus status;
    private EpicStatus epic;

    public Task(String name, String description, int id, TaskStatus status, EpicStatus epic) {
        this.name = name;
        this.description = description;
        this.id = id;
        this.status = status;
        this.epic = epic;
    }

    public Task() {
        this.name = null;
        this.description = null;
        this.id = 0;
        this.status = TaskStatus.NEW;
        this.epic = EpicStatus.NOT_EPIC;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        int hash = 17;
        int random = (int)(Math.random() * 131);
        if (name != null) {
            hash = name.hashCode();
        }
        if (description != null) {
            hash = hash + description.hashCode()+random;
        }
        return hash;
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

    public int getId() {
        return id;
    }

    public void setId(int hash) {
        this.id = hash;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public EpicStatus getEpic() {
        return epic;
    }

    public void setEpic(EpicStatus epic) {
        this.epic = epic;
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
}
