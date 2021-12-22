package services;

import storage.SubTaskStorage;
import storage.TaskStatus;
import storage.TaskStorage;
import tasks.Task;

import java.util.Scanner;

public class UpdateTask {

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
                    String name = scanner.nextLine();
                    if (name != null) {
                        task.setName(name);
                    }
                    TaskStorage.replaceTask(index, task);
                    break;
                default:
                    System.out.println("Вы ввели неверное значение!");
                    break;
            }
        }
        return task;


    }




}
