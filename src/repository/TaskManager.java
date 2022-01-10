package repository;

import tasks.Task;

public interface TaskManager {

    void createTask(String[] userTask);

    void createEpicTask(String[] userTask);

    void createSubTask(Task task, String[] userTask);

    Task getTaskById(int id);

    void printTask(Task task);

    void printEpics();

    void printSingleTasks();

    void printSubTasksByEpic(Task task);

    void printHistory();

    void removeAllTasks();

    void removeTask(Task task);

    void history();

}
