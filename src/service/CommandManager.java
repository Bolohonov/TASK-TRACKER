package service;

public class CommandManager {

    Managers manager = new Managers();

    public void getCommandPanel() {
        int command = -1;

        while (command != 0) {
            Print.printMenu();
            command = Scan.getIntOrZero();

            switch (command) {
                case 1:
                    manager.getDefault().saveFromCommand();
                    break;
                case 2:
                    manager.getDefault().saveSubTaskFromCommand();
                    break;
                case 3:
                    manager.getDefault().printTasks();
                    break;
                case 4:
                    manager.getDefault().printEpics();
                    break;
                case 5:
                    manager.getDefault().printSubTasksFromUserSelect();
                    break;
                case 6:
                    manager.getDefault().printTask(manager.getDefault()
                            .returnObject(Scan.selectId()));
                    break;
                case 7:
                    manager.getDefault().updateTask(manager.getDefault()
                            .returnObject(Scan.selectId()));
                    break;
                case 8:
                    manager.getDefault().removeAllTasks();
                    break;
                case 9:
                    manager.getDefault().removeEpicTask();
                    break;
                case 10:
                    manager.getDefault().removeSubTaskById();
                    break;
                case 11:
                    manager.getDefault().printHistory();
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
