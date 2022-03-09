package tasks;

import org.junit.jupiter.api.Test;
import repository.InMemoryTasksManager;
import repository.TaskStatus;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EpicTaskTest {

    private EpicTask createEpicTask() {
        EpicTask epic = new EpicTask("TestEpicName",
                "TestEpicDescription", InMemoryTasksManager.getId());
        return epic;
    }

    @Test
    void getStatusWhenNoSubTasks() {
        EpicTask epic = createEpicTask();
        assertEquals(TaskStatus.NEW, epic.getStatus());
    }

    @Test
    void getStatusWhenAllSubTasksHasStatusNew() {
        EpicTask epic = createEpicTask();
        Task subTask1 = new SubTask(epic, "name1", "desc1", InMemoryTasksManager.getId(),
                Optional.of(Duration.ofHours(7)), Optional.of(LocalDateTime.now(ZoneId.of("Europe/Moscow"))));
        Task subTask2 = new SubTask(epic, "name2", "desc2", InMemoryTasksManager.getId(),
                Optional.of(Duration.ofHours(8)), Optional.of(LocalDateTime.now(ZoneId.of("Europe/Moscow"))));
        Task subTask3 = new SubTask(epic, "name3", "desc3", InMemoryTasksManager.getId(),
                Optional.of(Duration.ofHours(5)), Optional.of(LocalDateTime.now(ZoneId.of("Europe/Moscow"))));
        assertEquals(TaskStatus.NEW, epic.getStatus());
    }

    @Test
    void getStatusWhenAllSubTasksHasStatusDone() {
        EpicTask epic = createEpicTask();
        Task subTask1 = new SubTask(epic, "name1", "desc1", InMemoryTasksManager.getId(),
                Optional.of(Duration.ofHours(7)), Optional.of(LocalDateTime.now(ZoneId.of("Europe/Moscow"))));
        Task subTask2 = new SubTask(epic, "name2", "desc2", InMemoryTasksManager.getId(),
                Optional.of(Duration.ofHours(8)), Optional.of(LocalDateTime.now(ZoneId.of("Europe/Moscow"))));
        Task subTask3 = new SubTask(epic, "name3", "desc3", InMemoryTasksManager.getId(),
                Optional.of(Duration.ofHours(5)), Optional.of(LocalDateTime.now(ZoneId.of("Europe/Moscow"))));
        subTask1.setStatus(TaskStatus.DONE);
        subTask2.setStatus(TaskStatus.DONE);
        subTask3.setStatus(TaskStatus.DONE);
        assertEquals(TaskStatus.DONE, epic.getStatus());
    }

    @Test
    void getStatusWhenAllSubTasksHasStatusNewAndDone() {
        EpicTask epic = createEpicTask();
        Task subTask1 = new SubTask(epic, "name1", "desc1", InMemoryTasksManager.getId(),
                Optional.of(Duration.ofHours(7)), Optional.of(LocalDateTime.now(ZoneId.of("Europe/Moscow"))));
        Task subTask2 = new SubTask(epic, "name2", "desc2", InMemoryTasksManager.getId(),
                Optional.of(Duration.ofHours(8)), Optional.of(LocalDateTime.now(ZoneId.of("Europe/Moscow"))));
        Task subTask3 = new SubTask(epic, "name3", "desc3", InMemoryTasksManager.getId(),
                Optional.of(Duration.ofHours(5)), Optional.of(LocalDateTime.now(ZoneId.of("Europe/Moscow"))));
        epic.getSubTasks();
        subTask1.setStatus(TaskStatus.NEW);
        subTask2.setStatus(TaskStatus.NEW);
        subTask3.setStatus(TaskStatus.DONE);
        assertEquals(TaskStatus.IN_PROGRESS, epic.getStatus());
    }

    @Test
    void getStatusWhenAllSubTasksHasStatusInProgress() {
        EpicTask epic = createEpicTask();
        Task subTask1 = new SubTask(epic, "name1", "desc1", InMemoryTasksManager.getId(),
                Optional.of(Duration.ofHours(7)), Optional.of(LocalDateTime.now(ZoneId.of("Europe/Moscow"))));
        Task subTask2 = new SubTask(epic, "name2", "desc2", InMemoryTasksManager.getId(),
                Optional.of(Duration.ofHours(8)), Optional.of(LocalDateTime.now(ZoneId.of("Europe/Moscow"))));
        Task subTask3 = new SubTask(epic, "name3", "desc3", InMemoryTasksManager.getId(),
                Optional.of(Duration.ofHours(5)), Optional.of(LocalDateTime.now(ZoneId.of("Europe/Moscow"))));
        subTask1.setStatus(TaskStatus.IN_PROGRESS);
        subTask2.setStatus(TaskStatus.IN_PROGRESS);
        subTask3.setStatus(TaskStatus.IN_PROGRESS);
        assertEquals(TaskStatus.IN_PROGRESS, epic.getStatus());
    }


}