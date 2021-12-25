package service;

import repository.TaskRepository;
import tasks.SingleTask;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Scan {

    public static int getScanOrZero() {
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

    public static String[] saveLinesFromUser() {
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

    public static int selectType() {
        Print.printMenuToSaveTask();
        return Scan.getScanOrZero();
    }

//    public static SingleTask selectUserTaskByID() {
//        int id = selectId();
//        SingleTask singleTask = null;
//        for (SingleTask singleTaskSelect : TaskRepository.getTasks()) {
//            if (singleTaskSelect.getId() == id) {
//                singleTask = singleTaskSelect;
//            }
//        }
//        if (singleTask == null) {
//            System.out.println("Вы ввели неверный ID задачи");
//        }
//        return singleTask;
//    }
//
//    public static int selectId() {
//        Scanner scanner = new Scanner(System.in);
//        System.out.println("Выберите задачу по ID: ");
//        Print.printTaskList(TaskRepository.singleTasks);
//        int id = 0;
//        try {
//            id = scanner.nextInt();
//        } catch (InputMismatchException exp) {
//            System.out.println("Вы ввели неверное значение!");
//        }
//
//        return id;
//    }
}
