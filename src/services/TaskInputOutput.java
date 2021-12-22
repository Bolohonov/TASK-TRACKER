package services;

import storage.TaskStorage;
import storage.SubTaskStorage;
import storage.TaskStatus;
import tasks.Task;

import java.util.Scanner;

public class TaskInputOutput {
    String userTask[] = new String[2];
    Scanner scanner = new Scanner(System.in);

    public Task saveTask() {
        Task task = null;
        saveUserTask();
        if (!userTask[0].equals(null)) {
            int hash = hashCode(userTask[0], userTask[1]);
            task = new Task(userTask[0], userTask[1], hash,
                    TaskStatus.NEW, 0);
            int command = -1;

            while (command != 0) {
                printMenuToAddSubTask();
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

    public String[] saveUserTask() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите название задачи: ");
        userTask[0] = scanner.nextLine();
        System.out.println("Введите описание задачи (можно оставить поле пустым): ");
        userTask[1] = scanner.nextLine();
        return userTask;
    }

    public int hashCode(String name, String description) {
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

    public Task selectUserTaskByID() {
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



    private void printMenuToAddSubTask() {
        System.out.println("Введите: ");
        System.out.println("0 - Для выхода в меню: ");
        System.out.println("1 - Для для добавления подзадач: ");
    }
}
