package repository;

import tasks.SubTask;
import tasks.Task;

import java.util.LinkedList;

public class Repository<T extends Task> {
    private LinkedList<T> tasks = new LinkedList<>();
    Repository<T> repository;
    T obj;

    public Repository() {
    }

    public Repository(T obj) {
        this.obj = obj;
    }

    public T returnObject(long id) {
        for (Task task : TaskStorage.getTasks()) {
            if (task.getId() == id) {
                obj = (T) task;
            }
        }
        for (SubTask subtask : SubTaskStorage.getSubTasksList()) {
            if (subtask.getId() == id) {
                obj = (T) subtask;
            }
        }
        return obj;
    }

    public void setObjectToRepository(T obj) {
        System.out.println(obj.getClass().toString());
    }

    public void setObjectToRepository() {
        if (obj.getClass().equals(Task.class)) {
            TaskStorage.setTaskStorage();
        } else if (obj.getClass().equals(SubTask.class)) {
            SubTaskStorage.setSubTaskFromUserSelect();
        }

    }

    public Repository getRepositoryObj(T obj) {
        return repository = new Repository<T>();
    }

}
