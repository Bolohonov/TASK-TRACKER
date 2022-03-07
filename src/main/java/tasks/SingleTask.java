package tasks;

import repository.TaskStatus;

import java.util.Objects;

public class SingleTask extends Task{

    public SingleTask(String name, String description, int id) {
        super(name, description, id);
    }

    public SingleTask(int id, String name, TaskStatus status, String description) {
        super(id, name, status, description);
    }

    @Override
    public String toString() {
        return "Задача{" +
                "Имя='" + super.getName() + '\'' +
                ", Описание='" + super.getDescription() + '\'' +
                ", ID=" + super.getId() +
                ", Статус=" + super.getStatus() +
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
                Objects.equals(super.getStatus(), task.getStatus());
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(super.getName(), super.getDescription(),
                super.getId(), super.getStatus());
        result = 31 * result + super.getId();
        return result;
    }

    @Override
    public TaskType getType() {
        return TaskType.TASK;
    }
}