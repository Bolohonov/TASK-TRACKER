package repository;

import tasks.EpicTask;
import tasks.SingleTask;
import tasks.SubTask;
import tasks.Task;

import java.time.Duration;
import java.time.LocalDateTime;

public interface TaskCreator {

    SingleTask createSingleTask(String[] userTask,
                                Duration duration,
                                LocalDateTime startTime) throws IntersectionException;

    EpicTask createEpicTask(String[] userTask);

    SubTask createSubTask(Task epicTask, String[] userTask,
                          Duration duration, LocalDateTime startTime) throws IntersectionException;
}
