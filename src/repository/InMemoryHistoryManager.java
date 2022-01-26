package repository;

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
        LinkedList<Node> nodes = new LinkedList<>(historyList);
        if (historyList == null || historyList.isEmpty()) {
            System.out.println("Истории пока нет");
            return null;
        } else {
            int iter = 0;
            for (Node n : historyList) {
                if (n.getNextNode() != null) {
                    historyArrayList.add(iter, n.getTask());
                } else {
                    historyArrayList.add(iter, n.getTask());
                    break;
                }
            }
        }
        return historyArrayList;
    }

    private void removeNode(Node node) {
        if (!node.equals(first) && !node.equals(last)) {
            print();
            Node prevNode = node.getPrevNode();
            Node nextNode = node.getNextNode();
            prevNode.setNextNode(nextNode);
            nextNode.setPrevNode(prevNode);
            historyMap.remove(node.getTask().getId());
        } else  if (node.equals(first) && historyList.stream().iterator().hasNext()) {
            Node nextNode = node.getNextNode();
            nextNode.setPrevNode(null);
            historyMap.remove(node.getTask().getId());
        } else if (node.equals(last) && historyList.listIterator(size).hasPrevious()) {
            Node prevNode = node.getPrevNode();
            prevNode.setNextNode(null);
            historyMap.remove(node.getTask().getId());
        } else {
            last = null;
            first = null;
            historyMap.remove(node.getTask().getId());
        }
        unlink(node);
        size--;
        print();
    }

    private void unlink(Node node) {
        node.setNextNode(null);
        node.setPrevNode(null);
        node.setTask(null);
    }

    private void print() {
        for (Node n : historyMap.values()) {
                System.out.println(n.getTask().getId() + " ----" + n.getTask());
            }
            System.out.println("--------------");
        for (int i = 0 ; i < size ; i++) {
            System.out.println(i + " ----" +historyList.get(i).getTask());
        }
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!");
    }
}

