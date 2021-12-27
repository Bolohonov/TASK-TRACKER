package tasks;

import repository.TaskManager;
import repository.TaskStatus;

import java.util.LinkedList;
import java.util.Objects;

public class EpicTask extends SingleTask{

    private String name;
    private String description;
    private int id;
    private TaskStatus status;
    private LinkedList<SubTask> subTasksList;

    public EpicTask(String name, String description) {
        this.name = name;
        this.description = description;
        this.id = calcAndCheckId();
        this.status = TaskStatus.NEW;
        this.subTasksList = getSubTasksList();
    }

    public LinkedList<SubTask> getSubTasksList() {
        return subTasksList;
    }

    public void setSubTaskToList(SubTask subTask) {
        subTasksList.add(subTask);
        if (getStatus().equals(TaskStatus.NEW)) {
            setStatus(TaskStatus.IN_PROGRESS);
        }
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

    public int calcAndCheckId() {
        int id = (int) (Math.random() * 17 + Math.random() * 137);
        TaskManager manager = new TaskManager();
        if (manager.returnObject(id) != null) {
            return calcAndCheckId();
        }
        return id;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus() {
        boolean allDone = true;
        if (subTasksList.isEmpty()) {
            this.status = TaskStatus.NEW;
        } else {
            for (SubTask st : subTasksList) {
                if (st.getStatus().equals(TaskStatus.NEW)
                        || st.getStatus().equals(TaskStatus.IN_PROGRESS)) {
                    allDone = false;
                }
            }
        }
        if (allDone)  {
            setStatus(TaskStatus.DONE);
        }
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }
}
