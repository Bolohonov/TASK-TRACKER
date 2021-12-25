package tasks;

import repository.TaskManager;
import repository.TaskStatus;

import java.util.Objects;

public class EpicTask extends Task {

    private String name;
    private String description;
    private long id;
    private TaskStatus status;

    public EpicTask(String name, String description, long id, TaskStatus status) {
        this.name = name;
        this.description = description;
        this.id = id;
        this.status = status;
    }

    public EpicTask() {
        this.name = null;
        this.description = null;
        this.id = 0;
    }

    @Override
    public String toString() {
        return "Эпик{" +
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
        EpicTask task = (EpicTask) o;
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
}
