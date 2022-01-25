package repository;

import tasks.Task;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class InMemoryHistoryManager implements HistoryManager {

    private static final List<Task> history = new LinkedList<>();
    private static final Map<Integer, Task> historyMap = new HashMap();

    @Override
    public void add(Task task) {
        history.add(task);
        historyMap.put(task.getId(), task);
    }

    @Override
    public void remove(int id) {
        history.remove(id);
    }

    @Override
    public List<Task> getHistory() {
        if (history == null || history.isEmpty()) {
            System.out.println("Истории пока нет");
            return history;
        } else {
            return history;
        }
    }

    public void clearHistory() {
        history.clear();
        historyMap.clear();

    }
}
