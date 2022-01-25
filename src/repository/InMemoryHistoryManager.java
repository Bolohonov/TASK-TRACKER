package repository;

import tasks.Task;

import java.util.*;

public class InMemoryHistoryManager implements HistoryManager {

    private static final List<Task> history = new LinkedList<>();
    private static final Map<Integer, Node> historyMap = new HashMap();

    Node last;
    Node first;
    int size = 0;


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

    }

    private void linkLast(Task task) {
        int id = task.getId();
        if (historyMap.containsKey(id)) {
            removeNode(historyMap.get(id));
        }
        final Node l = last;
        final Node newNode = new Node(l, task, null);
        last = newNode;
        if (l == null) {
            first = newNode;
            historyMap.put(id, newNode);
        } else {
            historyMap.put(id, newNode);
            l.setNext(newNode);
        }
    }

    private ArrayList<Task> getTasks() {
        ArrayList<Task> historyList = new ArrayList<>();
        if (historyMap == null || historyMap.isEmpty()) {
            System.out.println("Истории пока нет");
            return null;
        } else {
            Node node = first;
            int i = 0;
            historyList.add(i, node.getTask());
            for (Map.Entry entry : historyMap.entrySet()) {
                if (!node.equals(last) && entry.getKey().equals(node.getNext().getTask().getId())) {
                    node = node.getNext();
                    ++i;
                    historyList.add(i, node.getTask());
                } else {
                    break;
                }
            }
        }
        return historyList;
    }

    private void removeNode(Node node) {
        if (!node.equals(first)) {
            Node prevNode = node.getPrev();
            Node nextNode = node.getNext();
            prevNode = new Node(prevNode.getPrev(), prevNode.getTask(), nextNode);
            nextNode = new Node(prevNode, nextNode.getTask(), nextNode.getNext());
            historyMap.remove(node);
            size--;
        } else {
            historyMap.remove(node);
            size--;
        }
    }
}
