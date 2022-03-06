import repository.FileBackedTasksManager;
import service.CommandManager;
import service.Managers;
import tasks.EpicTask;
import tasks.SingleTask;
import tasks.SubTask;
import tasks.Task;

import java.io.File;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {
        CommandManager manager = new CommandManager();
        manager.getCommandPanel();
    }
}

