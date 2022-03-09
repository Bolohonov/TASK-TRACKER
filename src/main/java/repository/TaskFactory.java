package repository;

import tasks.EpicTask;
import tasks.SingleTask;
import tasks.SubTask;
import tasks.Task;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;

public class TaskFactory implements TaskCreator {

    @Override
    public SingleTask createSingleTask(String[] userTask, Duration duration, LocalDateTime startTime) throws IntersectionException {
            if (InMemoryTasksManager.checkIntersection(duration, startTime)) {
                return new SingleTask(userTask[0], userTask[1], InMemoryTasksManager.getId(),
                        Optional.ofNullable(duration), Optional.ofNullable(startTime));
            } else {
                throw new IntersectionException("Временной интервал занят! Задача " + userTask[0] + " не создана");
            }
    }

    @Override
    public EpicTask createEpicTask(String[] userTask) {
        return new EpicTask(userTask[0], userTask[1], InMemoryTasksManager.getId());
    }

    @Override
    public SubTask createSubTask(Task epicTask, String[] userTask, Duration duration, LocalDateTime startTime) throws IntersectionException {
        if (InMemoryTasksManager.checkIntersection(duration, startTime)) {
            if (epicTask != null && epicTask.getClass().equals(EpicTask.class)) {
                return new SubTask((EpicTask) epicTask, userTask[0], userTask[1],
                        InMemoryTasksManager.getId(), Optional.ofNullable(duration), Optional.ofNullable(startTime));
            } else {
                System.out.println("Эпик не найден!");
                return null;
            }
        } else {
            throw new IntersectionException("Временной интервал занят! Задача " + userTask[0] + " не создана");
        }
    }
}
