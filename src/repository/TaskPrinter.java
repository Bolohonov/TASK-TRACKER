package repository;

import tasks.Task;

import java.util.LinkedList;

public class TaskPrinter<T extends Task> implements Printable<T> {

    LinkedList<T> list = new LinkedList<>();

    @Override
    public void printList(LinkedList<T> tasksList) {
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
    public void printTask(T task) {
        System.out.println(task);
    }
}
