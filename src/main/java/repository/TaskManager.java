package repository;

import tasks.EpicTask;
import tasks.SingleTask;
import tasks.SubTask;
import tasks.Task;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface TaskManager {

    void putTask(Task task) throws IntersectionException;

    Task getTaskById(int id);

    Map<Integer, SingleTask> getSingleTasks();

    Map<Integer, EpicTask> getEpicTasks();

    Map<Integer, SubTask> getSubTasksByEpic(Task task);

    boolean updateTask(Task newTask);

    void removeAllTasks();

    void removeTaskById(int id);

    List<Task> getHistory();

    Set<Task> getPrioritizedTasks();
}
