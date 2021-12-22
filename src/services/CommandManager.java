package services;

import java.util.Scanner;

import storage.SubTaskStorage;
import storage.TaskStorage;

public class CommandManager {

    Scanner scanner = new Scanner(System.in);

    public void getCommandPanel() {
        int command = -1;

        while (command != 0) {
            Print.printMenu();
            command = scanner.nextInt();

            switch (command) {
                case 1:
                    TaskStorage.setTaskStorage();
                    break;
                case 2:
                    SubTaskStorage.setSubTaskFromUserSelect();
                    break;
                case 3:
                    Print.printSubTaskList(SubTaskStorage.getSubTasksList());
                    break;
                case 4:
                    Print.printSubTaskList(SubTaskStorage.getSubTasksListFromUserSelect());
                    break;
                case 5:
                    //SubTaskStorage.printSubTasksListFromUserChoice();
                    break;
                case 6: break;
                case 7: break;
                case 8: break;
                case 0:
                    System.out.println("Работа программы завершена!");
                    break;
                default:
                    System.out.println("Извините, такой команды пока нет.");
                    break;
            }
        }
    }
}
