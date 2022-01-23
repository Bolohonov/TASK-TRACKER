package repository;

import tasks.EpicTask;
import tasks.SingleTask;
import tasks.SubTask;
import tasks.Task;

import java.util.LinkedList;
import java.util.Map;

public interface TaskManager {

    void putTask(Task task);

    Task getTaskById(int id);

    Map<Integer, SingleTask> getSingleTasks();

    Map<Integer, EpicTask> getEpicTasks();

    Map<Integer, SubTask> getSubTasksByEpic(Task task);

    LinkedList<Task> getHistory();

    boolean updateTask(Task newTask);

    void removeAllTasks();

    void removeTask(Task task);
}
