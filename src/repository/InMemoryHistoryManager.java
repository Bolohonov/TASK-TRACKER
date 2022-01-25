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
        Node newNode = new Node(index, task);
        last = newNode;
        if (l == null) {
            first = newNode;
            historyMap.put(id, newNode);
            historyList.addFirst(newNode);
            ++index;
        } else {
            historyMap.put(id, newNode);
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
            ListIterator<Node> iter = historyList.listIterator(i-1);
            iter.next();
            iter.remove();
            historyMap.remove(node.getTask().getId());
            for (Node n : historyMap.values()) {
                int currInd = n.getIndex();
                if (currInd > i) {
                    n.setIndexMinus();
                }
            }
        } else  if (node.equals(first) && historyList.stream().iterator().hasNext()) {
            int i = node.getIndex();
            ListIterator<Node> iter = historyList.listIterator(i);
            first = iter.next();
            historyList.removeFirst();
            historyMap.remove(node.getTask().getId());
            for (Node n : historyMap.values()) {
                int currInd = n.getIndex();
                if (currInd > i) {
                    n.setIndexMinus();
                }
            }
        } else if (node.equals(last) && historyList.listIterator(node.getIndex()).hasPrevious()) {
            int i = node.getIndex();
            ListIterator<Node> iter = historyList.listIterator(i);
            last = iter.previous();
            historyList.removeLast();
            historyMap.remove(node.getTask().getId());
            for (Node n : historyMap.values()) {
                int currInd = n.getIndex();
                if (currInd > i) {
                    n.setIndexMinus();
                }
            }
        } else {
            last = null;
            first = null;
            historyMap.remove(node.getTask().getId());
        }
    }
}
