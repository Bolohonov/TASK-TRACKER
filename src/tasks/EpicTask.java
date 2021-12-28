package tasks;

import repository.TaskStatus;

import java.util.LinkedList;
import java.util.Objects;

public class EpicTask extends SingleTask {

    private String name;
    private String description;
    private int id;
    private TaskStatus status;
    private LinkedList<SubTask> subTasksList;

    public EpicTask(String name, String description, int id) {
        this.name = name;
        this.description = description;
        this.id = id;
        this.status = getStatus();
        subTasksList = new LinkedList<>();
    }

    public LinkedList<SubTask> getSubTasksList() {
        try {
            return subTasksList;
        } catch (NullPointerException exp) {
            return subTasksList = new LinkedList<>();
        }
    }

    public LinkedList<SubTask> setSubTaskToList(SubTask subTask) {
        if (subTask != null) {
            subTasksList.add(subTask);
        }
        return subTasksList;
    }

    public LinkedList<SubTask> removeSubTaskFromList(SubTask subTask) {
        if (subTask != null) {
            subTasksList.remove(subTask);
        }
        return subTasksList;
    }

    public void removeAllSubTasksFromList() {
        subTasksList.clear();
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
        result = 37 * result + id;
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

    public TaskStatus getStatus() {
        boolean allDone = true;
        boolean inProgress = false;
        if (subTasksList == null || subTasksList.isEmpty()) {
            this.status = TaskStatus.NEW;
        } else {
            for (SubTask st : subTasksList) {
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
        if (allDone && (this.status != TaskStatus.NEW)) {
            this.status = TaskStatus.DONE;
        }
        if (inProgress && !allDone) {
            this.status = TaskStatus.IN_PROGRESS;
        }
        return status;
    }
}
