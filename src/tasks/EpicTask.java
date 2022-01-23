package tasks;

import repository.TaskStatus;

import java.util.*;

public class EpicTask extends Task {

    private TaskStatus status;
    private Map<Integer, SubTask> subTasksMap;

    public EpicTask(String name, String description, int id) {
        super(name, description, id);
        this.status = getStatus();
        subTasksMap = new LinkedHashMap<>();
    }

    public Map<Integer,SubTask> getSubTasksMap() {
        try {
            return subTasksMap;
        } catch (NullPointerException exp) {
            System.out.println("Подзадач пока нет!");
            return subTasksMap = new LinkedHashMap<>();
        }
    }

    public Map<Integer, SubTask> setSubTaskToList(SubTask subTask) {
        if (subTask != null) {
            subTasksMap.put(subTask.getId(), subTask);
            getStatus();
        }
        return subTasksMap;
    }

    public Map<Integer, SubTask> removeSubTaskFromMap(SubTask subTask) {
        if (subTask != null) {
            subTasksMap.remove(subTask);
            getStatus();
        }
        return subTasksMap;
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
        if (subTasksMap == null || subTasksMap.isEmpty()) {
            this.status = TaskStatus.NEW;
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
