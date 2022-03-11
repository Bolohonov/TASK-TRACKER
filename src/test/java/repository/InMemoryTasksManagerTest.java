package repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tasks.EpicTask;
import tasks.SingleTask;
import tasks.SubTask;
import tasks.Task;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;


class InMemoryTasksManagerTest implements TaskManagerTest {

    @BeforeEach
    private void clear() {
        manager.removeAllTasks();
        manager.getPrioritizedTasks().clear();
    }

    EpicTask epicTask1 = creator.createEpicTask(
            new String[]{"TestEpicName", "TestDescription"});
    EpicTask epicTask2 = creator.createEpicTask(
            new String[]{"TestEpicName2", "TestDescription"});
    EpicTask epicTask3 = creator.createEpicTask(
            new String[]{"TestEpicName3", "TestDescription3"});
    SubTask subTask1 = creator.createSubTask(epicTask1,
            new String[]{"TestNameSub1", "TestDescriptionSub1"},
            Duration.ofHours(2), LocalDateTime.now(ZoneId.of("Europe/Moscow")).plusHours(1));
    SubTask subTask2 = creator.createSubTask(epicTask1,
            new String[]{"TestNameSub2", "TestDescriptionSub1"},
            Duration.ofHours(2), LocalDateTime.now(ZoneId.of("Europe/Moscow")).plusHours(4));
    SubTask subTask3 = creator.createSubTask(epicTask2,
            new String[]{"TestNameSub3", "TestDescriptionSub1"},
            Duration.ofHours(2), LocalDateTime.now(ZoneId.of("Europe/Moscow")).plusHours(7));
    SingleTask task1 = creator.createSingleTask(
            new String[]{"TestName1", "TestDescription"},
            Duration.ofHours(2), LocalDateTime.now(ZoneId.of("Europe/Moscow")).plusHours(11));
    SingleTask task2 = creator.createSingleTask(
            new String[]{"TestName2", "TestDescription"},
            Duration.ofHours(2), LocalDateTime.now(ZoneId.of("Europe/Moscow")).plusHours(14));

    InMemoryTasksManagerTest() throws IntersectionException {
    }

    @Override
    @Test
    public void putTaskStandardBehavior() throws IntersectionException, IOException {
        manager.putTask(epicTask1);
        manager.putTask(epicTask2);
        manager.putTask(epicTask3);
        manager.putTask(subTask1);
        manager.putTask(subTask2);
        manager.putTask(subTask3);
        manager.putTask(task1);
        manager.putTask(task2);
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
    public void putTaskIntersectionException() throws IntersectionException {
        manager.putTask(epicTask1);
        manager.putTask(subTask2);
        SingleTask taskIntersection = creator.createSingleTask(
                new String[]{"Intersection", "TestDescription"},
                Duration.ofHours(1), LocalDateTime.now(ZoneId.of("Europe/Moscow")).plusHours(4));
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
        manager.putTask(epicTask1);
        manager.putTask(epicTask2);
        manager.putTask(epicTask3);
        manager.putTask(subTask1);
        manager.putTask(subTask2);
        manager.putTask(subTask3);
        manager.putTask(task1);
        manager.putTask(task2);
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
    public void getTaskByIdEmptyRepository() throws IntersectionException {
        int id = 1;
        assertEquals(null, managers.getTaskManager().getTaskById(id));
    }

    @Override
    @Test
    public void getTaskByIdWrongId() throws IntersectionException {
        manager.putTask(epicTask1);
        manager.putTask(task1);
        assertEquals(null, managers.getTaskManager().getTaskById(epicTask1.getId()));
        assertEquals(null, managers.getTaskManager().getTaskById(task1.getId()));
        assertEquals(null, managers.getTaskManager().getTaskById(0));
    }

    @Test
    @Override
    public void getSingleTasksStandardBehavior() throws IntersectionException {
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
    public void getEpicTasksStandardBehavior() throws IntersectionException {
        Repository<EpicTask> epicTaskRepository = new Repository<>();
        manager.putTask(epicTask1);
        manager.putTask(epicTask2);
        manager.putTask(epicTask3);
        manager.putTask(subTask1);
        manager.putTask(subTask2);
        manager.putTask(subTask3);

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
    public void getSubTasksByEpicStandardBehavior() throws IntersectionException {
        Map<Integer, Task> subTasksMap1 = new LinkedHashMap();
        Map<Integer, Task> subTasksMap2 = new LinkedHashMap();
        Map<Integer, Task> subTasksMap3 = new LinkedHashMap();
        manager.putTask(epicTask1);
        manager.putTask(epicTask2);
        manager.putTask(epicTask3);
        manager.putTask(subTask1);
        subTasksMap1.put(subTask1.getId(), subTask1);
        manager.putTask(subTask2);
        subTasksMap1.put(subTask2.getId(), subTask2);
        manager.putTask(subTask3);
        subTasksMap2.put(subTask3.getId(), subTask3);
        assertEquals(subTasksMap1, epicTask1.getSubTasks());
        assertEquals(subTasksMap2, epicTask2.getSubTasks());
        assertEquals(subTasksMap3, epicTask3.getSubTasks());
    }

    @Test
    @Override
    public void getSubTasksByEpicEmptyRepository() throws IntersectionException {
        Map<Integer, SubTask> subTasksMap1 = new LinkedHashMap<>();
        EpicTask task1 = creator.createEpicTask(
                new String[]{"TestName", "TestDescription"});
        manager.putTask(task1);
        assertEquals(subTasksMap1, task1.getSubTasks());
    }

    @Override
    public void updateTask() {

    }

    @Override
    @Test
    public void removeAllTasks() throws IntersectionException {
        Repository<EpicTask> epicTaskRepository = new Repository<>();
        Repository<SingleTask> singleTaskRepository = new Repository<>();
        manager.putTask(epicTask1);
        manager.putTask(epicTask2);
        manager.putTask(epicTask3);
        manager.putTask(subTask1);
        manager.putTask(subTask2);
        manager.putTask(subTask3);
        manager.putTask(task1);
        manager.putTask(task2);
        manager.removeAllTasks();
        assertEquals(singleTaskRepository.getTasks(), manager.getSingleTasks());
        assertEquals(epicTaskRepository.getTasks(), manager.getEpicTasks());
    }

    @Override
    @Test
    public void removeTaskByIdStandardBehavior() throws IntersectionException {
        manager.putTask(epicTask1);
        manager.putTask(epicTask2);
        manager.putTask(epicTask3);
        manager.putTask(subTask1);
        manager.putTask(subTask2);
        manager.putTask(subTask3);
        manager.putTask(task1);
        manager.putTask(task2);
        manager.removeTaskById(epicTask1.getId());
        assertFalse(manager.getEpicTasks().containsKey(epicTask1.getId()));
        manager.removeTaskById(subTask3.getId());
        assertFalse(epicTask2.getSubTasks().containsKey(subTask3.getId()));
        manager.removeTaskById(task1.getId());
        assertFalse(manager.getSingleTasks().containsKey(task1.getId()));
    }

    @Override
    @Test
    public void getHistoryStandardBehavior() throws IntersectionException {
        List<Task> historyList = new LinkedList<>();
        manager.putTask(epicTask1);
        manager.putTask(epicTask2);
        manager.putTask(epicTask3);
        manager.putTask(subTask1);
        manager.putTask(subTask2);
        manager.putTask(subTask3);
        manager.putTask(task1);
        manager.putTask(task2);
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
        assertEquals(historyList, manager.getHistory());

    }

    @Test
    @Override
    public void shouldGetPrioritizedTasks() throws IntersectionException {
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
}
