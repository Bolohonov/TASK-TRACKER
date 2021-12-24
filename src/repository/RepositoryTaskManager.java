package repository;

import tasks.Task;
import tasks.EpicTask;
import tasks.SubTask;
import tasks.SingleTask;

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
        for (SingleTask singleTask : TaskRepository.getTasks()) {
            if (singleTask.getId() == id) {
                obj = (T) singleTask;
            }
        }
        for (EpicTask epicTask : EpicTaskRepository.getTasks()) {
            if (epicTask.getId() == id) {
                obj = (T) epicTask;
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
        if (obj.getClass().equals(SingleTask.class)) {
            TaskRepository.setTaskStorage();
        } else if (obj.getClass().equals(SubTask.class)) {
            SubTaskRepository.setSubTaskFromUserSelect();
        }

    }

    public RepositoryTaskManager getRepositoryObj(T obj) {
        return repositoryTaskManager = new RepositoryTaskManager<T>();
    }

}
