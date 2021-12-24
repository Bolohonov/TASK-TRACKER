package repository;

import tasks.SubTask;
import tasks.Task;

import java.util.LinkedList;

public class RepositoryTaskManager<T extends Task> {

    private LinkedList<T> tasks = new LinkedList<>();

    RepositoryTaskManager<T> repositoryTaskManager;

    T obj;

    public RepositoryTaskManager() {
    }

    public RepositoryTaskManager(T obj) {
        this.obj = obj;
    }

    public T returnObject(long id) {
        for (Task task : TaskRepository.getTasks()) {
            if (task.getId() == id) {
                obj = (T) task;
            }
        }
        for (SubTask subtask : SubTaskRepository.getSubTasksList()) {
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
            TaskRepository.setTaskStorage();
        } else if (obj.getClass().equals(SubTask.class)) {
            SubTaskRepository.setSubTaskFromUserSelect();
        }

    }

    public RepositoryTaskManager getRepositoryObj(T obj) {
        return repositoryTaskManager = new RepositoryTaskManager<T>();
    }

}
