package repository;

import tasks.EpicTask;
import tasks.SubTask;
import tasks.Task;

import java.util.*;

public class InMemoryHistoryManager implements HistoryManager {

    @Override
    public void add(Task task) {
        if (task != null) {
            LinkedUniqueList.linkLast(task);
        }
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

    static final class LinkedUniqueList {
        private static final Map<Integer, Node> historyMap = new HashMap();
        private static Node last;
        private static Node first;


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

        private static void linkLast(Task task) {
            Node l = last;
            Node newNode = new Node(task, l, null);
            last = newNode;
            if (l == null) {
                first = newNode;
            } else {
                l.setNextNode(newNode);
            }
            LinkedUniqueList.addTask(task, newNode);
        }

        private static void addTask(Task task, Node node) {
            int id = task.getId();
            if (historyMap.containsKey(id)) {
                removeNode(historyMap.get(id));
            }
            historyMap.put(id, node);
        }

        private static ArrayList<Task> getTasks() {
            ArrayList<Task> historyArrayList = new ArrayList<>();
            historyArrayList.clear();
            if (historyMap == null || historyMap.isEmpty()) {
                System.out.println("Истории пока нет");
                return null;
            } else {
                Node node = first;
                for (int i = 0; i < size; i++) {
                    historyArrayList.add(i, node.getTask());
                    node = node.getNextNode();
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
                if (historyMap.get(id).getTask() instanceof EpicTask) {

                }
//            try {
//                EpicTask epic = (EpicTask) historyMap.get(id).getTask();
//                Map<Integer, SubTask> subTaskMap = epic.getSubTasksMap();
//                for (Integer subTaskId : subTaskMap.keySet()) {
//                    if (historyMap.containsKey(subTaskId)) {
//                        removeNode(historyMap.get(subTaskId));
//                    }
//                }
//            } catch (ClassCastException exp) {
//            }
                removeNode(historyMap.get(id));
            } else {
                System.out.println("В истории отсутствует задача с таким ID!");
            }
        }

        private static void clear() {
            historyMap.clear();
        }
    }
}

