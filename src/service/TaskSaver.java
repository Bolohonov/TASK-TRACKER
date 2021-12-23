package service;

import repository.TaskStatus;
import repository.EpicStatus;
import tasks.Task;

import java.util.InputMismatchException;
import java.util.Scanner;

public class TaskSaver {

    public static Task saveTask() {
        Task task = null;
        String[] userTask = TaskSaver.saveUserTask();
        if (!userTask[0].equals(null)) {
            long id = 0;
            task = new Task(userTask[0], userTask[1], id,
                    TaskStatus.NEW, EpicStatus.NOT_EPIC);
            id = task.calcAndCheckId();
            task.setId(id);
            task = SubTaskSaver.saveSubTaskFromTask(task);
        } else {
            System.out.println("Поле Название должно быть заполнено");
            return task;
        }
        return task;
    }

    public static String[] saveUserTask() {
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
}
