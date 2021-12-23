package repository;

import tasks.SubTask;
import tasks.Task;

import java.util.LinkedList;

public class Repository<Object extends Task> {
    private LinkedList<Object> tasks = new LinkedList<>();
    Object obj;

    public Object returnObject(long id) {
        for (Task task : TaskStorage.getTasks()) {
            if (task.getId() == id) {
                obj = (Object) task;
            }
        }
        for (SubTask subtask : SubTaskStorage.getSubTasksList()) {
            if (subtask.getId() == id) {
                obj = (Object) subtask;
            }
        }
        return obj;
    }
}
