package repository;

import org.junit.jupiter.api.BeforeEach;
import tasks.EpicTask;
import tasks.SingleTask;

public class FileBackedTasksManagerTest extends InMemoryTasksManagerTest implements TaskManagerTest{

    private static final Repository<SingleTask> singleTaskRepository = new Repository<>();
    private static final Repository<EpicTask> epicTaskRepository = new Repository<>();
    protected static final HistoryManager historyManager = new InMemoryHistoryManager();

    FileBackedTasksManagerTest() throws IntersectionException {
    }

    @BeforeEach
    private void clear() {
        manager.removeAllTasks();
        manager.getPrioritizedTasks().clear();
    }

    @Override
    public void putTaskStandardBehavior() {

    }
}
