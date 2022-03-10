package tasks;

import repository.TaskStatus;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

public class SubTask extends Task {
    private EpicTask epicTask;

    public SubTask(EpicTask epicTask, String name, String description,
                   int id, Optional<Duration> duration, Optional<LocalDateTime> startTime) {
        super(name, description, id, duration, startTime);
        setEpicTask(epicTask);
        epicTask.addSubTask(this);
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
                ", Продолжительность=" + super.getDuration() +
                ", Время начала=" + super.getStartTime() +
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
                (epicTask.equals(subtask.epicTask)) &&
                Objects.equals(super.getDuration(), subtask.getDuration()) &&
                Objects.equals(super.getStartTime(), subtask.getStartTime());
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(super.getName(), super.getDescription(),
                super.getId(), super.getStatus(), super.getDuration(), super.getStartTime());
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
    public String toString(Task task) {
        return super.toString(task) + epicTask.getId();
    }
}
