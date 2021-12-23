package service;

import repository.*;
import tasks.SubTask;
import tasks.Task;

import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.Scanner;

public class TaskUpdater {

    public static boolean checkEpicStatus(Task task) {
        if (task.getEpic().equals(EpicStatus.EPIC)) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean checkStatusAllDone(SubTask subTask) {
        LinkedList<SubTask> subTaskList = SubTaskStorage.getSubTasksListBySubTask(subTask);
        boolean status = true;
        if (subTask != null) {
            for (SubTask subT : subTaskList) {
                if (!subT.getStatus().equals(TaskStatus.DONE)) {
                    status = false;
                }
            }
        }
        return status;
    }

    public static Task setStatus(Task task) {
        System.out.println("Текущий статус" + task.getStatus());
        System.out.println("Выберите статус");
        Print.printStatusList();
        Scanner scanner = new Scanner(System.in);
        int statusIndex = 0;
        try {
            statusIndex = scanner.nextInt();
        } catch (InputMismatchException exp) {
            System.out.println("Вы ввели неверное значение!");
            statusIndex = 0;
        }
        switch (statusIndex) {
            case 2:
                updateTaskStatus(task, TaskStatus.IN_PROGRESS);
                break;
            case 3:
                updateTaskStatus(task, TaskStatus.DONE);
                break;
            default:
                Print.printMistake();
                break;
        }
        return task;
    }

    public static SubTask setStatus(SubTask subTask) {
        System.out.println("Текущий статус" + subTask.getStatus());
        System.out.println("Выберите статус");
        Print.printStatusList();
        Scanner scanner = new Scanner(System.in);
        int statusIndex = scanner.nextInt();
        switch (statusIndex) {
            case 2:
                updateSubTaskStatus(subTask, TaskStatus.IN_PROGRESS);
                break;
            case 3:
                updateSubTaskStatus(subTask, TaskStatus.DONE);
                break;
            default:
                Print.printMistake();
                break;
        }
        return subTask;
    }

    public static boolean checkStatusInProgressOrDone(SubTask subTask) {
        Task task = subTask.getTask();
        if (task.getStatus().equals(TaskStatus.IN_PROGRESS) || task.getStatus().equals(TaskStatus.DONE)) {
            return true;
        } else {
            return false;
        }
    }

    public static void updateTaskStatus(Task task, TaskStatus status) {
        task.setStatus(status);
        int index = TaskStorage.getTaskIndex(task);
        TaskStorage.replaceTask(index, task);
    }

    public static void updateEpicStatus(Task task, EpicStatus status) {
        task.setEpic(status);
        int index = TaskStorage.getTaskIndex(task);
        TaskStorage.replaceTask(index, task);
    }

    public static void updateSubTaskStatus(SubTask subTask, TaskStatus status) {
        subTask.setStatus(status);
        int index = SubTaskStorage.getSubTaskIndex(subTask);
        SubTaskStorage.replaceSubTask(index, subTask);
    }

    public static void updateObjectByInt() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Выберите задачу по ID: ");
        int id = 0;
        try {
            id = scanner.nextInt();
        } catch (NullPointerException exp) {
            System.out.println("Список был пуст!");
        }
        Repository rep = new Repository();
        if (rep.returnObject(id) instanceof SubTask) {
            updateSubTaskById(id);
        } else {
            updateTaskById(id);
        }
    }

    public static void updateSubTaskById(long id) {
        SubTask subTask = SubTaskStorage.getSubTaskByID(id);
        int index = SubTaskStorage.getSubTaskIndex(subTask);
        int command = -1;
        while (command != 0) {
            Print.printMenuToUpdateTask();
            Scanner scanner = new Scanner(System.in);
            command = scanner.nextInt();
            switch (command) {
                case 0:
                    break;
                case 1:
                    System.out.println("Введите новое название подзадачи");
                    scanner = new Scanner(System.in);
                    String name = scanner.nextLine();
                    if (name != null) {
                        subTask.setName(name);
                    }
                    SubTaskStorage.replaceSubTask(index, subTask);
                    break;
                case 2:
                    System.out.println("Введите новое описание подзадачи");
                    scanner = new Scanner(System.in);
                    String description = scanner.nextLine();
                    if (description != null) {
                        subTask.setDescription(description);
                    }
                    TaskStorage.replaceTask(index, subTask);
                    break;
                case 3:
                    subTask = setStatus(subTask);
                    if (checkStatusAllDone(subTask)) {
                        updateTaskStatus(subTask.getTask(), TaskStatus.DONE);
                    }
                    if (subTask.getStatus().equals(TaskStatus.IN_PROGRESS)
                            && checkStatusInProgressOrDone(subTask)) {
                        updateTaskStatus(subTask.getTask(), TaskStatus.IN_PROGRESS);
                    }
                    break;
                default:
                    Print.printMistake();
                    break;
            }
        }
    }

    public static void updateTaskById(int id) {
        Task task = TaskStorage.getTaskByID(id);
        int index = TaskStorage.getTaskIndex(task);
        int command = -1;
        while (command != 0) {
            Print.printMenuToUpdateTask();
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
                        task.setDescription(description);
                    }
                    TaskStorage.replaceTask(index, task);
                    break;
                case 3:
                    if (!checkEpicStatus(task)) {
                        task = setStatus(task);
                    } else {
                        System.out.println("Статус не подлежит изменению!");
                    }
                    break;
                default:
                    Print.printMistake();
                    break;
            }
        }
    }
}
