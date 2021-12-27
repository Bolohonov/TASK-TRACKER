package tasks;

import repository.TaskManager;
import repository.TaskStatus;

import java.util.Objects;

public class SubTask extends SingleTask {
    private EpicTask epicTask;
    private String name;
    private String description;
    private int id;
    private TaskStatus status;

    public SubTask(String name, String description) {
        this.epicTask = getEpicTask();
        this.name = name;
        this.description = description;
        this.id = calcAndCheckId();
        this.status = TaskStatus.NEW;
    }

    public EpicTask getEpicTask() {
        return epicTask;
    }

    public void setEpicTask(EpicTask epicTask) {
        this.epicTask = epicTask;
    }

    @Override
    public String toString() {
        return "Подзадача{" +
                "Имя эпика=" + epicTask.getName() +
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
                (epicTask.equals(subtask.epicTask));
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(name, description, id, status);
        result = 41 * result + epicTask.hashCode() + id;
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
        int id = (int) (Math.random() * 17 + Math.random() * 137);
        TaskManager manager = new TaskManager();
        if (manager.returnObject(id) != null) {
            return calcAndCheckId();
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
