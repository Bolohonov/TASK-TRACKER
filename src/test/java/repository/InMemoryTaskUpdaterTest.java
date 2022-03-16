package repository;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryTaskUpdaterTest extends InMemoryTasksManagerTest implements TaskUpdaterTest{

    TaskUpdater taskUpdater = new InMemoryTaskUpdater();

    InMemoryTaskUpdaterTest() throws IntersectionException {
    }

    @Test
    @Override
    public void updateName() {
        taskUpdater.updateName(epicTask1, "NewNameEpic");
        taskUpdater.updateName(subTask1, "NewNameSub");
        taskUpdater.updateName(task1, "NewNameSingle");
        assertEquals("NewNameEpic", epicTask1.getName());
        assertEquals("NewNameSub", subTask1.getName());
        assertEquals("NewNameSingle", task1.getName());

    }

    @Test
    @Override
    public void updateDescription() {
        taskUpdater.updateDescription(epicTask1, "NewDescriptionEpic");
        taskUpdater.updateDescription(subTask1, "NewDescriptionSub");
        taskUpdater.updateDescription(task1, "NewDescriptionSingle");
        assertEquals("NewDescriptionEpic", epicTask1.getDescription());
        assertEquals("NewDescriptionSub", subTask1.getDescription());
        assertEquals("NewDescriptionSingle", task1.getDescription());
    }

    @Test
    @Override
    public void updateStatus() {
        taskUpdater.updateStatus(epicTask1, TaskStatus.DONE);
        taskUpdater.updateStatus(subTask1, TaskStatus.DONE);
        taskUpdater.updateStatus(task1, TaskStatus.DONE);
        assertEquals(TaskStatus.IN_PROGRESS, epicTask1.getStatus());
        assertEquals(TaskStatus.DONE, subTask1.getStatus());
        assertEquals(TaskStatus.DONE, task1.getStatus());
    }
}