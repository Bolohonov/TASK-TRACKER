package repository;

import tasks.EpicTask;
import tasks.SingleTask;
import tasks.SubTask;
import tasks.Task;

import java.util.LinkedList;

public interface TaskManager {

    void addTask(Task task);

    Task getTaskById(int id);

    LinkedList<SingleTask> getSingleTasks();

    LinkedList<EpicTask> getEpicTasks();

    LinkedList<SubTask> getSubTasksByEpic(Task task);

    LinkedList<Task> getHistory();

    boolean updateTask(Task newTask);

    void removeAllTasks();

    void removeTask(Task task);
}
