package services;

import storage.TaskStatus;
import tasks.SubTask;
import tasks.Task;

import java.util.Arrays;
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

    static void printTaskList(LinkedList<Task> list) {
        list.forEach((Task task) -> System.out.println(task));
    }

    static void printSubTaskList(LinkedList<SubTask> list) {
        list.forEach((SubTask subtask) -> System.out.println(subtask));
    }

    static void printDeleted() {
        System.out.println("Задача удалена!");
    }

    static void printSaved() {
        System.out.println("Задача сохранена!");
    }

    static void printExit() {
        System.out.println("Работа программы завершена!");
    }

    static void printNoCommand() {
        System.out.println("Извините, такой команды пока нет.");
    }

    static void printStatusList() {
        System.out.println(Arrays.toString(TaskStatus.values()));
    }
}
