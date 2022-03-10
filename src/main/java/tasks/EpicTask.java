package tasks;


import repository.TaskStatus;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

public class EpicTask extends Task {

    private Map<Integer, SubTask> subTasksMap;

    public EpicTask(String name, String description, int id) {
        super(name, description, id);
        subTasksMap = new LinkedHashMap<>();
    }

    public Map<Integer, SubTask> getSubTasks() {
        try {
            return subTasksMap;
        } catch (NullPointerException exp) {
            System.out.println("Подзадач пока нет!");
            return new LinkedHashMap<>();
        }
    }

    public Map<Integer, SubTask> addSubTask(SubTask subTask) {
        if (subTask != null) {
            subTasksMap.put(subTask.getId(), subTask);
        }
        return subTasksMap;
    }

    public Map<Integer, SubTask> removeSubTask(SubTask subTask) {
        if (subTask != null) {
            subTasksMap.remove(subTask.getId());
        }
        return subTasksMap;
    }

    @Override
    public String toString() {
        return "Эпик{" +
                "Имя='" + super.getName() + '\'' +
                ", Описание='" + super.getDescription() + '\'' +
                ", ID=" + super.getId() +
                ", Статус=" + getStatus() +
                ", Продолжительность=" + super.getDuration() +
                ", Время начала=" + super.getStartTime() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EpicTask task = (EpicTask) o;
        return super.getId() == task.getId() &&
                Objects.equals(super.getName(), task.getName()) &&
                Objects.equals(super.getDescription(), task.getDescription()) &&
                Objects.equals(getStatus(), task.getStatus()) &&
                Objects.equals(getDuration(), task.getDuration()) &&
                Objects.equals(getStartTime(), task.getStartTime());
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(super.getName(), super.getDescription(),
                super.getId(), getStatus(), getDuration(), getStartTime());
        result = 37 * result + super.getId();
        return result;
    }

    @Override
    public TaskStatus getStatus() {
        boolean allDone = true;
        boolean inProgress = false;
        TaskStatus status = TaskStatus.NEW;
        if (subTasksMap == null || subTasksMap.isEmpty()) {
            status = TaskStatus.NEW;
        } else {
            for (SubTask st : subTasksMap.values()) {
                if (st.getStatus().equals(TaskStatus.NEW)
                        || st.getStatus().equals(TaskStatus.IN_PROGRESS)) {
                    allDone = false;
                }
                if (st.getStatus().equals(TaskStatus.DONE)
                        || st.getStatus().equals(TaskStatus.IN_PROGRESS)) {
                    inProgress = true;
                }
            }
        }
        if (allDone && (!subTasksMap.isEmpty())) {
            status = TaskStatus.DONE;
        }
        if (inProgress && !allDone) {
            status = TaskStatus.IN_PROGRESS;
        }
        return status;
    }

    @Override
    public void setStatus(TaskStatus status) {
        System.out.println("Статус не подлежит изменению");
    }

    @Override
    public TaskType getType() {
        return TaskType.EPIC;
    }

    @Override
    public Optional<Duration> getDuration() {
        if (subTasksMap == null || subTasksMap.isEmpty()) {
            return Optional.empty();
        } else {
            Duration duration = Duration.ofHours(0);
            for (SubTask sub : subTasksMap.values()) {
                if (sub.getDuration().isPresent()) {
                    duration.plus(sub.getDuration().get());
                }
            }
            return Optional.of(duration);
        }
    }

    @Override
    public Optional<LocalDateTime> getStartTime() {
        LocalDateTime startTime = null;
        if (subTasksMap != null && !subTasksMap.isEmpty()) {
            if (subTasksMap.entrySet().stream().findFirst()
                    .get().getValue().getStartTime().isPresent()) {
                startTime = subTasksMap.entrySet().stream().findFirst()
                        .get().getValue().getStartTime().get();
            }
        }
        if (startTime == null) {
            return Optional.empty();
        } else {
            return Optional.of(startTime);
        }
    }
}
