package repository;

import tasks.EpicTask;
import tasks.SingleTask;
import tasks.SubTask;
import tasks.Task;

import java.util.List;

public interface TaskManager {

    void addTask(Task task);

    Task getTaskById(int id);

    List<SingleTask> getSingleTasks();

    List<EpicTask> getEpicTasks();

    List<SubTask> getSubTasksByEpic(Task task);

    List<Task> getHistory();

    boolean updateTask(Task newTask);

    void removeAllTasks();

    void removeTask(Task task);
}
