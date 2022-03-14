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
    TaskManager manager = managers.getTaskManager();
    HistoryManager historyManager = new InMemoryHistoryManager();

    List<Task> list = new LinkedList<>();
    EpicTask epicTask1 = creator.createEpicTask(
            new String[]{"TestEpicName", "TestDescription"});
    EpicTask epicTask2 = creator.createEpicTask(
            new String[]{"TestEpicName2", "TestDescription"});
    EpicTask epicTask3 = creator.createEpicTask(
            new String[]{"TestEpicName3", "TestDescription3"});
    SubTask subTask1 = creator.createSubTask(epicTask1,
            new String[]{"TestNameSub1", "TestDescriptionSub1"},
            Duration.ofHours(2), LocalDateTime.of(2022,03,11, 10,0,00));
    SubTask subTask2 = creator.createSubTask(epicTask1,
            new String[]{"TestNameSub2", "TestDescriptionSub1"},
            Duration.ofHours(2), LocalDateTime.of(2022,03,11, 14,0,00));
    SubTask subTask3 = creator.createSubTask(epicTask2,
            new String[]{"TestNameSub3", "TestDescriptionSub1"},
            Duration.ofHours(2), LocalDateTime.of(2022,03,11, 17,0,00));
    SingleTask task1 = creator.createSingleTask(
            new String[]{"TestName1", "TestDescription"},
            Duration.ofHours(2), LocalDateTime.of(2022,03,11, 21,0,00));
    SingleTask task2 = creator.createSingleTask(
            new String[]{"TestName2", "TestDescription"},
            Duration.ofHours(2), LocalDateTime.of(2022,03,12, 00,0,00));

    private void fillRepository() throws IntersectionException {
        manager.putTask(epicTask1);
        manager.putTask(epicTask2);
        manager.putTask(epicTask3);
        manager.putTask(subTask1);
        manager.putTask(subTask2);
        manager.putTask(subTask3);
        manager.putTask(task1);
        manager.putTask(task2);
    }

    private void fillHistory() throws IntersectionException {
        historyManager.add(epicTask1);
        historyManager.add(epicTask2);
        historyManager.add(epicTask3);
        historyManager.add(subTask1);
        historyManager.add(subTask2);
        historyManager.add(subTask3);
        historyManager.add(task1);
        historyManager.add(task2);
    }

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
        historyManager.add(subTask1);
        list.add(subTask1);
        historyManager.add(subTask2);
        list.add(subTask2);
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
        historyManager.add(epicTask2);
        list.add(epicTask2);
        historyManager.add(epicTask1);
        list.add(epicTask1);
        historyManager.add(subTask1);
        list.add(subTask1);
        historyManager.add(subTask1);
        list.remove(subTask1);
        list.add(subTask1);
        historyManager.add(subTask2);
        list.add(subTask2);
        historyManager.add(subTask3);
        list.add(subTask3);
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
        historyManager.add(subTask1);
        list.add(subTask1);
        historyManager.add(epicTask2);
        list.add(epicTask2);
        historyManager.add(epicTask1);
        list.add(epicTask1);
        historyManager.add(subTask1);
        list.remove(subTask1);
        list.add(subTask1);
        historyManager.remove(task1.getId());
        list.remove(task1);
        historyManager.remove(subTask1.getId());
        list.remove(subTask1);
        assertEquals(list, historyManager.getHistory());
    }

    @Test
    void removeDuplication() {
        historyManager.add(task1);
        list.add(task1);
        historyManager.add(subTask1);
        list.add(subTask1);
        historyManager.add(epicTask2);
        list.add(epicTask2);
        historyManager.add(epicTask1);
        list.add(epicTask1);
        historyManager.add(subTask1);
        list.remove(subTask1);
        list.add(subTask1);
        historyManager.add(task1);
        list.remove(task1);
        list.add(task1);
        historyManager.add(epicTask2);
        list.remove(epicTask2);
        list.add(epicTask2);
        historyManager.remove(epicTask2.getId());
        list.remove(epicTask2);
        historyManager.remove(subTask1.getId());
        list.remove(subTask1);
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
        historyManager.add(subTask1);
        list.add(subTask1);
        historyManager.add(epicTask2);
        list.add(epicTask2);
        historyManager.add(epicTask1);
        list.add(epicTask1);
        assertEquals(list, historyManager.getHistory());
    }

    @Test
    void clearHistoryStandardBehavior() {
        historyManager.add(task1);
        historyManager.add(subTask1);
        historyManager.add(epicTask2);
        historyManager.add(epicTask1);
        historyManager.add(epicTask3);
        historyManager.clearHistory();
        assertEquals(null, historyManager.getHistory());
    }
}