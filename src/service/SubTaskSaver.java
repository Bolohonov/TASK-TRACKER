package service;

import repository.SubTaskRepository;
import repository.TaskStatus;
import repository.TaskRepository;
import repository.EpicStatus;
import tasks.SubTask;
import tasks.Task;

import java.util.InputMismatchException;
import java.util.Scanner;

public class SubTaskSaver extends TaskSaver {

    public static SubTask saveSubTask(Task task) {
        String[] userTask = saveUserTask();
        long id = 0;
        SubTask subTask = new SubTask(task, userTask[0], userTask[1], id,
                TaskStatus.NEW);
        id = subTask.calcAndCheckId();
        subTask.setId(id);
        return subTask;
    }

    public static SubTask saveSubTaskFromUserSelect() {
        Task task = TaskRepository.selectUserTaskByID();
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
            try {
                command = scanner.nextInt();
            } catch (InputMismatchException exp) {
                System.out.println("Вы ввели неверное значение!");
                command = 0;
            }
            switch (command) {
                case 0:
                    break;
                case 1:
                    task.setEpic(EpicStatus.EPIC);
                    SubTaskRepository.setSubTaskStorage(task);
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
