package service;

import repository.*;
import tasks.EpicTask;
import tasks.SubTask;
import tasks.SingleTask;

import java.util.LinkedList;
import java.util.Scanner;

public class TaskUpdater {

    TaskManager manager = new TaskManager();

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

    public static SubTask setSubTaskStatus(SubTask subTask) {
        System.out.println("Текущий статус" + subTask.getStatus());
        System.out.println("Выберите статус");
        Print.printStatusList();
        int statusIndex = Scan.getIntOrZero();
        switch (statusIndex) {
            case 2:
                updateSubTaskStatus(subTask, TaskStatus.IN_PROGRESS);
                break;
            case 3:
                updateSubTaskStatus(subTask, TaskStatus.DONE);
                break;
            default:
                Print.printWrongValue();
                break;
        }
        return subTask;
    }

    public static boolean checkStatusInProgressOrDone(SubTask subTask) {
        SingleTask singleTask = subTask.getTask();
        if (singleTask.getStatus().equals(TaskStatus.IN_PROGRESS)
                || singleTask.getStatus().equals(TaskStatus.DONE)) {
            return true;
        } else {
            return false;
        }
    }

    public static void updateEpicTaskStatus(EpicTask epicTask, TaskStatus status) {
        epicTask.setStatus(status);
        int index = EpicTaskRepository.getTaskIndex(epicTask);
        EpicTaskRepository.replaceTask(index, epicTask);
    }

    public static void updateTaskStatus(SingleTask singleTask, TaskStatus status) {
        singleTask.setStatus(status);
        int index = SingleTaskRepository.getTaskIndex(singleTask);
        SingleTaskRepository.replaceTask(index, singleTask);
    }

    public static void updateSubTaskStatus(SubTask subTask, TaskStatus status) {
        subTask.setStatus(status);
        int index = SubTaskRepository.getSubTaskIndex(subTask);
        SubTaskRepository.replaceSubTask(index, subTask);
    }

    public static void updateTask(SingleTask task) {
        int command = -1;
        int index = SingleTaskRepository.getTaskIndex(task);
        while (command != 0) {
            Print.printMenuToUpdateTask();
            command = Scan.getIntOrZero();
            switch (command) {
                case 0:
                    break;
                case 1:
                    Print.printName();
                    task.setName(Scan.selectString());
                    SingleTaskRepository.replaceTask(index, task);
                    break;
                case 2:
                    Print.printDescription();
                    task.setDescription(Scan.selectString());
                    SingleTaskRepository.replaceTask(index, task);
                    break;
                case 3:
                    Print.printStatusList();
                    task.setStatus(Scan.selectStatus());
                    SingleTaskRepository.replaceTask(index, task);
                    break;
                default:
                    Print.printWrongValue();
                    break;
            }
        }
    }

    public static void updateEpicTask(EpicTask task) {
        int command = -1;
        int index = EpicTaskRepository.getTaskIndex(task);
        while (command != 0) {
            Print.printMenuToUpdateTask();
            command = Scan.getIntOrZero();
            switch (command) {
                case 0:
                    break;
                case 1:
                    Print.printName();
                    task.setName(Scan.selectString());
                    EpicTaskRepository.replaceTask(index, task);
                    break;
                case 2:
                    Print.printDescription();
                    task.setDescription(Scan.selectString());
                    EpicTaskRepository.replaceTask(index, task);
                    break;
                case 3:
                    System.out.println("Статус не подлежит изменению!");
                    break;
                default:
                    Print.printWrongValue();
                    break;
            }
        }
    }

    public static void updateSubTask(SubTask task) {
        int command = -1;
        int index = SubTaskRepository.getTaskIndex(task);
        while (command != 0) {
            Print.printMenuToUpdateTask();
            Scanner scanner = new Scanner(System.in);
            command = scanner.nextInt();
            switch (command) {
                case 0:
                    break;
                case 1:
                    Print.printName();
                    task.setName(Scan.selectString());
                    SubTaskRepository.replaceSubTask(index, task);
                    break;
                case 2:
                    Print.printDescription();
                    task.setDescription(Scan.selectString());
                    SubTaskRepository.replaceSubTask(index, task);
                    break;
                case 3:
                    Print.printStatusList();
                    task.setStatus(Scan.selectStatus());
                    if (checkStatusAllDone(task)) {
                        updateEpicTaskStatus(task.getTask(), TaskStatus.DONE);
                    }
                    if (task.getStatus().equals(TaskStatus.IN_PROGRESS)
                            && checkStatusInProgressOrDone(task)) {
                        updateEpicTaskStatus(task.getTask(), TaskStatus.IN_PROGRESS);
                    }
                    break;
                default:
                    Print.printWrongValue();
                    break;
            }
        }
    }

}
