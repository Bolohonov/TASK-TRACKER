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
import java.time.ZoneId;
import java.util.*;

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
        managerToFile.removeAllTasks();
        managerToFile.getPrioritizedTasks().clear();
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

    private void fillRepositoryWithTestId() throws IntersectionException {
        managerToFile.removeAllTasks();
        managerToFile.getPrioritizedTasks().clear();
        EpicTask epicTask1 = new EpicTask("TestEpicName",
                "TestEpicDescription", 1001);
        EpicTask epicTask2 = new EpicTask("TestEpicName2",
                "TestEpicDescription2", 1002);;
        EpicTask epicTask3 = new EpicTask("TestEpicName3",
                "TestEpicDescription3", 1003);
        SubTask subTask1 = new SubTask(epicTask1, "TestNameSub1",
                "TestDescriptionSub1", 1004, Optional.of(Duration.ofHours(1)),
                Optional.of(LocalDateTime
                        .of(2022,03,14,1,00,10)));
        SubTask subTask2 = new SubTask(epicTask1, "TestNameSub1",
                "TestDescriptionSub1", 1005, Optional.of(Duration.ofHours(2)),
                Optional.of(LocalDateTime
                        .of(2022, 03,14,3,00,10)));
        SubTask subTask3 = new SubTask(epicTask1, "TestNameSub1",
                "TestDescriptionSub1", 1006, Optional.of(Duration.ofHours(2)),
                Optional.of(LocalDateTime
                        .of(2022, 03,14,8,00,10)));
        SingleTask task1 = new SingleTask("TestSingleName",
                "TestSingleDescription", 1007, Optional.of(Duration.ofHours(2)),
                Optional.of(LocalDateTime
                        .of(2022, 03,13,7,00,10)));
        SingleTask task2 = new SingleTask("TestSingleName",
                "TestSingleDescription", 1008, Optional.of(Duration.ofHours(2)),
                Optional.of(LocalDateTime
                        .of(2022, 03,13,10,00,10)));

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

    @Override
    @Test
    public void putTaskIntersectionException() throws IntersectionException {
        EpicTask epicTask1 = new EpicTask("TestEpicName",
                "TestEpicDescription", 1001);
        SubTask subTask1 = new SubTask(epicTask1, "TestNameSub1",
                "TestDescriptionSub1", 1004, Optional.of(Duration.ofHours(1)),
                Optional.of(LocalDateTime
                        .of(2022,03,13,11,00,10)));
        managerToFile.putTask(epicTask1);
        managerToFile.putTask(subTask1);
        SingleTask taskIntersection = creator.createSingleTask(
                new String[]{"Intersection", "TestDescription"},
                Duration.ofHours(1), LocalDateTime
                .of(2022,03,13,10,30,10));;
        final IntersectionException exception = assertThrows(
                IntersectionException.class,
                () -> manager.putTask(taskIntersection)
        );

        assertEquals("Временной интервал занят! Задача Intersection не сохранена",
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
    public void updateTask() {

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

    @Override
    @Test
    public void getHistoryStandardBehavior() throws IntersectionException {
        List<Task> historyList = new LinkedList<>();
        fillRepository();
        manager.getTaskById(epicTask1.getId());
        historyList.add(epicTask1);
        manager.getTaskById(epicTask2.getId());
        historyList.add(epicTask2);
        manager.getTaskById(epicTask3.getId());
        historyList.add(epicTask3);
        manager.getTaskById(subTask1.getId());
        historyList.add(subTask1);
        manager.getTaskById(subTask2.getId());
        historyList.add(subTask2);
        manager.getTaskById(subTask3.getId());
        historyList.add(subTask3);
        manager.getTaskById(task1.getId());
        historyList.add(task1);
        manager.getTaskById(task2.getId());
        historyList.add(task2);
        assertEquals(historyList, managerToFile.getHistory());

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
        prioritizedTasksTest.add(task1);
        prioritizedTasksTest.add(task2);
        assertEquals(prioritizedTasksTest, managerToFile.getPrioritizedTasks());
    }
}
