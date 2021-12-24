package service;

import repository.*;
import tasks.SubTask;
import tasks.SingleTask;

import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.Scanner;

public class TaskUpdater {

    public static boolean checkEpicStatus(SingleTask singleTask) {
        if (singleTask.getEpic().equals(EpicStatus.EPIC)) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean checkStatusAllDone(SubTask subTask) {
        LinkedList<SubTask> subTaskList = SubTaskRepository.getSubTasksListBySubTask(subTask);
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

    public static SingleTask setStatus(SingleTask singleTask) {
        System.out.println("Текущий статус" + singleTask.getStatus());
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
                updateTaskStatus(singleTask, TaskStatus.IN_PROGRESS);
                break;
            case 3:
                updateTaskStatus(singleTask, TaskStatus.DONE);
                break;
            default:
                Print.printMistake();
                break;
        }
        return singleTask;
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
        SingleTask singleTask = subTask.getTask();
        if (singleTask.getStatus().equals(TaskStatus.IN_PROGRESS) || singleTask.getStatus().equals(TaskStatus.DONE)) {
            return true;
        } else {
            return false;
        }
    }

    public static void updateTaskStatus(SingleTask singleTask, TaskStatus status) {
        singleTask.setStatus(status);
        int index = TaskRepository.getTaskIndex(singleTask);
        TaskRepository.replaceTask(index, singleTask);
    }

    public static void updateEpicStatus(SingleTask singleTask, EpicStatus status) {
        singleTask.setEpic(status);
        int index = TaskRepository.getTaskIndex(singleTask);
        TaskRepository.replaceTask(index, singleTask);
    }

    public static void updateSubTaskStatus(SubTask subTask, TaskStatus status) {
        subTask.setStatus(status);
        int index = SubTaskRepository.getSubTaskIndex(subTask);
        SubTaskRepository.replaceSubTask(index, subTask);
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
        RepositoryTaskManager rep = new RepositoryTaskManager();
        if (rep.returnObject(id) instanceof SubTask) {
            updateSubTaskById(id);
        } else {
            updateTaskById(id);
        }
    }

    public static void updateSubTaskById(long id) {
        SubTask subTask = SubTaskRepository.getSubTaskByID(id);
        int index = SubTaskRepository.getSubTaskIndex(subTask);
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
                    SubTaskRepository.replaceSubTask(index, subTask);
                    break;
                case 2:
                    System.out.println("Введите новое описание подзадачи");
                    scanner = new Scanner(System.in);
                    String description = scanner.nextLine();
                    if (description != null) {
                        subTask.setDescription(description);
                    }
                    TaskRepository.replaceTask(index, subTask);
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
        SingleTask singleTask = TaskRepository.getTaskByID(id);
        int index = TaskRepository.getTaskIndex(singleTask);
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
                        singleTask.setName(name);
                    }
                    TaskRepository.replaceTask(index, singleTask);
                    break;
                case 2:
                    System.out.println("Введите новое описание задачи");
                    scanner = new Scanner(System.in);
                    String description = scanner.nextLine();
                    if (description != null) {
                        singleTask.setDescription(description);
                    }
                    TaskRepository.replaceTask(index, singleTask);
                    break;
                case 3:
                    if (!checkEpicStatus(singleTask)) {
                        singleTask = setStatus(singleTask);
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
