package service;

import repository.TaskStatus;
import tasks.EpicTask;
import tasks.SubTask;
import tasks.SingleTask;

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

    static void printMenuToSaveTask() {
        System.out.println("Что вы хотите сохранить: ");
        System.out.println("0 - Для выхода в меню: ");
        System.out.println("1 - Задачу: ");
        System.out.println("2 - Эпик: ");
        System.out.println("3 - Подзадачу: ");
    }

    public static void printMenuToUpdateTask() {
        System.out.println("Что вы хотите обновить: ");
        System.out.println("0 - Для выхода в меню: ");
        System.out.println("1 - Для обновления названия: ");
        System.out.println("2 - Для обновления описания: ");
        System.out.println("3 - Для обновления статуса: ");
    }

    static void printDeleted() {
        System.out.println("Задача удалена!");
    }

    static void printSaved() {
        System.out.println("Задача сохранена!");
    }

    public static void printMistake() {
        System.out.println("Вы ввели неверное значение!");
    }

    static void printExit() {
        System.out.println("Работа программы завершена!");
    }

    static void printNoCommand() {
        System.out.println("Извините, такой команды пока нет.");
    }

    public static void printStatusList() {
        for (TaskStatus taskStatus : TaskStatus.values()) {
            System.out.println(taskStatus.getStatus());
        }
    }
}
