package repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tasks.EpicTask;
import tasks.SingleTask;
import tasks.SubTask;
import tasks.Task;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;


class InMemoryTasksManagerTest implements TaskManagerTest {

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

    @BeforeEach
    private void clear() {
        manager.removeAllTasks();
        manager.getPrioritizedTasks().clear();
    }

    @Override
    @Test
    public void putTaskStandardBehavior() throws IntersectionException {
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
        Task task5 = creator.createSingleTask(new String[]{"TestName5", "TestDescription5"},
                Duration.ofHours(18), LocalDateTime.now(ZoneId.of("Europe/Moscow")));
        manager.putTask(task5);
        Task epic1 = creator.createEpicTask(new String[]{"TestEpicName1", "TestEpicDescription1"});
        manager.putTask(epic1);
        Task subTask = creator.createSubTask(epic1,
                new String[]{"TestEpicName1", "TestEpicDescription1"}, Duration.ofHours(21),
                LocalDateTime.now(ZoneId.of("Europe/Moscow")).plusHours(25));
        manager.putTask(subTask);
        int id1 = 4;
        int id2 = 5;
        int id3 = 0;
        assertEquals(null, managers.getTaskManager().getTaskById(id1));
        assertEquals(null, managers.getTaskManager().getTaskById(id2));
        assertEquals(null, managers.getTaskManager().getTaskById(id3));
    }

