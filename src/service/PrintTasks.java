package service;

import tasks.Task;

import java.util.LinkedList;

public class PrintTasks<T extends Task> {

    LinkedList<T> list = new LinkedList<>();

    void printList(LinkedList<T> tasksList) {
        list = tasksList;
        if (!list.isEmpty() || list != null) {
            for (T task : list) {
                System.out.println(task);
            }
        } else {
            System.out.println("Список пуст!");
        }
    }

    void printTask(T task) {
        System.out.println(task);
    }
}
