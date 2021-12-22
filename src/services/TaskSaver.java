package services;

import storage.TaskStatus;
import tasks.EpicStatus;
import tasks.Task;

import java.util.Scanner;

public class TaskSaver {

    public static Task saveTask() {
        Task task = null;
        String[] userTask = TaskSaver.saveUserTask();
        if (!userTask[0].equals(null)) {
            int id = 0;
            task = new Task(userTask[0], userTask[1], id,
                    TaskStatus.NEW, EpicStatus.NOT_EPIC);
            id = task.hashCode();
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
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите название задачи: ");
        userTask[0] = scanner.nextLine();
        System.out.println("Введите описание задачи: ");
        userTask[1] = scanner.nextLine();
        return userTask;
    }

}
