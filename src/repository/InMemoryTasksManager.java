package repository;

import service.Print;
import service.Scan;
import tasks.EpicTask;
import tasks.SubTask;
import tasks.SingleTask;

import java.util.LinkedList;

public class InMemoryTasksManager<T extends SingleTask> implements TaskManager<T> {

    private static final SingleTaskRepository singleTaskRepository = new SingleTaskRepository();
    private static final EpicTaskRepository epicTaskRepository = new EpicTaskRepository();
    private static final SubTaskRepository subTaskRepository = new SubTaskRepository();

    static int id;
    T obj;

    public InMemoryTasksManager() {
    }

    @Override
    public void saveFromCommand() {
        int command = Scan.selectType();
        if (command == 1) {
            singleTaskRepository.createTask();
        } else if (command == 2) {
            epicTaskRepository.createTask();
        } else if (command == 3) {
            subTaskRepository.createSubTaskFromUserSelect();
        }
    }

    @Override
    public void saveSubTaskFromCommand() {
        subTaskRepository.createSubTaskFromUserSelect();
    }

    @Override
    public T returnObject(int id) {
        for (SingleTask singleTask : singleTaskRepository.getTasks()) {
            if (singleTask.getId() == id) {
                obj = (T) singleTask;
            }
        }
        for (EpicTask epicTask : epicTaskRepository.getTasks()) {
            if (epicTask.getId() == id) {
                obj = (T) epicTask;
            }
        }
        try {
            for (SubTask subtask : epicTaskRepository.getSubTasks()) {
                if (subtask.getId() == id) {
                    obj = (T) subtask;
                }
            }
        } catch (NullPointerException exp) {
        }
        return obj;
    }

    private static boolean checkIdNumber(int id) {
        boolean isIDAlreadyExist = false;
        for (SingleTask singleTask : SingleTaskRepository.getTasks()) {
            if (singleTask.getId() == id) {
                isIDAlreadyExist = true;
            }
        }
        for (EpicTask epicTask : EpicTaskRepository.getTasks()) {
            if (epicTask.getId() == id) {
                isIDAlreadyExist = true;
            }
        }
        try {
            for (SubTask subtask : EpicTaskRepository.getSubTasks()) {
                if (subtask.getId() == id) {
                    isIDAlreadyExist = true;
                }
            }
        } catch (NullPointerException exp) {
        }
        return isIDAlreadyExist;
    }

    public static int getId() {
        ++id;
        if ((!checkIdNumber(id)) && (id != 0)) {
            return id;
        } else {
            return getId();
        }
    }


    @Override
    public void updateTask(T task) {
        try {
            if (task.getClass().equals(SingleTask.class)) {
                TaskUpdater.updateTask(task);
            } else if (task.getClass().equals(EpicTask.class)) {
                TaskUpdater.updateEpicTask((EpicTask) task);
            } else if (task.getClass().equals(SubTask.class)) {
                TaskUpdater.updateSubTask((SubTask) task);
            }
        } catch (NullPointerException exp) {
            Print.printWrongValue();
        }
    }

    @Override
    public void printTask(T task) {
        try {
            System.out.println(task);
        } catch (NullPointerException exp) {
            System.out.println("Значение не найдено!");
        }
    }

    @Override
    public void printEpics() {
        LinkedList<EpicTask> list = epicTaskRepository.getTasks();
        if (list.isEmpty()) {
            System.out.println("Список пуст!");
        } else {
            list.forEach(System.out::println);
        }
    }

    @Override
    public void printTasks() {
        LinkedList<SingleTask> list = singleTaskRepository.getTasks();
        if (list.isEmpty()) {
            System.out.println("Список пуст!");
        } else {
            list.forEach(System.out::println);
        }
    }

    @Override
    public void printSubTasksFromUserSelect() {
        LinkedList<SubTask> list = null;
        try {
            list = subTaskRepository.getSubTasksListFromUserSelect();
        } catch (NullPointerException exp) {
            System.out.println("Список пуст!");
        }
        try {
            list.forEach(System.out::println);
        } catch (NullPointerException exp) {
            System.out.println("Список пуст!");
        }
    }

    @Override
    public void removeAllTasks() {
        try {
            singleTaskRepository.removeAllTasks();
        } catch (NullPointerException exp) {
            System.out.println("В списке не было задач");
        }
    }

    @Override
    public void removeEpicTask() {
        try {
            epicTaskRepository.removeTask();
        } catch (NullPointerException exp) {
            System.out.println("Неверный ввод!");
        }
    }

    @Override
    public void removeSubTaskById() {
        try {
            subTaskRepository.removeSubTaskById();
        } catch (NullPointerException exp) {
            System.out.println("Неверный ввод!");
        }
    }
}
