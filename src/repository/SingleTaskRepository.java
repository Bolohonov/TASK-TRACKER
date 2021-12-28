package repository;

import service.Scan;
import tasks.SingleTask;

import java.util.LinkedList;

public class SingleTaskRepository {

    private static LinkedList<SingleTask> singleTasks = new LinkedList<>();

    static void createTask() {
        SingleTask singleTask;
        String[] userTask = Scan.saveLinesFromUser();
        singleTask = new SingleTask(userTask[0], userTask[1], TaskManager.getId());
        if (singleTask != null) {
            singleTasks.add(singleTask);
        }
    }

    static LinkedList<SingleTask> getTasks() {
        return singleTasks;
    }

    static int getTaskIndex(SingleTask singleTask) {
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

    static void removeAllTasks() {
        SingleTaskRepository.getTasks().clear();
    }

    static void replaceTask(int index, SingleTask singleTask) {
        singleTasks.set(index, singleTask);
    }
}
