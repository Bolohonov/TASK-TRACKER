package repository;

import tasks.EpicTask;
import tasks.SubTask;
import tasks.SingleTask;
import tasks.Task;

import java.util.LinkedList;

public class InMemoryTasksManager implements TaskManager {

    private static final Repository<SingleTask> singleTaskRepository = new Repository<>();
    private static final Repository<EpicTask> epicTaskRepository = new Repository<>();

    private static int id;
    private Task obj;

    private final LinkedList<Task> history = new LinkedList<>();

    private static boolean checkIdNumber(int id) {
        boolean isIDAlreadyExist = false;
        for (SingleTask singleTask : singleTaskRepository.getTasks()) {
            if (singleTask.getId() == id) {
                isIDAlreadyExist = true;
            }
        }
        for (EpicTask epicTask : epicTaskRepository.getTasks()) {
            if (epicTask.getId() == id) {
                isIDAlreadyExist = true;
            }
        }
        for (EpicTask epicTask : epicTaskRepository.getTasks()) {
            for (SubTask subtask : epicTask.getSubTasksList()) {
                if (subtask.getId() == id) {
                    isIDAlreadyExist = true;
                }
            }
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
    public void createTask(String[] userTask) {
        SingleTask singleTask;
        singleTask = new SingleTask(userTask[0], userTask[1], getId());
        if (singleTask != null) {
            singleTaskRepository.addTask(singleTask);
        }
    }

    @Override
    public void createEpicTask(String[] userTask) {
        EpicTask epicTask;
        epicTask = new EpicTask(userTask[0], userTask[1], getId());
        if (epicTask != null) {
            epicTaskRepository.addTask(epicTask);
        }
    }

    @Override
    public void createSubTask(Task epicTask, String[] userTask) {
        SubTask subTask;
        if (epicTask != null && epicTask.getClass().equals(EpicTask.class)) {
            subTask = new SubTask((EpicTask)epicTask, userTask[0], userTask[1], getId());
            ((EpicTask) epicTask).setSubTaskToList(subTask);
        } else {
            System.out.println("Эпик не найден!");
        }
    }

    @Override
    public Task getTaskById(int id) {
        obj = null;
        for (SingleTask singleTask : singleTaskRepository.getTasks()) {
            if (singleTask.getId() == id) {
                obj = singleTask;
                history();
                history.add(obj);
            }
        }
        for (EpicTask epicTask : epicTaskRepository.getTasks()) {
            if (epicTask.getId() == id) {
                obj = epicTask;
                history();
                history.add(obj);
            }
        }
        for (EpicTask epicTask : epicTaskRepository.getTasks()) {
            for (SubTask subtask : epicTask.getSubTasksList()) {
                if (subtask.getId() == id) {
                    obj = subtask;
                    history();
                    history.add(obj);
                }
            }
        }
        return obj;
    }

    @Override
    public LinkedList<SingleTask> getSingleTasks() {
        return singleTaskRepository.getTasks();
    }

    @Override
    public LinkedList<EpicTask> getEpicTasks() {
        return epicTaskRepository.getTasks();
    }

    @Override
    public LinkedList<SubTask> getSubTasksByEpic(Task epictask) {
        LinkedList<SubTask> subTasks = new LinkedList<>();
        if (epictask != null && epictask.getClass().equals(EpicTask.class)) {
            EpicTask epic = (EpicTask)epictask;
            subTasks = epic.getSubTasksList();
        } else {
            System.out.println("Эпик не найден!");
        }
        return subTasks;
    }

    @Override
    public LinkedList<Task> getHistory() {
        if (history == null || history.isEmpty()) {
            System.out.println("Истории пока нет");
            return null;
        } else {
            return history;
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
    public void removeTask(Task task) {
        try {
            singleTaskRepository.removeTask((SingleTask) task);
        } catch (ClassCastException exp) {
        }
        try {
            epicTaskRepository.removeTask((EpicTask) task);
        } catch (ClassCastException exp) {
        }
        try {
            for (EpicTask epicTask : epicTaskRepository.getTasks()) {
                for (int i = 0; i < epicTask.getSubTasksList().size(); i++) {
                    if (task.equals(epicTask.getSubTasksList().get(i))) {
                        epicTask.removeSubTaskFromList((SubTask) task);
                    }
                }
            }
        } catch (ClassCastException exp) {
        }
    }

    public void history() {
        if (history != null && !history.isEmpty() && history.size() > 9) {
            history.removeFirst();
        }
    }
}
