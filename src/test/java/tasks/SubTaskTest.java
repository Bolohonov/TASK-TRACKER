package tasks;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SubTaskTest extends BaseTaskManager {

    @BeforeEach
    private void clear() throws ManagerSaveException {
        manager.removeAllTasks();
        manager.getPrioritizedTasks().clear();
    }

    @Test
    void getEpicTask() throws IntersectionException, ManagerSaveException {
        EpicTask epic = createEpicTask();
        SubTask subTask = creator.createSubTask(epic,
                new String[]{"TestEpicName", "TestEpicDescription"},
                Duration.ofHours(1), LocalDateTime.now(ZoneId.of("Europe/Moscow")).plusHours(2));
        epic.addSubTask(subTask);
        manager.putTask(epic);
        assertEquals(epic, manager.getTaskById(subTask.getEpicId()));

    }

    @Test
    void setStatusInProgress() throws IntersectionException {
        EpicTask epic = createEpicTask();
        SubTask subTask = creator.createSubTask(epic,
                new String[]{"TestEpicName", "TestEpicDescription"},
                Duration.ofHours(1), LocalDateTime.now(ZoneId.of("Europe/Moscow")).plusHours(2));
        subTask.setStatus(TaskStatus.IN_PROGRESS);
        assertEquals(TaskStatus.IN_PROGRESS, subTask.getStatus());
    }

    @Test
    void setStatusDone() throws IntersectionException {
        EpicTask epic = createEpicTask();
        SubTask subTask = creator.createSubTask(epic,
                new String[]{"TestEpicName", "TestEpicDescription"},
                Duration.ofHours(1), LocalDateTime.now(ZoneId.of("Europe/Moscow")).plusHours(2));
        subTask.setStatus(TaskStatus.DONE);
        assertEquals(TaskStatus.DONE, subTask.getStatus());
    }
}
