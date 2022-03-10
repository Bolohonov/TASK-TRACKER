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

class InMemoryHistoryManagerTest {

    Managers managers = new Managers();
    TaskCreator creator = managers.getFactory();
    HistoryManager historyManager = new InMemoryHistoryManager();

    List<Task> list = new LinkedList<>();
    EpicTask epicTask1 = creator.createEpicTask(
            new String[]{"TestName", "TestDescription"});
    EpicTask epicTask2 = creator.createEpicTask(
            new String[]{"TestName2", "TestDescription"});
    EpicTask epicTask3 = creator.createEpicTask(
            new String[]{"TestName3", "TestDescription3"});
    SubTask subTask = creator.createSubTask(epicTask1,
            new String[]{"TestNameSub1", "TestDescriptionSub1"},
            Duration.ofHours(2), LocalDateTime.now(ZoneId.of("Europe/Moscow")));
    SingleTask task1 = creator.createSingleTask(
            new String[]{"TestName", "TestDescription"},
            Duration.ofHours(2), LocalDateTime.now(ZoneId.of("Europe/Moscow")).plusHours(3));

    InMemoryHistoryManagerTest() throws IntersectionException {
    }

    @BeforeEach
    private void clear() {
        historyManager.clearHistory();
    }

    @Test
    void addStandardBehavior() {
        historyManager.add(task1);
        list.add(task1);
        historyManager.add(subTask);
        list.add(subTask);
        historyManager.add(epicTask2);
        list.add(epicTask2);
        historyManager.add(epicTask1);
        list.add(epicTask1);
        assertEquals(list, historyManager.getHistory());
    }

    @Test
    void addEmptyHistory() {
        historyManager.add(task1);
        historyManager.remove(task1.getId());
        assertEquals(null, historyManager.getHistory());
    }

    @Test
    void addDuplication() {
        historyManager.add(task1);
        list.add(task1);
        historyManager.add(subTask);
        list.add(subTask);
        historyManager.add(epicTask2);
        list.add(epicTask2);
        historyManager.add(epicTask1);
        list.add(epicTask1);
        historyManager.add(subTask);
        list.remove(subTask);
        list.add(subTask);
        historyManager.add(task1);
        list.remove(task1);
        list.add(task1);
        historyManager.add(epicTask2);
        list.remove(epicTask2);
        list.add(epicTask2);
        assertEquals(list, historyManager.getHistory());
    }

    @Test
    void removeStandardBehavior() {
        historyManager.add(task1);
        list.add(task1);
        historyManager.add(subTask);
        list.add(subTask);
        historyManager.add(epicTask2);
        list.add(epicTask2);
        historyManager.add(epicTask1);
        list.add(epicTask1);
        historyManager.add(subTask);
        list.remove(subTask);
        list.add(subTask);
        historyManager.remove(task1.getId());
        list.remove(task1);
        historyManager.remove(subTask.getId());
        list.remove(subTask);
        assertEquals(list, historyManager.getHistory());
    }

    @Test
    void removeDuplication() {
        historyManager.add(task1);
        list.add(task1);
        historyManager.add(subTask);
        list.add(subTask);
        historyManager.add(epicTask2);
        list.add(epicTask2);
        historyManager.add(epicTask1);
        list.add(epicTask1);
        historyManager.add(subTask);
        list.remove(subTask);
        list.add(subTask);
        historyManager.add(task1);
        list.remove(task1);
        list.add(task1);
        historyManager.add(epicTask2);
        list.remove(epicTask2);
        list.add(epicTask2);
        historyManager.remove(epicTask2.getId());
        list.remove(epicTask2);
        historyManager.remove(subTask.getId());
        list.remove(subTask);
        assertEquals(list, historyManager.getHistory());
    }

    @Test
    void removeWrongId() {
        historyManager.add(epicTask2);
        historyManager.add(epicTask1);
        historyManager.add(epicTask3);
        final NoSuchElementException exception = assertThrows(
                NoSuchElementException.class,
                () -> historyManager.remove(task1.getId())
        );
        assertEquals("В истории отсутствует задача с таким ID!",
                exception.getMessage());
    }

    @Test
    void getEmptyHistory() {
        assertEquals(null, historyManager.getHistory());
    }

    @Test
    void geHistoryStandardBehavior() {
        historyManager.add(task1);
        list.add(task1);
        historyManager.add(subTask);
        list.add(subTask);
        historyManager.add(epicTask2);
        list.add(epicTask2);
        historyManager.add(epicTask1);
        list.add(epicTask1);
        assertEquals(list, historyManager.getHistory());
    }

    @Test
    void clearHistory() {
    }
}