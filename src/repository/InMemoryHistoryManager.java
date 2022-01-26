package repository;

import tasks.EpicTask;
import tasks.SubTask;
import tasks.Task;

import java.util.*;

public class InMemoryHistoryManager implements HistoryManager {

    private static final Map<Integer, Node> historyMap = new HashMap();
    private static final LinkedList<Node> historyList = new LinkedList<>();

    private Node last;
    private Node first;
    private int size = 0;

    @Override
    public void add(Task task) {
        if (task != null) {
            linkLast(task);
        }
        size++;
    }

    @Override
    public void remove(int id) {
        if (historyMap.containsKey(id)) {
            try {
                EpicTask epic = (EpicTask) historyMap.get(id).getTask();
                Map<Integer, SubTask> subTaskMap = epic.getSubTasksMap();
                for (Integer subTaskId : subTaskMap.keySet()) {
                    if (historyMap.containsKey(subTaskId)) {
                        removeNode(historyMap.get(subTaskId));
                    }
                }
            } catch (ClassCastException exp) {
            }
            removeNode(historyMap.get(id));
        } else {
            System.out.println("В истории отсутствует задача с таким ID!");
        }
    }

    @Override
    public List<Task> getHistory() {
        return getTasks();
    }

    @Override
    public void clearHistory() {
        historyMap.clear();
        historyList.clear();
    }

    private void linkLast(Task task) {
        int id = task.getId();
        if (historyMap.containsKey(id)) {
            removeNode(historyMap.get(id));
        }
        Node l = last;
        Node newNode = new Node(task, l, null);
        last = newNode;
        if (l == null) {
            first = newNode;
            historyMap.put(id, newNode);
            historyList.addFirst(newNode);
        } else {
            l.setNextNode(newNode);
            historyMap.put(id, newNode);
            historyList.addLast(newNode);
        }
    }

    private ArrayList<Task> getTasks() {
        ArrayList<Task> historyArrayList = new ArrayList<>();
        historyArrayList.clear();
        if (historyList == null || historyList.isEmpty()) {
            System.out.println("Истории пока нет");
            return null;
        } else {
            //int iter = 0;
            Node node = first;
            for (int i = 0; i < size; i++) {
                historyArrayList.add(i, node.getTask());
                node = node.getNextNode();
            }
        }
        return historyArrayList;
    }

    private void removeNode(Node node) {
        if (!node.equals(first) && !node.equals(last)) {
            Node prevNode = node.getPrevNode();
            Node nextNode = node.getNextNode();
            prevNode.setNextNode(nextNode);
            nextNode.setPrevNode(prevNode);
            historyMap.remove(node.getTask().getId());
        } else if (node.equals(first) && historyList.stream().iterator().hasNext()) {
            Node nextNode = node.getNextNode();
            nextNode.setPrevNode(null);
            first = nextNode;
            historyMap.remove(node.getTask().getId());
        } else if (node.equals(last) && historyList.listIterator(size).hasPrevious()) {
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
        size--;
    }

    private void unlink(Node node) {
        node.setNextNode(null);
        node.setPrevNode(null);
        node.setTask(null);
    }
}

