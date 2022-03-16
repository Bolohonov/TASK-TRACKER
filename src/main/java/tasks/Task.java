package tasks;


import repository.TaskStatus;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

public abstract class Task {

    private String name;
    private String description;
    private int id;
    private TaskStatus status;
    private Optional<Duration> duration;
    private Optional<LocalDateTime> startTime;

    public Task(String name, String description, int id,
                Optional<Duration> duration, Optional<LocalDateTime> startTime) {
        this.name = name;
        this.description = description;
        this.id = id;
        this.status = TaskStatus.NEW;
        this.duration = duration;
        this.startTime = startTime;
    }

    public Task(String name, String description, int id) {
        this.name = name;
        this.description = description;
        this.id = id;
        this.status = TaskStatus.NEW;
        this.duration = getDuration();
        this.startTime = getStartTime();
    }

    @Override
    public String toString() {
        return "Задача{" +
                "Имя='" + name + '\'' +
                ", Описание='" + description + '\'' +
                ", ID=" + id +
                ", Статус=" + status +
                ", Продолжительность=" + duration +
                ", Время начала=" + startTime +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return id == task.id &&
                Objects.equals(name, task.name) &&
                Objects.equals(description, task.description) &&
                Objects.equals(status, task.status) &&
                (Objects.equals(getDuration(), task.getDuration())) &&
                Objects.equals(getStartTime(), task.getStartTime());
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(name, description, id, status, getDuration(), getStartTime());
        result = 31 * result + id;
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

    public String getDescription() {
        return description;
    }

    public int getId() {
        return id;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public abstract TaskType getType();

    public String toString(Task task) {
        String durationOrNull = null;
        String startTimeOrNull = null;
        if (getDuration().isPresent()) {
            durationOrNull = getDuration().get().toString();
        }
        if (getStartTime().isPresent()) {
            startTimeOrNull = getStartTime().get().toString();
        }
        return task.getId() + "," + task.getType() + "," + task.getName() + "," + task.getStatus()
                + "," + task.getDescription() + "," + durationOrNull + "," + startTimeOrNull + ",";
    }

    public Optional<Duration> getDuration() {
        if (!duration.isPresent()) {
            return Optional.empty();
        } else {
            return duration;
        }
    }

    public void setDuration(Duration duration) {
        this.duration = Optional.of(duration);
    }

    public Optional<LocalDateTime> getStartTime() {
        if (!startTime.isPresent()) {
            return Optional.empty();
        } else {
            return startTime;
        }
    }
}
