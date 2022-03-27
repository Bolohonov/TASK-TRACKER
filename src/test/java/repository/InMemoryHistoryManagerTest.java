package repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tasks.EpicTask;
import tasks.SingleTask;
import tasks.SubTask;
import tasks.Task;

import java.net.URISyntaxException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryHistoryManagerTest {

    private static Managers managers;

    static {
        try {
            managers = new Managers();
        } catch (ManagerSaveException e) {
            e.printStackTrace();
        }  catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private static final TaskCreator creator = managers.getFactory();
    private static final TaskManager manager = managers.getInMemoryTasksManager();
    private HistoryManager historyManager = new InMemoryHistoryManager();
    private static final List<Task> list = new LinkedList<>();

    EpicTask epicTask1 = creator.createEpicTask(
            new String[]{"TestEpicName", "TestDescription"});
    EpicTask epicTask2 = creator.createEpicTask(
            new String[]{"TestEpicName2", "TestDescription"});
    EpicTask epicTask3 = creator.createEpicTask(
            new String[]{"TestEpicName3", "TestDescription3"});
    SubTask subTask1 = creator.createSubTask(epicTask1,
            new String[]{"TestNameSub1", "TestDescriptionSub1"},
            Duration.ofHours(2), LocalDateTime
                    .of(2022, 03, 7, 10, 0, 00));
    SubTask subTask2 = creator.createSubTask(epicTask1,
            new String[]{"TestNameSub2", "TestDescriptionSub1"},
            Duration.ofHours(2), LocalDateTime
                    .of(2022, 03, 8, 14, 0, 00));
    SubTask subTask3 = creator.createSubTask(epicTask2,
            new String[]{"TestNameSub3", "TestDescriptionSub1"},
            Duration.ofHours(2), LocalDateTime
                    .of(2022, 03, 8, 17, 0, 00));
    SingleTask task1 = creator.createSingleTask(
            new String[]{"TestName1", "TestDescription"},
            Duration.ofHours(2), LocalDateTime
                    .of(2022, 03, 11, 21, 0, 00));
    SingleTask task2 = creator.createSingleTask(
            new String[]{"TestName2", "TestDescription"},
            Duration.ofHours(2), LocalDateTime
                    .of(2022, 03, 12, 00, 0, 00));

    private void fillLinkedList() {
        list.add(epicTask1);
        list.add(epicTask2);
        list.add(epicTask3);
        list.add(subTask1);
        list.add(subTask2);
        list.add(subTask3);
        list.add(task1);
        list.add(task2);
    }


    private void fillHistory() {
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
        manager.getPrioritizedTasks().clear();
        historyManager.clearHistory();
        list.clear();
    }

    @Test
    void addStandardBehavior() {
        fillHistory();
        fillLinkedList();
        assertEquals(list, historyManager.getHistory());
    }

    @Test
    void addEmptyHistory() {
        historyManager.add(task1);
        historyManager.remove(task1.getId());
        List<Task> list = new ArrayList();
        assertEquals(list, historyManager.getHistory());
    }

    @Test
    void addDuplication() {
        fillHistory();
        fillLinkedList();
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
        fillHistory();
        fillLinkedList();
        list.remove(subTask1);
        list.add(subTask1);
        historyManager.remove(task1.getId());
        list.remove(task1);
        historyManager.remove(subTask1.getId());
        list.remove(subTask1);
        assertEquals(list, historyManager.getHistory());
    }

    @Test
    void removeFirstElement() {
        fillHistory();
        fillLinkedList();
        list.remove(epicTask1);
        historyManager.remove(epicTask1.getId());
        assertEquals(list, historyManager.getHistory());
    }

    @Test
    void removeLastElement() {
        fillHistory();
        fillLinkedList();
        list.remove(task2);
        historyManager.remove(task2.getId());
        assertEquals(list, historyManager.getHistory());
    }

    @Test
    void removeHalfElement() {
        fillHistory();
        fillLinkedList();
        list.remove(subTask2);
        historyManager.remove(subTask2.getId());
        assertEquals(list, historyManager.getHistory());
    }

    @Test
    void removeDuplication() {
        fillHistory();
        fillLinkedList();
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
    void getEmptyHistory() {
        List<Task> list = new ArrayList();
        assertEquals(list, historyManager.getHistory());
    }

    @Test
    void getHistoryStandardBehavior() {
        fillHistory();
        fillLinkedList();
        assertEquals(list, historyManager.getHistory());
    }

    @Test
    void clearHistoryStandardBehavior() {
        fillHistory();
        historyManager.clearHistory();
        List<Task> list = new ArrayList();
        assertEquals(list, historyManager.getHistory());
    }
}