    @Test
    @Override
    public void getSingleTasksStandardBehavior() throws IntersectionException {
        Repository<SingleTask> singleTaskRepository = new Repository<>();
        SingleTask task1 = creator.createSingleTask(
                new String[]{"TestName", "TestDescription"},
                Duration.ofHours(8), LocalDateTime.now(ZoneId.of("Europe/Moscow")));
        manager.putTask(task1);
        SingleTask task2 = creator.createSingleTask(
                new String[]{"TestName2", "TestDescription"},
                Duration.ofHours(1), LocalDateTime.now(ZoneId.of("Europe/Moscow")).plusHours(3));
        manager.putTask(task2);
        SingleTask task3 = creator.createSingleTask(
                new String[]{"TestName3", "TestDescription3"},
                Duration.ofHours(2), LocalDateTime.now(ZoneId.of("Europe/Moscow")).plusHours(5));
        manager.putTask(task3);
        singleTaskRepository.putTask(task1);
        singleTaskRepository.putTask(task2);
        singleTaskRepository.putTask(task3);
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
        EpicTask task1 = creator.createEpicTask(
                new String[]{"TestName", "TestDescription"});
        manager.putTask(task1);
        EpicTask task2 = creator.createEpicTask(
                new String[]{"TestName2", "TestDescription"});
        manager.putTask(task2);
        EpicTask task3 = creator.createEpicTask(
                new String[]{"TestName3", "TestDescription3"});
        manager.putTask(task3);
        SubTask task4 = creator.createSubTask(task1,
                new String[]{"TestNameSub1", "TestDescriptionSub1"},
                Duration.ofHours(8), LocalDateTime.now(ZoneId.of("Europe/Moscow")));

        epicTaskRepository.putTask(task1);
        epicTaskRepository.putTask(task2);
        epicTaskRepository.putTask(task3);
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
        Map<Integer, SubTask> subTasksMap1 = new LinkedHashMap<>();
        Map<Integer, SubTask> subTasksMap2 = new LinkedHashMap<>();
        EpicTask task1 = creator.createEpicTask(
                new String[]{"TestName", "TestDescription"});
        manager.putTask(task1);
        EpicTask task2 = creator.createEpicTask(
                new String[]{"TestName2", "TestDescription"});
        manager.putTask(task2);
        EpicTask task3 = creator.createEpicTask(
                new String[]{"TestName3", "TestDescription3"});
        manager.putTask(task3);
        SubTask subTask1 = creator.createSubTask(task1,
                new String[]{"TestNameSub1", "TestDescriptionSub1"},
                Duration.ofHours(1), LocalDateTime.now(ZoneId.of("Europe/Moscow")).plusHours(2));
        subTasksMap1.put(subTask1.getId(), subTask1);
        SubTask subTask2 = creator.createSubTask(task1,
                new String[]{"TestNameSub1", "TestDescriptionSub1"},
                Duration.ofHours(3), LocalDateTime.now(ZoneId.of("Europe/Moscow")).plusHours(2));
        subTasksMap1.put(subTask2.getId(), subTask2);
        SubTask subTask3 = creator.createSubTask(task2,
                new String[]{"TestNameSub1", "TestDescriptionSub1"},
                Duration.ofHours(3), LocalDateTime.now(ZoneId.of("Europe/Moscow")).plusHours(4));
        SubTask subTask4 = creator.createSubTask(task3,
                new String[]{"TestNameSub1", "TestDescriptionSub1"},
                Duration.ofHours(3), LocalDateTime.now(ZoneId.of("Europe/Moscow")).plusHours(8));
        subTasksMap2.put(subTask3.getId(), subTask3);
        assertEquals(subTasksMap1, task1.getSubTasks());
        assertEquals(subTasksMap2, task2.getSubTasks());
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
    public void removeAllTasks() throws IntersectionException {
        Repository<EpicTask> epicTaskRepository = new Repository<>();
        Repository<SingleTask> singleTaskRepository = new Repository<>();
        EpicTask epicTask1 = creator.createEpicTask(
                new String[]{"TestName", "TestDescription"});
        manager.putTask(epicTask1);
        EpicTask epicTask2 = creator.createEpicTask(
                new String[]{"TestName2", "TestDescription"});
        manager.putTask(epicTask2);
        EpicTask epicTask3 = creator.createEpicTask(
                new String[]{"TestName3", "TestDescription3"});
        manager.putTask(epicTask3);
        SubTask task4 = creator.createSubTask(epicTask1,
                new String[]{"TestNameSub1", "TestDescriptionSub1"},
                Duration.ofHours(2), LocalDateTime.now(ZoneId.of("Europe/Moscow")));
        SingleTask task1 = creator.createSingleTask(
                new String[]{"TestName", "TestDescription"},
                Duration.ofHours(2), LocalDateTime.now(ZoneId.of("Europe/Moscow")).plusHours(3));
        manager.putTask(task1);
        SingleTask task2 = creator.createSingleTask(
                new String[]{"TestName2", "TestDescription"},
                Duration.ofHours(1), LocalDateTime.now(ZoneId.of("Europe/Moscow")).plusHours(10));
        manager.putTask(task2);
        SingleTask task3 = creator.createSingleTask(
                new String[]{"TestName3", "TestDescription3"},
                Duration.ofHours(1), LocalDateTime.now(ZoneId.of("Europe/Moscow")).plusHours(12));
        manager.putTask(task3);
        epicTaskRepository.putTask(epicTask1);
        epicTaskRepository.putTask(epicTask2);
        epicTaskRepository.putTask(epicTask3);
        singleTaskRepository.putTask(task1);
        singleTaskRepository.putTask(task2);
        singleTaskRepository.putTask(task3);
        manager.removeAllTasks();
        singleTaskRepository.removeAllTasks();
        epicTaskRepository.removeAllTasks();
        assertEquals(singleTaskRepository.getTasks(), manager.getSingleTasks());
        assertEquals(epicTaskRepository.getTasks(), manager.getEpicTasks());
    }

    @Override
    @Test
    public void removeTaskByIdStandardBehavior() throws IntersectionException {
        EpicTask epicTask1 = creator.createEpicTask(
                new String[]{"TestName", "TestDescription"});
        manager.putTask(epicTask1);
        SingleTask task1 = creator.createSingleTask(
                new String[]{"TestName", "TestDescription"},
                Duration.ofHours(1), LocalDateTime.now(ZoneId.of("Europe/Moscow")));
        manager.putTask(task1);
        SubTask subTask1 = creator.createSubTask(epicTask1,
                new String[]{"TestNameSub1", "TestDescriptionSub1"},
                Duration.ofHours(1), LocalDateTime.now(ZoneId.of("Europe/Moscow")).plusHours(2));
        int epicTaskId = epicTask1.getId();
        int taskId = task1.getId();
        int subTaskId = epicTask1.getSubTasks().get(subTask1.getId()).getId();
        manager.removeTaskById(subTaskId);
        assertFalse(epicTask1.getSubTasks().containsKey(subTaskId));
        manager.removeTaskById(epicTaskId);
        manager.removeTaskById(taskId);
        assertFalse(manager.getSingleTasks().containsKey(taskId));
        assertFalse(manager.getEpicTasks().containsKey(epicTaskId));

    }

    @Override
    @Test
    public void getHistoryStandardBehavior() throws IntersectionException {
        List<Task> historyList = new LinkedList<>();
        EpicTask epicTask1 = creator.createEpicTask(
                new String[]{"TestName", "TestDescription"});
        manager.putTask(epicTask1);
        EpicTask epicTask2 = creator.createEpicTask(
                new String[]{"TestName2", "TestDescription"});
        manager.putTask(epicTask2);
        EpicTask epicTask3 = creator.createEpicTask(
                new String[]{"TestName3", "TestDescription3"});
        manager.putTask(epicTask3);
        SubTask task4 = creator.createSubTask(epicTask1,
                new String[]{"TestNameSub1", "TestDescriptionSub1"},
                Duration.ofHours(2), LocalDateTime.now(ZoneId.of("Europe/Moscow")));
        SingleTask task1 = creator.createSingleTask(
                new String[]{"TestName", "TestDescription"},
                Duration.ofHours(2), LocalDateTime.now(ZoneId.of("Europe/Moscow")).plusHours(3));
        manager.putTask(task1);
        SingleTask task2 = creator.createSingleTask(
                new String[]{"TestName2", "TestDescription"},
                Duration.ofHours(1), LocalDateTime.now(ZoneId.of("Europe/Moscow")).plusHours(10));
        manager.putTask(task2);
        SingleTask task3 = creator.createSingleTask(
                new String[]{"TestName3", "TestDescription3"},
                Duration.ofHours(1), LocalDateTime.now(ZoneId.of("Europe/Moscow")).plusHours(13));
        manager.putTask(task3);
        manager.getTaskById(task3.getId());
        historyList.add(task3);
        manager.getTaskById(task2.getId());
        historyList.add(task2);
        manager.getTaskById(task1.getId());
        historyList.add(task1);
        manager.getTaskById(task4.getId());
        historyList.add(task4);
        manager.getTaskById(epicTask1.getId());
        historyList.add(epicTask1);
        manager.getTaskById(epicTask2.getId());
        historyList.add(epicTask2);
        manager.getTaskById(epicTask3.getId());
        historyList.add(epicTask3);
        assertEquals(historyList, manager.getHistory());

    }

    @Test
    @Override
    public void shouldGetPrioritizedTasks() throws IntersectionException {
        Set<Task> prioritizedTasksTest = new TreeSet<>((o1, o2) -> {
            if (!o1.getStartTime().isPresent()) {
                return 1;
            } else if (!o2.getStartTime().isPresent()) {
                return -1;
            } else {
                return o1.getStartTime().get().compareTo(o2.getStartTime().get());
            }
        });
        manager.putTask(epicTask3);
        prioritizedTasksTest.add(epicTask3);
        System.out.println(prioritizedTasksTest.contains(epicTask3));

        assertEquals(prioritizedTasksTest, managers.getTaskManager().getPrioritizedTasks());
    }
}
