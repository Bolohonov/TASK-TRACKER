package repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tasks.EpicTask;
import tasks.SingleTask;
import tasks.Task;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;


class InMemoryTaskManagerTest implements TaskManagerTest {

    @BeforeEach
    private void clear() {
        manager.removeAllTasks();
        manager.getPrioritizedTasks().clear();
    }

    @Override
    @Test
    public void putTaskStandardBehavior() throws IntersectionException {
        Task task5 = creator.createSingleTask(
                new String[]{"TestName5", "TestDescription5"}, Duration.ofHours(18),
                LocalDateTime.now(ZoneId.of("Europe/Moscow")));
        manager.putTask(task5);
        int id = task5.getId();
        Task epic1 = creator.createEpicTask(new String[]{"TestEpicName1", "TestEpicDescription1"});
        manager.putTask(epic1);
        int id2 = epic1.getId();
        Task subTask = creator.createSubTask(epic1,
                new String[]{"TestEpicName1", "TestEpicDescription1"},
                Duration.ofHours(21), LocalDateTime.now(ZoneId.of("Europe/Moscow")).plusHours(25));
        manager.putTask(subTask);
        int id3 = subTask.getId();
        assertTrue(manager.getSingleTasks().containsKey(id));
        assertTrue(manager.getEpicTasks().containsKey(id2));
        assertTrue(manager.getSubTasksByEpic(epic1).containsKey(id3));

    }


    @Override
    @Test
    public void getTaskByIdStandardBehavior() throws IntersectionException {
        Task task5 = creator.createSingleTask(new String[]{"TestName5", "TestDescription5"},
                Duration.ofHours(18), LocalDateTime.now(ZoneId.of("Europe/Moscow")));
        manager.putTask(task5);
        int id = task5.getId();
        Task epic1 = creator.createEpicTask(new String[]{"TestEpicName1", "TestEpicDescription1"});
        manager.putTask(epic1);
        int id2 = epic1.getId();
        Task subTask = creator.createSubTask(epic1,
                new String[]{"TestEpicName1", "TestEpicDescription1"}, Duration.ofHours(21),
                LocalDateTime.now(ZoneId.of("Europe/Moscow")).plusHours(25));
        manager.putTask(subTask);
        int id3 = subTask.getId();
        assertEquals(task5, managers.getTaskManager().getTaskById(id));
        assertEquals(epic1, managers.getTaskManager().getTaskById(id2));
        assertEquals(subTask, managers.getTaskManager().getTaskById(id3));
    }

    @Override
    public void getSingleTasks() {

    }

    @Override
    public void getEpicTasks() {

    }

    @Override
    public void getSubTasksByEpic() {

    }

    @Override
    public void updateTask() {

    }

    @Override
    public void removeAllTasks() {

    }

    @Override
    public void removeTaskById() {

    }

    @Override
    public void getHistory() {

    }

    @Override
    @Test
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
        SingleTask task1 = creator.createSingleTask(
                new String[]{"TestName", "TestDescription"},
                Duration.ofHours(8), LocalDateTime.now(ZoneId.of("Europe/Moscow")));
        manager.putTask(task1);
        SingleTask task2 = creator.createSingleTask(
                new String[]{"TestName2", "TestDescription"},
                Duration.ofHours(9), LocalDateTime.now(ZoneId.of("Europe/Moscow")).plusHours(10));
        manager.putTask(task2);
        SingleTask task3 = creator.createSingleTask(
                new String[]{"TestName3", "TestDescription3"},
                Duration.ofHours(9), LocalDateTime.now(ZoneId.of("Europe/Moscow")).plusHours(12));
        manager.putTask(task3);

        prioritizedTasksTest.add(task1);
        prioritizedTasksTest.add(task2);
        prioritizedTasksTest.add(task3);

        assertEquals(prioritizedTasksTest, managers.getTaskManager().getPrioritizedTasks());
    }
}
