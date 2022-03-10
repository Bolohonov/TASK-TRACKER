package repository;

import tasks.Task;

import java.util.*;

public class InMemoryHistoryManager implements HistoryManager {
    private final LinkedUniqueList taskManagerHistory = new LinkedUniqueList();

    @Override
    public void add(Task task) {
        taskManagerHistory.linkLast(task);
        taskManagerHistory.addTask(task);
    }

    @Override
    public void remove(int id) {
        //try {
            taskManagerHistory.remove(id);
       // } catch (NoSuchElementException exp) {
        //    System.out.println(exp.getMessage());
       // }
    }

    @Override
    public List<Task> getHistory() {
        return taskManagerHistory.getTasks();
    }

    @Override
    public void clearHistory() {
        taskManagerHistory.clear();
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

        private void remove(int id) throws NoSuchElementException{
            if (historyMap.containsKey(id)) {
                removeNode(historyMap.get(id));
            } else {
                throw new NoSuchElementException("В истории отсутствует задача с таким ID!");
            }
        }

        private void clear() {
            historyMap.clear();
        }
    }
}

