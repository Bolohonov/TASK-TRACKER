package repository;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tasks.EpicTask;
import tasks.SingleTask;
import tasks.SubTask;
import tasks.Task;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class FileBackedTasksManagerTest extends InMemoryTasksManagerTest
        implements TaskManagerTest {

    protected static final Path TEST_PUT_TASK_STANDARD_BEHAVIOR
            = Paths.get("./resources/testPutStandardBehavior.csv");
    protected static final Path EMPTY_FILE
            = Paths.get("./resources/empty.csv");

    FileBackedTasksManagerTest() throws IntersectionException {
    }

    @BeforeEach
    private void clear() throws IOException {
        managerToFile.removeAllTasks();
        managerToFile.getPrioritizedTasks().clear();
        File file = REPOSITORY.toFile();
        if (file.delete()) {
            file.createNewFile();
        }
    }

    private void fillRepositoryWithTestId() throws IntersectionException {
        EpicTask epicTask1 = new EpicTask("TestEpicName",
                "TestEpicDescription", 1001);
        EpicTask epicTask2 = new EpicTask("TestEpicName2",
                "TestEpicDescription2", 1002);
        EpicTask epicTask3 = new EpicTask("TestEpicName3",
                "TestEpicDescription3", 1003);
        SubTask subTask1 = new SubTask(epicTask1, "TestNameSub1",
                "TestDescriptionSub1", 1004, Optional.of(Duration.ofHours(1)),
                Optional.of(LocalDateTime
                        .of(2022, 03, 10, 1, 00, 10)));
        SubTask subTask2 = new SubTask(epicTask1, "TestNameSub1",
                "TestDescriptionSub1", 1005, Optional.of(Duration.ofHours(2)),
                Optional.of(LocalDateTime
                        .of(2022, 03, 10, 3, 00, 10)));
        SubTask subTask3 = new SubTask(epicTask2, "TestNameSub1",
                "TestDescriptionSub1", 1006, Optional.of(Duration.ofHours(2)),
                Optional.of(LocalDateTime
                        .of(2022, 03, 10, 8, 00, 10)));
        SingleTask task1 = new SingleTask("TestSingleName",
                "TestSingleDescription", 1007, Optional.of(Duration.ofHours(2)),
                Optional.of(LocalDateTime
                        .of(2022, 03, 13, 7, 00, 10)));
        SingleTask task2 = new SingleTask("TestSingleName",
                "TestSingleDescription", 1008, Optional.of(Duration.ofHours(2)),
                Optional.of(LocalDateTime
                        .of(2022, 03, 13, 10, 00, 10)));

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
        fillRepositoryWithTestId();
        FileReader input1 = new FileReader(REPOSITORY.toFile());
        FileReader input2
                = new FileReader(TEST_PUT_TASK_STANDARD_BEHAVIOR.toFile());
        assertTrue(IOUtils.contentEqualsIgnoreEOL(input1, input2));
    }

    @Test
    public void putTaskEmptyFile() {
        TaskManager emptyToFile =
                new FileBackedTasksManager(EMPTY_FILE.toFile());
        managerToFile.removeAllTasks();
        assertEquals(emptyToFile.getSingleTasks(), managerToFile.getSingleTasks());
        assertEquals(emptyToFile.getEpicTasks(), managerToFile.getEpicTasks());
    }

    @Override
    @Test
    public void putTaskIntersectionException() throws IntersectionException {
        EpicTask epicTask1 = new EpicTask("TestEpicName",
                "TestEpicDescription", 1001);
        SubTask subTask1 = new SubTask(epicTask1, "TestNameSub1",
                "TestDescriptionSub1", 1004, Optional.of(Duration.ofHours(5)),
                Optional.of(LocalDateTime
                        .of(2022, 03, 13, 11, 00, 10)));
        managerToFile.putTask(epicTask1);
        managerToFile.putTask(subTask1);
        SingleTask taskIntersection = creator.createSingleTask(
                new String[]{"Intersection", "TestDescription"},
                Duration.ofHours(1), LocalDateTime
                        .of(2022, 03, 13, 15, 0, 00));
        final IntersectionException exception = assertThrows(
                IntersectionException.class,
                () -> manager.putTask(taskIntersection)
        );

        assertEquals("Временной интервал занят! Задача с ID "
                        + taskIntersection.getId() + " не сохранена",
                exception.getMessage());
    }

    @Override
    @Test
    public void getTaskByIdStandardBehavior() throws IntersectionException {
        fillRepository();
        assertEquals(epicTask1, managerToFile.getTaskById(epicTask1.getId()));
        assertEquals(epicTask2, managerToFile.getTaskById(epicTask2.getId()));
        assertEquals(epicTask3, managerToFile.getTaskById(epicTask3.getId()));
        assertEquals(subTask1, managerToFile.getTaskById(subTask1.getId()));
        assertEquals(subTask2, managerToFile.getTaskById(subTask2.getId()));
        assertEquals(subTask3, managerToFile.getTaskById(subTask3.getId()));
        assertEquals(task1, managerToFile.getTaskById(task1.getId()));
        assertEquals(task2, managerToFile.getTaskById(task2.getId()));
    }

    @Override
    @Test
    public void getTaskByIdEmptyRepository() {
        int id = 1;
        assertEquals(null, managerToFile.getTaskById(id));
    }

    @Test
    public void getEpicTaskWithoutSubTasks() throws IntersectionException {
        fillRepository();
        assertEquals(epicTask3, managerToFile.getTaskById(epicTask3.getId()));
    }

    @Override
    @Test
    public void getTaskByIdWrongId() throws IntersectionException {
        managerToFile.putTask(epicTask1);
        managerToFile.putTask(task1);
        assertEquals(epicTask1, managers.getTaskManager().getTaskById(epicTask1.getId()));
        assertEquals(task1, managers.getTaskManager().getTaskById(task1.getId()));
        assertEquals(null, managers.getTaskManager().getTaskById(1000));
    }

    @Test
    @Override
    public void getSingleTasksStandardBehavior() throws IntersectionException {
        Repository<SingleTask> singleTaskRepository = new Repository<>();
        managerToFile.putTask(task1);
        managerToFile.putTask(task2);
        singleTaskRepository.putTask(task1);
        singleTaskRepository.putTask(task2);
        assertEquals(singleTaskRepository.getTasks(), managerToFile.getSingleTasks());
    }

    @Test
    @Override
    public void getSingleTasksEmptyRepository() {
        Repository<SingleTask> singleTaskRepository = new Repository<>();
        assertEquals(singleTaskRepository.getTasks(), managerToFile.getSingleTasks());
    }

    @Test
    @Override
    public void getEpicTasksStandardBehavior() throws IntersectionException {
        fillRepository();
        Repository<EpicTask> epicTaskRepository = new Repository<>();
        epicTaskRepository.putTask(epicTask1);
        epicTaskRepository.putTask(epicTask2);
        epicTaskRepository.putTask(epicTask3);
        assertEquals(epicTaskRepository.getTasks(), managerToFile.getEpicTasks());
    }

    @Test
    @Override
    public void getEpicTasksEmptyRepository() {
        Repository<EpicTask> epicTaskRepository = new Repository<>();
        assertEquals(epicTaskRepository.getTasks(), managerToFile.getEpicTasks());
    }

    @Test
    @Override
    public void getSubTasksByEpicEmptyRepository() throws IntersectionException {
        Map<Integer, SubTask> subTasksMap1 = new LinkedHashMap<>();
        EpicTask epic1 = creator.createEpicTask(
                new String[]{"TestName", "TestDescription"});
        managerToFile.putTask(epic1);
        assertEquals(subTasksMap1, epic1.getSubTasks());
    }

    @Override
    @Test
    public void updateTask() throws IntersectionException {
        fillRepository();
        managerToFile.getTaskById(task1.getId()).setStatus(TaskStatus.IN_PROGRESS);
        assertTrue(managerToFile.updateTask(task1));
    }

    @Override
    @Test
    public void removeAllTasks() throws IntersectionException {
        fillRepository();
        Repository<EpicTask> epicTaskRepository = new Repository<>();
        Repository<SingleTask> singleTaskRepository = new Repository<>();
        managerToFile.removeAllTasks();
        assertEquals(singleTaskRepository.getTasks(), manager.getSingleTasks());
        assertEquals(epicTaskRepository.getTasks(), manager.getEpicTasks());
    }

    @Override
    @Test
    public void removeTaskByIdStandardBehavior() throws IntersectionException {
        fillRepository();
        managerToFile.removeTaskById(epicTask1.getId());
        assertFalse(manager.getEpicTasks().containsKey(epicTask1.getId()));
        managerToFile.removeTaskById(subTask3.getId());
        assertFalse(epicTask2.getSubTasks().containsKey(subTask3.getId()));
        managerToFile.removeTaskById(task1.getId());
        assertFalse(manager.getSingleTasks().containsKey(task1.getId()));
    }

    @Test
    @Override
    public void shouldGetPrioritizedTasks() throws IntersectionException {
        Set<Task> prioritizedTasksTest = new TreeSet<>(Comparator.<Task, LocalDateTime>comparing(
                        t -> t.getStartTime().orElse(null),
                        Comparator.nullsLast(Comparator.naturalOrder())
                )
                .thenComparingInt(Task::getId));

        fillRepository();
        prioritizedTasksTest.add(epicTask1);
        prioritizedTasksTest.add(epicTask2);
        prioritizedTasksTest.add(epicTask3);
        prioritizedTasksTest.add(subTask1);
        prioritizedTasksTest.add(subTask2);
        prioritizedTasksTest.add(subTask3);
        prioritizedTasksTest.add(task1);
        prioritizedTasksTest.add(task2);
        assertEquals(prioritizedTasksTest, managerToFile.getPrioritizedTasks());
    }

    @Test
    public void shouldFillRepositoryFromFile() throws IntersectionException {
        FileBackedTasksManager testManager
                = new FileBackedTasksManager(Paths
                .get("./resources/shouldFillRepositoryFromFile.csv").toFile());
        Repository<Task> testRep = new Repository();

        EpicTask epicTask1 = new EpicTask("TestEpicName",
                "TestEpicDescription", 1001);
        EpicTask epicTask2 = new EpicTask("TestEpicName2",
                "TestEpicDescription2", 1002);
        ;
        EpicTask epicTask3 = new EpicTask("TestEpicName3",
                "TestEpicDescription3", 1003);
        SubTask subTask1 = new SubTask(epicTask1, "TestNameSub1",
                "TestDescriptionSub1", 1004, Optional.of(Duration.ofHours(1)),
                Optional.of(LocalDateTime
                        .of(2022, 03, 12, 1, 00, 10)));
        SubTask subTask2 = new SubTask(epicTask1, "TestNameSub1",
                "TestDescriptionSub1", 1005, Optional.of(Duration.ofHours(2)),
                Optional.of(LocalDateTime
                        .of(2022, 03, 12, 3, 00, 10)));
        SubTask subTask3 = new SubTask(epicTask1, "TestNameSub1",
                "TestDescriptionSub1", 1006, Optional.of(Duration.ofHours(2)),
                Optional.of(LocalDateTime
                        .of(2022, 03, 12, 8, 00, 10)));
        SingleTask task1 = new SingleTask("TestSingleName",
                "TestSingleDescription", 1007, Optional.of(Duration.ofHours(2)),
                Optional.of(LocalDateTime
                        .of(2022, 03, 13, 7, 00, 10)));
        SingleTask task2 = new SingleTask("TestSingleName",
                "TestSingleDescription", 1008, Optional.of(Duration.ofHours(2)),
                Optional.of(LocalDateTime
                        .of(2022, 03, 13, 10, 00, 10)));

        testRep.putTask(epicTask1);
        testRep.putTask(epicTask2);
        testRep.putTask(epicTask3);
        assertEquals(testRep.getTasks(), testManager.getEpicTasks());
        testRep.removeAllTasks();
        testRep.putTask(subTask1);
        testRep.putTask(subTask2);
        testRep.putTask(subTask3);
        assertEquals(testRep.getTasks(), testManager.getEpicTasks().get(1001).getSubTasks());
        testRep.removeAllTasks();
        testRep.putTask(task1);
        testRep.putTask(task2);
        assertEquals(testRep.getTasks(), testManager.getSingleTasks());
    }

    @Test
    public void shouldFillHistoryFromFile() throws IntersectionException {
        FileBackedTasksManager testManager
                = new FileBackedTasksManager(Paths
                .get("./resources/shouldFillHistoryFromFile.csv").toFile());
        List<Task> actualList = new LinkedList<>();
        for (int i=5; i>0; i--) {
            actualList.add(testManager.getHistory().get(testManager.getHistory().size()-i));
        }

        EpicTask epicTask1 = new EpicTask("TestEpicName",
                "TestEpicDescription", 1001);
        EpicTask epicTask3 = new EpicTask("TestEpicName3",
                "TestEpicDescription3", 1003);
        SubTask subTask1 = new SubTask(epicTask1, "TestNameSub1",
                "TestDescriptionSub1", 1004, Optional.of(Duration.ofHours(1)),
                Optional.of(LocalDateTime
                        .of(2022, 03, 12, 1, 00, 10)));
        SubTask subTask2 = new SubTask(epicTask1, "TestNameSub1",
                "TestDescriptionSub1", 1005, Optional.of(Duration.ofHours(2)),
                Optional.of(LocalDateTime
                        .of(2022, 03, 12, 3, 00, 10)));
        SingleTask task1 = new SingleTask("TestSingleName",
                "TestSingleDescription", 1007, Optional.of(Duration.ofHours(2)),
                Optional.of(LocalDateTime
                        .of(2022, 03, 13, 7, 00, 10)));
        SingleTask task2 = new SingleTask("TestSingleName",
                "TestSingleDescription", 1008, Optional.of(Duration.ofHours(2)),
                Optional.of(LocalDateTime
                        .of(2022, 03, 13, 10, 00, 10)));

        List<Task> list = new LinkedList<>();
        list.add(task2);
        list.add(task1);
        list.add(epicTask1);
        list.add(subTask2);
        list.add(epicTask3);
        assertEquals(list, actualList);
    }
}
