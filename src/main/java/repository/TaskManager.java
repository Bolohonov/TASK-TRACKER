package main.java.repository;

import main.java.tasks.EpicTask;
import main.java.tasks.SingleTask;
import main.java.tasks.SubTask;
import main.java.tasks.Task;

import java.util.List;
import java.util.Map;

public interface TaskManager {

    void putTask(Task task);

    Task getTaskById(int id);

    Map<Integer, SingleTask> getSingleTasks();

    Map<Integer, EpicTask> getEpicTasks();

    Map<Integer, SubTask> getSubTasksByEpic(Task task);

    boolean updateTask(Task newTask);

    void removeAllTasks();

    void removeTaskById(int id);

    List<Task> getHistory();
}
