package tasks;

import repository.TaskStatus;

import java.util.Objects;

public class SubTask extends Task {
    private EpicTask epicTask;

    public SubTask(EpicTask epicTask, String name, String description, int id) {
        super(name, description, id);
        setEpicTask(epicTask);
    }

    public void setEpicTask(EpicTask epicTask) {
        this.epicTask = epicTask;
    }

    public EpicTask getEpicTask() {
        return epicTask;
    }

    @Override
    public String toString() {
        return "Подзадача{" +
                "Имя эпика=" + epicTask.getName() +
                ", Имя подзадачи='" + super.getName() + '\'' +
                ", Описание='" + super.getDescription() + '\'' +
                ", ID=" + super.getId() +
                ", Статус=" + super.getStatus() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SubTask subtask = (SubTask) o;
        return super.getId() == subtask.getId() &&
                Objects.equals(super.getName(), subtask.getName()) &&
                Objects.equals(super.getDescription(), subtask.getDescription()) &&
                Objects.equals(super.getStatus(), subtask.getStatus()) &&
                (epicTask.equals(subtask.epicTask));
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(super.getName(), super.getDescription(),
                super.getId(), super.getStatus());
        result = 41 * result + epicTask.hashCode() + super.getId();
        return result;
    }

    @Override
    public void setStatus(TaskStatus status) {
        super.setStatus(status);
    }

    @Override
    public TaskType getType() {
        return TaskType.SUBTASK;
    }

    @Override
    public String toStringToFile() {
        return super.getId() + "," + getType() + "," + super.getName()
                + "," + super.getStatus() + "," + super.getDescription();
    }
}
