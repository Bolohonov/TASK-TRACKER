package services;

import storage.TaskStatus;
import storage.TaskStorage;
import tasks.SubTask;
import tasks.Task;

import java.util.Scanner;

public class SubTaskInputOutput extends TaskInputOutput {

    private static SubTaskInputOutput subTaskIO;

    public static SubTask saveSubTask(Task task) {
        String[] userTask = saveUserTask();
        int hash = hashCode(userTask[0], userTask[1]);
        SubTask subTask = new SubTask(task, userTask[0], userTask[1], hash,
                TaskStatus.NEW);
        return subTask;
    }

    public static SubTask saveSubTaskFromUserSelect() {
        Task task = selectUserTaskByID();
        if (task != null) {
            return saveSubTask(task);
        } else {
            return null;
        }
    }

    public static Task selectUserTaskByID() {
        for (Task task : TaskStorage.getTasks()) {
            System.out.println(task.toString());
        }
        Task task = null;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Выберите задачу по ID: ");
        int id = scanner.nextInt();
        for (Task taskChoose : TaskStorage.getTasks()) {
            if (taskChoose.getId() == id) {
                task = taskChoose;
            }
        }
        if (task == null) {
            System.out.println("Вы ввели неверный ID задачи");
        }
        return task;
    }
}
