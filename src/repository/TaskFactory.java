package repository;

import tasks.EpicTask;
import tasks.SingleTask;
import tasks.SubTask;
import tasks.Task;

public class TaskFactory implements Saveable{

    @Override
    public SingleTask createSingleTask(String[] userTask) {
        return new SingleTask(userTask[0], userTask[1], InMemoryTasksManager.getId());
    }

    @Override
    public EpicTask createEpicTask(String[] userTask) {
        return new EpicTask(userTask[0], userTask[1], InMemoryTasksManager.getId());
    }

    @Override
    public SubTask createSubTask(Task epicTask, String[] userTask) {
        if (epicTask != null && epicTask.getClass().equals(EpicTask.class)) {
            return new SubTask((EpicTask)epicTask, userTask[0], userTask[1],
                    InMemoryTasksManager.getId());
        } else {
            System.out.println("Эпик не найден!");
            return null;
        }
    }
}
