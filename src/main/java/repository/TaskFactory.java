package main.java.repository;

import main.java.tasks.EpicTask;
import main.java.tasks.SingleTask;
import main.java.tasks.SubTask;
import main.java.tasks.Task;

public class TaskFactory implements TaskCreator {

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
            return new SubTask((EpicTask) epicTask, userTask[0], userTask[1],
                    InMemoryTasksManager.getId());
        } else {
            System.out.println("Эпик не найден!");
            return null;
        }
    }
}
