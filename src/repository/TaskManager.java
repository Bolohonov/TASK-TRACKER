package repository;

import tasks.EpicTask;
import tasks.SingleTask;
import tasks.SubTask;
import tasks.Task;

import java.util.LinkedList;

public interface TaskManager {

    /*Добавление новой задачи, эпика и подзадачи.
    Сам объект должен передаваться в качестве параметра.
    Поскольку объект создается в InMemoryTaskManager,
    то в следующие три метода передаются только те данные, которые вводит пользователь.
    Или необходимо вынести создание объекта в отдельный класс и сюда передавать готовый объект?*/
    void createTask(String[] userTask);

    void createEpicTask(String[] userTask);

    void createSubTask(Task task, String[] userTask);

    //Получение задачи любого типа по идентификатору.
    Task getTaskById(int id);

    //Получение списка всех задач.
    LinkedList<SingleTask> getSingleTasks();

    //Получение списка всех эпиков.
    LinkedList<EpicTask> getEpicTasks();

    //Получение списка всех подзадач определённого эпика.
    LinkedList<SubTask> getSubTasksByEpic(Task task);

    //Получение истории.
    LinkedList<Task> getHistory();

    //Удаление ранее добавленных задач — всех и по идентификатору.
    void removeAllTasks();

    void removeTask(Task task);

    //Обновление задач происходит сочетанием manager.getDefault().getTaskById(id)
    // и соответствующих set-ров и связано в большей степени с интерфейсом,
    // что отражено в методе CommandManager.updatePanel()
}
