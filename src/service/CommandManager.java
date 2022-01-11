package service;

public class CommandManager {

    Managers manager = new Managers();
    PrintTasks printTasks = new PrintTasks();

    public void getCommandPanel() {
        int command = -1;
        int id;

        while (command != 0) {
            Print.printMenu();
            command = Scan.getIntOrZero();

            switch (command) {
                case 1:
                    int subCommand = Scan.selectType();
                    String[] userTask = Scan.saveLinesFromUser();
                    if (subCommand == 1) {
                        manager.getDefault().createTask(userTask);
                    } else if (subCommand == 2) {
                        manager.getDefault().createEpicTask(userTask);
                    } else if (subCommand == 3) {
                        id = Scan.selectId();
                        manager.getDefault().createSubTask(manager.getDefault().getTaskById(id),
                                userTask);
                    } else {
                        Print.printNoCommand();
                    }
                    break;
                case 2:
                    id = Scan.selectId();
                    userTask = Scan.saveLinesFromUser();
                    manager.getDefault().createSubTask(manager.getDefault().getTaskById(id),
                            userTask);
                    break;
                case 3:
                    printTasks.printList(manager.getDefault().getSingleTasks());
                    break;
                case 4:
                    printTasks.printList(manager.getDefault().getEpicTasks());
                    break;
                case 5:
                    id = Scan.selectId();
                    printTasks.printList(manager.getDefault().getSubTasksByEpic(manager.getDefault()
                            .getTaskById(id)));
                    break;
                case 6:
                    id = Scan.selectId();
                    printTasks.printTask(manager.getDefault().getTaskById(id));
                    break;
                case 7:
                    id = Scan.selectId();
                    updatePanel(id);
                    break;
                case 8:
                    manager.getDefault().removeAllTasks();
                    break;
                case 9:
                    id = Scan.selectId();
                    manager.getDefault().removeTask(manager.getDefault().getTaskById(id));
                    break;
                case 10:
                    printTasks.printList(manager.getDefault().getHistory());
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

    private void updatePanel(int id) {
        int command = -1;
        while (command != 0) {
            Print.printMenuToUpdateTask();
            command = Scan.getIntOrZero();
            switch (command) {
                case 0:
                    break;
                case 1:
                    Print.printName();
                    manager.getDefault().getTaskById(id).setName(Scan.selectString());
                    break;
                case 2:
                    Print.printDescription();
                    manager.getDefault().getTaskById(id).setDescription(Scan.selectString());
                    break;
                case 3:
                    Print.printStatusList();
                    manager.getDefault().getTaskById(id).setStatus(Scan.selectStatus());
                    break;
                default:
                    Print.printWrongValue();
                    break;
            }
        }
    }
}
