package service;

import java.util.InputMismatchException;
import java.util.Scanner;

import repository.SubTaskStorage;
import repository.TaskStorage;

//Не сразу увидел слово бэкэнд, поэтому наворотил тут меню (да и вообще много лишнего!
// Если нужно сразу переделать, то готов поправить, удалив лишнее!

public class CommandManager {

    Scanner scanner = new Scanner(System.in);

    public void getCommandPanel() {
        int command = -1;

        while (command != 0) {
            Print.printMenu();
            try {
                command = scanner.nextInt();
            } catch (InputMismatchException exp) {
                System.out.println("Вы ввели неверное значение!");
                command = 0;
            }

            switch (command) {
                case 1:
                    try {
                        TaskStorage.setTaskStorage();
                        Print.printSaved();
                    } catch (NullPointerException exp) {
                        System.out.println("Список был пуст!");
                    }
                    break;
                case 2:
                    try {
                        SubTaskStorage.setSubTaskFromUserSelect();
                        Print.printSaved();
                    } catch (NullPointerException exp) {
                        System.out.println("Список был пуст!");
                    }
                    break;
                case 3:
                    try {
                        Print.printSubTaskList(SubTaskStorage.getSubTasksList());
                    } catch (NullPointerException exp) {
                        System.out.println("Список был пуст!");
                    }
                    break;
                case 4:
                    try {
                        Print.printTaskList(TaskStorage.getEpics());
                    } catch (NullPointerException exp) {
                        System.out.println("Список был пуст!");
                    }
                    break;
                case 5:
                    try {
                    Print.printSubTaskList(SubTaskStorage.getSubTasksListFromUserSelect());
                    } catch (NullPointerException exp) {
                        System.out.println("Список был пуст!");
                    }
                    break;
                case 6:
                    try {
                        TaskStorage.printObjectByInt();
                    } catch (NullPointerException exp) {
                        System.out.println("Неверный ввод!");
                    }
                    break;
                case 7:
                    try {
                        TaskUpdater.updateObjectByInt();
                    } catch (NullPointerException exp) {
                        System.out.println("Неверный ввод!");
                    }
                    Print.printSaved();
                    break;
                case 8:
                    try {
                    TaskStorage.removeAllTasks();
                    } catch (NullPointerException exp) {
                        System.out.println("В списке не было задач");
                    }
                    Print.printDeleted();
                    break;
                case 9:
                    try {
                        TaskStorage.removeTask();
                    } catch (NullPointerException exp) {
                        System.out.println("Неверный ввод!");
                    }
                    Print.printDeleted();
                    break;
                case 10:
                    try {
                        SubTaskStorage.removeSubTaskById();
                    } catch (NullPointerException exp) {
                    System.out.println("Неверный ввод!");
                    }
                    Print.printDeleted();
                case 0:
                    Print.printExit();
                    break;
                default:
                    Print.printNoCommand();
                    break;
            }
        }
    }
}
