package repository;

import org.junit.jupiter.api.Test;
import tasks.EpicTask;
import tasks.SingleTask;
import tasks.SubTask;
import tasks.Task;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryHistoryManagerTest {

    Managers managers = new Managers();
    TaskManager manager = managers.getTaskManager();
    TaskCreator creator = managers.getFactory();
    private final LinkedUniqueList taskManagerHistory = new LinkedUniqueList();

    @Test
    void addEmptyHistory() throws IntersectionException {
        EpicTask epicTask1 = creator.createEpicTask(
                new String[]{"TestName", "TestDescription"});
        taskManagerHistory.linkLast(epicTask1);
        taskManagerHistory.addTask(epicTask1);
        EpicTask epicTask2 = creator.createEpicTask(
                new String[]{"TestName2", "TestDescription"});
        taskManagerHistory.linkLast(epicTask2);
        taskManagerHistory.addTask(epicTask2);
        EpicTask epicTask3 = creator.createEpicTask(
                new String[]{"TestName3", "TestDescription3"});
        taskManagerHistory.linkLast(epicTask3);
        taskManagerHistory.addTask(epicTask3);
        SubTask task4 = creator.createSubTask(epicTask1,
                new String[]{"TestNameSub1", "TestDescriptionSub1"},
                Duration.ofHours(2), LocalDateTime.now(ZoneId.of("Europe/Moscow")));
        taskManagerHistory.linkLast(task4);
        taskManagerHistory.addTask(task4);
        SingleTask task1 = creator.createSingleTask(
                new String[]{"TestName", "TestDescription"},
                Duration.ofHours(2), LocalDateTime.now(ZoneId.of("Europe/Moscow")).plusHours(3));
        taskManagerHistory.linkLast(task1);
        taskManagerHistory.addTask(task1);
    }

    @Test
    void addDuplication() {
    }

    @Test
    void remove() {
    }

    @Test
    void getHistory() {
    }

    @Test
    void clearHistory() {
    }

    private static class LinkedUniqueList {
        private Map<Integer, Node> historyMap = new HashMap();
        private Node last;
        private Node first;


        private void linkLast(Task task) {
            if (task != null) {
                Node l = last;
                Node newNode = new Node(task, l, null);
                last = newNode;
                if (l == null) {
                    first = newNode;
                } else {
                    l.setNextNode(newNode);
                }
            }
        }

        private void addTask(Task task) {
            int id = task.getId();
            if (historyMap.containsKey(id)) {
                removeNode(historyMap.get(id));
            }
            historyMap.put(id, last);
        }

        private void removeNode(Node node) {
            if (!node.equals(first) && !node.equals(last)) {
                Node prevNode = node.getPrevNode();
                Node nextNode = node.getNextNode();
                prevNode.setNextNode(nextNode);
                nextNode.setPrevNode(prevNode);
                historyMap.remove(node.getTask().getId());
            } else if (node.equals(first) && node.getNextNode() != null) {
                Node nextNode = node.getNextNode();
                nextNode.setPrevNode(null);
                first = nextNode;
                historyMap.remove(node.getTask().getId());
            } else if (node.equals(last) && node.getPrevNode() != null) {
                Node prevNode = node.getPrevNode();
                prevNode.setNextNode(null);
                last = prevNode;
                historyMap.remove(node.getTask().getId());
            } else {
                last = null;
                first = null;
                historyMap.remove(node.getTask().getId());
            }
            unlink(node);
        }

        private ArrayList<Task> getTasks() {
            ArrayList<Task> historyArrayList = new ArrayList<>(historyMap.size());
            if (historyMap == null || historyMap.isEmpty()) {
                return null;
            } else {
                Node node = first;
                int i = 0;
                while (node != null) {
                    historyArrayList.add(i, node.getTask());
                    node = node.getNextNode();
                    i++;
                }
            }
            return historyArrayList;
        }

        private void unlink(Node node) {
            node.setNextNode(null);
            node.setPrevNode(null);
            node.setTask(null);
        }

        private void remove(int id) {
            if (historyMap.containsKey(id)) {
                removeNode(historyMap.get(id));
            } else {
                System.out.println("В истории отсутствует задача с таким ID!");
            }
        }

        private void clear() {
            historyMap.clear();
        }
    }
}