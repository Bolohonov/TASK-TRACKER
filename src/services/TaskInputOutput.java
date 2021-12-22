package services;

import storage.TaskStorage;
import storage.SubTaskStorage;
import storage.TaskStatus;
import tasks.Task;

import java.util.Scanner;

public class TaskInputOutput {

    public static Task saveTask() {
        Task task = null;
        String[] userTask = TaskInputOutput.saveUserTask();
        if (!userTask[0].equals(null)) {
            int hash = hashCode(userTask[0], userTask[1]);
            task = new Task(userTask[0], userTask[1], hash,
                    TaskStatus.NEW, 0);
            int command = -1;

            while (command != 0) {
                Print.printMenuToAddSubTask();
                Scanner scanner = new Scanner(System.in);
                command = scanner.nextInt();
                switch (command) {
                    case 0:
                        break;
                    case 1:
                        task.setEpic(1);
                        SubTaskStorage.setSubTaskStorage(task);
                        task.setStatus(TaskStatus.IN_PROGRESS);
                        break;
                    default:
                        System.out.println("Вы ввели неверное значение!");
                        break;
                }
            }
        } else {
            System.out.println("Поле Название должно быть заполнено");
            return task;
        }
        return task;
    }

    public static String[] saveUserTask() {
        String[] userTask = new String[2];
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите название задачи: ");
        userTask[0] = scanner.nextLine();
        System.out.println("Введите описание задачи: ");
        userTask[1] = scanner.nextLine();
        return userTask;
    }

    public static int hashCode(String name, String description) {
        int hash = 17;
        int random = (int)(Math.random() * 131);
        if (name != null) {
            hash = name.hashCode();
        }
        if (description != null) {
            hash = hash + description.hashCode()+random;
        }
        return hash;
    }

    public static Task selectUserTaskByID() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Выберите задачу по ID: ");
        for (Task task : TaskStorage.getTaskStorage().getTasks()) {
            System.out.println(task.toString());
        }
        int id = scanner.nextInt();

        Task task = null;
        for (Task taskSelect : TaskStorage.getTaskStorage().getTasks()) {
            if (taskSelect.getId() == id) {
                task = taskSelect;
            } else {
                System.out.println("Вы ввели неверный ID задачи");
            }
        }
        return task;
    }
}
