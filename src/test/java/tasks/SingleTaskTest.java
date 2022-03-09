package tasks;

import org.junit.Test;
import repository.InMemoryTasksManager;
import repository.TaskStatus;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SingleTaskTest {

    private SingleTask createSingleTask() {
        SingleTask task = new SingleTask("TestName",
                "TestDescription", InMemoryTasksManager.getId(),
                Duration.ofHours(8), LocalDateTime.now(ZoneId.of("Europe/Moscow")));
        return task;
    }

    @Test
    void setStatusInProgress() {
        Task task = createSingleTask();
        task.setStatus(TaskStatus.IN_PROGRESS);
        assertEquals(TaskStatus.IN_PROGRESS, task.getStatus());
    }

    @Test
    void setStatusDone() {
        Task task = createSingleTask();
        task.setStatus(TaskStatus.DONE);
        assertEquals(TaskStatus.DONE, task.getStatus());
    }


}
