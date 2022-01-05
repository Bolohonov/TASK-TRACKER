package repository;

import tasks.SingleTask;

public interface TaskManager<T extends SingleTask> {

    void saveFromCommand();

    void saveSubTaskFromCommand();

    T returnObject(int id);

    void updateTask(T task);

    void printTask(T task);

    void printEpics();

    void printTasks();

    void printSubTasksFromUserSelect();

    void removeAllTasks();

    void removeEpicTask();

    void removeSubTaskById();

}
