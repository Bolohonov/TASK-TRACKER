package repository;

import tasks.EpicTask;
import tasks.SingleTask;
import tasks.SubTask;
import tasks.Task;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface TaskManager {

    void putTask(Task task) throws IntersectionException, ManagerSaveException;

    Task getTaskById(int id) throws ManagerSaveException;

    Map<Integer, SingleTask> getSingleTasks();

    Map<Integer, EpicTask> getEpicTasks();

    Map<Integer, SubTask> getSubTasksByEpic(Task task) throws ManagerSaveException;

    boolean updateTask(Task newTask) throws ManagerSaveException;

    void removeAllTasks() throws ManagerSaveException;

    void removeTaskById(int id) throws ManagerSaveException;

    List<Task> getHistory();

    Set<Task> getPrioritizedTasks();
}
