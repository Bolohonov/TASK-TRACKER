package tasks;

import org.junit.Test;
import repository.InMemoryTasksManager;
import repository.TaskStatus;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SubTaskTest {

    private EpicTask createEpicTask() {
        EpicTask epic = new EpicTask("TestEpicName",
                "TestEpicDescription", InMemoryTasksManager.getId());
        return epic;
    }

    @Test
    void getEpicTask() {
        EpicTask epic = createEpicTask();
        SubTask subTask = new SubTask(epic, "name1", "desc1", InMemoryTasksManager.getId(),
                Optional.of(Duration.ofHours(8)), Optional.of(LocalDateTime.now(ZoneId.of("Europe/Moscow"))));
        assertEquals(epic, subTask.getEpicTask());

    }


    @Test
    void setStatusInProgress() {
        EpicTask epic = createEpicTask();
        SubTask subTask = new SubTask(epic, "name1", "desc1", InMemoryTasksManager.getId(),
                Optional.of(Duration.ofHours(8)), Optional.of(LocalDateTime.now(ZoneId.of("Europe/Moscow"))));
        subTask.setStatus(TaskStatus.IN_PROGRESS);
        assertEquals(TaskStatus.IN_PROGRESS, subTask.getStatus());
    }

    @Test
    void setStatusDone() {
        EpicTask epic = createEpicTask();
        SubTask subTask = new SubTask(epic, "name1", "desc1", InMemoryTasksManager.getId(),
                Optional.of(Duration.ofHours(8)), Optional.of(LocalDateTime.now(ZoneId.of("Europe/Moscow"))));
        subTask.setStatus(TaskStatus.DONE);
        assertEquals(TaskStatus.DONE, subTask.getStatus());
    }


}
