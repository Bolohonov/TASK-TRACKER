package repository;

import org.junit.jupiter.api.Test;
import tasks.EpicTask;
import tasks.SingleTask;
import tasks.Task;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;


class InMemoryTaskManagerTest implements TaskManagerTest{

    Managers manager = new Managers();
    TaskManager m = manager.getTaskManager();

    @Override
    public void putTaskStandardBehavior() {

    }

    @Override
    public void getTaskById() {

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
    public void shouldGetPrioritizedTasks() {
        Set<Task> prioritizedTasksTest = new TreeSet<>((o1, o2) -> {
            if (!o1.getStartTime().isPresent()) {
                return -1;
            } else if (!o2.getStartTime().isPresent()) {
                return 1;
            } else {
                return o1.getStartTime().get().compareTo(o2.getStartTime().get());
            }
        });
        SingleTask task1 = new SingleTask("TestName",
                "TestDescription", InMemoryTasksManager.getId(),
                Optional.of(Duration.ofHours(8)), Optional.of(LocalDateTime.now(ZoneId.of("Europe/Moscow"))));
        SingleTask task2 = new SingleTask("TestName2",
                "TestDescription", InMemoryTasksManager.getId(),
                Optional.of(Duration.ofHours(9)),
                Optional.of(LocalDateTime.now(ZoneId.of("Europe/Moscow")).plusHours(15)));
        SingleTask task3 = new SingleTask("TestName3",
                "TestDescription", InMemoryTasksManager.getId(),
                Optional.of(Duration.ofHours(10)),
                Optional.of(LocalDateTime.now(ZoneId.of("Europe/Moscow")).plusHours(2)));
        EpicTask epic1 = new EpicTask("TestEpicName",
                "TestEpicDescription", InMemoryTasksManager.getId());
        EpicTask epic2 = new EpicTask("TestEpicName",
                "TestEpicDescription", InMemoryTasksManager.getId());
        SingleTask task4 = new SingleTask("TestName3",
                "TestDescription", InMemoryTasksManager.getId(),
                Optional.empty(),
                Optional.empty());

        m.putTask(task1);
        m.putTask(epic1);
        m.putTask(task2);
        m.putTask(task3);
        m.putTask(epic2);
        m.putTask(task4);
        prioritizedTasksTest.add(task1);
        prioritizedTasksTest.add(epic1);
        prioritizedTasksTest.add(task2);
        prioritizedTasksTest.add(task3);
        prioritizedTasksTest.add(task4);
        prioritizedTasksTest.add(epic2);

        manager.getTaskManager().getPrioritizedTasks().forEach(System.out::println);
        System.out.println("--------");
        prioritizedTasksTest.forEach(System.out::println);
        assertEquals(prioritizedTasksTest, manager.getTaskManager().getPrioritizedTasks());

    }
}
