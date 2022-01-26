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
            for (int i = 0; i < size; i++) {
                historyArrayList.add(i, nodes.getFirst().getTask());
                nodes.removeFirst();
            }
        }
        return historyArrayList;
    }

    private void removeNode(Node node) {
        if (!node.equals(first) && !node.equals(last)) {
//            for (Node n : historyMap.values()) {
//                System.out.println(n.getTask().getId() + " ----" + n.getIndex());
//            }
//            System.out.println("--------------");
//            historyList.forEach((Node n) -> System.out.println(n.getIndex() + " ----" + n.getTask().getId()));
//            int i = node.getIndex();
//            ListIterator<Node> iter = historyList.listIterator(i);
//            iter.next();
//            iter.remove();
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
            historyList.removeFirst();
            historyMap.remove(node.getTask().getId());
        }
        size--;
//        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!");
//        for (Node n : historyMap.values()) {
//            System.out.println(n.getTask().getId() + " ----" + n.getIndex());
//        }
//        System.out.println("--------------");
//        historyList.forEach((Node n) -> System.out.println(n.getIndex() + " ----" + n.getTask().getId()));
    }
}

