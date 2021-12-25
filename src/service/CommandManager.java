package service;

import repository.TaskManager;

//Поздно рассмотрел слово "бэкэнд", поэтому наворотил тут меню (да и вообще много лишнего!).
// Если нужно сразу переделать, то готов поправить, удалив лишнее!

public class CommandManager {

    TaskManager manager = new TaskManager();


    public void getCommandPanel() {
        int command = -1;

        while (command != 0) {
            Print.printMenu();
            command = Scan.getScanOrZero();

            switch (command) {
                case 1:
                    manager.saveFromCommand();
                    break;
                case 2:
                    manager.saveSubTaskFromCommand();
                    break;
                case 3:
                    manager.printSubTasks();
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
