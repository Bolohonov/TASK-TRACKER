package service;

import repository.TaskStatus;
import tasks.SubTask;
import tasks.Task;

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

    public static void printTaskList(LinkedList<Task> list) {
        if (list.isEmpty()) {
            System.out.println("Список пуст!");
        } else {
            list.forEach((Task task) -> System.out.println(task));
        }
    }

    public static void printEpicList(LinkedList<Task> list) {
        if (list.isEmpty()) {
            System.out.println("Список пуст!");
        } else {
            list.forEach((Task task) -> System.out.println(task));
        }
    }

    public static void printSubTaskList(LinkedList<SubTask> list) {
        if (list.isEmpty()) {
            System.out.println("Список пуст!");
        } else {
            list.forEach((SubTask subtask) -> System.out.println(subtask));
        }
    }

    static void printDeleted() {
        System.out.println("Задача удалена!");
    }

    static void printSaved() {
        System.out.println("Задача сохранена!");
    }
    static void printMistake() {
        System.out.println("Вы ввели неверное значение!");
    }


    static void printExit() {
        System.out.println("Работа программы завершена!");
    }

    static void printNoCommand() {
        System.out.println("Извините, такой команды пока нет.");
    }

    static void printStatusList() {
        for (TaskStatus taskStatus : TaskStatus.values()) {
            System.out.println(taskStatus.getStatus());
        }
    }
}
