package repository;

import tasks.Task;

import java.util.*;

public class InMemoryHistoryManager implements HistoryManager {

    private static final Map<Integer, Node> historyMap = new HashMap();
    private static final LinkedList<Node> historyList = new LinkedList<>();
    Node last;
    Node first;
    private int index = 0;

    @Override
    public void add(Task task) {
        if (task != null) {
            linkLast(task, index);
        }
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

    private void linkLast(Task task, int index) {
        int id = task.getId();
        if (historyMap.containsKey(id)) {
            removeNode(historyMap.get(id));
        }
        Node l = last;
        Node newNode = new Node(index, l, task, null);
        last = newNode;
        if (l == null) {
            first = newNode;
            historyMap.put(id, newNode);
            historyList.addFirst(newNode);
            ++index;
        } else {
            historyMap.put(id, newNode);
            l.setNext(newNode);
            historyList.addLast(newNode);
            ++index;
        }
    }

    private ArrayList<Task> getTasks() {
        ArrayList<Task> historyArrayList = new ArrayList<>();
        LinkedList<Node> nodes = new LinkedList<>(historyList);
        if (historyList == null || historyList.isEmpty()) {
            System.out.println("Истории пока нет");
            return null;
        } else {
            for (int i = 0; i < nodes.size() - 1; i++) {
                historyArrayList.add(i, nodes.getFirst().getTask());
                nodes.removeFirst();
            }
        }
        return historyArrayList;
    }

    private void removeNode(Node node) {
        if (!node.equals(first) && !node.equals(last)) {
            int i = node.getIndex();
            Node prevNode = node.getPrev();
            Node nextNode = node.getNext();
            prevNode.setNext(nextNode);
            prevNode.setIndex(node.getIndex()-1);
            nextNode.setPrev(prevNode);
            for (Node n : historyMap.values()) {
                int currInd = n.getIndex();
                if (currInd >= i) {
                    n.setIndex(currInd - 1);
                }
            }
            ListIterator<> listIterator(i) = new ListIterator<>();
            historyList.listIterator(i);

            historyMap.remove(node.getTaskId());
            historyList.remove(i);
        } else  if (node.equals(first) && node.getNext() != null) {
            Node nextNode = node.getNext();
            nextNode.setPrev(null);
            first = nextNode;
            historyMap.remove(node.getTaskId());
        } else if (node.equals(last) && node.getPrev() != null) {
            Node prevNode = node.getPrev();
            prevNode.setPrev(null);
            last = prevNode;
            historyMap.remove(node.getTaskId());
        } else {
            last = null;
            first = null;
            historyMap.remove(node.getTaskId());
        }
    }
}
