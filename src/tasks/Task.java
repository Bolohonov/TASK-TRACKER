package tasks;

import repository.TaskStatus;

import java.util.Objects;

public abstract class Task {

    private String name;
    private String description;
    private int id;
    private TaskStatus status;

    public Task(String name, String description, int id) {
        this.name = name;
        this.description = description;
        this.id = id;
        this.status = TaskStatus.NEW;
    }

    public Task(int id, String name, TaskStatus status, String description) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.description = description;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return id == task.id &&
                Objects.equals(name, task.name) &&
                Objects.equals(description, task.description) &&
                Objects.equals(status, task.status);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(name, description, id, status);
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

    //Данный метод перегружается в классе, также он переопределяется в наследнике SubTask.
    //В классе FileBackedTasksManager как я понял по ТЗ должен быть метод toString()
    // с сигнатурой String toString(HistoryManager manager)
    public String toString(Task task) {
        return task.getId() + "," + task.getType() + "," + task.getName() + "," + task.getStatus()
                + "," + task.getDescription() + ",";
    }
}
