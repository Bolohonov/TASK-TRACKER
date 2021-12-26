package service;

import repository.TaskStatus;

public class Print {

    static void printMenu() {
        System.out.println("Выберите действие: ");
        for (Menu m : Menu.values()) {
            System.out.println(m.getMenu());
        }
    }

    public static void printMenuToAddSubTask() {
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

    static void printSelectById() {
        System.out.println("Выберите задачу по ID: ");
    }

    public static void printWrongValue() {
        System.out.println("Вы ввели неверное значение!");
    }

    public static void printNoValue() {
        System.out.println("Вы не ввели значение");
    }

    static void printExit() {
        System.out.println("Работа программы завершена!");
    }

    static void printNoCommand() {
        System.out.println("Извините, такой команды пока нет.");
    }

    public static void printName() {
        System.out.println("Введите название задачи:");
    }

    public static void printDescription() {
        System.out.println("Введите описание задачи:");
    }

    public static void printStatusList() {
        for (TaskStatus taskStatus : TaskStatus.values()) {
            System.out.println(taskStatus.getStatus());
        }
    }
}
