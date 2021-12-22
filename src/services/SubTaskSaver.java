package services;

import storage.SubTaskStorage;
import storage.TaskStatus;
import storage.TaskStorage;
import tasks.SubTask;
import tasks.Task;

import java.util.Scanner;

public class SubTaskSaver extends TaskSaver {

    public static SubTask saveSubTask(Task task) {
        String[] userTask = saveUserTask();
        int id = 0;
        SubTask subTask = new SubTask(task, userTask[0], userTask[1], id,
                TaskStatus.NEW);
        id = subTask.hashCode();
        subTask.setId(id);
        return subTask;
    }

    public static SubTask saveSubTaskFromUserSelect() {
        Task task = TaskStorage.selectUserTaskByID();
        if (task != null) {
            return saveSubTask(task);
        } else {
            return null;
        }
    }

    public static Task saveSubTaskFromTask(Task task) {
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
        return task;
    }
}
