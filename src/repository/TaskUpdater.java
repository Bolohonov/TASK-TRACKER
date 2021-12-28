package repository;

import service.Print;
import service.Scan;
import tasks.EpicTask;
import tasks.SubTask;
import tasks.SingleTask;

import java.util.Scanner;

public class TaskUpdater {

    static void updateTask(SingleTask task) {
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

    static void updateEpicTask(EpicTask task) {
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

    static void updateSubTask(SubTask task) {
        int command = -1;
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
                    SubTaskRepository.replaceSubTask(task);
                    break;
                case 2:
                    Print.printDescription();
                    task.setDescription(Scan.selectString());
                    SubTaskRepository.replaceSubTask(task);
                    break;
                case 3:
                    Print.printStatusList();
                    task.setStatus(Scan.selectStatus());
                    task.getEpicTask().getStatus();
                    break;
                default:
                    Print.printWrongValue();
                    break;
            }
        }
    }
}
