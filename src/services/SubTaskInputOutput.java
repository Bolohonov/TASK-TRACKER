package services;

import storage.SubTaskStorage;
import storage.TaskStatus;
import tasks.SubTask;
import tasks.Task;

import java.util.Scanner;

public class SubTaskInputOutput {

    public static SubTask saveSubTask(Task task) {
        String[] userTask = TaskInputOutput.saveUserTask();
        int hash = TaskInputOutput.hashCode(userTask[0], userTask[1]);
        SubTask subTask = new SubTask(task, userTask[0], userTask[1], hash,
                TaskStatus.NEW);
        return subTask;
    }

    public static SubTask saveSubTaskFromUserSelect() {
        Task task = TaskInputOutput.selectUserTaskByID();
        if (task != null) {
            return saveSubTask(task);
        } else {
            return null;
        }
    }

    public static SubTask selectUserSubTaskByID() {
        int id = selectSubTaskId();
        SubTask subTask = null;
        for (SubTask taskSelect : SubTaskStorage.getSubTasksList()) {
            if (taskSelect.getId() == id) {
                subTask = taskSelect;
            }
        }
        if (subTask == null) {
            System.out.println("Вы ввели неверный ID задачи");
        }
        return subTask;
    }

    public static int selectSubTaskId() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Выберите задачу по ID: ");
        Print.printSubTaskList(SubTaskStorage.getSubTasksList());
        int id = scanner.nextInt();
        return id;
    }

}
