package repository;

import tasks.EpicTask;
import tasks.SingleTask;
import tasks.SubTask;
import tasks.Task;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

public class InMemoryTasksManager implements TaskManager {

    private static final Repository<SingleTask> singleTaskRepository = new Repository<>();
    private static final Repository<EpicTask> epicTaskRepository = new Repository<>();
    protected static final HistoryManager historyManager = new InMemoryHistoryManager();

    private static Set<Task> prioritizedTasks =
            new TreeSet<>(Comparator.<Task, LocalDateTime>comparing(
                            t -> t.getStartTime().orElse(null),
                            Comparator.nullsLast(Comparator.naturalOrder())
                    )
                    .thenComparingInt(Task::getId));

    private static int id;

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

    private static boolean checkIntersection(Task taskToCheck, Duration duration,
                                             LocalDateTime startTime) {
        boolean flag = true;
        if (duration != null && startTime != null) {
            LocalDateTime finishTime = startTime.plus(duration);
            for (Task task : prioritizedTasks) {
                if (taskToCheck instanceof SubTask && task instanceof EpicTask) {
                    if ((((SubTask) taskToCheck).getEpicId() != task.getId())
                            && task.getStartTime().isPresent() && task.getDuration().isPresent()) {
                        LocalDateTime taskStart = task.getStartTime().get();
                        LocalDateTime taskFinish = taskStart.plus(task.getDuration().get());
                        if (!checkIntersectionTimeSegment(startTime,
                                finishTime, taskStart, taskFinish)) {
                            flag = false;
                        }
                    }
                } else {
                    if (task.getStartTime().isPresent() && task.getDuration().isPresent()) {
                        LocalDateTime taskStart = task.getStartTime().get();
                        LocalDateTime taskFinish = taskStart.plus(task.getDuration().get());
                        if (!checkIntersectionTimeSegment(startTime,
                                finishTime, taskStart, taskFinish)) {
                            flag = false;
                        }
                    }
                }
            }
        }
        return flag;
    }

    private static boolean checkIntersectionTimeSegment(LocalDateTime startTime,
                                                        LocalDateTime finishTime,
                                                        LocalDateTime taskStart,
                                                        LocalDateTime taskFinish) {
        boolean flag = true;
        if (startTime.isEqual(taskStart) || finishTime.isEqual(taskFinish)) {
            flag = false;
        }
        if (startTime.isAfter(taskStart)
                && startTime.isBefore(taskFinish)) {
            flag = false;
        }
        if (startTime.isBefore(taskStart)
                && (finishTime.isAfter(taskStart)
                && finishTime.isBefore(taskFinish))) {
            flag = false;
        }
        if (startTime.isAfter(taskStart)
                && finishTime.isBefore(taskFinish)) {
            flag = false;
        }
        if (startTime.isAfter(taskStart) && startTime.isBefore(taskFinish)) {
            flag = false;
        }
        if (startTime.isBefore(taskStart) && finishTime.isAfter(taskFinish)) {
            flag = false;
        }
        return flag;
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
    public void putTask(Task task) throws IntersectionException, ManagerSaveException {
        if (task.getDuration().isPresent() && task.getStartTime().isPresent()
                && checkIntersection(task, task.getDuration().get(),
                task.getStartTime().get())) {
            prioritizedTasks.add(task);
            if (task instanceof SingleTask) {
                singleTaskRepository.putTask((SingleTask) task);
            }
            if (task instanceof EpicTask) {
                epicTaskRepository.putTask((EpicTask) task);
            }
            if (task instanceof SubTask) {
                epicTaskRepository.getTasks()
                        .get(((SubTask) task).getEpicId()).addSubTask((SubTask)task);
            }
        } else {
            if (!task.getDuration().isPresent()
                    || !task.getStartTime().isPresent()) {
                prioritizedTasks.add(task);
                if (task instanceof SingleTask) {
                    singleTaskRepository.putTask((SingleTask) task);
                }
                if (task instanceof EpicTask) {
                    epicTaskRepository.putTask((EpicTask) task);
                }
            } else if (!checkIntersection(task, task.getDuration().get(),
                    task.getStartTime().get())) {
                throw new IntersectionException("Временной интервал занят! Задача с ID "
                        + task.getId() + " не сохранена");
            } else {
                System.out.println("Задача не создана!");
            }
        }
    }

    @Override
    public Task getTaskById(int id) throws ManagerSaveException {
        Task task = null;
        if (singleTaskRepository.getTasks().containsKey(id)) {
            task = singleTaskRepository.getTasks().get(id);
            historyManager.add(task);
        }
        if (epicTaskRepository.getTasks().containsKey(id)) {
            task = epicTaskRepository.getTasks().get(id);
            historyManager.add(task);
        }
        if (getSubTaskOrNullById(id) != null) {
            task = getSubTaskOrNullById(id);
            historyManager.add(task);
        }
        return task;
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
    public boolean updateTask(Task task) throws ManagerSaveException {
        boolean isUpdate = false;
        if (task != null) {
            try {
                if (singleTaskRepository.getTasks().containsKey(task.getId())) {
                    singleTaskRepository.putTask((SingleTask) task);
                    isUpdate = true;
                    prioritizedTasks.add(task);
                    historyManager.add(task);
                }
            } catch (ClassCastException exp) {
            }
            try {
                if (epicTaskRepository.getTasks().containsKey(task.getId())) {
                    epicTaskRepository.putTask((EpicTask) task);
                    isUpdate = true;
                    prioritizedTasks.add(task);
                    historyManager.add(task);
                }
            } catch (ClassCastException exp) {
            }
            try {
                EpicTask epic = epicTaskRepository.getTasks().get(((SubTask) task)
                        .getEpicId());
                if (epic.getSubTasks().containsKey(task.getId())) {
                    epic.addSubTask((SubTask) task);
                    isUpdate = true;
                    prioritizedTasks.add(task);
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
    public void removeAllTasks() throws ManagerSaveException {
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
        prioritizedTasks.clear();
        historyManager.clearHistory();
    }

    @Override
    public void removeTaskById(int id) throws ManagerSaveException {
        if (singleTaskRepository.getTasks().containsKey(id)) {
            try {
                historyManager.remove(id);
                prioritizedTasks.remove(singleTaskRepository.getTasks().get(id));
            } catch (NoSuchElementException e) {
                System.out.println(e.getMessage());
            }
            singleTaskRepository.getTasks().remove(id);
        }
        if (epicTaskRepository.getTasks().containsKey(id)) {
            EpicTask epic = epicTaskRepository.getTasks().get(id);
            Map<Integer, SubTask> subTaskMap = epic.getSubTasks();
            for (Integer subTaskId : subTaskMap.keySet()) {
                try {
                    historyManager.remove(subTaskId);
                    prioritizedTasks.remove(epic.getSubTasks().get(subTaskId));
                } catch (NoSuchElementException e) {
                    System.out.println(e.getMessage());
                }
            }
            historyManager.remove(id);
            prioritizedTasks.remove(epicTaskRepository.getTasks().get(id));
            epicTaskRepository.getTasks().remove(id);
        }
        if (getSubTaskOrNullById(id) != null) {
            try {
                historyManager.remove(id);
            } catch (NoSuchElementException e) {
                System.out.println(e.getMessage());
            }
            SubTask subTask = (SubTask) getSubTaskOrNullById(id);
            prioritizedTasks.remove(subTask);
            epicTaskRepository.getTasks().get(subTask.getEpicId()).removeSubTask(subTask);
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
