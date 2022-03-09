package tasks;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EpicTaskTest extends BaseTaskManager{

    @BeforeEach
    private void clear() {
        manager.removeAllTasks();
        manager.getPrioritizedTasks().clear();
    }

    @Test
    void getStatusWhenNoSubTasks() {
        EpicTask epic = super.createEpicTask();
        assertEquals(TaskStatus.NEW, epic.getStatus());
    }

    @Test
    void getStatusWhenAllSubTasksHasStatusNew() throws IntersectionException {
        EpicTask epic = createEpicTask();
        creator.createSubTask(epic, new String[]{"name1", "desc1"},
                Duration.ofHours(1), LocalDateTime.now(ZoneId.of("Europe/Moscow")));
        creator.createSubTask(epic, new String[]{"name1", "desc1"},
                Duration.ofHours(1), LocalDateTime.now(ZoneId.of("Europe/Moscow")).plusHours(2));
        creator.createSubTask(epic, new String[]{"name1", "desc1"},
                Duration.ofHours(1), LocalDateTime.now(ZoneId.of("Europe/Moscow")).plusHours(4));
        assertEquals(TaskStatus.NEW, epic.getStatus());
    }

    @Test
    void getStatusWhenAllSubTasksHasStatusDone() throws IntersectionException {
        EpicTask epic = createEpicTask();
        creator.createSubTask(epic, new String[]{"name1", "desc1"},
                Duration.ofHours(1), LocalDateTime.now(ZoneId.of("Europe/Moscow")))
                .setStatus(TaskStatus.DONE);
        creator.createSubTask(epic, new String[]{"name1", "desc1"},
                Duration.ofHours(1), LocalDateTime.now(ZoneId.of("Europe/Moscow")).plusHours(2))
                .setStatus(TaskStatus.DONE);
        creator.createSubTask(epic, new String[]{"name1", "desc1"},
                Duration.ofHours(1), LocalDateTime.now(ZoneId.of("Europe/Moscow")).plusHours(4))
                .setStatus(TaskStatus.DONE);;
        assertEquals(TaskStatus.DONE, epic.getStatus());
    }

    @Test
    void getStatusWhenAllSubTasksHasStatusNewAndDone() throws IntersectionException {
        EpicTask epic = createEpicTask();
        creator.createSubTask(epic, new String[]{"name1", "desc1"},
                Duration.ofHours(1), LocalDateTime.now(ZoneId.of("Europe/Moscow")));
        creator.createSubTask(epic, new String[]{"name1", "desc1"},
                Duration.ofHours(1), LocalDateTime.now(ZoneId.of("Europe/Moscow")).plusHours(2));
        creator.createSubTask(epic, new String[]{"name1", "desc1"},
                Duration.ofHours(1), LocalDateTime.now(ZoneId.of("Europe/Moscow")).plusHours(4))
                .setStatus(TaskStatus.DONE);
        assertEquals(TaskStatus.IN_PROGRESS, epic.getStatus());
    }

    @Test
    void getStatusWhenAllSubTasksHasStatusInProgress() throws IntersectionException {
        EpicTask epic = createEpicTask();
        creator.createSubTask(epic, new String[]{"name1", "desc1"},
                Duration.ofHours(1), LocalDateTime.now(ZoneId.of("Europe/Moscow")));
        creator.createSubTask(epic, new String[]{"name1", "desc1"},
                Duration.ofHours(1), LocalDateTime.now(ZoneId.of("Europe/Moscow")).plusHours(2));
        creator.createSubTask(epic, new String[]{"name1", "desc1"},
                Duration.ofHours(1), LocalDateTime.now(ZoneId.of("Europe/Moscow")).plusHours(4))
                        .setStatus(TaskStatus.IN_PROGRESS);
        assertEquals(TaskStatus.IN_PROGRESS, epic.getStatus());
    }
}