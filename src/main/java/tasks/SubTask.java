package tasks;

import repository.TaskStatus;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

public class SubTask extends Task {

    private int epicId;

    public SubTask(int epicId, String name, String description,
                   int id, Optional<Duration> duration, Optional<LocalDateTime> startTime) {
        super(name, description, id, duration, startTime);
        setEpicId(epicId);
    }

    public void setEpicId(int epicId) {
        this.epicId = epicId;
    }

    public int getEpicId() {
        return epicId;
    }

    @Override
    public String toString() {
        return "Подзадача{" +
                "ID эпика=" + epicId +
                ", Имя подзадачи='" + super.getName() + '\'' +
                ", Описание='" + super.getDescription() + '\'' +
                ", ID=" + super.getId() +
                ", Статус=" + super.getStatus() +
                ", Продолжительность=" + super.getDuration().get() +
                ", Время начала=" + super.getStartTime().get() +
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
                (epicId == subtask.getEpicId()) &&
                Objects.equals(super.getDuration(), subtask.getDuration()) &&
                Objects.equals(super.getStartTime(), subtask.getStartTime());
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(super.getName(), super.getDescription(),
                super.getId(), super.getStatus(), super.getDuration(), super.getStartTime());
        result = 41 * result + epicId + super.getId();
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
        return super.toString(task) + epicId;
    }
}
