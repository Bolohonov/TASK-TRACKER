package tasks;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.InMemoryTasksManager;
import repository.TaskStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

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
        Task t1 = new SubTask(epic,"name1", "desc1", InMemoryTasksManager.getId());
        Task t2 = new SubTask(epic,"name2", "desc2", InMemoryTasksManager.getId());
        Task t3 = new SubTask(epic,"name3", "desc3", InMemoryTasksManager.getId());
        assertEquals(TaskStatus.NEW, epic.getStatus());
    }

    @Test
    void getStatusWhenAllSubTasksHasStatusDone() {
        EpicTask epic = createEpicTask();
        Task t1 = new SubTask(epic,"name1", "desc1", InMemoryTasksManager.getId());
        Task t2 = new SubTask(epic,"name2", "desc2", InMemoryTasksManager.getId());
        Task t3 = new SubTask(epic,"name3", "desc3", InMemoryTasksManager.getId());
        t1.setStatus(TaskStatus.DONE);
        t2.setStatus(TaskStatus.DONE);
        t3.setStatus(TaskStatus.DONE);
        assertEquals(TaskStatus.DONE, epic.getStatus());
    }

    @Test
    void getStatusWhenAllSubTasksHasStatusNewAndDone() {
        EpicTask epic = createEpicTask();
        Task t1 = new SubTask(epic,"name1", "desc1", InMemoryTasksManager.getId());
        Task t2 = new SubTask(epic,"name2", "desc2", InMemoryTasksManager.getId());
        Task t3 = new SubTask(epic,"name3", "desc3", InMemoryTasksManager.getId());
        epic.getSubTasks();
        t1.setStatus(TaskStatus.NEW);
        t2.setStatus(TaskStatus.NEW);
        t3.setStatus(TaskStatus.DONE);
        assertEquals(TaskStatus.IN_PROGRESS, epic.getStatus());
    }

    @Test
    void getStatusWhenAllSubTasksHasStatusInProgress() {
        EpicTask epic = createEpicTask();
        Task t1 = new SubTask(epic,"name1", "desc1", InMemoryTasksManager.getId());
        Task t2 = new SubTask(epic,"name2", "desc2", InMemoryTasksManager.getId());
        Task t3 = new SubTask(epic,"name3", "desc3", InMemoryTasksManager.getId());
        t1.setStatus(TaskStatus.IN_PROGRESS);
        t2.setStatus(TaskStatus.IN_PROGRESS);
        t3.setStatus(TaskStatus.IN_PROGRESS);
        assertEquals(TaskStatus.IN_PROGRESS, epic.getStatus());
    }




}