package service;

import repository.InMemoryTasksManager;

public class CommandManager {

    InMemoryTasksManager manager = new InMemoryTasksManager();


    public void getCommandPanel() {
        int command = -1;

        while (command != 0) {
            Print.printMenu();
            command = Scan.getIntOrZero();

            switch (command) {
                case 1:
                    manager.saveFromCommand();
                    break;
                case 2:
                    manager.saveSubTaskFromCommand();
                    break;
                case 3:
                    manager.printTasks();
                    break;
                case 4:
                    manager.printEpics();
                    break;
                case 5:
                    manager.printSubTasksFromUserSelect();
                    break;
                case 6:
                    manager.printTask(manager.returnObject(Scan.selectId()));
                    break;
                case 7:
                    manager.updateTask(manager.returnObject(Scan.selectId()));
                    break;
                case 8:
                    manager.removeAllTasks();
                    break;
                case 9:
                    manager.removeEpicTask();
                    break;
                case 10:
                    manager.removeSubTaskById();
                    break;
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
