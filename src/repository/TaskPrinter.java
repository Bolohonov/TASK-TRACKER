package repository;

import tasks.Task;

import java.util.LinkedList;
import java.util.List;

public class TaskPrinter<T extends Task> implements Printable<T> {

    List<T> list = new LinkedList<>();

    @Override
    public void printList(List<T> tasksList) {
        list = tasksList;
        if (!list.isEmpty() || list != null) {
            for (T task : list) {
                System.out.println(task);
            }
        } else {
            System.out.println("Список пуст!");
        }
    }

    @Override
    public void printTask(Task task) {
        System.out.println(task);
    }
}
