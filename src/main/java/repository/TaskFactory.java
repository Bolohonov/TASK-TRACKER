package repository;

import tasks.EpicTask;
import tasks.SingleTask;
import tasks.SubTask;
import tasks.Task;

import java.time.Duration;
import java.time.LocalDateTime;

public class TaskFactory implements TaskCreator {

    @Override
    public SingleTask createSingleTask(String[] userTask, Duration duration, LocalDateTime startTime) {
        return new SingleTask(userTask[0], userTask[1], InMemoryTasksManager.getId(), duration, startTime);
    }

    @Override
    public EpicTask createEpicTask(String[] userTask) {
        return new EpicTask(userTask[0], userTask[1], InMemoryTasksManager.getId());
    }

    @Override
    public SubTask createSubTask(Task epicTask, String[] userTask, Duration duration, LocalDateTime startTime) {
        if (epicTask != null && epicTask.getClass().equals(EpicTask.class)) {
            return new SubTask((EpicTask) epicTask, userTask[0], userTask[1],
                    InMemoryTasksManager.getId(), duration, startTime);
        } else {
            System.out.println("Эпик не найден!");
            return null;
        }
    }
}
