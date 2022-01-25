package repository;

import tasks.EpicTask;
import tasks.SubTask;
import tasks.SingleTask;
import tasks.Task;

import java.util.LinkedHashMap;
import java.util.Map;

public class InMemoryTasksManager implements TaskManager {

    private static final Repository<SingleTask> singleTaskRepository = new Repository<>();
    private static final Repository<EpicTask> epicTaskRepository = new Repository<>();

    private static int id;
    private Task obj;

    private static boolean checkIdNumber(int id) {
        boolean isIDAlreadyExist = false;
        if (singleTaskRepository.getTasksMap().containsKey(id)) {
            isIDAlreadyExist = true;
        }
        if (epicTaskRepository.getTasksMap().containsKey(id)) {
            isIDAlreadyExist = true;
        }
        if (getSubTaskOrNullById(id) != null) {
            isIDAlreadyExist = true;
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

    private static Task getSubTaskOrNullById(int id) {
        SubTask subTask = null;
        for (EpicTask epicTask : epicTaskRepository.getTasksMap().values()) {
            if (epicTask.getSubTasksMap().containsKey(id)) {
                subTask = epicTask.getSubTasksMap().get(id);
            }
        }
        return subTask;
    }

    @Override
    public void putTask(Task task) {
        if (task != null) {
            try {
                singleTaskRepository.putTaskToMap((SingleTask) task);
            } catch (ClassCastException exp) {
            }
            try {
                epicTaskRepository.putTaskToMap((EpicTask) task);
            } catch (ClassCastException exp) {
            }
            try {
                SubTask subTask = (SubTask) task;
                subTask.getEpicTask().setSubTaskToMap(subTask);
            } catch (ClassCastException exp) {
            }
        } else {
            System.out.println("Задача не создана!");
        }
    }

    @Override
    public Task getTaskById(int id) {
        obj = null;
        if (singleTaskRepository.getTasksMap().containsKey(id)) {
            obj = singleTaskRepository.getTasksMap().get(id);
        }
        if (epicTaskRepository.getTasksMap().containsKey(id)) {
            obj = epicTaskRepository.getTasksMap().get(id);
        }
        if (getSubTaskOrNullById(id) != null) {
            obj = getSubTaskOrNullById(id);
        }
        return obj;
    }

    @Override
    public Map<Integer, SingleTask> getSingleTasks() {
        return singleTaskRepository.getTasksMap();
    }

    @Override
    public Map<Integer, EpicTask> getEpicTasks() {
        return epicTaskRepository.getTasksMap();
    }

    @Override
    public Map<Integer, SubTask> getSubTasksByEpic(Task epicTask) {
        if (epicTask != null && epicTask.getClass().equals(EpicTask.class)) {
            EpicTask epic = (EpicTask) epicTask;
            return epic.getSubTasksMap();
        } else {
            System.out.println("Эпик не найден!");
            return new LinkedHashMap<>();
        }
    }

    @Override
    public boolean updateTask(Task task) {
        boolean isUpdate = false;
        if (task != null) {
            try {
                if (singleTaskRepository.getTasksMap().containsKey(task.getId())
                        && singleTaskRepository.getTasksMap().get(task.getId()).equals(task)) {
                    isUpdate = true;
                }
            } catch (ClassCastException exp) {
            }
            try {
                if (epicTaskRepository.getTasksMap().containsKey(task.getId())
                        && epicTaskRepository.getTasksMap().get(task.getId()).equals(task)) {
                    isUpdate = true;
                }
            } catch (ClassCastException exp) {
            }
            try {
                SubTask subTask = (SubTask) task;
                if (getSubTaskOrNullById(subTask.getId()).equals(task)) {
                    isUpdate = true;
                }
            } catch (ClassCastException exp) {
            }
        } else {
            System.out.println("Задача не обновлена!");
        }
        return isUpdate;
    }

    @Override
    public void removeAllTasks() {
        try {
            singleTaskRepository.removeAllTasks();
        } catch (NullPointerException exp) {
            System.out.println("В списке не было задач");
        }
        try {
            epicTaskRepository.removeAllTasks();
        } catch (NullPointerException exp) {
            System.out.println("В списке не было задач");
        }
    }

    @Override
    public void removeTaskById(int id) {
        if (singleTaskRepository.getTasksMap().containsKey(id)) {
            singleTaskRepository.getTasksMap().remove(id);
        }
        if (epicTaskRepository.getTasksMap().containsKey(id)) {
            epicTaskRepository.getTasksMap().remove(id);
        }
        if (getSubTaskOrNullById(id) != null) {
            SubTask subTask = (SubTask) getSubTaskOrNullById(id);
            subTask.getEpicTask().removeSubTaskFromMap(subTask);
        }
    }
}
