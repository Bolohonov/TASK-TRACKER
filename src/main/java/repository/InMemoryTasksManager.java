package repository;

import tasks.EpicTask;
import tasks.SingleTask;
import tasks.SubTask;
import tasks.Task;

import java.util.*;

public class InMemoryTasksManager implements TaskManager {

    private static final Repository<SingleTask> singleTaskRepository = new Repository<>();
    private static final Repository<EpicTask> epicTaskRepository = new Repository<>();
    protected static final HistoryManager historyManager = new InMemoryHistoryManager();
    protected static final Set<Task> prioritizedTasks = new TreeSet<>((o1, o2) -> {
        if (!o1.getStartTime().isPresent()) {
            return -1;
        } else if (!o2.getStartTime().isPresent()) {
            return 1;
        } else {
            return o1.getStartTime().get().compareTo(o2.getStartTime().get());
        }
    });

    private static int id;
    private Task obj;

    private static boolean checkIdNumber(int id) {
        boolean isIDAlreadyExist = false;
        if (singleTaskRepository.getTasks().containsKey(id)) {
            isIDAlreadyExist = true;
        }
        if (epicTaskRepository.getTasks().containsKey(id)) {
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
        for (EpicTask epicTask : epicTaskRepository.getTasks().values()) {
            if (epicTask.getSubTasks().containsKey(id)) {
                subTask = epicTask.getSubTasks().get(id);
            }
        }
        return subTask;
    }

    @Override
    public void putTask(Task task) {
        prioritizedTasks.add(task);
        if (task != null) {
            if (task instanceof SingleTask) {
                singleTaskRepository.putTask((SingleTask) task);
            }
            if (task instanceof EpicTask) {
                epicTaskRepository.putTask((EpicTask) task);
            }
//            if (task instanceof SubTask) {
//                SubTask subTask = (SubTask) task;
//                subTask.getEpicTask().addSubTask(subTask);
//            }
        } else {
            System.out.println("Задача не создана!");
        }
    }

    @Override
    public Task getTaskById(int id) {
        obj = null;
        if (singleTaskRepository.getTasks().containsKey(id)) {
            obj = singleTaskRepository.getTasks().get(id);
            historyManager.add(obj);
        }
        if (epicTaskRepository.getTasks().containsKey(id)) {
            obj = epicTaskRepository.getTasks().get(id);
            historyManager.add(obj);
        }
        if (getSubTaskOrNullById(id) != null) {
            obj = getSubTaskOrNullById(id);
            historyManager.add(obj);
        }
        return obj;
    }

    @Override
    public Map<Integer, SingleTask> getSingleTasks() {
        return singleTaskRepository.getTasks();
    }

    @Override
    public Map<Integer, EpicTask> getEpicTasks() {
        return epicTaskRepository.getTasks();
    }

    @Override
    public Map<Integer, SubTask> getSubTasksByEpic(Task epicTask) {
        if (epicTask != null && (epicTask instanceof EpicTask)) {
            EpicTask epic = (EpicTask) epicTask;
            return epic.getSubTasks();
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
                if (singleTaskRepository.getTasks().containsKey(task.getId())
                        && singleTaskRepository.getTasks().get(task.getId()).equals(task)) {
                    isUpdate = true;
                    historyManager.add(task);
                }
            } catch (ClassCastException exp) {
            }
            try {
                if (epicTaskRepository.getTasks().containsKey(task.getId())
                        && epicTaskRepository.getTasks().get(task.getId()).equals(task)) {
                    isUpdate = true;
                    historyManager.add(task);
                }
            } catch (ClassCastException exp) {
            }
            try {
                SubTask subTask = (SubTask) task;
                if (getSubTaskOrNullById(subTask.getId()).equals(task)) {
                    isUpdate = true;
                    historyManager.add(task);
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
        historyManager.clearHistory();
    }

    @Override
    public void removeTaskById(int id) {
        if (singleTaskRepository.getTasks().containsKey(id)) {
            historyManager.remove(id);
            singleTaskRepository.getTasks().remove(id);
        }
        if (epicTaskRepository.getTasks().containsKey(id)) {
            EpicTask epic = epicTaskRepository.getTasks().get(id);
            Map<Integer, SubTask> subTaskMap = epic.getSubTasks();
            for (Integer subTaskId : subTaskMap.keySet()) {
                historyManager.remove(subTaskId);
            }
            historyManager.remove(id);
            epicTaskRepository.getTasks().remove(id);
        }
        if (getSubTaskOrNullById(id) != null) {
            historyManager.remove(id);
            SubTask subTask = (SubTask) getSubTaskOrNullById(id);
            subTask.getEpicTask().removeSubTask(subTask);
        }
    }

    @Override
    public List<Task> getHistory() {
        return historyManager.getHistory();
    }

    @Override
    public Set<Task> getPrioritizedTasks() {
        return prioritizedTasks;
    }
}
