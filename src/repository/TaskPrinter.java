package repository;

import tasks.Task;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class TaskPrinter<T extends Task> implements Printable<T> {

    Map<Integer, T> map = new LinkedHashMap<>();
    List<T> list = new LinkedList<>();

    @Override
    public void printMap(Map<Integer, T> tasksMap) {
        map = tasksMap;
        if (!map.isEmpty() || map != null) {
            map.values().forEach(System.out::println);
        } else {
            System.out.println("Список задач пуст!");
        }
    }

    @Override
    public void printList(List<T> tasksList) {
        list = tasksList;
        if (!list.isEmpty() || list != null) {
            list.forEach(System.out::println);
        } else {
            System.out.println("Список задач пуст!");
        }
    }

    @Override
    public void printTask(Task task) {
        System.out.println(task);
    }
}
