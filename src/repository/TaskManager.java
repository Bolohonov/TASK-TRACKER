package repository;

import tasks.EpicTask;
import tasks.SingleTask;
import tasks.SubTask;
import tasks.Task;

import java.util.LinkedList;

public interface TaskManager {

    void createTask(String[] userTask);

    void createEpicTask(String[] userTask);

    void createSubTask(Task task, String[] userTask);

    Task getTaskById(int id);

    LinkedList<SingleTask> getSingleTasks();

    LinkedList<EpicTask> getEpicTasks();

    LinkedList<SubTask> getSubTasksByEpic(Task task);

    LinkedList<Task> getHistory();

    void removeAllTasks();

    void removeTask(Task task);

}
