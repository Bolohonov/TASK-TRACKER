package repository;

import service.TaskSaver;
import tasks.SingleTask;

import java.util.LinkedList;

public class SingleTaskRepository {

    private static LinkedList<SingleTask> singleTasks = new LinkedList<>();

    public static void setTaskStorage() {
        TaskSaver taskSaver = new TaskSaver();
        SingleTask singleTask = (SingleTask) taskSaver.createTask();
        if (singleTask != null) {
            singleTasks.add(singleTask);
        }
    }

    public static LinkedList<SingleTask> getTasks() {
        return singleTasks;
    }

    public static int getTaskIndex(SingleTask singleTask) {
        int index = -1;
        if (singleTask != null) {
            for (SingleTask t : singleTasks) {
                if (t.equals(singleTask)) {
                    index = singleTasks.indexOf(t);
                }
            }
        }
        return index;
    }

    public static void removeAllTasks() {
        SingleTaskRepository.getTasks().clear();
    }

    public static void replaceTask(int index, SingleTask singleTask) {
        singleTasks.set(index, singleTask);
    }
}
