package tasks;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

public class SingleTask extends Task{

    public SingleTask(String name, String description, int id,
                      Optional<Duration> duration, Optional<LocalDateTime> startTime) {
        super(name, description, id, duration, startTime);
    }

    @Override
    public String toString() {
        return "Задача{" +
                "Имя='" + super.getName() + '\'' +
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
        SingleTask task = (SingleTask) o;
        return  super.getId() == task.getId() &&
                Objects.equals(super.getName(), task.getName()) &&
                Objects.equals(super.getDescription(), task.getDescription()) &&
                Objects.equals(super.getStatus(), task.getStatus()) &&
                Objects.equals(super.getDuration(), task.getDuration()) &&
                Objects.equals(super.getStartTime(), task.getStartTime());
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(super.getName(), super.getDescription(),
                super.getId(), super.getStatus(), super.getDuration(), super.getStartTime());
        result = 31 * result + super.getId();
        return result;
    }

    @Override
    public TaskType getType() {
        return TaskType.TASK;
    }
}
