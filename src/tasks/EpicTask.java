package tasks;

import repository.TaskStatus;

import java.util.LinkedList;
import java.util.Objects;

public class EpicTask extends Task {

    private TaskStatus status;
    private LinkedList<SubTask> subTasksList;

    public EpicTask(String name, String description, int id) {
        super(name, description, id);
        this.status = getStatus();
        subTasksList = new LinkedList<>();
    }

    public LinkedList<SubTask> getSubTasksList() {
        try {
            return subTasksList;
        } catch (NullPointerException exp) {
            System.out.println("Список пока пуст!");
            return subTasksList = new LinkedList<>();
        }
    }

    public LinkedList<SubTask> setSubTaskToList(SubTask subTask) {
        if (subTask != null) {
            subTasksList.add(subTask);
            getStatus();
        }
        return subTasksList;
    }

    public LinkedList<SubTask> removeSubTaskFromList(SubTask subTask) {
        if (subTask != null) {
            subTasksList.remove(subTask);
            getStatus();
        }
        return subTasksList;
    }

    @Override
    public String toString() {
        return "Эпик{" +
                "Имя='" + super.getName() + '\'' +
                ", Описание='" + super.getDescription() + '\'' +
                ", ID=" + super.getId() +
                ", Статус=" + status +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EpicTask task = (EpicTask) o;
        return  super.getId() == task.getId() &&
                Objects.equals(super.getName(), task.getName()) &&
                Objects.equals(super.getDescription(), task.getDescription()) &&
                Objects.equals(status, task.status);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(super.getName(), super.getDescription(), super.getId(), status);
        result = 37 * result + super.getId();
        return result;
    }

    @Override
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

    @Override
    public void setStatus(TaskStatus status) {
        System.out.println("Статус не подлежит изменению");
    }
}
