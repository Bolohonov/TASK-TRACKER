package tasks;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EpicTaskTest extends BaseTaskManager{

    @BeforeEach
    private void clear() {
        manager.removeAllTasks();
        manager.getPrioritizedTasks().clear();
    }

    @Test
    void getStatusWhenNoSubTasks() {
        EpicTask epic = createEpicTask();
        assertEquals(TaskStatus.NEW, epic.getStatus());
    }

    @Test
    void getStatusWhenAllSubTasksHasStatusNew() throws IntersectionException {
        EpicTask epic = new EpicTask("TEST_EPIC_NAME",
                "TEST_EPIC_Description",1200);
        creator.createSubTask(epic,
                new String[]{"TestNameSub1", "TestDescriptionSub1"},
                Duration.ofHours(2), LocalDateTime
                        .of(2022, 01, 3, 10, 0, 00));
        creator.createSubTask(epic,
                new String[]{"TestNameSub2", "TestDescriptionSub1"},
                Duration.ofHours(2), LocalDateTime
                        .of(2022, 01, 3, 14, 0, 00));
        creator.createSubTask(epic,
                new String[]{"TestNameSub3", "TestDescriptionSub1"},
                Duration.ofHours(2), LocalDateTime
                        .of(2022, 01, 3, 17, 0, 00));
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

    @Test
    void getDurationWhenNoSubTasks() {
        EpicTask epic = createEpicTask();
        assertEquals(Optional.empty(), epic.getDuration());
    }

    @Test
    void getDurationWhenOneSubTask() throws IntersectionException {
        EpicTask epic = new EpicTask("TEST_EPIC_NAME",
                "TEST_EPIC_Description",1200);
        SubTask sub = creator.createSubTask(epic,
                new String[]{"TestNameSub1", "TestDescriptionSub1"},
                Duration.ofHours(2), LocalDateTime
                        .of(2022, 01, 3, 10, 0, 00));
        assertEquals(Optional.of(Duration.ofHours(2)), epic.getDuration());
    }

    @Test
    void getDurationWhenTwoSubTasks() throws IntersectionException {
        EpicTask epic = new EpicTask("TEST_EPIC_NAME",
                "TEST_EPIC_Description",1200);
        SubTask sub = creator.createSubTask(epic,
                new String[]{"TestNameSub1", "TestDescriptionSub1"},
                Duration.ofHours(2), LocalDateTime
                        .of(2022, 01, 3, 10, 0, 00));
        SubTask sub2 = creator.createSubTask(epic,
                new String[]{"TestNameSub1", "TestDescriptionSub1"},
                Duration.ofHours(5), LocalDateTime
                        .of(2022, 01, 3, 13, 0, 00));
        assertEquals(Optional.of(Duration.ofHours(7)), epic.getDuration());
    }

    @Test
    void getStartTimeWhenNoSubTasks() {
        EpicTask epic = createEpicTask();
        assertEquals(Optional.empty(), epic.getStartTime());
    }

    @Test
    void getStartTimeWhenOneSubTask() throws IntersectionException {
        EpicTask epic = new EpicTask("TEST_EPIC_NAME",
                "TEST_EPIC_Description",1200);
        SubTask sub = creator.createSubTask(epic,
                new String[]{"TestNameSub1", "TestDescriptionSub1"},
                Duration.ofHours(2), LocalDateTime
                        .of(2022, 01, 3, 10, 0, 00));
        assertEquals(Optional.of(LocalDateTime
                .of(2022, 01, 3, 10, 0, 00)),
                epic.getStartTime());
    }

    @Test
    void getStartTimeStartTimeWhenTwoSubTasks() throws IntersectionException {
        EpicTask epic = new EpicTask("TEST_EPIC_NAME",
                "TEST_EPIC_Description",1200);
        SubTask sub = creator.createSubTask(epic,
                new String[]{"TestNameSub1", "TestDescriptionSub1"},
                Duration.ofHours(2), LocalDateTime
                        .of(2022, 01, 3, 10, 0, 00));
        SubTask sub2 = creator.createSubTask(epic,
                new String[]{"TestNameSub1", "TestDescriptionSub1"},
                Duration.ofHours(5), LocalDateTime
                        .of(2022, 01, 2, 13, 0, 00));
        assertEquals(Optional.of(LocalDateTime
                        .of(2022, 01, 2, 13, 0, 00)),
                epic.getStartTime());
    }

    @Test
    void getStartTimeStartTimeWhenManySubTasks() throws IntersectionException {
        EpicTask epic = new EpicTask("TEST_EPIC_NAME",
                "TEST_EPIC_Description",1200);
        SubTask sub = creator.createSubTask(epic,
                new String[]{"TestNameSub1", "TestDescriptionSub1"},
                Duration.ofHours(2), LocalDateTime
                        .of(2022, 01, 3, 10, 0, 00));
        SubTask sub2 = creator.createSubTask(epic,
                new String[]{"TestNameSub1", "TestDescriptionSub1"},
                Duration.ofHours(5), LocalDateTime
                        .of(2022, 01, 2, 13, 0, 00));
        SubTask sub3 = creator.createSubTask(epic,
                new String[]{"TestNameSub1", "TestDescriptionSub1"},
                Duration.ofHours(1), LocalDateTime
                        .of(2022, 01, 2, 18, 0, 10));
        SubTask sub4 = creator.createSubTask(epic,
                new String[]{"TestNameSub1", "TestDescriptionSub1"},
                Duration.ofSeconds(10), LocalDateTime
                        .of(2022, 01, 1, 10, 0, 10));
        SubTask sub5 = creator.createSubTask(epic,
                new String[]{"TestNameSub1", "TestDescriptionSub1"},
                Duration.ofHours(1), LocalDateTime
                        .of(2022, 01, 1, 10, 0, 21));
        assertEquals(Optional.of(LocalDateTime
                        .of(2022, 01, 1, 10, 0, 10)),
                epic.getStartTime());
    }
}