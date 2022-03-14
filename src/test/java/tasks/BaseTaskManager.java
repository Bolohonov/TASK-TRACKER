package tasks;

import repository.IntersectionException;
import repository.Managers;
import repository.TaskCreator;
import repository.TaskManager;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class BaseTaskManager {

    protected static final Managers managers = new Managers();
    protected static final TaskCreator creator = managers.getFactory();
    protected static final TaskManager manager = managers.getTaskManager();

    protected EpicTask createEpicTask() {
        return creator.createEpicTask(new String[]{"TestEpicName", "TestEpicDescription"});
    }

    protected SingleTask createSingleTask() throws IntersectionException {
        return creator.createSingleTask(new String[]{"TestEpicName", "TestEpicDescription"},
                Duration.ofHours(1), LocalDateTime.now(ZoneId.of("Europe/Moscow")).plusHours(2));
    }
}
