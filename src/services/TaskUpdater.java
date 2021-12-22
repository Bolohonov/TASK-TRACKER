package services;

import storage.SubTaskStorage;
import storage.TaskStatus;
import storage.TaskStorage;
import tasks.Task;

import java.util.Arrays;
import java.util.Scanner;

public class TaskUpdater {

    public static Task updateTask() {
        Task task = TaskInputOutput.selectUserTaskByID();
        int index = TaskStorage.getTaskIndex(task);
        int command = -1;
        while (command != 0) {
            Print.printMenuToUpdateTask();
            Scanner scanner = new Scanner(System.in);
            command = scanner.nextInt();
            switch (command) {
                case 0:
                    break;
                case 1:
                    System.out.println("Введите новое название задачи");
                    scanner = new Scanner(System.in);
                    String name = scanner.nextLine();
                    if (name != null) {
                        task.setName(name);
                    }
                    TaskStorage.replaceTask(index, task);
                    break;
                case 2:
                    System.out.println("Введите новое описание задачи");
                    scanner = new Scanner(System.in);
                    String description = scanner.nextLine();
                    if (description != null) {
                        task.setName(description);
                    }
                    TaskStorage.replaceTask(index, task);
                    break;
                case 3:
                    System.out.println(task.getStatus());
                    System.out.println("Выберите статус");
                    Print.printStatusList();
                    scanner = new Scanner(System.in);
                    String status = scanner.nextLine();
                    if (Arrays.stream(TaskStatus.values())
                            .anyMatch((t) -> t.name().equals(status))) {
                        task.setStatus(TaskStatus.valueOf(status));
                    }
                default:
                    System.out.println("Вы ввели неверное значение!");
                    break;
            }
        }
        return task;
    }
}
