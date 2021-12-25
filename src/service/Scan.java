package service;

import repository.TaskStatus;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Scan {

    public static int getIntOrZero() {
        Scanner scanner;
        int command;
        scanner = new Scanner(System.in);
        try {
            command = scanner.nextInt();
        } catch (InputMismatchException exp) {
            Print.printWrongValue();
            command = 0;
        } catch (NullPointerException exp) {
            Print.printNoValue();
            command = 0;
        }
        return command;
    }

    public static TaskStatus selectStatus() {
        Scanner scanner;
        int command;
        TaskStatus status;
        scanner = new Scanner(System.in);
        try {
            command = scanner.nextInt();
        } catch (InputMismatchException exp) {
            Print.printWrongValue();
            command = 0;
        } catch (NullPointerException exp) {
            Print.printNoValue();
            command = 0;
        }
        if (command == 2) {
            return status = TaskStatus.IN_PROGRESS;
        } else if (command == 3) {
            return status = TaskStatus.DONE;
        } else {
            Print.printWrongValue();
            return null;
            }
        }

    public static String[] saveLinesFromUser() {
        String[] userTask = new String[2];
        try {
            Scanner scanner = new Scanner(System.in);
            Print.printName();
            userTask[0] = scanner.nextLine();
            Print.printDescription();
            userTask[1] = scanner.nextLine();
        } catch (InputMismatchException exp) {
            Print.printWrongValue();
        } catch (NullPointerException e) {
            Print.printNoValue();
        }
        return userTask;
    }

    public static int selectType() {
        Print.printMenuToSaveTask();
        return Scan.getIntOrZero();
    }

    public static int selectId() {
        Scanner scanner = new Scanner(System.in);
        Print.printSelectById();
        int id = 0;
        try {
            id = scanner.nextInt();
        } catch (InputMismatchException exp) {
            Print.printWrongValue();
        }
        return id;
    }

    public static String selectString() {
        String str = null;
        try {
            Scanner scanner = new Scanner(System.in);
            str = scanner.nextLine();
        } catch (InputMismatchException exp) {
            Print.printWrongValue();
        } catch (NullPointerException e) {
            Print.printNoValue();
        }
        return str;
    }
}
