package service;

import java.io.File;
import java.nio.file.Paths;

public class CommandManager {

    Managers manager = new Managers();

    public void getCommandPanel() {
        int command = -1;
        int id;
        File file = Paths.get("./resources/data.csv").toFile();
        manager.getFileManager().loadFromFile(file);

        while (command != 0) {
            Print.printMenu();
            command = Scan.getIntOrZero();

            switch (command) {
                case 1:
                    int subCommand = Scan.selectType();
                    String[] userTask = Scan.saveLinesFromUser();
                    if (subCommand == 1) {
                        manager.getTaskManager().putTask(manager.getFactory()
                                .createSingleTask(userTask));
                    } else if (subCommand == 2) {
                        manager.getTaskManager().putTask(manager.getFactory()
                                .createEpicTask(userTask));
                    } else if (subCommand == 3) {
                        id = Scan.selectId();
                        manager.getTaskManager().putTask(manager.getFactory()
                                .createSubTask(manager.getTaskManager()
                                        .getTaskById(id), userTask));
                    } else {
                        Print.printNoCommand();
                    }
                    break;
                case 2:
                    id = Scan.selectId();
                    userTask = Scan.saveLinesFromUser();
                    manager.getTaskManager().putTask(manager.getFactory()
                            .createSubTask(manager.getTaskManager().getTaskById(id), userTask));
                    break;
                case 3:
                    manager.getPrinter().printMap(manager.getTaskManager().getSingleTasks());
                    break;
                case 4:
                    manager.getPrinter().printMap(manager.getTaskManager().getEpicTasks());
                    break;
                case 5:
                    id = Scan.selectId();
                    manager.getPrinter().printMap(manager.getTaskManager()
                            .getSubTasksByEpic(manager.getTaskManager().getTaskById(id)));
                    break;
                case 6:
                    id = Scan.selectId();
                    manager.getPrinter().printTask(manager.getTaskManager().getTaskById(id));
                    break;
                case 7:
                    id = Scan.selectId();
                    updatePanel(id);
                    break;
                case 8:
                    manager.getTaskManager().removeAllTasks();
                    break;
                case 9:
                    id = Scan.selectId();
                    manager.getTaskManager().removeTaskById(id);
                    break;
                case 10:
                    manager.getPrinter().printList(manager.getTaskManager().getHistory());
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
                    manager.getUpdate().updateName(manager.getTaskManager().getTaskById(id),
                            Scan.selectString());
                    if (manager.getTaskManager().updateTask(manager.getTaskManager().getTaskById(id))) {
                        Print.printTaskUpdated();
                    } else {
                        Print.printTaskNotUpdated();
                    }
                    break;
                case 2:
                    Print.printDescription();
                    manager.getUpdate().updateDescription(manager.getTaskManager().getTaskById(id),
                            Scan.selectString());
                    if (manager.getTaskManager().updateTask(manager.getTaskManager().getTaskById(id))) {
                        Print.printTaskUpdated();
                    } else {
                        Print.printTaskNotUpdated();
                    }
                    break;
                case 3:
                    Print.printStatusList();
                    manager.getUpdate().updateStatus(manager.getTaskManager().getTaskById(id),
                            Scan.selectStatus());
                    if (manager.getTaskManager().updateTask(manager.getTaskManager().getTaskById(id))) {
                        Print.printTaskUpdated();
                    } else {
                        Print.printTaskNotUpdated();
                    }
                    break;
                default:
                    Print.printNoCommand();
                    break;
            }
        }
    }
}
