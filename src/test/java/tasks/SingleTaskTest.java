package tasks;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SingleTaskTest extends BaseTaskManager{

    @BeforeEach
    private void clear() throws ManagerSaveException {
        manager.removeAllTasks();
        manager.getPrioritizedTasks().clear();
    }

    @Test
    void setStatusInProgress() throws IntersectionException {
        Task task = createSingleTask();
        task.setStatus(TaskStatus.IN_PROGRESS);
        assertEquals(TaskStatus.IN_PROGRESS, task.getStatus());
    }

    @Test
    void setStatusDone() throws IntersectionException {
        Task task = createSingleTask();
        task.setStatus(TaskStatus.DONE);
        assertEquals(TaskStatus.DONE, task.getStatus());
    }
}
