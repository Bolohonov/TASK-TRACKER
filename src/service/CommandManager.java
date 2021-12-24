package service;

import java.util.Scanner;

import repository.EpicTaskRepository;
import repository.RepositoryTaskManager;
import repository.SubTaskRepository;
import repository.TaskRepository;

//Поздно рассмотрел слово "бэкэнд", поэтому наворотил тут меню.
// Если нужно сразу переделать, то готов поправить, удалив лишнее!

public class CommandManager {

    Scanner scanner = new Scanner(System.in);

    public void getCommandPanel() {
        int command = -1;

        while (command != 0) {
            Print.printMenu();
            command = Scan.getScanIntCommandOrZero();

            switch (command) {
                case 1:
                    try {
                        Print.printSaved();
                    } catch (NullPointerException exp) {
                        System.out.println("Список был пуст!");
                    }


                    try {
                        TaskRepository.setTaskStorage();
                        Print.printSaved();
                    } catch (NullPointerException exp) {
                        System.out.println("Список был пуст!");
                    }
                    break;
                case 2:
                    try {
                        SubTaskRepository.setSubTaskFromUserSelect();
                        Print.printSaved();
                    } catch (NullPointerException exp) {
                        System.out.println("Список был пуст!");
                    }
                    break;
                case 3:
                    try {
                        RepositoryTaskManager.printTaskList(SubTaskRepository.getSubTasksList());
                    } catch (NullPointerException exp) {
                        System.out.println("Список был пуст!");
                    }
                    break;
                case 4:
                    try {
                        RepositoryTaskManager.printTaskList(EpicTaskRepository.getTasks());
                    } catch (NullPointerException exp) {
                        System.out.println("Список был пуст!");
                    }
                    break;
                case 5:
                    try {
                        RepositoryTaskManager.printTaskList(SubTaskRepository.getSubTasksListFromUserSelect());
                    } catch (NullPointerException exp) {
                        System.out.println("Список был пуст!");
                    }
                    break;
                case 6:
                    try {
                        TaskRepository.printObjectById();
                    } catch (NullPointerException exp) {
                        System.out.println("Неверный ввод!");
                    }
                    break;
                case 7:
                    try {
                        TaskUpdater.updateObjectByInt();
                    } catch (NullPointerException exp) {
                        System.out.println("Неверный ввод!");
                    }
                    Print.printSaved();
                    break;
                case 8:
                    try {
                        TaskRepository.removeAllTasks();
                    } catch (NullPointerException exp) {
                        System.out.println("В списке не было задач");
                    }
                    Print.printDeleted();
                    break;
                case 9:
                    try {
                        TaskRepository.removeTask();
                    } catch (NullPointerException exp) {
                        System.out.println("Неверный ввод!");
                    }
                    Print.printDeleted();
                    break;
                case 10:
                    try {
                        SubTaskRepository.removeSubTaskById();
                    } catch (NullPointerException exp) {
                        System.out.println("Неверный ввод!");
                    }
                    Print.printDeleted();
                case 0:
                    Print.printExit();
                    break;
                default:
                    Print.printNoCommand();
                    break;
            }
        }
    }
}
