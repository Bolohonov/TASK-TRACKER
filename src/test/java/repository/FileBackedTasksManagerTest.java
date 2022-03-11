package repository;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tasks.EpicTask;
import tasks.SingleTask;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

public class FileBackedTasksManagerTest extends InMemoryTasksManagerTest implements TaskManagerTest{

    private static final Repository<SingleTask> singleTaskRepository = new Repository<>();
    private static final Repository<EpicTask> epicTaskRepository = new Repository<>();
    protected static final HistoryManager historyManager = new InMemoryHistoryManager();
    private static final Path REPOSITORY = Paths.get("./resources/data.csv");
    private static final Path TEST_PUT_TASK_STANDARD_BEHAVIOR
            = Paths.get("./resources/testPutStandardBehavior.csv");

    FileBackedTasksManagerTest() throws IntersectionException {
    }

    @BeforeEach
    private void clear() throws IOException {
        manager.removeAllTasks();
        manager.getPrioritizedTasks().clear();
        File file = REPOSITORY.toFile();
        if (file.delete()) {
            file.createNewFile();
        } else {
            System.out.println("Удаление невозможно!");
        }
    }

    private void fillRepository() throws IntersectionException {
        managerToFile.putTask(epicTask1);
        managerToFile.putTask(epicTask2);
        managerToFile.putTask(epicTask3);
        managerToFile.putTask(subTask1);
        managerToFile.putTask(subTask2);
        managerToFile.putTask(subTask3);
        managerToFile.putTask(task1);
        managerToFile.putTask(task2);
    }

    @Override
    @Test
    public void putTaskStandardBehavior() throws IntersectionException, IOException {
        fillRepository();
        FileReader input1 = new FileReader(REPOSITORY.toFile());
        FileReader input2
                = new FileReader(TEST_PUT_TASK_STANDARD_BEHAVIOR.toFile());
        assertTrue(IOUtils.contentEqualsIgnoreEOL(input1, input2));
    }
}
