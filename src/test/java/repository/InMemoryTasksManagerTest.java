package repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tasks.EpicTask;
import tasks.SingleTask;
import tasks.SubTask;
import tasks.Task;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;


class InMemoryTasksManagerTest implements TaskManagerTest {

    protected static final Managers managers = new Managers();
    protected static final TaskManager manager = managers.getTaskManager();
    protected static final TaskManager managerToFile = managers.getTaskManagerToFile();
    protected static final TaskCreator creator = managers.getFactory();
    protected static final Path REPOSITORY = Paths.get("./resources/data.csv");

    @BeforeEach
    private void clear() throws ManagerSaveException {
        try {
            manager.removeAllTasks();
            manager.getPrioritizedTasks().clear();
            manager.getHistory().clear();
            File file = REPOSITORY.toFile();
            if (file.delete()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected EpicTask epicTask1 = creator.createEpicTask(
            new String[]{"TestEpicName", "TestDescription"});
    protected EpicTask epicTask2 = creator.createEpicTask(
            new String[]{"TestEpicName2", "TestDescription"});
    protected EpicTask epicTask3 = creator.createEpicTask(
            new String[]{"TestEpicName3", "TestDescription3"});
    protected SubTask subTask1 = creator.createSubTask(epicTask1,
            new String[]{"TestNameSub1", "TestDescriptionSub1"},
            Duration.ofHours(2), LocalDateTime
                    .of(2022, 03, 3, 10, 0, 00));
    protected SubTask subTask2 = creator.createSubTask(epicTask1,
            new String[]{"TestNameSub2", "TestDescriptionSub1"},
            Duration.ofHours(2), LocalDateTime
                    .of(2022, 03, 3, 14, 0, 00));
    protected SubTask subTask3 = creator.createSubTask(epicTask2,
            new String[]{"TestNameSub3", "TestDescriptionSub1"},
            Duration.ofHours(2), LocalDateTime
                    .of(2022, 03, 3, 17, 0, 00));
    protected SingleTask task1 = creator.createSingleTask(
            new String[]{"TestName1", "TestDescription"},
            Duration.ofHours(2), LocalDateTime
                    .of(2022, 03, 3, 21, 0, 00));
    protected SingleTask task2 = creator.createSingleTask(
            new String[]{"TestName2", "TestDescription"},
            Duration.ofHours(2), LocalDateTime
                    .of(2022, 03, 4, 00, 0, 00));

    protected void fillRepository() throws IntersectionException, ManagerSaveException {
        manager.putTask(epicTask1);
        manager.putTask(epicTask2);
        manager.putTask(epicTask3);
        manager.putTask(subTask1);
        manager.putTask(subTask2);
        manager.putTask(subTask3);
        manager.putTask(task1);
        manager.putTask(task2);
    }

    InMemoryTasksManagerTest() throws IntersectionException {
    }

    @Override
    @Test
    public void putTaskStandardBehavior() throws IntersectionException, IOException, ManagerSaveException {
        fillRepository();
        assertTrue(manager.getSingleTasks().containsKey(task1.getId()));
        assertTrue(manager.getSingleTasks().containsKey(task2.getId()));
        assertTrue(manager.getEpicTasks().containsKey(epicTask1.getId()));
        assertTrue(manager.getEpicTasks().containsKey(epicTask2.getId()));
        assertTrue(manager.getEpicTasks().containsKey(epicTask3.getId()));
        assertTrue(manager.getSubTasksByEpic(epicTask1).containsKey(subTask1.getId()));
        assertTrue(manager.getSubTasksByEpic(epicTask1).containsKey(subTask2.getId()));
        assertTrue(manager.getSubTasksByEpic(epicTask2).containsKey(subTask3.getId()));
    }

    @Override
    @Test
    public void putTaskIntersectionException() throws IntersectionException, ManagerSaveException {
        manager.putTask(epicTask1);
        manager.putTask(subTask2);
        SingleTask taskIntersection = creator.createSingleTask(
                new String[]{"Intersection", "TestDescription"},
                Duration.ofHours(1), LocalDateTime
                        .of(2022, 03, 3, 15, 0, 00));
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
    public void getTaskByIdStandardBehavior() throws IntersectionException, ManagerSaveException {
        fillRepository();
        assertEquals(epicTask1, managers.getTaskManager().getTaskById(epicTask1.getId()));
        assertEquals(epicTask2, managers.getTaskManager().getTaskById(epicTask2.getId()));
        assertEquals(epicTask3, managers.getTaskManager().getTaskById(epicTask3.getId()));
        assertEquals(subTask1, managers.getTaskManager().getTaskById(subTask1.getId()));
        assertEquals(subTask2, managers.getTaskManager().getTaskById(subTask2.getId()));
        assertEquals(subTask3, managers.getTaskManager().getTaskById(subTask3.getId()));
        assertEquals(task1, managers.getTaskManager().getTaskById(task1.getId()));
        assertEquals(task2, managers.getTaskManager().getTaskById(task2.getId()));
    }

    @Override
    @Test
    public void getTaskByIdEmptyRepository() throws ManagerSaveException {
        int id = 1;
        assertEquals(null, managers.getTaskManager().getTaskById(id));
    }

    @Override
    @Test
    public void getTaskByIdWrongId() throws IntersectionException, ManagerSaveException {
        manager.putTask(epicTask1);
        manager.putTask(task1);
        assertEquals(epicTask1, managers.getTaskManager().getTaskById(epicTask1.getId()));
        assertEquals(task1, managers.getTaskManager().getTaskById(task1.getId()));
        assertEquals(null, managers.getTaskManager().getTaskById(0));
    }

    @Test
    @Override
    public void getSingleTasksStandardBehavior() throws IntersectionException, ManagerSaveException {
        Repository<SingleTask> singleTaskRepository = new Repository<>();
        manager.putTask(task1);
        manager.putTask(task2);
        singleTaskRepository.putTask(task1);
        singleTaskRepository.putTask(task2);
        assertEquals(singleTaskRepository.getTasks(), manager.getSingleTasks());
    }

    @Test
    @Override
    public void getSingleTasksEmptyRepository() {
        Repository<SingleTask> singleTaskRepository = new Repository<>();
        assertEquals(singleTaskRepository.getTasks(), manager.getSingleTasks());
    }

    @Test
    @Override
    public void getEpicTasksStandardBehavior() throws IntersectionException, ManagerSaveException {
        Repository<EpicTask> epicTaskRepository = new Repository<>();
        fillRepository();
        epicTaskRepository.putTask(epicTask1);
        epicTaskRepository.putTask(epicTask2);
        epicTaskRepository.putTask(epicTask3);
        assertEquals(epicTaskRepository.getTasks(), manager.getEpicTasks());
    }

    @Test
    @Override
    public void getEpicTasksEmptyRepository() {
        Repository<EpicTask> epicTaskRepository = new Repository<>();
        assertEquals(epicTaskRepository.getTasks(), manager.getEpicTasks());
    }

    @Test
    @Override
    public void getSubTasksByEpicStandardBehavior() throws IntersectionException, ManagerSaveException {
        Map<Integer, Task> subTasksMap1 = new LinkedHashMap();
        Map<Integer, Task> subTasksMap2 = new LinkedHashMap();
        Map<Integer, Task> subTasksMap3 = new LinkedHashMap();
        fillRepository();
        subTasksMap1.put(subTask1.getId(), subTask1);
        subTasksMap1.put(subTask2.getId(), subTask2);
        subTasksMap2.put(subTask3.getId(), subTask3);
        assertEquals(subTasksMap1, epicTask1.getSubTasks());
        assertEquals(subTasksMap2, epicTask2.getSubTasks());
        assertEquals(subTasksMap3, epicTask3.getSubTasks());
    }

    @Test
    @Override
    public void getSubTasksByEpicEmptyRepository() throws IntersectionException, ManagerSaveException {
        Map<Integer, SubTask> subTasksMap1 = new LinkedHashMap<>();
        EpicTask task1 = creator.createEpicTask(
                new String[]{"TestName", "TestDescription"});
        manager.putTask(task1);
        assertEquals(subTasksMap1, task1.getSubTasks());
    }

    @Override
    @Test
    public void updateTask() throws IntersectionException, ManagerSaveException {
        fillRepository();
        manager.getTaskById(task1.getId()).setStatus(TaskStatus.IN_PROGRESS);
        assertTrue(manager.updateTask(task1));
        manager.getHistory().clear();
    }

    @Override
    @Test
    public void updateTaskWithWrongId() throws IntersectionException, ManagerSaveException {
        fillRepository();
        manager.getTaskById(task1.getId()).setStatus(TaskStatus.IN_PROGRESS);
        assertFalse(manager.updateTask(manager.getTaskById(1515)));
    }

    @Override
    @Test
    public void removeAllTasks() throws IntersectionException, ManagerSaveException {
        Repository<EpicTask> epicTaskRepository = new Repository<>();
        Repository<SingleTask> singleTaskRepository = new Repository<>();
        fillRepository();
        manager.removeAllTasks();
        assertEquals(singleTaskRepository.getTasks(), manager.getSingleTasks());
        assertEquals(epicTaskRepository.getTasks(), manager.getEpicTasks());
    }

    @Override
    @Test
    public void removeTaskByIdStandardBehavior() throws IntersectionException, ManagerSaveException {
        fillRepository();
        manager.removeTaskById(epicTask1.getId());
        assertFalse(manager.getEpicTasks().containsKey(epicTask1.getId()));
        manager.removeTaskById(subTask3.getId());
        assertFalse(epicTask2.getSubTasks().containsKey(subTask3.getId()));
        manager.removeTaskById(task1.getId());
        assertFalse(manager.getSingleTasks().containsKey(task1.getId()));
    }

    @Test
    @Override
    public void shouldGetPrioritizedTasks() throws IntersectionException, ManagerSaveException {
        Set<Task> prioritizedTasksTest = new TreeSet<>(Comparator.<Task, LocalDateTime>comparing(
                        t -> t.getStartTime().orElse(null),
                        Comparator.nullsLast(Comparator.naturalOrder())
                )
                .thenComparingInt(Task::getId));

        manager.putTask(epicTask1);
        manager.putTask(epicTask2);
        manager.putTask(epicTask3);
        manager.putTask(subTask1);
        manager.putTask(subTask2);
        manager.putTask(task1);
        manager.putTask(task2);
        prioritizedTasksTest.add(epicTask1);
        prioritizedTasksTest.add(epicTask2);
        prioritizedTasksTest.add(epicTask3);
        prioritizedTasksTest.add(subTask1);
        prioritizedTasksTest.add(subTask2);
        prioritizedTasksTest.add(task1);
        prioritizedTasksTest.add(task2);
        assertEquals(prioritizedTasksTest, managers.getTaskManager().getPrioritizedTasks());
    }

    @Test
    public void getHistory() throws IntersectionException, ManagerSaveException {
        List<Task> list = new LinkedList<>();
        fillRepository();
        manager.getTaskById(epicTask1.getId());
        list.add(epicTask1);
        manager.getTaskById(subTask2.getId());
        list.add(subTask2);
        manager.getTaskById(task1.getId());
        list.add(task1);
        manager.getTaskById(epicTask2.getId());
        list.add(epicTask2);
        List<Task> actualList = new LinkedList<>();
        int length = manager.getHistory().size();
        actualList.add(manager.getHistory().get(length-4));
        actualList.add(manager.getHistory().get(length-3));
        actualList.add(manager.getHistory().get(length-2));
        actualList.add(manager.getHistory().get(length-1));
        assertEquals(list, actualList);
    }
}
