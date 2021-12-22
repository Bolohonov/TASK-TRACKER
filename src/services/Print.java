package services;

import tasks.SubTask;

import java.util.LinkedList;

public class Print {

    public static void printMenu() {
        System.out.println("Выберите действие: ");
        for (Menu m : Menu.values()) {
            System.out.println(m.getMenu());
        }
    }

    public static void printSubTaskList(LinkedList<SubTask> list) {
        list.forEach((SubTask subtask) -> System.out.println(subtask));
    }
}
