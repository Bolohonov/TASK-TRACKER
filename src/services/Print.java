package services;

import tasks.SubTask;

import java.util.LinkedList;

public class Print {

    static void printMenu() {
        System.out.println("Выберите действие: ");
        for (Menu m : Menu.values()) {
            System.out.println(m.getMenu());
        }
    }

    static void printMenuToAddSubTask() {
        System.out.println("Введите: ");
        System.out.println("0 - Для выхода в меню: ");
        System.out.println("1 - Для для добавления подзадач: ");
    }

    static void printMenuToUpdateTask() {
        System.out.println("Что вы хотите обновить: ");
        System.out.println("0 - Для выхода в меню: ");
        System.out.println("1 - Для обновления названия: ");
        System.out.println("2 - Для обновления описания: ");
        System.out.println("3 - Для обновления статуса: ");
    }

    static void printSubTaskList(LinkedList<SubTask> list) {
        list.forEach((SubTask subtask) -> System.out.println(subtask));
    }
}
