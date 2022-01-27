package repository;

import tasks.EpicTask;
import tasks.SubTask;
import tasks.Task;

import java.util.*;

public class InMemoryHistoryManager implements HistoryManager {

    @Override
    public void add(Task task) {
        LinkedUniqueList.linkLast(task);
        LinkedUniqueList.addTask(task);
    }

    @Override
    public void remove(int id) {
        LinkedUniqueList.remove(id);
    }

    @Override
    public List<Task> getHistory() {
        return LinkedUniqueList.getTasks();
    }

    @Override
    public void clearHistory() {
        LinkedUniqueList.clear();
    }

    private static final class LinkedUniqueList {
        private static final Map<Integer, Node> historyMap = new HashMap();
        private static Node last;
        private static Node first;


        private static void linkLast(Task task) {
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

        private static void addTask(Task task) {
            int id = task.getId();
            if (historyMap.containsKey(id)) {
                checkAndRemoveSubTasks(id);
                removeNode(historyMap.get(id));
            }
            historyMap.put(id, last);
        }

        private static void removeNode(Node node) {
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

        private static ArrayList<Task> getTasks() {
            ArrayList<Task> historyArrayList = new ArrayList<>(historyMap.size());
            if (historyMap == null || historyMap.isEmpty()) {
                System.out.println("Истории пока нет");
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

        private static void unlink(Node node) {
            node.setNextNode(null);
            node.setPrevNode(null);
            node.setTask(null);
        }

        private static void remove(int id) {
            if (historyMap.containsKey(id)) {
                checkAndRemoveSubTasks(id);
                removeNode(historyMap.get(id));
            } else {
                System.out.println("В истории отсутствует задача с таким ID!");
            }
        }

        private static void checkAndRemoveSubTasks(int id) {
            if (historyMap.get(id).getTask() instanceof EpicTask) {
                    /*
                    По замечанию:
                    Нужно получить лист с SubTask с их idшниками, чтобы быстро удалить эти записи из истории.
                    Если удалять историю в момент удаления EpicTask в TaskManager, тогда InMemoryTaskManager
                    будет знать про интерфейс HistoryManager, т.к. там придется его реализацию вызывать,
                    а сейчас они никак не взаимодействуют. Может все же этот код здесь оставить?
                     */
                EpicTask epic = (EpicTask) historyMap.get(id).getTask();
                Map<Integer, SubTask> subTaskMap = epic.getSubTasks();
                for (Integer subTaskId : subTaskMap.keySet()) {
                    if (historyMap.containsKey(subTaskId)) {
                        removeNode(historyMap.get(subTaskId));
                    }
                }
            }
        }

        private static void clear() {
            historyMap.clear();
        }
    }
}

