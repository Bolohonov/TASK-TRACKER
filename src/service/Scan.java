package service;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Scan {

    public static int getScanIntCommandOrZero() {
        Scanner scanner;
        int command;
        scanner = new java.util.Scanner(System.in);
        try {
            command = scanner.nextInt();
        } catch (InputMismatchException exp) {
            System.out.println("Вы ввели неверное значение!");
            command = 0;
        }
        return command;
    }

    public static int selectTaskTypeFromUser() {
        Print.printMenuToSaveTask();
        return Scan.getScanIntCommandOrZero();
    }

    public static String[] getLinesFromUser() {
        String[] userTask = new String[2];
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Введите название задачи: ");
            userTask[0] = scanner.nextLine();
            System.out.println("Введите описание задачи: ");
            userTask[1] = scanner.nextLine();
        } catch (InputMismatchException exp) {
            System.out.println("Вы ввели неверное значение!");
        }
        return userTask;
    }

    public static int selectId() {
        System.out.println("Выберите задачу по ID: ");
        int id = getScanIntCommandOrZero();
        return id;
    }
}
