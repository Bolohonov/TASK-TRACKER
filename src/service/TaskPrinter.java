package service;

import main.java.tasks.Task;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class TaskPrinter<T extends Task> implements Printable<T> {

    Map<Integer, T> map = new LinkedHashMap<>();

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
        if (tasksList == null || tasksList.isEmpty()) {
            System.out.println("Список пуст!");
        } else {
            tasksList.forEach(System.out::println);
        }
    }

    @Override
    public void printTask(Task task) {
        if (task == null) {
            System.out.println("Задача не найдена!");
        } else {
            System.out.println(task);
        }
    }
}
